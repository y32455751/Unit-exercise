package com.yangdayu.socket.socketgameclient.unit;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class ReturnUnit {

    public static String RETURN_SUCCESS = "success";
    public static String RETURN_ERROR = "error";

    public static Map<String,Object> getReturnMap(String status,String message,Object date){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("status",status);
        map.put("message",message);
        map.put("data",date);
        return map;
    }

    public static String getReturnError(String message){
        return JSON.toJSONString(ReturnUnit.getReturnMap(ReturnUnit.RETURN_ERROR,message,null));
    }

    public static String getReturnError(String message,Object date){
        return JSON.toJSONString(ReturnUnit.getReturnMap(ReturnUnit.RETURN_ERROR,message,date));
    }

    public static String getReturnSuccess(String message,Object date){
        return JSON.toJSONString(ReturnUnit.getReturnMap(ReturnUnit.RETURN_SUCCESS,message,date));
    }

    public static String getReturnError(){
        return JSON.toJSONString(ReturnUnit.getReturnMap(ReturnUnit.RETURN_ERROR,"发生错误，请联系管理员",null));
    }

    public static String getReturnSuccess(Object date){
        return JSON.toJSONString(ReturnUnit.getReturnMap(ReturnUnit.RETURN_SUCCESS,"操作成功",date));
    }

}
