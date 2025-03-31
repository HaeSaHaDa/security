package com.example.securityTest.security.authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 인증이 성공했을 때의 논리를 맞춤 구성
 */
@Component
public class CustomAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        var authorities = authentication.getAuthorities();

        var auth =
                authorities.stream()
                        .filter(a -> a.getAuthority().equals("read"))
                        .findFirst();

        System.out.println("success");


        if (auth.isPresent()) {
            response
                    .sendRedirect("/home");
        } else {
            response
                    .sendRedirect("/error");
        }
    }
}
