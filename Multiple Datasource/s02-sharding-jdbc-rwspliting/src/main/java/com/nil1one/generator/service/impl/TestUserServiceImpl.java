package com.nil1one.generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nil1one.generator.domain.TestUser;
import com.nil1one.generator.service.TestUserService;
import com.nil1one.generator.mapper.TestUserMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【test_user】的数据库操作Service实现
* @createDate 2022-12-11 05:04:40
*/
@Service
public class TestUserServiceImpl extends ServiceImpl<TestUserMapper, TestUser>
    implements TestUserService{

}




