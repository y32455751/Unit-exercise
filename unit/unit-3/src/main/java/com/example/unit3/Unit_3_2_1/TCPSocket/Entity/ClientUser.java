package com.example.unit3.Unit_3_2_1.TCPSocket.Entity;

import java.net.Socket;
import java.util.Date;

/**
 * 客户端实体类
 */
public class ClientUser {
    // 连接
    private Socket socket;
    // 昵称
    private String userName;
    // 上线时间
    private Date onlineTime;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Date onlineTime) {
        this.onlineTime = onlineTime;
    }
}
