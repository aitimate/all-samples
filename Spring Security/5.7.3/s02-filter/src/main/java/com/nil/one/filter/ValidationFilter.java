package com.nil.one.filter;

import org.apache.logging.log4j.util.Strings;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ValidationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String flag = httpServletRequest.getHeader("Host");
        if (Strings.isBlank(flag)) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        System.out.println("Host: " + flag);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
