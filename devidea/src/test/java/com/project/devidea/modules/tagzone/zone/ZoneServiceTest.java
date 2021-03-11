package com.project.devidea.modules.tagzone.zone;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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