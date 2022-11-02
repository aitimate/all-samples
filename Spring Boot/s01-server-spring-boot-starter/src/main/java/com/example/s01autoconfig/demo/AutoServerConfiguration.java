package com.example.s01autoconfig.demo;

import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.InetSocketAddress;

@Configuration
@EnableConfigurationProperties(ServerProperties.class) // 启用属性配置类: ServerProperties
public class AutoServerConfiguration {
    private final Logger logger = LoggerFactory.getLogger(AutoServerConfiguration.class);

    @Bean
    @ConditionalOnClass(HttpServer.class) // 条件注解
    public HttpServer httpServer(ServerProperties serverProperties) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(serverProperties.getPort()), 0);
        server.start();
        logger.info("\n\n[httpServer][启动服务器成功，端口为:{}]\n\n", serverProperties.getPort());
        return server;
    }
}
