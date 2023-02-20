package com.nil1one.s02textdialogplugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 网络文本的工具类
 * */
public class MyUtil {
    public static AtomicInteger COUNT = new AtomicInteger(1);
    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     {
         "status": 1,
         "msg": "毒汤数据获取成功！",
         "data": [
             {
             "id": 39,
             "content": "你并没那么缺少安全感，因为没钱是最安全的。"
             }
         ],
         "timestamp": 1671814736156
     }
     */
    public static String getText() {
        Request request = new Request.Builder().url("https://api.nextrt.com/V1/Dutang").build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            List list = (List) mapper.readValue(response.body().string(), Map.class).get("data");
            String content = ((Map<String, String>) list.get(0)).get("content");
            System.out.println(content);
            return content;
        } catch (IOException e) {
        }
        return "数据拉取异常";
    }
}
