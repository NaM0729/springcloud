package com.demo.device_server.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeviceListByPageVO {

    @JsonProperty("name")
    private String id;

    private String type;

    private String model;

    private String serialNumber;

    private String identifier;

    private String admsAthleteName;

    public Integer pageNum;

    public Integer pageRowNum;
}
