package com.gdut.safecuit.device.dao;

import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.device.common.po.Area;
import com.gdut.safecuit.device.common.po.Province;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Garson in 20:34 2018/1/18
 * Description :
 */
@Repository
public interface ProvinceMapper extends BaseDao<Province> {

}
