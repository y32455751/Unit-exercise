package com.example.unit3.Unit_3_3_1.RPC.rmi.Server.service.impl;

import com.example.unit3.Unit_3_3_1.RPC.rmi.Server.service.IHelloService;

public class HelloService implements IHelloService {
    @Override
    public String sayHello(String name) {
        return "hello" + name;
    }
}
