package com.example.spring49;

import jakarta.annotation.Resource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Spring49Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Spring49Application.class, args);
		context.close();
	}
}
