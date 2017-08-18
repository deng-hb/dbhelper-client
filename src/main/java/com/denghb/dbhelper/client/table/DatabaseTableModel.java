package com.denghb.dbhelper.client.table;

import com.denghb.dbhelper.generate.domain.DatabaseInfo;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class DatabaseTableModel extends AbstractTableModel {

    String[] n = {"Table", "Comment", "Check"};

    private Vector<DatabaseInfo> data;

    public DatabaseTableModel(Vector<DatabaseInfo> data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return n.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        DatabaseInfo info = data.get(rowIndex);
        if (0 == columnIndex) {
            return info.getTableName();
        } else if (1 == columnIndex) {
            return info.getTableComment();
        } else if (2 == columnIndex) {
            return info.isChecked();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return n[column];
    }


    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 2;// 复选框可编辑
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        System.out.println("setValueAt" + aValue);
        if (columnIndex == 2) {
            data.get(rowIndex).setChecked((Boolean) aValue);
        }

        fireTableCellUpdated(rowIndex, columnIndex);
    }
}
