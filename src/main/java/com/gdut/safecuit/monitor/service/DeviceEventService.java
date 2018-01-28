package com.gdut.safecuit.monitor.service;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.gdut.safecuit.common.Page;
import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.common.base.BaseServiceImpl;
import com.gdut.safecuit.common.util.LogUtil;
import com.gdut.safecuit.device.common.po.Device;
import com.gdut.safecuit.device.dao.DeviceMapper;
import com.gdut.safecuit.monitor.common.po.DeviceEvent;
import com.gdut.safecuit.monitor.common.vo.DeviceEventVO;
import com.gdut.safecuit.monitor.dao.DeviceEventMapper;



import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Garson in 14:33 2018/1/25
 * Description : 报警事件service
 */
@Service
public class DeviceEventService extends BaseServiceImpl<DeviceEvent> {

	@Resource
	private DeviceEventMapper deviceEventMapper;
	@Resource
	private DeviceMapper deviceMapper;

	public List<DeviceEventVO> selectByPage(Page page ,Integer electricBoxId){
		List<DeviceEventVO> deviceEventVOS = new ArrayList<>();

		try{
			List<DeviceEvent> deviceEvents = deviceEventMapper.selectAllByPage(page ,electricBoxId);

			System.out.println(deviceEvents);

			for (DeviceEvent deviceEvent :deviceEvents) {
				Device device = deviceMapper.selectByPrimaryKey(deviceEvent.getDeviceId());

				System.out.println(device);

				if (device.getElectricBoxId().intValue() !=  electricBoxId)
					continue;

				//将pair分割成json数据
				JSONObject jsonObject = textToJson(deviceEvent.getPair());

				DeviceEventVO deviceEventVO = new DeviceEventVO(deviceEvent.getId() ,device.getCode() ,deviceEvent.getCircuitNo()
						,deviceEvent.getEventCode() ,deviceEvent.getSerialNo() ,deviceEvent.getType(),deviceEvent.getTime()
						,jsonObject);

				deviceEventVOS.add(deviceEventVO);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			LogUtil.error(this.getClass() ,"json error" ,e);
		}

		return deviceEventVOS;
	}

	private JSONObject textToJson(String pair) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		if (pair == null)
			return jsonObject;

		String[] strings = pair.replaceAll(" " ,"").split(",");
		for (String string : strings) {
			System.out.println(string);
			String[] keyAndValue = string.split(":");
			jsonObject.put(keyAndValue[0] ,keyAndValue[1]);
		}
		return jsonObject;
	}


	public Integer getTotalByElectricBoxId(Integer electricBoxId){
		return deviceEventMapper.getTotalByEelectricBoxId(electricBoxId);
	}


	@Override
	public BaseDao<DeviceEvent> getDao() {
		return deviceEventMapper;
	}
}
