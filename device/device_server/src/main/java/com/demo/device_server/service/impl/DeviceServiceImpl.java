package com.demo.device_server.service.impl;

import com.demo.device_server.dao.DeviceMapper;
import com.demo.device_server.dto.DeviceListByPageDTO;
import com.demo.device_server.dto.UpdateDeviceAsADTO;
import com.demo.device_server.model.Device;
import com.demo.device_server.service.DeviceService;
import com.demo.device_server.util.DataResult;
import com.demo.device_server.util.PageSizeDataGrid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceMapper deviceMapper;

    @Override
    public PageSizeDataGrid getDeviceListByPage(DeviceListByPageDTO deviceListByPageDTO) {

        PageSizeDataGrid psdg = new PageSizeDataGrid();
        Integer pageNum = deviceListByPageDTO.getPageNum();
        Integer pageRowNum = deviceListByPageDTO.getPageRowNum();
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageRowNum == null || pageRowNum == 0) {
            pageRowNum = 10;
            // 保存每页条数
            deviceListByPageDTO.setPageRowNum(pageRowNum);
        }
        // 保存当前页码
        deviceListByPageDTO.setStartSize((pageNum - 1) * pageRowNum);
        // 查看该教练下设备条数
        Integer recordNum = deviceMapper.getDeviceCountByCoachId(deviceListByPageDTO);

        List<Device> list = new ArrayList<>();
        if (recordNum > 0) {
            // 获取运动员id
//            =================
            //查询结果集
            list = deviceMapper.getDeviceList(deviceListByPageDTO);
            int totalPage = recordNum % pageRowNum == 0 ? recordNum / pageRowNum : recordNum / pageRowNum + 1;
            psdg.setTotal(recordNum);
            psdg.setTotalPage(totalPage);
            psdg.setRows(list);
            psdg.setStatus(200);
            psdg.setMessage("查询成功!");
        } else {
            psdg.setStatus(200);
            psdg.setRows(list);
            psdg.setMessage("无数据!");
        }
        return psdg;
    }

    @Override
    public int insert(Device record) {
        return 0;
    }

    @Override
    public Device selectByPrimaryKey(String id) {
        return null;
    }

    @Override
    public int updateByIdentifier(Device device) {
        return 0;
    }

    /**
     * 设备匹配模块 查询该教练下所有设备信息 zyn 2018-08-10
     *
     * @param coachId
     * @return
     */
    @Override
    public DataResult getDeviceListByCoachId(String coachId) {
        List<Device> deviceListByCoachId = deviceMapper.getDeviceListByCoachId(coachId);
        return DataResult.success(deviceListByCoachId);
    }

    /**
     * 添加按钮 查询该教练下所有型号 zyn 2018-08-10
     *
     * @param coachId
     * @return
     */
    @Override
    public DataResult getModelListByCoachId(String coachId) {
        List<Device> deviceListByCoachId = deviceMapper.getDeviceListByCoachId(coachId);
        if (!StringUtils.isEmpty(deviceListByCoachId)) {
            List<String> collect = deviceListByCoachId.stream().
                    map(Device::getModel).distinct().
                    collect(Collectors.toList());
            return DataResult.success(collect);
        } else {
            return DataResult.success("该教练下没有设备！");
        }
    }

    /**
     * 单个添加设备信息 zyn 2018-08-10
     *
     * @param updateDeviceAsADTO
     * @return
     */
    @Override
    public DataResult saveDeviceByCoachId(UpdateDeviceAsADTO updateDeviceAsADTO) {
        Device deviceDTO = new Device();
        BeanUtils.copyProperties(updateDeviceAsADTO, deviceDTO);
        Device device1 = deviceMapper.selectDeviceCountBySerialNumber(deviceDTO);
        if (StringUtils.isEmpty(device1)) {
            return DataResult.fail("序列号有误");
        }
        if (deviceMapper.selectDeviceCountByIdentifier(deviceDTO) > 0) {
            return DataResult.fail("该编号已被占用");
        }
        if ("1".equals(updateDeviceAsADTO.getType())
                && !StringUtils.isEmpty(device1.getIdentifier())) {
            return DataResult.fail("该设备与编号已存在绑定");
        }
        if ("1".equals(updateDeviceAsADTO.getType())
                || "2".equals(updateDeviceAsADTO.getType())) {
            device1.setIdentifier(deviceDTO.getIdentifier());
            int update = deviceMapper.updateByPrimaryKey(device1);
            if (update > 0) {
                return DataResult.success("添加成功！");
            } else {
                return DataResult.fail("添加失败！");
            }
        }else {
            return DataResult.fail("请求类型为非法参数");
        }
    }
}
