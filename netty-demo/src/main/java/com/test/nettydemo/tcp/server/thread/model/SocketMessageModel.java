package com.test.nettydemo.tcp.server.thread.model;

import java.io.Serializable;

public class SocketMessageModel implements Serializable {
    String head;
    String id ;
    String type ;
    String length ;
    String time ;
    /**
     * 经度
     **/
    Double longitude ;
    /**
     * 纬度
     **/
    Double latitude ;
    /**
     * 心跳
     **/
    String heartbeat ;
    /**
     * 速度 3.30m/s
     */
    String speed ;
    /**
     * 位置 正北为0 顺时针转 不能为负 例：210 = 南偏西30度
     **/
    String position ;
    /**
     * 步频
     **/
    String stepFreq ;
    /**
     * 距离 0.1m
     **/
    String distance ;

    public SocketMessageModel() {
        super();
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(String heartbeat) {
        this.heartbeat = heartbeat;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStepFreq() {
        return stepFreq;
    }

    public void setStepFreq(String stepFreq) {
        this.stepFreq = stepFreq;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
