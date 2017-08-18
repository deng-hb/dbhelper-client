package com.denghb.dbhelper.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class TableJFrame2 extends JFrame {

    JButton buttonAlt = new JButton("修改");
	
	JButton buttonDel = new JButton("删除");
//	/表格方法使用
	public TableJFrame2() {
		init();

		this.setTitle("表格的例子2");
		this.setSize(new Dimension(400, 450));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private void init() {	
		
		Vector<String> colHeader = new Vector<String>();
		colHeader.add("编号");
		colHeader.add("名字");
		colHeader.add("性别");
		colHeader.add("日期");
		
		Vector<Vector<String>> dataVec = new Vector<Vector<String>>();
		Vector<String> row1 = new Vector<String>();
		row1.add("0001");
		row1.add("旺财");
		row1.add("男");
		row1.add(new Date().toString());
		Vector<String> row2 = new Vector<String>();
		row2.add("0002");
		row2.add("小强");
		row2.add("男");
		row2.add(new Date().toString());
		Vector<String> row3 = new Vector<String>();
		row3.add("0003");
		row3.add("韦小宝");
		row3.add("女");
		row3.add(new Date().toString());
		Vector<String> row4 = new Vector<String>();
		row4.add("0004");
		row4.add("零零七");
		row4.add("男");
		row4.add(new Date().toString());
		
		dataVec.add(row1);
		dataVec.add(row2);
		dataVec.add(row3);
		for (int i = 0;i < 100;i++) {

			dataVec.add(row4);
		}
		
		final JTable table = new JTable(dataVec,colHeader){
			//1单元格不可以编辑
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return true;
			}
		};
		
		//2设置表头行高
		table.getTableHeader().setPreferredSize(new Dimension(0,30));
		//3设置表内容行高
		table.setRowHeight(25);
		//4设置单选模式
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//5设置单元格不可拖动
		table.getTableHeader().setReorderingAllowed(false);
		//6设置不可改变列宽
		table.getTableHeader().setResizingAllowed(false);
		
		//7设置列宽
		table.getColumnModel().getColumn(0).setPreferredWidth(45);
		table.getColumnModel().getColumn(1).setPreferredWidth(55);
		table.getColumnModel().getColumn(2).setPreferredWidth(40);
		table.getColumnModel().getColumn(3).setPreferredWidth(305);
//		table.getColumnModel().getColumn(4).setPreferredWidth(305);//注意索引越界
		int rowIndex = table.getSelectedRow();
		System.out.println("suoyin:"+rowIndex);
		//监听事件
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){

			public void valueChanged(ListSelectionEvent e) {
				
				if(e.getValueIsAdjusting()){//连续操作
					int rowIndex = table.getSelectedRow();
					if(rowIndex!=-1){
						System.out.println("表格行被选中"+rowIndex);
						buttonAlt.setEnabled(true);
						buttonDel.setEnabled(true);
					}					
				}
				
			}});
		
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);
		this.add(scrollPane);
		
		buttonDel.setEnabled(false);
		buttonAlt.setEnabled(false);
		
		JPanel panel = new JPanel();
		panel.add(buttonAlt);
		panel.add(buttonDel);
		this.add(panel,BorderLayout.SOUTH);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new TableJFrame2();

	}

}

