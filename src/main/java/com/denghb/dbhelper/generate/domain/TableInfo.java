package com.denghb.dbhelper.generate.domain;

import java.io.Serializable;

import com.denghb.dbhelper.generate.utils.ColumnUtils;

public class TableInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String columnName;
    private String dataType;
    private String columnKey;

    private String columnComment;

    // updated_by -> updatedBy
    private String objectName;
    // updated_by -> UpdatedBy
    private String methodName;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        // TODO
        return ColumnUtils.databaseTypeToJavaType(dataType);
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getObjectName() {
        return ColumnUtils.removeAll_AndNextCharToUpperCase(this.columnName.toLowerCase());
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    public String getMethodName() {
        return ColumnUtils.firstCharToUpperCase(getObjectName());
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String toString() {
        return "TableInfo [columnName=" + columnName + ", dataType=" + dataType + ", columnKey=" + columnKey
                + ", columnComment=" + columnComment + ", objectName=" + objectName + ", methodName=" + methodName
                + "]";
    }

}
