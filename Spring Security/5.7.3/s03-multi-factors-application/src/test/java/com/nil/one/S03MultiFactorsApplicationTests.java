package com.nil.one;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class S03MultiFactorsApplicationTests {

    class A {
        public boolean isCool(){
            return false;
        }
    }

    class B extends A {
        public boolean isCool(){
            return true;
        }
    }

    @Test
    void contextLoads() {
        B b = new B();
        System.out.println(b.isCool());
        System.out.println(b.getClass());
        A a = b;
        System.out.println(a.isCool());
        System.out.println(a.getClass());
    }
}
