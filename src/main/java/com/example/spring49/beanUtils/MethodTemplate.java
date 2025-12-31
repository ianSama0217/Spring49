package com.example.spring49.beanUtils;

import java.util.ArrayList;
import java.util.List;

public class MethodTemplate {
    public static void main(String[] args) {
        MyBeanFactory beanFactory = new MyBeanFactory();
        beanFactory.addBeanPostProcessor(bean -> System.out.println("解析@Autowired"));
        beanFactory.addBeanPostProcessor(bean -> System.out.println("解析@Resource"));
        beanFactory.getBean();
    }

    // Template Method Pattern
    static class MyBeanFactory{
        public Object getBean(){
            Object bean = new Object();
            System.out.println("建構: " + bean);
            System.out.println("依賴注入: " + bean); // @Autowired, @Resource
            for(BeanPostProcessor processor : processors){
                processor.inject(bean);
            }
            System.out.println("初始化: " + bean);
            return bean;
        }

        private List<BeanPostProcessor> processors = new ArrayList<>();

        public void addBeanPostProcessor(BeanPostProcessor processor){
            processors.add(processor);
        }
    }

    static interface BeanPostProcessor{
        public void inject(Object bean);
    }
}
