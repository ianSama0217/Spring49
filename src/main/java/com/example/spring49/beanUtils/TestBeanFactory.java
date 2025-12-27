package com.example.spring49.beanUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class TestBeanFactory {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // bean的定義(class, scope, 初始化, 銷毀)

        AbstractBeanDefinition beanDefinition =
                BeanDefinitionBuilder.genericBeanDefinition(Config.class).setScope("singleton").getBeanDefinition();
        beanFactory.registerBeanDefinition("config", beanDefinition);

        // 給BeanFactory添加一些常用的後處理器
        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);

        // BeanFactory後處理器主要功能: 補充了一些bean定義
        beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values().forEach(beanFactoryPostProcessor -> {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        });

        // Bean後處理器, 針對bean的生命週期各個階段提供擴展, 例如 @Autowired, @Resource...
        beanFactory.getBeansOfType(BeanPostProcessor.class).values().forEach(beanFactory::addBeanPostProcessor);

        for(String beanName : beanFactory.getBeanDefinitionNames()){
            System.out.println(beanName);
        }

        beanFactory.preInstantiateSingletons(); // 準備好所有單例
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("bean2: " + beanFactory.getBean(Bean1.class).getBean2());
    }

    @Configuration
    static class Config {
        @Bean
        public Bean1 bean1() {return new Bean1();};

        @Bean
        public Bean2 bean2() {return new Bean2();};
    }

    static class Bean1 {
        public Bean1() {System.out.println("建構Bean1()");}

        @Autowired
        private Bean2 bean2;

        public Bean2 getBean2() {return bean2;}
    }

    static class Bean2 {
        public Bean2() {System.out.println("建構Bean2()");}
    }
}
