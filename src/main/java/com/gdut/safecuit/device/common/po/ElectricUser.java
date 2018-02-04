package com.gdut.safecuit.device.common.po;

import java.util.Date;

public class ElectricUser {
    private Integer id;

    private Integer userId;

    private Integer electricBoxId;

    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getElectricBoxId() {
        return electricBoxId;
    }

    public void setElectricBoxId(Integer electricBoxId) {
        this.electricBoxId = electricBoxId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}