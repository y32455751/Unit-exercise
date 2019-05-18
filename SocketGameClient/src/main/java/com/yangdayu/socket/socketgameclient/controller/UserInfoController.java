package com.yangdayu.socket.socketgameclient.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yangdayu.socket.socketgameclient.config.BaseConfig;
import com.yangdayu.socket.socketgameclient.entity.UserInfoEntity;
import com.yangdayu.socket.socketgameclient.redis.Redis;
import com.yangdayu.socket.socketgameclient.serializer.SerializerUtil;
import com.yangdayu.socket.socketgameclient.service.UserInfoEntityService;
import com.yangdayu.socket.socketgameclient.unit.ReturnUnit;
import com.yangdayu.socket.socketgameclient.unit.StatusValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.rmi.server.ExportException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/User")
public class UserInfoController {

    @Autowired
    UserInfoEntityService userService;
    @Autowired
    StringRedisTemplate redis;

    @RequestMapping("/login")
    public String login(UserInfoEntity entity, HttpServletRequest request){
        try {
            entity = userService.login(entity);
            if (null != entity) {
                entity.setInitUserStatus();
//                HttpSession session = request.getSession();
//                session.setAttribute(entity.getObjuid(),entity);
//                session.setMaxInactiveInterval(BaseConfig.SESSION_MAX_INACTIVE_INTERVAL);
                BaseConfig.addList(entity);
                return ReturnUnit.getReturnSuccess(entity);
            } else {
                return ReturnUnit.getReturnError();
            }
        }catch(Exception e){
            e.printStackTrace();
            return ReturnUnit.getReturnError();
        }
    }

    @RequestMapping("/register")
    public String register(UserInfoEntity entity){
        try{
            UserInfoEntity newEntity = new UserInfoEntity();
            newEntity = userService.findByLoginName(entity);
            if(null != newEntity && !"".equals(newEntity.getObjuid())){
                return ReturnUnit.getReturnError("账号已经存在");
            }else{
                entity = userService.addUserinfoentity(entity);
                if(null != entity && !"".equals(entity.getObjuid())){
                    return ReturnUnit.getReturnSuccess("注册成功",entity);
                }else{
                    return ReturnUnit.getReturnError("注册失败，请联系管理员");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            return ReturnUnit.getReturnError();
        }
    }

}
