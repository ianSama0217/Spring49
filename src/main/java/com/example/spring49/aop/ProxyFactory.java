package com.example.spring49.aop;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class ProxyFactory {
    // 目標對象
    private final Object target;
    public ProxyFactory(Object target) {
        this.target = target;
    }

    // 返回代理對象
    public Object getProxy() {
        /*
         *  newProxyInstance()參數:
         *  1. ClassLoader: 加載動態生成代理類的加載器
         *  2. Class[] interfaces: 目標物件實現所有接口的class類型陣列
         *  3. InvocationHandler: 設置代理物件實現目標物件方法的過程
         */
        // ClassLoader
        ClassLoader classLoader = target.getClass().getClassLoader();
        // interfaces
        Class<?>[] interfaces = target.getClass().getInterfaces();
        // InvocationHandler
        InvocationHandler invocationHandler = new InvocationHandler() {
            /*
             *   invoke()參數:
             *
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("[Proxy][日誌] " + method.getName() + ", 參數: " + Arrays.toString(args)); // 動態代理日誌內容
                Object result = method.invoke(target, args);
                System.out.println("[Proxy][日誌] " + method.getName() + ", 結果: " + result); // 動態代理日誌內容
                return result;
            }
        };

        return Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
    }
}
