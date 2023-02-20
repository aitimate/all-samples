package com.nil1one.s03remarksplugin;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageDialogBuilder;
import com.intellij.openapi.ui.OkCancelDialogBuilder;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.nil1one.s03remarksplugin.template.DefaultFreeMarkerProcessor;
import com.nil1one.s03remarksplugin.template.TemplateData;
import freemarker.template.TemplateException;
import org.jsoup.helper.StringUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * 自定义工具栏窗体(界面图形化生成)
 * */
public class NoteTableWindowBody {
    private JPanel noteTablePanel;
    private JTextField fileNameField;
    private JTable noteTable;
    private JButton genBtn;
    private JButton clearBtn;
    private JButton closeBtn;

    private static final String[] tableHeader = new String[]{"标题", "备注", "源文件", "文本段"};
    public static DefaultTableModel tableModel = new DefaultTableModel(null, tableHeader);

    /**
     * 要求: 传递 NoteListWindowFactory窗口扩展点 参数
     */
    public NoteTableWindowBody(Project project, ToolWindow toolWindow) {
        // 表格数据显示
        noteTable.setModel(tableModel);
        noteTable.setEnabled(true);

        genBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 校验是否有输出文件名
                String fileName = fileNameField.getText();
                if (StringUtil.isBlank(fileName)) {
                    OkCancelDialogBuilder dialogBuilder = MessageDialogBuilder.okCancel("提示", "输出文件名还未输入！\n是否使用默认名 \"default\"?");
                    boolean yes = dialogBuilder.ask(project);
                    if (!yes) {
                        return;
                    }
                    fileName = "default.md";
                }
                // 弹出文件目录选择器
                VirtualFile virtualFile = FileChooser.chooseFile(FileChooserDescriptorFactory.createSingleFolderDescriptor(), project, project.getProjectFile());
                if (virtualFile != null) {
                    // 模板生成文件
                    String output = virtualFile.getPath() + File.separator + fileName;
                    TemplateData templateData = new TemplateData(output, fileName, MemoryData.notes);
                    try {
                        new DefaultFreeMarkerProcessor(templateData).process();
                    } catch (TemplateException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
        });
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 清空内存列表
                MemoryData.notes.clear();
                // 清空面板表格
                tableModel.setDataVector(null, tableHeader);
            }
        });
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 隐藏工具窗
                toolWindow.hide();
            }
        });
    }

    public JPanel getNoteTablePanel() {
        return noteTablePanel;
    }
}
