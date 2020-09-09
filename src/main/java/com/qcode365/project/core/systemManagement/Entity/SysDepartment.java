package com.qcode365.project.core.systemManagement.Entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import com.qcode365.project.core.base.BaseEntity;


@TableName("sys_department")
public class SysDepartment extends BaseEntity<SysDepartment> {
	
	private static final long serialVersionUID = 8238423108390903520L;
	
	@TableId(type=IdType.AUTO)
	private Integer depart_id = 0; // 部门ID
	
	@TableField
	private Integer parent_id = 0; // 父级ID
 
	@TableField
	private String depart_code = ""; // 部门编码

	@TableField
	private String depart_name = ""; // 门部名称

	@TableField
	private String remark = ""; // 备注

	@TableField
	private Integer sort = 0; // 排序号
	
	@Override
	protected Serializable pkVal() {
		return depart_id;
	}

	public Integer getDepart_id() {
		return depart_id;
	}

	public void setDepart_id(Integer depart_id) {
		this.depart_id = depart_id;
	}

	public Integer getParent_id() {
		return parent_id;
	}

	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}

	 
	public String getDepart_code() {
		return depart_code;
	}

	public void setDepart_code(String depart_code) {
		this.depart_code = depart_code;
	}

	public String getDepart_name() {
		return depart_name;
	}

	public void setDepart_name(String depart_name) {
		this.depart_name = depart_name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

    
}
