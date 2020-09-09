package com.qcode365.project.core.base;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.qcode365.project.core.util.DateUtil;

//===================版权说明=======================/
//本快速开发框架系统 版权归 快码猿所有                                                                    /
//本框架源代码由快码猿提供                               /
//注册商标：快码猿 QCode                              / 
//备注时间：2019-10-01                              / 
//===============================================/
/**
 * 基础实体类, 每个实体类应继承该类获取四个基本属性
 * @author wwh
 */
public class BaseEntity<T extends BaseEntity<T>> extends Model<T> implements Serializable{
   
   private static final long serialVersionUID = 8161554597404141427L;
   
   @TableField(value="create_user_id",fill=FieldFill.INSERT)
   private Integer createUserId;
   
   @TableField(value="create_time",fill=FieldFill.INSERT)
   private Date createTime;
   
   @TableField(exist = false)
   private String createTime_tf;
   
   @TableField(exist = false)
   private String createTime_df;
   
   @TableField(value="update_user_id",fill=FieldFill.INSERT_UPDATE)
   private Integer updateUserId;
   
   @TableField(value="update_time",fill=FieldFill.INSERT_UPDATE)
   private Date updateTime;
   
   @TableField(exist = false)
   private String updateTime_tf;
   
   @TableField(exist = false)
   private String updateTime_df;
   
   @TableField(exist = false)
   private String createUser;
   
   @TableField(exist = false)
   private String updateUser;
    
   
   //================
   
   public String getCreateUser() {
       
       return createUser;
   }
   public void setCreateUser(String createUser) {
       this.createUser = createUser;
   }
   public String getUpdateUser() {
       return updateUser;
   }
   public void setUpdateUser(String updateUser) {
       this.updateUser = updateUser;
   }

   
   public Integer getCreateUserId() {
       return createUserId;
   }
   public void setCreateUserId(Integer createUserId) {
       this.createUserId = createUserId;
   }
   public Date getCreateTime() {
       return createTime;
   }
   public void setCreateTime(Date createTime) {
       this.createTime = createTime;
       if (null != createTime) {
           this.createTime_df = DateUtil.dateFormat.format(createTime);
           this.createTime_tf = DateUtil.datetimeFormat.format(createTime);
       }
   }
   public Integer getUpdateUserId() {
       return updateUserId;
   }
   public void setUpdateUserId(Integer updateUserId) {
       this.updateUserId = updateUserId;
   }
   public Date getUpdateTime() {
       return updateTime;
   }
   public void setUpdateTime(Date updateTime) {
       this.updateTime = updateTime;
       if (null != updateTime) {
           this.updateTime_tf = DateUtil.datetimeFormat.format(updateTime);
           this.updateTime_df = DateUtil.dateFormat.format(updateTime);
       }
   }
   public String getCreateTime_tf() {
       return createTime_tf;
   }
   public void setCreateTime_tf(String createTime_tf) {
       this.createTime_tf = createTime_tf;
   }
   public String getCreateTime_df() {
       return createTime_df;
   }
   public void setCreateTime_df(String createTime_df) {
       this.createTime_df = createTime_df;
   }
   public String getUpdateTime_tf() {
       return updateTime_tf;
   }
   public void setUpdateTime_tf(String updateTime_tf) {
       this.updateTime_tf = updateTime_tf;
   }
   public String getUpdateTime_df() {
       return updateTime_df;
   }
   public void setUpdateTime_df(String updateTime_df) {
       this.updateTime_df = updateTime_df;
   }
   protected Serializable pkVal() {
       return null;
   }
   
   
}

