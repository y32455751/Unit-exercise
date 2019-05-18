package com.yangdayu.socket.socketgameclient.entity;

/**
 * 攻击信息返回类
 */
public class AttackReturnEntity {

    /**
     * 损伤状态 0,未击中 1，击中 2，击毁
     */
    private int hurtStatus;
    /**
     * 击毁坐标数组
     */
    private String[] hurtCoordinate;

    private String beAttack;

    private String attacker;

    public int getHurtStatus() {
        return hurtStatus;
    }

    public void setHurtStatus(int hurtStatus) {
        this.hurtStatus = hurtStatus;
    }

    public String[] getHurtCoordinate() {
        return hurtCoordinate;
    }

    public void setHurtCoordinate(String[] hurtCoordinate) {
        this.hurtCoordinate = hurtCoordinate;
    }

    public String getBeAttack() {
        return beAttack;
    }

    public void setBeAttack(String beAttack) {
        this.beAttack = beAttack;
    }

    public String getAttacker() {
        return attacker;
    }

    public void setAttacker(String attacker) {
        this.attacker = attacker;
    }

    public AttackReturnEntity(int hurtStatus, String[] hurtCoordinate, String beAttack, String attacker) {
        this.hurtStatus = hurtStatus;
        this.hurtCoordinate = hurtCoordinate;
        this.beAttack = beAttack;
        this.attacker = attacker;
    }
}
