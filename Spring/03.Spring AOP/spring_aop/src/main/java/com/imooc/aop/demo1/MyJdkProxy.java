package com.imooc.aop.demo1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyJdkProxy  implements InvocationHandler {
    private UserDao userDao;

    public MyJdkProxy(UserDao userDao) {
        this.userDao=userDao;
    }

    public  Object createProxy() {
        //要传递三个参数：1.类加载器；2.实现的所有的接口；3.InvocationHandler接口的实例对象
        //返回的是一个类对象（即代理类）
        Object proxy=Proxy.newProxyInstance(userDao.getClass().getClassLoader(),userDao.getClass().getInterfaces(),this);
        return  proxy;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if ("save".equals(method.getName())) {
            System.out.println("save方法的权限校验");
            return method.invoke(userDao,args);
        }
        //args：方法参数列表
        return method.invoke(userDao,args);
    }
}
