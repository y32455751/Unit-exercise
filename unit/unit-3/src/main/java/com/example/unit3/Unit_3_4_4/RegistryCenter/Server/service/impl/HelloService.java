package com.example.unit3.Unit_3_4_4.RegistryCenter.Server.service.impl;

import com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Server.tool.RpcAnnotation;
import com.example.unit3.Unit_3_4_4.RegistryCenter.Server.service.IHelloService;

@RpcAnnotation(IHelloService.class)
public class HelloService implements IHelloService {
    @Override
    public String sayHello(String name) {
        return "hello" + name;
    }
}
