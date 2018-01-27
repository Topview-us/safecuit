package com.gdut.safecuit.organization.service;

import com.gdut.safecuit.BaseTest;
import com.gdut.safecuit.organization.common.po.Area;
import com.gdut.safecuit.organization.common.po.City;
import com.gdut.safecuit.organization.common.po.Province;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.List;

public class ProvinceCityAreaServiceTest extends BaseTest {

    @Resource
    private ProvinceCityAreaService provinceCityAreaService;

    @Test
    public void getAllProvince() {
        List<Province> list = provinceCityAreaService.getAllProvinces();
        for (Province p : list) {
            System.out.println(p);
        }
    }

    @Test
    public void getCityByProvinceId() {
        List<City> list = provinceCityAreaService.getCitiesByProvinceId("440000");
        for (City c : list) {
            System.out.println(c);
        }
    }

    @Test
    public void getAreaByCityId() {
        List<Area> list = provinceCityAreaService.getAreasByCityId("440100");
        for (Area a : list) {
            System.out.println(a);
        }
    }

    @Test
    public void getProvince() {
        System.out.println(provinceCityAreaService.getProvince("440000"));
    }

    @Test
    public void getCity() {
        System.out.println(provinceCityAreaService.getCity("620800"));
    }

    @Test
    public void getArea() {
        System.out.println(provinceCityAreaService.getArea("110101"));
    }
}