package com.demo.device_server.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class DeleteDeviceDTO {

    @NotEmpty(message = "教练数据为空")
    private String coachId;
    private List<String> deviceIdList;

}
