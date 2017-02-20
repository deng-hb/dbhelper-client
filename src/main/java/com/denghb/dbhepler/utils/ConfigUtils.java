package com.denghb.dbhepler.utils;


import java.io.*;
import java.util.Properties;

/**
 * Created by denghb on 2016/12/16.
 */
public class ConfigUtils {



    private static String PROP_FILE_NAME = ".config";

    /**
     * 获取属性
     *
     * @param name
     * @return
     */
    public static String getValue(String name) {


        String value = null;

        try {
            InputStream in = getInputStream();
            Properties properties = new Properties();
            properties.load(in);

            value = properties.getProperty(name);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 获取属性
     *
     * @param name
     * @return
     */
    public static void setValue(String name, String value) {

        try {
            InputStream in = getInputStream();
            Properties properties = new Properties();
            properties.load(in);

            properties.setProperty(name, value);
            FileOutputStream fos = new FileOutputStream(PROP_FILE_NAME);
            // 将Properties集合保存到流中
            properties.store(fos, "Copyright (c) denghb.com");
            fos.close();// 关闭流
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static InputStream getInputStream() {
        InputStream in = null;
        try {
            in = new FileInputStream(PROP_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();

        }

        try {
            if (null == in) {
                OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(PROP_FILE_NAME), "utf-8");
                out.write("");
                out.flush();
                out.close();
                in = new FileInputStream(PROP_FILE_NAME);
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return in;

    }


    public static void main(String... args) {
        setValue("a", "b");
        System.out.println(getValue("a"));
    }

}
