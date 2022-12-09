package com.nil1one.baomidou.dynamic.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.nil1one.baomidou.dynamic.generator.domain.TestOrder;
import com.nil1one.baomidou.dynamic.generator.domain.TestUser;
import com.nil1one.baomidou.dynamic.generator.service.TestOrderService;
import com.nil1one.baomidou.dynamic.generator.service.TestUserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class TestService {
    @Resource
    private TestOrderService orderService;

    @Resource
    private TestUserService userService;

    /**
     * 作用: 注入当前对象的代理对象
     * 前提: 开启AOP代理并暴露: `@EnableAspectJAutoProxy(exposeProxy = true)`
     * 用法: proxy.saveOrder()，等价于 (TestService)AopContext.currentProxy().saveOrder()
     * *
     * 说明: 为什么不用 this？ 因为只有通过代理对象调用才有事务，直接使用this会指向原生对象而非代理
     * */
    @Resource
    @Lazy
    private TestService proxy;

    /**
     * @Transactional REQUIRES_NEW事务: 如果存在事务，则挂起并新建
     * */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @DS("db2")
    public void saveUser() {
        TestUser user = new TestUser();
        user.setUsername("test2");
        user.setPassword("123456");
        user.setCreateTime(new Date());
        System.out.println("saveUser::" + userService.save(user));
        // 注意: 此处不能使用 this，否则没有事务逻辑
        proxy.saveOrder();
    }
    /**
     * @Transactional REQUIRES_NEW事务: 如果存在事务，则挂起并新建
     * */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @DS("db1")
    public void saveOrder() {
        TestOrder order = new TestOrder();
        order.setId(1); // Test Exception: Duplicate entry '1', try to comment this line and re-run
        order.setUserId(2);
        System.out.println("saveOrder::" + orderService.save(order));
    }
}
