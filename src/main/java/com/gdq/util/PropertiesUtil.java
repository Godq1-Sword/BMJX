package com.gdq.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * program : HighSpeedScanner
 * description: 配置信息工具类
 *
 * @author : gdq
 * data : 2019-11-13 09:31
 **/
public class PropertiesUtil {
    private static Properties properties;

    static {
        properties = new Properties();
        String filePath = System.getProperty("user.dir") + "/conf/config.properties";
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.get(key).toString();
    }
}
