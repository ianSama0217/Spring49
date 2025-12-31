package com.example.spring49.beanUtils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application03 {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application03.class, args);
        context.close();
    }
}
