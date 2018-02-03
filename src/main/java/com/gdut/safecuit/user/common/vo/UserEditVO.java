package com.gdut.safecuit.user.common.vo;

/**
 * 用于编辑用户信息时回显信息
 */
public class UserEditVO {
    private Integer userId;
    private String username;
    private String realName;
    private Integer orgProvinceId;
    private String orgProvince;
    private Integer orgCityId;
    private String orgCity;
    private Integer orgAreaId;
    private String orgArea;
    private String orgName;
    private String phone; // 可选
    private Integer qq; // 可选
    private String description; // 可选

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getOrgProvinceId() {
        return orgProvinceId;
    }

    public void setOrgProvinceId(Integer orgProvinceId) {
        this.orgProvinceId = orgProvinceId;
    }

    public String getOrgProvince() {
        return orgProvince;
    }

    public void setOrgProvince(String orgProvince) {
        this.orgProvince = orgProvince;
    }

    public Integer getOrgCityId() {
        return orgCityId;
    }

    public void setOrgCityId(Integer orgCityId) {
        this.orgCityId = orgCityId;
    }

    public String getOrgCity() {
        return orgCity;
    }

    public void setOrgCity(String orgCity) {
        this.orgCity = orgCity;
    }

    public Integer getOrgAreaId() {
        return orgAreaId;
    }

    public void setOrgAreaId(Integer orgAreaId) {
        this.orgAreaId = orgAreaId;
    }

    public String getOrgArea() {
        return orgArea;
    }

    public void setOrgArea(String orgArea) {
        this.orgArea = orgArea;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getQq() {
        return qq;
    }

    public void setQq(Integer qq) {
        this.qq = qq;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "UserEditVO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", realName='" + realName + '\'' +
                ", orgProvinceId=" + orgProvinceId +
                ", orgProvince='" + orgProvince + '\'' +
                ", orgCityId=" + orgCityId +
                ", orgCity='" + orgCity + '\'' +
                ", orgAreaId=" + orgAreaId +
                ", orgArea='" + orgArea + '\'' +
                ", orgName='" + orgName + '\'' +
                ", phone='" + phone + '\'' +
                ", qq=" + qq +
                ", description='" + description + '\'' +
                '}';
    }
}
