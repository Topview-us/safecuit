package com.gdut.safecuit.device.common.po;

public class ElectricBox {
    private Integer id;

    private String name;

    private String address;

    private Integer orgId;

    private Integer parentId;

    private Float longitude;

    private Float latitude;

    private Integer delTag;

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

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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

    public ElectricBox() {
    }

    public ElectricBox(String name, String address, Integer orgId, Float longitude, Float latitude) {
        this.name = name;
        this.address = address;
        this.orgId = orgId;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}