package com.gdut.safecuit.device.common.vo;

/**
 * Created by Garson in 16:29 2018/1/19
 * Description :
 */
public class DeviceVO {

	private Integer deviceId;
	private String name;
	private String code;
	private String temperatureValue;//温度阈值
	private int isOnline;//是否在线状态
	private String electricBoxName;//所属电箱名称
	private String electricBoxAddress;//所属电箱地址
	private int typeId;//监控类型

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTemperatureValue() {
		return temperatureValue;
	}

	public void setTemperatureValue(String temperatureValue) {
		this.temperatureValue = temperatureValue;
	}

	public int getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(int isOnline) {
		this.isOnline = isOnline;
	}

	public String getElectricBoxName() {
		return electricBoxName;
	}

	public void setElectricBoxName(String electricBoxName) {
		this.electricBoxName = electricBoxName;
	}

	public String getElectricBoxAddress() {
		return electricBoxAddress;
	}

	public void setElectricBoxAddress(String electricBoxAddress) {
		this.electricBoxAddress = electricBoxAddress;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}


	public DeviceVO(String name, String code, String temperatureValue ,int isOnline ,String electricBoxName, String electricBoxAddress, int typeId) {
		this.name = name;
		this.code = code;
		this.temperatureValue = temperatureValue;
		this.isOnline = isOnline;
		this.electricBoxName = electricBoxName;
		this.electricBoxAddress = electricBoxAddress;
		this.typeId = typeId;
	}

	public DeviceVO(Integer deviceId, String name, String code, String temperatureValue,
					int isOnline, String electricBoxName, String electricBoxAddress, int typeId) {
		this.deviceId = deviceId;
		this.name = name;
		this.code = code;
		this.temperatureValue = temperatureValue;
		this.isOnline = isOnline;
		this.electricBoxName = electricBoxName;
		this.electricBoxAddress = electricBoxAddress;
		this.typeId = typeId;
	}

	public DeviceVO(Integer deviceId ,String name, String code, String temperatureValue, int isOnline, int typeId) {
		this.deviceId = deviceId;
		this.name = name;
		this.code = code;
		this.temperatureValue = temperatureValue;
		this.isOnline = isOnline;
		this.typeId = typeId;
	}

	@Override
	public String toString() {
		return "DeviceVO{" +
				"name='" + name + '\'' +
				", code='" + code + '\'' +
				", electricBoxName='" + electricBoxName + '\'' +
				", electricBoxAddress='" + electricBoxAddress + '\'' +
				", typeId=" + typeId +
				'}';
	}
}
