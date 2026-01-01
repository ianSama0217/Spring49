package com.example.spring49.a04;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.StandardEnvironment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/*
*   Autowired 運行分析
*/
public class DigInAutowired {
    public static void main(String[] args) throws NoSuchFieldException, NoSuchMethodException {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerSingleton("bean2", new Bean2());
        beanFactory.registerSingleton("bean3", new Bean3());
        beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver()); // @Value
        beanFactory.addEmbeddedValueResolver(new StandardEnvironment()::resolveRequiredPlaceholders); // ${}解析容器

        // 查找那些屬性, 方法加了Autowired
        AutowiredAnnotationBeanPostProcessor processor = new AutowiredAnnotationBeanPostProcessor();
        processor.setBeanFactory(beanFactory);

        Bean1 bean1 = new Bean1();
        System.out.println(bean1);
        processor.postProcessProperties(null, bean1, "bean1"); // 執行依賴注入 @Autowired, @Value
        System.out.println(bean1);

        // 按照類型查找值
        System.out.println(">>>>>>>>>>>>按照類型查找值");
        Field bean3 = Bean1.class.getDeclaredField("bean3");
        DependencyDescriptor dd1 = new DependencyDescriptor(bean3, false);
        Object obj1 = beanFactory.doResolveDependency(dd1, null, null, null);
        System.out.println(obj1);

        Method setBean2 = Bean1.class.getDeclaredMethod("setBean2", Bean2.class);
        DependencyDescriptor dd2 =
                new DependencyDescriptor(new MethodParameter(setBean2, 0), true);
        Object obj2 = beanFactory.doResolveDependency(dd2, null, null, null);
        System.out.println(obj2);

        Method setHome = Bean1.class.getDeclaredMethod("setHome", String.class);
        DependencyDescriptor dd3 =
                new DependencyDescriptor(new MethodParameter(setHome, 0), true);
        Object obj3 = beanFactory.doResolveDependency(dd3, null, null, null);
        System.out.println(obj3);
    }
}
