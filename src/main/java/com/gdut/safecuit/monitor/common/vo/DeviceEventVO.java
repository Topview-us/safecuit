package com.gdut.safecuit.monitor.common.vo;


import com.alibaba.fastjson.JSONObject;

import java.util.Date;

/**
 * Created by Garson in 14:46 2018/1/25
 * Description : 报警事件表
 */
public class DeviceEventVO {

	private Integer id;
	private String deviceCode;//报警设备编号
	private Integer circuitNo;//报警回路
	private int event_code;//事件编号
	private int serialNo;//序列号
	private int type;//报警事件处理状态
	private Date date;//报警时间
	private JSONObject pair;//

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public Integer getCircuitNo() {
		return circuitNo;
	}

	public void setCircuitNo(Integer circuitNo) {
		this.circuitNo = circuitNo;
	}

	public int getEvent_code() {
		return event_code;
	}

	public void setEvent_code(int event_code) {
		this.event_code = event_code;
	}

	public int getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public JSONObject getPair() {
		return pair;
	}

	public void setPair(JSONObject pair) {
		this.pair = pair;
	}

	public DeviceEventVO(Integer id, String deviceCode, Integer circuitNo, int event_code,
						 int serialNo, int type, Date date, JSONObject pair) {
		this.id = id;
		this.deviceCode = deviceCode;
		this.circuitNo = circuitNo;
		this.event_code = event_code;
		this.serialNo = serialNo;
		this.type = type;
		this.date = date;
		this.pair = pair;
	}

	public DeviceEventVO(Integer id, String deviceCode, Integer circuitNo, int event_code, int serialNo, int type, Date date) {
		this.id = id;
		this.deviceCode = deviceCode;
		this.circuitNo = circuitNo;
		this.event_code = event_code;
		this.serialNo = serialNo;
		this.type = type;
		this.date = date;
	}

	@Override
	public String toString() {
		return "DeviceEventVO{" +
				"id=" + id +
				", deviceCode='" + deviceCode + '\'' +
				", circuitNo=" + circuitNo +
				", event_code=" + event_code +
				", serialNo=" + serialNo +
				", type=" + type +
				", date=" + date +
				", pair=" + pair +
				'}';
	}
}
