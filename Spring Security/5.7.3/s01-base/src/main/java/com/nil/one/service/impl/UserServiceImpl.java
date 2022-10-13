package com.nil.one.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nil.one.entity.User;
import com.nil.one.service.UserService;
import com.nil.one.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【t_user】的数据库操作Service实现
* @createDate 2022-10-13 20:07:29
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




