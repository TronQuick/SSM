package com.imooc.aspectJ.demo2;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class MyAspectXml {

    // 前置通知
    public void before(JoinPoint joinPoint){
        System.out.println("XML方式的前置通知=============="+joinPoint);
    }

    // 后置通知
    public void afterReturing(Object result){
        System.out.println("XML方式的后置通知=============="+result);
    }

    // 环绕通知
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("XML方式的环绕前通知==============");
        Object obj = joinPoint.proceed();
        System.out.println("XML方式的环绕后通知==============");
        return obj;
    }

    // 异常抛出通知
    public void afterThrowing(Throwable e){
        System.out.println("XML方式的异常抛出通知============="+e.getMessage());
    }

    // 最终通知
    public void after(){
        System.out.println("XML方式的最终通知=================");
    }
}
