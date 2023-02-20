package org.example;

import okhttp3.*;
import okio.Buffer;
import okio.BufferedSource;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class HttpLogInterceptor implements Interceptor {
    private final CookieJar cookieJar;

    public HttpLogInterceptor(CookieJar cookieJar) {
        this.cookieJar = cookieJar;
    }

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {

        // 读取请求体 -> reqBody
        Request request = chain.request();
        RequestBody requestBody = request.body();
        String reqBody = "";
        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = null;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) charset = contentType.charset();
            if (charset == null) charset = StandardCharsets.UTF_8;
            reqBody = buffer.readString(charset);
        }
        // 读取请求头
        StringBuilder reqHeaders = new StringBuilder();
        request.headers().forEach((p) -> reqHeaders.append("\n\t<").append(p.getFirst()).append(">: ").append(p.getSecond()));
        if (cookieJar != null) {
            List<Cookie> cookies = cookieJar.loadForRequest(request.url());
            if (!cookies.isEmpty()){
                reqHeaders.append("\n\t<Cookie>: ").append(cookieHeader(cookies));
            }
        }

        long startTime = System.nanoTime();
        Response response = chain.proceed(request);
        long totalTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);

        // 读取响应体 -> respBody
        ResponseBody responseBody = response.body();
        String respBody = "";
        boolean isHTML = false;
        if (responseBody != null) {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.getBuffer();

            Charset charset = null;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                if (contentType.toString().contains("text/html")) isHTML = true;
                charset = contentType.charset();
            }
            if (isHTML) {
                respBody = "<HTML>";
            } else {
                if (charset == null) charset = StandardCharsets.UTF_8;
                respBody = buffer.clone().readString(charset);
            }
        }
        // 读取响应头
        StringBuilder respHeaders = new StringBuilder();
        response.headers().forEach((p) -> respHeaders.append("\n\t<").append(p.getFirst()).append(">: ").append(p.getSecond()));

        String info = """
                请求地址: %s [%s] [%s] %sms
                请求头: %s
                请求体: %s
                响应头: %s
                响应体: %s
                """.formatted(request.url(), request.method(), response.code(), totalTime, reqHeaders, reqBody, respHeaders, respBody);
        System.out.println(info);
        return response;
    }

    private String cookieHeader(List<Cookie> cookies) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cookies.size(); i++) {
            if (i > 0) sb.append("; ");
            sb.append(cookies.get(i).name()).append('=').append(cookies.get(i).value());
        }
        return sb.toString();
    }
}
