package com.gdut.safecuit.device.service;

import com.gdut.safecuit.common.Page;
import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.common.base.BaseServiceImpl;
import com.gdut.safecuit.device.common.po.Device;
import com.gdut.safecuit.device.common.vo.DeviceVO;
import com.gdut.safecuit.device.dao.DeviceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.gdut.safecuit.common.util.CacheManager.cacheMap;

/**
 * Created by Garson in 10:19 2018/1/12
 * Description : 设备service
 */
@Service
public  class DeviceService extends BaseServiceImpl<Device> {

	@Resource
	private DeviceMapper deviceMapper;

	/**
	 * 添加设备对象
	 * @param device device对象，属性包括：设备编码code、设备名称name、所属电箱id、监控类型、温度阈值
	 * @return 添加数据条数
	 */
	@Transactional
	public int insertDevice(Device device){
		int insert;
		device.setDelTag(0);
		device.setIsOnline(1);//在线
		//添加设备
		insert = deviceMapper.insertSelective(device);

		Integer device_total = (Integer) cacheMap.get("DEVICE_TOTAL");

		if (device_total == null)
			cacheMap.put("DEVICE_TOTAL" ,deviceMapper.getTotalByElectricBoxId(device.getElectricBoxId()));
		else
			cacheMap.put("DEVICE_TOTAL" ,++device_total);//全局变量缓存数加一

		return insert;
	}

	/*public int deleteById(Integer id){
		Device device = deviceMapper.selectByPrimaryKey(id);
		int delete = deviceMapper.deleteByPrimaryKey(id);
		Integer device_total = (Integer) cacheMap.get("DEVICE_TOTAL");

		if (device_total == null)
			cacheMap.put("DEVICE_TOTAL" ,deviceMapper.getTotal(device.getElectricBoxId()));
		else
			cacheMap.put("DEVICE_TOTAL" ,--device_total);//全局变量缓存数减一
		return delete;
	}*/

	/**
	 * 假删除
	 * @param id 设备id
	 * @return 修改数据条数
	 */
	@Transactional
	public int fakeDeleteDevice(Integer id ,Integer electricBoxId){
		int delete = deviceMapper.fakeDelete(id);

		Integer device_total = (Integer) cacheMap.get("DEVICE_TOTAL");

		if (device_total == null)
			cacheMap.put("DEVICE_TOTAL" ,deviceMapper.getTotalByElectricBoxId(electricBoxId));
		else
			cacheMap.put("DEVICE_TOTAL" ,--device_total);//全局变量缓存数减一
		return delete;
	}

	/**
	 * 通过电箱id来分页显示相关设备编号或监控类型的设备
	 * @param page 页数对象
	 * @param electricBoxId 下拉框选择的电箱id
	 * @param code 查询的设备编码
	 * @param typeId 下拉框选择的监控类型
	 * @return DeviceVO集合
	 */
	public List<DeviceVO> selectDeviceByPage(Page page ,Integer electricBoxId ,String code ,Integer typeId){

		List<DeviceVO> DeviceVOList = new ArrayList<>();

		List<Device> devices = deviceMapper.selectByPage(code ,typeId ,page ,electricBoxId);
		for (Device device: devices) {
			//假删除标志为1
			if(device.getDelTag() == 1)
				continue;
			DeviceVOList.add(new DeviceVO(device.getId() ,device.getName() ,device.getCode() ,device.getTemperatureValue()
					,device.getIsOnline() ,device.getTypeId()));
		}

		return DeviceVOList;
	}

	/**
	 * 修改设备
	 * @param device 修改的设备对象，属性包括：设备编码code、设备名称name、设备所属电箱的id、监控类型typeId、温度阈值
	 * @return 修改数据条数
	 */
	public int updateDevice(Device device){

		Integer update;

		//如果该设备假删除标识为1，则返回0
		if(deviceMapper.selectDelTag(device.getId()) == 1)
			return 0;
		else
			update = deviceMapper.updateByPrimaryKeySelective(device);

		return update;
	}

	public Integer getTotalByElectricBoxId(Integer electricBoxId){
		return deviceMapper.getTotalByElectricBoxId(electricBoxId);
	}

	/**
	 * 修改设备的状态，供getInfoFromDevice（dto交互）方法使用
	 * @param id 设备id
	 * @param isOnline 是否在线状态
	 */
	public void updateIsOnline(Integer id ,int isOnline){
		 deviceMapper.updateIsOnline(id ,isOnline);
	}

	@Override
	public BaseDao<Device> getDao() {
		return deviceMapper;
	}
}
