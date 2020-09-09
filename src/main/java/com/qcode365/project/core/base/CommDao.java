package com.qcode365.project.core.base;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.qcode365.project.core.util.DateUtil;

@Repository
public class CommDao {

   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   
   @Resource
   private JdbcTemplate jdbcTemplate;
   
   public int getTotalCount(String table)  throws Exception{
       String sql = "select count(0) from " + table;
       //log.info(sql);
       return jdbcTemplate.queryForObject(sql,Integer.class);
   }
   
   //获取一个表格的数据
   public List<Map<String, Object>> getListbyTable(String table) throws Exception{
       return jdbcTemplate.queryForList("select * from " + table);
   }
   
   //获取一个SQL语句数据
   public List<Map<String, Object>> getList(String sql) throws Exception{
       //log.info(sql);
       List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
       DateUtil.buildList(list);
       return list;
   }
   
   //执行SQL语句
   public void ExcuteSQL (String sql)throws Exception{
       //log.info(sql);
       jdbcTemplate.execute(sql);
   }
   

}

