package com.example.talkroom.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Roxi酱
 */
@WebFilter(filterName = "user",urlPatterns = "/add")
public class UserFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("假装日志");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        HttpSession session=request.getSession();
        String id= (String) session.getAttribute("user");
        if(id!=null){
            filterChain.doFilter(request,response);
        }else {
            response.getWriter().write("请先登录");
        }
    }

    @Override
    public void destroy() {
        System.out.println("啊我死了");
    }
}
