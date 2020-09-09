package com.qcode365.project.core.systemManagement.Entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;

/**
 * 用户,角色关联表
 * @author wwh
 *
 */
public class SysRoleUser extends Model<SysRoleUser>{
	
	private static final long serialVersionUID = 1L;
	
	private Integer ruid;
	private Integer user_id;
	private Integer role_id;
	
	public Integer getRuid() {
		return ruid;
	}
	public void setRuid(Integer ruid) {
		this.ruid = ruid;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getRole_id() {
		return role_id;
	}
	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}
	@Override
	protected Serializable pkVal() {
		return ruid;
	}
	
	
	

}
