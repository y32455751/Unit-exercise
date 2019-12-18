package com.example.demo.test;

import com.example.demo.util.ResultCode;
import com.example.demo.util.ReturnUtil;

import java.util.HashMap;
import java.util.Map;

public class test {
    public static void main(String[] args) {
        System.out.println(ert());
        System.out.println(eee());
        System.out.println(sss());
    }

    public static String eee(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("name","杨大宇");
        map.put("age",123);
        return ReturnUtil.rt(ResultCode.ERROR,map);
    }
    public static String sss(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("name","杨大宇");
        map.put("age",123);
        return ReturnUtil.srt(null);
    }

    public static String ert(){
        return ReturnUtil.ert();
    }

}
