package com.example.unit3.Unit_3_3_1.RPC.rmi.Api.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 这个类是rpc的一个部署类
 */
public class RmiResponse {

    ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * 发布一个服务
     * 通过一个ServerSocket来接收客户端发送的请求
     * 把请求扔进弹性线程池，即解决处理逻辑存放位置，也解决了效率问题
     * @param service 要被发布的服务
     * @param port ServerSocket启动的端口
     */
    public void publicher(final Object service,int port){
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(port);
            // 这里使用一个轮询来轮询发送来的请求
            while(true){
                Socket socket = serverSocket.accept();
                // 每当收到一个请求就分配一个线程来对应处理
                executorService.submit(new SubmitHandler(socket,service));
            }
        } catch (Exception e) {
            throw new RuntimeException("发布服务失败",e);
        } finally {
            if(serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
