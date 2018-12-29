package com.example.unit3.Unit_3_3_1.RPC.rmi.Server;

import com.example.unit3.Unit_3_3_1.RPC.rmi.Api.Server.RmiResponse;
import com.example.unit3.Unit_3_3_1.RPC.rmi.Server.service.IHelloService;
import com.example.unit3.Unit_3_3_1.RPC.rmi.Server.service.impl.HelloService;

/**
 * server端的一个启动类
 */
public class ServerDemo {

    public static void main(String[] args) {
        IHelloService helloService = new HelloService();
        RmiResponse response = new RmiResponse();
        // 发布服务的方法
        response.publicher(helloService,8888);
    }
}
