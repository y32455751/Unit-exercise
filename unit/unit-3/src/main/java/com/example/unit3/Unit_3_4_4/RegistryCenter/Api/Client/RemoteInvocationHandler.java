package com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Client;

import com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Client.discover.IServiceDiscover;
import com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Public.RmiRequest;
import com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Server.registerCenter.IRegisterCenter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 执行代理处理逻辑的类
 */
public class RemoteInvocationHandler implements InvocationHandler {


    IServiceDiscover serviceDiscover;

    public RemoteInvocationHandler(IServiceDiscover serviceDiscover) {
        this.serviceDiscover = serviceDiscover;
    }

    /**
     * 处理代理逻辑的方法
     * @param proxy 代理的类
     * @param method 要代理的方法
     * @param args 要代理的方法的参数
     * @return 方法的类或者方法本身
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RmiRequest request = new RmiRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setArgs(args);
        // 这里原本是使用传入的地址和端口，换成了发现服务并且负载均衡后返回的地址
        String serviceAddress = serviceDiscover.discover(request.getClassName());
        RmiTcpTransport tcpTransport = new RmiTcpTransport(serviceAddress);
        return tcpTransport.sendRequest(request);
    }
}
