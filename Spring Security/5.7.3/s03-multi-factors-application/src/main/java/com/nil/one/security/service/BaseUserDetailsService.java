package com.nil.one.security.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nil.one.entity.UserAuthority;
import com.nil.one.entity.UserCredential;
import com.nil.one.service.UserAuthorityService;
import com.nil.one.service.impl.UserCredentialServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public class BaseUserDetailsService implements UserDetailsService {
    private final String accType;

    public BaseUserDetailsService(String accType) {
        this.accType = accType;
    }

    @Autowired
    private UserCredentialServiceImpl userCredentialService;
    @Autowired
    private UserAuthorityService userAuthorityService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        LambdaQueryWrapper<UserCredential> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCredential::getAccType, this.accType).eq(UserCredential::getIdentifier, username);
        UserCredential userCredential = userCredentialService.getOne(wrapper);
        if (userCredential != null) {
            // UserAuthority<GrantedAuthority>
            List<UserAuthority> authorities = userAuthorityService.list(new LambdaQueryWrapper<UserAuthority>().eq(UserAuthority::getUid, userCredential.getUid()));
            return org.springframework.security.core.userdetails.User
                    .withUsername(userCredential.getIdentifier())
                    .password(userCredential.getCredential())
                    .authorities(authorities)
                    .build();
        }
        return null;
    }
}
