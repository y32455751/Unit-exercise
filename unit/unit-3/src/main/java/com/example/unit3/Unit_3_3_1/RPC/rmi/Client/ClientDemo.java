package com.example.unit3.Unit_3_3_1.RPC.rmi.Client;

import com.example.unit3.Unit_3_3_1.RPC.rmi.Api.Client.RmiClientProxy;
import com.example.unit3.Unit_3_3_1.RPC.rmi.Server.service.IHelloService;

public class ClientDemo {

    public static void main(String[] args) {
        RmiClientProxy proxy = new RmiClientProxy();
        IHelloService helloService = null;
        // 远程代理一个方法
        helloService = proxy.proxyMethod(IHelloService.class,"127.0.0.1",8888);
        System.out.println(helloService.sayHello("杨大宇"));
    }

}
