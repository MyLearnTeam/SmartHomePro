package com.qcode365.project.core.systemManagement.service;


import java.util.List;
import java.util.Map;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.qcode365.project.core.Exception.BussinessException;
import com.qcode365.project.core.base.BaseService;
import com.qcode365.project.core.base.CommService;
import com.qcode365.project.core.systemManagement.Entity.SysUser;
import com.qcode365.project.core.systemManagement.mappers.SysRoleUserMapper;
import com.qcode365.project.core.systemManagement.mappers.SysUserMapper;

//===================版权说明=======================/
//本快速开发框架系统 版权归 快码猿所有                                                                    /
//本框架源代码由快码猿提供                               /
//注册商标：快码猿 QCode                              / 
//备注时间：2019-10-01                              / 
//===============================================/

@Service
@Transactional
public class SysUserService extends BaseService<SysUserMapper,SysUser>{
	
	private Logger log =  LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SysUserMapper userMapper;
	@Autowired
	private SysRoleUserMapper roleUserMapper;
	@Autowired
	private CommService commService;
	
	//用户登录
	public SysUser userLogin(Map<String, String> map) throws BussinessException {
		
		String verify_code = map.get("verify_code");
        String logname = map.get("logname");
        String password = map.get("password");
        String session_code = map.get("session_code");
        log.debug(verify_code+"===================="+session_code);
        
		if(!verify_code.equals(session_code)){
            //throw new BussinessException("400", "验证码不正确！");
        }
		//添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                map.get("username").toString(),
                map.get("password").toString());
        //进行验证，这里可以捕获异常，然后返回对应信息
        subject.login(usernamePasswordToken);
		SysUser user = getUserByLoginName(logname);
		if(!user.getPassword().equals(password)) {
			throw new BussinessException("400", "用户名或密码错误！");
		}
		return user;
	}

	public SysUser getUserByLoginName(String loginName) throws BussinessException {
		if(null == loginName || "".equals(loginName)) {
			 throw new BussinessException("400", "登录名不能为空！");
		}
		List<SysUser> userList = userMapper.getListByLoginName(loginName);
		if(null==userList || userList.size()==0) {
			throw new BussinessException("400", "找不到该用户！");
		}
		if(null==userList || userList.size()>1) {
			throw new BussinessException("400", "该用户名不唯一！");
		}
		return userList.get(0);
	}
	
	public Map<String, Object> getListByPage(Map<String, Object> map, Map<String, Object> respMap) throws Exception {

		String sqlstr = "select * from";
		String countstr = "select count(0) as row from";

		String sTable = " sys_userinfo user "
				+ "left join sys_department depart on user.depart_id=depart.depart_id ";
		String orderBy = " order by user.create_time desc";// 在这里修改排序
		String strWhere = " where 1=1 ";
		
		// 获取参数值
		String start = map.get("offset") == null ? "0" : map.get("offset").toString();// 从0页开始
		String length = map.get("limit") == null ? "15" : map.get("limit").toString();// 默认15条每页
		String departid = map.get("departid") == null ? "" : map.get("departid").toString();

		// todo 组装查询条件==================================
		if (!"".equals(departid)) {
			strWhere += " and user.depart_id=" + departid;
		}

		// =========================

		List<Map<String, Object>> list = commService.getList(sqlstr + sTable + strWhere + orderBy + " limit " + start + "," + length);
		int totalCount = commService.getFieldIntValue(countstr + sTable + strWhere);

		respMap.put("rows", list);
		respMap.put("total", totalCount);

		return respMap;
	}

	//获取用户角色
	public List<Map<String, Object>> getSysUserRole(String userId) {
		return userMapper.getSysUserRole(userId);
	}

	//设置角色
	public void setRoleForUser(String userId, String roleIds) throws Exception {
		//先清除该角色的权限，再重新写入
        roleUserMapper.deleteSysRoleUserByUserId(Integer.parseInt(userId));
        if (null != roleIds && !"".equals(roleIds)){
            String[] idstr = roleIds.split(",");
            for (int i=0;i<idstr.length;i++){
                commService.ExcuteSQL("insert into sys_role_user(user_id,role_id) values(" +userId +","+idstr[i]+")");
            }
        }
	}
   
}
