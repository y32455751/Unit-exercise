package com.example.unit3.Unit_3_2_1.TCPSocket.Thread;

import com.example.unit3.Unit_3_2_1.TCPSocket.Entity.ClientUser;
import com.example.unit3.Unit_3_2_1.TCPSocket.Entity.ServerEntity;
import com.example.unit3.Unit_3_2_1.TCPSocket.Util.SocketUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 客户端轮询线程
 *
 * @remark 在创建线程的时候，需要先设定客户端实体类中的昵称，然后创建一个socket工具类
 */
public class Demand implements Runnable{

    public SocketUtil util;
    public boolean exit = false;

    public Demand(ClientUser user){
        util = new SocketUtil(user);
    }

    /**
     * 线程执行方法
     * @remark 每个客户端启动后，启动一个线程，线程轮询接收消息并且输出
     */
    @Override
    public void run() {
        String msg = "";
        while (!exit){
            msg = util.receiveFromClient();
            System.out.println(msg);
        }
    }

    public SocketUtil getUtil() {
        return util;
    }

    public void setUtil(SocketUtil util) {
        this.util = util;
    }
}