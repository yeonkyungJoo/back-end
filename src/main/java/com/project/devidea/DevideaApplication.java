package com.project.devidea;

//import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
//@EnableScheduling
@SpringBootApplication
//@EnableAdminServer
@EnableCaching
@EnableJpaAuditing
public class DevideaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevideaApplication.class, args);
    }

}
