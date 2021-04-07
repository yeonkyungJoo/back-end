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
import com.project.devidea.modules.tagzone.tag.Tag;
import com.project.devidea.modules.tagzone.tag.TagRepository;
import com.project.devidea.modules.tagzone.zone.Zone;
import com.project.devidea.modules.tagzone.zone.ZoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    TagRepository tagRepository;
    @Autowired
    ZoneRepository zoneRepository;

    Set<String> tagSet = new HashSet<>();
    Set<String> zoneSet = new HashSet<>();

    Set<String> updateTagSet = new HashSet<>();
    Set<String> updateZoneSet = new HashSet<>();

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

        if (zoneRepository.count() == 0) {
            resource = new ClassPathResource("zones_kr.csv");
            Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8).stream()
                    .forEach(line -> {
                        String[] split = line.split(",");
                        Zone zone = Zone.builder()
                                .city(split[0])
                                .province(split[1])
                                .build();
                        zoneRepository.save(zone);
                    });
        }

        List<Tag> tagList = tagRepository.findAll();
        List<Zone> zoneList = zoneRepository.findAll();

        Random random = new Random();
        while (tagSet.size() < 3) {
            int randomIdx = random.nextInt(tagList.size());
            tagSet.add(tagList.get(randomIdx).toString());
        }
        while (updateTagSet.size() < 3) {
            int randomIdx = random.nextInt(tagList.size());
            updateTagSet.add(tagList.get(randomIdx).toString());
        }
        while (zoneSet.size() < 3) {
            int randomIdx = random.nextInt(zoneList.size());
            zoneSet.add(zoneList.get(randomIdx).toString());
        }
        while (updateZoneSet.size() < 3) {
            int randomIdx = random.nextInt(zoneList.size());
            updateZoneSet.add(zoneList.get(randomIdx).toString());
        }
    }

    private Set<Tag> getTags(Set<String> tags) {
        return tags.stream()
                .map(tag -> tagRepository.findByFirstName(tag)).collect(Collectors.toSet());
    }

    private Set<Zone> getZones(Set<String> zones) {
        return zones.stream().map(zone -> {
            String[] locations = zone.split("/");
            return zoneRepository.findByCityAndProvince(locations[0], locations[1]);
        }).collect(Collectors.toSet());
    }

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
                .zones(zoneSet)
                .tags(tagSet)
                .free(true)
                .cost(0)
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
        assertEquals(0, mentor.getCost());
        assertEquals(3, mentor.getZones().size());
        assertEquals(3, mentor.getTags().size());
        assertEquals(true, mentor.isOpen());
    }

    @Test
    @DisplayName("멘토 등록 - Invalid Input으로 400 에러 발생")
    @WithAccount("yk")
    public void newMentor_withInvalidInput() throws Exception {
        // Given
        Account account = accountRepository.findByNickname("yk");
        createResume(account);

        // When
        // Then
        CreateMentorRequest request = CreateMentorRequest.builder()
                .zones(zoneSet)
                .tags(tagSet)
                .free(true)
                .cost(10000)
                .build();

        mockMvc.perform(post("/mentor/")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    @DisplayName("멘토 등록 - Invalid Input으로 400 에러 발생")
    @WithAccount("yk")
    public void newMentor_withInvalidInputEmptyZoneSet() throws Exception {
        // Given
        Account account = accountRepository.findByNickname("yk");
        createResume(account);

        // When
        // Then
        CreateMentorRequest request = CreateMentorRequest.builder()
                .zones(new HashSet<String>())
                .tags(tagSet)
                .free(true)
                .cost(10000)
                .build();

        mockMvc.perform(post("/mentor/")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(400));
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
                .zones(zoneSet)
                .tags(tagSet)
                .free(true)
                .cost(0)
                .build();

        mockMvc.perform(post("/mentor/")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(403));
    }

    @Test
    @DisplayName("멘토 정보 수정")
    @WithAccount("yk")
    public void editMentor() throws Exception {
        // Given
        Account account = accountRepository.findByNickname("yk");
        Resume resume = createResume(account);

        Mentor mentor = Mentor.createMentor(account, resume, getZones(zoneSet), getTags(tagSet), false, 10000);
        mentorRepository.save(mentor);

        // When, Then
        UpdateMentorRequest request = UpdateMentorRequest.builder()
                .zones(updateZoneSet)
                .tags(updateTagSet)
                .free(true)
                .cost(0)
                .open(false)
                .build();

        mockMvc.perform(post("/mentor/update")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        Mentor findMentor = mentorRepository.findByAccountId(account.getId());
        assertEquals(true, findMentor.isFree());
        assertEquals(0, findMentor.getCost());
        assertEquals(false, findMentor.isOpen());
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

        Mentor mentor = Mentor.createMentor(account, resume, getZones(zoneSet), getTags(tagSet), false, 10000);
        mentorRepository.save(mentor);

        // When, Then
        mockMvc.perform(post("/mentor/delete"))
                .andDo(print())
                .andExpect(status().isOk());

        Mentor findMentor = mentorRepository.findByAccountId(account.getId());
        assertNull(findMentor);
        assertNotNull(resumeRepository.findByAccountId(account.getId()));
    }

}
