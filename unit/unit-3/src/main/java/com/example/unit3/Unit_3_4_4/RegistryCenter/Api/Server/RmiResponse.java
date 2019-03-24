package com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Server;

import com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Server.registerCenter.IRegisterCenter;
import com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Server.registerCenter.RegisterCenter;
import com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Server.tool.RpcAnnotation;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 这个类是rpc的一个部署类
 */
public class RmiResponse {

    ExecutorService executorService = Executors.newCachedThreadPool();
    // 注册中心
    IRegisterCenter registerCenter;
    // 节点名称和节点实例
    Map<String,Object> serviceMap = new HashMap<>();
    String serviceAddress;


    public RmiResponse(IRegisterCenter registerCenter, String serviceAddress) {
        this.registerCenter = registerCenter;
        this.serviceAddress = serviceAddress;
    }

    /**
     * 绑定服务
     * @param objects ...表示是通过列表进行传递，传递一个的时候就是一个，传递多个的时候就是数组
     */
    public void bind(Object... objects){
        for(Object service : objects){
            RpcAnnotation annotation = service.getClass().getAnnotation(RpcAnnotation.class);
            String serviceTypeName = annotation.value().getName();
            serviceMap.put(serviceTypeName,service);
        }
    }


    /**
     * 发布一个服务
     * 通过一个ServerSocket来接收客户端发送的请求
     * 把请求扔进弹性线程池，即解决处理逻辑存放位置，也解决了效率问题
     */
    public void publicher(){
        ServerSocket serverSocket = null;
        try{
            String[] arrs = serviceAddress.split(":");
            serverSocket = new ServerSocket(Integer.parseInt(arrs[1]));

            for(String serviceName : serviceMap.keySet()){
                registerCenter.register(serviceName,serviceAddress);
            }
            // 这里使用一个轮询来轮询发送来的请求
            while(true){
                Socket socket = serverSocket.accept();
                // 每当收到一个请求就分配一个线程来对应处理
                executorService.submit(new SubmitHandler(socket,serviceMap));
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
