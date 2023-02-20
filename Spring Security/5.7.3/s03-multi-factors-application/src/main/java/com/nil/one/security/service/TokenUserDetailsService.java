package com.nil.one.security.service;

import org.springframework.stereotype.Service;

@Service
public class TokenUserDetailsService extends BaseUserDetailsService {

    public TokenUserDetailsService() {
        super("Token");
    }
}