package com.denghb.dbhepler.client;

import com.denghb.dbhepler.generate.Generate;
import com.denghb.dbhepler.utils.ConfigUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by denghb on 2017/2/19.
 */
public class DbHelperClient extends JFrame {

    private JTextField targetField;
    private JLabel msgLabel;

    private static final int _X = 10;
    private static final int _LABEL_WIDTH = 100;
    private static final int _LABEL_HEIGHT = 20;

    private static final int _FIELD_WIDTH = 250;
    private static final int _V_GAP = 10;

    public DbHelperClient() {
        this.setTitle("Generate Entity from MySQL!!!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 关闭按钮的动作为退出窗口
        this.setSize(400, 320);// 大小
        this.setResizable(false);// 禁止缩放
        this.setLocationRelativeTo(null);// 屏幕居中显示，需要先设置frame大小

        initView();

        this.setVisible(true);// 显示
    }

    private void initView() {
        JPanel panel = new JPanel();
        panel.setLayout(null);


        JLabel label = new JLabel("MySQL Config:");
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setBounds(_X, 0, _LABEL_WIDTH, _LABEL_HEIGHT);
        panel.add(label);

        // 主机名
        final JLabel hostLabel = new JLabel("Host:");
        hostLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        hostLabel.setBounds(_X, 30, _LABEL_WIDTH, _LABEL_HEIGHT);
        panel.add(hostLabel);

        final JTextField hostField = new JTextField();
        hostField.setBounds(getRight(hostLabel), 30, _FIELD_WIDTH, _LABEL_HEIGHT);
        panel.add(hostField);

        // 用户名
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        usernameLabel.setBounds(_X, getBottom(hostLabel) + _V_GAP, _LABEL_WIDTH, _LABEL_HEIGHT);
        panel.add(usernameLabel);

        final JTextField usernameField = new JTextField();
        usernameField.setBounds(getRight(usernameLabel), getBottom(hostField) + _V_GAP, _FIELD_WIDTH, _LABEL_HEIGHT);
        panel.add(usernameField);

        // 密码
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        passwordLabel.setBounds(_X, getBottom(usernameLabel) + _V_GAP, _LABEL_WIDTH, _LABEL_HEIGHT);
        panel.add(passwordLabel);

        final JTextField passwordField = new JTextField();
        passwordField.setBounds(getRight(passwordLabel), getBottom(usernameField) + _V_GAP, _FIELD_WIDTH, _LABEL_HEIGHT);
        panel.add(passwordField);

        // 数据库
        JLabel databaseLabel = new JLabel("Database:");
        databaseLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        databaseLabel.setBounds(_X, getBottom(passwordLabel) + _V_GAP, _LABEL_WIDTH, _LABEL_HEIGHT);
        panel.add(databaseLabel);

        final JTextField databaseField = new JTextField();
        databaseField.setBounds(getRight(databaseLabel), getBottom(passwordField) + _V_GAP, _FIELD_WIDTH, _LABEL_HEIGHT);
        panel.add(databaseField);

        // 端口
        JLabel portLabel = new JLabel("Port:");
        portLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        portLabel.setBounds(_X, getBottom(databaseLabel) + _V_GAP, _LABEL_WIDTH, _LABEL_HEIGHT);
        panel.add(portLabel);

        final JTextField portField = new JTextField();
        portField.setBounds(getRight(portLabel), getBottom(databaseField) + _V_GAP, _FIELD_WIDTH, _LABEL_HEIGHT);
        panel.add(portField);

        // -------------------
        JButton chooseButton = new JButton("Target Choose");
        chooseButton.setBounds(_X, getBottom(portLabel) + _V_GAP, _LABEL_WIDTH, _LABEL_HEIGHT);
        chooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                jfc.showDialog(new JLabel(), "Choose");
                File file = jfc.getSelectedFile();
                if (null != file && file.isDirectory()) {
                    targetField.setText(file.getAbsolutePath());
                } else {
                    msgLabel.setText("Please Choose Directory..");
                }
            }
        });
        panel.add(chooseButton);

        targetField = new JTextField();
        targetField.setEnabled(false);
        targetField.setBounds(getRight(chooseButton), getBottom(portLabel) + _V_GAP, _FIELD_WIDTH, _LABEL_HEIGHT);
        panel.add(targetField);

        // 包名
        JLabel packageLabel = new JLabel("Package:");
        packageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        packageLabel.setBounds(_X, getBottom(chooseButton) + _V_GAP, _LABEL_WIDTH, _LABEL_HEIGHT);
        panel.add(packageLabel);

        final JTextField packageField = new JTextField();
        packageField.setBounds(getRight(packageLabel), getBottom(targetField) + _V_GAP, _FIELD_WIDTH, _LABEL_HEIGHT);
        panel.add(packageField);

        // 消息提示
        msgLabel = new JLabel();
        msgLabel.setBounds(getRight(chooseButton), getBottom(packageField) + _V_GAP, _FIELD_WIDTH, 30);
        panel.add(msgLabel);


        JButton executeButton = new JButton("Execute");
        executeButton.setBounds(getRight(targetField) - 100, getBottom(packageField) + _V_GAP, 100, 30);
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // host
                String host = hostField.getText();

                // username
                String username = usernameField.getText();

                // password
                String password = passwordField.getText();

                // database
                String database = databaseField.getText();

                // port
                String port = portField.getText();

                String packageName = packageField.getText();

                String targetDir = targetField.getText();

                Generate.start(host, username, password, database, port, packageName, targetDir);

                msgLabel.setText("Done!!");

                // 保存配置
                ConfigUtils.setValue("host",host);
                ConfigUtils.setValue("username",username);
                ConfigUtils.setValue("password",password);

                ConfigUtils.setValue("database",database);
                ConfigUtils.setValue("port",port);
                ConfigUtils.setValue("packageName",packageName);
                ConfigUtils.setValue("targetDir",targetDir);
            }
        });
        panel.add(executeButton);


        this.add(panel, BorderLayout.CENTER);

        // 初始化数据
        hostField.setText(ConfigUtils.getValue("host"));
        usernameField.setText(ConfigUtils.getValue("username"));
        passwordField.setText(ConfigUtils.getValue("password"));

        databaseField.setText(ConfigUtils.getValue("database"));
        portField.setText(ConfigUtils.getValue("port"));
        packageField.setText(ConfigUtils.getValue("packageName"));
        targetField.setText(ConfigUtils.getValue("targetDir"));

    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    new DbHelperClient();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * 获取控件最右端
     *
     * @param component
     * @return
     */
    private static int getRight(JComponent component) {
        return component.getX() + component.getWidth();
    }

    /**
     * 获取控件最下端
     *
     * @param component
     * @return
     */
    private static int getBottom(JComponent component) {
        return component.getY() + component.getHeight();
    }
}
