package com.yangdayu.socket.socketgameclient.generate;

import com.yangdayu.socket.socketgameclient.entity.UserInfoEntity;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Scanner;


public class MapperXMLCode {

	public static void main(String[] args){
		
		Scanner input=new Scanner(System.in);
		UserInfoEntity t=new UserInfoEntity();
		System.out.println("输入类名");
		String classname = input.next();
		String entityname = classname.toLowerCase();
		// 获取实体类的所有属性，返回Field数组
		Field[] field = t.getClass().getDeclaredFields(); 
		StringBuffer buffer=new StringBuffer();
		
		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buffer.append("\n<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\""); 
		buffer.append("\n\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
		buffer.append("\n<!--"); 
		buffer.append("\nnamespace：必须与对应的接口全类名一致");
		buffer.append("\nid:必须与对应接口的某个对应的方法名一致");
		buffer.append("\n-->");
		buffer.append("\n<mapper namespace=\"com.yangdayu.socket.socketgameclient.mapper."+classname+"Mapper\">");
		buffer.append("\n\t<insert id=\"add"+classname+"\" parameterType=\""+classname+"\">");
		
		buffer.append("\n\t\tinsert into "+classname+"(");
		
		for (int i = 0; i < field.length; i++){  
			String name = field[i].getName();
			buffer.append(name+",");
		}
		
		buffer.deleteCharAt(buffer.length()-1);
		
		buffer.append(") values(");
		
		for (int i = 0; i < field.length; i++){  
			String name = field[i].getName();
			buffer.append("#{"+name+"},");
		}

		buffer.deleteCharAt(buffer.length()-1);
		
		buffer.append(")");
		
		buffer.append("\n\t</insert>");
		
		buffer.append("\n\t<update id=\"update"+classname+"\" parameterType=\""+classname+"\">");
		
		buffer.append("\n\t\tupdate "+classname+" set ");
		
		for (int i = 0; i < field.length; i++){  
			String name = field[i].getName();
			if(!"objuid".equals(name))
			buffer.append(name+"=#{"+name+"},");
		}

		buffer.deleteCharAt(buffer.length()-1);
		
		buffer.append(" where objuid=#{objuid}");
		
		buffer.append("\n\t</update>");
		
		buffer.append("\n\t<delete id=\"delete"+classname+"\" parameterType=\"String\">");
		
		buffer.append("\n\t\tdelete from "+classname);
		
		buffer.append(" where objuid=#{objuid}");
		
		buffer.append("\n\t</delete>");
		
		buffer.append("\n\t<select id=\"find"+classname+"ByObjuid\" parameterType=\"String\" resultType=\""+classname+"\">");
		
		buffer.append("\n\t\tselect ");
		
		for (int i = 0; i < field.length; i++){  
			String name = field[i].getName();
			buffer.append(name+",");
		}

		buffer.deleteCharAt(buffer.length()-1);
		
		buffer.append(" from "+classname);
		
		buffer.append(" where objuid=#{objuid}");
		
		buffer.append("\n\t</select>");
		
		buffer.append("\n\t<select id=\"findAll"+classname+"\" resultType=\""+classname+"\">");
		
		buffer.append("\n\t\tselect ");
		
		for (int i = 0; i < field.length; i++){  
			String name = field[i].getName();
			buffer.append(name+",");
		}

		buffer.deleteCharAt(buffer.length()-1);
		
		buffer.append(" from "+classname);
		
		buffer.append("\n\t</select>");
		
		buffer.append("\n\t<select id=\"find"+classname+"ByPage\" parameterType=\""+classname+"\" resultType=\""+classname+"\">");
		
		buffer.append("\n\t\tselect ");
		
		for (int i = 0; i < field.length; i++){  
			String name = field[i].getName();
			buffer.append(name+",");
		}

		buffer.deleteCharAt(buffer.length()-1);

		buffer.append(" from (");
		
		buffer.append("\n\t\t\tselect ");
		
		for (int i = 0; i < field.length; i++){  
			String name = field[i].getName();
			buffer.append(name+",");
		}

		buffer.append("rownum row_num from "+classname);
		
		buffer.append(" where 1=1 ");
		
		for (int i = 0; i < field.length; i++){  
			String name = field[i].getName();
			Class c = field[i].getType();
			
			if(c==String.class){
				buffer.append("\n\t\t\t<if test=\""+name+"!=null and "+name+"!='' \">");
				buffer.append("\n\t\t\t\tAND "+name+" LIKE concat(concat('%',#{"+name+"}),'%') ");
				buffer.append("\n\t\t\t</if>");
			}else if(c==Integer.class){
				buffer.append("\n\t\t\t<if test=\""+name+"!=null and "+name+"!='' \">");
				buffer.append("\n\t\t\t\tAND "+name+" = #{"+name+"} ");
				buffer.append("\n\t\t\t</if>");
			}else if(c==Date.class){
				buffer.append("\n\t\t\t<if test=\""+name+"1!=null and "+name+"1!='' \">");
				buffer.append("\n\t\t\t\tAND "+name+" &gt;= #{"+name+"} ");
				buffer.append("\n\t\t\t</if>");
				buffer.append("\n\t\t\t<if test=\""+name+"2!=null and "+name+"2!='' \">");
				buffer.append("\n\t\t\t\tAND "+name+" &lt;= #{"+name+"} ");
				buffer.append("\n\t\t\t</if>");
			}else if(c==int.class){
				buffer.append("\n\t\t\t<if test=\""+name+"!=null and "+name+"!='' \">");
				buffer.append("\n\t\t\t\tAND "+name+" = #{"+name+"} ");
				buffer.append("\n\t\t\t</if>");
			}
		}
		
		buffer.append("\n\t\t) where row_num between #{pagestart} and #{pageend}");
		
		buffer.append("\n\t</select>");
		
		buffer.append("\n\t<select id=\"find"+classname+"ByWhere\" parameterType=\""+classname+"\" resultType=\""+classname+"\">");
		
		buffer.append("\n\t\tselect ");
		
		for (int i = 0; i < field.length; i++){  
			String name = field[i].getName();
			buffer.append(name+",");
		}

		buffer.deleteCharAt(buffer.length()-1);
		
		buffer.append(" from "+classname);
		
		buffer.append(" where 1=1 ");
		
		for (int i = 0; i < field.length; i++){  
			String name = field[i].getName();
			Class c = field[i].getType();
			
			if(c==String.class){
				buffer.append("\n\t\t<if test=\""+name+"!=null and "+name+"!='' \">");
				buffer.append("\n\t\t\tAND "+name+" LIKE concat(concat('%',#{"+name+"}),'%') ");
				buffer.append("\n\t\t</if>");
			}else if(c==Integer.class){
				buffer.append("\n\t\t<if test=\""+name+"!=null and "+name+"!='' \">");
				buffer.append("\n\t\t\tAND "+name+" = #{"+name+"} ");
				buffer.append("\n\t\t</if>");
			}else if(c==Date.class){
				buffer.append("\n\t\t<if test=\""+name+"1!=null and "+name+"1!='' \">");
				buffer.append("\n\t\t\tAND "+name+" &gt;= #{"+name+"} ");
				buffer.append("\n\t\t</if>");
				buffer.append("\n\t\t<if test=\""+name+"2!=null and "+name+"2!='' \">");
				buffer.append("\n\t\t\tAND "+name+" &lt;= #{"+name+"} ");
				buffer.append("\n\t\t</if>");
			}else if(c==int.class){
				buffer.append("\n\t\t<if test=\""+name+"!=null and "+name+"!='' \">");
				buffer.append("\n\t\t\tAND "+name+" = #{"+name+"} ");
				buffer.append("\n\t\t</if>");
			}
		}
		
		buffer.append("\n\t</select>\n</mapper>");
		
		
		System.out.println(buffer.toString());
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
}
