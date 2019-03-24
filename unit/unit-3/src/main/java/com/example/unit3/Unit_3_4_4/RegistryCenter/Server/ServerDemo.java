package com.example.unit3.Unit_3_4_4.RegistryCenter.Server;

import com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Server.RmiResponse;
import com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Server.registerCenter.IRegisterCenter;
import com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Server.registerCenter.RegisterCenter;
import com.example.unit3.Unit_3_4_4.RegistryCenter.Server.service.IHelloService;
import com.example.unit3.Unit_3_4_4.RegistryCenter.Server.service.ITellPhoneService;
import com.example.unit3.Unit_3_4_4.RegistryCenter.Server.service.impl.HelloService;
import com.example.unit3.Unit_3_4_4.RegistryCenter.Server.service.impl.TellPhoneService;


/**
 * server端的一个启动类
 */
public class ServerDemo {


    public static void main(String[] args) {
        IHelloService helloService = new HelloService();
        ITellPhoneService tellPhoneService = new TellPhoneService();
        IRegisterCenter registerCenter = new RegisterCenter();
        RmiResponse response = new RmiResponse(registerCenter,"127.0.0.1:8888");
        response.bind(helloService,tellPhoneService);
        // 发布服务的方法
        response.publicher();
    }

}
