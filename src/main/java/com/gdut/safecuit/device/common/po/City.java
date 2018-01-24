package com.gdut.safecuit.device.common.po;

/**
 * Created by Garson in 9:48 2018/1/18
 * Description : 城市po表
 */
public class City {
	private Integer id;			//主键
	private String name;		//城市
	private Integer provinceId;		//省份id

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

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
}
