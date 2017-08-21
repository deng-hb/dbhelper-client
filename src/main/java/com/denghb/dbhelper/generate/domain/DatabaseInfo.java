package com.denghb.dbhelper.generate.domain;

/**
 * Created by ppd on 2017/2/20.
 */
public class DatabaseInfo {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表备注
     */
    private String tableComment;

    private boolean checked = true;

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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "DatabaseInfo{" +
                "tableName='" + tableName + '\'' +
                ", tableComment='" + tableComment + '\'' +
                ", checked=" + checked +
                '}';
    }
}
