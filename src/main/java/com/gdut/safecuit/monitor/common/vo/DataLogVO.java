package com.gdut.safecuit.monitor.common.vo;

import com.gdut.safecuit.monitor.common.po.CircuitDataLog;

import java.util.List;

/**
 * Created by Garson in 15:22 2018/1/25
 * Description :
 */
public class DataLogVO {

	private Integer deviceId;//设备id
	private String deviceCode;//设备编码
	private int isOnline;//在线状态
	private int typeId;//设备监控类型
	private Integer isHint;//1为报警，0为没报警
	private List<CircuitDataLog> circuitDataLogs;//回路数据集合

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public int getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(int isOnline) {
		this.isOnline = isOnline;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public Integer getIsHint() {
		return isHint;
	}

	public void setIsHint(Integer isHint) {
		this.isHint = isHint;
	}

	public List<CircuitDataLog> getCircuitDataLogs() {
		return circuitDataLogs;
	}

	public void setCircuitDataLogs(List<CircuitDataLog> circuitDataLogs) {
		this.circuitDataLogs = circuitDataLogs;
	}

	public DataLogVO(Integer deviceId ,String deviceCode, int isOnline, int typeId, List<CircuitDataLog> circuitDataLogs) {
		this.deviceId = deviceId;
		this.deviceCode = deviceCode;
		this.isOnline = isOnline;
		this.typeId = typeId;
		this.circuitDataLogs = circuitDataLogs;
	}

	public DataLogVO(Integer deviceId, String deviceCode, int isOnline,
					 int typeId, Integer isHint, List<CircuitDataLog> circuitDataLogs) {
		this.deviceId = deviceId;
		this.deviceCode = deviceCode;
		this.isOnline = isOnline;
		this.typeId = typeId;
		this.isHint = isHint;
		this.circuitDataLogs = circuitDataLogs;
	}

	@Override
	public String toString() {
		return "DataLogVO{" +
				"deviceId=" + deviceId +
				", deviceCode='" + deviceCode + '\'' +
				", isOnline=" + isOnline +
				", typeId=" + typeId +
				", isHint=" + isHint +
				", circuitDataLogs=" + circuitDataLogs +
				'}';
	}
}
