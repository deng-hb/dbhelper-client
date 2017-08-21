package com.denghb.dbhelper.client;

import com.denghb.dbhelper.client.table.DatabaseTableModel;
import com.denghb.dbhelper.client.table.TableView;
import com.denghb.dbhelper.domain.ConnectionInfo;
import com.denghb.dbhelper.generate.Generate;
import com.denghb.dbhelper.generate.GenerateException;
import com.denghb.dbhelper.generate.domain.DatabaseInfo;
import com.denghb.dbhelper.generate.utils.DatabaseTableInfoUtils;
import com.denghb.dbhelper.generate.utils.DbUtils;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by denghb on 2017/2/19.
 */
public class DbHelperClient extends JFrame {

    JPanel panel = new JPanel();

    List<DatabaseInfo> result = new ArrayList<DatabaseInfo>();
    Vector<DatabaseInfo> data = new Vector<DatabaseInfo>();
    DatabaseTableModel model = new DatabaseTableModel(data);
    TableView table = new TableView(model);

    ConnectionInfo connInfo;

    public DbHelperClient() {
        this.setTitle("Generate Entity from MySQL!!!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 关闭按钮的动作为退出窗口
        this.setSize(800, 600);// 大小
        this.setResizable(false);// 禁止缩放
        this.setLocationRelativeTo(null);// 屏幕居中显示，需要先设置frame大小

        panel.setLayout(null);
        this.add(panel, BorderLayout.CENTER);

        // 操作面板
        ControlPanel controlPanel = new ControlPanel();
        controlPanel.setBounds(0, 0, this.getWidth(), 280);
        controlPanel.setConnectionHandler(new ControlPanel.ControlConnectionHandler() {
            @Override
            public boolean execute(ConnectionInfo info) {
                connInfo = info;

                try {
                    DbUtils.init(connInfo);
                } catch (GenerateException e) {
                    e.printStackTrace();
                    return false;
                }
                result = DatabaseTableInfoUtils.load(DbUtils.getConnection(), connInfo.getDatabase());
                data.removeAllElements();
                for (DatabaseInfo dbInfo : result) {
                    data.add(dbInfo);
                }
                table.reloadData();

                return true;
            }
        });
        controlPanel.setSearchHandler(new ControlPanel.ControlSearchHandler() {
            @Override
            public void search(String string) {

                data.removeAllElements();
                for (DatabaseInfo info : result) {
                    if (info.getTableName().indexOf(string) > -1) {
                        data.add(info);
                    }
                }

                table.reloadData();
            }
        });
        controlPanel.setExecuteHandler(new ControlPanel.ControlExecuteHandler() {
            @Override
            public void execute(String packageName, String targetDir) {
                if (data.isEmpty()) {
                    System.out.println("data is null");
                    return;
                }


                for (DatabaseInfo info : data) {

                    try {
                        if (info.isChecked()) {
                            Generate.create(DbUtils.getConnection(), connInfo.getDatabase(), packageName, info, targetDir);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }

                }
                System.out.println("execute ok");
            }
        });
        panel.add(controlPanel);

        // 表格
        table.setBounds(30, 280, 740, 280);
        panel.add(table);

        this.setVisible(true);// 显示
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
}
