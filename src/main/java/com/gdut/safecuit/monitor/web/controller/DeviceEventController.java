package com.gdut.safecuit.monitor.web.controller;

import com.gdut.safecuit.common.Page;
import com.gdut.safecuit.common.Result;
import com.gdut.safecuit.common.util.StringUtil;
import com.gdut.safecuit.monitor.common.vo.DeviceEventVO;
import com.gdut.safecuit.monitor.service.DeviceEventService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.gdut.safecuit.common.util.MatchUtil.isPositiveInteger;

/**
 * Created by Garson in 21:30 2018/1/25
 * Description :
 */
@RestController
@RequestMapping("/deviceEvent")
public class DeviceEventController {

	@Resource
	private DeviceEventService deviceEventService;

	@RequestMapping("/list")
	public Result<List<DeviceEventVO>> select(@RequestParam(value = "pageNo",required = false ,defaultValue = "1") Integer pageNo
			, @RequestParam(value = "pageSize" ,required = false ,defaultValue = "10") Integer pageSize
			, @RequestParam(value = "electricBoxId" ,required = false)Integer electricBoxId){

		List<DeviceEventVO> deviceEvents;
		String message;

		Page page = new Page(pageSize ,pageNo , deviceEventService.getTotalByElectricBoxId(electricBoxId));

		deviceEvents = deviceEventService.selectByPage(page ,electricBoxId);

		if (deviceEvents.size() == 0)
			message = "暂无设备报警信息";
		else
			message = "列举成功";

		return new Result<>(deviceEvents ,message ,true ,200 ,page.getTotal());
	}

	@RequestMapping("/updateType")
	public Result<Integer> updateType(@RequestParam(value = "id" ,required = false)Integer id ,
									  @RequestParam(value = "type" ,required = false)Integer type){

		if (StringUtil.isEmpty(id ,type))
			return new Result<>(null ,"参数不能为空" ,false ,400 );
		Integer update = deviceEventService.updateType(type ,id);

		if (update == 1)
			return new Result<>(1 ,"操作成功" ,true ,200 );
		else
			return new Result<>(0 ,"操作失败，请重试" ,false ,500);
	}

}
