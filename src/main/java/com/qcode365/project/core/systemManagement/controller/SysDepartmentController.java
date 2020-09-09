package com.qcode365.project.core.systemManagement.controller;

import java.util.ArrayList;
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
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qcode365.project.core.base.BaseController;
import com.qcode365.project.core.systemManagement.Entity.SysDepartment;
import com.qcode365.project.core.systemManagement.service.SysDepartmentService;
import com.qcode365.project.core.util.ComponentHelper;

//===================版权说明=======================/
//本快速开发框架系统 版权归 快码猿所有                                                                    /
//本框架源代码由快码猿提供                               /
//注册商标：快码猿 QCode                              / 
//备注时间：2019-10-01                              / 
//===============================================/

@Controller
public class SysDepartmentController extends BaseController {
	
	private Logger log =  LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SysDepartmentService sysdepartmentService;

	// 进入添加页面
	@RequestMapping(value = "/sysDepartment_new")
	@RequiresPermissions(value="sysDepartment_new")
	public ModelAndView newSysDepartment(HttpServletRequest request) {
		ModelAndView mv = getModelAndView();
		try {
			mv.setViewName("systemManagement/sysdepartment/sysdepartment_new");
		} catch (Exception e) {
			log.error(e.toString(), e);
			e.printStackTrace();
			buildErrorPage(mv,e.toString());
		}
		return mv;
	}

	// 通过MAP保存新记录
	@RequestMapping(value = "/saveSysDepartment")
	@ResponseBody
	@RequiresPermissions(value="sysDepartment_new")
	public Map<String, Object> saveSysDepartment(HttpServletRequest request, SysDepartment depart) {
		Map<String, Object> respMap = new HashMap<String, Object>();
		try {
			sysdepartmentService.insert(depart);
			buildSuccessResultMap(respMap);
		} catch (Exception e) {
			log.error(e.toString(), e);
			e.printStackTrace();
			buildFailedResultMap(respMap);
		}
		return respMap;
	}

	// 进入修改页面
	@RequestMapping(value = "/sysDepartment_edit")
	@RequiresPermissions(value="sysDepartment_edit")
	public ModelAndView sysDepartment_edit(HttpServletRequest request) {
		ModelAndView mv = getModelAndView();
		try {
			String id = request.getParameter("id");
			String parentname = "无上级";
			SysDepartment sysdepartment = sysdepartmentService.selectById(id);
			SysDepartment parent = sysdepartmentService.selectById(sysdepartment.getParent_id());
			if(null != parent) {
				parentname = parent.getDepart_name();
			}
			
			mv.addObject("sysdepartment", sysdepartment);
			mv.addObject("parentname", parentname);
			mv.setViewName("systemManagement/sysdepartment/sysdepartment_edit");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString(), e);
			buildErrorPage(mv,e.toString());
		}
		return mv;
	}

	// 保存修改数据
	@RequestMapping(value = "/updateSysDepartment")
	@ResponseBody
	@RequiresPermissions(value="sysDepartment_edit")
	public Map<String, Object> updateSysDepartment(HttpServletRequest request,SysDepartment depart) {
		Map<String, Object> respMap = new HashMap<String, Object>();
		try {
			sysdepartmentService.updateById(depart);
			buildSuccessResultMap(respMap);
		} catch (Exception e) {
			log.error(e.toString(), e);
			e.printStackTrace();
			buildFailedResultMap(respMap);
		}
		return respMap;
	}

	// 删除数据
	@RequestMapping(value = "/deleteSysDepartment")
	@ResponseBody
	@RequiresPermissions(value="deleteSysDepartment")
	public Map<String, Object> deleteSysDepartment(HttpServletRequest request,@RequestBody Map<String,String> map) {
		Map<String, Object> respMap = new HashMap<String, Object>();
		try {
			String ids = map.get("ids");
			sysdepartmentService.deleteBatchIds(ids);
			buildSuccessResultMap(respMap);
		} catch (Exception e) {
			log.error(e.toString(), e);
			e.printStackTrace();
			buildFailedResultMap(respMap);
		}
		return respMap;
	}

	// 进入排序页面
	@RequestMapping(value = "/sortSysDepartment")
	public ModelAndView sortSysDepartment(HttpServletRequest request) {
		ModelAndView mv = getModelAndView();
		try {
			//List<Map<String, Object>> list = commService.getList("select * from sys_department where parent_id=0 order by sort");
			List<SysDepartment> list = sysdepartmentService.selectList(new EntityWrapper<SysDepartment>().eq("parent_id", 0).orderBy("sort"));
			mv.addObject("sysdepartmentlist", list);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString(), e);
		}
		mv.setViewName("systemManagement/sysdepartment/sysdepartment_sort");
		return mv;
	}

	// 保存排序结果
	@RequestMapping(value = "/sortedSysDepartment")
	@ResponseBody
	public Map<String, Object> sortedSysDepartment(HttpServletRequest request) {
		Map<String, Object> map = ComponentHelper.requestToMap(request);
		try {
			sysdepartmentService.SysDepartmentSort(map);
			map.put("statusCode", 200);
		} catch (Exception e) {
			log.error(e.toString(), e);
			e.printStackTrace();
			map.put("statusCode", 400);
		}
		return map;
	}

	// 进入查看页面
	@RequestMapping(value = "/sysDepartment_view")
	public ModelAndView sysDepartment_view(HttpServletRequest request) {
		ModelAndView mv = getModelAndView();
		try {
			String id = request.getParameter("id");
			SysDepartment sysdepartment = sysdepartmentService.selectById(id);
			mv.addObject("sysdepartment", sysdepartment);
			mv.setViewName("systemManagement/sysdepartment/sysdepartment_view");
		} catch (Exception e) {
			log.error(e.toString(), e);
			e.printStackTrace();
			buildErrorPage(mv, e.getMessage());
		}
		return mv;
	}
	// 进入列表页面
	@RequestMapping(value = "/sysDepartment_list")
	@RequiresPermissions(value="sysDepartment_list")
	public ModelAndView sysDepartment_list(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		try {
			mv.setViewName("systemManagement/sysdepartment/sysdepartment_list");
		} catch (Exception e) {
			log.error(e.toString(), e);
			e.printStackTrace();
			buildErrorPage(mv, e.getMessage());
		}
		return mv;
	}

	// 获取列表数据
	@RequestMapping(value = "/getSysDepartmentList")
	@ResponseBody
	@RequiresPermissions(value="sysDepartment_list")
	public Map<String, Object> getSysDepartmentList(HttpServletRequest request,@RequestParam Map<String, Object> reqMap) throws Exception {
		Map<String, Object> respMap = new HashMap<>();
		try {
			respMap = sysdepartmentService.getListByPage(reqMap,respMap);
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
	// 获取部门树数据
	@RequestMapping(value = "/getSysDepartmentTreeData")
	@ResponseBody
	public List<Map<String, Object>> getProducttypeTreeData(HttpServletRequest request) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
		/*	map.put("id", 0);
			map.put("pId", -1);
			map.put("name", "组织架构");
			map.put("open", 1);
			list.add(map);*/

			List<Map<String, Object>> list1 = sysdepartmentService.getSysDepartmentTreeData();
			for(int k=0;k<list1.size();k++) {
				list1.get(k).put("open", 1);
			}
			list.addAll(list1);

		} catch (Exception e) {
			log.error(e.toString(), e);
		}
		return list;
	}

	// 进入选择上级页面
	@RequestMapping(value = "/treeSysDepartment")
	public ModelAndView treeSysDepartment(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		mv.setViewName("systemManagement/sysdepartment/treesysdepartment");
		return mv;
	}

	// 进入选择机构树页面
	@RequestMapping(value = "/sltSysDepartment")
	public ModelAndView sltSysDepartment(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		mv.setViewName("systemManagement/sysuser/treesysdepartment");
		return mv;
	}

	
}
