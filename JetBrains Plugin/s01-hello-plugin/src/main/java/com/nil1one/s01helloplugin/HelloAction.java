package com.nil1one.s01helloplugin;

import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.MessageType;

public class HelloAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        NotificationGroup notificationGroup = new NotificationGroup("test-hello-plugin-id", NotificationDisplayType.BALLOON, true);
        Notifications.Bus.notify(notificationGroup.createNotification("Hello, plugin!", MessageType.INFO));
    }
}
