package com.example.spring49.applicationUtils;

import com.example.spring49.beanUtils.TestBeanFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.tomcat.servlet.TomcatServletWebServerFactory;
import org.springframework.boot.web.context.servlet.AnnotationConfigServletWebApplicationContext;
import org.springframework.boot.web.server.servlet.ServletWebServerFactory;
import org.springframework.boot.web.server.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.boot.webmvc.autoconfigure.DispatcherServletRegistrationBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class TestApplication {
    public static void main(String[] args) {
        System.out.println("調用testClassPathXmlApplicationContext()");
        testClassPathXmlApplicationContext();
        System.out.println("調用testFileSystemXmlApplicationContext()");
        testFileSystemXmlApplicationContext();
//        System.out.println("調用testXmlBeanDefinitionReader()");
//        testXmlBeanDefinitionReader();
        System.out.println("調用testAnnotationConfigApplicationContext()");
        testAnnotationConfigApplicationContext();
        System.out.println("調用testAnnotationConfigServletWebServerApplicationContext()");
        testAnnotationConfigServletWebServerApplicationContext();
    }

    private static void testClassPathXmlApplicationContext() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("b01.xml");

        for(String beanName : context.getBeanDefinitionNames()){
            System.out.println(beanName);
        }

        System.out.println("bean1: " + context.getBean(Bean2.class).getBean1());
    }

    private static void testFileSystemXmlApplicationContext() {
        FileSystemXmlApplicationContext context =
                new FileSystemXmlApplicationContext("src\\main\\resources\\b01.xml");

        for(String beanName : context.getBeanDefinitionNames()){
            System.out.println(beanName);
        }

        System.out.println("bean1: " + context.getBean(Bean2.class).getBean1());
    }

    // ClassPathXmlApplicationContext && FileSystemXmlApplicationContext 讀取xml實現
    private static void testXmlBeanDefinitionReader() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        System.out.println("讀取xml前...");
        for(String beanName : beanFactory.getBeanDefinitionNames()){
            System.out.println(beanName);
        }
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(new ClassPathResource("b01.xml"));
        System.out.println("讀取xml後...");
        for(String beanName : beanFactory.getBeanDefinitionNames()){
            System.out.println(beanName);
        }
    }

    private static void testAnnotationConfigApplicationContext() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        for(String beanName : context.getBeanDefinitionNames()){
            System.out.println(beanName);
        }

        System.out.println("bean1: " + context.getBean(Bean2.class).getBean1());
    }

    // 用於web環境
    private static void testAnnotationConfigServletWebServerApplicationContext() {
        AnnotationConfigServletWebServerApplicationContext context = new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
    }

    @Configuration
    static class WebConfig{
        // tomcat web server服務器
        @Bean
        public ServletWebServerFactory servletWebServerFactory(){
            return new TomcatServletWebServerFactory();
        }

        // 前處理器
        @Bean
        public DispatcherServlet  dispatcherServlet(){
            return new DispatcherServlet();
        }

        // 將DispatcherServlet注入至tomcat服務器
        @Bean
        public DispatcherServletRegistrationBean registrationBean(DispatcherServlet  dispatcherServlet){
            return new DispatcherServletRegistrationBean(dispatcherServlet, "/");
        }

        @Bean("/hello")
        public Controller controller(){
            return (request, response) -> {
                response.getWriter().print("hello controller");
                return null;
            };
        }
    }

    @Configuration
    static class Config {
        @Bean
        public Bean1 bean1() {return new Bean1();};

        @Bean
        public Bean2 bean2(Bean1 bean1) {
            Bean2 bean2 = new Bean2();
            bean2.setBean1(bean1);
            return bean2;
        };
    }

    static class Bean1{}

    static class Bean2{
        private Bean1 bean1;

        public Bean1 getBean1() {return bean1;}

        public void setBean1(Bean1 bean1) {this.bean1 = bean1;}
    }
}
