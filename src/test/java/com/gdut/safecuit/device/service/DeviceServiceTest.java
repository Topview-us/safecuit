package com.gdut.safecuit.device.service;

import com.gdut.safecuit.BaseTest;
import com.gdut.safecuit.device.dao.DeviceMapper;
import org.junit.Test;

import javax.annotation.Resource;

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
		System.out.println(deviceMapper.selectByPrimaryKey(1));
	}

/*	@Test
	public void selectByPageTest(){
		System.out.println(deviceService.selectByPage(0,2 ,1 ,null ,null));
	}*/

	/*@Test
	public void insertTest(){
		System.out.println(deviceService.insert(new Device(StringUtil.getUUID(),"name1","address1","aaa",1,0)));
	}

	@Test
	public void updateTest(){
		System.out.println(deviceService.update(new Device(StringUtil.getUUID(),"code","name1","aaa",1,0)));
	}*/

	@Test
	public void select(){
	//	Map<String ,Object> map = new HashMap<>();
		//map.put("page" , new Page(2,1,3));
		//map.put("electricBoxId" ,1);
	//	System.out.println(deviceMapper.searchElectricBox("a"));
	//	System.out.println(deviceMapper.selectByPage(map));
	}

	/*@Test
	public void deleteTest(){
		System.out.println(deviceService.deleteById(1));
	}

	@Test
	public void fakeDeleteTest(){
		System.out.println(deviceService.fakeDelete(1));
	}*/

}
