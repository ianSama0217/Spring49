package com.example.spring49.annoaop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.example.spring49.annoaop")
@EnableAspectJAutoProxy
public class AppConfig {
}
