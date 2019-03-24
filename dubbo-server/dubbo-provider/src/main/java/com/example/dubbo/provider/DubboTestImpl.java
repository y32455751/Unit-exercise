package com.example.dubbo.provider;

import com.example.dubbo.api.IDubboTest;

public class DubboTestImpl implements IDubboTest {
    public String sayHello(String name) {
        return "hello " + name;
    }
}
