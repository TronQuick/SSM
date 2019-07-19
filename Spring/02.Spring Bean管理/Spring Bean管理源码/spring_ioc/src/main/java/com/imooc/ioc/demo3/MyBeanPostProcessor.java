package com.imooc.ioc.demo3;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("第五步：初始化前方法...");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, String beanName) throws BeansException {
        System.out.println("第八步：初始化后方法...");

        //如果是userDao类，则进行增强代理，否正返回原来的bean
        if("userDao".equals(beanName)){

            //产生proxy代理对象
            Object proxy = Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                    //如果是save方法，进行增强
                    if("save".equals(method.getName())){
                        System.out.println("权限校验===================");
                        return method.invoke(bean,args);
                    }

                    return method.invoke(bean,args);
                }
            });
            return proxy;
        }else{
            return bean;
        }

    }
}
