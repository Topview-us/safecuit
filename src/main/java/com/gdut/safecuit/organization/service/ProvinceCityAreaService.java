package com.gdut.safecuit.organization.service;

import com.gdut.safecuit.organization.common.po.Area;
import com.gdut.safecuit.organization.common.po.City;
import com.gdut.safecuit.organization.common.po.Province;
import com.gdut.safecuit.organization.common.po.example.ProvinceExample;
import com.gdut.safecuit.organization.dao.AreaMapper;
import com.gdut.safecuit.organization.dao.CityMapper;
import com.gdut.safecuit.organization.dao.ProvinceMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProvinceCityAreaService {

    @Resource
    private AreaMapper areaMapper;

    @Resource
    private CityMapper cityMapper;

    @Resource
    private ProvinceMapper provinceMapper;

    public List<Province> getAllProvince() {
        ProvinceExample provinceExample = new ProvinceExample();
        return provinceMapper.selectByExample(provinceExample);
    }

    public List<City> getCityByProvinceId(String id) {
        return cityMapper.getCityByProvinceId(id);
    }

    public List<Area> getAreaByCityId(String id) {
        return areaMapper.getAreaByCityId(id);
    }
}
