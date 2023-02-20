package com.nil1one.s03remarksplugin.template;


import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;


/**
 * 2- 抽离重复的部分 (模板模式)
 */
public abstract class AbstractFreeMarkerProcessor implements Processor{

    protected TemplateData templateData;

    public AbstractFreeMarkerProcessor(TemplateData templateData){
        this.templateData = templateData;
    }

    protected abstract Object getModel();
    protected abstract Template getTemplate() throws IOException;
    protected abstract Writer getWriter() throws FileNotFoundException;

    /**
     * 抽离固定的处理流程 (模板模式)
     * */
    @Override
    public final void process() throws TemplateException, IOException {
        Template template = getTemplate();
        Object model = getModel();
        Writer writer = getWriter();
        template.process(model,writer);
    }
}
