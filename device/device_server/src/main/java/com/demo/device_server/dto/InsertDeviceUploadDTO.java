package com.demo.device_server.dto;

import lombok.Data;

@Data
public class InsertDeviceUploadDTO {

    private String id;

    private String type;

    private String model;

    private String serialNumber;

    private String identifier;

    private Integer status;

    private String admsCoachId;

    private String admsAthleteId;

    private String effective;

    public InsertDeviceUploadDTO(String id, String type, String model,
                                 String serialNumber, String identifier,
                                 Integer status, String admsCoachId,
                                 String admsAthleteId, String effective) {
        this.id = id;
        this.type = type;
        this.model = model;
        this.serialNumber = serialNumber;
        this.identifier = identifier;
        this.status = status;
        this.admsCoachId = admsCoachId;
        this.admsAthleteId = admsAthleteId;
        this.effective = effective;
    }
    public InsertDeviceUploadDTO(){

    }
}
