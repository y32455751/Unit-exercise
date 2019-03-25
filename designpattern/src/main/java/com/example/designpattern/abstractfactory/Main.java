package com.example.designpattern.abstractfactory;

import com.example.designpattern.abstractfactory.engineer.Engineer;
import com.example.designpattern.abstractfactory.entity.Computer;
import com.example.designpattern.abstractfactory.eum.FactoryType;

/**
 * 这是一个抽象工厂模式的模板
 * 抽象工厂模式的目的：当我们想获得一个复杂的对象，这个对象可能还需要其他对象来组成，而我们并不清楚需要怎么去建造他，
 * 届时抽象工厂模式就可以通过制造的方式，通过条件来制造出一个对象提供使用。
 * 抽象工厂在建造的时候，只需要知道极少的条件，或者说是只需要知道足够制造对象的条件。
 * 比如在选购电脑的时候，我们只需要提供一些我们想要的电脑的一些信息，例如：
 * CPU型号、想运行什么样的软件、使用场景、外观等。
 * 工程师就会通过这些信息来制造一台符合条件的电脑。
 */
public class Main {

    public static void main(String[] args) {
        // 工程师
        Engineer engineer = new Engineer();
        // 工程师制造电脑
        Computer computer = engineer.buildComputer(FactoryType.Amd);//Amd，需求者提供的条件信息
        computer.showOption();
    }

}
