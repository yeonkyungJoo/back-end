package com.project.devidea.infra.config;

import com.project.devidea.infra.config.security.LoginUser;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;
import java.util.Collections;

@Configuration
@EnableSwagger2
@EnableAutoConfiguration
//@Profile({"local","dev"})
public class SwaggerConfig
 {
    @Bean
    public Docket api() {

        ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder.name("Authorization") //헤더 이름
                .description("Access Tocken") //설명
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();
        List<Parameter> aParameters = new ArrayList<>();
        aParameters.add(aParameterBuilder.build());


        return new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(LoginUser.class)
                .globalOperationParameters(aParameters)
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.project.devidea"))
                .paths(PathSelectors.any()).build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("TEST API", "Some custom description of API.", "0.0.1", "Terms of service", new Contact("DevIdea", "https://devidea.com", "public@gmail.com"), "License of API", "API license URL", Collections.emptyList());
    }
}

