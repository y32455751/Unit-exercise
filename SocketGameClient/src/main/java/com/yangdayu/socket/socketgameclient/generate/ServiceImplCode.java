package com.yangdayu.socket.socketgameclient.generate;

import java.util.Scanner;

public class ServiceImplCode {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StringBuffer buffer=new StringBuffer();
		
		Scanner input=new Scanner(System.in);
		System.out.println("请输入表名");
		
		String entityname = input.next().toLowerCase();
		String classname = entityname.substring(0, 1).toUpperCase()+entityname.substring(1).toLowerCase();
		entityname = entityname.toLowerCase();
		
		buffer.append("package com.yangdayu.socket.socketgameclient.service.impl;\n\n");
		
		buffer.append("import java.util.List;\n\n");
		
		buffer.append("import javax.annotation.Resource;\n\n");
		
		buffer.append("import org.springframework.stereotype.Service;\n");
		buffer.append("import org.springframework.transaction.annotation.Transactional;\n\n");
		
//		buffer.append("import com.easyShop.codeinfo.dao.CodeInfoMapper;\n");
//		buffer.append("import com.easyShop.codeinfo.entity.CodeInfo;\n");
//		buffer.append("import com.easyShop.codeinfo.service.CodeInfoService;\n");
		buffer.append("import com.yangdayu.socket.socketgameclient.unit.YUUID;\n\n");
		
		buffer.append("@Service\n");
		buffer.append("@Transactional\n");
		buffer.append("public class "+classname+"ServiceImpl implements "+classname+"Service {\n\n");
		
		buffer.append("\t@Resource\n");
		buffer.append("\tprivate "+classname+"Mapper mapper;\n\n");
		
		buffer.append("\t@Override\n");
		buffer.append("\tpublic "+classname+" add"+classname+"("+classname+" "+entityname+") {\n");
		buffer.append("\t\tString objuid = YUUID.getUUID();\n");
		buffer.append("\t\t"+entityname+".setObjuid(objuid);\n");
		buffer.append("\t\tmapper.add"+classname+"("+entityname+");\n");
		buffer.append("\t\t"+entityname+" = new "+classname+"();\n");
		buffer.append("\t\t"+entityname+" = this.find"+classname+"ByObjuid(objuid);\n");
		buffer.append("\t\treturn "+entityname+";\n");
		buffer.append("\t}\n\n");
		
		buffer.append("\t@Override\n");
		buffer.append("\tpublic int delete"+classname+"(String objuid) {\n");
		buffer.append("\t\treturn mapper.delete"+classname+"(objuid);\n");
		buffer.append("\t}\n\n");
		
		buffer.append("\t@Override\n");
		buffer.append("\tpublic "+classname+" update"+classname+"("+classname+" "+entityname+") {\n");
		buffer.append("\t\tint num = mapper.update"+classname+"("+entityname+");\n");
		buffer.append("\t\tif(num>0){\n");
		buffer.append("\t\t\tString objuid = "+entityname+".getObjuid();\n");
		buffer.append("\t\t\t"+entityname+" = new "+classname+"();\n");
		buffer.append("\t\t\t"+entityname+" = this.find"+classname+"ByObjuid(objuid);\n");
		buffer.append("\t\t\treturn "+entityname+";\n");
		buffer.append("\t\t}else{\n");
		buffer.append("\t\t\treturn null;\n");
		buffer.append("\t\t}\n");
		buffer.append("\t}\n\n");
		
		buffer.append("\t@Override\n");
		buffer.append("\tpublic "+classname+" find"+classname+"ByObjuid(String objuid) {\n");
		buffer.append("\t\treturn mapper.find"+classname+"ByObjuid(objuid);\n");
		buffer.append("\t}\n\n");
		
		buffer.append("\t@Override\n");
		buffer.append("\tpublic List<"+classname+"> findAll"+classname+"() {\n");
		buffer.append("\t\treturn mapper.findAll"+classname+"();\n");
		buffer.append("\t}\n\n");
		
		buffer.append("\t@Override\n");
		buffer.append("\tpublic List<"+classname+"> find"+classname+"ByWhere("+classname+" "+entityname+") {\n");
		buffer.append("\t\treturn mapper.find"+classname+"ByWhere("+entityname+");\n");
		buffer.append("\t}\n\n");
		
		buffer.append("\t@Override\n");
		buffer.append("\tpublic List<"+classname+"> find"+classname+"ByPage("+classname+" "+entityname+") {\n");
		buffer.append("\t\treturn mapper.find"+classname+"ByPage("+entityname+");");
		buffer.append("\t}\n\n");
		
		buffer.append("}");
		
		System.out.println(buffer.toString());
	}

}

























