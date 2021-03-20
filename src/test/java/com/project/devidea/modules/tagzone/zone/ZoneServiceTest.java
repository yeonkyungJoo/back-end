//package com.project.devidea.modules.tagzone.zone;
//
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
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
//
//
//    @DisplayName("Tag 잘주입됐는지 확인")
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
//
////        print
////        cities.forEach(str -> log.info("cities = {}", str));
////        log.info("==============================구분===================================");
////        provinces.forEach((key, value) -> log.info("{} = {}", key, value));
//    }
//}
