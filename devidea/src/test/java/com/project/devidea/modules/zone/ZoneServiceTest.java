package com.project.devidea.modules.zone;

import com.project.devidea.modules.tag.Tag;
import com.project.devidea.modules.tag.TagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class ZoneServiceTest {
    @Autowired
    ZoneRepository zoneRepository;

    @DisplayName("Tag 잘주입됐는지 확인")
    @Test
    void Print() {
        List<Zone> zoneList = zoneRepository.findAll();
        zoneList.stream().forEach((zone) -> System.out.println("Zone:"+zone.toString()));
        return;
    }

}