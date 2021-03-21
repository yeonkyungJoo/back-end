package com.project.devidea.modules.content.mentoring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.devidea.infra.MockMvcTest;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.account.repository.AccountRepository;
import com.project.devidea.modules.content.mentoring.account.WithAccount;
import com.project.devidea.modules.content.mentoring.form.CreateMenteeRequest;
import com.project.devidea.modules.content.mentoring.form.UpdateMenteeRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
class MenteeControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MenteeRepository menteeRepository;

    @Test
    @DisplayName("멘티 등록 - 인증된 사용자")
    @WithAccount("yk")
    public void newMentee() throws Exception {
        // Given
        // When, Then
        CreateMenteeRequest request = CreateMenteeRequest.builder()
                .description("description")
                .free(true)
                .build();

        String result = mockMvc.perform(post("/mentee/")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                // .with(csrf()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long menteeId = Long.parseLong(result);
        Mentee findMentee = menteeRepository.findById(menteeId).get();

        assertNotNull(findMentee);
        assertEquals("description", findMentee.getDescription());
        assertEquals(true, findMentee.isFree());
        assertEquals(true, findMentee.isOpen());
        assertEquals("yk", findMentee.getAccount().getNickname());

    }

    @Test
    @DisplayName("멘티 등록 - 실패")
    public void newMentee_withoutAccount() throws Exception {
        // Given

        // When, Then
        CreateMenteeRequest request = CreateMenteeRequest.builder()
                .description("description")
                .free(true)
                .build();

        mockMvc.perform(post("/mentee/")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                // .with(csrf()))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }

    private Long createMentee(String nickname) {
        Account account = accountRepository.findByNickname("yk");
        Mentee mentee = Mentee.builder()
                .account(account)
                .description("description")
                .free(true)
                .build();
        mentee.publish();
        return menteeRepository.save(mentee).getId();
    }

    @Test
    @DisplayName("멘티 수정")
    @WithAccount("yk")
    public void editMentee() throws Exception {
        // Given
        Long menteeId = createMentee("yk");

        // When
        // Then
        UpdateMenteeRequest request = UpdateMenteeRequest.builder()
                .description("change description")
                .open(false)
                .free(false)
                .build();

        mockMvc.perform(post("/mentee/update")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        Mentee findMentee = menteeRepository.findById(menteeId).get();
        assertNotNull(findMentee);
        assertEquals("change description", findMentee.getDescription());
        assertEquals(false, findMentee.isFree());
        assertEquals(false, findMentee.isOpen());
        assertEquals("yk", findMentee.getAccount().getNickname());

    }

    @Test
    @DisplayName("멘티 삭제")
    @WithAccount("yk")
    public void quitMentee() throws Exception {
        // Given
        Long menteeId = createMentee("yk");

        // When
        // Then
        mockMvc.perform(post("/mentee/delete"))
                .andDo(print())
                .andExpect(status().isOk());

        Account account = accountRepository.findByNickname("yk");
        assertNotNull(account);
        assertNull(menteeRepository.findByAccountId(account.getId()));
        assertTrue(menteeRepository.findById(menteeId).isEmpty());
    }

    @Test
    @DisplayName("멘티 삭제 - 실패")
    @WithAccount("yk")
    public void quitMentee_withoutAccount() throws Exception {
        // Given
        Long menteeId = createMentee("yk");

        // When
        // Authentication 제거
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(null);

        // Then
        mockMvc.perform(post("/mentee/delete"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
        assertTrue(menteeRepository.findById(menteeId).isPresent());
    }
}