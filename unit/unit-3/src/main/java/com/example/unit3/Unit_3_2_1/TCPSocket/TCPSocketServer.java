package com.example.unit3.Unit_3_2_1.TCPSocket;

import com.example.unit3.Unit_3_2_1.TCPSocket.Thread.Service;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * 服务器主要代码
 */
public class TCPSocketServer {
    static Logger logger = Logger.getLogger("TCPSocketServer");

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080); //启动服务器
            logger.info("服务器启动成功......");
            while(true){
                Socket socket = serverSocket.accept();  // 轮询等待客户端加入
                Service service = new Service(socket);  // 每当客户端加入，分配给客户端一个线程
                Thread t = new Thread(service); // 放入线程容器
                t.start();  // 开始线程
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

























