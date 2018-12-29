package com.example.unit3.Unit_3_3_1.RPC.rmi.Api.Client;

import com.example.unit3.Unit_3_3_1.RPC.rmi.Api.Public.RmiRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 执行代理处理逻辑的类
 */
public class RemoteInvocationHandler implements InvocationHandler {

    private String host;
    private int port;

    public RemoteInvocationHandler(String host, int port) {
        this.host = host;
        this.port = port;
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

        RmiTcpTransport tcpTransport = new RmiTcpTransport(host,port);
        return tcpTransport.sendRequest(request);
    }
}
