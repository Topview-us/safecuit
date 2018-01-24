package com.gdut.safecuit.device.common.vo;

import java.util.List;

/**
 * Created by Garson in 9:43 2018/1/21
 * Description :
 */
public class DataTreeVO {

	//private DataTree dataTree;//自身数据树属性
	private Integer id;
	private String label;//结点姓名
	private Integer typeId;//结点类型
	private String typeInfo;//机构id或电箱id
	private Integer stateId;
	private Integer parentId;
	private Integer roleId;
	private Integer isHint;//报警标志
	private List<DataTreeVO> children;//孩子对象

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTypeInfo() {
		return typeInfo;
	}

	public void setTypeInfo(String typeInfo) {
		this.typeInfo = typeInfo;
	}

	public Integer getIsHint() {
		return isHint;
	}

	public void setIsHint(Integer isHint) {
		this.isHint = isHint;
	}

	public List<DataTreeVO> getChildren() {
		return children;
	}

	public void setChildren(List<DataTreeVO> children) {
		this.children = children;
	}

	public DataTreeVO(Integer id ,String label, Integer typeId, String typeInfo, Integer parentId,
					  Integer roleId, Integer isHint ,Integer stateId) {
		this.id = id;
		this.label = label;
		this.typeId = typeId;
		this.typeInfo = typeInfo;
		this.parentId = parentId;
		this.roleId = roleId;
		this.isHint = isHint;
		this.stateId = stateId;
	}

	@Override
	public String toString() {
		return "DataTreeVO{" +
				"label='" + label + '\'' +
				", typeId=" + typeId +
				", typeInfo='" + typeInfo + '\'' +
				", parentId=" + parentId +
				", roleId=" + roleId +
				", isHint=" + isHint +
				", children=" + children +
				'}';
	}
}
