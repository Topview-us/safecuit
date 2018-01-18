package com.gdut.safecuit.device.web.controller;

import com.gdut.safecuit.common.Result;
import com.gdut.safecuit.device.po.Device;
import com.gdut.safecuit.device.service.DeviceService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static com.gdut.safecuit.common.util.StringUtil.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Garson in 17:01 2018/1/18
 * Description :
 */
@RestController
@RequestMapping("/device")
public class DeviceController {

	@Resource
	private DeviceService deviceService;

	/**
	 * 分页显示接口
	 * url:	/device/list?pageNo=xxx&&pageSize=xxx
	 * @param pageNo 当前页面数
	 * @param pageSize 每一页的数据量
	 * @return 结果集
	 */
	@RequestMapping("/list")
	public Result<List<Device>> selectByPage(@RequestParam("pageNo") int pageNo ,@RequestParam("pageSize") int pageSize) {
		List<Device> list;
		String message;
		int status;
		Boolean isSuccess;

		if (isEmpty(pageNo, pageSize)) {
			message = "参数缺失";
			status = 400;
			isSuccess = false;
			list = null;
		} else {
			list = deviceService.selectByPage(pageNo, pageSize);
			if (list.size() == 0)
				message = "暂无设备";
			else
				message = "列举成功";
			status = 200;
			isSuccess = true;
		}

		return new Result<>(list, message, isSuccess, status);
	}

	/**
	 * 添加设备接口
	 * url:	/device/add
	 * @param device device对象
	 * @return 结果集
	 */
	@RequestMapping("/add")
	public Result<Integer> insert(@RequestBody Device device){
		String message;
		int status;
		Boolean isSuccess;
		Integer i = deviceService.insert(device);
		if(i == 0){
			message = "添加失败";
			status = 500;
			isSuccess = false;
		}else {
			message = "添加成功";
			status = 200;
			isSuccess = true;
		}
		return new Result<>(i ,message ,isSuccess ,status);
	}

	@RequestMapping("/delete")
	public Result<Integer> delete(@RequestParam("id") String id){
		String message;
		int status;
		Boolean isSuccess;
		Integer i = deviceService.fakeDelete(id);
		if(i == 0){
			message = "删除失败";
			status = 500;
			isSuccess = false;
		}else {
			message = "删除成功";
			status = 200;
			isSuccess = true;
		}
		return new Result<>(i ,message ,isSuccess ,status);
	}


}
