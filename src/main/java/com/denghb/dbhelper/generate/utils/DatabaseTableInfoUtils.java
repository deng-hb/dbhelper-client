package com.denghb.dbhelper.generate.utils;

import com.denghb.dbhelper.generate.domain.DatabaseTableInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ppd on 2017/2/20.
 */
public class DatabaseTableInfoUtils {

    public static List<DatabaseTableInfo> load(Connection conn, String database) throws SQLException {
        List<DatabaseTableInfo> list = new ArrayList<DatabaseTableInfo>();


        String sql = "select table_name,table_comment from information_schema.tables where table_schema=?;";
        PreparedStatement preStatement = conn.prepareStatement(sql);
        preStatement.setString(1, database);
        ResultSet result = preStatement.executeQuery();

        // 展开结果集数据库
        DatabaseTableInfo dti = null;
        while (result.next()) {
            // 通过字段检索
            String name = result.getString("table_name");
            String comment = result.getString("table_comment");

            dti = new DatabaseTableInfo();
            dti.setTableName(name);
            dti.setTableComment(comment);
            list.add(dti);
        }
        try {
            preStatement.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
