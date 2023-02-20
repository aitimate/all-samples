package com.nil1one.s03remarksplugin.template;

import freemarker.template.TemplateException;

import java.io.IOException;

/**
 * 1- 接口，仅需要实现固定的方法 (模板模式)
 */
public interface Processor {
    void process() throws TemplateException, IOException;
}
