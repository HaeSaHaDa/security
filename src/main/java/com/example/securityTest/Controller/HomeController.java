package com.example.securityTest.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication a = context.getAuthentication();

        return "home.html";
    }

    @GetMapping("/none")
    public String error(){

        return "error.html";
    }
}
