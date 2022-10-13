package com.nil.one.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nil.one.entity.User;
import com.nil.one.entity.UserAuthority;
import com.nil.one.service.UserAuthorityService;
import com.nil.one.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private UserAuthorityService userAuthorityService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername,username));
        List<UserAuthority> authorities = userAuthorityService.list(new LambdaQueryWrapper<UserAuthority>().eq(UserAuthority::getUid, user.getId()));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}