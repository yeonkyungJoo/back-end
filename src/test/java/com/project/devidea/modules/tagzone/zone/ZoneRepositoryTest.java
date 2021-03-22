//package com.project.devidea.modules.tagzone.zone;
//
//import com.project.devidea.infra.TestConfig;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//@ActiveProfiles("test")
//@Import(TestConfig.class)
//@Slf4j
//class ZoneRepositoryTest {
//
//    @Autowired
//    ZoneRepository zoneRepository;
//
//    /**
//     * initService를 주석한 상태에서 진행한 beforeEach입니다.
//     * init이 정상적으로 작동되면 이 메서드를 지워주세요! -범석
//     */
//    @BeforeEach
//    void init() throws IOException {
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
//    @Test
//    @DisplayName("모든 지역 가져오기")
//    void findAllZones() throws Exception {
//
////        when
//        List<Zone> zones = zoneRepository.findAll();
//
////        then
//        assertEquals(zones.size(), 360);
//        for (Zone zone : zones) {
////            log.info("city, province = {} {}", zone.getCity(), zone.getProvince());
//        }
//    }
//
//    @Test
//    @DisplayName("city, province에 포함된 zone들 찾아오기")
//    void findCitiesAndProvinces() throws Exception {
//
////        given
//        List<String> cities = Arrays.asList("서울특별시", "경기도");
//        List<String> provinces = Arrays.asList("동대문구", "중랑구", "수원시", "장안구");
//
////        when
//        List<Zone> zones = zoneRepository.findByCityInAndProvinceIn(cities, provinces);
//        List<String> getCities = zones.stream().map(Zone::getCity).collect(Collectors.toList());
//        List<String> getProvinces = zones.stream().map(Zone::getProvince).collect(Collectors.toList());
//
////        then
//        assertEquals(zones.size(), 4);
//        assertThat(getCities).contains("서울특별시", "경기도");
//        assertThat(getProvinces).contains("동대문구", "중랑구", "수원시", "장안구");
//    }
//}
