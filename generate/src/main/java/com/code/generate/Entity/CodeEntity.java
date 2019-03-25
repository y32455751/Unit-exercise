package com.code.generate.Entity;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

public class CodeEntity {

    /**
     * 导入，引用关键字
     */
    public static String Import = "import"; //导入
    public static String From = "from";     //来自于
    public static String Export = "export"; //导出
    public static String Implements = "implements"; //引用
    /**
     * 变量定义
     */
    public static String Public = "public"; //修饰符-公共的
    public static String Static = "static"; //修饰符-静态的
    public static String Let = "let";   //变量定义
    public static String Const = "const";   //变量定义
    /**
     * 文件类别
     */
    public static String Module = "module"; //模型
    public static String Class = "class";   //类
    public static String Component = "component"; //基础
    public static String Routes = "routes"; //路由
    public static String Spec = "spec"; //基础
    public static String Ts = "ts"; //脚本文件后缀
    public static String Scss = "scss"; //样式文件后缀
    public static String Html = "html"; //静态页面文件后缀
    /**
     * 页面
     */
    public static String HtmlInitText = "<div>运行成功</div>";  //页面初始HTML
    /**
     * ts方法
     */
    public static String Constructor = "constructor";   //构造方法
    public static String NgOnInit = "ngOnInit"; //初始化方法
    public static String Author_YangDayu = "杨大宇";
    public static String OUT_FILE_PATH = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\file\\";
    public static String OUT_FILE_RELATIVE_PATH = "\\file\\";

    protected String moduleName;
    protected String moduleName_Else;
    protected String titleName;
    protected String outPath;
    protected HttpServletResponse response;
    protected String rarFilename;
    protected String rarFilePath;
    protected File[] files;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleName_Else() {
        return moduleName_Else;
    }

    public void setModuleName_Else(String moduleName_Else) {
        this.moduleName_Else = moduleName_Else;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getOutPath() {
        return outPath;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public String getRarFilename() {
        return rarFilename;
    }

    public void setRarFilename(String rarFilename) {
        this.rarFilename = rarFilename;
    }

    public String getRarFilePath() {
        return rarFilePath;
    }

    public void setRarFilePath(String rarFilePath) {
        this.rarFilePath = rarFilePath;
    }

    public File[] getFiles() {
        return files;
    }

    public void setFiles(File[] files) {
        this.files = files;
    }
}
