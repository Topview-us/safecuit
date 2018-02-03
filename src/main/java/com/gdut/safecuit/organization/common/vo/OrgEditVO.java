package com.gdut.safecuit.organization.common.vo;

public class OrgEditVO {
    private int orgId; // 机构id
    private String name; // 机构名
    private Integer provinceId;
    private String province; // 省份
    private Integer cityId;
    private String city; // 市
    private Integer areaId;
    private String area; // 区
    private String address; // 地址
    private String admin; // 机构管理员用户名
    private String email; // 机构邮箱
    private String phone; // 机构邮箱
    private String description; // 描述

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "OrgEditVO{" +
                "orgId=" + orgId +
                ", name='" + name + '\'' +
                ", provinceId=" + provinceId +
                ", province='" + province + '\'' +
                ", cityId=" + cityId +
                ", city='" + city + '\'' +
                ", areaId=" + areaId +
                ", area='" + area + '\'' +
                ", address='" + address + '\'' +
                ", admin='" + admin + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
