package com.project.devidea.modules.content.mentoring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.devidea.infra.MockMvcTest;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.account.repository.AccountRepository;
import com.project.devidea.modules.content.mentoring.account.WithAccount;
import com.project.devidea.modules.content.mentoring.form.CreateMentorRequest;
import com.project.devidea.modules.content.mentoring.form.UpdateMentorRequest;
import com.project.devidea.modules.content.resume.Resume;
import com.project.devidea.modules.content.resume.ResumeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
@Transactional
class MentorControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    MentorRepository mentorRepository;
    @Autowired
    MentorService mentorService;
    @Autowired
    ResumeRepository resumeRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("멘토 등록")
    @WithAccount("yk")
    public void newMentor() throws Exception {
        // Given
        Account account = accountRepository.findByNickname("yk");
        createResume(account);

        // When
        // Then
        CreateMentorRequest request = CreateMentorRequest.builder()
                .free(true)
                .build();

        String saveId = mockMvc.perform(post("/mentor/")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long mentorId = Long.parseLong(saveId);
        Optional<Mentor> findMentor = mentorRepository.findById(mentorId);
        assertTrue(findMentor.isPresent());

        Mentor mentor = findMentor.get();
        assertEquals("01012345678", mentor.getResume().getPhoneNumber());
        assertEquals("yk@github.com", mentor.getResume().getGithub());
        assertEquals("yk@blog.com", mentor.getResume().getBlog());
        assertEquals(account, mentor.getAccount());
        assertEquals(true, mentor.isFree());

    }

    private Resume createResume(Account account) {
        Resume resume = Resume.builder()
                .phoneNumber("01012345678")
                .github("yk@github.com")
                .blog("yk@blog.com")
                .account(account)
                .build();
        resumeRepository.save(resume);
        return resume;
    }

    @Test
    @DisplayName("멘토 등록 - 인증된 사용자 X")
    public void newMentor_withoutAccount() throws Exception {
        // Given
        Account account = accountRepository.findByNickname("yk");
        createResume(account);

        // When, Then
        CreateMentorRequest request = CreateMentorRequest.builder()
                .free(true)
                .build();

        mockMvc.perform(post("/mentor/")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("멘토 정보 수정")
    @WithAccount("yk")
    public void editMentor() throws Exception {
        // Given
        Account account = accountRepository.findByNickname("yk");
        Resume resume = createResume(account);

        Mentor mentor = Mentor.builder()
                .account(account)
                .resume(resume)
                .free(false)
                .cost(100000)
                .build();
        mentorService.createMentor(mentor);

        // When, Then
        UpdateMentorRequest request = UpdateMentorRequest.builder()
                .free(true)
                .cost(0)
                .build();
        mockMvc.perform(post("/mentor/update")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        Mentor findMentor = mentorRepository.findByAccountId(account.getId());
        assertEquals(mentor, findMentor);
        assertEquals(mentor.isFree(), findMentor.isFree());
        assertEquals(true, findMentor.isFree());
        assertEquals(0, findMentor.getCost());
        assertEquals(account, findMentor.getAccount());
        assertEquals(resume, findMentor.getResume());

    }

    @Test
    @DisplayName("멘토 삭제")
    @WithAccount("yk")
    public void quitMentor() throws Exception {
        // Given
        Account account = accountRepository.findByNickname("yk");
        Resume resume = createResume(account);

        Mentor mentor = Mentor.builder()
                .account(account)
                .resume(resume)
                .free(false)
                .cost(100000)
                .build();
        mentorService.createMentor(mentor);

        // When, Then
        mockMvc.perform(post("/mentor/delete"))
                .andDo(print())
                .andExpect(status().isOk());

        Mentor findMentor = mentorRepository.findByAccountId(account.getId());
        assertNull(findMentor);
        // assertNull(mentor);
    }

}