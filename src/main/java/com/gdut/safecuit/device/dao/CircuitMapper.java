package com.gdut.safecuit.device.dao;

import com.gdut.safecuit.device.common.po.Circuit;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CircuitMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Circuit record);

    int insertSelective(Circuit record);

    Circuit selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Circuit record);

    int updateByPrimaryKey(Circuit record);

    int insertCircuit(List<Circuit> circuits);
}