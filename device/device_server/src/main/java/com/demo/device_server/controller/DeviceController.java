package com.demo.device_server.controller;

import com.demo.device_server.dto.DeviceListByPageDTO;
import com.demo.device_server.dto.UpdateDeviceAsADTO;
import com.demo.device_server.enums.ResultEmun;
import com.demo.device_server.exception.DeviceException;
import com.demo.device_server.service.DeviceService;
import com.demo.device_server.util.DataResult;
import com.demo.device_server.util.PageSizeDataGrid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/device")
@ResponseBody
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    /**
     * 设备管理分页查看+条件搜索 zyn 2018-08-10
     * @param deviceListByPageDTO
     * @return
     */
    @PostMapping("/getDeviceList")
    public PageSizeDataGrid getDevice(@RequestBody DeviceListByPageDTO deviceListByPageDTO){

       return deviceService.getDeviceListByPage(deviceListByPageDTO);
    }

    /**
     * 设备匹配模块 查询该教练下所有设备信息 zyn 2018-08-10
     * @param coachId
     * @return
     */
    @GetMapping("/getDeviceListByCoachId/{coachId}")
    public DataResult getDeviceListByCoachId(@PathVariable String coachId){
        return deviceService.getDeviceListByCoachId(coachId);
    }

    /**
     * 添加按钮 查询该教练下所有型号  zyn 2018-08-10
     * @param coachId
     * @return
     */
    @GetMapping("/getModelListByCoachId/{coachId}")
    public DataResult getModelListByCoachId(@PathVariable String coachId){
        return deviceService.getModelListByCoachId(coachId);
    }

    /**
     * 单个添加/删除设备信息 zyn 2018-08-10
     * @param updateDeviceAsADTO
     * @return
     */
    @PostMapping("/saveDeviceByCoachId")
    public DataResult saveDeviceByCoachId(@RequestBody @Valid UpdateDeviceAsADTO updateDeviceAsADTO,
                                          BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("=={}",updateDeviceAsADTO);
            throw new DeviceException(ResultEmun.PARAM_ERROR);
//            return DataResult.fail();
        }
        return deviceService.saveDeviceByCoachId(updateDeviceAsADTO);
    }

}
