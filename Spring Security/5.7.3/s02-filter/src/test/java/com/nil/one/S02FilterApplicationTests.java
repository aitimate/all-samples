package com.nil.one;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class S02FilterApplicationTests {

    @Test
    void contextLoads() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        //注意这里键名和键值都是String类型的
        Map<String, String> map = new HashMap<>();
        map.put("name", "小李飞刀");
        map.put("sex", "男");

        //先生成一个json方便理解
        String json = objectMapper.writeValueAsString(map);
        System.out.println(json);
        Map<String, String> reflectMap =  objectMapper.readValue(json, new TypeReference<>(){} );
        System.out.println(reflectMap);
    }

}
