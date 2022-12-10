package com.nil1one.baomidou.dynamic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
//@EnableAspectJAutoProxy(exposeProxy = true)
public class S01BaomidouDynamicApplication {

	public static void main(String[] args) {
		SpringApplication.run(S01BaomidouDynamicApplication.class, args);
	}

}
