package com.qcode365.project.core.systemManagement.controller;

import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.qcode365.project.core.base.BaseController;
import com.qcode365.project.core.config.BaseConfig;
import com.qcode365.project.core.systemManagement.Entity.FileEntity;
import com.qcode365.project.core.systemManagement.service.FileService;
import com.qcode365.project.core.util.FileUtil;

//===================版权说明=======================/
//本快速开发框架系统 版权归 快码猿所有                                                                    /
//本框架源代码由快码猿提供                               /
//注册商标：快码猿 QCode                              / 
//备注时间：2019-10-01                              / 
//===============================================/

@Controller
public class FileController extends BaseController{
		
	private Logger log =  LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FileService fileService;
	
	
	/**
     * 上传文件
     */
    @RequestMapping(value = "/multiUploadFiles")
    @ResponseBody
    public Map<String,Object> multiUploadFiles(HttpServletRequest request, HttpServletResponse response,
    		@RequestParam("files[]") MultipartFile[] files) throws Exception {

    	Map<String,Object> respMap = new HashMap<>();
        try {
        	// 设置字符编码为UTF-8, 这样支持汉字显示
        	response.setContentType("text/html");
        	response.setCharacterEncoding("UTF-8");
        	if (files.length < 1) {
        		return buildResultMap(respMap, "400", "请先选择要上传的文件 ");
        	}
        	
        	StringBuilder fileIds = new StringBuilder();
        	List<FileEntity> fileList = new ArrayList<>();
            for (MultipartFile file : files) {
                if (file != null && file.getSize() > 0) {
                    FileEntity saveFile = new FileEntity();
                    //String fileContentType = file.getContentType();
                    double fileLength = (double)file.getSize()/1000;//单位kb
                    String fileName = file.getOriginalFilename();
                    String extName = FileUtil.getExtName(fileName);
                    //保存
                    String filePath = FileUtil.getPath(file, fileName, "sysFiles/", request);
                    
                    //插入记录
                    saveFile.setFilename(fileName);
                    saveFile.setSavename(filePath.substring(filePath.lastIndexOf("/")));
                    saveFile.setExtname(extName);
                    saveFile.setFilePath(filePath);
                    saveFile.setFileSize(fileLength);
                    fileService.insert(saveFile);
                    fileList.add(saveFile);
                    saveFile.setFilePath(BaseConfig.getFileServerPath()+saveFile.getFilePath());
                    fileIds.append(",").append(saveFile.getFile_id());
                }
            }
            respMap.put("files", fileList);
            if(fileIds.toString().startsWith(",")) {
            	respMap.put("file_ids", fileIds.substring(1));
            	buildSuccessResultMap(respMap);
            }else {            	
            	buildResultMap(respMap, "400", "上传文件失败，请重试 ");
            }
        } catch (Exception e) {
            log.error("上传异常：" + e);
            e.printStackTrace();
            buildResultMap(respMap, "400", "上传异常 ");
        }
        return respMap;
    }
    /**
	 * 删除文件
	 */
	@RequestMapping(value = "/delFile")
	@ResponseBody
	public Map<String,Object> delFile(@RequestBody Map<String,Object> map, HttpServletRequest request) throws Exception {
		Map<String,Object> respMap = new HashMap<>();
		try {

			if (null == map.get("ids") || "".equals(map.get("ids").toString())) {
				return buildResultMap(respMap, "400", "请求参数错误");
			}
			String ids = map.get("ids").toString();
			
			fileService.deleteBatchIds(ids);
			buildSuccessResultMap(respMap);
		} catch (Exception e) {
			log.error("上传异常：" + e);
			e.printStackTrace();
			buildFailedResultMap(respMap);
		}

		return respMap;
	}

	@RequestMapping(value = "/download")
	public ResponseEntity<byte[]> download(HttpServletRequest request) {
		String url = request.getParameter("url");
		String filename = request.getParameter("filename");
		File file =new File( url);// new File(GlobalContext.FileSavePath + url);
		HttpHeaders headers = new HttpHeaders();
		try {
			String fileName = new String(filename.getBytes("UTF-8"), "iso-8859-1");
			headers.setContentDispositionFormData("attachment	", fileName);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// 富文本文件保存
	@RequestMapping(value = "/kindeditorUpload")
	@ResponseBody
	public Map<String, Object> kindeditorUpload(HttpServletRequest request) throws IOException {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multipartRequest.getFile("imgFile");// 获取文件流
			String path = FileUtil.getPath(file, file.getOriginalFilename(), "upload/head/", request);
			String base = request.getScheme() + "://" + request.getHeader("Host") + BaseConfig.getFileServerPath();
			log.debug("base：" + base);
			base = base + path;
			map.put("error", 0);// 200代表图片上传成功
			log.debug("basepath：" + base);
			map.put("url", base);
			// log.debug(base);
		} catch (Exception e) {
			// TODO: handle exception
			map.put("error", 1);// 200代表图片上传成功
			map.put("message", "文件上传失败");
		}

		return map;
	}
	@RequestMapping(value = "/downloadFile")
	public ResponseEntity<byte[]> downloadFile(HttpServletRequest request) {
		String url = request.getParameter("url");
		String filename = request.getParameter("name");
		url = url.replace(BaseConfig.getFileServerPath(), BaseConfig.getFileDirPath());
		File file = new File(url);
		HttpHeaders headers = new HttpHeaders();
		try {
			String fileName = new String(filename.getBytes("UTF-8"), "iso-8859-1");
			headers.setContentDispositionFormData("attachment", fileName);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
