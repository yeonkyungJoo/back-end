package com.project.devidea.infra.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 인터셉터로 요청을 안전하게 보호하는 방법을 설정하기 위한 오버라이딩이다.
        http.authorizeRequests()  // 모두 다 인증이 되어있어야한다.
                .antMatchers("/").permitAll()
                .antMatchers("/h2-console/**").permitAll();
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");
    }
}
