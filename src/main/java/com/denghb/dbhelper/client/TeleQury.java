package com.denghb.dbhelper.client;

import javax.swing.table.*;
import java.awt.*;
import javax.swing.*;

class TeleQury extends JFrame {
    Button b = new Button();
    TextField tf;
    Object[][] cells =
            {
                    {"", "", "", "", "", "", ""},
            };
    String[] columns = {"用户名", "手机号", "余额", "市话费", "长途费", "漫游费", "网费"};
    JTable t;
    JScrollPane scroll = new JScrollPane();

    public TeleQury() {
        Frame f = new Frame();
        f.setSize(600, 520);
        f.setTitle("手机信息查询");
        f.setBackground(Color.lightGray);
        f.setResizable(false);
        Panel p = new Panel();
        Panel p1 = new Panel();
        Panel p2 = new Panel();
        t = new JTable(cells, columns);
        t.setAutoResizeMode(t.AUTO_RESIZE_OFF);
        t.setCellSelectionEnabled(false);
        p1.setLayout(null);
        scroll.getViewport().add(t);
        scroll.setBounds(10, 10, 310, 50);
        p1.add(scroll);
        p1.setBounds(10, 10, 320, 60);
        b.setLabel("查询");
        p2.add(new Label("手机号"));
        tf = new TextField(13);
        p2.add(tf);
        p2.add(b);
        p.add(p1);
        p.add(p2);
        f.add(p);
        f.setVisible(true);
    }

    public static void main(String args[]) {
        new TeleQury();
    }
}