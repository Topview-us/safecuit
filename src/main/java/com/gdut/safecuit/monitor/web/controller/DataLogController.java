package com.gdut.safecuit.monitor.web.controller;

import com.gdut.safecuit.common.Page;
import com.gdut.safecuit.common.Result;
import com.gdut.safecuit.common.util.StringUtil;
import com.gdut.safecuit.monitor.common.vo.DataLogHistoryVO;
import com.gdut.safecuit.monitor.common.vo.DataLogVO;
import com.gdut.safecuit.monitor.service.DataLogService;
import org.json.JSONException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.gdut.safecuit.common.util.StringUtil.isEmpty;
import static com.gdut.safecuit.monitor.common.EventCode.*;

/**
 * Created by Garson in 10:30 2018/1/26
 * Description :
 */
@RestController
@RequestMapping("/dataLog")
public class DataLogController {

	@Resource
	private DataLogService dataLogService;

	@RequestMapping("/list")
	public Result<List<DataLogVO>> selectDataLog(@RequestParam(value = "electricBoxId" ,required = false)Integer electricBoxId) throws JSONException {

		if (StringUtil.isEmpty(electricBoxId))
			return new Result<>(null ,"参数不能为空" ,true ,200);
		else
			return new Result<>(dataLogService.selectDataLog(electricBoxId) ,"搜索成功" ,true ,200);
	}

	@RequestMapping("/historyList")
	public Result<List<DataLogHistoryVO>> selectHistoryInfo(@RequestParam(value = "deviceId" ,required = false)Integer deviceId
			, @RequestParam(value = "typeId" ,required = false)Integer typeId
			, @RequestParam(value = "startDate" ,required = false)Date startDate
			, @RequestParam(value = "endDate" ,required = false)Date endDate
			, @RequestParam(value = "pageNo" ,required = false ,defaultValue = "1")Integer pageNo
			, @RequestParam(value = "pageSize" ,required = false ,defaultValue = "10")Integer pageSize){

		List<DataLogHistoryVO> dataLogHistoryVOS;
		String message;
		int status;

		if (StringUtil.isEmpty(deviceId) || pageNo < 1 || pageSize < 1){
			message = "请求参数有误";
			status = 400;
			return new Result<>(null ,message ,false ,status);
		} else {
			if (typeId != CURRENT_EXCESS && typeId != TEMPERATURE_EXCESS && typeId != MILI_CURRENT_EXCESS){
				message = "url参数有误";
				status = 400;
				return new Result<>(null ,message ,false ,status);
			}

			Page page  = new Page(pageSize ,pageNo ,dataLogService.getTotalByTypeIdAndDeviceId(typeId
					,deviceId ,startDate ,endDate));
			dataLogHistoryVOS = dataLogService.getHistoryInfo(deviceId ,typeId ,startDate ,endDate ,page);

			if (dataLogHistoryVOS.size() == 0)
				message = "暂无信息";
			else
				message = "列举成功";
			status = 200;
			return new Result<>(dataLogHistoryVOS ,message ,true ,status ,page.getTotal());
		}



	}

}
