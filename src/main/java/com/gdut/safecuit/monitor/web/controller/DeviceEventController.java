package com.gdut.safecuit.monitor.web.controller;

import com.gdut.safecuit.common.Page;
import com.gdut.safecuit.common.Result;
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
	public Result<List<DeviceEventVO>> select(@RequestParam(value = "pageNo",required = false ,defaultValue = "0") String pageNo
			, @RequestParam(value = "pageSize" ,required = false ,defaultValue = "10") String pageSize
			, @RequestParam(value = "electricBoxId" ,required = false)Integer electricBoxId){

		List<DeviceEventVO> deviceEvents;
		String message;

		if(!isPositiveInteger(pageNo) || !isPositiveInteger(pageSize)){
			return new Result<>(null ,"url参数有误" ,false ,400 );
		}else {

			Page page = new Page(Integer.valueOf(pageSize) ,Integer.valueOf(pageNo)
					,deviceEventService.getTotalByElectricBoxId(electricBoxId));

			deviceEvents = deviceEventService.selectByPage(page ,electricBoxId);

			if (deviceEvents.size() == 0)
				message = "暂无设备报警信息";
			else
				message = "列举成功";

			return new Result<>(deviceEvents ,message ,true ,200 ,page.getTotalPages());

		}

	}

}
