package com.nil1one.s02textdialogplugin;

import com.intellij.openapi.ui.DialogWrapper;

import javax.swing.*;
import java.awt.*;

/**
 * 弹窗标题 弹窗中央区域 弹窗脚区域
 * */
public class MyTextDialog extends DialogWrapper {
    private JLabel jLabel;

    public MyTextDialog() {
        super(true);
        init();
        setTitle("一碗毒鸡汤");
    }

    @Override
    protected JComponent createCenterPanel() {
        JPanel jPanel = new JPanel(new BorderLayout());
        jLabel = new JLabel(MyUtil.getText());
        jLabel.setPreferredSize(new Dimension(800, 600));
        jPanel.add(jLabel, BorderLayout.CENTER);
        return jPanel;
    }

    @Override
    protected JComponent createSouthPanel() {
        JPanel jPanel = new JPanel(new FlowLayout());
        JButton jButton = new JButton("next one");
        jButton.addActionListener(e -> {
            jLabel.setText(MyUtil.getText());
        });
        jPanel.add(jButton);
        return jPanel;
    }
}
