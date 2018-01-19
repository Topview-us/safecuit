package com.gdut.safecuit.device.service;

import com.gdut.safecuit.common.Page;
import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.common.base.BaseServiceImpl;
import com.gdut.safecuit.device.common.vo.DeviceVO;
import com.gdut.safecuit.device.dao.DeviceMapper;
import com.gdut.safecuit.device.common.po.Device;
import com.gdut.safecuit.device.common.util.DataCache;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gdut.safecuit.common.util.StringUtil.*;

/**
 * Created by Garson in 10:19 2018/1/12
 * Description : 设备service
 */
@Service
public  class DeviceService extends BaseServiceImpl<Device> {

	@Resource
	private  DeviceMapper deviceMapper;

	/**
	 * 通过电箱id来分页显示设备，如果electric_box_id为0，则显示所有设备；如果不为0，则显示对应电箱设备，设备编号与设备监控类型同理
	 * @param pageNo 当前页数
	 * @param pageSize 每页数据量
	 * @param electricBoxId 下拉框选择的电箱id
	 * @param code 查询的设备编码
	 * @param typeId 下拉框选择的监控类型
	 * @return DeviceVO集合
	 */
	public List<DeviceVO> selectByPage(Integer pageNo ,Integer pageSize ,Integer electricBoxId ,String code ,Integer typeId){
		List<DeviceVO> DeviceVOList;
		Page page;
		Map<String ,Object> map;

		//如果全局变量值为0，则在数据库查找数量,如果不为0则直接使用
		if(DataCache.DEVICETOTAL == 0)
			DataCache.DEVICETOTAL = deviceMapper.getDeviceTotal();

		page = new Page(pageSize,pageNo,DataCache.DEVICETOTAL);
		map = new HashMap<>();
		DeviceVOList = new ArrayList<>();

		map.put("page",page);
		map.put("electricBoxId",electricBoxId);
		map.put("code" ,code);
		map.put("typeId" ,typeId);

		List<Device> devices = deviceMapper.selectByPage(map);

		for (Device device: devices) {
			//假删除标志为1
			if(device.getDelTag() == 1)
				continue;

			Map<String ,String> electricBoxInfo = deviceMapper.searchElectricBox(device.getElectricBoxId());

			DeviceVOList.add(new DeviceVO(device.getName() ,device.getCode()
					,electricBoxInfo.get("name"), electricBoxInfo.get("address"),device.getTypeId()));
		}

		return DeviceVOList;
	}

	/**
	 * 添加设备对象
	 * @param device device对象，属性包括：设备编码code、设备名称name、所属电箱id、监控类型
	 * @return 添加数据条数
	 */
	public int insert(Device device){
		int insert;
		device.setId(getUUID());
		device.setDelTag(0);
		insert = deviceMapper.insert(device);
		DataCache.DEVICETOTAL++;//全局变量缓存数加一
		return insert;
	}

	public int deleteById(String id){
		int delete = deviceMapper.deleteById(id);
		DataCache.DEVICETOTAL--;//全局变量缓存数减一
		return delete;
	}

	/**
	 * 假删除
	 * @param id 设备id
	 * @return 修改数据条数
	 */
	public int fakeDelete(String id){
		int delete = deviceMapper.fakeDelete(id);
		DataCache.DEVICETOTAL--;//全局变量缓存数减一
		return delete;
	}

	/**
	 * 修改设备
	 * @param device 修改的设备对象，属性包括：设备编码code、设备名称name、设备所属电箱的id、监控类型typeId
	 * @return 修改数据条数
	 */
	public int update(Device device){
		//如果该设备假删除标识为1，则返回0
		if(deviceMapper.selectDelTag(device.getId()) == 1)
			return 0;
		else
			return deviceMapper.update(device);
	}

	@Override
	public BaseDao<Device> getDao() {
		return deviceMapper;
	}
}
