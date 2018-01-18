package com.gdut.safecuit.device.po;

/**
 * Created by Garson in 9:53 2018/1/18
 * Description : 电箱po
 */
public class ElectricBox {

	private int id;
	private String name;
	private String address;
	private int orgId;//机构id
	private float longitube;//经度
	private float latitude;//纬度

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

	public float getLongitube() {
		return longitube;
	}

	public void setLongitube(float longitube) {
		this.longitube = longitube;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
}
