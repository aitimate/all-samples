package com.nil.one.filter;


import javax.servlet.*;
import java.io.IOException;

public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("IP: " + servletRequest.getRemoteAddr());
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
