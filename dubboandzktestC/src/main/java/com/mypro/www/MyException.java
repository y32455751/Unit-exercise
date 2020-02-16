package com.mypro.www;

import org.apache.dubbo.rpc.RpcException;

import java.util.HashMap;
import java.util.Map;

public class MyException {

    public static Map<String,String> GET_EXCEPTION_MSG(Exception e){
        Map<String,String> map = new HashMap<String,String>();
        if(e instanceof RpcException){
            map.put("msg","超时");
            map.put("code","timeout");
        }
        if(e instanceof NullPointerException){
            map.put("msg","空指针");
            map.put("code","nullpointer");
        }
        return map;
    }

}
