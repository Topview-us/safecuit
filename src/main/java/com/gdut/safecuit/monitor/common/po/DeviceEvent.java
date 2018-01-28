package com.gdut.safecuit.monitor.common.po;

import java.util.Date;

public class DeviceEvent {
    private Integer id;

    private Integer eventCode;

    private Integer deviceId;

    private Integer circuitNo;

    private Date time;

    private Integer serialNo;

    private Integer type;

    private String pair;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEventCode() {
        return eventCode;
    }

    public void setEventCode(Integer eventCode) {
        this.eventCode = eventCode;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getCircuitNo() {
        return circuitNo;
    }

    public void setCircuitNo(Integer circuitNo) {
        this.circuitNo = circuitNo;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair == null ? null : pair.trim();
    }

    public DeviceEvent() {
    }

    public DeviceEvent(Integer id, Integer eventCode, Integer deviceId, Integer circuitNo,
                       Date time, Integer serialNo, Integer type, String pair) {
        this.id = id;
        this.eventCode = eventCode;
        this.deviceId = deviceId;
        this.circuitNo = circuitNo;
        this.time = time;
        this.serialNo = serialNo;
        this.type = type;
        this.pair = pair;
    }

    @Override
    public String toString() {
        return "DeviceEvent{" +
                "id=" + id +
                ", eventCode=" + eventCode +
                ", deviceId=" + deviceId +
                ", circuitNo=" + circuitNo +
                ", time=" + time +
                ", serialNo=" + serialNo +
                ", type=" + type +
                ", pair='" + pair + '\'' +
                '}';
    }
}