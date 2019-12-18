package com.example.demo.util;

public enum ResultCode {

    SUCCESS(200,"操作成功"),
    ERROR(999,"操作失败:未知错误,请联系管理员")

    ;

    private Integer code;
    private String msg;


    ResultCode(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer code(){
        return this.code;
    }

    public String msg(){
        return this.msg;
    }
}
