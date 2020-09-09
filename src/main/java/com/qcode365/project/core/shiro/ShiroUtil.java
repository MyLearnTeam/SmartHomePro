package com.qcode365.project.core.shiro;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qcode365.project.core.systemManagement.Entity.SysUser;

//===================版权说明=======================/
//本快速开发框架系统 版权归 快码猿所有                                                                    /
//本框架源代码由快码猿提供                               /
//注册商标：快码猿 QCode                              / 
//备注时间：2019-10-01                              / 
//===============================================/
public class ShiroUtil {
	private static Logger log =  LoggerFactory.getLogger(ShiroUtil.class);

	/**
	 * 返回是否已经验证登录
	 * 
	 * @return
	 */
	public static final boolean isAuthenticated() {
		return SecurityUtils.getSubject().isAuthenticated();
	}

	/**
	 * 存储内容到session里面
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public static final void setAttribute(Object key, Object value) {
		SecurityUtils.getSubject().getSession().setAttribute(key, value);
	}

	/**
	 * 获取session的内容
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public static final Object getAttribute(Object key) {
		return SecurityUtils.getSubject().getSession().getAttribute(key);
	}

	/**
	 * 获取当前登录的用户
	 * 
	 * @return
	 */
	public static final SysUser getCurrentUser() {
		return (SysUser) getAttribute("sysUser");
	}

	

	/**
	 * 判断是否允许访问内容
	 * 
	 * @param permission
	 *            通配符权限
	 * @return
	 */
	public static final boolean checkPermission(String permission) {
		/*if (ConverUtil.isEmpty(permission)) {
			return false;
		}*/
		/*
		 * 授权
		 */
		try {
			if (SecurityUtils.getSubject().isAuthenticated()) {
				SecurityUtils.getSubject().checkPermission(permission);
				return true;
			}
		} catch (AuthorizationException e) {
			log.error("验证权限"+permission+"出错");
			return false;
		}
		return false;
	}

	/**
	 * 判断是否允许访问内容
	 * 
	 * @param request
	 * 
	 * @param response
	 * 
	 * @return
	 */
	public static final boolean checkPermission(HttpServletRequest request, HttpServletResponse response) {
		/*
		 * 声明变量
		 
		String domain = PermissionDomain.WEB_MENU.getName();
		String action = request.getParameter("action");
		String instance = request.getParameter("instance");
		
		 * 判断参数
		 
		if (ConverUtil.isEmpty(domain) || ConverUtil.isEmpty(action) || ConverUtil.isEmpty(instance)) {
			return false;
		}
		
		 * 匿名访问
		 
		if (action.equals(PermissionDomain.ANON.getName()) && instance.equals(PermissionDomain.ANON.getName())) {
			return true;
		}
		
		
		 * 授权
		 
		String perm = domain + ":" + action + ":" + instance;*/

		return checkPermission("");
	}
	//清除权限 相关的缓存
	public static final void clearAllCache() {
		//添加成功之后 清除缓存
		DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager)SecurityUtils.getSecurityManager();
		CustomRealm shiroRealm = (CustomRealm) securityManager.getRealms().iterator().next();
		//清除权限 相关的缓存
		shiroRealm.clearAllCache();
	} 
	//清除权限 相关的缓存
	public static final void clearLoginCache() {
		//添加成功之后 清除缓存
		DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager)SecurityUtils.getSecurityManager();
		CustomRealm shiroRealm = (CustomRealm) securityManager.getRealms().iterator().next();
		//清除权限 相关的缓存
		shiroRealm.clearAllCachedAuthenticationInfo();
	} 
	//清除权限 相关的缓存
	public static final void clearPermissionCache() {
		//添加成功之后 清除缓存
		DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager)SecurityUtils.getSecurityManager();
		CustomRealm shiroRealm = (CustomRealm) securityManager.getRealms().iterator().next();
		//清除权限 相关的缓存
		shiroRealm.clearAllCachedAuthorizationInfo();
	} 

}
