package com.demo.device_server.model;

import lombok.Data;

@Data
public class Device {
    private String id;

    /**
     * 类型
     */
    private String type;

    /**
     * 型号
     */
    private String model;

    /**
     * 序列号
     */
    private String serialNumber;

    /**
     * 编号
     */
    private String identifier;

    /**
     * 状态:1.已连接    2.未连接
     */
    private Integer status;

    private String admsCoachId;

    private String admsAthleteId;
}