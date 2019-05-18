package com.yangdayu.socket.socketgameclient.unit;

import com.yangdayu.socket.socketgameclient.config.BaseConfig;
import com.yangdayu.socket.socketgameclient.entity.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameUtil {

    public static int getMathNum(){
        return (int)(Math.random()*6+1);
    }

    public static Map<String,Object> determine(UserInfoEntity user, String coordinate, String beAttack, String attacker, String gameId){
//        user = temp.getUser_one().getStatus();
        Map<String,Object> map = new HashMap<String,Object>();
        GameEntity temp = BaseConfig.finGameByGameId(gameId);
        List<AircraftEntity> list =  user.getStatus().getAircraftEntityList();
        for(AircraftEntity item : list){
            CoordinateEntity coo = item.getCoordinate();
            if(coo.getIsHead().equals(coordinate)){
                if(temp.getUser_one().getUsername().equals(user.getUsername())){
                    GameUtil.setAircraftDeath(temp.getUser_one(),item);
                }else if(temp.getUser_two().getUsername().equals(user.getUsername())){
                    GameUtil.setAircraftDeath(temp.getUser_two(),item);
                }
                map.put("gameInfo",temp);
                map.put("attackReturn",new AttackReturnEntity(2,coo.getCoordinate(),beAttack,attacker));
                return map;
            }else{
                String[] s = coo.getCoordinate();
                for(String c : s){
                    if(c.equals(coordinate)){
                        boolean isDeath = false;
                        if(temp.getUser_one().getUsername().equals(user.getUsername())){
                            isDeath = GameUtil.setAircraftState(temp.getUser_one(),item);
                        }else if(temp.getUser_two().getUsername().equals(user.getUsername())){
                            isDeath = GameUtil.setAircraftState(temp.getUser_two(),item);
                        }
                        map.put("gameInfo",temp);
                        if(isDeath) {
                            map.put("attackReturn", new AttackReturnEntity(2, coo.getCoordinate(), beAttack, attacker));
                        }else {
                            map.put("attackReturn", new AttackReturnEntity(1, new String[]{c}, beAttack, attacker));
                        }
                        return map;
                    }
                }
            }
        }
        map.put("gameInfo",temp);
        map.put("attackReturn",new AttackReturnEntity(0,new String[]{coordinate},beAttack,attacker));
        return map;
    }

    private static void setAircraftDeath(UserInfoEntity user,AircraftEntity item){
        user.getStatus().setAricraftNum(user.getStatus().getAricraftNum() - 1);
        for(AircraftEntity a : user.getStatus().getAircraftEntityList()){
            if(a.getAircraftID().equals(item.getAircraftID())){
                a.setAircraftStatus(0);
                a.setHurtNum(10);
            }
        }
    }

    private static boolean setAircraftState(UserInfoEntity user,AircraftEntity item){
        for(AircraftEntity a : user.getStatus().getAircraftEntityList()){
            if(a.getAircraftID().equals(item.getAircraftID())){
                a.setHurtNum(a.getHurtNum()+1);
                if(a.getHurtNum()>=6){
                    setAircraftDeath(user,a);
                    return true;
                }
            }
        }
        return false;
    }


}
