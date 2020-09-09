package com.qcode365.project.core.systemManagement.Entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import com.qcode365.project.core.base.BaseEntity;
import java.util.List;
/**
 * SysCatalog entity. @author MyEclipse Persistence Tools
 */

@TableName(value="sys_catalog")
public class SysCatalog implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	// Fields
	 private Integer catalog_id=0; //流水号
     private String catalog_cn=""; //中文
     private String catalogcode=""; //标识
     private String catalog=""; //名称
     private Integer parent_id=0; //上级ID
     private String img=""; //图标
     private String url=""; //访问地址
     private Integer is_menu; //是否菜单(1-菜单,2-权限)
     private Integer winWidth=650; //宽度
     private Integer winHeight=550; //高度
     private String remark=""; //备注
     private Integer sort=0; //排序
	 private List<SysCatalog> childList;//只作显示使用
     
	// Constructors
	public Integer getCatalog_id() {
		return catalog_id;
	}
	public void setCatalog_id(Integer catalogId) {
		catalog_id = catalogId;
	}
	public String getCatalog_cn() {
		return catalog_cn;
	}
	public void setCatalog_cn(String catalogCn) {
		catalog_cn = catalogCn;
	}
	public String getCatalogcode() {
		return catalogcode;
	}
	public void setCatalogcode(String catalogcode) {
		this.catalogcode = catalogcode;
	}
	public String getCatalog() {
		return catalog;
	}
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	public Integer getParent_id() {
		return parent_id;
	}
	public void setParent_id(Integer parentId) {
		parent_id = parentId;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public Integer getIs_menu() {
		return is_menu;
	}
	public void setIs_menu(Integer is_menu) {
		this.is_menu = is_menu;
	}
	public Integer getWinWidth() {
		return winWidth;
	}
	public void setWinWidth(Integer winWidth) {
		this.winWidth = winWidth;
	}
	public Integer getWinHeight() {
		return winHeight;
	}
	public void setWinHeight(Integer winHeight) {
		this.winHeight = winHeight;
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

	public List<SysCatalog> getChildList() {
		return childList;
	}

	public void setChildList(List<SysCatalog> childList) {
		this.childList = childList;
	}
	 
}
