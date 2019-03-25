package com.example.designpattern.abstractfactory.entity;

public class Cpu {

    private int needleNum = 0;
    private String name = "";

    public Cpu(int needleNum,String name){
        this.needleNum = needleNum;
        this.name = name;
    }

    public int getNeedleNum() {
        return needleNum;
    }

    public void setNeedleNum(int needleNum) {
        this.needleNum = needleNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
