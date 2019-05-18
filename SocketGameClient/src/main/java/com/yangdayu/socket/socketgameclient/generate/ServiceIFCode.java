package com.yangdayu.socket.socketgameclient.generate;

import java.util.Scanner;

public class ServiceIFCode {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StringBuffer buffer=new StringBuffer();
		
		Scanner input=new Scanner(System.in);
		System.out.println("请输入表名");
		
		String entityname = input.next().toLowerCase();
		String classname = entityname.substring(0, 1).toUpperCase()+entityname.substring(1).toLowerCase();
		entityname = entityname.toLowerCase();
		
		buffer.append("package com.yangdayu.socket.socketgameclient.service;\n\n");
		buffer.append("import java.util.List;\n\n");
		buffer.append("import com.yangdayu.socket.socketgameclient.entity."+entityname+";\n\n");
		buffer.append("public interface "+classname+"Service {\n\n");
		buffer.append("\t"+classname+" add"+classname+"("+classname+" "+entityname+");\n\n");
		buffer.append("\tint delete"+classname+"(String objuid);\n\n");
		buffer.append("\t"+classname+" update"+classname+"("+classname+" "+entityname+");\n\n");
		buffer.append("\t"+classname+" find"+classname+"ByObjuid(String objuid);\n\n");
		buffer.append("\tList<"+classname+"> findAll"+classname+"();\n\n");
		buffer.append("\tList<"+classname+"> find"+classname+"ByWhere("+classname+" "+entityname+");\n\n");
		buffer.append("\tList<"+classname+"> find"+classname+"ByPage("+classname+" "+entityname+");\n\n}");
		
		System.out.println(buffer.toString());
	}

}
