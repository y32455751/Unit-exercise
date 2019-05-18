package com.yangdayu.socket.socketgameclient.generate;

import java.lang.reflect.Field;
import java.util.Scanner;

import com.yangdayu.socket.socketgameclient.entity.UserInfoEntity;


public class BOInstanceCode {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input=new Scanner(System.in);
		UserInfoEntity t=new UserInfoEntity();
		System.out.println("输入类名");
		String classname = input.next();
		String entityname = classname.toLowerCase();
		// 获取实体类的所有属性，返回Field数组
		Field[] field = t.getClass().getDeclaredFields(); 
		System.out.println("public BOInstance toBOInstance("+classname+" "+entityname+"){");
		System.out.println("\tBOInstance bo = new BOInstance();");
		for (int i = 0; i < field.length; i++){  
			String name = field[i].getName();
			System.out.println("\tbo.putValue(\""+name+"\", "+entityname+".get"+name.replace(name.substring(0, 1), name.substring(0, 1).toUpperCase())+"());");
		}
		System.out.println("\treturn bo;");
		System.out.println("}");
	}

}
