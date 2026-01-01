package com.example.spring49.a04;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.support.GenericApplicationContext;

public class Application04 {
    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();

        context.registerBean("bean1", Bean1.class);
        context.registerBean("bean2", Bean2.class);
        context.registerBean("bean3", Bean3.class);
        context.registerBean("bean4", Bean4.class);

        context.getDefaultListableBeanFactory().setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver()); // 防止context報錯(目前不了解功能)
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class); // 添加@Autowired, @Value處理器
        context.registerBean(CommonAnnotationBeanPostProcessor.class); // 添加@Resource, @PostConstruct, @PreDestory處理器
        ConfigurationPropertiesBindingPostProcessor.register(context.getDefaultListableBeanFactory()); // 添加@ConfigurationProperties處理器

        context.refresh(); // 執行beanFactory後處理器, 添加bean後處理器
        System.out.println(context.getBean(Bean4.class));
        context.close();
    }
}
