package com.yangdayu.socket.socketgameclient.controller;

import com.yangdayu.socket.socketgameclient.config.BaseConfig;
import com.yangdayu.socket.socketgameclient.entity.*;
import com.yangdayu.socket.socketgameclient.service.UserInfoEntityService;
import com.yangdayu.socket.socketgameclient.socket.entity.MessageEntity;
import com.yangdayu.socket.socketgameclient.unit.GameUtil;
import com.yangdayu.socket.socketgameclient.unit.ReturnUnit;
import com.yangdayu.socket.socketgameclient.unit.StatusValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class WsController {

    @Autowired
    UserInfoEntityService userService;

    @MessageMapping("/welcome")//1
    @SendTo("/topic/getResponse")//2
    public MessageEntity say(MessageEntity message) throws Exception {
        return message;
    }

    @MessageMapping("/init")//1
    @SendTo("/topic/initGetResponse")//2
    public GameEntity init(GameEntity game) throws Exception {
        GameEntity temp = BaseConfig.findGameListHasEntity_(game);
        if(temp==null)
            game = new GameEntity(userService.findByUserName(game.getUser_one()),userService.findByUserName(game.getUser_two()));
        else
            game = temp;
        try{
            BaseConfig.addGameList(game);
            BaseConfig.changeUserGameStateAll(game, StatusValue.IN_GAME);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return game;
    }
    @MessageMapping("/start")//1
    @SendTo("/topic/startGetResponse")//2
    public String start(String gameId) throws Exception {
        GameEntity temp = BaseConfig.finGameByGameId(gameId);
        int user_one = 0;
        int user_two = 0;
        do{
            user_one = GameUtil.getMathNum();
            user_two = GameUtil.getMathNum();
        }while(user_one == user_two);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("user1_num",temp.getUser_one().getUsername() + "的点数为:" + user_one);
        map.put("user2_num",temp.getUser_two().getUsername() + "的点数为:" + user_two);
        map.put("gameId",gameId);
        if(user_one > user_two){
            map.put("data",temp.getUser_one());
            map.put("beAttack",temp.getUser_two().getUsername());
            return ReturnUnit.getReturnSuccess(map);
        }else{
            map.put("data",temp.getUser_two());
            map.put("beAttack",temp.getUser_one().getUsername());
            return ReturnUnit.getReturnSuccess(map);
        }
    }
    @MessageMapping("/attack")//1
    @SendTo("/topic/attackGetResponse")//2
    public String attack(AttackEntity attack) throws Exception {
        GameEntity temp = BaseConfig.finGameByGameId(attack.getGameId());
        if(attack.getBeAttacker().equals(temp.getUser_one().getUsername())){
            return ReturnUnit.getReturnSuccess(GameUtil.determine(temp.getUser_one(),attack.getCoordinate(),attack.getBeAttacker(),attack.getAttacker(),attack.getGameId()));
        }
        return ReturnUnit.getReturnSuccess(GameUtil.determine(temp.getUser_two(),attack.getCoordinate(),attack.getBeAttacker(),attack.getAttacker(),attack.getGameId()));
    }
}
