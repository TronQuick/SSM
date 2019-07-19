package com.imooc.aop.demo2;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MyCglibProxy implements MethodInterceptor {

    private ProductDao productDao;

    public MyCglibProxy(ProductDao productDao) {
        this.productDao = productDao;
    }

    public  Object createProxy() {
        //1.创建核心类
        Enhancer enhancer= new Enhancer();
        //2.设置父类
        enhancer.setSuperclass(productDao.getClass());
        //3.设置回调
        enhancer.setCallback(this);
        //4.生成代理
        Object proxy=enhancer.create();
        return proxy;
    }

    //传入代理类proxy，和方法参数args
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        if ("save".equals(method.getName())) {
            //增强方法
            System.out.println("save权限校验...");
            return methodProxy.invokeSuper(proxy,args);
        }

        //执行原来的方法
        return methodProxy.invokeSuper(proxy,args);
    }
}
