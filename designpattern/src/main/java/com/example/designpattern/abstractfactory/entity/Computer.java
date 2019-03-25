package com.example.designpattern.abstractfactory.entity;

public class Computer {

    private Cpu cpu;

    private MainBoard mainBoard;

    public Computer(Cpu cpu,MainBoard mainBoard){
        this.cpu = cpu;
        this.mainBoard = mainBoard;
    }

    public void showOption(){
        System.out.println("此计算机配置为:");
        System.out.println("CPU:"+cpu.getName()+",针脚数:"+cpu.getNeedleNum());
        System.out.println("主板:"+mainBoard.getName()+",针脚数:"+mainBoard.getNeedleNum());
    }

}
