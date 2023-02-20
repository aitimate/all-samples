package com.nil1one.solog;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.web.server.WebFilterChainProxy;

@Configuration
@EnableAspectJAutoProxy
@ConditionalOnClass(value = {WebFilterChainProxy.class})
public class SoLogAutoConfiguration {

    @Bean
    public SoLogAspect operateLogAspect() {
        return new SoLogAspect();
    }
}