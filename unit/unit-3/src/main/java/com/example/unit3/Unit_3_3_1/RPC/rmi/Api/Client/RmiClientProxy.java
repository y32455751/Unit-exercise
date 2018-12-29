package com.example.unit3.Unit_3_3_1.RPC.rmi.Api.Client;

import java.lang.reflect.Proxy;

public class RmiClientProxy {


    /**
     * 通过映射类，映射一个方法，
     * @param interfaceCls 要被映射的class
     * @param host 服务地址
     * @param port 服务端口号
     * @param <T> 泛型类
     * @return 代理服务的类
     */
    public <T> T proxyMethod(Class<T> interfaceCls,final String host,final int port){
        // 代理工具类，新建一个代理实例，（代理类，接口列表？，执行代理处理逻辑的类）
        return (T) Proxy.newProxyInstance(interfaceCls.getClassLoader(),new Class[]{interfaceCls},new RemoteInvocationHandler(host,port));
    }


}
