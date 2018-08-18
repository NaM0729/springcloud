package com.demo.device_server.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("athleticsName")
    private String athleticsId;

    private String serialNumber;

    private String identifier;

}
