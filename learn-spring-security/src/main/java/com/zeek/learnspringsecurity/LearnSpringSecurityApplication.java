package com.zeek.learnspringsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class LearnSpringSecurityApplication {

    @GetMapping("/")
    public String hello() {
        return "hello, spring security";
    }

    public static void main(String[] args) {
        SpringApplication.run(LearnSpringSecurityApplication.class, args);
    }

}
