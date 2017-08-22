package com.denghb.dbhelper.client;

import com.denghb.dbhelper.domain.ConnectionInfo;
import com.denghb.dbhelper.utils.ConfigUtils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * host      username   password
 * database  port       connection
 */
public class ControlPanel extends Panel {

    public interface ControlConnectionHandler {
        boolean execute(ConnectionInfo info);
    }

    public interface ControlSearchHandler {
        void search(String string);
    }

    public interface ControlExecuteHandler {
        void execute(String packageName, String targetDir);
    }

    private static int _X = 30;
    private static int _WIDTH = 230;
    private static int _HEIGHT = 28;

    private ControlConnectionHandler connectionHandler;
    private ControlSearchHandler searchHandler;
    private ControlExecuteHandler executeHandler;

    public ControlPanel() {

        this.setLayout(null);
        JLabel label = new JLabel("MySQL Config:");
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setBounds(_X, 0, _WIDTH, _HEIGHT);
        this.add(label);

        // 主机名
        Label hostLabel = new Label("Host:");
        hostLabel.setBounds(_X, getBottom(label), _WIDTH, _HEIGHT - 8);
        this.add(hostLabel);

        final TextField hostField = new TextField();
        hostField.setBounds(_X, getBottom(hostLabel), _WIDTH, _HEIGHT);
        this.add(hostField);

        // 用户名
        Label usernameLabel = new Label("Username:");
        usernameLabel.setBounds(getRight(hostLabel) + _X, getBottom(label), _WIDTH, _HEIGHT - 8);
        this.add(usernameLabel);

        final TextField usernameField = new TextField();
        usernameField.setBounds(usernameLabel.getX(), getBottom(usernameLabel), _WIDTH, _HEIGHT);
        this.add(usernameField);

        // 密码
        Label passwordLabel = new Label("Password:");
        passwordLabel.setBounds(getRight(usernameLabel) + _X, getBottom(label), _WIDTH, _HEIGHT - 8);
        this.add(passwordLabel);

        final JPasswordField passwordField = new JPasswordField();
        Border line = BorderFactory.createLineBorder(Color.DARK_GRAY);
        Border empty = new EmptyBorder(0, 5, 0, 0);
        CompoundBorder border = new CompoundBorder(line, empty);
        passwordField.setBorder(border);
        passwordField.setBounds(passwordLabel.getX(), getBottom(passwordLabel), _WIDTH, _HEIGHT);
        this.add(passwordField);

        // 数据库
        Label databaseLabel = new Label("Database:");
        databaseLabel.setBounds(_X, getBottom(hostField), _WIDTH, _HEIGHT - 8);
        this.add(databaseLabel);

        final TextField databaseField = new TextField();
        databaseField.setBounds(_X, getBottom(databaseLabel), _WIDTH, _HEIGHT);
        this.add(databaseField);

        // 端口
        Label portLabel = new Label("Port:");
        portLabel.setBounds(getRight(databaseLabel) + _X, getBottom(hostField), _WIDTH, _HEIGHT - 8);
        this.add(portLabel);

        final TextField portField = new TextField();
        portField.setBounds(portLabel.getX(), getBottom(portLabel), _WIDTH, _HEIGHT);
        this.add(portField);

        TextField connField = new TextField();
        connField.setEnabled(false);
        connField.setBounds(getRight(portField) + _X, portField.getY(), _WIDTH, _HEIGHT);
        this.add(connField);

        JButton connButton = new JButton("1. Start Connection");
        connButton.setBorderPainted(false);
        connButton.setBounds(0, 0, _WIDTH, _HEIGHT);
        connButton.setBackground(Color.lightGray);
        connButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                hostField.setBorder(javax.swing.BorderFactory.createLineBorder(Color.GRAY));

                // host
                String host = hostField.getText();
                if (isEmpty(host)) {
                    showError(hostField);
                    return;
                }
                // username
                String username = usernameField.getText();
                if (isEmpty(username)) {
                    showError(usernameField);
                    return;
                }

                // password
                String password = passwordField.getText();

                // database
                String database = databaseField.getText();
                if (isEmpty(database)) {
                    showError(databaseField);
                    return;
                }

                // port
                String port = portField.getText();
                if (isEmpty(port)) {
                    port = "3306";
                }


                if (null != connectionHandler) {
                    ConnectionInfo info = new ConnectionInfo();
                    info.setDatabase(database);
                    info.setHost(host);
                    info.setPassword(password);
                    info.setPort(port);
                    info.setUsername(username);

                    connectionHandler.execute(info);
                }


                // 保存配置
                ConfigUtils.setValue("host", host);
                ConfigUtils.setValue("username", username);
                ConfigUtils.setValue("password", password);

                ConfigUtils.setValue("database", database);
                ConfigUtils.setValue("port", port);
            }
        });
        connField.add(connButton);


        // package
        Label packageLabel = new Label("Package:");
        packageLabel.setBounds(_X, getBottom(databaseField), _WIDTH, _HEIGHT - 8);
        this.add(packageLabel);

        final TextField packageField = new TextField();
        packageField.setBounds(_X, getBottom(packageLabel), _WIDTH, _HEIGHT);
        this.add(packageField);

        // targetDir
        final Label targetDirLabel = new Label("Target Directory:");
        targetDirLabel.setBounds(getRight(packageLabel) + _X, getBottom(databaseField), _WIDTH, _HEIGHT - 8);
        this.add(targetDirLabel);

        final TextField targetField = new TextField();
        targetField.setBounds(targetDirLabel.getX(), getBottom(targetDirLabel), _WIDTH * 2 + _X, _HEIGHT);
        this.add(targetField);

        JButton chooseButton = new JButton("Choose");
        chooseButton.setBorderPainted(false);
        chooseButton.setHorizontalAlignment(SwingConstants.RIGHT);
        chooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                jfc.showDialog(new JLabel(), "Choose");
                File file = jfc.getSelectedFile();
                if (null != file) {
                    if (file.isDirectory()) {
                        targetField.setText(file.getAbsolutePath());
                    } else {
                        targetField.setText(file.getParent());
                    }
                } else {
                    showError(targetField);
                }
            }
        });
        chooseButton.setBackground(Color.white);
        chooseButton.setBounds(targetField.getWidth() - 120, 0, 120, _HEIGHT);
        targetField.add(chooseButton);

        // _______________________________________

        // Search
        Label searchLabel = new Label("Search:");
        searchLabel.setBounds(_X, getBottom(packageField) + 20, _WIDTH, _HEIGHT - 8);
        this.add(searchLabel);
        final TextField searchField = new TextField();
//        searchField.setPlaceholder(" Keyword");
        searchField.setBounds(_X, getBottom(searchLabel), _WIDTH, _HEIGHT);
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                                                          public void changedUpdate(DocumentEvent e) {//这是更改操作的处理
                                                              String s = searchField.getText().trim();//trim()方法用于去掉你可能误输入的空格号
                                                              if (null != searchHandler) {
                                                                  searchHandler.search(s);
                                                              }
                                                          }

                                                          public void insertUpdate(DocumentEvent e) {//这是插入操作的处理
                                                              String s = searchField.getText().trim();
                                                              if (null != searchHandler) {
                                                                  searchHandler.search(s);
                                                              }
                                                          }

                                                          public void removeUpdate(DocumentEvent e) {//这是删除操作的处理
                                                              String s = searchField.getText().trim();
                                                              if (null != searchHandler) {
                                                                  searchHandler.search(s);
                                                              }
                                                          }
                                                      }
        );

        this.add(searchField);


        TextField executeField = new TextField();
        executeField.setEnabled(false);
        executeField.setBounds(connField.getX(), searchField.getY(), _WIDTH, _HEIGHT);
        this.add(executeField);

        final JButton executeButton = new JButton("2. Execute");
        executeButton.setBorderPainted(false);
        executeButton.setBounds(0, 0, _WIDTH, _HEIGHT);
        executeButton.setBackground(Color.lightGray);
        executeField.add(executeButton);
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String packageName = packageField.getText();
                if (isEmpty(packageName)) {
                    showError(packageField);
                    return;
                }

                String targetDir = targetField.getText();
                if (isEmpty(targetDir)) {
                    showError(targetField);
                    return;
                }

                ConfigUtils.setValue("packageName", packageName);
                ConfigUtils.setValue("targetDir", targetDir);

                if (null != executeHandler) {
                    executeHandler.execute(packageName, targetDir);
                }

            }
        });
        // 初始化数据
        hostField.setText(ConfigUtils.getValue("host"));
        usernameField.setText(ConfigUtils.getValue("username"));
        passwordField.setText(ConfigUtils.getValue("password"));

        databaseField.setText(ConfigUtils.getValue("database"));
        portField.setText(ConfigUtils.getValue("port"));

        packageField.setText(ConfigUtils.getValue("packageName"));
        targetField.setText(ConfigUtils.getValue("targetDir"));

    }

    private void showError(JComponent component) {

        Border line = BorderFactory.createLineBorder(Color.RED);
        Border empty = new EmptyBorder(0, 5, 0, 0);
        CompoundBorder border = new CompoundBorder(line, empty);
        component.setBorder(border);
    }

    private boolean isEmpty(String text) {
        return null == text || 0 == text.trim().length();
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


    public ControlConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }

    public void setConnectionHandler(ControlConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    public ControlSearchHandler getSearchHandler() {
        return searchHandler;
    }

    public void setSearchHandler(ControlSearchHandler searchHandler) {
        this.searchHandler = searchHandler;
    }

    public ControlExecuteHandler getExecuteHandler() {
        return executeHandler;
    }

    public void setExecuteHandler(ControlExecuteHandler executeHandler) {
        this.executeHandler = executeHandler;
    }

    // 自定义
    private class TextField extends JTextField {
        public TextField() {
            Border line = BorderFactory.createLineBorder(Color.GRAY);
            Border empty = new EmptyBorder(0, 5, 0, 0);
            CompoundBorder border = new CompoundBorder(line, empty);
            this.setBorder(border);
        }


    }

    private class Label extends JLabel {
        public Label(String title) {
            super(title);
            this.setVerticalAlignment(SwingConstants.BOTTOM);
            this.setHorizontalAlignment(SwingConstants.LEFT);
        }
    }
}
