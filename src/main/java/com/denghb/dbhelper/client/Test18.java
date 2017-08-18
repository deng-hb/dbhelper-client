package com.denghb.dbhelper.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 * 使用复选框作为编辑器来编辑表格中的单元格
 * 
 * @author burns
 * 
 */
public class Test18 {

    public Test18() {
        JFrame f = new JFrame();
        MyTable18 mt = new MyTable18();
        /**
         * 由于MyTable类继承了AbstractTableModel，并且实现了getColmunCount()、getRowCount()、
         * getValueAt()方法 由此可以通过通过MyTable18来产生TableModel的实体
         */
        JTable t = new JTable(mt);
        JCheckBox jc1 = new JCheckBox();
        t.getColumnModel().getColumn(4)
                .setCellEditor(new DefaultCellEditor(jc1));
        t.setPreferredScrollableViewportSize(new Dimension(550, 30));
        JScrollPane s = new JScrollPane(t);
        f.getContentPane().add(s, BorderLayout.CENTER);
        f.setTitle("ColumnModelDd");
        f.pack();
        f.setVisible(true);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        new Test18();
    }
}

class MyTable18 extends AbstractTableModel {

    Object[][] p = {
            { "王鹏", new Integer(66), new Integer(32), new Integer(98), false },
            { "宋兵", new Integer(85), new Integer(69), new Integer(154), true }, };

    String[] n = { "姓名", "语文", "数学", "总分", "及格与否" };

    @Override
    public int getRowCount() {
        return p.length;
    }

    @Override
    public int getColumnCount() {
        return n.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return p[rowIndex][columnIndex];
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
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        p[rowIndex][columnIndex] = aValue;
        fireTableCellUpdated(rowIndex, columnIndex);
    }
}