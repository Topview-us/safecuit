package com.gdut.safecuit.device.common.po;

public class DataTree {
    private Integer id;

    private String name;
    /**
     * 结点类型id：1省、2市、3区、4机构、5电箱、6设备、-1分组
     */
    private Integer typeId;

    /**
     * 结点所带信息，包括电箱id、设备id
     */
    private String typeInfo;

    /**
     * 是否有下级，1为有，0为无
     */
    private Integer stateId;

    private Integer parentId;

    private Integer roleId;

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

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeInfo() {
        return typeInfo;
    }

    public void setTypeInfo(String typeInfo) {
        this.typeInfo = typeInfo == null ? null : typeInfo.trim();
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }



    public DataTree(String name, Integer typeId, String typeInfo, Integer stateId, Integer parentId, Integer roleId) {
        this.name = name;
        this.typeId = typeId;
        this.typeInfo = typeInfo;
        this.stateId = stateId;
        this.parentId = parentId;
        this.roleId = roleId;
    }

    public DataTree(Integer id, String name, Integer typeId, String typeInfo, Integer stateId, Integer parentId, Integer roleId) {
        this.id = id;
        this.name = name;
        this.typeId = typeId;
        this.typeInfo = typeInfo;
        this.stateId = stateId;
        this.parentId = parentId;
        this.roleId = roleId;
    }

    public DataTree(String name, String typeInfo, Integer parentId) {
        this.name = name;
        this.typeInfo = typeInfo;
        this.parentId = parentId;
    }

    public DataTree(Integer typeId, String typeInfo, Integer parentId) {
        this.typeId = typeId;
        this.typeInfo = typeInfo;
        this.parentId = parentId;
    }

    public DataTree(Integer id, Integer stateId) {
        this.id = id;
        this.stateId = stateId;
    }

    public DataTree(String name, Integer typeId, String typeInfo, Integer stateId, Integer parentId) {
        this.name = name;
        this.typeId = typeId;
        this.typeInfo = typeInfo;
        this.stateId = stateId;
        this.parentId = parentId;
    }

    public DataTree(String name, Integer parentId) {
        this.name = name;
        this.parentId = parentId;
    }

    public DataTree() {
    }

    @Override
    public String toString() {
        return "DataTree{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", typeId=" + typeId +
                ", typeInfo='" + typeInfo + '\'' +
                ", stateId=" + stateId +
                ", parentId=" + parentId +
                ", roleId=" + roleId +
                '}';
    }
}