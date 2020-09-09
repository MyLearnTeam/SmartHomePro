package com.qcode365.project.core.systemManagement.Entity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import com.qcode365.project.core.base.BaseEntity;

   /** 模块名称：
   * Entity Class
   * author：QCode
   * time：2019-06-24 12:50:35
   */

   @TableName("sys_logs")
   public class SysLogs extends BaseEntity<SysLogs>{
       // Fields
       private static final long serialVersionUID = 1L;

       @TableId(type = IdType.AUTO)
       private Integer log_id; //key id

       @TableField
       private Integer log_type_id; //日志类型

       @TableField
       private String title; //标题

       @TableField
       private String log_info; //日志信息

       @TableField
       private Integer user_id; //操作用户(0-代表系统)

       @TableField
       private String user_name; //操作人

       @TableField
       private String ip_addr; //IP地址

       @TableField
       private String channel; //登陆方式

    
       ///////////////////////////////
       /** default constructor */
       public SysLogs() {
       }
       /** full constructor */
       ///////////////////////////////

       public Integer getLog_id() {
           return log_id;
       }
       public void setLog_id(Integer log_id) {
           this.log_id = log_id;
       }
       public Integer getLog_type_id() {
           return log_type_id;
       }
       public void setLog_type_id(Integer log_type_id) {
           this.log_type_id = log_type_id;
       }
       public String getTitle() {
           return title;
       }
       public void setTitle(String title) {
           this.title = title;
       }
       public String getLog_info() {
           return log_info;
       }
       public void setLog_info(String log_info) {
           this.log_info = log_info;
       }
       public Integer getUser_id() {
           return user_id;
       }
       public void setUser_id(Integer user_id) {
           this.user_id = user_id;
       }
       public String getUser_name() {
           return user_name;
       }
       public void setUser_name(String user_name) {
           this.user_name = user_name;
       }
       public String getIp_addr() {
           return ip_addr;
       }
       public void setIp_addr(String ip_addr) {
           this.ip_addr = ip_addr;
       }
       public String getChannel() {
           return channel;
       }
       public void setChannel(String channel) {
           this.channel = channel;
       }
   }
