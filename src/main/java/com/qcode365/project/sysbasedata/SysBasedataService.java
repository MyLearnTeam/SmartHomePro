package com.qcode365.project.sysbasedata;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.qcode365.project.core.shiro.ShiroUtil;
import com.qcode365.project.core.base.BaseService;


   /* 模块名称：数据词典
   * Service Class
   * @author：快码猿(QCode)
   * @time：2020-01-07
   * List<Map<String,Object>> list =new ArrayList<Map<String, Object>>();
   * Map<String,Object>map=new HashMap<String, Object>();
   */

@Transactional
@Service
public class SysBasedataService extends BaseService<SysBasedataMapper,SysBasedata>{
   
   String thisTitle="数据词典";//模块名称
   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
   SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   private Logger logger =  LoggerFactory.getLogger(this.getClass());

   //插入记录
   public void insertSysBasedata(SysBasedata sysbasedata) throws Exception {
     if (sysbasedata.getSort()==null)
     {
        Integer sort = commService.getMaxSort("sys_basedata");
        sysbasedata.setSort(sort);
     }
   
     insert(sysbasedata);
 
     WriteSyslog(thisTitle+" 添加记录成功 ",sysbasedata.toString());
   }

   //更新记录
   public void updateSysBasedataById(SysBasedata sysbasedata) throws Exception {
    
     SysBasedata oldsysbasedata = selectById(sysbasedata.getParam_id());
     updateById(sysbasedata);
     WriteSyslog(thisTitle+" 修改记录成功 ",oldsysbasedata.toString() +"-->" + sysbasedata.toString());
   }

   //删除记录
   public void deleteSysBasedataBatchIds(String ids) throws Exception {
    
     List<Map<String,Object>>list=commService.getList("select * from sys_basedata where param_id in(" + ids +")");
     deleteBatchIds(ids);
     WriteSyslog(thisTitle+" 删除记录成功 ",list.toString() );
   }
   //排序结果
   public void sortedSysBasedataById(String idstr) throws Exception {
    if (!idstr.equals("")) {
        String[] ids = idstr.split(",");
        List<SysBasedata> list=new ArrayList<SysBasedata>();
        for (int k = 0; k < ids.length; k++) {
           SysBasedata sysbasedata=new SysBasedata();
           sysbasedata.setParam_id(Integer.parseInt(ids[k]));
           sysbasedata.setSort(k+1);
           list.add(sysbasedata);
        }
        updateBatchById(list);
    }
   }

 //根据字典类型和数据项ID获取值
   public SysBasedata selectByTypeId(String token,Integer index) throws Exception {
	 
	        List<SysBasedata> list=new ArrayList<SysBasedata>();
	        list=commService.getList("select * from sys_basedata where parent_id in(select param_id from sys_basedata where param_token='" + token +"')  and pvalue=" + index +"",SysBasedata.class);
	        if (list.size()>0)
	        {
	        	return list.get(0);
	        }
	        else
	        {
	        	return null;
	        }
   }
   //根据字典类型和数据项ID获取值
   public List<SysBasedata> selectByType(String token) throws Exception {
	 
	        List<SysBasedata> list=new ArrayList<SysBasedata>();
	        String sql;
	        if(token.equals(""))
	        {
	        	sql="select * from sys_basedata order by sort";
	        }
	        else
	        {
	        	sql="select * from sys_basedata where parent_id in(select param_id from sys_basedata where param_token='" + token +"') order by sort";
	        }
	        list=commService.getList(sql,SysBasedata.class);

	        return list;
   }

   // 根据条件查询记录
   public Map<String, Object> getSysBasedataList(Map<String, Object> map) throws Exception {

      Map<String, Object> respMap =new HashMap<String, Object>();
      MapSqlParameterSource parameters= new MapSqlParameterSource();
      //Integer userId = ShiroUtil.getCurrentUser().getUser_id();
      Integer start =map.get("offset")==null?0:Integer.parseInt(map.get("offset").toString());//从0页开始
      Integer length =map.get("limit")==null?15:Integer.parseInt(map.get("limit").toString());//默认15条每页
       
      String countstr = "select count(0) as row from";
      String sqlstr = "select * ";
      sqlstr +=" from ";//todo
      String sTable=" sys_basedata ";
      String orderBy = " order by sort";// 在这里修改排序
      String strWhere = " where 1=1 ";
       //获取参数值
       
       //todo 组装查询条件==================================
       if(map.get("parent_id")!=null && !map.get("parent_id").equals("")){
          strWhere +=" and parent_id=:parent_id";
          parameters.addValue("parent_id",map.get("parent_id").toString().trim());
       }
       
       
       String sql=sqlstr + sTable + strWhere + orderBy + " limit " + start +","+length;
       List<Map<String, Object>> list =commService.getList(sql,parameters);
       int totalCount =commService.getFieldIntValue(countstr + sTable + strWhere,parameters);
       for (int k=0;k<list.size();k++) {
          String parent_id_cn = "";
          SysBasedata parent=selectById(list.get(k).get("parent_id").toString());
          if (null != parent)
          {
              parent_id_cn = parent.getParam_token();
           }
          list.get(k).put("parent_id_cn", parent_id_cn);
       }
       
       
       respMap.put("rows", list);
       respMap.put("total", totalCount);
      
      
       return respMap;
   }

//获取树数据
public List<Map<String, Object>> getSysBasedataTreeData() throws Exception{
    String sql  = "select param_id as id,parent_id  as pId,ptext as name from sys_basedata order by sort";
    List<Map<String,Object>> list =commService.getList(sql );
    return list;
}
   //todo 新添加功能=======================================================================
   

}


