package com.nil1one.s02textdialogplugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import org.jetbrains.annotations.NotNull;

/**
 * 项目的活动钩子(当项目启动完成并开始活动时回调) 扩展点: postStartupActivity
 * */
public class MyStartupActivity implements StartupActivity {
    @Override
    public void runActivity(@NotNull Project project) {
        System.out.println(MyUtil.COUNT.getAndIncrement() + "::runActivity::在项目启动完成时回调");
    }
}
