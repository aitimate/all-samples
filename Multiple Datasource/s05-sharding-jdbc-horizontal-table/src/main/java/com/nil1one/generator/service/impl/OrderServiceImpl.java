package com.nil1one.generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nil1one.generator.domain.Order;
import com.nil1one.generator.service.OrderService;
import com.nil1one.generator.mapper.OrderMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【t_order0】的数据库操作Service实现
* @createDate 2022-12-11 23:06:02
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

}




