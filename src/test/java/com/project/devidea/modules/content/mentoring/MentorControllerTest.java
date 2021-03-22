package com.project.devidea.modules.content.mentoring;

import com.project.devidea.infra.MockMvcTest;
import com.project.devidea.infra.WithAccount;
import com.project.devidea.modules.content.resume.Resume;
import com.project.devidea.modules.content.resume.ResumeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@MockMvcTest
class MentorControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    MentorRepository mentorRepository;
    @Autowired
    ResumeRepository resumeRepository;


    @Test
    @DisplayName("멘토 등록")
    @WithAccount(NickName ="yk")
    public void newMentor() {
        // Given
        Resume resume = Resume.builder().build();
        // When

        // Then
    }

    @Test
    @DisplayName("멘토 정보 수정")
    @WithAccount(NickName ="yk")
    public void editMentor() {
        // Given
        // When
        // Then
    }

    @Test
    @DisplayName("멘토 삭제")
    @WithAccount(NickName ="yk")
    public void quitMentor() {
        // Given
        // When
        // Then
    }


}