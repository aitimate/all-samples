package com.nil.one.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nil.one.entity.UserHealth;
import com.nil.one.service.impl.UserHealthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @Autowired
    private UserHealthServiceImpl userHealthService;

    @GetMapping("/home")
    @PreAuthorize("hasAnyAuthority('READ')")
    public String main(Authentication auth) {
        String username = auth.getName();
        LambdaQueryWrapper<UserHealth> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserHealth::getUsername,username);
        return "username: %s, health: %s".formatted(username,userHealthService.list(queryWrapper));
    }
}
