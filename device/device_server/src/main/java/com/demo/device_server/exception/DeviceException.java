package com.demo.device_server.exception;

import com.demo.device_server.enums.ResultEmun;

public class DeviceException extends RuntimeException{
    private Integer code;

    public DeviceException( Integer code,String message) {
        super(message);
        this.code = code;
    }

    public DeviceException(ResultEmun resultEmun){
        super(resultEmun.getMessage());
        this.code = resultEmun.getCode();
    }
}
