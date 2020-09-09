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
import com.qcode365.project.core.base.BaseController;
import com.qcode365.project.core.base.CommService;
import com.qcode365.project.core.systemManagement.Entity.SysCatalog;
import com.qcode365.project.core.systemManagement.service.SysCatalogService;
import com.qcode365.project.core.util.ComponentHelper;

//===================版权说明=======================/
//本快速开发框架系统 版权归 快码猿所有                                                                    /
//本框架源代码由快码猿提供                               /
//注册商标：快码猿 QCode                              / 
//备注时间：2019-10-01                              / 
//===============================================/
@Controller
public class SysCatalogController extends BaseController {
	
	private Logger log =  LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SysCatalogService sysCatalogService;
	@Autowired
	private CommService commService;
	
	// 模块列表
	@RequiresPermissions("listCatalog")
	@RequestMapping(value = "/listCatalog")
	public ModelAndView userList(HttpServletRequest request) {
		try {
			mv.setViewName("systemManagement/syscatalog/syscatalog_list");
		} catch (Exception e) {
			log.error(e.toString(), e);
		}
		return mv;
	}


	//模块列表获取数据
	@RequiresPermissions("listCatalog")
	@RequestMapping(value = "/getCatalogList")
	@ResponseBody
	public Map<String, Object> getCatalogList(HttpServletRequest request)throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String start = request.getParameter("offset");
			String length = request.getParameter("limit");
			String pid = request.getParameter("pid");
			if(null == pid || "".equals(pid)){
				pid = "0";
			}
			if(null == start || "".equals(start)){
				start = "0";
			}
			map.put("start", start==null?0:Integer.parseInt(start));
			map.put("length", length==null?null:Integer.parseInt(length));
			map.put("pid", pid);
			// // 列出用户列表
			//List<SysCatalog> userList = sysCatalogService.getSysCatalogList(map);
			
			String sql="select * from sys_catalog";
			String sql1="select count(*) as row from sys_catalog";
			if (map.get("pid")!=null)
			{
				if ((!map.get("pid").toString().equals("")) && (!map.get("pid").toString().equals("-1")))
				{
					sql=sql+" where parent_id=" + map.get("pid").toString() + " or catalog_id=" +map.get("pid").toString();
					sql1=sql1+" where parent_id=" + map.get("pid").toString() + " or catalog_id=" +map.get("pid").toString();
				}
			}
			sql=sql+" order by sort limit " + start +","+length;
			
			List<Map<String, Object>> catalogList =commService.getList(sql);// sysCatalogService.getSysCatalogList(map);
			int totalnum =commService.getFieldIntValue(sql1);// sysCatalogService.getSysCatalogListNum(map);
			map.put("rows", catalogList);
			map.put("total", totalnum);
		} catch (Exception e) {
			log.error(e.toString(), e);
		}
		return map;
	}
	//模块列表获取树数据
	@RequestMapping(value = "/getCatalogTreeData")
	@ResponseBody
	public List<Map<String,Object>> getCatalogTreeData(HttpServletRequest request)throws Exception {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("id", 0);
			map.put("pId", -1);
			map.put("name", "功能模块结构");
			map.put("open", 1);
			list = sysCatalogService.getCatalogTreeData();
			 
			list.add(map);
		} catch (Exception e) {
			log.error(e.toString(), e);
		}
		return list;
	}
	// 进入添加栏目页面
	@RequestMapping(value = "/toAddSysCatalog")
	public ModelAndView toAddSysCatalog(HttpServletRequest request) {
		try {
			mv.setViewName("systemManagement/syscatalog/syscatalog_new");
		} catch (Exception e) {
			log.error(e.toString(), e);
		}
		return mv;
	}
	// 保存新记录
	@RequestMapping(value = "/saveSysCatalog")
	@ResponseBody
	public Map<String, Object> saveSysCatalog(HttpServletRequest request,SysCatalog catalog,@RequestParam Map<String,Object> map) {
		try {
			if (!map.get("catalogcode").toString().equals("")){
				String chkDblCode=commService.getFieldValue("select catalogcode from sys_catalog where catalogcode='"+ map.get("catalogcode").toString()+"'");
				if (chkDblCode!=""){
					map.put("statusCode", 201);
					map.put("errMsg", "编号重复了！");
					return map;
				}
			}
			sysCatalogService.saveSysCatalog(map);
			map.put("statusCode", 200);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString(), e);
			map.put("statusCode", 300);
		}
		return map;
	}

	// 进入修改页面
	@RequestMapping(value = "/editSysCatalog")
	public ModelAndView editSysCatalog(HttpServletRequest request) {
		Map<String, Object> map = ComponentHelper.requestToMap(request);
		try {
			SysCatalog sysCatalog = sysCatalogService.getSysCatalogById(map);
			mv.addObject("sysCatalog", sysCatalog);
			String parentname=commService.getFieldValue("select catalog from sys_catalog where catalog_id=" + sysCatalog.getParent_id());
			if (parentname==""){
				parentname="无上级";
			}
			mv.addObject("parentname",parentname);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString(), e);
		}
		mv.setViewName("systemManagement/syscatalog/syscatalog_edit");
		return mv;
	}

	// 保存修改数据
	@RequestMapping(value = "/updateSysCatalog")
	@ResponseBody
	public Map<String, Object> updateSysCatalog(HttpServletRequest request) {
		Map<String, Object> map = ComponentHelper.requestToMap(request);
		try {
			sysCatalogService.updateSysCatalog(map);
			map.put("statusCode", 200);
		} catch (Exception e) {
			log.error(e.toString(), e);
			map.put("statusCode", 300);
		}
		return map;
	}

	// 删除数据
	@RequestMapping(value = "/deleteSysCatalog")
	@ResponseBody
	public Map<String, Object> deleteSysCatalog(HttpServletRequest request,@RequestBody Map<String,Object> map) {
		try {
			sysCatalogService.deleteSysCatalog(map);
			map.put("statusCode", 200);
		} catch (Exception e) {
			log.error(e.toString(), e);
			map.put("statusCode", 300);
		}
		return map;
	}

	// 进入查看页面
	@RequestMapping(value = "/viewSysCatalog")
	public ModelAndView viewSysCatalog(HttpServletRequest request) {
		Map<String, Object> map = ComponentHelper.requestToMap(request);
		try {
			SysCatalog sysCatalog = sysCatalogService.getSysCatalogById(map);
			String parentname=commService.getFieldValue("select catalog from sys_catalog where catalog_id=" + sysCatalog.getParent_id());
			if (parentname==""){
				parentname="无上级";
			}
			mv.addObject("parentname",parentname);
			mv.addObject("sysCatalog", sysCatalog);
			mv.setViewName("systemManagement/syscatalog/syscatalog_view");
		} catch (Exception e) {
			log.error(e.toString(), e);
		}
		return mv;
	}
	// 进入排序页面
   @RequestMapping(value = "/sortSysCatalog")
   public ModelAndView sortSysCatalog(HttpServletRequest request) {
       try {
           List<Map<String,Object>> list=commService.getList("select * from sys_catalog where parent_id=0 order by sort");
           mv.addObject("syscataloglist", list);
       } catch (Exception e) {
           e.printStackTrace();
           log.error(e.toString(), e);
       }
       mv.setViewName("systemManagement/syscatalog/syscatalog_sort");
       return mv;
   }

   // 保存排序结果
   @RequestMapping(value = "/sortedSysCatalog")
   @ResponseBody
   public Map<String, Object> sortedSysCatalog(HttpServletRequest request) {
       Map<String, Object> map = ComponentHelper.requestToMap(request);
       try {
    	   sysCatalogService.SysDepartmentSort(map);
           map.put("statusCode", 200);
       } catch (Exception e) {
           log.error(e.toString(), e);
           e.printStackTrace();
           map.put("statusCode", 400);
       }
       return map;
   }
// 进入选择上级页面
   @RequestMapping(value = "/treeCatalog")
   public ModelAndView treeCatalog(HttpServletRequest request) {
       
       mv.setViewName("systemManagement/syscatalog/treecatalog");
       return mv;
   }
// 保存排序结果
   @RequestMapping(value = "/getCatalogChilds")
   @ResponseBody
   public Map<String, Object> getCatalogChilds(HttpServletRequest request) {
       Map<String, Object> map = ComponentHelper.requestToMap(request);
       try {
    	   List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
    	   if (map.get("dowhat").toString().equals("up")){
    		   String pid=commService.getFieldValue("select parent_id from sys_catalog where catalog_id=" + map.get("cid").toString()+"");
      		  list=commService.getList("select * from sys_catalog where parent_id=(select parent_id from sys_catalog where catalog_id=" + pid +") order by sort");

    	   }else{
    		  list=commService.getList("select * from sys_catalog where parent_id=" + map.get("cid").toString()+" order by sort");
    	   }
    	   map.put("list", list);
    	   map.put("statusCode", 200);
       } catch (Exception e) {
           log.error(e.toString(), e);
           e.printStackTrace();
           map.put("statusCode", 400);
       }
       return map;
   }
}
