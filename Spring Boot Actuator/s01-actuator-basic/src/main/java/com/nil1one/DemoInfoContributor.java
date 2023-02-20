package com.nil1one;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

@Component
public class DemoInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        MediaType.ALL_VALUE
        MimeTypeUtils.ALL_VALUE
        MimeTypeUtils.APPLICATION_GRAPHQL.toString();
        builder.withDetail("自定义信息节点",
                Collections.singletonMap("key", "value"));
    }

}