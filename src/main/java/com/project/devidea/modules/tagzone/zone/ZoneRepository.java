package com.project.devidea.modules.tagzone.zone;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;


public interface ZoneRepository extends JpaRepository<Zone, Long> {
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true")})
    List<Zone> findAll();
    Zone findByCityAndProvince(String cityName, String provinceName);

    List<Zone> findByCityInAndProvinceIn(List<String> cities, List<String> provinces);
}

