package com.gdut.safecuit.monitor.common.vo;

import java.util.Date;

/**
 * Created by Garson in 12:44 2018/1/27
 * Description :
 */
public class DataLogHistoryVO {

	private Integer id;
	private Integer serialNo;
	private String deviceCode;
	private Integer circuitNo;
	private Integer circuitStatus;
	private String value;
	private Integer total;
	private Date date;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
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

	public Integer getCircuitStatus() {
		return circuitStatus;
	}

	public void setCircuitStatus(Integer circuitStatus) {
		this.circuitStatus = circuitStatus;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public DataLogHistoryVO() {
	}

	public DataLogHistoryVO(Integer id, Integer serialNo,
							String deviceCode, Integer circuitNo, Integer circuitStatus, String value, Date date) {
		this.id = id;
		this.serialNo = serialNo;
		this.deviceCode = deviceCode;
		this.circuitNo = circuitNo;
		this.circuitStatus = circuitStatus;
		this.value = value;
		this.date = date;
	}

	public DataLogHistoryVO(Integer id, Integer serialNo, String deviceCode,
							Integer circuitNo, Integer circuitStatus, String value, Integer total , Date date) {
		this.id = id;
		this.serialNo = serialNo;
		this.deviceCode = deviceCode;
		this.circuitNo = circuitNo;
		this.circuitStatus = circuitStatus;
		this.value = value;
		this.total = total;
		this.date = date;
	}
}
