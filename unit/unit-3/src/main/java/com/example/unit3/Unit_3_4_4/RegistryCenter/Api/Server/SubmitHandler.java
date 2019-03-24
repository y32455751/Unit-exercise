package com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Server;

import com.example.unit3.Unit_3_4_4.RegistryCenter.Api.Public.RmiRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理请求的类，接收到请求，反射一个方法并且返回方法供客户端调用
 */
public class SubmitHandler implements Runnable {

    // ServerScoket收到的来自客户端请求的socket
    private Socket socket;
    // 要调用的类，这里使用了注册中心，所以变成了一个MAP集合，用来存储对应关系
//    private Object service;
    Map<String,Object> serviceMap;

    public SubmitHandler(Socket socket, Map<String,Object> serviceMap) {
        this.socket = socket;
        this.serviceMap = serviceMap;
    }

    /**
     * 线程方法，用来处理远程调用，
     * 接收客户端的请求实体类，调用分析方法，
     * 返回映射好的方法
     */
    @Override
    public void run() {
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try{
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            // 获得客户端的请求类
            RmiRequest request = (RmiRequest) objectInputStream.readObject();
            // 分析请求类方法
            Object result = invock(request);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            // 写出反射好的方法
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();
        } catch (Exception e) {
            throw new RuntimeException("服务方法反射机制错误",e);
        } finally {
            try {
                if(objectInputStream != null) {
                    objectInputStream.close();
                }
                if(objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 映射方法类
     * 获得请求类的信息，分析后返回对应的方法映射
     * @param request 请求类
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     */
    private Object invock(RmiRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        // 获得方法的请求参数数组
        Object[] objects = request.getArgs();
        Object o = null;
        // 创建一个class类型的数组
        Class<?>[] argsType = new Class<?>[objects.length];
        // 存放所有参数的类型
        for(int i=0;i<argsType.length;i++){
            argsType[i] = objects[i].getClass();
        }
        // 因为使用的是注册中心  所以要从众多的服务中取出这个服务
        Object service = serviceMap.get(request.getClassName());

        // 获得方法映射，方法名和参数类型可以确定到一个方法
        Method method = service.getClass().getMethod(request.getMethodName(),argsType);
        try {
            // 反射方法执行
            /*
                这里曾经犯了一个错误，本应该防止objects的参数位置被窝防止了argsType，
                然后报错信息是参数类型不正确，
                这里的参数放置的是参数的值，因为这里是要直接映射的调用方法，所以需要具体的参数，而不是错误的吧类型放进去，
                类型在上面映射方法，选择方法的时候需要使用，因为要确定一个方法。
             */
            o = method.invoke(service, objects);
        } catch(Exception e){
            e.printStackTrace();
        }
        return o;
    }
}




























