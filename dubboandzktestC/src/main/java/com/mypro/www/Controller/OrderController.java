package com.mypro.www.Controller;

import com.alibaba.fastjson.JSON;
import com.mypro.userInfoService.service.UserInfoService;
import com.mypro.www.bean.CoachOrder3;
import com.mypro.www.service.CoachOrder3Service;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

//    @Reference
//    CoachOrder3Service coachOrderService;

    @Reference
    UserInfoService userInfoService;

//    @RequestMapping(value = "/getOrderById",method = RequestMethod.POST)
//    public String getOrder(HttpServletRequest request,int id){
//        return JSON.toJSONString(coachOrderService.getOrderById(id));
//    }
//
//    @RequestMapping(value = "/getOrderList",method = RequestMethod.POST)
//    public String getOrderList(HttpServletRequest request){
//        List<CoachOrder3> list = null;
//        list = coachOrderService.getOrderList();
//        return JSON.toJSONString(list);
//    }
//
//    @RequestMapping(value = "/addOrder",method = RequestMethod.POST)
//    public String addOrder(HttpServletRequest request,String name){
//        return JSON.toJSONString(coachOrderService.addOrder(name));
//    }
//
//    @RequestMapping(value = "/delOrder",method = RequestMethod.POST)
//    public String delOrder(HttpServletRequest request,int id){
//        coachOrderService.delOrder(id);
//        return "";
//    }
//
//    @RequestMapping(value = "/getUserInfoList_",method = RequestMethod.POST)
//    public String getUserInfoList_(HttpServletRequest request){
//        return JSON.toJSONString(coachOrderService.getUserInfoList_());
//    }

    @RequestMapping(value = "/getUserInfoList",method = RequestMethod.POST)
    public String getUserInfoList(HttpServletRequest request){
        return JSON.toJSONString(userInfoService.getUserInfoList());
    }

}
