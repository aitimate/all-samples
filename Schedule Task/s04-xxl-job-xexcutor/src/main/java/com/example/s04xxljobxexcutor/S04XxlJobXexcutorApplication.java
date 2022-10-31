package com.example.s04xxljobxexcutor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
public class S04XxlJobXexcutorApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(S04XxlJobXexcutorApplication.class, args);
        new CountDownLatch(1).await();
    }
}
