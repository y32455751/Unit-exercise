package com.example.designpattern.abstractfactory.factory;

import com.example.designpattern.abstractfactory.entity.Cpu;
import com.example.designpattern.abstractfactory.entity.MainBoard;

/**
 * 这个工厂类继承了抽象工厂，因为所有的工厂都必须接受抽象工厂的规定
 */
public class AMDFactory extends AbstrackFactory {

    // Amd的Cpu和主板的默认针脚数
    private int needleNum = 1166;
    // 为生产的产品进行名称喷漆
    private String name = "AMD";

    public AMDFactory(int needleNum){
        this.needleNum = needleNum;
    }
    public AMDFactory(){

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
