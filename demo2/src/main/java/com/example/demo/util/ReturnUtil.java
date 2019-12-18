package com.example.demo.util;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class ReturnUtil {

    static String content_abb;
    static Integer code;
    static String msg;
    static Object data;
    public static String srt(Object data){
        return ReturnUtil.rt(ResultCode.SUCCESS,data);
    }

    public static String ert(){
        return ReturnUtil.rt(ResultCode.ERROR,null);
    }

    public static String rt(ResultCode resultCode,Object data){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("content_abb",resultCode);
        map.put("code",resultCode.code());
        map.put("msg",resultCode.msg());
        if(null == data){
            data = new HashMap<String,Object>();
        }
        map.put("data",data);
        return JSON.toJSONString(map);
    }

}
