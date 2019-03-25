package com.example.designpattern.abstractfactory.factory;


import com.example.designpattern.abstractfactory.entity.Cpu;
import com.example.designpattern.abstractfactory.entity.MainBoard;

/**
 * 抽象工厂类，
 * 这个类规定了所有工厂的生产方式，和生产的方法
 * 这里表示，所有工厂都必须生产Cpu和MainBoard
 */
public abstract class AbstrackFactory {

    public abstract Cpu createCpu();

    public abstract MainBoard createMainBoard();
}
