package com.gdut.safecuit.organization.service;

import com.gdut.safecuit.organization.common.po.Area;
import com.gdut.safecuit.organization.common.po.City;
import com.gdut.safecuit.organization.common.po.Province;
import com.gdut.safecuit.organization.common.po.example.AreaExample;
import com.gdut.safecuit.organization.common.po.example.CityExample;
import com.gdut.safecuit.organization.common.po.example.ProvinceExample;
import com.gdut.safecuit.organization.dao.AreaMapper;
import com.gdut.safecuit.organization.dao.CityMapper;
import com.gdut.safecuit.organization.dao.ProvinceMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProvinceCityAreaService {

    @Resource
    private AreaMapper areaMapper;

    @Resource
    private CityMapper cityMapper;

    @Resource
    private ProvinceMapper provinceMapper;

    public List<Province> getAllProvinces() {
        ProvinceExample provinceExample = new ProvinceExample();
        return provinceMapper.selectByExample(provinceExample);
    }

    public List<City> getCitiesByProvinceId(String id) {
        return cityMapper.getCityByProvinceId(id);
    }

    public List<Area> getAreasByCityId(String id) {
        return areaMapper.getAreaByCityId(id);
    }

    public Province getProvince(String provinceId) {
        ProvinceExample example = new ProvinceExample();
        example.or().andProvinceIdEqualTo(provinceId);
        List<Province> list = provinceMapper.selectByExample(example);
        return list.size() != 0 ? list.get(0) : null;
    }

    public City getCity(String cityId) {
        CityExample example = new CityExample();
        example.or().andCityIdEqualTo(cityId);
        List<City> list = cityMapper.selectByExample(example);
        return list.size() != 0 ? list.get(0) : null;
    }

    public Area getArea(String areaId) {
        AreaExample example = new AreaExample();
        example.or().andAreaIdEqualTo(areaId);
        List<Area> list = areaMapper.selectByExample(example);
        return list.size() != 0 ? list.get(0) : null;
    }

    public Map<String, Object> getProvinceCityArea(int areaId) {
        Map<String, Object> map = new HashMap<>();
        Area area = getArea("" + areaId);
        City city = getCity(area.getFather());
        Province province = getProvince(city.getFather());
        map.put("area", area);
        map.put("city", city);
        map.put("province", province);
        return map;
    }
}
