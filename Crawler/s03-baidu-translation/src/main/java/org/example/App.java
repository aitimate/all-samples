package org.example;

import okhttp3.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class App {

    public static void main(String[] args) {
        AutoCookieJar cookieJar = new AutoCookieJar();
        String index = "https://fanyi.baidu.com";
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .addInterceptor((chain) -> {
                    Request.Builder builder = chain.request().newBuilder();
                    builder.addHeader("Referer", index);
                    builder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36");
                    return chain.proceed(builder.build());
                })
                .addInterceptor(new HttpLogInterceptor(cookieJar))
                .build();
        try {
            Response response1 = client.newCall(new Request.Builder().url(index).build()).execute();
            cookieJar.store = new ConcurrentHashMap<>();
            Cookie cookie = new Cookie.Builder().domain("baidu.com").name("BAIDUID").value("32975EB7756F7DD376B9E6AA77A5AA4B:FG=1").build();
            cookieJar.store.put(index,new ArrayList<>(Arrays.asList(cookie)));
            RequestBody formBody = new FormBody.Builder()
                    .add("from", "zh")
                    .add("to", "en")
                    .add("query", "测试")
                    .add("transtype", "translang")
                    .add("simple_means_flag", "3")
                    .add("sign", "706553.926920")
                    .add("token", "fa55e1db9f704b3870f90029591a4ad4")
                    .add("domain", "common")
                    .build();
            Response response = client.newCall(new Request.Builder()
                    .url("https://fanyi.baidu.com/v2transapi?from=zh&to=en")
                    .post(formBody)
                    .build()).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
