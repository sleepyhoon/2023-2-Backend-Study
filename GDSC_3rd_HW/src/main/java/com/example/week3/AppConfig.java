package com.example.week3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ClassA classA() {
        return new ClassA();
    }
}
