package com.simple.util;

/**
 * 驼峰<=>下划线转换
 * @author chenkx
 * @date   2017-12-27
 */
public class CamelConvertUtil {

    public static String underline2Camel(String s) {
        return parse2Camel(s, "_");
    }

    public static String horizontalLine2Camel(String s) {
        return parse2Camel(s, "-");
    }

    public static String camel2Underline(String s){
        if (s==null||"".equals(s.trim())){
            return "";
        }
        int len=s.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c=s.charAt(i);
            if (Character.isUpperCase(c)){
                sb.append("_");
                sb.append(Character.toLowerCase(c));
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private static String parse2Camel(String s, String splitFlag) {
        // 快速检查
        if (s == null || s.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!s.contains(splitFlag)) {
            // 不含下划线，仅将首字母小写
            return s.substring(0, 1).toLowerCase() + s.substring(1);
        }
        StringBuilder sb = new StringBuilder();
        // 用下划线将原始字符串分割
        String camels[] = s.split(splitFlag);
        for (String camel :  camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 处理真正的驼峰片段
            if (sb.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                sb.append(camel.toLowerCase());
            } else {
                // 其他的驼峰片段，首字母大写
                sb.append(camel.substring(0, 1).toUpperCase());
                sb.append(camel.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(underline2Camel("_apple_boy_cat_dog_"));
        System.out.println(horizontalLine2Camel("-apple-boy-cat-dog_"));
        System.out.println(camel2Underline("appleBoyCatDog"));
    }

}
