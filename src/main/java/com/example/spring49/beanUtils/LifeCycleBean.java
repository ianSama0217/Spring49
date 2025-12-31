package com.example.spring49.beanUtils;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LifeCycleBean {
    public LifeCycleBean() {
        System.out.println("建構LifeCycleBean!!");
    }

    @Autowired
    public void autowired(@Value("${JAVA_HOME}") String javaHome) {
        System.out.println("依賴注入: " + javaHome);
    }

    @PostConstruct
    public void init(){System.out.println("初始化...");}

    @PreDestroy
    public void destory(){System.out.println("銷毀...");}
}
