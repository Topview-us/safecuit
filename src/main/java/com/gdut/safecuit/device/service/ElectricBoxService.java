package com.gdut.safecuit.device.service;

import com.gdut.safecuit.common.Page;
import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.common.base.BaseServiceImpl;
import com.gdut.safecuit.device.common.po.ElectricBox;
import com.gdut.safecuit.device.common.vo.ElectricBoxVO;
import com.gdut.safecuit.device.dao.ElectricBoxMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gdut.safecuit.device.common.util.DataCache.*;

/**
 * Created by Garson in 15:35 2018/1/19
 * Description :
 */
@Service
public class ElectricBoxService extends BaseServiceImpl<ElectricBox> {

	@Resource
	private ElectricBoxMapper electricBoxMapper;

	//未完成
	public List<ElectricBoxVO> selectByPage(int pageNo ,int pageSize ,int areaId){
		Page page;
		Map<String ,Object> map;

		if(ELECTRICBOXTOTAL == 0)
			ELECTRICBOXTOTAL = electricBoxMapper.getElectricBoxTotal();

		page = new Page(pageSize,pageNo,ELECTRICBOXTOTAL);
		map = new HashMap<>();
		map.put("page" ,page);
		map.put("areaId" ,areaId);

		List<ElectricBox> list = electricBoxMapper.selectByPage(map);

		return new ArrayList<>();
	}


	@Override
	public BaseDao<ElectricBox> getDao() {
		return electricBoxMapper;
	}
}
