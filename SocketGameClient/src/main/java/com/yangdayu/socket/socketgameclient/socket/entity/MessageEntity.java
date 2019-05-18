package com.yangdayu.socket.socketgameclient.socket.entity;

import com.yangdayu.socket.socketgameclient.socket.Util.NetUtil;
import com.yangdayu.socket.socketgameclient.socket.Util.SendType;

import java.io.Serializable;

public class MessageEntity implements Serializable {

    private String fromAddress;
    private String fromHost;
    private String toAddress;
    private String sendType;
    private String toHost;
    private String message;
    private InvitationEntity invitation;

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getFromHost() {
        return fromHost;
    }

    public void setFromHost(String fromHost) {
        this.fromHost = fromHost;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getToHost() {
        return toHost;
    }

    public void setToHost(String toHost) {
        this.toHost = toHost;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public InvitationEntity getInvitation() {
        return invitation;
    }

    public void setInvitation(InvitationEntity invitation) {
        this.invitation = invitation;
    }

    public MessageEntity(String toAddress, String sendType, String toHost, String message) {
        this.fromAddress = NetUtil.getLocalHostLANAddress().getHostAddress();
        this.fromHost = fromHost;
        this.toAddress = toAddress;
        this.sendType = sendType;
        this.toHost = toHost;
        this.message = message;
    }

    @Override
    public String toString() {
        return "MessageEntity{" +
                "fromAddress='" + fromAddress + '\'' +
                ", fromHost='" + fromHost + '\'' +
                ", toAddress='" + toAddress + '\'' +
                ", sendType=" + sendType +
                ", toHost='" + toHost + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public MessageEntity() {
    }
}
