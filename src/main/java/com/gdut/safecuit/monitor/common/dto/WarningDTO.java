package com.gdut.safecuit.monitor.common.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Garson in 15:08 2018/1/24
 * Description :
 */
public class WarningDTO implements Serializable {

	private int id;                 //po主键
	private Integer deviceId;        //产生事件的设备
	private int circuitNo;          //产生事件的回路
	private int eventCode;          //事件值，取自EventCode
	private Date date;              //事件日期

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public int getCircuitNo() {
		return circuitNo;
	}

	public void setCircuitNo(int circuitNo) {
		this.circuitNo = circuitNo;
	}

	public int getEventCode() {
		return eventCode;
	}

	public void setEventCode(int eventCode) {
		this.eventCode = eventCode;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public WarningDTO() {
	}

	public WarningDTO(int id, Integer deviceId, int circuitNo, int eventCode, Date date) {
		this.id = id;
		this.deviceId = deviceId;
		this.circuitNo = circuitNo;
		this.eventCode = eventCode;
		this.date = date;
	}
}
