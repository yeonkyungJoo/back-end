package com.project.devidea.modules.content.mentoring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.devidea.infra.MockMvcTest;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.account.repository.AccountRepository;
import com.project.devidea.modules.content.mentoring.account.WithAccount;
import com.project.devidea.modules.content.mentoring.form.CreateMenteeRequest;
import com.project.devidea.modules.content.mentoring.form.UpdateMenteeRequest;
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
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
@Transactional
class MenteeControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MenteeRepository menteeRepository;
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
    @DisplayName("멘티 등록 - empty zones")
    @WithAccount("yk")
    public void newMentee_withInvalidInput() throws Exception {
        // Given
        // When, Then
        CreateMenteeRequest request = CreateMenteeRequest.builder()
                .description("description")
                .zones(new HashSet<String>())
                .tags(new HashSet<String>())
                .free(true)
                .build();

        mockMvc.perform(post("/mentee/")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                // .with(csrf()))
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    @DisplayName("멘티 등록 - 인증된 사용자")
    @WithAccount("yk")
    public void newMentee() throws Exception {
        // Given
        // When, Then
        CreateMenteeRequest request = CreateMenteeRequest.builder()
                .description("description")
                .zones(zoneSet)
                .tags(tagSet)
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
        assertEquals(3, findMentee.getZones().size());
        assertEquals(3, findMentee.getTags().size());
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
                .zones(zoneSet)
                .tags(tagSet)
                .free(true)
                .build();

        mockMvc.perform(post("/mentee/")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                // .with(csrf()))
                .andDo(print())
                .andExpect(status().is(403));

    }

    private Long createMentee(String nickname) {
        Account account = accountRepository.findByNickname("yk");
        Mentee mentee = Mentee.builder()
                .account(account)
                .tags(getTags(tagSet))
                .zones(getZones(zoneSet))
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
                .zones(updateZoneSet)
                .tags(updateTagSet)
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
        assertEquals(getZones(updateZoneSet), findMentee.getZones());
        assertEquals(getTags(updateTagSet), findMentee.getTags());
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
                .andExpect(status().is(403));
        assertTrue(menteeRepository.findById(menteeId).isPresent());
    }
}