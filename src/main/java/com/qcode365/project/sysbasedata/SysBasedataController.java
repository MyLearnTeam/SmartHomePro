package com.qcode365.project.sysbasedata;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.qcode365.project.core.base.BaseController;
import com.qcode365.project.core.base.CommService;
import com.qcode365.project.core.systemManagement.Entity.FileEntity;
import com.qcode365.project.core.systemManagement.service.FileService;
import com.qcode365.project.core.systemManagement.Entity.SysUser;
import com.qcode365.project.core.systemManagement.service.SysUserService;
import com.qcode365.project.core.util.ComponentHelper;
import com.qcode365.project.core.config.BaseConfig;



   /* 模块名称：数据词典
   * Action Class
   * @author：快码猿(QCode)
   * @time：2020-01-07 15:02:43
   * List<Map<String,Object>> list =new ArrayList<Map<String, Object>>();
   * Map<String,Object>map=new HashMap<String, Object>();
   */

@Controller
public class SysBasedataController extends BaseController {

   private Logger logger =  LoggerFactory.getLogger(this.getClass());

   @Autowired
   private SysBasedataService sysbasedataService;
   @Autowired
   private FileService fileService;
   @Autowired
   private CommService commService;
   @Autowired
   private SysUserService sysuserService;

   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
   SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


   // 进入添加页面
   @RequestMapping(value = "/newSysBasedata")
   @RequiresPermissions("newSysBasedata")
   public ModelAndView newSysBasedata(HttpServletRequest request) {
       ModelAndView mv = new ModelAndView();
       try {
           mv.setViewName("sysbasedata/sysbasedata_new");
       } catch (Exception e) {
           logger.error(e.toString(), e);
           e.printStackTrace();
           buildErrorPage(mv,e.toString());
       }
       return mv;
   }


   // 通过对象保存新记录，并返回最新ID
   @RequestMapping(value = "/saveSysBasedata")
   @ResponseBody
   @RequiresPermissions("newSysBasedata")
   public Map<String, Object> saveSysBasedata(HttpServletRequest request,SysBasedata sysbasedata, @RequestParam Map<String,Object> reqMap) {
       Map<String, Object> respMap =new HashMap<String, Object>();
       try {
          //这里可以修改实体类的参数值
           sysbasedataService.insertSysBasedata(sysbasedata);
           buildSuccessResultMap(respMap);
       } catch (Exception e) {
           logger.error(e.toString(), e);
           e.printStackTrace();
           buildFailedResultMap(respMap);
           WriteErrorLog(e.toString());
       }
       return respMap;
   }

   // 进入修改页面
   @RequestMapping(value = "/editSysBasedata")
   @RequiresPermissions("editSysBasedata")
   public ModelAndView editSysBasedata(HttpServletRequest request) {
       ModelAndView mv = new ModelAndView();
       try {
           String param_id= request.getParameter("id");
           SysBasedata sysbasedata = sysbasedataService.selectById(param_id);
           mv.addObject("sysbasedata", sysbasedata);
           String parent_id_cn = "无上级";
           SysBasedata parent=sysbasedataService.selectById(sysbasedata.getParent_id());
           if (null != parent)
           {
               parent_id_cn = parent.getParam_token();
           }
           mv.addObject("parent_id_cn", parent_id_cn);
           mv.setViewName("sysbasedata/sysbasedata_edit");
       } catch (Exception e) {
           e.printStackTrace();
           logger.error(e.toString(), e);
           buildErrorPage(mv,e.toString());
       }
       return mv;
   }

   // 保存修改数据
   @RequestMapping(value = "/updateSysBasedata")
   @ResponseBody
   @RequiresPermissions("editSysBasedata")
   public Map<String, Object> updateSysBasedata(HttpServletRequest request,SysBasedata sysbasedata, @RequestParam Map<String,Object> reqMap) {
       Map<String, Object> respMap =new HashMap<String, Object>();
       try {
           sysbasedataService.updateSysBasedataById(sysbasedata);
           buildSuccessResultMap(respMap);
       } catch (Exception e) {
           logger.error(e.toString(), e);
           e.printStackTrace();
           buildFailedResultMap(respMap);
       }
       return respMap;
   }

   // 进入查看页面
   @RequestMapping(value = "/viewSysBasedata")
   public ModelAndView viewSysBasedata(HttpServletRequest request) {
       ModelAndView mv = new ModelAndView();
       try {
           String param_id= request.getParameter("id");
           SysBasedata sysbasedata = sysbasedataService.selectById(param_id);
           mv.addObject("sysbasedata", sysbasedata);
           String parent_id_cn = "无上级";
           SysBasedata parent=sysbasedataService.selectById(sysbasedata.getParent_id());
           if (null != parent)
           {
               parent_id_cn = parent.getParam_token();
           }
           mv.addObject("parent_id_cn", parent_id_cn);
           mv.setViewName("sysbasedata/sysbasedata_view");
       } catch (Exception e) {
           logger.error(e.toString(), e);
           e.printStackTrace();
           buildErrorPage(mv,e.toString());
       }
       return mv;
   }

   // 删除数据
   @RequestMapping(value = "/deleteSysBasedata")
   @ResponseBody
   @RequiresPermissions("deleteSysBasedata")
   public Map<String, Object> deleteSysBasedata(@RequestBody Map<String, Object> reqMap,HttpServletRequest request) {
       Map<String, Object> respMap =new HashMap<String, Object>();
       try {
           String ids = reqMap.get("ids")==null?"":reqMap.get("ids").toString();
           sysbasedataService.deleteSysBasedataBatchIds(ids);
           buildSuccessResultMap(respMap);
       } catch (Exception e) {
           logger.error(e.toString(), e);
           e.printStackTrace();
           buildFailedResultMap(respMap);
       }
       return respMap;
   }

   // 进入排序页面
   @RequestMapping(value ="/sortSysBasedata")
   @RequiresPermissions("sortSysBasedata")
   public ModelAndView sortSysBasedata(HttpServletRequest request) {
       ModelAndView mv = new ModelAndView();
       try {
    	   List<Map<String,Object>>list=commService.getList("select * from sys_basedata where parent_id=0 order by sort");
           mv.addObject("sysbasedatalist", list);
           mv.setViewName("sysbasedata/sysbasedata_sort");
       } catch (Exception e) {
           logger.error(e.toString(), e);
           e.printStackTrace();
           buildErrorPage(mv,e.toString());
       }
       return mv;
   }

   // 保存排序结果
   @RequestMapping(value = "/sortedSysBasedata")
   @ResponseBody
   @RequiresPermissions("sortSysBasedata")
   public Map<String, Object> sortedSysBasedata(@RequestBody Map<String, Object> reqMap,HttpServletRequest request) {
       Map<String, Object> respMap =new HashMap<String, Object>();
       try {
           String ids= reqMap.get("ids")==null?"":reqMap.get("ids").toString();
            
           sysbasedataService.sortedSysBasedataById(ids);
           
           buildSuccessResultMap(respMap);
       } catch (Exception e) {
           logger.error(e.toString(), e);
           e.printStackTrace();
         
           buildFailedResultMap(respMap);
       }
       return respMap;
   }
 
   // 进入列表页面
   @RequestMapping(value = "/listSysBasedata")
   @RequiresPermissions("listSysBasedata")
   public ModelAndView listSysBasedata(HttpServletRequest request) {
       ModelAndView mv = new ModelAndView();
       try {
           //todo
          mv.setViewName("sysbasedata/sysbasedata_list");
       } catch (Exception e) {
           logger.error(e.toString(), e);
           e.printStackTrace();
           buildErrorPage(mv,e.toString());
       }
       return mv;
   }

   // 获取列表数据
   @RequestMapping(value = "/getSysBasedataList")
   @ResponseBody
   public Map<String, Object> getSysBasedataList(@RequestParam Map<String, Object> reqMap,HttpServletRequest request) throws Exception {
       Map<String, Object> respMap =new HashMap<String, Object>();
       try {
           respMap=sysbasedataService.getSysBasedataList(reqMap);
           buildSuccessResultMap(respMap);
       } catch (Exception e) {
           e.printStackTrace();
           logger.error(e.toString(), e);
           buildFailedResultMap(respMap);
       }
       return respMap;

   }


// 进入选择上级页面
@RequestMapping(value = "/treeSysBasedata")
public ModelAndView treeSysBasedata(HttpServletRequest request) {
	ModelAndView mv = new ModelAndView();
    mv.addObject("parentwinname",request.getParameter("parentwinname"));
	mv.setViewName("sysbasedata/treesysbasedata");
	return mv;
}
// 获取树数据
@RequestMapping(value = "/getSysBasedataTreeData")
@ResponseBody
public List<Map<String, Object>> getSysBasedataTreeData(HttpServletRequest request) throws Exception {
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	try {
		String RootText=request.getParameter("RootText")==null?"树型结构":request.getParameter("RootText");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", 0);
		map.put("pId", -1);
		map.put("name", RootText);
		map.put("open", 1);
		list.add(map);
		List<Map<String, Object>> list1 = sysbasedataService.getSysBasedataTreeData();
		for(int k=0;k<list1.size();k++) {
			list1.get(k).put("open", 1);
		}
		list.addAll(list1);
	} catch (Exception e) {
		e.printStackTrace();
		logger.error(e.toString(), e);
	}
	return list;
}

   //todo 新添加功能=================================================================================
// 获取层级数据
@RequestMapping(value = "/getSysBaseDataChilds")
@ResponseBody
public Map<String, Object> getSysBaseDataChilds(HttpServletRequest request) {
    Map<String, Object> map = ComponentHelper.requestToMap(request);
    try {
 	   List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
 	   if (map.get("dowhat").toString().equals("up")){
 		   String pid=commService.getFieldValue("select parent_id from sys_basedata where param_id=" + map.get("pid").toString()+"");
   		  list=commService.getList("select * from sys_basedata where parent_id=(select parent_id from sys_basedata where param_id=" + pid +") order by sort");

 	   }else{
 		  list=commService.getList("select * from sys_basedata where parent_id=" + map.get("pid").toString()+" order by sort");
 	   }
 	   map.put("list", list);
 	   map.put("statusCode", 200);
    } catch (Exception e) {
     
        e.printStackTrace();
        map.put("statusCode", 400);
    }
    return map;
}
}


