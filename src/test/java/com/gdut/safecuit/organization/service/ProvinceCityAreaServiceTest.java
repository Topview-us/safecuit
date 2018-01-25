package com.gdut.safecuit.organization.service;

import com.gdut.safecuit.BaseTest;
import com.gdut.safecuit.organization.common.po.Area;
import com.gdut.safecuit.organization.common.po.City;
import com.gdut.safecuit.organization.common.po.Province;
import org.junit.Test;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

public class ProvinceCityAreaServiceTest extends BaseTest {

    @Resource
    private ProvinceCityAreaService provinceCityAreaService;

    @Test
    public void getAllProvince() {
        List<Province> list = provinceCityAreaService.getAllProvince();
        for (Province p : list) {
            System.out.println(p);
        }
    }

    @Test
    public void getCityByProvinceId() {
        List<City> list = provinceCityAreaService.getCityByProvinceId("440000");
        for (City c : list) {
            System.out.println(c);
        }
    }

    @Test
    public void getAreaByCityId() {
        List<Area> list = provinceCityAreaService.getAreaByCityId("440100");
        for (Area a : list) {
            System.out.println(a);
        }
    }
}