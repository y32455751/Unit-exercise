package com.yangdayu.socket.socketgameclient.entity;

/**
 * 攻击信息类
 */
public class AttackEntity {

    /**
     * 攻击坐标
     */
    private String coordinate;
    private String attacker;
    private String beAttacker;
    private String gameId;

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getAttacker() {
        return attacker;
    }

    public void setAttacker(String attacker) {
        this.attacker = attacker;
    }

    public String getBeAttacker() {
        return beAttacker;
    }

    public void setBeAttacker(String beAttacker) {
        this.beAttacker = beAttacker;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
