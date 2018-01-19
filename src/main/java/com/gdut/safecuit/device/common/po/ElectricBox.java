package com.gdut.safecuit.device.common.po;

/**
 * Created by Garson in 9:53 2018/1/18
 * Description : 电箱po
 */
public class ElectricBox {

	private int id;
	private String name;
	private String address;
	private int orgId;//机构id
	private float longitude;//经度
	private float latitude;//纬度
	private int delTag;

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public int getDelTag() {
		return delTag;
	}

	public void setDelTag(int delTag) {
		this.delTag = delTag;
	}


}
