package com.yangdayu.socket.socketgameclient.entity;

public class CoordinateEntity {

    private String[] coordinate;
    private String isHead;

    public String[] getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String[] coordinate) {
        this.coordinate = coordinate;
    }

    public String getIsHead() {
        return isHead;
    }

    public void setIsHead(String isHead) {
        this.isHead = isHead;
    }

    public CoordinateEntity(String[] coordinate, String isHead){
        this.coordinate = coordinate;
        this.isHead = isHead;
    }

    public CoordinateEntity(){
    }
}
