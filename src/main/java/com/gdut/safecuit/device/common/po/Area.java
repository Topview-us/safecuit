package com.gdut.safecuit.device.common.po;

/**
 * Created by Garson in 9:51 2018/1/18
 * Description : 地区po表
 */
public class Area {

	private Integer id;			//主键
	private String name;		//地区
	private Integer cityId;			//城市id

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

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
}
