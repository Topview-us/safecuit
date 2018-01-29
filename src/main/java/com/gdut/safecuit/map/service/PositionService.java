package com.gdut.safecuit.map.service;

import com.gdut.safecuit.map.common.po.Position;
import com.gdut.safecuit.map.common.po.example.PositionExample;
import com.gdut.safecuit.map.dao.PositionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PositionService {
    @Resource
    private PositionMapper positionMapper;

    public int insert(Position p) {
        return positionMapper.insert(p);
    }

    public int deleteByPositionId(int positionId) {
        return positionMapper.deleteByPrimaryKey(positionId);
    }

    public int deleteByKeyId(String type, int keyId) {
        PositionExample example = new PositionExample();
        example.or()
                .andTypeEqualTo(type)
                .andKeyIdEqualTo(keyId);
        return positionMapper.deleteByExample(example);
    }
}
