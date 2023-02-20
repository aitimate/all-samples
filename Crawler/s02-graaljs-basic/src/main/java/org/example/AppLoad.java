package org.example;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import java.io.File;
import java.io.IOException;

/**
 * Hello world!
 */
public class AppLoad {
    public static void main(String[] args) throws IOException {
        Context ctx = Context.create("js");
        File calculatorJS = new File(AppLoad.class.getClassLoader().getResource("main.js").getFile());
        ctx.eval(Source.newBuilder("js", calculatorJS).build());
        String text = ctx.getBindings("js").getMember("text").toString();
        System.out.println(text);
    }
}
