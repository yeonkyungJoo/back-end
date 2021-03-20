package com.project.devidea.modules.tagzone.zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ZoneRepository extends JpaRepository<Zone, Long> {
    List<Zone> findAll();
    Zone findByCityAndProvince(String cityName, String provinceName);

    List<Zone> findByCityInAndProvinceIn(List<String> cities, List<String> provinces);
}

