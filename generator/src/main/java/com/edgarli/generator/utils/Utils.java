package com.edgarli.generator.utils;

import com.edgarli.generator.entity.Generator;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName Utils
 * @Description
 * @Author lixun
 * @Date 2020/9/3 17:34
 * @Version 1.0
 **/
public class Utils {

    private static final char SEPARATOR = '_';

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }

        s = s.toLowerCase();

        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    static String toUnderScoreCase(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i > 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * @return Map<String, Object>
     * @Title: getProfileByPropertiesLoaderUtils
     * @Description: Spring 提供的 PropertiesLoaderUtils 允许您直接通过基于类路径的文件地址加载属性资源
     * 最大的好处就是：实时加载配置文件，修改后立即生效，不必重启
     */
    public static Generator getProfileByPropertiesLoaderUtils() {
        Properties props = new Properties();
        Generator generator = new Generator();

        Map<String, Object> profileMap = new HashMap<String, Object>();
        try {
            props = PropertiesLoaderUtils.loadAllProperties("generatorcConfig.properties");
            for (Object key : props.keySet()) {
                profileMap.put(key.toString(), props.getProperty(key.toString()).replace("\"", ""));
            }
            generator = EntityUtils.mapToEntity(profileMap, Generator.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return generator;
    }

    /**
     * 传递键值对的Map，更新properties文件
     *
     * @param keyValueMap 键值对Map
     */
    public static void updateProperties(Map<String, Object> keyValueMap) {
        // 输入流
        // InputStream
        // inputStream=PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);
        // 获取文件的路径
        String filePath = Utils.class.getClassLoader().getResource("generatorcConfig.properties").getPath();
        String first = String.valueOf(filePath.charAt(0));
        if (first.equals("/")) {
            filePath = filePath.substring(1);
        }
        System.out.println("propertiesPath:" + filePath);
        Properties props = new Properties();
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            // 从输入流中读取属性列表（键和元素对）
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            props.load(br);
            br.close();
            // 写入属性文件
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)));
            // 清空旧的文件
            // props.clear();
            for (String key : keyValueMap.keySet()) {
                props.setProperty(key, keyValueMap.get(key).toString());
            }
            props.store(bw, "改变数据");
            System.out.println("改变数据");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Visit " + filePath + " for updating " + "" + " value error");
        } finally {
            try {
                br.close();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
