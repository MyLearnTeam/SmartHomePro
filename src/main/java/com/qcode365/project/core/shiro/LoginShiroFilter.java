package com.qcode365.project.core.shiro;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;
import com.qcode365.project.core.config.BaseConfig;

//===================版权说明=======================/
//本快速开发框架系统 版权归 快码猿所有                                                                    /
//本框架源代码由快码猿提供                               /
//注册商标：快码猿 QCode                              / 
//备注时间：2019-10-01                              / 
//===============================================/
public class LoginShiroFilter extends FormAuthenticationFilter{
	private Logger log =  LoggerFactory.getLogger(this.getClass());

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		if (isLoginRequest(request, response)) {
			return true;
        }
		log.info("您的登录信息已失效,请重新登录!");
		
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String header = httpServletRequest.getHeader("X-Requested-With");
        boolean isAjax = "XMLHttpRequest".equals(header);
        
        
        //如果是ajax返回指定格式数据
        if (isAjax) {
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json");
            //返回禁止访问json字符串
            Map<String, String> map = new HashMap<String, String>();
            map.put("statusCode","405");
            map.put("msg","您的登录信息已失效,请重新登录!");
            httpServletResponse.getWriter().write(JSONObject.fromObject(map).toString());
        } else {//如果是普通请求进行重定向
        	
        	log.info("请求地址:"+httpServletRequest.getRequestURI());
        	
        	//此处可以根据路径判断跳转到对应的登录页面, 
        	//例如 1:web端/web/时跳转到web/login.html
        	// 2:wap手机端/wap/时跳转到wap/login.html
        	String redictLoginUrl = BaseConfig.pcLoginUrl;
        	
        	WebUtils.issueRedirect(request, response, redictLoginUrl);
            //super.onAccessDenied(httpServletRequest, httpServletResponse);
        }
		return false;
	}
}
