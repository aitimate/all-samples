package com.example.s01autoconfig.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "demo.server")
public class ServerProperties {
    private Integer port;

    public Integer getPort() {
        return port == null ? 8000 : port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
