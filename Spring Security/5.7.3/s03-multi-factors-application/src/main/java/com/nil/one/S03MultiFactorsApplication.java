package com.nil.one;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.nil.one.mapper")
public class S03MultiFactorsApplication {

    public static void main(String[] args) {
        SpringApplication.run(S03MultiFactorsApplication.class, args);
    }

}
