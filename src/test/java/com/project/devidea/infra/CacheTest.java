package com.project.devidea.infra;

import com.project.devidea.modules.tagzone.tag.TagRepository;
import com.project.devidea.modules.tagzone.zone.ZoneRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@SpringBootTest
@EnableCaching
@Slf4j
public class CacheTest {
    @Autowired
    TagRepository tagRepository;
    @Autowired
    ZoneRepository zoneRepository;


    @PostConstruct
    void init(){
        System.out.println("init작업");
        tagRepository.findAll();
        zoneRepository.findAll();
    }
    //캐쉬 등록했을경우
    @Test
    @DisplayName("1")
    void TagCacheTest(){
        tagRepository.findAll();
        System.out.println("---------------------------------");
    }

    //캐쉬 등록안했을경우
    @Test
    @DisplayName("2")
    void zoneCacheTest(){
        zoneRepository.findAll();
        System.out.println("---------------------------------");
    }
}
