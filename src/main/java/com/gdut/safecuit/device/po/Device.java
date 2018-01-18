package com.gdut.safecuit.device.po;

/**
 * Created by Garson in 9:44 2018/1/18
 * Description : 设备po表
 */
public class Device {

	private String id;
	private String code;//设备序号
	private String name;//设备名称
	private String address;//地址
	private int electricBoxId;//电箱外键
	private int typeId;//类型id
	private int delTag;//假删除标识

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public int getElectricBoxId() {
		return electricBoxId;
	}

	public void setElectricBoxId(int electricBoxId) {
		this.electricBoxId = electricBoxId;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public int getDelTag() {
		return delTag;
	}

	public void setDelTag(int delTag) {
		this.delTag = delTag;
	}

	public Device() {
	}

	public Device(String code, String name, String address, int electricBoxId, int typeId, int delTag) {
		this.code = code;
		this.name = name;
		this.address = address;
		this.electricBoxId = electricBoxId;
		this.typeId = typeId;
		this.delTag = delTag;
	}

	public Device(String id, String code, String name, String address, int electricBoxId, int typeId, int delTag) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.address = address;
		this.electricBoxId = electricBoxId;
		this.typeId = typeId;
		this.delTag = delTag;
	}

	@Override
	public String toString() {
		return "Device{" +
				"id=" + id +
				", code='" + code + '\'' +
				", name='" + name + '\'' +
				", address='" + address + '\'' +
				", electricBoxId=" + electricBoxId +
				", typeId=" + typeId +
				", delTag=" + delTag +
				'}';
	}
}
