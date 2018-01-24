package com.gdut.safecuit.device.common.vo;

/**
 * Created by Garson in 15:09 2018/1/19
 * Description : 电箱vo
 */
public class ElectricBoxVO {

	private String name;
	private String address;//省+市+区+电箱地址
	private float longitude;//电箱经度
	private float latitude;//电箱纬度
	private String orgName;//所属机构名称
	private String orgManager;//机构管理员
	private String phone;//机构电话


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

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgManager() {
		return orgManager;
	}

	public void setOrgManager(String orgManager) {
		this.orgManager = orgManager;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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


	public ElectricBoxVO(String name, String address, float longitude, float latitude, String orgName, String orgManager, String phone) {
		this.name = name;
		this.address = address;
		this.longitude = longitude;
		this.latitude = latitude;
		this.orgName = orgName;
		this.orgManager = orgManager;
		this.phone = phone;
	}
}
