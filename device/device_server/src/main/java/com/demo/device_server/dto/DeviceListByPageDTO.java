package com.demo.device_server.dto;

import lombok.Data;

@Data
public class DeviceListByPageDTO {

    private String coachId;

    public Integer pageNum;

    private Integer StartSize;
    /**
     * 每页条数 默认十条
     */
    public Integer pageRowNum;

    private String model;

    private String athleticsName;

    private String serialNumber;

    private String identifier;

}
