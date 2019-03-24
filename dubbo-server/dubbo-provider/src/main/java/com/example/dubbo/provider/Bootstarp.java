package com.example.dubbo.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class Bootstarp {

    public static void main(String[] args) throws IOException {
        // 加载配置文件
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("dubbo-server.xml");
        // 启动服务配置
        context.start();
        // 阻塞服务等待连接
        System.in.read();
    }
}
