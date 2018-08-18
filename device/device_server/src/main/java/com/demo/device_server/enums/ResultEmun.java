package com.demo.device_server.enums;

import lombok.Getter;

@Getter
public enum  ResultEmun {
    PARAM_ERROR(1,"参数错误"),
    ;

    private Integer code;
    private String message;

    ResultEmun(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
