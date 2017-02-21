package com.denghb.dbhelper.generate.utils;

/**
 *
 */
public class ColumnUtils {

    /**
     * 移除所有的"_"然后每个"_"下一个字符转大写
     *
     * @return
     */
    public static String removeAll_AndNextCharToUpperCase(String string) {
        if (null != string) {
            while (true) {
                string = remove_AndNextCharToUpperCase(string);
                if (-1 == string.indexOf("_")) {
                    return string;
                }
            }
        }
        return null;
    }

    /**
     * 移除第一个"_"并将后一个字符串大写
     *
     * @param string
     * @return
     */
    private static String remove_AndNextCharToUpperCase(String string) {
        int index = string.indexOf("_");
        if (-1 < index) {
            // 将字符串截成2份 以"_"分开
            String str1 = string.substring(0, index);
            String str2 = string.substring(index + 1, string.length());
            return str1 + firstCharToUpperCase(str2);
        }
        return string;
    }

    /**
     * 数据库类型转java类型 TODO 待完善
     *
     * @param dataType
     * @return
     */
    public static String databaseTypeToJavaType(String dataType) {
        if (null == dataType) {
            return null;
        }

        dataType = dataType.toLowerCase().trim();
        if ("varchar".equals(dataType) || "text".equals(dataType) || "string".equals(dataType) || "char".equals(dataType) || "longtext".equals(dataType)) {
            dataType = "String";
        } else if ("int".equals(dataType) || "integer".equals(dataType) || "smallint".equals(dataType)) {
            dataType = "Integer";
        } else if ("bigint".equals(dataType) || "long".equals(dataType)) {
            dataType = "Long";
        } else if ("timestamp".equals(dataType) || "datetime".equals(dataType) || "date".equals(dataType)) {
            dataType = "java.util.Date";
        } else if ("tinyint".equals(dataType) || "boolean".equals(dataType)) {
            dataType = "Boolean";
        } else if ("decimal".equals(dataType)) {
            dataType = "java.math.BigDecimal";
        } else {
            throw new RuntimeException("dataType not find :" + dataType);
        }
        return dataType;
    }

    /**
     * 首字母转大写
     *
     * @param string
     * @return
     */
    public static String firstCharToUpperCase(String string) {
        if (null != string) {
            int length = string.length();
            // 获取第一个转大写
            String first = string.substring(0, 1);
            first = first.toUpperCase();

            // 判断字符长度
            if (1 == length) {
                return first;
            }

            String other = string.substring(1, length);
            return first + other;
        }
        return string;
    }
}
