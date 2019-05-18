package com.yangdayu.socket.socketgameclient.entity;

import com.yangdayu.socket.socketgameclient.unit.YUUID;

import java.util.Date;

/**
 * 对局信息
 */
public class GameEntity {

    /**
     * 对局ID
     */
    private String gameId;
    /**
     * 玩家1
     */
    private UserInfoEntity user_one;
    /**
     * 玩家2
     */
    private UserInfoEntity user_two;
    /**
     * 开始时间
     */
    private Date startTime;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public UserInfoEntity getUser_one() {
        return user_one;
    }

    public void setUser_one(UserInfoEntity user_one) {
        this.user_one = user_one;
    }

    public UserInfoEntity getUser_two() {
        return user_two;
    }

    public void setUser_two(UserInfoEntity user_two) {
        this.user_two = user_two;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public GameEntity(){

    }

    public GameEntity(UserInfoEntity user_one, UserInfoEntity user_two) {
        this.gameId = YUUID.getUUID();
        UserInfoEntity user = new UserInfoEntity();
        user = user_one;
        user.setInitUserStatus();
        this.user_one = user;
        user = new UserInfoEntity();
        user = user_two;
        user.setInitUserStatus();
        this.user_two = user;
        this.startTime = new Date();
    }
}
