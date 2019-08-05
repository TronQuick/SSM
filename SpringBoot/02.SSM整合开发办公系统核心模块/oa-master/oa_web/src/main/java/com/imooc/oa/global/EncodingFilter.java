package com.imooc.oa.global;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//字符集过滤器类
public class EncodingFilter implements Filter {

    //默认编码
    private String encoding = "utf-8";
    public void init(FilterConfig filterConfig) throws ServletException {
        //判断filterConfig.getInitParameter()初始化参数中encoding是否为空，
        // 如果不为空将值赋给encoding参数
        if(filterConfig.getInitParameter("encoding")!=null){
            encoding = filterConfig.getInitParameter("encoding");
        }
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        //设置字符集
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        //返回
        filterChain.doFilter(request,response);
    }

    public void destroy() {
    }
}
