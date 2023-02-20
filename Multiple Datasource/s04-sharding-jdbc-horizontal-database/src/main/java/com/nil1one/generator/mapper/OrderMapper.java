package com.nil1one.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nil1one.generator.domain.Order;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Administrator
* @description 针对表【t_order】的数据库操作Mapper
* @createDate 2022-12-11 22:40:00
* @Entity com.nil1one.generator.domain.Order
*/
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}




