package com.gdut.safecuit.organization.web;

import com.gdut.safecuit.common.Result;
import com.gdut.safecuit.common.util.MatchUtil;
import com.gdut.safecuit.organization.common.po.Area;
import com.gdut.safecuit.organization.common.po.City;
import com.gdut.safecuit.organization.common.po.Province;
import com.gdut.safecuit.organization.service.ProvinceCityAreaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/provinceCityArea")
public class ProvinceCityAreaController {

    @Resource
    private ProvinceCityAreaService provinceCityAreaService;

    @RequestMapping("/getAllProvinces")
    public Result<List<Province>> getAllProvinces() {
        List<Province> provinces = provinceCityAreaService.getAllProvince();
        return new Result<>(provinces, "请求成功", true, 200);
    }

    @RequestMapping("/getCitiesByProvince")
    public Result<List<City>> getCitiesByProvince(@RequestParam(value = "provinceId") String provinceId) {
        if (!MatchUtil.isPositiveNumber(provinceId)) {
            return new Result<>(null, "省份id无效", false, 500);
        } else {
            List<City> cities = provinceCityAreaService.getCityByProvinceId(provinceId);
            return new Result<>(cities, "请求成功", true, 200);
        }
    }

    @RequestMapping("/getAreasByCity")
    public Result<List<Area>> getAreasByCity(@RequestParam(value = "cityId") String cityId) {
        if (!MatchUtil.isPositiveNumber(cityId)) {
            return new Result<>(null, "市id无效", false, 500);
        } else {
            List<Area> areas = provinceCityAreaService.getAreaByCityId(cityId);
            return new Result<>(areas, "请求成功", true, 200);
        }
    }
}
