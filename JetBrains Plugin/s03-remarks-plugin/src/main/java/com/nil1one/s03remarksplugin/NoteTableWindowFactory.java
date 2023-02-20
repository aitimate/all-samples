package com.nil1one.s03remarksplugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

/**
 * ToolWindowFactory 扩展点
 * 说明: 自定义的窗体必须用 IDEA内容窗(ContentFactory) 承载，然后在 工具窗(ToolWindow) 注册，否则无法显示
 * */
public class NoteTableWindowFactory implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        // 创建自定义工具窗体
        NoteTableWindowBody noteListWindow = new NoteTableWindowBody(project, toolWindow);
        // 获取内容区工厂对象 ~ ApplicationManager.getApplication().getService(ContentFactory.class);
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        // 工厂构建内容区组件(最终形态)
        Content content = contentFactory.createContent(noteListWindow.getNoteTablePanel(), "Page1", false);
        // 注册最终内容区组件
        toolWindow.getContentManager().addContent(content);
    }
}
