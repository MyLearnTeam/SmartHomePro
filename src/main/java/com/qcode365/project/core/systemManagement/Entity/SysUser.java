package com.qcode365.project.core.systemManagement.Entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import com.qcode365.project.core.base.BaseEntity;

@TableName(value="sys_userinfo")
public class SysUser extends BaseEntity<SysUser> implements Serializable{
    
	private static final long serialVersionUID = -7975458290995740511L;

	@TableId(type=IdType.AUTO,value="user_id")
    private Integer user_id; //用户ID
	@TableField
    private String login_name; //登录名
	@TableField
    private String password; //密码
	@TableField
    private String truename; //真实姓名
	@TableField
    private String usercode; //用户代码
	@TableField
    private Integer status; //账号状态
	@TableField
    private Integer usertype; //用户类型
	@TableField
    private Integer depart_id; // 所属部门
	@TableField(exist=false)
    private String depart_cn; //中文
	@TableField
    private String photo; //头像地址
	@TableField
    private String mobile; //手机
	@TableField
    private String phone; //座机固话
	@TableField
    private String job_title; //职位
	@TableField
    private String degree; //职称
	@TableField
    private String email; //电子邮箱
	@TableField
    private String remark; //备注

	@TableField(exist=false)
	private String ip="";//记录访问终端的IP地址

	
	protected Serializable pkVal() {
        return user_id;
    }
	
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTruename() {
		return truename;
	}
	public void setTruename(String truename) {
		this.truename = truename;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getUsertype() {
		return usertype;
	}
	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}
	public Integer getDepart_id() {
		return depart_id;
	}
	public void setDepart_id(Integer depart_id) {
		this.depart_id = depart_id;
	}
	public String getDepart_cn() {
		return depart_cn;
	}
	public void setDepart_cn(String depart_cn) {
		this.depart_cn = depart_cn;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getJob_title() {
		return job_title;
	}
	public void setJob_title(String job_title) {
		this.job_title = job_title;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}

