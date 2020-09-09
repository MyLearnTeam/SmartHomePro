package com.qcode365.project.core.systemManagement.service;

import java.util.*;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qcode365.project.core.base.BaseService;
import com.qcode365.project.core.base.CommService;
import com.qcode365.project.core.systemManagement.Entity.SysDepartment;
import com.qcode365.project.core.systemManagement.mappers.SysDepartmentMapper;

//===================版权说明=======================/
//本快速开发框架系统 版权归 快码猿所有                                                                    /
//本框架源代码由快码猿提供                               /
//注册商标：快码猿 QCode                              / 
//备注时间：2019-10-01                              / 
//===============================================/
@Transactional
@Service
public class SysDepartmentService extends BaseService<SysDepartmentMapper,SysDepartment>{
	
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private SysDepartmentMapper sysdepartmentMapper;
    @Resource
    private CommService commService;


    public Map<String, Object> getListByPage(Map<String, Object> map, Map<String, Object> respMap) throws Exception {

		String sqlstr = "select * from";
		String countstr = "select count(0) as row from";

		String sTable = " sys_department ";
		String orderBy = " order by create_time desc";// 在这里修改排序
		String strWhere = " where parent_id>0 ";
		
		// 获取参数值
		String start = map.get("offset") == null ? "0" : map.get("offset").toString();// 从0页开始
		String length = map.get("limit") == null ? "15" : map.get("limit").toString();// 默认15条每页
		String parentid = map.get("parentid") == null ? "" : map.get("parentid").toString();

		// todo 组装查询条件==================================
		if (!"".equals(parentid)) {
			strWhere += " and parent_id=" + parentid;
		}

		// =========================

		List<Map<String, Object>> list = commService.getList(sqlstr + sTable + strWhere + orderBy + " limit " + start + "," + length);
		int totalCount = commService.getFieldIntValue(countstr + sTable + strWhere);

		respMap.put("rows", list);
		respMap.put("total", totalCount);

		return respMap;
	}
    
   
   
    // 排序
    public void SysDepartmentSort(Map<String, Object> map) throws Exception {

        String idstr = map.get("ids").toString();
        if (idstr != null) {
            String[] ids = idstr.split(",");
            for (int k = 0; k < ids.length; k++) {
                commService.ExcuteSQL("update  sys_department set sort=" + (k + 1)
                        + " where depart_id=" + ids[k]);
            }
        }
    }
    //todo 新添加功能===============================================================================================================

    //获取树数据
    public List<Map<String, Object>> getSysDepartmentTreeData() {
        String sql  = "select  depart_id as id,parent_id  as pId,depart_name as name from sys_department order by sort";
        List<Map<String,Object>> list =jdbcTemplate.queryForList(sql );//部门

        return list;
    }
}


