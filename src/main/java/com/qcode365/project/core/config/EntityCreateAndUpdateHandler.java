package com.qcode365.project.core.config;

import java.util.Date;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;

import com.qcode365.project.core.shiro.ShiroUtil;
 
//===================版权说明=======================/
//本快速开发框架系统 版权归 快码猿所有                                                                    /
//本框架源代码由快码猿提供，任何单位或个人均可商用和传播                               /
//注册商标：快码猿 QCode                              / 
//备注时间：2019-10-01                              / 
//===============================================/

@Component
public class EntityCreateAndUpdateHandler extends MetaObjectHandler {
 
    /**
     * metaObject是页面传递过来的参数的包装对象，不是从数据库取的持久化对象，因此页面传过来哪些值，metaObject里就有哪些值。
     */
    @Override
    public void insertFill(MetaObject metaObject) {
    	Integer userId = ShiroUtil.getCurrentUser().getUser_id();
    	Date now = new Date();
    	Object createTime = this.getFieldValByName("createTime", metaObject);
    	if(null == createTime){
    		this.setFieldValByName("createTime", now, metaObject);
    	}
    	Object updateTime = this.getFieldValByName("updateTime", metaObject);
    	if(null == updateTime){
    		this.setFieldValByName("updateTime", now, metaObject);
    	}
    	Object createUserId = this.getFieldValByName("createUserId", metaObject);
    	if(null == createUserId){
    		this.setFieldValByName("createUserId", userId, metaObject);
    	}
    	Object updateUserId = this.getFieldValByName("updateUserId", metaObject);
    	if(null == updateUserId){
    		this.setFieldValByName("updateUserId", userId, metaObject);
    	}
    }
    
    @Override
    public void updateFill(MetaObject metaObject) {
    	Integer userId = ShiroUtil.getCurrentUser().getUser_id();
    	Date now = new Date();
    	Object updateTime = this.getFieldValByName("updateTime", metaObject);
    	if(null == updateTime){
    		this.setFieldValByName("updateTime", now, metaObject);
    	}
    	Object updateUserId = this.getFieldValByName("updateUserId", metaObject);
    	if(null == updateUserId){
    		this.setFieldValByName("updateUserId", userId, metaObject);
    	}
    }
}
