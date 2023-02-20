package com.nil1one.s03remarksplugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.psi.PsiFile;

/**
 * 右键菜单项
 * */
public class RightClickAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // 读取选中文本
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        SelectionModel model = editor.getSelectionModel();
        MemoryData.SELECTED_TEXT = model.getSelectedText();
        // 读取文件名
        PsiFile psiFile = e.getRequiredData(CommonDataKeys.PSI_FILE);
        MemoryData.FILE_NAME = psiFile.getName();
        // 显示对话框
        new RemarksDialog().show();
    }
}
