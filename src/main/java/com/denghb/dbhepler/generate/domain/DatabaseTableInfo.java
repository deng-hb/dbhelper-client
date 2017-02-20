package com.denghb.dbhepler.generate.domain;

/**
 * Created by ppd on 2017/2/20.
 */
public class DatabaseTableInfo {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表备注
     */
    private String tableComment;

    public String getTableName() {

        if (null != tableName) {
            // 去掉*号
            tableName = tableName.replaceAll("\\*/", "");
        }
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        if (null != tableComment) {
            tableComment = tableComment.replaceAll("\\*/", "");
        }
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

}
