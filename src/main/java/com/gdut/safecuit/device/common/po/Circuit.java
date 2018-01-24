package com.gdut.safecuit.device.common.po;

public class Circuit {
    private Integer id;

    private Integer circuitNo;

    private String deviceId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCircuitNo() {
        return circuitNo;
    }

    public void setCircuitNo(Integer circuitNo) {
        this.circuitNo = circuitNo;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public Circuit(Integer circuitNo, String deviceId) {
        this.circuitNo = circuitNo;
        this.deviceId = deviceId;
    }
}