package com.example.securityTest.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

//@Configuration
//public class WebAuthorizationConfig {
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .httpBasic(withDefaults())
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().permitAll())
//                .formLogin(withDefaults());  // 기본 로그인 폼 사용
//
//        return http.build();
//    }
//}
