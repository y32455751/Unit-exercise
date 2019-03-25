package com.example.designpattern.abstractfactory.factory;

import com.example.designpattern.abstractfactory.entity.Cpu;
import com.example.designpattern.abstractfactory.entity.MainBoard;

public class InterFactory extends AbstrackFactory {

    private int needleNum = 775;
    private String name = "Inter";

    public InterFactory(int needleNum){
        this.needleNum = needleNum;
    }

    public InterFactory(){

    }

    @Override
    public Cpu createCpu() {
        return new Cpu(this.needleNum,this.name);
    }

    @Override
    public MainBoard createMainBoard() {
        return new MainBoard(this.needleNum,this.name);
    }
}
