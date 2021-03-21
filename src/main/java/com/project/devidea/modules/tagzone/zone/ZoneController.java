package com.project.devidea.modules.tagzone.zone;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ZoneController {

    private final ZoneService zoneService;

    @GetMapping("/zones")
    public ZonesResponseDto findAllZones() {
        return zoneService.findAll();
    }
}
