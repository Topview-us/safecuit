package com.gdut.safecuit.device.service;

import com.gdut.safecuit.BaseTest;
import com.gdut.safecuit.common.Page;
import com.gdut.safecuit.common.util.StringUtil;
import com.gdut.safecuit.device.dao.DeviceMapper;
import com.gdut.safecuit.device.common.po.Device;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Garson in 14:24 2018/1/18
 * Description : 设备service测试
 */
public class DeviceServiceTest extends BaseTest {

	@Resource
	private DeviceService deviceService;

	@Resource
	private DeviceMapper deviceMapper;

	@Test
	public void selectAllByIdTest(){
		System.out.println(deviceService.selectOneById(1L));
	}

	@Test
	public void selectByPageTest(){
		System.out.println(deviceService.selectByPage(0,2 ,1 ,"aaa" ,null));
	}

	@Test
	public void insertTest(){
		System.out.println(deviceService.insert(new Device("code1","name1","address1",1,1,0)));
	}

	@Test
	public void updateTest(){
		System.out.println(deviceService.update(new Device(StringUtil.getUUID(),"code","name1",1,1,0)));
	}

	@Test
	public void select(){
	//	Map<String ,Object> map = new HashMap<>();
		//map.put("page" , new Page(2,1,3));
		//map.put("electricBoxId" ,1);
		System.out.println(deviceMapper.searchElectricBox(1));
	//	System.out.println(deviceMapper.selectByPage(map));
	}

	@Test
	public void deleteTest(){
		System.out.println(deviceService.deleteById(2L));
	}

	@Test
	public void fakeDeleteTest(){
		System.out.println(deviceService.fakeDelete("aaa"));
	}
}
