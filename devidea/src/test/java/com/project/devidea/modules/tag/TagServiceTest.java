package com.project.devidea.modules.tag;

import lombok.ToString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TagServiceTest {
    @Autowired
    TagRepository tagRepository;

    @DisplayName("Tag 잘주입됐는지 확인")
    @Test
    void Print() {
        List<Tag> tagList = tagRepository.findAll();
        tagList.stream().forEach((tag) -> System.out.println("tag:"+tag.toString()));
        return;
    }
}