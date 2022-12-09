package com.nil1one.baomidou.dynamic.generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nil1one.baomidou.dynamic.generator.domain.TestUser;
import com.nil1one.baomidou.dynamic.generator.service.TestUserService;
import com.nil1one.baomidou.dynamic.generator.mapper.TestUserMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【test_user】的数据库操作Service实现
* @createDate 2022-12-10 02:56:00
*/
@Service
public class TestUserServiceImpl extends ServiceImpl<TestUserMapper, TestUser>
    implements TestUserService{

}




