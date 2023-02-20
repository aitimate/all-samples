package com.nil.one.config;

import com.nil.one.filter.LoginFilter;
import com.nil.one.filter.ValidationFilter;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;

import java.io.PrintWriter;

@Configuration
public class SecurityConfig {

    @Bean
    @Lazy
    SecurityFilterChain securityFilterChain(HttpSecurity http, LoginFilter loginFilter) throws Exception {
        http.addFilterBefore(loginFilter, LogoutFilter.class);
        http.addFilterAfter(new ValidationFilter(), LogoutFilter.class);
        http.authorizeRequests().anyRequest().permitAll();
        http.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    SessionRegistryImpl sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    LoginFilter loginFilter(SessionRegistryImpl sessionRegistry, UserDetailsService userDetailsService) {
        LoginFilter loginFilter = new LoginFilter();
        // 验证成功处理器
        loginFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            SecurityProperties.User user = (SecurityProperties.User) authentication.getPrincipal();
            user.setPassword(null);
            // TODO Others
            out.write("登录成功!");
            out.flush();
            out.close();
        });
        // 验证失败处理器
        loginFilter.setAuthenticationFailureHandler((request, response, exception) -> {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            String msg = "登录异常";
            if (exception instanceof LockedException) {
                msg = "账户被锁定，请联系管理员!";
            } else if (exception instanceof CredentialsExpiredException) {
                msg = "密码过期，请联系管理员!";
            } else if (exception instanceof AccountExpiredException) {
                msg = "账户过期，请联系管理员!";
            } else if (exception instanceof DisabledException) {
                msg = "账户被禁用，请联系管理员!";
            } else if (exception instanceof BadCredentialsException) {
                msg = "用户名或者密码输入错误，请重新输入!";
            }
            out.write(msg);
            out.flush();
            out.close();
        });
        loginFilter.setFilterProcessesUrl("/login");
        // 设置验证管理者和验证器
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        loginFilter.setAuthenticationManager(new ProviderManager(daoAuthenticationProvider));
        // 设置session机制
        ConcurrentSessionControlAuthenticationStrategy sessionStrategy = new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry);
        sessionStrategy.setMaximumSessions(1);
        loginFilter.setSessionAuthenticationStrategy(sessionStrategy);
        return loginFilter;
    }
}
