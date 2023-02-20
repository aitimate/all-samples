package com.nil1one.s02textdialogplugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import org.jetbrains.annotations.NotNull;

/**
 * 项目的生命周期钩子: 监听器 ProjectManagerListener
 * */
public class MyProjectManagerListener implements ProjectManagerListener {
    @Override
    public void projectOpened(@NotNull Project project) {
        System.out.println(MyUtil.COUNT.getAndIncrement() + "::projectOpened::在项目启动时回调");
        if (!MyAppLifecycleListener.hasWelcomeScreenDisplayed){
            MyTextDialog myTextDialog = new MyTextDialog();
            myTextDialog.show();
        }
    }
}
