package com.example.spring49.aop;

public class TestCal {
    public static void main(String[] args) {
        // 創建代理對象
        ProxyFactory proxyFactory = new ProxyFactory(new CalculatorImpl());
        Calculator proxy = (Calculator) proxyFactory.getProxy();
        proxy.add(1, 2);
        proxy.sub(2, 3);
        proxy.mul(2, 3);
        proxy.div(8, 2);
    }
}
