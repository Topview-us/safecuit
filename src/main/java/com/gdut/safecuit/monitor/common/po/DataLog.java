package com.gdut.safecuit.monitor.common.po;

import java.util.Date;

public class DataLog {
    private Integer id;

    private Integer serialNo;

    private String deviceId;

    private Integer circuitNo;

    private Integer circuitStatus;

    private String value;

    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public Integer getCircuitNo() {
        return circuitNo;
    }

    public void setCircuitNo(Integer circuitNo) {
        this.circuitNo = circuitNo;
    }

    public Integer getCircuitStatus() {
        return circuitStatus;
    }

    public void setCircuitStatus(Integer circuitStatus) {
        this.circuitStatus = circuitStatus;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "DataLog{" +
                "id=" + id +
                ", serialNo=" + serialNo +
                ", deviceId='" + deviceId + '\'' +
                ", circuitNo=" + circuitNo +
                ", circuitStatus=" + circuitStatus +
                ", value='" + value + '\'' +
                ", date=" + date +
                '}';
    }
}