package com.project.devidea.modules.content.resume;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.devidea.infra.MockMvcTest;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.account.repository.AccountRepository;
import com.project.devidea.modules.content.mentoring.account.WithAccount;
import com.project.devidea.modules.content.resume.career.Career;
import com.project.devidea.modules.content.resume.career.CareerRepository;
import com.project.devidea.modules.content.resume.form.career.CreateCareerRequest;
import com.project.devidea.modules.content.resume.form.career.UpdateCareerRequest;
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.tag.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
@Transactional
class CareerControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ResumeRepository resumeRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    CareerRepository careerRepository;
    @Autowired
    TagRepository tagRepository;

    Set<String> tagSet = new HashSet<>();
    Set<String> updateTagSet = new HashSet<>();

    @BeforeEach
    @Transactional
    void init() throws Exception {
        Resource resource = null;
        if (tagRepository.count() == 0) {
            resource = new ClassPathResource("tag_kr.csv");
            Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8).stream()
                    .forEach(line -> {
                        String[] split = line.split(",");
                        Tag tag = Tag.builder()
                                .firstName(split[1])
                                .secondName(split[2].equals("null") ? null : split[2])
                                .thirdName(split[3].equals("null") ? null : split[3])
                                .build();
                        if (!split[0].equals("parent")) {
                            Tag tagParent = tagRepository.findByFirstName(split[0]);
                            tagParent.addChild(tag);
                        }
                        tagRepository.save(tag);
                    });
        }

        List<Tag> tagList = tagRepository.findAll();

        Random random = new Random();
        while (tagSet.size() < 3) {
            int randomIdx = random.nextInt(tagList.size());
            tagSet.add(tagList.get(randomIdx).toString());
        }
        while (updateTagSet.size() < 3) {
            int randomIdx = random.nextInt(tagList.size());
            updateTagSet.add(tagList.get(randomIdx+1).toString());
        }
    }

    private Set<Tag> getTags(Set<String> tags) {
        return tags.stream()
                .map(tag -> tagRepository.findByFirstName(tag)).collect(Collectors.toSet());
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
    @DisplayName("Career 등록")
    @WithAccount("yk")
    public void newCareer() throws Exception {
        // Given
        Account account = accountRepository.findByNickname("yk");
        Resume resume = createResume(account);

        // When
        // Then
        CreateCareerRequest request = CreateCareerRequest.builder()
                .companyName("ABC")
                .duty("senior")
                .startDate("2021-01-22")
                .endDate("2021-01-31")
                .present(false)
                .tags(tagSet)
                .detail("")
                .url("")
                .build();

        String result = mockMvc.perform(post("/resume/career/")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long careerId = Long.parseLong(result);
        Optional<Career> findCareer = careerRepository.findById(careerId);
        assertTrue(findCareer.isPresent());

        Career career = findCareer.get();
        assertNotNull(career.getResume());
        assertTrue(resume.getCareers().contains(career));
        assertEquals("ABC", career.getCompanyName());
        assertEquals("senior", career.getDuty());
        assertEquals(LocalDate.of(2021, 1, 22), career.getStartDate());
        assertEquals(LocalDate.of(2021, 1, 31), career.getEndDate());
        assertEquals(false, career.isPresent());
        assertEquals(3, career.getTags().size());

    }

    @Test
    @DisplayName("Career 등록 - 재직중인 경우")
    @WithAccount("yk")
    public void newCareer_present() throws Exception {
        // Given
        Account account = accountRepository.findByNickname("yk");
        Resume resume = createResume(account);

        // When
        // Then
        CreateCareerRequest request = CreateCareerRequest.builder()
                .companyName("ABC")
                .duty("senior")
                .startDate("2021-01-22")
                .endDate("")
                .present(true)
                .tags(tagSet)
                .detail("")
                .url("")
                .build();

        String result = mockMvc.perform(post("/resume/career/")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long careerId = Long.parseLong(result);
        Optional<Career> findCareer = careerRepository.findById(careerId);
        assertTrue(findCareer.isPresent());

        Career career = findCareer.get();
        assertNotNull(career.getResume());
        assertTrue(resume.getCareers().contains(career));
        assertEquals("ABC", career.getCompanyName());
        assertEquals("senior", career.getDuty());
        assertEquals(LocalDate.of(2021, 1, 22), career.getStartDate());
        assertEquals(true, career.isPresent());
        assertEquals(3, career.getTags().size());

    }

    @Test
    @DisplayName("Career 등록 - 재직중인 경우, Invalid Input")
    @WithAccount("yk")
    public void newCareer_presentButInvalidInput() throws Exception {
        // Given
        Account account = accountRepository.findByNickname("yk");
        Resume resume = createResume(account);

        // When
        // Then
        CreateCareerRequest request = CreateCareerRequest.builder()
                .companyName("ABC")
                .duty("senior")
                .startDate("2021-01-22")
                .endDate("")
                .present(false)
                .tags(tagSet)
                .detail("")
                .url("")
                .build();

        mockMvc.perform(post("/resume/career/")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    @DisplayName("Career 등록 - @NotEmpty 필드 값 X")
    @WithAccount("yk")
    public void newCareer_withWrongRequest() throws Exception {
        // Given
        Account account = accountRepository.findByNickname("yk");
        Resume resume = createResume(account);

        // When
        // Then
        CreateCareerRequest request = CreateCareerRequest.builder()
                .companyName("ABC")
                .startDate("2021-01-22")
                .endDate("2021-01-31")
                .present(false)
                .tags(tagSet)
                .detail("")
                .url("")
                .build();

        mockMvc.perform(post("/resume/career/")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(400));
        assertTrue(resume.getCareers().isEmpty());
    }

//    @Test
//    @DisplayName("Career 등록 - 인증 실패")
//    @WithAccount("yk")
//    public void newCareer_withoutAccount() throws Exception {
//        // Given
//        Account account = accountRepository.findByNickname("yk");
//        Resume resume = createResume(account);
//
//        // When
//        SecurityContext context = SecurityContextHolder.getContext();
//        context.setAuthentication(null);
//
//        // Then
//        CreateCareerRequest request = CreateCareerRequest.builder()
//                .companyName("ABC")
//                .duty("senior")
//                .startDate("2021-01-22")
//                .endDate("2021-01-31")
//                .present(false)
//                .tags(tagSet)
//                .detail("")
//                .url("")
//                .build();
//
//        mockMvc.perform(post("/resume/career/")
//                .content(objectMapper.writeValueAsString(request))
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().is(403));
//        assertTrue(resume.getCareers().isEmpty());
//    }

    @Test
    @DisplayName("Career 등록 - 이력서가 없는 경우")
    @WithAccount("yk")
    public void newCareer_withoutResume() throws Exception {
        // Given
        // When, Then
        CreateCareerRequest request = CreateCareerRequest.builder()
                .companyName("ABC")
                .duty("senior")
                .startDate("2021-01-22")
                .endDate("2021-01-31")
                .present(false)
                .tags(tagSet)
                .detail("")
                .url("")
                .build();

        mockMvc.perform(post("/resume/career/")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    @DisplayName("Career 수정")
    @WithAccount("yk")
    public void updateCareer() throws Exception {
        // Given
        Account account = accountRepository.findByNickname("yk");
        createResume(account);
        Resume resume = resumeRepository.findByAccountId(account.getId());

        Career career = Career.createCareer(
                resume,
                "ABC",
                "senior",
                LocalDate.of(2021, 1, 22),
                LocalDate.of(2021, 1, 31),
                false,
                getTags(tagSet),
                "",
                "");
        careerRepository.save(career);

        // When, Then
        UpdateCareerRequest request = UpdateCareerRequest.builder()
                .companyName("ABCD")
                .duty("senior")
                .startDate("2021-01-22")
                .endDate("")
                .present(true)
                .tags(updateTagSet)
                .detail("")
                .url("")
                .build();

        Long careerId = career.getId();
        mockMvc.perform(post(String.format("/resume/career/%d/edit", careerId))
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        assertNotNull(career.getResume());
        assertTrue(resume.getCareers().contains(career));

        assertEquals("ABCD", career.getCompanyName());
        Career findCareer = resume.getCareers().stream()
                .filter(c -> careerId.equals(c.getId())).findAny().get();
        assertEquals("ABCD", findCareer.getCompanyName());
        assertEquals("senior", career.getDuty());
        assertEquals(LocalDate.of(2021, 1, 22), career.getStartDate());
        assertNull(career.getEndDate());
        assertEquals(true, career.isPresent());

    }

    @Test
    @DisplayName("Career 삭제")
    @WithAccount("yk")
    public void deleteCareer() throws Exception {
        // Given
        Account account = accountRepository.findByNickname("yk");
        createResume(account);
        Resume resume = resumeRepository.findByAccountId(account.getId());

        Career career = Career.createCareer(
                resume,
                "ABC",
                "senior",
                LocalDate.of(2021, 1, 22),
                LocalDate.of(2021, 1, 31),
                false,
                getTags(tagSet),
                "",
                "");
        careerRepository.save(career);
        assertTrue(resume.getCareers().contains(career));

        // When, Then
        Long careerId = career.getId();
        mockMvc.perform(post(String.format("/resume/career/%d/delete", careerId)))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<Career> findCareer = careerRepository.findById(careerId);
        assertTrue(findCareer.isEmpty());
        assertFalse(resume.getCareers().contains(career));
    }

}
