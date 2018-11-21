package com.example.unit3.Unit_3_2_1.TCPSocket;

import com.example.unit3.Unit_3_2_1.TCPSocket.Entity.ClientUser;
import com.example.unit3.Unit_3_2_1.TCPSocket.Entity.ServerEntity;
import com.example.unit3.Unit_3_2_1.TCPSocket.Thread.Demand;
import com.example.unit3.Unit_3_2_1.TCPSocket.Util.SocketUtil;

import java.io.*;
import java.net.Socket;

/**
 * 客户端主要代码
 */
public class TCPSocketClient {

    public static void main(String[] args) {
        BufferedReader reader;
        ClientUser user;
        SocketUtil util;
        String msg;
        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("请输入昵称");
            user = new ClientUser();
            user.setUserName(reader.readLine());
            Demand demand = new Demand(user);   // 创建一个轮询接收消息的线程类
            util = demand.getUtil();    // 获得线程创建的工具类
            Thread t = new Thread(demand);  // 放到线程容器中
            t.start();  // 启动线程
            while(true){    // 发送消息的轮询
                msg = reader.readLine();
                util.sendFromClient(msg,user.getUserName());
                if(msg.toUpperCase().equals(ServerEntity.EXIT_ORDER)){
                    demand.exit = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}