package com.nil.one;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nil.one.entity.User;
import com.nil.one.entity.UserAuthority;
import com.nil.one.entity.UserHealth;
import com.nil.one.service.impl.UserAuthorityServiceImpl;
import com.nil.one.service.impl.UserHealthServiceImpl;
import com.nil.one.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class S01BaseApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserHealthServiceImpl userHealthService;
    @Autowired
    UserAuthorityServiceImpl userAuthorityService;

    @Test
    void testMybatisPlus() {
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        System.out.println(userService.getOne(userWrapper.eq(User::getUsername, "foo")));

        LambdaQueryWrapper<UserHealth> userHealthWrapper = new LambdaQueryWrapper<>();
        System.out.println(userHealthService.list(userHealthWrapper.eq(UserHealth::getUsername, "foo")));

        LambdaQueryWrapper<UserAuthority> userAuthorityWrapper = new LambdaQueryWrapper<>();
        System.out.println(userAuthorityService.list(userAuthorityWrapper.eq(UserAuthority::getUid, 1)));
    }
}
