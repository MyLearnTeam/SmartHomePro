package com.qcode365.project.core.systemManagement.Entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import com.qcode365.project.core.base.BaseEntity;

@TableName("sys_files")
public class FileEntity extends BaseEntity<FileEntity> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@TableId(type = IdType.AUTO)
	private Integer file_id; //
	@TableField
	private String filename; // 文件名
	@TableField
	private String savename; //
	@TableField
	private String extname; // 扩展名
	@TableField
	private Integer file_type_id = 0; // 文件类型ID
	@TableField
	private String filePath; // 保存路径
	@TableField
	private double fileSize; // 文件大小
	@TableField
	private Integer status = 1; // 状态
	@TableField
	private String remark; // 备注
	
	@Override
	protected Serializable pkVal() {
		return file_id;
	}

	public FileEntity() { }

	public Integer getFile_id() {
		return file_id;
	}

	public void setFile_id(Integer file_id) {
		this.file_id = file_id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getSavename() {
		return savename;
	}

	public void setSavename(String savename) {
		this.savename = savename;
	}

	public String getExtname() {
		return extname;
	}

	public void setExtname(String extname) {
		this.extname = extname;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public double getFileSize() {
		return fileSize;
	}

	public void setFileSize(double fileSize) {
		this.fileSize = fileSize;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getFile_type_id() {
		return file_type_id;
	}

	public void setFile_type_id(Integer file_type_id) {
		this.file_type_id = file_type_id;
	}
}
