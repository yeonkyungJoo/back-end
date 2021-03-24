//package com.project.devidea.modules.tagzone.zone;
//
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//@ActiveProfiles("test")
//@Slf4j
//public class ZoneServiceTest {
//
//    @Autowired
//    ZoneRepository zoneRepository;
//    @Autowired
//    ZoneService zoneService;
//
//    /**
//     * initService를 주석한 상태에서 진행한 beforeEach입니다.
//     * init이 정상적으로 작동되면 이 메서드를 지워주세요! -범석
//     */
//    @BeforeEach
//    void init() throws Exception {
//        Map<String,Map<String,Boolean>> CheckMap=new HashMap<String, Map<String, Boolean>>();
//        if (zoneRepository.count() == 0) {
//            Resource resource = new ClassPathResource("zones_kr.csv");
//            List<Zone> zoneList = Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8).stream()
//                    .map(line -> {
//                        String[] split = line.split(",");
//                        return Zone.builder().city(split[0]).province(split[1]).build();
//                    }).collect(Collectors.toList());
//            zoneRepository.saveAll(zoneList);
//        }
//    }
//
//    @DisplayName("Zone 잘주입됐는지 확인")
//    @Test
//    void Print() {
//        List<Zone> zoneList = zoneRepository.findAll();
//        zoneList.stream().forEach((zone) -> System.out.println("Zone:" + zone.toString()));
//        return;
//    }
//
//    @Test
//    @DisplayName("모든 위치 가져오기 확인")
//    void findAllZones() throws Exception {
//
////        when
//        ZonesResponseDto dto = zoneService.findAll();
//        Set<String> cities = dto.getCities();
//        Map<String, List<String>> provinces = dto.getProvinces();
//
////        then
//        assertEquals(cities.size(), 17);
//        assertEquals(provinces.keySet().size(), 17);
//    }
//}
