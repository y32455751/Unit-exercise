package com.example.unit3.Unit_3_3_1.RPC.rmi.Api.Client;

import com.example.unit3.Unit_3_3_1.RPC.rmi.Api.Public.RmiRequest;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 客户端调用远程服务时候的TCP通信类
 */
public class RmiTcpTransport {

    private String host;
    private int port;

    public RmiTcpTransport(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 新建一个socket，这样就不用每次都使用host和port重新新建，只要使用一次host和port就可以了
     * @return
     */
    private Socket newSocket(){
        Socket socket = null;
        try {
            socket = new Socket(host,port);
        } catch (Exception e){
            throw new RuntimeException("socket创建失败",e);
        }
        return socket;
    }

    /**
     * 发送请求方法
     * 把已经设置好信息的请求类通过序列化写出
     * 具体代码没有什么好写注释的
     * @param request 请求类
     * @return 服务端返回的映射方法或者类
     */
    public Object sendRequest(RmiRequest request){
        Socket socket = null;
        Object object = null;
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        try{
            socket = this.newSocket();
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            object = objectInputStream.readObject();
        } catch (Exception e) {
            throw new RuntimeException("远程请求失败",e);
        } finally {
            if(objectOutputStream != null){
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(objectInputStream != null){
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return object;
    }


}
