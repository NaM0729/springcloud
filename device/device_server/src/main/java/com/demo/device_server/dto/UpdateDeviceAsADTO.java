package com.demo.device_server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdateDeviceAsADTO {

    @NotEmpty(message = "教练数据为空")
    @JsonProperty("coachId")
    private String admsCoachId;

    @NotEmpty(message = "型号数据为空")
    private String model;

    @NotEmpty(message = "序列号数据为空")
    private String serialNumber;

    @NotEmpty(message = "编号数据为空")
    private String identifier;

    /**
     * 表示为单个添加功能1，
     * 修改编号功能，
     */
    @NotEmpty(message = "操作类型为空")
    private String type;

    public UpdateDeviceAsADTO(String admsCoachId, String model, String serialNumber, String identifier, String type) {
        this.admsCoachId = admsCoachId;
        this.model = model;
        this.serialNumber = serialNumber;
        this.identifier = identifier;
        this.type = type;
    }

    public UpdateDeviceAsADTO(){

    }
}
