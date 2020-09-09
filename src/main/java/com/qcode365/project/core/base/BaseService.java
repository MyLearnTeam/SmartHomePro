package com.qcode365.project.core.base;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qcode365.project.core.shiro.ShiroUtil;

//===================版权说明=======================/
//本快速开发框架系统 版权归 快码猿所有                                                                    /
//本框架源代码由快码猿提供                               /
//注册商标：快码猿 QCode                              / 
//备注时间：2019-10-01                              / 
//===============================================/


public class BaseService<Mapper extends BaseMapper<T>,T extends BaseEntity<T>> extends ServiceImpl<BaseMapper<T>, T>{

   @Autowired
   public Mapper mapper;
   
   @Autowired
   public CommService commService;
   
   @Resource
    private JdbcTemplate jdbcTemplate;
   
   //按指定排序获取列表(eg: ew.groupBy("id,name").having("id={0}",22).and("password is not null").orderBy("id,name"))
   public List<T> getListByOrder(boolean isAsc,String orderColumn){
       EntityWrapper<T> qw = new EntityWrapper<T>();
       qw.orderBy(orderColumn, isAsc);
       return mapper.selectList(qw);
   }
   
   // 批量删除实体
   public void deleteBatchIds(String ids) {
       String[] idArray = ids.split(",");
       List<String> idList = Arrays.asList(idArray);
       mapper.deleteBatchIds(idList);
   }
   // 根据ids获取实体List
   public List<T> selectByIds(String ids) {
       String[] idArray = ids.split(",");
       List<String> idList = Arrays.asList(idArray);
       return mapper.selectBatchIds(idList);
   }

// 根据属性值获取列表
   public List<T> selectByColumn(String column, Object value, String orderBy, boolean isAsc) {
       return mapper.selectList(new EntityWrapper<T>().eq(column, value).orderBy(orderBy, isAsc));
   }

   // 根据属性值获取列表
   public List<T> selectByColumns(Map<String, Object> columnMap, String orderBy, boolean isAsc) {
       return mapper.selectList(new EntityWrapper<T>().allEq(columnMap).orderBy(orderBy, isAsc));
   }

   // 根据属性值获取列表
   public List<T> selectByColumns(Map<String, Object> columnMap) {
       return mapper.selectByMap(columnMap);
   }
          //写系统日志函数
          public void WriteSyslog(String title,String loginfo){
             try {
                  SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    //type_code:操作类型（01登录，02退出，03insert,04update,05delete,06list,07view,08checked,09unchecked,10others），操作说明：
                     Integer userId = ShiroUtil.getCurrentUser().getUser_id();    
                     String username =ShiroUtil.getCurrentUser().getTruename();
                     String ip_addr =ShiroUtil.getCurrentUser().getIp();
                     
                       String sql="";
                       sql="insert into sys_logs(user_id,user_name,ip_addr,log_type_id,title,log_info,create_time)";
                       sql=sql +" values("+userId+",'" + username +"','"+ip_addr+"','0','" + title +"','"+loginfo+"','"+ tf.format(new Date())+"')";
                       jdbcTemplate.execute(sql);
          
                   } catch (Exception e) {         
                      e.printStackTrace();
                     // WriteErrorLog(e.toString());
                   } 
                   
          }

}

