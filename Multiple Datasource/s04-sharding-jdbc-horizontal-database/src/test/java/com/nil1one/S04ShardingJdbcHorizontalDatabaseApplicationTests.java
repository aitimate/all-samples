package com.nil1one;

import com.nil1one.generator.domain.Order;
import com.nil1one.generator.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class S04ShardingJdbcHorizontalDatabaseApplicationTests {

    @Autowired
    OrderService orderService;

    /**
     * 水平分库写入测试，期望: 0 2 4 -> h_db0，1 3 -> h_db1
     * 说明: 为了保证多库主键的唯一性，不要使用自增策略
     * */
    @Test
    void save() {
        for (int i = 0; i < 5; i++) {
            Order order = new Order();
            order.setUserId((long) i);
            orderService.save(order);
        }
    }

    /**
     * 水平分库查询测试
     * */
    @Test
    void read() {
        orderService.list().forEach(System.out::println);
    }
}
