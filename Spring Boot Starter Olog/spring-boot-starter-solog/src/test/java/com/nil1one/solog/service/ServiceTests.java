package com.nil1one.solog.service;

import com.nil1one.solog.SoLog;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@SpringBootTest
@Service
public class ServiceTests {
    @SoLog( "test")
    public void xxx() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("ServiceTests");
    }
}
