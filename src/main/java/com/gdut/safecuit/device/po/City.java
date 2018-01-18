package com.gdut.safecuit.device.po;

/**
 * Created by Garson in 9:48 2018/1/18
 * Description : 城市po表
 */
public class City {
	private Long id;			//主键
	private String name;		//城市
	private int provinceId;		//省份id

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}
}
