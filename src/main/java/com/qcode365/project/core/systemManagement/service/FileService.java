package com.qcode365.project.core.systemManagement.service;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qcode365.project.core.base.BaseService;
import com.qcode365.project.core.config.BaseConfig;
import com.qcode365.project.core.systemManagement.Entity.FileEntity;
import com.qcode365.project.core.systemManagement.mappers.FileMapper;

@Service
@Transactional
public class FileService extends BaseService<FileMapper,FileEntity>{

	// 批量删除实体
	public void deleteBatchIds(String ids) {
		List<FileEntity> fileList = super.selectByIds(ids);
		if (fileList != null && fileList.size() > 0) {
			for (FileEntity file : fileList) {
				String full_path = BaseConfig.getFileDirPath() +file.getFilePath();
				File pathFile = new File(full_path);
				if (pathFile.exists()) {
					pathFile.delete();
				}
			}
			super.deleteBatchIds(ids);
		}
	}
	//加虚拟路径
	public FileEntity selectWithPathById(Integer id) {
		FileEntity file = super.selectById(id);
		if(null != file) {			
			file.setFilePath(BaseConfig.getFileServerPath()+file.getFilePath());
		}
		return file;
	}
	//加虚拟路径
	public List<FileEntity> selectWithPathByIds(String ids) {
		List<FileEntity> fileList = super.selectByIds(ids);
		for(FileEntity file:fileList) {
			if(null != file) {	
				file.setFilePath(BaseConfig.getFileServerPath()+file.getFilePath());
			}
		}
		return fileList;
	}
}
