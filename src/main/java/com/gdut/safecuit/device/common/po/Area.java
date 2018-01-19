package com.gdut.safecuit.device.common.po;

/**
 * Created by Garson in 9:51 2018/1/18
 * Description : 地区po表
 */
public class Area {

	private int id;			//主键
	private String name;		//地区
	private int cityId;			//城市id

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

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
}
