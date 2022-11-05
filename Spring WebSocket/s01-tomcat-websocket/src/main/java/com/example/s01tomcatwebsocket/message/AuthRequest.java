package com.example.s01tomcatwebsocket.message;

import lombok.Data;
import lombok.experimental.Accessors;

/**
* 1-用户认证请求
* */
@Data
@Accessors(chain = true)
public class AuthRequest implements Message {

    public static final String TYPE = "AUTH_REQUEST";

    /**
     * 认证 Token
     */
    private String accessToken;
}