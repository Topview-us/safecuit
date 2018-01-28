package com.gdut.safecuit.device.common.vo;

/**
 * Created by Garson in 10:45 2018/1/25
 * Description :
 */
public class CircuitVO {

	private Integer id;
	private String name;
	private String address;
	private int circuitNo;//回路序号
	private String deviceCode;//设备序号
	private String deviceName;//设备名称
	private int typeId;//设备监控类型

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getCircuitNo() {
		return circuitNo;
	}

	public void setCircuitNo(int circuitNo) {
		this.circuitNo = circuitNo;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public CircuitVO(Integer id, String name, String address, int circuitNo, String deviceCode, String deviceName, int typeId) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.circuitNo = circuitNo;
		this.deviceCode = deviceCode;
		this.deviceName = deviceName;
		this.typeId = typeId;
	}
}
