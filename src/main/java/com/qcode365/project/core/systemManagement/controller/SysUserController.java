package com.qcode365.project.core.systemManagement.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.qcode365.project.core.base.BaseController;
import com.qcode365.project.core.shiro.ShiroUtil;
import com.qcode365.project.core.systemManagement.Entity.SysDepartment;
import com.qcode365.project.core.systemManagement.Entity.SysUser;
import com.qcode365.project.core.systemManagement.Entity.SysCatalog;
import com.qcode365.project.core.systemManagement.service.SysCatalogService;
import com.qcode365.project.core.systemManagement.service.SysDepartmentService;
import com.qcode365.project.core.systemManagement.service.SysUserService;
import com.qcode365.project.core.util.MD5;

//===================版权说明=======================/
//本快速开发框架系统 版权归 快码猿所有                                                                    /
//本框架源代码由快码猿提供                               /
//注册商标：快码猿 QCode                              / 
//备注时间：2019-10-01                              / 
//===============================================/

@Controller
public class SysUserController extends BaseController {
	
	private Logger log =  LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SysUserService sysuserService;
	@Autowired
	private SysDepartmentService departService;
	@Autowired
	private SysCatalogService sysCatalogService;

	// 用户登录
	@RequestMapping(value = "/sysLogin")
	@ResponseBody
	public Map<String, Object> sysLogin(HttpServletRequest request, @RequestBody Map<String, String> map) {
		try {
			HttpSession session = request.getSession();
			String session_code = null == session.getAttribute("securityCode")?"":session.getAttribute("securityCode").toString();
			
			String verify_code = map.get("verify_code");
	        String logname = map.get("logname");
	        String password = map.get("password");
	        log.debug(verify_code+"===================="+session_code);
	        
			if(!verify_code.equals(session_code)){
	            //throw new BussinessException("400", "验证码不正确！");
	        }
			//添加用户认证信息
	        Subject subject = SecurityUtils.getSubject();
	        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(logname,password);
	        //进行验证，这里可以捕获异常，然后返回对应信息
	        subject.login(usernamePasswordToken);
	        
	        SysUser user = (SysUser)subject.getPrincipal();
			//-------------获取IP
	        String ip="";
		    try {
			    if (request.getHeader("x-forwarded-for") == null) {  
			       ip=request.getRemoteAddr();  
			    }  
			    else{
			      ip=request.getHeader("x-forwarded-for");  
			    }
		    }
		    catch(Exception e) {}
		    user.setIp(ip);

	        ShiroUtil.setAttribute("sysUser", user);
	        session.setAttribute("yhwl_username",user.getTruename());
	        
			List<SysCatalog> list = sysCatalogService.getCatalogByUserId(ShiroUtil.getCurrentUser().getUser_id());
			session.setAttribute("catalogList", list);
 

		}catch (UnknownAccountException e) {
	    	log.info("==========用户名不存在=======");
	    	return buildNewResultMap("401", e.getMessage());
	    }catch (DisabledAccountException e) {
	    	log.info("==========您的账户已经被冻结=======");
	    	return buildNewResultMap("401", "您的账户已经被冻结");
	    } catch (IncorrectCredentialsException e) {
	    	log.info("==========密码错误=======");
	    	return buildNewResultMap("401", "密码错误");
	    } catch (ExcessiveAttemptsException e) {
	    	log.info("==========您错误的次数太多了=======");
	    	return buildNewResultMap("401", "您错误的次数太多了");
	    }catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString(), e);
			return buildNewFailedResultMap();
		}
		return buildNewSuccessResultMap();
	}
	@RequestMapping("/logout")
	public String logOut(HttpServletRequest request) {
		try {
			System.out.println("logout");
			ShiroUtil.clearAllCache();
			Subject subject = SecurityUtils.getSubject();
			subject.logout();
			request.getSession().invalidate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return "login";
	}
	// 进入添加页面
    @RequestMapping(value = "/sysUser_new")
    public ModelAndView sysUser_new(HttpServletRequest request) {
    	mv.clear();
        try {
        	mv.setViewName("systemManagement/sysuser/sysuser_new");
        } catch (Exception e) {
            log.error(e.toString(), e);
            e.printStackTrace();
            buildErrorPage(mv, e.getMessage());
        }
        return mv;
    }

    // 通过MAP保存新记录
    @RequestMapping(value = "/saveSysUser")
    @ResponseBody
    public Map<String, Object> saveSysUser(HttpServletRequest request,SysUser user) {
        Map<String, Object> map = new HashMap<>();
        try {
        	user.setPassword(MD5.md5(user.getPassword()));
        	user.setStatus(1);
            sysuserService.insert(user);

            buildSuccessResultMap(map);
        } catch (Exception e) {
            log.error(e.toString(), e);
            e.printStackTrace();
            buildFailedResultMap(map);
        }
        return map;
    }

    // 进入修改页面
    @RequestMapping(value = "/sysUser_edit")
    public ModelAndView sysUser_edit(HttpServletRequest request) {
    	mv.clear();
        try {
        	String id = request.getParameter("id");
        	
            SysUser sysuser = sysuserService.selectById(id);
            SysDepartment depart = departService.selectById(sysuser.getDepart_id());
            
            sysuser.setDepart_cn(depart.getDepart_name());
            mv.addObject("sysuser", sysuser);
            mv.addObject("departname",depart.getDepart_name());
            mv.setViewName("systemManagement/sysuser/sysuser_edit");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString(), e);
            buildErrorPage(mv, e.getMessage());
        }
        return mv;
    }

    // 保存修改数据
    @RequestMapping(value = "/updateSysUser")
    @ResponseBody
    public Map<String, Object> updateSysUser(HttpServletRequest request, SysUser user) {
        Map<String, Object> map = new HashMap<>();
        try {
        	SysUser dbUser = sysuserService.selectById(user.getUser_id());
        	if(null==dbUser.getPassword() || !dbUser.getPassword().equals(user.getPassword())) {
        		user.setPassword(MD5.md5(user.getPassword()));
        	}
            sysuserService.updateById(user);
            buildSuccessResultMap(map);
        } catch (Exception e) {
            log.error(e.toString(), e);
            e.printStackTrace();
            map.put("statusCode",400);
            buildFailedResultMap(map);
        }
        return map;
    }

    // 删除数据
    @RequestMapping(value = "/deleteSysUser")
    @ResponseBody
    public Map<String, Object> deleteSysUser(HttpServletRequest request,@RequestBody Map<String,String> reqmap) {
        Map<String, Object> map = new HashMap<>();
        try {
        	String ids = reqmap.get("ids");
            sysuserService.deleteBatchIds(ids);
            buildSuccessResultMap(map);
        } catch (Exception e) {
            log.error(e.toString(), e);
            e.printStackTrace();
            map.put("statusCode",400);
            buildFailedResultMap(map);
        }
        return map;
    }

    // 进入查看页面
    @RequestMapping(value = "/sysUser_view")
    public ModelAndView sysUser_view(HttpServletRequest request) {
    	mv.clear();
        try {
        	String id = request.getParameter("id");
        	
            SysUser sysuser = sysuserService.selectById(id);
            SysDepartment depart = departService.selectById(sysuser.getDepart_id());
            
            sysuser.setDepart_cn(depart.getDepart_name());
            
            mv.addObject("sysuser", sysuser);
            mv.setViewName("systemManagement/sysuser/sysuser_view");
        } catch (Exception e) {
            log.error(e.toString(), e);
            e.printStackTrace();
            buildErrorPage(mv, e.getMessage());
        }
        return mv;
    }

    // 进入列表页面
    @RequestMapping(value = "/sysUser_list")
    public ModelAndView sysUser_list(HttpServletRequest request) {
    	mv.clear();
        try {

        } catch (Exception e) {
            log.error(e.toString(), e);
            e.printStackTrace();
        }
        mv.setViewName("systemManagement/sysuser/sysuser_list");
        return mv;
    }

    // 获取列表数据
    @RequestMapping(value = "/getSysUserList")
    @ResponseBody
    public Map<String, Object> getSysUserList(HttpServletRequest request,@RequestParam Map<String, Object> reqMap) throws Exception {
		Map<String, Object> respMap = new HashMap<>();
		try {
			respMap = sysuserService.getListByPage(reqMap,respMap);
			buildSuccessResultMap(respMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString(), e);
			buildFailedResultMap(respMap);
		}
		return respMap;
	}
    
 // 进入设置用户角色页面
    @RequestMapping(value = "/sysUser_setRole")
    public ModelAndView setUserRole(HttpServletRequest request) {
        try {
            String userId = request.getParameter("userid");
            mv.addObject("userid",userId);
        } catch (Exception e) {
            log.error(e.toString(), e);
            e.printStackTrace();
        }
        mv.setViewName("systemManagement/sysuser/setuserrole");
        return mv;
    }
    // 获取用户角色
    @RequestMapping(value = "/getSysUserRole")
    @ResponseBody
    public List<Map<String,Object>> getSysUserRole(HttpServletRequest request,@RequestParam String userId) throws Exception {
    	List<Map<String,Object>> list = sysuserService.getSysUserRole(userId);
    	return list;
    }
    // 保存设置用户角色
    @RequestMapping(value = "/setUserRole")
    @ResponseBody
    public Map<String, Object> setUserRoleOK(HttpServletRequest request) {
    	Map<String ,Object> map = new HashMap<>();
        try {
        	String userId = request.getParameter("userId");
        	String roleIds = request.getParameter("roleIds");
        	sysuserService.setRoleForUser(userId,roleIds);
            buildSuccessResultMap(map);
        } catch (Exception e) {
            log.error(e.toString(), e);
            e.printStackTrace();
            buildFailedResultMap(map);
        }
        return map;
    }
    
}
