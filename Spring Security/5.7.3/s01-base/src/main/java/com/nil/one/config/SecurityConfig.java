package com.nil.one.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.InsecureChannelProcessor;
import org.springframework.security.web.access.channel.SecureChannelProcessor;

import java.util.Arrays;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin().defaultSuccessUrl("/home", true);
        http.authorizeRequests().anyRequest().authenticated();
        return http.build();
    }
    @Bean
    SecurityFilterChain securityFilterChain2(HttpSecurity http) throws Exception {
        // 1- HTTP 协议处理器
        InsecureChannelProcessor insecureChannelProcessor = new InsecureChannelProcessor();
        insecureChannelProcessor.setEntryPoint((request,response)->{
            // TODO 指点正确入口 或 使用实现类 RetryWithHttpsEntryPoint
        });
        // 2- HTTPS 协议处理器
        SecureChannelProcessor secureChannelProcessor = new SecureChannelProcessor();
        secureChannelProcessor.setEntryPoint((request,response)->{
            // TODO 指点正确入口 或 使用实现类 RetryWithHttpEntryPoint
        });
        http.requiresChannel((builder)->{
            builder.channelProcessors(Arrays.asList(insecureChannelProcessor,secureChannelProcessor));
            // 3.* HTTPS=>SecureChannelProcessor的映射，数量不限
            builder.mvcMatchers("/login1").requiresSecure();
            // 3.* HTTP=>InsecureChannelProcessor的映射，数量不限
            builder.mvcMatchers("/login2").requiresInsecure();
        });
        // TODO 其他节点配置
        return http.build();
    }
}
