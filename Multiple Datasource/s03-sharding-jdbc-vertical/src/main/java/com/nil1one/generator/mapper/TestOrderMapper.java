package com.nil1one.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nil1one.generator.domain.TestOrder;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Administrator
* @description 针对表【test_order】的数据库操作Mapper
* @createDate 2022-12-11 16:02:10
* @Entity com.nil1one.generator.domain.TestOrder
*/
@Mapper
public interface TestOrderMapper extends BaseMapper<TestOrder> {

}




