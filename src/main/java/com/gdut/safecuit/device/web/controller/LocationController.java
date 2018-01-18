package com.gdut.safecuit.device.web.controller;

import com.gdut.safecuit.common.Result;
import com.gdut.safecuit.device.po.Area;
import com.gdut.safecuit.device.po.City;
import com.gdut.safecuit.device.po.Province;
import com.gdut.safecuit.device.service.AreaService;
import com.gdut.safecuit.device.service.CityService;
import com.gdut.safecuit.device.service.ProvinceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Garson in 21:13 2018/1/18
 * Description : 省市区接口
 */
@RestController
@RequestMapping("/location")
public class LocationController {
	@Resource
	private ProvinceService provinceService;
	@Resource
	private CityService cityService;
	@Resource
	private AreaService areaService;

	@RequestMapping("/province")
	public Result<List<Province>> provinceSelect(){
		List<Province> list = provinceService.select();
		System.out.println(list.get(0));
		return getResult(list);
	}

	@RequestMapping("/city")
	public Result<List<City>> citySelect(@RequestParam(value = "provinceId" ,required = false) int provinceId){
		List<City> list = cityService.select(provinceId);
		return getResult(list);
	}

	@RequestMapping("/area")
	public Result<List<Area>> areaSelect(int cityId){
		List<Area> list = areaService.select(cityId);
		return getResult(list);
	}

	private<T> Result<List<T>> getResult(List<T> list){

		String message;
		Boolean isSuccess;
		int status;
		if(list.size() == 0){
			message = "服务器出错";
			status = 500;
			isSuccess = false;
		}else {
			message = "搜索成功";
			status = 200;
			isSuccess = true;
		}
		return new Result<>(list ,message ,isSuccess ,status);

	}

}
