package com.nil1one;

import com.nil1one.generator.domain.TestUser;
import com.nil1one.generator.service.TestUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@SpringBootTest
class S02ShardingJdbcRwsplitingApplicationTests {

    @Autowired
    private TestUserService userService;

    /**
     * 1-普通读写测试，期望√: 控制台打印，写入节点: master，读取节点: slave1
     * */
    @Test
    void saveAndRead() {
        TestUser user = new TestUser();
        user.setUsername("test2");
        user.setPassword("123456");
        user.setCreateTime(new Date());
        userService.save(user);
        List<TestUser> users = userService.list();
        users.forEach(System.out::println);
    }

    /**
     * 2-事务测试，期望√: 控制台打印，写入节点: master，读取节点: master
     * */
    @Transactional
    @Test
    void saveAndReadTransactional() {
        TestUser user = new TestUser();
        user.setUsername("test2");
        user.setPassword("123456");
        user.setCreateTime(new Date());
        userService.save(user);
        List<TestUser> users = userService.list();
        users.forEach(System.out::println);
    }

    /**
     * 3-负载均衡测试，期望√: 控制台打印，每次读取从节点都在变化
     * */
    @Test
    void balance() {
        List<TestUser> users1 = userService.list();
        users1.forEach(System.out::println);

        List<TestUser> users2 = userService.list();
        users2.forEach(System.out::println);

        List<TestUser> users3 = userService.list();
        users3.forEach(System.out::println);
    }
}
