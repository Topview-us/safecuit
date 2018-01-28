package com.gdut.safecuit.common;

import java.io.Serializable;

/**
 * Created by Garson in 14:10 2018/1/18
 * Description : 结果类
 */
public class Result<T> implements Serializable {

	private T object;
	private String message;
	private Boolean isSuccess;
	private int status;
	private Integer pageTotal;

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Boolean getSuccess() {
		return isSuccess;
	}

	public void setSuccess(Boolean success) {
		isSuccess = success;
	}

	public Integer getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(Integer pageTotal) {
		this.pageTotal = pageTotal;
	}

	public Result() {
	}

	public Result(T object, String message, Boolean isSuccess, int status, Integer pageTotal) {
		this.object = object;
		this.message = message;
		this.isSuccess = isSuccess;
		this.status = status;
		this.pageTotal = pageTotal;
	}

	public Result(T object, String message, Boolean isSuccess, int status) {
		this.object = object;
		this.message = message;
		this.isSuccess = isSuccess;
		this.status = status;
	}
}
