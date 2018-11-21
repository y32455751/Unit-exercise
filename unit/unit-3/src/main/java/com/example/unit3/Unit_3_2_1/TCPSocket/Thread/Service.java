package com.example.unit3.Unit_3_2_1.TCPSocket.Thread;

import com.example.unit3.Unit_3_2_1.TCPSocket.Entity.ServerEntity;
import com.example.unit3.Unit_3_2_1.TCPSocket.Util.SocketUtil;

import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 服务器轮询线程
 */
public class Service implements Runnable{

    // 连接服务器的客户端连接，使用static让他为静态，只在服务器刚启动的时候新实例化一个集合
    // 以后每个客户端连接之后向其中添加一个客户端连接
    static List<Socket> clientList = new ArrayList<Socket>();
    // 每个线程中都有一个客户端的连接，他跟上面的集合是不一样的，每个线程中都是不同的socket，对应一个客户端
    Socket socket;
    // 和上面的socket是差不多的，但是这个是用来进行socket操作
    SocketUtil util;
    DateFormat format = new SimpleDateFormat("HH:mm:ss");
    // 停止线程的标志，使用stop来停止线程会产生问题，相当于电脑直接关闭电源，很危险。
    boolean exit = false;

    /**
     * 线程的构造方法
     * @remark 在线程刚创建的时候，创建一个关于当前线程对应客户端的socket工具类
     * @remark 然后像其他所有在线的客户端群发消息，表示新的客户端来到
     * @remark 把新的socket连接加入到连接集合中保存
     * @param socket 新的socket连接
     */
    public Service(Socket socket){
        util = new SocketUtil(socket);
        for (Socket clientUser : clientList) {
            util.sendFromServer("["+format.format(new Date())+"] 系统 : 欢迎新朋友加入聊天室",clientUser);
        }
        clientList.add(socket);
        this.socket = socket;
    }

    /**
     * 服务器的线程轮询
     * @remark 首先轮询是否有发送到服务器的消息
     * @remark 当有消息之后，首先判断是否是退出命令
     * @remark 当发送来的消息是关闭命令时，首先在集合中寻找当前的连接
     * @remark 关闭这个连接，然后将这个连接在集合中移除
     * @remark 设置当前轮询为最后一次
     * @remark 将退出的消息发送给其他在线的客户端
     */
    @Override
    public void run() {
        String msg = "";
        while(!exit){
            msg = util.receiveFromServer();
            if(msg.toUpperCase().indexOf(ServerEntity.EXIT_ORDER) > 0){
                for (int i = 0; i < clientList.size(); i++) {
                    if(clientList.get(i).equals(this.socket)){
                        try {
                            this.socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        clientList.remove(i);
                    }
                }
                msg = "一位朋友已经退出";
                exit = true;
            }
            for (Socket item : clientList) {
                if(!item.equals(this.socket))
                    util.sendFromServer(msg,item);
            }

        }
    }
}
