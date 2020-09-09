package com.qcode365.project.core.systemManagement.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.qcode365.project.core.base.BaseController;
import com.qcode365.project.core.base.CommService;
import com.qcode365.project.core.systemManagement.Entity.SysLogs;
import com.qcode365.project.core.systemManagement.service.SysLogsService;
import com.qcode365.project.core.util.ComponentHelper;

//===================版权说明=======================/
//本快速开发框架系统 版权归 快码猿所有                                                                    /
//本框架源代码由快码猿提供                               /
//注册商标：快码猿 QCode                              / 
//备注时间：2019-10-01                              / 
//===============================================/

@Controller
public class SysLogsController extends BaseController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SysLogsService syslogsService;
	@Autowired
	private CommService commService;

	String thisTitle = "";// 模块名称

	// 进入添加页面
	@RequestMapping(value = "/newSysLogs")
	@RequiresPermissions("newSysLogs")
	public ModelAndView newSysLogs(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		try {
			mv.setViewName("syslogs/syslogs_new");
		} catch (Exception e) {
			log.error(e.toString(), e);
			e.printStackTrace();
			buildErrorPage(mv, e.toString());
		}
		return mv;
	}

	// 通过对象保存新记录，并返回最新ID
	@RequestMapping(value = "/saveSysLogs")
	@ResponseBody
	@RequiresPermissions("newSysLogs")
	public Map<String, Object> saveSysLogs(HttpServletRequest request, SysLogs syslogs,
			@RequestParam Map<String, Object> reqMap) {
		Map<String, Object> respMap = new HashMap<String, Object>();
		try {
			// 这里可以修改实体类的参数值

			syslogsService.insert(syslogs);

			buildSuccessResultMap(respMap);
			
		} catch (Exception e) {
			log.error(e.toString(), e);
			e.printStackTrace();
			buildFailedResultMap(respMap);
			WriteErrorLog(e.toString());
		}
		return respMap;
	}

	// 进入修改页面
	@RequestMapping(value = "/editSysLogs")
	@RequiresPermissions("editSysLogs")
	public ModelAndView editSysLogs(HttpServletRequest request) {
		Map<String, Object> map = ComponentHelper.requestToMap(request);
		ModelAndView mv = new ModelAndView();
		try {
			Integer log_id = map.get("log_id") == null ? 0 : Integer.parseInt(map.get("log_id").toString());
			SysLogs syslogs = syslogsService.selectById(log_id);
			mv.addObject("syslogs", syslogs);
			mv.setViewName("syslogs/syslogs_edit");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString(), e);
			buildErrorPage(mv, e.toString());
		}
		return mv;
	}

	// 保存修改数据
	@RequestMapping(value = "/updateSysLogs")
	@ResponseBody
	@RequiresPermissions("editSysLogs")
	public Map<String, Object> updateSysLogs(HttpServletRequest request, SysLogs syslogs,
			@RequestParam Map<String, Object> reqMap) {
		Map<String, Object> respMap = new HashMap<String, Object>();
		try {
			syslogsService.updateById(syslogs);
			buildSuccessResultMap(respMap);
			
		} catch (Exception e) {
			log.error(e.toString(), e);
			e.printStackTrace();
			buildFailedResultMap(respMap);
		}
		return respMap;
	}

	// 进入查看页面
	@RequestMapping(value = "/viewSysLogs")
	public ModelAndView viewSysLogs(HttpServletRequest request) {
		Map<String, Object> map = ComponentHelper.requestToMap(request);
		ModelAndView mv = new ModelAndView();
		try {
			Integer log_id = map.get("log_id") == null ? 0 : Integer.parseInt(map.get("log_id").toString());
			SysLogs syslogs = syslogsService.selectById(log_id);
			mv.addObject("syslogs", syslogs);

			mv.setViewName("syslogs/syslogs_view");
		} catch (Exception e) {
			log.error(e.toString(), e);
			e.printStackTrace();
			buildErrorPage(mv, e.toString());
		}
		return mv;
	}

	// 删除数据
	@RequestMapping(value = "/deleteSysLogs")
	@ResponseBody
	@RequiresPermissions("deleteSysLogs")
	public Map<String, Object> deleteSysLogs(@RequestBody Map<String, Object> reqMap, HttpServletRequest request) {
		Map<String, Object> respMap = new HashMap<String, Object>();
		try {
			String ids = reqMap.get("ids") == null ? "0" : reqMap.get("ids").toString();
			List<Map<String, Object>> list = commService.getList("select * from sys_logs where log_id in(" + ids + ")");
			syslogsService.deleteBatchIds(ids);
			
			buildSuccessResultMap(respMap);
		} catch (Exception e) {
			log.error(e.toString(), e);
			e.printStackTrace();
			buildFailedResultMap(respMap);
		}
		return respMap;
	}

	// 进入列表页面
	@RequestMapping(value = "/listSysLogs")
	@RequiresPermissions("listSysLogs")
	public ModelAndView listSysLogs(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		try {
			// todo
			mv.setViewName("systemManagement/syslogs/syslogs_list");

		} catch (Exception e) {
			log.error(e.toString(), e);
			e.printStackTrace();
			buildErrorPage(mv, e.toString());
		}
		return mv;
	}

	// 获取列表数据
	@RequestMapping(value = "/getlistSysLogs")
	@ResponseBody
	public Map<String, Object> getlistSysLogs(@RequestParam Map<String, Object> reqMap, HttpServletRequest request)
			throws Exception {
		Map<String, Object> respMap = new HashMap<String, Object>();
		try {
			respMap = syslogsService.searchSysLogs(reqMap);
			buildSuccessResultMap(respMap);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString(), e);
			buildFailedResultMap(respMap);
		}
		return respMap;

	}

	// todo
	// 新添加功能===============================================================================================================

}
