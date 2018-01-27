package com.gdut.safecuit.organization.common.vo;

/**
 * 机构添加页面
 */
public class OrgAddVo {
    // 机构信息
    private String orgName;
    private String orgAddress;
    private Integer orgAreaId;
    private String orgEmail;
    private String orgPhone;
    private String orgDescription;

    // 用户信息
    private String username;
    private String userRealName;
    private String userPassword;
    private String userRePassword;
    private String userPhone;
    private String userQQ;
    private String userDescription;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public Integer getOrgAreaId() {
        return orgAreaId;
    }

    public void setOrgAreaId(Integer orgAreaId) {
        this.orgAreaId = orgAreaId;
    }

    public String getOrgEmail() {
        return orgEmail;
    }

    public void setOrgEmail(String orgEmail) {
        this.orgEmail = orgEmail;
    }

    public String getOrgPhone() {
        return orgPhone;
    }

    public void setOrgPhone(String orgPhone) {
        this.orgPhone = orgPhone;
    }

    public String getOrgDescription() {
        return orgDescription;
    }

    public void setOrgDescription(String orgDescription) {
        this.orgDescription = orgDescription;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserRePassword() {
        return userRePassword;
    }

    public void setUserRePassword(String userRePassword) {
        this.userRePassword = userRePassword;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserQQ() {
        return userQQ;
    }

    public void setUserQQ(String userQQ) {
        this.userQQ = userQQ;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    @Override
    public String toString() {
        return "OrgAddVo{" +
                "orgName='" + orgName + '\'' +
                ", orgAddress='" + orgAddress + '\'' +
                ", orgAreaId=" + orgAreaId +
                ", orgEmail='" + orgEmail + '\'' +
                ", orgPhone='" + orgPhone + '\'' +
                ", orgDescription='" + orgDescription + '\'' +
                ", username='" + username + '\'' +
                ", userRealName='" + userRealName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userRePassword='" + userRePassword + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userQQ='" + userQQ + '\'' +
                ", userDescription='" + userDescription + '\'' +
                '}';
    }
}
