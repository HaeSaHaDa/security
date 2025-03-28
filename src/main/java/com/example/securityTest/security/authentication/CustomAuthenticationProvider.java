package com.example.securityTest.security.authentication;

import com.example.securityTest.security.model.User;
import com.example.securityTest.security.service.InMemoryUserDetailsService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableAsync
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails u = new User("john", "12345", "read");
        List<UserDetails> users = List.of(u);
        new InMemoryUserDetailsService(users);

        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());


        UserDetails user = userDetailsService.loadUserByUsername(username);

        if (passwordEncoder.matches(password, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(
                    username,
                    password,
                    u.getAuthorities());
        } else {
            System.out.println("not token");
            throw new BadCredentialsException("Something went wrong");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        System.out.println("supports");
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }


}
