package com.project.devidea.modules.tag;

import com.project.devidea.modules.zone.Zone;
import com.project.devidea.modules.zone.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class TagService {

    private final TagRepository tagRepository;

    @PostConstruct
    public void initTagData() throws IOException {
        if (tagRepository.count() == 0) {
            Resource resource = new ClassPathResource("tag_kr.csv");
            Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8).stream()
                    .forEach(line -> {
                        String[] split = line.split(",");
                        Tag tag = Tag.builder()
                                .firstName(split[1])
                                .secondName(split[2].equals("null")?null:split[2])
                                .thirdName(split[3].equals("null")?null:split[3])
                                .build();
                        if (!split[0].equals("parent")){
                            Tag tagParent = tagRepository.findByFirstName(split[0]);
                            tagParent.addChild(tag);
                        }
                        tagRepository.save(tag);
                    });
        }
    }
}
