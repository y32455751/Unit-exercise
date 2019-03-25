package com.example.designpattern.abstractfactory.engineer;

import com.example.designpattern.abstractfactory.entity.Computer;
import com.example.designpattern.abstractfactory.entity.Cpu;
import com.example.designpattern.abstractfactory.entity.MainBoard;
import com.example.designpattern.abstractfactory.eum.FactoryType;
import com.example.designpattern.abstractfactory.factory.AMDFactory;
import com.example.designpattern.abstractfactory.factory.AbstrackFactory;
import com.example.designpattern.abstractfactory.factory.InterFactory;

/**
 * 工程师角色，他来对需求者提供服务
 */
public class Engineer {

    // 制作好的cpu
    private Cpu cpu;
    // 制作好的主板
    private MainBoard mainBoard;

    /**
     * 通过需求者的条件来进行选购或制造
     * @param type 需求者的条件
     */
    private void buyParts(FactoryType type){
        AbstrackFactory af = null;
        if(type.equals(FactoryType.Inter)){ // 当需求者条件为Inter时
            // 这里调用Inter的制造工厂进行制造
            af = new InterFactory(111);
            this.cpu = af.createCpu();
            this.mainBoard = af.createMainBoard();
        }else if(type.equals(FactoryType.Amd)){ // 当需求者条件为Amd时
            // 这里调用Amd的制造工厂进行制造
            af = new AMDFactory();
            this.cpu = af.createCpu();
            this.mainBoard = af.createMainBoard();
        }
    }

    /**
     * 组合一台电脑
     * @return
     */
    private Computer createComputer(){
        //组合并且想需求者返回这台电脑
        return new Computer(this.cpu,this.mainBoard);
    }

    /**
     * 工程师组装电脑的过程
     * @param type 需求者提供的条件
     * @return 需求者所需要的电脑
     */
    public Computer buildComputer(FactoryType type){
        buyParts(type); // 采购部件
        return createComputer(); // 创建电脑并且返回
    }

}
