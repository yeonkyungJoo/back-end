package com.project.devidea.modules.tagzone.zone;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("ZoneService")
@Transactional
@RequiredArgsConstructor
public class ZoneService {

    private final ZoneRepository zoneRepository;

    @PostConstruct
    public void initZoneData() throws IOException {
        Map<String,Map<String,Boolean>> CheckMap=new HashMap<String, Map<String, Boolean>>();
        if (zoneRepository.count() == 0) {
            ClassPathResource resource = new ClassPathResource("zones_kr.csv");
            InputStream inputStream = resource.getInputStream();
            File file = File.createTempFile("zones_kr", ".csv");
            FileUtils.copyInputStreamToFile(inputStream, file);
            List<Zone> zoneList = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8).stream()
                    .map(line -> {
                        String[] split = line.split(",");
                        return Zone.builder().city(split[0]).province(split[1]).build();
                    }).collect(Collectors.toList());
            zoneRepository.saveAll(zoneList);
        }
    }

    @Transactional(readOnly = true)
    public ZonesResponseDto findAll() {
        List<Zone> zones = zoneRepository.findAll();
        ZonesResponseDto dto = new ZonesResponseDto();

        zones.forEach(zone -> {
            dto.getCities().add(zone.getCity());
            dto.getProvinces().computeIfAbsent(zone.getCity(), k -> new ArrayList<>());
            dto.getProvinces().get(zone.getCity()).add(zone.getProvince());
        });
        return dto;
    }
}
