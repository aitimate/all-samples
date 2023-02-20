package org.example;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Context ctx = Context.create("js");
        ctx.eval("js","function eval(a, b){ return a + b; }");
        Value eval  = ctx.getBindings("js").getMember("eval");
        System.out.println(eval.execute("Hello ","World!"));
        // 1-开启js访问java
        // 2-从文件引入js Source
    }
}
