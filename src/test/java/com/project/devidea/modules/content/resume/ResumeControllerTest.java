package com.project.devidea.modules.content.resume;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.devidea.infra.MockMvcTest;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.account.repository.AccountRepository;
import com.project.devidea.modules.content.mentoring.account.WithAccount;
import com.project.devidea.modules.content.resume.form.CreateResumeRequest;
import com.project.devidea.modules.content.resume.form.UpdateResumeRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
class ResumeControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ResumeRepository resumeRepository;
    @Autowired
    ResumeService resumeService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CareerRepository careerRepository;

    @Test
    @DisplayName("이력서 등록")
    @WithAccount("yk")
    public void newResume() throws Exception {
        // Given
        // When, Then
        CreateResumeRequest request = CreateResumeRequest.builder()
                .phoneNumber("01012345678")
                .github("yk@github.com")
                .blog("yk@blog.com")
                .build();

        String id = mockMvc.perform(post("/resume/")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long resumeId = Long.parseLong(id);
        Optional<Resume> findResume = resumeRepository.findById(resumeId);
        assertTrue(findResume.isPresent());

        Resume resume = findResume.get();
        assertEquals("yk", resume.getAccount().getNickname());
        assertEquals("01012345678", resume.getPhoneNumber());
        assertEquals("yk@github.com", resume.getGithub());
        assertEquals("yk@blog.com", resume.getBlog());
    }

    @Test
    @DisplayName("이력서 등록 실패 - withoutAccount로 unauthorized")
    public void newResume_withoutAccount() throws Exception {
        // Given
        // When, Then
        CreateResumeRequest request = CreateResumeRequest.builder()
                .phoneNumber("01012345678")
                .github("yk@github.com")
                .blog("yk@blog.com")
                .build();

        mockMvc.perform(post("/resume/")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("이력서 수정")
    @WithAccount("yk")
    public void editResume() throws Exception {
        // Given
        Long resumeId = createResume("yk");

        // When, Then
        UpdateResumeRequest request = UpdateResumeRequest.builder()
                .phoneNumber("01056781234")
                .github("yk1@github.com")
                .blog("yk1@blog.com")
                .build();

        mockMvc.perform(post("/resume/edit")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<Resume> findResume = resumeRepository.findById(resumeId);
        assertTrue(findResume.isPresent());

        Resume resume = findResume.get();
        assertEquals(accountRepository.findByNickname("yk"), resume.getAccount());
        assertEquals("01056781234", resume.getPhoneNumber());
        assertEquals("yk1@github.com", resume.getGithub());
        assertEquals("yk1@blog.com", resume.getBlog());

    }

    @Test
    @DisplayName("이력서 수정 실패 - withoutAccount로 unauthorized")
    @WithAccount("yk")
    public void editResume_withoutAccount() throws Exception {
        // Given
        Long resumeId = createResume("yk");
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(null);

        // When, Then
        UpdateResumeRequest request = UpdateResumeRequest.builder()
                .phoneNumber("01056781234")
                .github("yk1@github.com")
                .blog("yk1@blog.com")
                .build();

        mockMvc.perform(post("/resume/edit")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    private Long createResume(String nickname) {
        Account account = accountRepository.findByNickname(nickname);
        Resume resume = Resume.builder()
                .account(account)
                .phoneNumber("01012345678")
                .github(nickname + "@github.com")
                .blog(nickname + "@blog.com")
                .build();
        return resumeRepository.save(resume).getId();
    }

    @Test
    @DisplayName("이력서 삭제 - CASCADE 확인")
    @WithAccount("yk")
    public void deleteResume() throws Exception {
        // Given
        Long resumeId = createResume("yk");
        Resume resume = resumeRepository.findById(resumeId).get();

        Career career = Career.createCareer(
                resume,
                "ABC",
                "senior",
                LocalDate.of(2021, 1, 22),
                LocalDate.of(2021, 1, 31),
                false,
                null,
                null,
                null);
        careerRepository.save(career);
        Long careerId = career.getId();

        // When, Then
        mockMvc.perform(post("/resume/delete"))
                .andDo(print())
                .andExpect(status().isOk());
        assertTrue(resumeRepository.findById(resumeId).isEmpty());
        // CASCADE 확인
        assertTrue(careerRepository.findById(careerId).isEmpty());
    }

    @Test
    @DisplayName("이력서 삭제 실패")
    @WithAccount("yk")
    public void deleteResume_withoutAccount() throws Exception {
        // Given
        Long resumeId = createResume("yk");
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(null);

        // When, Then
        mockMvc.perform(post("/resume/delete"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
        assertTrue(resumeRepository.findById(resumeId).isPresent());
    }
}