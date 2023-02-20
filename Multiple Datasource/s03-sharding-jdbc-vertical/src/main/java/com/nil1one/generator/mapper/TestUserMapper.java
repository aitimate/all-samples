package com.nil1one.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nil1one.generator.domain.TestUser;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Administrator
* @description 针对表【test_user】的数据库操作Mapper
* @createDate 2022-12-11 05:04:40
* @Entity com.nil1one.generator.domain.TestUser
*/
@Mapper
public interface TestUserMapper extends BaseMapper<TestUser> {

}




