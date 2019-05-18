package com.yangdayu.socket.socketgameclient.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.yangdayu.socket.socketgameclient.config.BaseConfig;
import com.yangdayu.socket.socketgameclient.entity.*;
import com.yangdayu.socket.socketgameclient.redis.Redis;
import com.yangdayu.socket.socketgameclient.service.UserInfoEntityService;
import com.yangdayu.socket.socketgameclient.unit.ReturnUnit;
import com.yangdayu.socket.socketgameclient.unit.StatusValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gameController")
public class GameController {

    @Autowired
    UserInfoEntityService userService;

    @RequestMapping("/loadingUserList")
    public String loadingUserList(HttpServletRequest request,String objuid){
//        HttpSession session = request.getSession();
//        UserInfoEntity entity = new UserInfoEntity();
//        entity = (UserInfoEntity) session.getAttribute(objuid);
        return ReturnUnit.getReturnSuccess(BaseConfig.getInstance());
    }

    @RequestMapping("/complete")
    public String complete(HttpServletRequest request, String gameId, String username, String coordinate,String isHead,String aId){
        List<GameEntity> list = BaseConfig.getGameInstance();
        UserInfoEntity user = new UserInfoEntity();
        String objuid = userService.findByUserName(username).getObjuid();
        user = BaseConfig.changeUserGameState(objuid,StatusValue.COMPLETE);
        String[] c_1 = coordinate.split("#");
        String[] h_1 = isHead.split("#");
        String[] d_1 = aId.split("#");
        List<CoordinateEntity> cList = new ArrayList<CoordinateEntity>();
        if(user.getStatus().getAircraftEntityList()!=null)
            user.getStatus().getAircraftEntityList().clear();
        for(int i=0;i<c_1.length;i++){
            user.getStatus().getAircraftEntityList().add(new AircraftEntity(d_1[i],new CoordinateEntity(c_1[i].split(";"),h_1[i])));
        }
        for(int i=0;i<list.size();i++){
            if(list == null)
                continue;
            if(list.get(i).getGameId().equals(gameId)){
                if(list.get(i).getUser_one().getObjuid().equals(objuid)){
                    list.get(i).setUser_one(user);
                } else if (list.get(i).getUser_two().getObjuid().equals(objuid)) {
                    list.get(i).setUser_two(user);
                }
                return ReturnUnit.getReturnSuccess(list.get(i));
            }
        }
        return ReturnUnit.getReturnError("没有找到该局游戏");
    }

    @RequestMapping("/gameover")
    public String gameOver(String objuid1,String objuid2,String gameId){
        BaseConfig.resetUserList(objuid1);
        BaseConfig.resetUserList(objuid2);
        BaseConfig.removeGameById(gameId);
        return "";
    }

}
