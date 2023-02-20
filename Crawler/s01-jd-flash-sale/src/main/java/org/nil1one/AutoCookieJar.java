package org.nil1one;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AutoCookieJar implements CookieJar {

    public final Map<String, List<Cookie>> store = new ConcurrentHashMap<>();

    @NotNull
    public List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {
        List<Cookie> cookies = new ArrayList<>();
        store.forEach((k, v) -> cookies.addAll(v));
        return cookies;
    }

    public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list) {
        store.put(httpUrl.host(), list);
    }

    public String findByName(@NotNull String name) {
        for (String k : store.keySet()) {
            for (Cookie cookie : store.get(k)) {
                if (cookie.name().equals(name)) {
                    return cookie.value();
                }
            }
        }
        return "";
    }
}