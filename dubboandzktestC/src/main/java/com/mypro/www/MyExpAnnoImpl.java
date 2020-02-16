package com.mypro.www;


import org.apache.dubbo.rpc.RpcException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

//@ControllerAdvice(basePackages = "com.mypro.www.Controller")
public class MyExpAnnoImpl {

    @ExceptionHandler(Exception.class)
    @ResponseBody//返回json串
    public ReturnUtil errorResoult(Exception e) {
        Map<String, Object> errorMap = new HashMap<String, Object>();
        if(e instanceof RpcException){
            return new ReturnUtil(ResultCode.TIME_OUT);
        }
        return new ReturnUtil(ResultCode.ERROR);
    }

}
