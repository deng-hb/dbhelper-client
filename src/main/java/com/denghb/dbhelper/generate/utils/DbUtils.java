package com.denghb.dbhelper.generate.utils;

import com.denghb.dbhelper.domain.ConnectionInfo;
import com.denghb.dbhelper.generate.GenerateException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtils {

    private static final String DB_URL = "jdbc:mysql://%s:%s/%s?useUnicode=true&amp;characterEncoding=utf-8";

    private static Connection connection = null;

    public static void init(ConnectionInfo info) throws GenerateException {
        try {
            String url = String.format(DB_URL, info.getHost(), info.getPort(), info.getDatabase());
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");

            // 打开链接
            System.out.println("Connection ...");
            connection = DriverManager.getConnection(url, info.getUsername(), info.getPassword());

        } catch (Exception e) {
            e.printStackTrace();
            throw new GenerateException("JDBC Error");
        }
    }

    /**
     * 先init
     *
     * @return
     */
    public static Connection getConnection() {
        return connection;
    }

    public static void closeConnection() {
        if (null != connection) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
