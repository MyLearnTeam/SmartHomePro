package com.qcode365.project.core.systemManagement.Entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import com.qcode365.project.core.base.BaseEntity;

@TableName(value="sys_role")
public class SysRole extends BaseEntity<SysRole>{

	private static final long serialVersionUID = 2359395036604233124L;
	
	@TableId(type=IdType.AUTO)
	private Integer role_id; // 角色ID
	@TableField
	private String role_name = ""; // 角色名称
	@TableField
	private String role_code = ""; // 角色编码
	@TableField
	private String remark = ""; // 备注
	
	@Override
	protected Serializable pkVal() {
		return role_id;
	}
	
	public Integer getRole_id() {
		return role_id;
	}
	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public String getRole_code() {
		return role_code;
	}
	public void setRole_code(String role_code) {
		this.role_code = role_code;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
