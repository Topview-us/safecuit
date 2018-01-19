package com.gdut.safecuit.device.common.po;

/**
 * Created by Garson in 9:50 2018/1/18
 * Description : 省份po表
 */
public class Province {

	private Long id;		//主键
	private String name;	//省份

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

	@Override
	public String toString() {
		return "Province{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}
