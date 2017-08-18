package com.denghb.dbhelper.generate.utils;

import com.denghb.dbhelper.generate.domain.TableInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ppd on 2017/2/20.
 */
public class TableInfoUtils {


    public static List<TableInfo> load(Connection conn, String database, String tableName) {
        List<TableInfo> list = new ArrayList<TableInfo>();

        try {

            String sql = "SELECT column_Name,data_Type,column_Key,column_Comment FROM information_schema.COLUMNS WHERE table_schema = ? AND table_name = ?;";
            PreparedStatement preStatement = conn.prepareStatement(sql);
            preStatement.setString(1, database);
            preStatement.setString(2, tableName);
            ResultSet result = preStatement.executeQuery();

            TableInfo ti = null;
            // 展开结果集数据库
            while (result.next()) {
                // 通过字段检索
                String columnName = result.getString("column_Name");
                String dataType = result.getString("data_Type");
                String columnKey = result.getString("column_Key");
                String columnComment = result.getString("column_Comment");

                ti = new TableInfo();
                ti.setColumnName(columnName);
                ti.setDataType(dataType);

                ti.setColumnKey(columnKey);
                ti.setColumnComment(columnComment);
                list.add(ti);
            }

            preStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
