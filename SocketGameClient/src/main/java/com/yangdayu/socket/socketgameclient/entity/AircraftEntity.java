package com.yangdayu.socket.socketgameclient.entity;

import com.yangdayu.socket.socketgameclient.unit.StatusValue;
import com.yangdayu.socket.socketgameclient.unit.YUUID;

/**
 * 飞机类
 */
public class AircraftEntity {

    /**
     * 飞机编号
     */
    private String aircraftID;
    /**
     * 飞机坐标数组
     */
    private CoordinateEntity coordinate;
    /**
     * 飞机状态
     * 存活 -> survival 1
     * 死亡 -> death 0
     */
    private int aircraftStatus;
    /**
     * 飞机击中数
     */
    private int hurtNum;

    public String getAircraftID() {
        return aircraftID;
    }

    public void setAircraftID(String aircraftID) {
        this.aircraftID = aircraftID;
    }

    public CoordinateEntity getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(CoordinateEntity coordinate) {
        this.coordinate = coordinate;
    }

    public int getAircraftStatus() {
        return aircraftStatus;
    }

    public void setAircraftStatus(int aircraftStatus) {
        this.aircraftStatus = aircraftStatus;
    }

    public int getHurtNum() {
        return hurtNum;
    }

    public void setHurtNum(int hurtNum) {
        this.hurtNum = hurtNum;
    }

    public AircraftEntity(String id,CoordinateEntity coordinate) {
        this.aircraftID = id;
        this.coordinate = coordinate;
        this.aircraftStatus = StatusValue.SURVIVAL;
        this.hurtNum = 0;
    }
}
