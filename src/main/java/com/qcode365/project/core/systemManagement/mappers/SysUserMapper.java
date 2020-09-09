package com.qcode365.project.core.systemManagement.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import com.qcode365.project.core.systemManagement.Entity.SysUser;

public interface SysUserMapper extends BaseMapper<SysUser>{
	
	@Select("select * from sys_userinfo where login_name = #{loginName}")
	List<SysUser> getListByLoginName(String loginName);

	@Select("select role.role_name as name,role.role_id as id,EXISTS(select role.role_id,role.role_name as name from sys_role_user where role_id=role.role_id and user_id=#{userId}) as checked from sys_role role")
	List<Map<String, Object>> getSysUserRole(String userId);
	
}
