package com.gdut.safecuit.device.service;

import com.gdut.safecuit.common.Page;
import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.common.base.BaseServiceImpl;
import com.gdut.safecuit.device.dao.DeviceMapper;
import com.gdut.safecuit.device.po.Device;
import com.gdut.safecuit.device.util.DataCache;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.gdut.safecuit.common.util.StringUtil.*;

/**
 * Created by Garson in 10:19 2018/1/12
 * Description : 设备service
 */
@Service
public  class DeviceService extends BaseServiceImpl<Device> {

	@Resource
	private DeviceMapper deviceMapper;

	public List<Device> selectByPage(int pageNo ,int pageSize){

		//如果全局变量值为0，则在数据库查找数量
		if(DataCache.DEVICETOTAL == 0)
			DataCache.DEVICETOTAL = deviceMapper.getDeviceTotal();

		Page page = new Page(pageSize,pageNo,DataCache.DEVICETOTAL);

		return deviceMapper.selectByPage(page);
	}

	public int insert(Device device){
		int i;
		device.setId(getUUID());
		i = deviceMapper.insert(device);
		DataCache.DEVICETOTAL++;//全局变量缓存数加一
		return i;
	}

	public int deleteById(Long id){
		DataCache.DEVICETOTAL--;//全局变量缓存数减一
		return deviceMapper.deleteById(id);
	}

	/**
	 * 假删除
	 * @param id id
	 * @return 修改数据条数
	 */
	public int fakeDelete(String id){
		DataCache.DEVICETOTAL--;//全局变量缓存数减一
		return deviceMapper.fakeDelete(id);
	}

	public int update(Device device){
		//如果该设备假删除标识为1，则返回0
		if(deviceMapper.fakeDelete(device.getId()) == 1)
			return 0;
		else
			return deviceMapper.update(device);
	}

	@Override
	public BaseDao<Device> getDao() {
		return deviceMapper;
	}
}
