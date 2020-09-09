package com.qcode365.project.core.systemManagement.service;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.qcode365.project.core.base.BaseService;
import com.qcode365.project.core.systemManagement.Entity.SysLogs;
import com.qcode365.project.core.systemManagement.mappers.SysLogsMapper;

//===================版权说明=======================/
//本快速开发框架系统 版权归 快码猿所有                                                                    /
//本框架源代码由快码猿提供                               /
//注册商标：快码猿 QCode                              / 
//备注时间：2019-10-01                              / 
//===============================================/

@Transactional
@Service
public class SysLogsService extends BaseService<SysLogsMapper,SysLogs>{
   
   // 根据条件查询记录
   public Map<String, Object> searchSysLogs(Map<String, Object> map) throws Exception {

      Map<String, Object> respMap =new HashMap<String, Object>();
      MapSqlParameterSource parameters= new MapSqlParameterSource();
      Integer start =map.get("offset")==null?0:Integer.parseInt(map.get("offset").toString());//从0页开始
      Integer length =map.get("limit")==null?15:Integer.parseInt(map.get("limit").toString());//默认15条每页
       
      String sqlstr = "select * ";
      sqlstr +=" from";//todo

      String countstr = "select count(0) as row from";
      String sTable=" sys_logs ";
      String orderBy = "";// 在这里修改排序
      String strWhere = "";
       //获取参数值
       
       //todo 组装查询条件==================================
       
       
     //=========================
       if(!strWhere.equals("")){
          strWhere=strWhere.trim();
          if(strWhere.startsWith("and")){//以and开头时要处理
              strWhere=" where "+ strWhere.substring(3,strWhere.length());
          }
       }
       
       String sql=sqlstr + sTable + strWhere + orderBy + " limit " + start +","+length;
       List<Map<String, Object>> list =commService.getList(sql,parameters);
       int totalCount =commService.getFieldIntValue(countstr + sTable + strWhere,parameters);
           
       respMap.put("rows", list);
       respMap.put("total", totalCount);
      
      
       return respMap;
   }

   //todo 新添加功能===============================================================================================================
   

}


