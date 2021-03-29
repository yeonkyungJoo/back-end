package com.project.devidea.infra.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.StaticResourceLocation;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

//    private final NotificationInterceptor notificationInterceptor;

    @Configuration
    public class addCorsMappings implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                .allowedOrigins("*");
        }
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
////        List<String> staticResourcesPath = Arrays.stream(StaticResourceLocation.values())
////                .flatMap(StaticResourceLocation::getPatterns)
////                .collect(Collectors.toList());
////        staticResourcesPath.add("/node_modules/**");
//
//        registry.addInterceptor(notificationInterceptor);
//    }
}
