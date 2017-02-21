package com.denghb.dbhelper.generate;

import com.denghb.dbhelper.generate.domain.DatabaseTableInfo;
import com.denghb.dbhelper.generate.domain.TableInfo;
import com.denghb.dbhelper.generate.utils.ColumnUtils;
import com.denghb.dbhelper.generate.utils.DatabaseTableInfoUtils;
import com.denghb.dbhelper.generate.utils.TableInfoUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ppd on 2017/2/20.
 */
public class Generate {


    private static final String DB_URL = "jdbc:mysql://%s:%s/%s?useUnicode=true&amp;characterEncoding=utf-8";

    private Generate(String host, String username, String password, String database, String port, String packageName, String targetDir) throws GenerateException {

        String url = String.format(DB_URL, host, port, database);

        Connection conn = null;
        try {
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");

            // 打开链接
            System.out.println("Connection ...");
            conn = DriverManager.getConnection(url, username, password);

            // 获取对应数据库表信息
            List<DatabaseTableInfo> list = DatabaseTableInfoUtils.load(conn, database);
            for (DatabaseTableInfo info : list) {
                create(conn, database, packageName, info, targetDir);
            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new GenerateException("JDBC Error");
        } finally {
            // 关闭资源
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }

    private void create(Connection conn, String database, String packageName, DatabaseTableInfo info, String targetDir) throws SQLException, GenerateException {
        // 类名
        String domainName = ColumnUtils.removeAll_AndNextCharToUpperCase(info.getTableName());
        domainName = ColumnUtils.firstCharToUpperCase(domainName);
        // 查询对应数据库对应表的字段信息
        String sql = "SELECT * FROM information_schema.COLUMNS WHERE table_name = ? AND table_schema = ? ";

        List<TableInfo> list = TableInfoUtils.load(conn, database, info.getTableName());

        // 查询DDL 只能拼接
        String ddlSql = "show create table `" + database + "`.`" + info.getTableName() + "`";
        PreparedStatement preStatement = conn.prepareStatement(ddlSql);
        ResultSet result = preStatement.executeQuery();
        String ddl = null;
        while (result.next()) {
            ddl = result.getString(2);
            break;
        }
        try {
            preStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Configuration config = new Configuration(Configuration.VERSION_2_3_0);
            config.setClassForTemplateLoading(ClassLoader.class, "/templates/");
            Template template = config.getTemplate("domain.ftl", "UTF-8");
            // 创建数据模型
            Map<String, Object> root = new HashMap<String, Object>();

            root.put("list", list);
            root.put("tableComment", info.getTableComment());
            root.put("domainName", domainName);

            root.put("tableName", info.getTableName());

            root.put("databaseName", database);
            root.put("tableDdl", ddl);

            root.put("generateTime", new Date().toString());
            root.put("packageName", packageName);

            // TODO 路径
            File file = new File(targetDir + "/" + domainName + ".java");
            if (!file.exists()) {
                // System.out.println("file exist");
                file.createNewFile();
            }
            Writer out = new BufferedWriter(new FileWriter(file));
            template.process(root, out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenerateException("Generate Error");
        }
    }

    public static void start(String host, String username, String password, String database, String port, String packageName, String targetDir) throws GenerateException {
        new Generate(host, username, password, database, port, packageName, targetDir);
    }
}
