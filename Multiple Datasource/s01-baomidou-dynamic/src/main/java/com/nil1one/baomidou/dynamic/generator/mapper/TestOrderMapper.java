package com.nil1one.baomidou.dynamic.generator.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.nil1one.baomidou.dynamic.generator.domain.TestOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Administrator
* @description 针对表【test_order】的数据库操作Mapper
* @createDate 2022-12-10 02:56:00
* @Entity com.nil1one.baomidou.dynamic.generator.domain.TestOrder
*/
@Mapper
@DS("db1")
public interface TestOrderMapper extends BaseMapper<TestOrder> {

}




