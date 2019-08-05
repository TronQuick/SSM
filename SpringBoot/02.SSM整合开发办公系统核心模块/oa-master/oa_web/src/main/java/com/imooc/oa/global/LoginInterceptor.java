package com.imooc.oa.global;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//登录拦截器类
public class LoginInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //获取请求路径
        String url = httpServletRequest.getRequestURI();
        //通过url.indexOf("login")查看登录路径中是否有login字段
        //toLowerCase()用来将URL全部转化为小写，再做对比
        if(url.toLowerCase().indexOf("login")>=0){
            //如果字段数大于等于0，则放行
            return true;
        }

        //获取session
        HttpSession session = httpServletRequest.getSession();
        if(session.getAttribute("employee")!=null){
            return true;
        }
        //拦截后跳转到"/to_login"
        httpServletResponse.sendRedirect("/to_login");
        return false;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
