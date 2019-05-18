package com.yangdayu.socket.socketgameclient.generate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EntityCode {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> name=new ArrayList<String>();
		List<String> type=new ArrayList<String>();
		List<String> text=new ArrayList<String>();
		StringBuffer buffer=new StringBuffer();
		Scanner input = new Scanner(System.in);
		boolean date=false;
		System.out.println("请输入表名");
		String classname = input.next();
		classname = classname.substring(0, 1).toUpperCase() + classname.substring(1).toLowerCase();
		String entityname = classname.toLowerCase();
		System.out.println("请输入字段名称");
		
		for(;;){
			String in = input.next();
			if("end".equals(in))
				break;
			name.add(in.toLowerCase());
		}
		
		System.out.println("请输入相对应的数据格式");
		
		for(;;){
			String in = input.next();
			if("end".equals(in))
				break;
			type.add(in.toLowerCase());
		}
		
		System.out.println("请输入相对应的注释");
		
		for(;;){
			String in = input.next();
			if("end".equals(in))
				break;
			text.add(in);
		}
		
		for(int i=0;i<type.size();i++){
			if("date".equals(type.get(i)))
				date=true;
		}
		
		buffer.append("package com.yangdayu.socket.socketgameclient.entity;\n\n");
		
		if(date)
			buffer.append("import java.util.Date;\n\n");
		
//		buffer.append("import com.exedosoft.plat.bo.BOInstance;\n\n");
		
		buffer.append("public class "+classname+" {\n\n");
		
		for(int i=0;i<name.size();i++){
			buffer.append("\t/**\n");
			buffer.append("\t * "+text.get(i)+"\n");
			buffer.append("\t */\n");
			String typestr="";
			if(type.get(i).indexOf("char")>=0){
				typestr="String";
			}else if(type.get(i).indexOf("date")>=0){
				typestr="Date";
			}else if(type.get(i).indexOf("number")>=0){
				typestr="int";
			}
			
			buffer.append("\tprivate "+typestr+" "+name.get(i)+";\n");
		}
		
		buffer.append("\n\n}");
		
		System.out.println(buffer.toString());
		
	}

}






















