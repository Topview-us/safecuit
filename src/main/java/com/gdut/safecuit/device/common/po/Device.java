package com.gdut.safecuit.device.common.po;

public class Device {
    private Integer id;

    private String code;

    private String name;

    private Integer electricBoxId;

    private String temperatureValue;

    private Integer isOnline;

    private Integer typeId;

    private Integer delTag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getElectricBoxId() {
        return electricBoxId;
    }

    public void setElectricBoxId(Integer electricBoxId) {
        this.electricBoxId = electricBoxId;
    }

    public String getTemperatureValue() {
        return temperatureValue;
    }

    public void setTemperatureValue(String temperatureValue) {
        this.temperatureValue = temperatureValue == null ? null : temperatureValue.trim();
    }

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getDelTag() {
        return delTag;
    }

    public void setDelTag(Integer delTag) {
        this.delTag = delTag;
    }

    public Device() {
    }

    public Device(Integer id, String code, String name, Integer electricBoxId,
                  String temperatureValue, Integer isOnline, Integer typeId, Integer delTag) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.electricBoxId = electricBoxId;
        this.temperatureValue = temperatureValue;
        this.isOnline = isOnline;
        this.typeId = typeId;
        this.delTag = delTag;
    }

    public Device(String code, String name, Integer electricBoxId, String temperatureValue, Integer typeId) {
        this.code = code;
        this.name = name;
        this.electricBoxId = electricBoxId;
        this.temperatureValue = temperatureValue;
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", electricBoxId=" + electricBoxId +
                ", temperatureValue='" + temperatureValue + '\'' +
                ", isOnline=" + isOnline +
                ", typeId=" + typeId +
                ", delTag=" + delTag +
                '}';
    }
}