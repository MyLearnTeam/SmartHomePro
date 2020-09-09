package com.qcode365.project.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//===================版权说明=======================/
//本快速开发框架系统 版权归 快码猿所有                                                                    /
//本框架源代码由快码猿提供，任何单位或个人均可商用和传播                               /
//注册商标：快码猿 QCode                              / 
//备注时间：2019-10-01                              / 
//===============================================/

@Configuration
@PropertySource("classpath:base.properties")
public class BaseConfig  implements WebMvcConfigurer {
	
	//文件虚拟路径resources/project
	public static String FileServerPath;
	
	//文件虚拟路径映射的硬盘路径D://resources/project
	public static String FileDirPath;
	
	//pc后台登录地址
	public static String pcLoginUrl;
	
	/**
	 * 配置以FileServerPath+"/**"的路径都转发到FileDirPath
	 */
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(FileServerPath+"/**").addResourceLocations("file:"+FileDirPath);
	}

	public static String getFileServerPath() {
		return FileServerPath;
	}
	
	@Value("${File.ServerPath}")
	private void setFileServerPath(String fileServerPath) {
		FileServerPath = fileServerPath;
	}

	public static String getFileDirPath() {
		return FileDirPath;
	}

	@Value("${File.DirPath}")
	private void setFileDirPath(String fileDirPath) {
		FileDirPath = fileDirPath;
	}
	
	public static String getPcLoginUrl() {
		return pcLoginUrl;
	}
	
	@Value("${shiro.pcLoginUrl}")
	private void setPcLoginUrl(String pcLoginUrl) {
		BaseConfig.pcLoginUrl = pcLoginUrl;
	}

}
