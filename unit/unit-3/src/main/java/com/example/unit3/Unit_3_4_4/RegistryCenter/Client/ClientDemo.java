package com.example.unit3.Unit_3_4_4.RegistryCenter.Client;

import com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Client.RmiClientProxy;
import com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Client.discover.IServiceDiscover;
import com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Client.discover.impl.ServiceDiscover;
import com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Public.ZKConfig;
import com.example.unit3.Unit_3_4_4.RegistryCenter.Server.service.IHelloService;
import com.example.unit3.Unit_3_4_4.RegistryCenter.Server.service.ITellPhoneService;

public class ClientDemo {

    public static void main(String[] args) {
        IServiceDiscover discover = new ServiceDiscover(ZKConfig.ZK_CONNECTION_STR);
        RmiClientProxy proxy = new RmiClientProxy(discover);
        IHelloService helloService = null;
        ITellPhoneService tellPhoneService = null;
        // 远程代理一个方法
        helloService = proxy.proxyMethod(IHelloService.class);
        tellPhoneService = proxy.proxyMethod(ITellPhoneService.class);
        if(helloService!=null){
            System.out.println(helloService.sayHello("杨大宇"));
        }
        if(tellPhoneService!=null){
            System.out.println(tellPhoneService.tell("杨大宇"));
        }
    }

}
