package com.nil1one.s03remarksplugin.template;

import com.intellij.ide.fileTemplates.impl.UrlUtil;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.HashMap;
import java.util.Objects;


/**
 * 3- 实现类 (模板模式)
 * 说明: 模板模式的最终实现类越多，被抽离的代码逻辑的复用收益越高
 */
public class DefaultFreeMarkerProcessor extends AbstractFreeMarkerProcessor {
    public DefaultFreeMarkerProcessor(TemplateData templateData) {
        super(templateData);
    }

    @Override
    protected Object getModel() {
        HashMap<String, TemplateData> model = new HashMap<>();
        model.put("model", templateData);
        return model;
    }

    @Override
    protected Template getTemplate() throws IOException {
        String v1ftl = UrlUtil.loadText(Objects.requireNonNull(DefaultFreeMarkerProcessor.class.getResource("/template/v1.ftl")));
        StringTemplateLoader templateLoader = new StringTemplateLoader();
        templateLoader.putTemplate("v1ftl", v1ftl);

        Configuration configuration = new Configuration(Configuration.VERSION_2_3_29);
        configuration.setTemplateLoader(templateLoader);
        return configuration.getTemplate("v1ftl");
    }

    @Override
    protected Writer getWriter() throws FileNotFoundException {
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(templateData.getPath()));
        return new BufferedWriter(new OutputStreamWriter(stream));
    }
}
