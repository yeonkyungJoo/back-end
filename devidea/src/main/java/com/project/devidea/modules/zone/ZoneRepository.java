package com.project.devidea.modules.zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ZoneRepository extends JpaRepository<Zone, Long> {
    List<Zone> findAll();
    Zone findByCityAndProvince(String cityName, String provinceName);

}

