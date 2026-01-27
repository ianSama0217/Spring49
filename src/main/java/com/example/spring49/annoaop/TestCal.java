package com.example.spring49.annoaop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestCal {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(AppConfig.class);

        Calculator cal = ctx.getBean(Calculator.class);

        cal.add(1, 2);
        System.out.println("================================================");
        cal.sub(3, 4);
        System.out.println("================================================");
        cal.mul(5, 6);
        System.out.println("================================================");
        cal.div(7, 8);

        ctx.close();
    }
}
