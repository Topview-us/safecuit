package com.gdut.safecuit.device.web.controller;

import com.gdut.safecuit.common.Result;
import com.gdut.safecuit.device.common.po.Area;
import com.gdut.safecuit.device.common.po.City;
import com.gdut.safecuit.device.common.po.Province;
import com.gdut.safecuit.device.service.AreaService;
import com.gdut.safecuit.device.service.CityService;
import com.gdut.safecuit.device.service.ProvinceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.gdut.safecuit.common.util.MatchUtil.*;

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

	/**
	 * 显示省份
	 * url：/location/province
	 * @return 省份信息
	 */
	@RequestMapping("/province")
	public Result<List<Province>> provinceSelect(){
		List<Province> list = provinceService.select();
		return getResult(list);
	}

	/**
	 * 传入外键省份的id，如果id为0，则显示所有城市；如果id不为0，则显示对应省份id的城市
	 * url：/location/city?provinceId=xxx
	 * @param provinceId 省份id
	 * @return 带有城市对象集合的结果集
	 */
	@RequestMapping("/city")
	public Result<List<City>> citySelect(@RequestParam(value = "provinceId" ,required = false,defaultValue = "0") String provinceId){
		List<City> list;

		//正则表达式匹配正整数
		if(!isPositiveInteger(provinceId))
			return new Result<>(null ,"参数类型有误",false ,400);

		list = cityService.select(Integer.valueOf(provinceId));

		//如果传入的cityId无法找到对应数据，则返回全部数据
		if(list.size() == 0)
			list = cityService.select(0);

		return getResult(list);
	}

	/**
	 * 传入外键城市的id，如果id为0，则显示所有地区；如果id不为0，则显示对应城市id的地区
	 * url：/location/area?cityId=xxx
	 * @param cityId 城市id
	 * @return 带有地区对象集合的结果集
	 */
	@RequestMapping("/area")
	public Result<List<Area>> areaSelect(@RequestParam(value = "cityId" ,required = false,defaultValue = "0") String cityId){
		List<Area> list;

		//正则表达式匹配正整数
		if(!isPositiveInteger(cityId))
			return new Result<>(null ,"参数类型有误",false ,400);

		list = areaService.select(Integer.valueOf(cityId));

		//如果传入的cityId无法找到对应数据，则返回全部数据
		if(list.size() == 0)
			list = areaService.select(0);

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
