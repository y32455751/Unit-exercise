package com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Client;

import com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Client.discover.IServiceDiscover;
import com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Server.registerCenter.IRegisterCenter;

import java.lang.reflect.Proxy;

public class RmiClientProxy {

    // 发现服务的类
    IServiceDiscover serviceDiscover;

    public RmiClientProxy(IServiceDiscover serviceDiscover) {
        this.serviceDiscover = serviceDiscover;
    }

    /**
     * 通过映射类，映射一个方法，
     * @param interfaceCls 要被映射的class
     * @param <T> 泛型类
     * @return 代理服务的类
     */
    public <T> T proxyMethod(Class<T> interfaceCls){
        // 代理工具类，新建一个代理实例，（代理类，接口列表？，执行代理处理逻辑的类）
        return (T) Proxy.newProxyInstance(interfaceCls.getClassLoader(),new Class[]{interfaceCls},new RemoteInvocationHandler(serviceDiscover));
    }


}
