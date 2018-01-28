package com.gdut.safecuit.device.common.po;

public class Circuit {
    private Integer id;

    private String name;

    private String address;

    private Integer circuitNo;

    private Integer deviceId;

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
        this.name = name == null ? null : name.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getCircuitNo() {
        return circuitNo;
    }

    public void setCircuitNo(Integer circuitNo) {
        this.circuitNo = circuitNo;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Circuit() {
    }

    public Circuit(Integer id, String name, String address, Integer circuitNo, Integer deviceId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.circuitNo = circuitNo;
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "Circuit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", circuitNo=" + circuitNo +
                ", deviceId=" + deviceId +
                '}';
    }
}