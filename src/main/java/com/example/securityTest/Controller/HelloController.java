package com.example.securityTest.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.concurrent.DelegatingSecurityContextCallable;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@Slf4j
public class HelloController {


    @GetMapping("/hello")
    public String hello(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication a = context.getAuthentication();

        return "Hello! " + a.getName() + "!";
    }

    @GetMapping("/bye")
    @Async
    public void gootbye(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication a = context.getAuthentication();
        System.out.println(a.getName());
    }

    @GetMapping("/ciao")
    public String ciao() throws Exception{
        Callable<String> task = () -> {
            SecurityContext context = SecurityContextHolder.getContext();
            log.info("==================");

            return context.getAuthentication().getName();
        };
        ExecutorService e = Executors.newCachedThreadPool();
        try{
            var contextTask = new DelegatingSecurityContextCallable<>(task);
            log.info("==================");
            return "Ciao, " + e.submit(contextTask).get() + "!";
        } finally {
            e.shutdown();
        }

    }

    @GetMapping("/hola")
    public String hola() throws Exception{
        Callable<String> task = () -> {
            SecurityContext context = SecurityContextHolder.getContext();
            log.info("==================");

            return context.getAuthentication().getName();
        };
        ExecutorService e = Executors.newCachedThreadPool();
        e = new DelegatingSecurityContextExecutorService(e);
        try{
            return "Hola, " + e.submit(task).get() + "!";
        } finally {
            e.shutdown();
        }

    }
}
