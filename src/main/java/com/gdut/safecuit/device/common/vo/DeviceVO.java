package com.gdut.safecuit.device.common.vo;

/**
 * Created by Garson in 16:29 2018/1/19
 * Description :
 */
public class DeviceVO {

	private String name;
	private String code;
	private String electricBoxName;//所属电箱名称
	private String electricBoxAddress;//所属电箱地址
	private int typeId;//监控类型

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

	public DeviceVO(String name, String code, String electricBoxName, String electricBoxAddress, int typeId) {
		this.name = name;
		this.code = code;
		this.electricBoxName = electricBoxName;
		this.electricBoxAddress = electricBoxAddress;
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
