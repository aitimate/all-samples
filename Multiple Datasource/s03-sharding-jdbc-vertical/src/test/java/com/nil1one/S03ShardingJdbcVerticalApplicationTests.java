package com.nil1one;

import com.nil1one.generator.domain.TestOrder;
import com.nil1one.generator.domain.TestUser;
import com.nil1one.generator.service.TestOrderService;
import com.nil1one.generator.service.TestUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class S03ShardingJdbcVerticalApplicationTests {

    @Autowired
    private TestUserService userService;

    @Autowired
    private TestOrderService orderService;

    /**
     * 1-写入测试
     */
    @Test
    void saveUser() {
        TestUser user = new TestUser();
        user.setUsername("test2");
        userService.save(user);

        user = new TestUser();
        user.setUsername("test3");
        userService.save(user);

        user = new TestUser();
        user.setUsername("test4");
        userService.save(user);
    }

    /**
     * 2-写入测试
     */
    @Test
    void saveOrder() {
        TestOrder order = new TestOrder();
        order.setUserId(100);
        orderService.save(order);

        order = new TestOrder();
        order.setUserId(101);
        orderService.save(order);

        order = new TestOrder();
        order.setUserId(102);
        orderService.save(order);
    }

    /**
     * 3-负载均衡测试，期望√: 控制台打印，每次读取从节点都在变化
     */
    @Test
    void balanceUser() {
        userService.list();

        userService.list();

        userService.list();
    }

    /**
     * 4-负载均衡测试，期望√: 控制台打印，每次读取从节点都在变化
     */
    @Test
    void balanceOrder() {
        orderService.list();

        orderService.list();

        orderService.list();
    }
}
