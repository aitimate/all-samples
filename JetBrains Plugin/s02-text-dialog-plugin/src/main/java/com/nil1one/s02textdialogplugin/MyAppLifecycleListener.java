package com.nil1one.s02textdialogplugin;

import com.intellij.ide.AppLifecycleListener;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 应用的生命周期钩子: 监听器 AppLifecycleListener
 * */
public class MyAppLifecycleListener implements AppLifecycleListener {
    public static Boolean hasWelcomeScreenDisplayed = false;

    @Override
    public void appFrameCreated(@NotNull List<String> commandLineArgs) {
        // System.out.println(System.getProperty("file.encoding"));
        System.out.println(MyUtil.COUNT.getAndIncrement() + "::appFrameCreated::在应用显示之前回调(不可绘图)");
    }

    @Override
    public void appStarted() {
        System.out.println(MyUtil.COUNT.getAndIncrement() + "::appStarted::在应用启动之后回调(不可绘图)");
    }

    @Override
    public void welcomeScreenDisplayed() {
        hasWelcomeScreenDisplayed = true;
        System.out.println(MyUtil.COUNT.getAndIncrement() + "::welcomeScreenDisplayed::在欢迎界面显示后回调(该函数在直接打开项目时不执行)");
        MyTextDialog myTextDialog = new MyTextDialog();
        myTextDialog.show();
    }

    @Override
    public void appClosing() {
        System.out.println(MyUtil.COUNT.getAndIncrement() + "::appClosing::在应用等待确认关闭/自动保存配置之前回调");
    }

    @Override
    public void appWillBeClosed(boolean isRestart) {
        System.out.println(MyUtil.COUNT.getAndIncrement() + "::appWillBeClosed::在应用已经确认关闭之后回调(最终时刻)");
    }
}
