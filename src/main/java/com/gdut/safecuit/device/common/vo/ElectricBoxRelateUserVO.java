package com.gdut.safecuit.device.common.vo;

import java.util.Date;

/**
 * Created by Garson in 11:03 2018/2/1
 * Description :
 */
public class ElectricBoxRelateUserVO {

	private Integer userId;
	private String userRealName;
	private String phone;
	private Date date;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "ElectricBoxRelateUserVO{" +
				"userId=" + userId +
				", userRealName='" + userRealName + '\'' +
				", phone=" + phone +
				", date=" + date +
				'}';
	}
}
