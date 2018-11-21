package com.example.unit3.Unit_3_2_1.TCPSocket.Util;

import com.example.unit3.Unit_3_2_1.TCPSocket.Entity.ClientUser;
import com.example.unit3.Unit_3_2_1.TCPSocket.Entity.ServerEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

/**
 * socket工具类
 */
public class SocketUtil {
    Logger logger = Logger.getLogger("SocketUtil");
    // 客户端信息实体类
    ClientUser user;
    // 字符输入
    BufferedReader bufferedReader;
    // 输出流
    PrintStream printStream;
    // 当前要被操作的socket
    Socket socket;
    DateFormat format = new SimpleDateFormat("HH:mm:ss");

    // 在要使用这个工具类的时候  必须指定要操作的socket
    public SocketUtil(Socket socket){
        this.socket = socket;
    }

    /**
     * 在客户端创建工具类的时候，指定一个客户端的信息
     * @remark 新建一个socket，然后连接到服务器
     * @remark 把这些东西保存到客户端信息类中保存
     * @param user 客户端信息类
     */
    public SocketUtil(ClientUser user) {
        logger.info("正在登录......");
        Socket socket = null;
        try {
            socket = new Socket(ServerEntity.SERVER_IP,ServerEntity.PROT);
        } catch (IOException e) {
            logger.warning("登录失败");
            e.printStackTrace();
        }
        logger.info("登录成功");
        user.setOnlineTime(new Date());
        user.setSocket(socket);
        this.user = user;
    }

    /**
     * 接收信息
     * @remark 实例化一个输入流，读取发送给此socket的消息
     * @remark 获得要发送的消息
     * @param socket 被操作的客户端socket
     * @return 要发送的消息
     */
    private String receiveInit(Socket socket){
        String msg = "";
        try {
            if(bufferedReader == null) {
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            }
            msg = bufferedReader.readLine();
        } catch (IOException e) {
            logger.info("消息接收失败");
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 客户端接收消息
     * @return 发送给该客户端的消息
     */
    public String receiveFromClient(){
        return this.receiveInit(this.user.getSocket());
    }

    /**
     * 服务器接收消息
     * @return 发送给服务器的消息
     */
    public String receiveFromServer(){
        return this.receiveInit(this.socket);
    }

    /**
     * 发送消息
     * @param printStream 将要被使用的输出流
     * @param socket 要发送消息的客户端
     * @return 被使用的输出流
     */
    private PrintStream sendInit(PrintStream printStream,Socket socket){
        if(printStream == null){
            try {
                printStream = new PrintStream(socket.getOutputStream());
            } catch (IOException e) {
                logger.warning("消息发送失败");
                e.printStackTrace();
            }
        }
        return printStream;
    }

    /**
     * 客户端发送消息
     * @remark 先发送消息
     * @remark 如果消息是断开的命令，则进行断开操作
     * @param msg 发送的消息
     * @param user 发送消息的用户名
     */
    public void sendFromClient(String msg,String user){
        this.sendInit(printStream,this.user.getSocket()).println("["+format.format(new Date())+"] "+user+" : " + msg);
        if(ServerEntity.EXIT_ORDER.equals(msg.toUpperCase()))
            this.disConnectServer();
    }

    /**
     * 服务器发送消息
     * @param msg 发送的消息
     * @param socket 发送给的socket
     */
    public void sendFromServer(String msg,Socket socket){
        this.sendInit(printStream,socket).println(msg);
    }

    /**
     * 断开连接操作
     */
    public void disConnectServer(){
        logger.info("即将断开服务器连接");
        if(this.user.getSocket() != null){
            try {
                this.user.getSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.warning("断开服务器失败");
            }
            logger.info("您已断开服务器连接");
        }else{
            logger.warning("丢失连接......");
        }
    }



}
