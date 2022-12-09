package com.nil1one.baomidou.dynamic;

import com.nil1one.baomidou.dynamic.generator.domain.TestOrder;
import com.nil1one.baomidou.dynamic.generator.domain.TestUser;
import com.nil1one.baomidou.dynamic.generator.service.TestOrderService;
import com.nil1one.baomidou.dynamic.generator.service.TestUserService;
import com.nil1one.baomidou.dynamic.service.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
class S01BaomidouDynamicApplicationTests {

    @Autowired
    private TestOrderService orderService;

    @Autowired
    private TestUserService userService;

    @Test
    void list() {
        List<TestUser> users = userService.list();
        List<TestOrder> orders = orderService.list();
        System.out.println("---------------------");
        users.forEach(System.out::println);
        orders.forEach(System.out::println);
        System.out.println("---------------------");
    }

    @Autowired
    TestService testService;

    @Test
    void saveUser() {
        testService.saveUser();
    }
}
