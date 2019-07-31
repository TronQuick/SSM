package com.imooc.handler;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class MyHandler implements Controller {

    @Override
    public ModelAndView handleRequest(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse) throws Exception {

        // ModelAndView用于装载模型数据和逻辑视图
        ModelAndView modelAndView = new ModelAndView();
        // 添加模型数据
        modelAndView.addObject("name","Tom");
        // 添加逻辑视图
        modelAndView.setViewName("show");

        return modelAndView;
    }
}
