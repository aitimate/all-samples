package com.nil1one.solog;

import com.nil1one.solog.service.ServiceTests;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = {SoLogAspect.class, ServiceTests.class})
class SpringBootStarterSologApplicationTests {
    @Resource
    ServiceTests serviceTests;

    @Test
    void contextLoads() throws InterruptedException {
        serviceTests.xxx();
    }

}
