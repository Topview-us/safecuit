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
import com.gdut.safecuit.monitor.common.vo.DataLogHistoryVO;
import com.gdut.safecuit.monitor.common.vo.DataLogVO;
import com.gdut.safecuit.monitor.dao.DataLogMapper;
import org.apache.ibatis.annotations.Param;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.swing.plaf.PanelUI;
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
		for (Integer deviceId : deviceIds)
			dataLogVOS.add(getDataLogVo(deviceId));

		return dataLogVOS;
	}

	private DataLogVO getDataLogVo(Integer deviceId){
		Device device = deviceMapper.selectByPrimaryKey(deviceId);
		return new DataLogVO(device.getId() ,device.getCode() ,device.getIsOnline() ,device.getTypeId() ,getCircuitDataLogs(deviceId));
	}

	private List<CircuitDataLog> getCircuitDataLogs(Integer deviceId){

		List<CircuitDataLog> circuitDataLogs = new ArrayList<>();
		Integer circuitCount = circuitMapper.selectCircuitCountByDeviceId(deviceId);
		for (Integer circuitNo = 1 ; circuitNo <= circuitCount ; circuitNo++)
			circuitDataLogs.add(getCircuitDataLog(deviceId ,circuitNo));

		return circuitDataLogs;
	}

	private CircuitDataLog getCircuitDataLog(Integer deviceId ,Integer circuitNo){
		DataLog currentData = dataLogMapper.selectByDeviceIdInCurrentTable(deviceId ,circuitNo);
		DataLog miLiCurrentData = dataLogMapper.selectByDeviceIdInMiliCurrentTable(deviceId ,circuitNo);
		DataLog temperatureData = dataLogMapper.selectByDeviceIdInTemperatureTable(deviceId ,circuitNo);

		return new CircuitDataLog(circuitNo ,temperatureData.getValue() ,miLiCurrentData.getValue() ,currentData.getValue());
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