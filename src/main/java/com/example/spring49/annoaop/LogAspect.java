package com.example.spring49.annoaop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LogAspect {
    // 重用切入點表達式
    @Pointcut(value = "execution(public int com.example.spring49.annoaop.CalculatorImpl.*(..))")
    private void pointCut(){}

    /* 設定切入點和通知類型
     * 通知類型: @Before, @AfterReturning, @AfterThrowing, @After, @Around
     */

    @Before(value = "pointCut()")
    public void beforeMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        System.out.println("[Before][日誌]方法名稱: " + methodName + ", 參數: " + Arrays.toString(args));
    }

    @AfterReturning(value = "pointCut()", returning = "result")
    public void afterReturningMethod(JoinPoint joinPoint, Object result){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("[AfterReturning][日誌]方法名稱: " + methodName + ", 結果: " + result);
    }

    @AfterThrowing(value = "pointCut()", throwing = "ex")
    public void afterThrowingMethod(JoinPoint joinPoint, Throwable ex){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("[AfterThrowing][日誌]方法名稱: " + methodName + ", 異常: " + ex);
    }

    @After(value = "pointCut()")
    public void afterMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("[After][日誌]方法名稱: " + methodName);
    }

    @Around(value = "pointCut()")
    public int aroundMethod(ProceedingJoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String argString = Arrays.toString(args);
        Object result = null;
        try{
            System.out.println("[Around Before][日誌]方法名稱: " + methodName + ", 參數: " + Arrays.toString(args));
            result = joinPoint.proceed();
            System.out.println("[Around After][日誌]方法名稱: " + methodName);
        } catch (Throwable e) {
            System.out.println("[Around AfterThrowing][日誌]方法名稱: " + methodName + ", 異常: " + e);
        } finally {
            System.out.println("[Around AfterReturning][日誌]方法名稱: " + methodName + ", 結果: " + result);
        }

        return (int) result;
    }
}
