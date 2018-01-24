package com.gdut.safecuit.device.common.po;

public class ElectricBox {
    private String id;

    private String name;

    private String address;

    private Integer orgId;

    private Float longitude;

    private Float latitude;

    private Integer delTag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Integer getDelTag() {
        return delTag;
    }

    public void setDelTag(Integer delTag) {
        this.delTag = delTag;
    }

    public ElectricBox(String id, String name, String address, Integer orgId, Float longitude, Float latitude, Integer delTag) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.orgId = orgId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.delTag = delTag;
    }
}