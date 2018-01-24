package com.gdut.safecuit.device.common.po;

public class Device {
    private String id;

    private String code;

    private String name;

    private String temperatureValue;

    private String electricBoxId;

    private Integer isOnline;

    private Integer typeId;

    private Integer delTag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getTemperatureValue() {
        return temperatureValue;
    }

    public void setTemperatureValue(String temperatureValue) {
        this.temperatureValue = temperatureValue == null ? null : temperatureValue.trim();
    }

    public String getElectricBoxId() {
        return electricBoxId;
    }

    public void setElectricBoxId(String electricBoxId) {
        this.electricBoxId = electricBoxId == null ? null : electricBoxId.trim();
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

    public Device(String id, String code, String name, String temperatureValue,
                  String electricBoxId, Integer isOnline, Integer typeId, Integer delTag) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.temperatureValue = temperatureValue;
        this.electricBoxId = electricBoxId;
        this.isOnline = isOnline;
        this.typeId = typeId;
        this.delTag = delTag;
    }

    public Device() {
    }
}