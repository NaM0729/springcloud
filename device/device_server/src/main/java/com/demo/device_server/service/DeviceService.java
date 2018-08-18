package com.demo.device_server.service;

import com.demo.device_server.dto.DeviceListByPageDTO;
import com.demo.device_server.dto.UpdateDeviceAsADTO;
import com.demo.device_server.model.Device;
import com.demo.device_server.util.DataResult;
import com.demo.device_server.util.PageSizeDataGrid;
public interface DeviceService {

    PageSizeDataGrid getDeviceListByPage(DeviceListByPageDTO deviceListByPageDTO);

    int insert(Device record);

    Device selectByPrimaryKey(String id);

    int updateByIdentifier(Device device);

    DataResult getDeviceListByCoachId(String coachId);

    DataResult getModelListByCoachId(String coachId);

    DataResult saveDeviceByCoachId(UpdateDeviceAsADTO updateDeviceAsADTO);
}
