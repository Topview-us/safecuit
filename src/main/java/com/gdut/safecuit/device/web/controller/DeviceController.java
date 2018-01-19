package com.gdut.safecuit.device.web.controller;

import com.gdut.safecuit.common.Result;
import com.gdut.safecuit.device.common.po.Device;
import com.gdut.safecuit.device.common.vo.DeviceVO;
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
	 * 通过电箱id来分页显示设备的接口，如果electric_box_id为0，则显示所有设备；如果不为0，则显示对应电箱设备，设备编号与设备监控类型同理
	 * url:	/device/list?pageNo=xxx&&pageSize=xxx&&electricBoxId=xxx&&code=xxx&&typeId=xxx
	 * @param pageNo 当前页数
	 * @param pageSize 每页数据量
	 * @param electricBoxId 下拉框选择的电箱id
	 * @param code 查询的设备编码
	 * @param typeId 下拉框选择的监控类型
	 * @return 结果集
	 */
	@RequestMapping("/list")
	public Result<List<DeviceVO>> selectByPage(@RequestParam(value = "pageNo",required = false ,defaultValue = "0") Integer pageNo
			, @RequestParam(value = "pageSize" ,required = false ,defaultValue = "10") Integer pageSize
			, @RequestParam(value = "electricBoxId" ,required = false) Integer electricBoxId
			, @RequestParam(value = "code" ,required = false) String code
			, @RequestParam(value = "typeId" ,required = false) Integer typeId) {

		List<DeviceVO> list;
		String message;
		int status;
		Boolean isSuccess;

		if (isEmpty(pageNo, pageSize)) {
			message = "参数缺失";
			status = 400;
			isSuccess = false;
			list = null;
		} else {
			list = deviceService.selectByPage(pageNo, pageSize ,electricBoxId, code, typeId);
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
	 * @param device device对象，属性包括：设备编码code、设备名称name、所属电箱id、监控类型
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

	/**
	 * 假删除设备接口
	 * @param id 设备id
	 * @return 结果集
	 */
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

	/**
	 * 更改设备信息接口
	 * url：/device/update
	 * @param device 设备对象，属性包括：设备编码code、设备名称name、设备所属电箱的id、监控类型typeId
	 * @return 结果集
	 */
	@RequestMapping("/update")
	public Result<Integer> update(@RequestBody Device device){
		String message;
		int status;
		Boolean isSuccess;
		Integer i = deviceService.update(device);
		if(i == 0){
			message = "更新失败";
			status = 500;
			isSuccess = false;
		}else {
			message = "更新成功";
			status = 200;
			isSuccess = true;
		}
		return new Result<>(i ,message ,isSuccess ,status);
	}

}
