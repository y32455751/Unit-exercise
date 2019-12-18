package com.example.demo.test;

import com.example.demo.util.ReturnUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class testController {

    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public String test(HttpServletRequest request,TestEntity testEntity){
        System.out.println("111");
        return ReturnUtil.srt(testEntity);
    }

}
