package com.denghb.dbhelper.client.table;

import com.denghb.dbhelper.generate.domain.DatabaseInfo;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Vector;

public class TableView extends JScrollPane {

    JTable table = null;

    public TableView(TableModel tableModel) {
        table = new JTable(tableModel);
        //设置表头行高
        table.getTableHeader().setPreferredSize(new Dimension(0, 20));
        //设置表内容行高
        table.setRowHeight(25);
        //设置单选模式
        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        //设置单元格不可拖动
        table.getTableHeader().setReorderingAllowed(false);
        //设置不可改变列宽
//        table.getTableHeader().setResizingAllowed(false);

        //设置列宽
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(80);
        int rowIndex = table.getSelectedRow();
        //监听事件

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {

                if (e.getValueIsAdjusting()) {//连续操作
                    int rowIndex = table.getSelectedRow();
                    if (rowIndex != -1) {
                        System.out.println("表格行被选中" + rowIndex);
                    }
                }

            }
        });
        JCheckBox jc1 = new JCheckBox();
        table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(jc1));
        this.setViewportView(table);
    }

    // 加载表信息
    public void reloadData() {
        table.updateUI();
    }

    // 搜索表格信息
    public void search(String str) {

    }

    public Vector<DatabaseInfo> getDatabaseInfo() {

        return null;
    }

}
