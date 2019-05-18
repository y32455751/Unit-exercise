package com.yangdayu.socket.socketgameclient.entity;

import com.yangdayu.socket.socketgameclient.unit.StatusValue;

import java.util.Date;
import java.util.List;

/**
 * 玩家信息
 */
public class UserEntity {

    /**
     * 对战状态
     */
    private int gameStatus;
    /**
     * 剩余飞机数量
     */
    private int aricraftNum;
    /**
     * 剩余飞机集合
     */
    private List<AircraftEntity> aircraftEntityList;

    public int getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(int gameStatus) {
        this.gameStatus = gameStatus;
    }

    public int getAricraftNum() {
        return aricraftNum;
    }

    public void setAricraftNum(int aricraftNum) {
        this.aricraftNum = aricraftNum;
    }

    public List<AircraftEntity> getAircraftEntityList() {
        return aircraftEntityList;
    }

    public void setAircraftEntityList(List<AircraftEntity> aircraftEntityList) {
        this.aircraftEntityList = aircraftEntityList;
    }

    public UserEntity(int aricraftNum, List<AircraftEntity> aircraftEntityList) {
        this.gameStatus = StatusValue.NOT_IN_GAME;
        this.aricraftNum = aricraftNum;
        this.aircraftEntityList = aircraftEntityList;
    }

    public UserEntity() {
    }
}
