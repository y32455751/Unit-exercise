package com.code.generate.Util;

import com.code.generate.Entity.CodeEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CodeUtil {
    /**
     * 生成import
     * @param unit 导入的类名
     * @param from 路径
     * @return 整齐的import代码
     */
    public static String generateImport(String unit,String from){
        String[] str = {unit};
        return generateImport(str,from);
    }

    /**
     * 生成import
     * @param unit 导入的类名（多个）
     * @param from 路径
     * @return 整齐的import代码
     */
    public static String generateImport(String[] unit,String from){
        StringBuffer buffer = new StringBuffer();
        buffer.append(CodeEntity.Import + " {" );
        for (String s : unit) {
            buffer.append(" " + s + ",");
        }
        buffer.deleteCharAt(buffer.length()-1);
        buffer.append(" } " + CodeEntity.From + " '" + from + "';");
        return buffer.toString();
    }

    /**
     * 生成class注释
     * @param name 名称
     * @return 整齐的类注释
     */
    public static String classNotes(String name,String indent){
        return classNotes(name,indent,"none");
    }

    /**
     * 生成class注释
     * @param name 名称
     * @param remarks 简介
     * @return 整齐的类注释
     */
    public static String classNotes(String name,String indent,String remarks){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuffer buffer = new StringBuffer();
        buffer.append(indent + "/**\n");
        buffer.append(indent + " * @author " + CodeEntity.Author_YangDayu + "\n");
        buffer.append(indent + " * @title " + name + "\n");
        if(!remarks.equals("none"))
        buffer.append(indent + " * @remark " + remarks + "\n");
        buffer.append(indent + " * @date " + format.format(new Date()) + "\n");
        buffer.append(indent + " */");
        return buffer.toString();
    }

    /**
     * 生成行内注释
     * @param text 注释内容
     * @return 整齐的行内注释
     */
    public static String lineNotes(String text){
        StringBuffer buffer = new StringBuffer();
        buffer.append("\t// " + text);
        return buffer.toString();
    }

    public static String upperCaseFrist(String str){
        char[] chars = str.toCharArray();
        if (chars[0] >= 'a' && chars[0] <= 'z') {
            chars[0] = (char)(chars[0] - 32);
        }
        return new String(chars);
    }

    public static void main(String[] args) {
        System.out.println(lineNotes("测试注释"));
    }
}
