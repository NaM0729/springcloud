package com.demo.device_server.dao;

import com.demo.device_server.dto.DeviceListByPageDTO;
import com.demo.device_server.model.Device;

import java.util.List;

public interface DeviceMapper {

    List<Device> getDeviceListByCoachId(String coachId);

    int getDeviceCountByCoachId(DeviceListByPageDTO deviceListByPageDTO);

    List<Device> getDeviceList(DeviceListByPageDTO deviceListByPageDTO);

    Device selectDeviceCountBySerialNumber(Device device);

    int selectDeviceCountByIdentifier(Device device);

    int updateByPrimaryKey(Device record);

    int deleteByPrimaryKey(String id);

    int insert(Device record);

    int insertSelective(Device record);

    Device selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Device record);


}