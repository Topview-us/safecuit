package com.gdut.safecuit.device.web.controller;

import com.gdut.safecuit.common.Page;
import com.gdut.safecuit.common.Result;
import com.gdut.safecuit.device.common.po.Device;
import com.gdut.safecuit.device.common.vo.DeviceVO;
import com.gdut.safecuit.device.service.DeviceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.gdut.safecuit.common.util.CacheManager.cacheMap;
import static com.gdut.safecuit.common.util.StringUtil.isEmpty;

/**
 * Created by Garson in 17:01 2018/1/18
 * Description :se
 */
@RestController
@RequestMapping("/device")
public class DeviceController extends BaseController {

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
	public Result<List<DeviceVO>> selectByPage(@RequestParam(value = "pageNo",required = false ,defaultValue = "1") Integer pageNo
			, @RequestParam(value = "pageSize" ,required = false ,defaultValue = "10") Integer pageSize
			, @RequestParam(value = "electricBoxId" ,required = false) Integer electricBoxId
			, @RequestParam(value = "code" ,required = false) String code
			, @RequestParam(value = "typeId" ,required = false ,defaultValue = "0") Integer typeId) {

		List<DeviceVO> list;
		String message;

		if (isEmpty(electricBoxId)){
			return new Result<>(null, "设备id不能为空", false, 400);
		} else {

			Page page = new Page(pageSize ,pageNo ,deviceService.getTotalByElectricBoxId(electricBoxId));

			list = deviceService.selectDeviceByPage(page,electricBoxId, code, typeId);
			if (list.size() == 0)
				message = "暂无设备";
			else
				message = "列举成功";
			return new Result<>(list, message, true, 200 ,page.getTotal());
		}

	}

	/**
	 * 添加设备接口
	 * url:	/device/add
	 * @param device device对象，属性包括：设备编码code、设备名称name、所属电箱id、温度阈值temperatureValue、监控类型typeId
	 * @return 结果集
	 */
	@RequestMapping("/add")
	public Result<Integer> insert(Device device){

		Integer i;

		if(isEmpty(device.getCode(),device.getName(),device.getTemperatureValue()
				,device.getElectricBoxId(),device.getTypeId()))
			i = -1;
		else
			i = deviceService.insertDevice(device);

		return getResult(i);
	}

	/**
	 * 假删除设备接口
	 * url:/device/delete?id=xxx
	 * @param id 设备id
	 * @return 结果集
	 */
	@RequestMapping("/delete")
	public Result<Integer> delete(@RequestParam("id") Integer id ,@RequestParam("electricBoxId")Integer electricBoxId){
		if (id == null || electricBoxId == null)
			return getResult(-1);
		Integer i = deviceService.fakeDeleteDevice(id ,electricBoxId);
		return getResult(i);
	}

	/**
	 * 更改设备信息接口
	 * url：/device/update
	 * @param device 设备对象，属性包括：设备id ,设备编码code、设备名称name、设备所属电箱的id、温度阈值temperatureValue、监控类型typeId
	 * @return 结果集
	 */
	@RequestMapping("/update")
	public Result<Integer> update(Device device){

		Integer i;

		if(isEmpty(device.getCode(),device.getName(),device.getElectricBoxId(),device.getTypeId()))
			i = -1;
		else
			i = deviceService.updateDevice(device);

		return getResult(i);
	}

}
