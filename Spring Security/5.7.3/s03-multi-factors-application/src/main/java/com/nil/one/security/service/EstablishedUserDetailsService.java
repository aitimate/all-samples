package com.nil.one.security.service;

import org.springframework.stereotype.Service;

@Service
public class EstablishedUserDetailsService extends BaseUserDetailsService {

    public EstablishedUserDetailsService() {
        super("自建账号");
    }
}