package com.yangdayu.socket.socketgameclient.config;

import com.yangdayu.socket.socketgameclient.entity.GameEntity;
import com.yangdayu.socket.socketgameclient.entity.UserEntity;
import com.yangdayu.socket.socketgameclient.entity.UserInfoEntity;
import com.yangdayu.socket.socketgameclient.unit.StatusValue;

import java.util.ArrayList;
import java.util.List;

public class BaseConfig {

    public static final int SESSION_MAX_INACTIVE_INTERVAL = 1800;

    private static List<UserInfoEntity> list = null;

    private static List<GameEntity> gameList = null;

    public synchronized  static List<GameEntity> getGameInstance(){
        if(gameList == null){
            gameList = new ArrayList<GameEntity>();
        }
        return gameList;
    }

    public static void addGameList(GameEntity game) throws Exception {
        if(!findGameListHasEntity(game)){
            BaseConfig.getGameInstance().add(game);
        }else{
            throw new Exception("有玩家已经在游戏中");
        }
    }

    public static void updateGamelist(GameEntity game){
        List<GameEntity> list = BaseConfig.getGameInstance();
        for(int i=0;i<list.size();i++){
            if(list.get(i).getGameId().equals(game.getGameId())){
                list.set(i,game);
            }
        }
    }

    public static boolean findGameListHasEntity(GameEntity game) throws Exception {
        if(findGameListHasEntity_(game)!=null){
            return true;
        }
        return false;
    }

    public static void removeGameById(String gameId){
        List<GameEntity> list = BaseConfig.getGameInstance();
        for(int i=0;i<list.size();i++){
            if(list.get(i).getGameId().equals(gameId)){
                list.remove(i);
            }
        }
    }

    public static GameEntity finGameByGameId(String gameId){
        List<GameEntity> list = BaseConfig.getGameInstance();
        for(GameEntity item : list){
            if(item.getGameId().equals(gameId)){
                return item;
            }
        }
        return null;
    }

    public static GameEntity findGameListHasEntity_(GameEntity game){
        List<GameEntity> list = BaseConfig.getGameInstance();
        for(GameEntity item : list){
            if(item.getUser_one().getObjuid().equals(game.getUser_one().getObjuid()) || item.getUser_two().getObjuid().equals(game.getUser_two().getObjuid())){
                return item;
            }
        }
        return null;
    }

    public synchronized static List<UserInfoEntity> getInstance(){
        if(list == null){
            list = new ArrayList<UserInfoEntity>();
        }
        return list;
    }

    public static void addList(UserInfoEntity entity){
        if(!findListHasEntity(entity)){
            BaseConfig.getInstance().add(entity);
        }
    }

    public static void resetUserList(String objuid){
        List<UserInfoEntity> list = BaseConfig.getInstance();
        for(int i=0;i<list.size();i++){
            if(list.get(i).getObjuid().equals(objuid)){
                list.get(i).setInitUserStatus();
            }
        }
    }

    public static boolean findListHasEntity(UserInfoEntity entity){
        List<UserInfoEntity> list = BaseConfig.getInstance();
        for(UserInfoEntity item : list){
            if(item.getObjuid().equals(entity.getObjuid())){
                return true;
            }
        }
        return false;
    }

    /**
     * 修改双方对战状态
     * @param entity
     */
    public static void changeUserGameStateAll(GameEntity entity,int state){
        List<UserInfoEntity> list = BaseConfig.getInstance();
        for(UserInfoEntity item : list){
            if(item.getObjuid().equals(entity.getUser_one().getObjuid())){
                item.getStatus().setGameStatus(state);
            }
            if(item.getObjuid().equals(entity.getUser_two().getObjuid())){
                item.getStatus().setGameStatus(state);
            }
        }
    }

    public static UserInfoEntity changeUserGameState(String objuid,int state){
        List<UserInfoEntity> list = BaseConfig.getInstance();
        UserInfoEntity user = null;
        for(UserInfoEntity item : list){
            if(item.getObjuid().equals(objuid)){
                user = new UserInfoEntity();
                user = item;
                item.getStatus().setGameStatus(state);
            }
        }
        return user;
    }

}
