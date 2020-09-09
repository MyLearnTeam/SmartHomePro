package com.qcode365.project.core.base;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.util.ArrayList;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.qcode365.project.core.util.DateUtil;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

//===================版权说明=======================/
//本快速开发框架系统 版权归 快码猿所有                                                                    /
//本框架源代码由快码猿提供                               /
//注册商标：快码猿 QCode                              / 
//备注时间：2019-10-01                              / 
//===============================================/

@Transactional
@Service
public class CommService {
   @Autowired  //@Resource(name = "sqlSessionTemplate")
   private SqlSessionTemplate sqlSessionTemplate;
   @Autowired
   private JdbcTemplate jdbcTemplate;
    
   /**
    * 输入SQL语句
    */
   public void WriteSQL(String sql) throws Exception {
        System.out.println(sql);
    }
   
   /**
    * 执行SQL语句（INSERT,UPDATE,DALETE)     
    * @param sql
    * @throws Exception
    */
    public void ExcuteSQL(String sql) throws Exception {
        
        jdbcTemplate.execute(sql);
    }
    
    /**
     * 执行一条查询语句,返回map list
     */
    public List<Map<String, Object>> getList(String sql)  throws Exception {
       List<Map<String, Object>>list=jdbcTemplate.queryForList(sql);
       DateUtil.buildList(list);
        return list;
     }
    
    /**
     * 执行一条查询语句采用？号传参数,返回map list
     * @param sql
     * @param parameters
     */
    public List<Map<String, Object>> getList(String sql,MapSqlParameterSource parameters)  throws Exception {
       NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
       List<Map<String, Object>>list=namedParameterJdbcTemplate.queryForList(sql, parameters);
          DateUtil.buildList(list);
       return list;
     }
    
    /**
     * 执行一条SQL语句，返回Bean list
     * @param sql
     * @param T
     * @return
     * @throws Exception
     */
    public <T> List<T> getList(String sql, Class<T> T)  throws Exception {
        List<Map<String, Object>> mapList=jdbcTemplate.queryForList(sql);
        return     convertListMap2ListBean(mapList,T);

    }
    
    /**
     * 获取一个语句查询到的记录数,
     * @param sql
     */
    public int getRecordCount(String sql) throws Exception {
        List<Map<String, Object>> list =jdbcTemplate.queryForList(sql);
        int iRet=0;
        if (list!=null){
           iRet=list.size();
        }
        return iRet;
    }
    
    /**
     * 获取一个字段的值，int
     * @param sql
     */
    public int getFieldIntValue(String sql) throws Exception {
        int iRet=0;
       String fieldname="";//提前获取值得字段名,select count(0) from d_city
        if (sql.indexOf("count(0) from")>0)
        {
            sql=sql.replace("from", "as fieldname from");
        }
        String[] aa=sql.split("from");
        String str=aa[0].trim();
        aa=str.split(" ");
        fieldname=aa[aa.length-1];
        List<Map<String, Object>> list =jdbcTemplate.queryForList(sql);
        if (list.size() > 0) {
            if (list.get(0).get(fieldname)!=null)
            {
               iRet=Integer.parseInt(list.get(0).get(fieldname).toString());
            }
            else
            {
               iRet=0;//null返回0
            }
        } else {
           iRet=0;// 没记录时返回0
        }
        return iRet;
    }
    
    /**
     * 获取一个字段的值，int
     * @param sql
     * @param parameters
     */
    public int getFieldIntValue(String sql,MapSqlParameterSource parameters)  throws Exception {
       NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        int iRet=0;
       String fieldname="";//提前获取值得字段名,select count(0) from d_city
        if (sql.indexOf("count(0) from")>0)
        {
            sql=sql.replace("from", "as fieldname from");
        }
        String[] aa=sql.split("from");
        String str=aa[0].trim();
        aa=str.split(" ");
        fieldname=aa[aa.length-1];
        List<Map<String, Object>> list =namedParameterJdbcTemplate.queryForList(sql, parameters);//jdbcTemplate.queryForList(sql);
        if (list.size() > 0) {
            if (list.get(0).get(fieldname)!=null)
            {
               iRet=Integer.parseInt(list.get(0).get(fieldname).toString());
            }
            else
            {
               iRet=0;//null返回0
            }
        } else {
           iRet=0;// 没记录时返回0
        }
        return iRet;
    }
    
    /**
     * 获取一个字段的值，float
     * @param sql
     */
    public float getFieldFloatValue(String sql) throws Exception {
        float iRet=0;
       String fieldname="";//提前获取值得字段名,select count(0) from d_city
        if (sql.indexOf("count(0) from")>0)
        {
            sql=sql.replace("from", "as fieldname from");
        }
        String[] aa=sql.split("from");
        String str=aa[0].trim();
        aa=str.split(" ");
        fieldname=aa[aa.length-1];
        List<Map<String, Object>> list =jdbcTemplate.queryForList(sql);
        if (list.size() > 0) {
            if (list.get(0).get(fieldname)!=null)
            {
               iRet=Float.parseFloat(list.get(0).get(fieldname).toString());
            }
            else
            {
               iRet=0;//null返回0
            }
        } else {
           iRet=0;// 没记录时返回0
        }
        return iRet;
    }
    
    /**
     * 获取一个字段的值，varchar
     * @param sql
     */
    public String getFieldValue(String sql) throws Exception {
       String iRet="";
       String fieldname="";//提前获取值得字段名,select count(0) from d_city
        if (sql.indexOf("count(0) from")>0)
        {
            sql=sql.replace("from", "as fieldname from");
        }
        String[] aa=sql.split("from");
        String str=aa[0].trim();
        aa=str.split(" ");
        fieldname=aa[aa.length-1];
        List<Map<String, Object>> list =jdbcTemplate.queryForList(sql);
        if (list.size() > 0) {
            if (list.get(0).get(fieldname)!=null)
            {
               iRet=list.get(0).get(fieldname).toString();
            }
            else
            {
               iRet="";//null返回""
            }
        } else {
           iRet="";// 没记录时返回""
        }
        return iRet;
    }
    /**
     * 获取一个字段的值，varchar
     * @param sql
     * @param parameters
     */
    public String getFieldValue(String sql,MapSqlParameterSource parameters)  throws Exception {
       NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
       String iRet="";
       String fieldname="";//提前获取值得字段名,select count(0) from d_city
        if (sql.indexOf("count(0) from")>0)
        {
            sql=sql.replace("from", "as fieldname from");
        }
        String[] aa=sql.split("from");
        String str=aa[0].trim();
        aa=str.split(" ");
        fieldname=aa[aa.length-1];
        List<Map<String, Object>> list =namedParameterJdbcTemplate.queryForList(sql, parameters);//jdbcTemplate.queryForList(sql);
        if (list.size() > 0) {
            if (list.get(0).get(fieldname)!=null)
            {
               iRet=list.get(0).get(fieldname).toString();
            }
            else
            {
               iRet="";//null返回""
            }
        } else {
           iRet="";// 没记录时返回""
        }
        return iRet;
    }
    
    /**
     * 获取一个表的最大排序号
     * @param table
     * @return
     * @throws Exception
     */
  
    public int getMaxSort(String table) throws Exception {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select max(sort)+1 as maxid from " + table);
        if (list.size() > 0) {
            if (list.get(0).get("maxid") != null) {//list.get(0).get(0)不能用序号来取值，必须用字段名字取值
                String fieldvalue=list.get(0).get("maxid").toString();
                if (fieldvalue.indexOf(".")>0)
                {
                    String aa[]=fieldvalue.split("\\.");
                    fieldvalue=aa[0];
                }
                return Integer.parseInt(fieldvalue);
            } else {
                return 1;// null返回1
            }
        } else {
            return 1;// 没记录时返回1
        }

    } 
//================================================================================================================ 
   
 
    /**
    * 保存对象
    * @param str
    * @param obj
    * @return
    * @throws Exception
    */
   public Object save(String str, Object obj) throws Exception {
    
       return sqlSessionTemplate.insert(str, obj);
   }
   /**
    * 批量更新
    * @param str
    * @param objs
    * @return
    * @throws Exception
    */
   public Object batchSave(String str, List objs) throws Exception {
       return sqlSessionTemplate.insert(str, objs);
   }

   /**
    * 修改对象
    * @param str
    * @param obj
    * @return
    * @throws Exception
    */
   public Object update(String str, Object obj) throws Exception {
        
       return sqlSessionTemplate.update(str, obj);
   }

   /**
    * 批量更新
    * @param str
    * @param objs
    * @return
    * @throws Exception
    */
   public void batchUpdate(String str, List objs) throws Exception {
       SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
       // 批量执行器
       SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
       try {
           if (objs != null) {
               for (int i = 0, size = objs.size(); i < size; i++) {
                   sqlSession.update(str, objs.get(i));
               }
               sqlSession.flushStatements();
               sqlSession.commit();
               sqlSession.clearCache();
           }
       } finally {
           sqlSession.close();
       }
   }

   /**
    * 批量更新
    * @param str
    * @param objs
    * @return
    * @throws Exception
    */
   public Object batchDelete(String str, List objs) throws Exception {
       return sqlSessionTemplate.delete(str, objs);
   }

   /**
    * 删除对象
    * @param str
    * @param obj
    * @return
    * @throws Exception
    */
   public Object delete(String str, Object obj) throws Exception {
       return sqlSessionTemplate.delete(str, obj);
   }
   
   /**
    * 查找对象
    * @param str
    * @param obj
    * @return
    * @throws Exception
    */
   public Object findForObject(String str, Object obj) throws Exception {
       return sqlSessionTemplate.selectOne(str, obj);
   }

   /**
    * 查找对象
    * @param str
    * @param obj
    * @return
    * @throws Exception
    */
   public Object findForList(String str, Object obj) throws Exception {
       return sqlSessionTemplate.selectList(str, obj);
   }
   
   //获取权限============================================================

   /**
    * 获取用户的所有权限列表
    */
   
    public List<Map<String, Object>> getUserPopedomList(Integer userid) throws Exception {
        String sql = "select catalog_id,catalogcode,catalog,url,parent_id,is_menu from sys_catalog where catalog_id in(select catalog_id from sys_role_user a,sys_role_popedom b where a.role_id=b.role_id and a.user_id=" + userid +")";
        return jdbcTemplate.queryForList(sql);
    }
    /**
     * 获取具有某个权限的全部人员清单
     * @param catalogcode   是权限标识
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getUserListbyPopCode(String catalogcode) throws Exception {
      Integer popid=getFieldIntValue("select catalog_id from sys_catalog where catalogcode='" + catalogcode +"' limit 0,1");
      String sql = "select * from sys_userinfo where user_id in(select user_id from sys_role_user a,sys_role_popedom b where a.role_id=b.role_id and b.catalog_id=" + popid +")";
      return jdbcTemplate.queryForList(sql);
        
    }
    /**
     * 检查用户是否具有某个权限
     * @param Userid
     * @param catalogcode
     * @return
     * @throws Exception
     */
    public boolean isHadPopbyCode(Integer Userid,String catalogcode) throws Exception {
      boolean iRet=false;
      Integer popid=getFieldIntValue("select catalog_id from sys_catalog where catalogcode='" + catalogcode +"' limit 0,1");
      String sql = "select * from sys_userinfo where user_id in(select user_id from sys_role_user a,sys_role_popedom b where a.role_id=b.role_id and b.catalog_id=" + popid +") ";
      List<Map<String, Object>> list=getList(sql);
      for (int k=0;k<list.size();k++)
      {
          if (Userid.equals(list.get(k).get("user_id").toString()))
          {
              iRet=true;
              break;
          }
      }
            
      return iRet;
     } 
    
   /**
    * 获取列表的权限按钮
    */
    
    
   //通用函数----------------------------------------------------------------------------------------------
   /**
    * 将 List<Map>对象转化为List<JavaBean> <一句话功能简述> <功能详细描述>
    * 
    * @param listMap
    * @param T
    * @return
    * @throws Exception
    * @see [类、类#方法、类#成员]
    */
   public <T> List<T> convertListMap2ListBean(List<Map<String, Object>> listMap, Class<T> T) throws Exception {
       List<T> beanList = new ArrayList<>();
       if (listMap != null && !listMap.isEmpty()) {
           for (int i = 0, n = listMap.size(); i < n; i++) {
               Map<String, Object> map = listMap.get(i);
               T bean = convertMap2Bean(map, T);
               beanList.add(bean);
           }
           return beanList;
       }
       return beanList;
   }

   /**
    * 将 List<JavaBean>对象转化为List<Map> <一句话功能简述> <功能详细描述>
    * 
    * @param beanList
    * @return
    * @throws Exception
    * @see [类、类#方法、类#成员]
    */
   public static <T> List<Map<String, Object>> convertListBean2ListMap(List<T> beanList, Class<T> T) throws Exception {
       List<Map<String, Object>> mapList = new ArrayList<>();
       for (int i = 0, n = beanList.size(); i < n; i++) {
           Object bean = beanList.get(i);
           Map<String, Object> map = convertBean2Map(bean);
           mapList.add(map);
       }
       return mapList;
   }
   /**
    * 将 Map对象转化为JavaBean <一句话功能简述> <功能详细描述>
    * 
    * @param map
    * @param T
    * @return
    * @throws Exception
    * @see [类、类#方法、类#成员]
    */
   public <T> T convertMap2Bean(Map<String, Object> map, Class<T> T) throws Exception {
       if (map == null || map.size() == 0) {
           return null;
       }
       // 获取map中所有的key值，全部更新成大写，添加到keys集合中,与mybatis中驼峰命名匹配
       Object mvalue = null;
       Map<String, Object> newMap = new HashMap<>();
       Iterator<Entry<String, Object>> it = map.entrySet().iterator();
       while (it.hasNext()) {
           String key = it.next().getKey();
           mvalue = map.get(key);
           //if (key.indexOf(CharacterConstant.UNDERLINE) != -1) {
           //  key = key.replaceAll(CharacterConstant.UNDERLINE, "");
           //}
           newMap.put(key.toUpperCase(Locale.US), mvalue);
       }

       BeanInfo beanInfo = Introspector.getBeanInfo(T);
       T bean = T.newInstance();
       PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
       for (int i = 0, n = propertyDescriptors.length; i < n; i++) {
           PropertyDescriptor descriptor = propertyDescriptors[i];
           String propertyName = descriptor.getName();
           String upperPropertyName = propertyName.toUpperCase();
           String propertyTypeName=descriptor.getPropertyType().getName();
           if (newMap.keySet().contains(upperPropertyName)) {
               Object value = newMap.get(upperPropertyName);
               //针对int基础数据
               if(propertyTypeName.equals("int")&&value==null){
                   value=0;
               }
               // 这个方法不会报参数类型不匹配的错误。
               BeanUtils.copyProperty(bean, propertyName, value);
           }
       }
       return bean;
   }

   /**
    * 将一个 JavaBean 对象转化为一个 Map <一句话功能简述> <功能详细描述>
    * 
    * @param bean
    * @return
    * @throws IntrospectionException
    * @throws IllegalAccessException
    * @throws InvocationTargetException
    * @see [类、类#方法、类#成员]
    */
   public static Map<String, Object> convertBean2Map(Object bean) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
       Class<? extends Object> type = bean.getClass();
       Map<String, Object> returnMap = new HashMap<>();
       BeanInfo beanInfo = Introspector.getBeanInfo(type);

       PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
       for (int i = 0; i < propertyDescriptors.length; i++) {
           PropertyDescriptor descriptor = propertyDescriptors[i];
           String propertyName = descriptor.getName();
           if (!"class".equals(propertyName)) {
               Method readMethod = descriptor.getReadMethod();
               Object result = readMethod.invoke(bean, new Object[0]);
               if (result != null) {
                   returnMap.put(propertyName, result);
               } else {
                   returnMap.put(propertyName, null);
               }
           }
       }
       return returnMap;
   }

/**
 * 获取网卡MAC地址
 */
public String NetWorkMac(){
   String sMac="";
   try {
         Enumeration<NetworkInterface> el = NetworkInterface.getNetworkInterfaces();
         StringBuilder builder = new StringBuilder();
         while (el.hasMoreElements()) {
             byte[] mac = el.nextElement().getHardwareAddress();
             if (mac == null){
                continue;
             }
             if(builder.length() > 0){
                 builder.append(",");
             }
             for (byte b : mac) {
                 
                //convert to hex string.
                String hex = Integer.toHexString(0xff & b).toUpperCase();
                if(hex.length() == 1){
                    hex  = "0" + hex;
                }
                builder.append(hex);
                builder.append("-");
             }
             builder.deleteCharAt(builder.length() - 1);
        }
        
             
        sMac=builder.toString();
        
    }catch (Exception exception) {
        exception.printStackTrace();
    }
   return sMac;
 
}

//组合条件查询，分页及排序
  public List<Map<String, Object>> getSearchList(String sqlstr, int start, int length) throws Exception {
     String sql = "";
     // 在这里区分不同数据库类型
     sql = sqlstr + " limit " + start + "," + length;
     return jdbcTemplate.queryForList(sql);
  }
// 获取一个数据表的记录行数
  public int getTotalCount(String table) throws Exception {
     String sql = "select count(0) from " + table;
     return jdbcTemplate.queryForObject(sql,Integer.class);
  }
 
  public static String md5(String str) {
       try {
           MessageDigest md = MessageDigest.getInstance("MD5");
           md.update(str.getBytes());
           byte b[] = md.digest();

           int i;

           StringBuffer buf = new StringBuffer("");
           for (int offset = 0; offset < b.length; offset++) {
               i = b[offset];
               if (i < 0)
                   i += 256;
               if (i < 16)
                   buf.append("0");
               buf.append(Integer.toHexString(i));
           }
           str = buf.toString();
       } catch (Exception e) {
           e.printStackTrace();

       }
       return str;
   }
  
}



