package com.example.securityTest.security.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserManagementConfig {

    @Autowired
    AuthenticationProvider authenticationProvider;

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(authenticationProvider);

        auth.inMemoryAuthentication()
                .withUser("jhon")
                .password("12345")
                .authorities("ROLE_USER")
                .and()
                .passwordEncoder(NoOpPasswordEncoder.getInstance());

//        var userDetailService = new InMemoryUserDetailsManager();
//        UserDetails user1 = new User("Jhon", "12345", List.of(new SimpleGrantedAuthority("ROLE_USER")));
//
//        var user = User.withUserDetails(user1)
//                .build();
//
//        userDetailService.createUser(user);

//        auth.userDetailsService(userDetailService).passwordEncoder(NoOpPasswordEncoder.getInstance());

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
