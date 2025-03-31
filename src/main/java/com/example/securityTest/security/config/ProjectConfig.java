package com.example.securityTest.security.config;

import com.example.securityTest.security.authentication.CustomAuthenticationFailureHandler;
import com.example.securityTest.security.authentication.CustomAuthenticationSuccessHandler;
import com.example.securityTest.security.model.User;
import com.example.securityTest.security.service.InMemoryUserDetailsService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
public class ProjectConfig {


    @Autowired
    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private CustomAuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails u = new User("john", "12345", "read");
        List<UserDetails> users = List.of(u);
        return new InMemoryUserDetailsService(users);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public InitializingBean initializingBean() {

        /**
         스레드 이용시 보안 컨텍스트 접근 전략
           1. MODE_THREADLOCAL
              : 각 스레드의 보안 컨텍스트를 격리할 수 있게 해준다.
                (새로운 스레드 생성 시 기존 스레드의 보안 컨텍스트 접근이 힘들다..)
           2. MODE_INHERITEDTHREADLOCAL
              : 요청의 원래 스레드에 있는 세부 정보를 비동기 메서드의 새로 생성된 스레드로 복사한다.
                (프레임워크가 자체적으로 스레드를 만들때만 작동한다. 직접 스레드 구현 시 MODE_THREADLOCAL와 같은 문제가 발생한다.  )
           3. MODE_GLOBAL
            : 모든 스레드가 같은 보안 컨텍스트에 접근한다. 따라서 경합 상황이 발생할 수 있으므로 동기화를 처리해야 한다.
         **/

        return () -> SecurityContextHolder.setStrategyName(
                SecurityContextHolder.MODE_THREADLOCAL
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.httpBasic(c -> {
//            c.realmName("OTHER");
//            c.authenticationEntryPoint(new CustomEntryPoint());
//        });

        http.formLogin(form -> {
            form
                    .successHandler(authenticationSuccessHandler)
                    .failureHandler(authenticationFailureHandler);

        });

        http.authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated());
        return http.build();
    }
}
