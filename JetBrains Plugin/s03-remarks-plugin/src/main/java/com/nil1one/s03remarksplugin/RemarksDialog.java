package com.nil1one.s03remarksplugin;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.MessageDialogBuilder;
import com.intellij.ui.EditorTextField;

import javax.swing.*;
import java.awt.*;

/**
 * 标题 文本输入框 保存按钮和事件
 * */
public class RemarksDialog extends DialogWrapper {
    EditorTextField remarks;
    EditorTextField title;

    public RemarksDialog() {
        super(true);
        init();
        setTitle("添加备注");
    }

    @Override
    protected JComponent createCenterPanel() {
        JPanel jPanel = new JPanel(new FlowLayout());
        jPanel.setPreferredSize(new Dimension(800, 300));
        title = new EditorTextField("填写标题");
        title.setPreferredSize(new Dimension(800, 100));
        remarks = new EditorTextField("填写备注");
        remarks.setPreferredSize(new Dimension(800, 150));

        jPanel.add(title);
        jPanel.add(remarks);
        return jPanel;
    }

    @Override
    protected JComponent createSouthPanel() {
        JPanel jPanel = new JPanel(new FlowLayout());
        JButton jButton = new JButton("保存");
        jButton.addActionListener(e -> {
            // 弹出yesNo确认框
            MessageDialogBuilder.YesNo yesNo = MessageDialogBuilder.yesNo("提示", "是否确定？");
            boolean yes = yesNo.guessWindowAndAsk();
            if (yes){
                MemoryData.Note note = new MemoryData.Note(title.getText(), remarks.getText());
                MemoryData.notes.add(note);
                NoteTableWindowBody.tableModel.addRow(note.toTableModel());
                System.out.println(note);
                // 关闭自身弹窗
                RemarksDialog.this.dispose();
            }
        });
        jPanel.add(jButton);
        return jPanel;
    }
}
