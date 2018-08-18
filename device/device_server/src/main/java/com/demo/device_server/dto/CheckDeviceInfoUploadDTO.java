package com.demo.device_server.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CheckDeviceInfoUploadDTO {

    @NotEmpty(message = "教练数据为空")
    private String admsCoachId;

    @NotEmpty(message = "型号数据为空")
    private String model;

    @NotEmpty(message = "序列号数据为空")
    private String serialNumber;

    @NotEmpty(message = "编号数据为空")
    private String identifier;

    public CheckDeviceInfoUploadDTO(String admsCoachId,
                              String model,String serialNumber,
                              String identifier) {
        this.admsCoachId = admsCoachId;
        this.model = model;
        this.serialNumber = serialNumber;
        this.identifier = identifier;
    }
    public CheckDeviceInfoUploadDTO(){

    }
}
