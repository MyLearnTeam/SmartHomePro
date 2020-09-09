package com.qcode365.project.core.systemManagement.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

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
import com.qcode365.project.core.systemManagement.Entity.SysRole;
import com.qcode365.project.core.systemManagement.service.SysCatalogService;
import com.qcode365.project.core.systemManagement.service.SysRoleService;

//===================版权说明=======================/
//本快速开发框架系统 版权归 快码猿所有                                                                    /
//本框架源代码由快码猿提供                               /
//注册商标：快码猿 QCode                              / 
//备注时间：2019-10-01                              / 
//===============================================/

@Controller
public class SysRoleController extends BaseController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SysRoleService roleService;
	@Autowired
	private SysCatalogService sysCatalogService;
	@Autowired
	private CommService commService;

	// 进入添加页面
	@RequestMapping(value = "sysRole_new")
	public ModelAndView sysRole_new(HttpServletRequest request) {
		try {
			mv.setViewName("systemManagement/sysrole/sysrole_new");
		} catch (Exception e) {
			log.error(e.toString(), e);
			e.printStackTrace();
			buildErrorPage(mv, e.getMessage());
		}
		return mv;
	}

	// 通过MAP保存新记录
	@RequestMapping(value = "/saveSysRole")
	@ResponseBody
	public Map<String, Object> saveSysRole(HttpServletRequest request, SysRole role) {
		Map<String, Object> map = new HashMap<>();
		try {
			roleService.insert(role);
			buildSuccessResultMap(map);
		} catch (Exception e) {
			log.error(e.toString(), e);
			e.printStackTrace();
			buildFailedResultMap(map);
		}
		return map;
	}

	// 进入修改页面
	@RequestMapping(value = "/sysRole_edit")
	public ModelAndView sysRole_edit(HttpServletRequest request) {
		try {
			String id = request.getParameter("id");
			SysRole sysrole = roleService.selectById(id);
			mv.addObject("sysrole", sysrole);
			mv.setViewName("systemManagement/sysrole/sysrole_edit");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString(), e);
			buildErrorPage(mv, e.getMessage());
		}
		return mv;
	}

	// 保存修改数据
	@RequestMapping(value = "/updateSysRole")
	@ResponseBody
	public Map<String, Object> updateSysRole(HttpServletRequest request, SysRole role) {
		Map<String, Object> map = new HashMap<>();
		try {
			roleService.updateById(role);
			buildSuccessResultMap(map);
		} catch (Exception e) {
			log.error(e.toString(), e);
			e.printStackTrace();
			buildFailedResultMap(map);
		}
		return map;
	}

	// 删除数据
	@RequestMapping(value = "/deleteSysRole")
	@ResponseBody
	public Map<String, Object> deleteSysRole(HttpServletRequest request, @RequestBody Map<String, String> reqmap) {
		Map<String, Object> map = new HashMap<>();
		try {
			String ids = reqmap.get("ids");
			roleService.deleteBatchIds(ids);
			buildSuccessResultMap(map);
		} catch (Exception e) {
			log.error(e.toString(), e);
			e.printStackTrace();
			buildFailedResultMap(map);
		}
		return map;
	}

	// 进入查看页面
	@RequestMapping(value = "/sysRole_view")
	public ModelAndView sysRole_view(HttpServletRequest request) {
		try {
			String id = request.getParameter("id");
			SysRole sysrole = roleService.selectById(id);
			mv.addObject("sysrole", sysrole);
			mv.setViewName("systemManagement/sysrole/sysrole_view");
		} catch (Exception e) {
			log.error(e.toString(), e);
			e.printStackTrace();
			buildErrorPage(mv, e.getMessage());
		}
		return mv;
	}

	// 进入列表查询页面
	@RequestMapping(value = "/sysRole_list")
	public ModelAndView searchSysRole(HttpServletRequest request) {
		try {
			// todo
			mv.setViewName("systemManagement/sysrole/sysrole_list");
		} catch (Exception e) {
			log.error(e.toString(), e);
			e.printStackTrace();
			buildErrorPage(mv, e.getMessage());
		}
		return mv;
	}

	// 获取列表查询数据
	@RequestMapping(value = "/getSysRoleList")
	@ResponseBody
	public Map<String, Object> getSysRoleList(HttpServletRequest request, @RequestParam Map<String, Object> reqMap)
			throws Exception {
		Map<String, Object> respMap = new HashMap<>();
		try {
			respMap = roleService.getListByPage(reqMap, respMap);
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

	// 进入角色权限设置页面
	@RequestMapping(value = "/setRolePopedom")
	public ModelAndView setRolePopedom(HttpServletRequest request) {
		try {
			String roleid = request.getParameter("roleid");
			mv.addObject("roleid", roleid);
			mv.setViewName("systemManagement/sysrole/setrolepopedom");
		} catch (Exception e) {
			log.error(e.toString(), e);
			e.printStackTrace();
			buildErrorPage(mv, e.getMessage());
		}
		return mv;
	}

	// 获取角色权限树
	@RequestMapping(value = "/getRoleCatalogTreeData")
	@ResponseBody
	public List<Map<String, Object>> getRoleCatalogTreeData(HttpServletRequest request) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			String roleId = request.getParameter("roleid");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", 0);
			map.put("pId", -1);
			map.put("name", "功能模块结构");
			map.put("open", 1);
			list = sysCatalogService.getCatalogTreeData();
			List<Map<String, Object>> list1 = commService
					.getList("select catalog_id from sys_role_popedom where role_id=" + roleId);
			for (int k = 0; k < list.size(); k++) {
				for (int l = 0; l < list1.size(); l++) {
					if (list.get(k).get("id").toString().equals(list1.get(l).get("catalog_id").toString())) {
						list.get(k).put("checked", "true");
					}
				}
			}
			list.add(map);
		} catch (Exception e) {
			log.error(e.toString(), e);
		}
		return list;
	}

	// 保存权限设置
	@RequestMapping(value = "/setRolePopedomOK")
	@ResponseBody
	public Map<String, Object> setRolePopedomOK(HttpServletRequest request, SysRole sysrole) {
		Map<String, Object> map = new HashMap<>();
		try {
			String cid = request.getParameter("cid");
			String roleId = request.getParameter("roleid");
			roleService.setRolePopedom(cid, roleId);
			buildSuccessResultMap(map);
		} catch (Exception e) {
			log.error(e.toString(), e);
			e.printStackTrace();
			buildFailedResultMap(map);
		}
		return map;
	}

	// 进入设置角色-用户页面
	@RequestMapping(value = "/setRoleUser")
	public ModelAndView setRoleUser(HttpServletRequest request) {
		try {
			String roleId = request.getParameter("roleid");
			List<Map<String, Object>> userlist = commService.getList(
					"select * from sys_userinfo  where user_id not in(select user_id from sys_role_user where role_id="
							+ roleId + ")");
			mv.addObject("userlist", userlist);

			List<Map<String, Object>> userlist2 = commService
					.getList("select * from sys_userinfo where user_id in(select user_id from sys_role_user where role_id="
							+ roleId + ")");
			mv.addObject("userlist2", userlist2);

			mv.addObject("roleid", roleId);

		} catch (Exception e) {
			log.error(e.toString(), e);
			e.printStackTrace();
		}
		mv.setViewName("systemManagement/sysrole/setroleuser");
		return mv;
	}

	// 保存设置用户角色
	@RequestMapping(value = "/setRoleUserOK")
	@ResponseBody
	public Map<String, Object> setRoleUserOK(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			String roleId = request.getParameter("roleid");
			String ids = request.getParameter("ids");
			roleService.setRoleUser(roleId, ids);

			buildSuccessResultMap(map);
		} catch (Exception e) {
			log.error(e.toString(), e);
			e.printStackTrace();
			buildFailedResultMap(map);
		}
		return map;
	}

}
