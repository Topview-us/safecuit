package com.gdut.safecuit.device.common.vo;

import java.util.List;

/**
 * Created by Garson in 9:43 2018/1/21
 * Description :
 */
public class DataTreeVO {

	private Integer id;
	private String label;//结点姓名
	private Integer typeId;//结点类型，1为分组，2为机构，3为电箱
	private Integer parentId;
	private Integer orgId;
	private Integer isHint;//报警标志
	private List<DataTreeVO> children;//孩子对象

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getIsHint() {
		return isHint;
	}

	public void setIsHint(Integer isHint) {
		this.isHint = isHint;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public List<DataTreeVO> getChildren() {
		return children;
	}

	public void setChildren(List<DataTreeVO> children) {
		this.children = children;
	}

	public DataTreeVO(Integer id, String label, Integer typeId, Integer parentId,
					  Integer orgId, Integer isHint, List<DataTreeVO> children) {
		this.id = id;
		this.label = label;
		this.typeId = typeId;
		this.parentId = parentId;
		this.orgId = orgId;
		this.isHint = isHint;
		this.children = children;
	}

	public DataTreeVO(Integer id, String label, Integer typeId,
					   Integer parentId, Integer orgId) {
		this.id = id;
		this.label = label;
		this.typeId = typeId;
		this.parentId = parentId;
		this.orgId = orgId;
	}

	public DataTreeVO() {
	}

	@Override
	public String toString() {
		return "DataTreeVO{" +
				"label='" + label + '\'' +
				", typeId=" + typeId +
				", parentId=" + parentId +
				", isHint=" + isHint +
				", children=" + children +
				'}';
	}
}
