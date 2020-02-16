package com.mypro.www;

import java.util.HashMap;

public class ReturnUtil {

    private String content_abb;
    private Integer code;
    private String msg;
    private Object data;
    private LoggerUtil logger = new LoggerUtil(new Exception().getStackTrace()[1].getClassName());
    public ReturnUtil(Object data){
        this.content_abb = ResultCode.SUCCESS.toString();
        this.code = ResultCode.SUCCESS.code();
        this.msg = ResultCode.SUCCESS.msg();
        if(null == data){
            data = new HashMap<String,Object>();
        }
        this.data = data;
        this.info(ResultCode.SUCCESS,data);
    }
    public ReturnUtil(){
        this.content_abb = ResultCode.SUCCESS.toString();
        this.code = ResultCode.SUCCESS.code();
        this.msg = ResultCode.SUCCESS.msg();
        this.data = new HashMap<String,Object>();
        this.info(ResultCode.SUCCESS,data);
    }


    public ReturnUtil(String msg, Object data){

        this.content_abb = "CUSTOM";
        this.code = 00000;
        this.msg = msg;
        if(null == data){
            data = new HashMap<String,Object>();
        }
        this.data = data;
        this.err("CUSTOM",00000,msg,data);
    }

    public ReturnUtil(String msg){
        this.content_abb = "CUSTOM";
        this.code = 00000;
        this.msg = msg;
        this.data = new HashMap<String,Object>();
        this.err("CUSTOM",00000,msg,data);
    }

    public ReturnUtil(ResultCode resultCode, Object data){
        this.content_abb = resultCode.toString();
        this.code = resultCode.code();
        this.msg = resultCode.msg();
        if(null == data){
            data = new HashMap<String,Object>();
        }
        this.data = data;
        this.info(resultCode,data);
    }

    public ReturnUtil(ResultCode resultCode){
        this.content_abb = resultCode.toString();
        this.code = resultCode.code();
        this.msg = resultCode.msg();
        this.data = new HashMap<String,Object>();
        this.info(resultCode,data);
    }

    private void info(ResultCode resultCode, Object data){
        this.printLogger(resultCode.toString(),resultCode.code(),resultCode.msg(),data,1);
    }

    private void err(ResultCode resultCode, Object data){
        this.printLogger(resultCode.toString(),resultCode.code(),resultCode.msg(),data,0);
    }

    private void info(String content_abb,Integer code,String msg,Object data){
        this.printLogger(content_abb,code,msg,data,1);
    }

    private void err(String content_abb,Integer code,String msg,Object data){
        this.printLogger(content_abb,code,msg,data,0);
    }

    private void printLogger(String content_abb,Integer code,String msg,Object data,int type){
        StringBuffer buffer = new StringBuffer();
        buffer.append("执行结果 \033[32;4m"+content_abb+"\033[0m");
        buffer.append(" \033[35;4m"+code+"\033[0m");
        buffer.append(" "+msg);
        if(null != data){
            buffer.append(" 返回参数："+data.toString());
        }
        if(type > 0){
            logger.info(buffer.toString());
        }else{
            logger.err(buffer.toString());
        }
    }

    public String getContent_abb() {
        return content_abb;
    }

    public void setContent_abb(String content_abb) {
        this.content_abb = content_abb;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
