package com.imooc.sm.global;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DispatcherServlet extends GenericServlet{

    private ApplicationContext context;

    public void init() throws  ServletException{
        super.init();
        context = new ClassPathXmlApplicationContext("spring.xml");
    }

    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletRequest request=(HttpServletRequest)req;
        HttpServletResponse response=(HttpServletResponse)res;

        /*
               staff/add.do        login.do
               staffController
               public void add(HttpServletRequest request, HttpServletResponse response){}
         */

        // 获取URL
        String path = request.getServletPath().substring(1);
        String beanName = null;
        String methodName = null;
        int index = path.indexOf("/");
        if (index != -1) {
            beanName = path.substring(0,index)+"Controller";
            methodName = path.substring(index,path.indexOf(".do"));
        }else {
            beanName = "selfController";
            methodName = path.substring(0,path.indexOf(".do"));
        }

        Object object = context.getBean(beanName);
        try {
            Method method = object.getClass().getMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
            try {
                method.invoke(object,request,response);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }


    }
}
