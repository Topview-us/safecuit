package com.gdut.safecuit.monitor.service;

import com.gdut.safecuit.common.Page;
import com.gdut.safecuit.common.base.BaseDao;
import com.gdut.safecuit.common.base.BaseServiceImpl;
import com.gdut.safecuit.device.common.po.Device;
import com.gdut.safecuit.device.dao.CircuitMapper;
import com.gdut.safecuit.device.dao.DeviceMapper;
import com.gdut.safecuit.device.dao.ElectricBoxMapper;
import com.gdut.safecuit.monitor.common.po.CircuitDataLog;
import com.gdut.safecuit.monitor.common.po.DataLog;
import com.gdut.safecuit.monitor.common.po.DeviceEvent;
import com.gdut.safecuit.monitor.common.vo.DataLogHistoryVO;
import com.gdut.safecuit.monitor.common.vo.DataLogVO;
import com.gdut.safecuit.monitor.dao.DataLogMapper;
import com.gdut.safecuit.monitor.dao.DeviceEventMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Garson in 9:39 2018/1/26
 * Description :
 */
@Service
public class DataLogService extends BaseServiceImpl<DataLog> {

	@Resource
	private DataLogMapper dataLogMapper;
	@Resource
	private DeviceMapper deviceMapper;
	@Resource
	private DeviceEventMapper deviceEventMapper;
	@Resource
	private ElectricBoxMapper electricBoxMapper;
	@Resource
	private CircuitMapper circuitMapper;

	/**
	 * 获取某电箱下所有设备的监控信息
	 * @param electricBoxId 电箱id
	 * @return 返回某电箱下所有设备的监控信息
	 */
	public List<DataLogVO> selectDataLog(Integer electricBoxId){
		List<DataLogVO> dataLogVOS = new ArrayList<>();

		List<Integer> deviceIds = electricBoxMapper.selectAllDeviceByElectricBoxId(electricBoxId);

		for (Integer deviceId : deviceIds){
			dataLogVOS.add(getDataLogVo(deviceId));
		}
		return dataLogVOS;
	}

	private DataLogVO getDataLogVo(Integer deviceId){
		Integer isHint = 0;
		Device device = deviceMapper.selectByPrimaryKey(deviceId);
		DeviceEvent deviceEvent = deviceEventMapper.selectByDeviceId(deviceId);
		//设备报警字段设为1
		if (deviceEvent != null)
			if (deviceEvent.getType() == 0)
				isHint = 1;

		return new DataLogVO(device.getId() ,device.getCode() ,device.getIsOnline() ,device.getTypeId()
				,isHint ,getCircuitDataLogs(deviceId ,device.getTypeId()));
	}

	private List<CircuitDataLog> getCircuitDataLogs(Integer deviceId ,Integer typeId){

		List<CircuitDataLog> circuitDataLogs = new ArrayList<>();
		Integer circuitCount = circuitMapper.selectCircuitCountByDeviceId(deviceId);
		for (Integer circuitNo = 1 ; circuitNo <= circuitCount ; circuitNo++){
				CircuitDataLog circuitDataLog = getCircuitDataLog(deviceId ,typeId ,circuitNo);
				if (circuitDataLog != null)
					circuitDataLogs.add(circuitDataLog);
		}

		return circuitDataLogs;
	}

	private CircuitDataLog getCircuitDataLog(Integer deviceId ,Integer typeId ,Integer circuitNo){
		DataLog dataLog = dataLogMapper.selectByDeviceIdAndCircuitNo(deviceId ,circuitNo ,typeId);
		if (dataLog == null)
			return null;
		else
			return new CircuitDataLog(circuitNo ,dataLog.getValue());
	}

	/**
	 * 获取某设备的历史监控信息
	 * @param deviceId 设备id
	 * @param typeId 监控类型id
	 * @param startDate	时间段的开始
	 * @param endDate	时间段的结束
	 * @param page		页数对象
	 * @return  添加数
	 */
	public List<DataLogHistoryVO> getHistoryInfo(Integer deviceId, Integer typeId , Date startDate
			, Date endDate ,Page page){
		List<DataLogHistoryVO> dataLogHistoryVOS = new ArrayList<>();

		Device device = deviceMapper.selectByPrimaryKey(deviceId);

		List<DataLog> dataLogs = dataLogMapper.selectHistoryData(deviceId ,typeId ,startDate ,endDate ,page);
		for (DataLog dataLog : dataLogs) {
			DataLogHistoryVO dataLogHistoryVO = new DataLogHistoryVO(dataLog.getId() ,dataLog.getSerialNo()
					,device.getCode() ,dataLog.getCircuitNo() ,dataLog.getCircuitStatus() ,dataLog.getValue() ,dataLog.getDate());
			dataLogHistoryVOS.add(dataLogHistoryVO);
		}

		return dataLogHistoryVOS;
	}

	public Integer getTotalByTypeIdAndDeviceId(Integer typeId ,Integer deviceId){
		return dataLogMapper.getTotalByTypeIdAndDeviceId(typeId ,deviceId);
	}

	@Override
	public BaseDao<DataLog> getDao() {
		return dataLogMapper;
	}
}
