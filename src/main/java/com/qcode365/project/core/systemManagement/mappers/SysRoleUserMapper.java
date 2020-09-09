package com.qcode365.project.core.systemManagement.mappers;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import com.qcode365.project.core.systemManagement.Entity.SysRole;
import com.qcode365.project.core.systemManagement.Entity.SysRoleUser;

public interface SysRoleUserMapper extends BaseMapper<SysRoleUser>{
	
	@Insert("insert into sys_role_user(role_id,user_id) values(role_id,user_id)")
	@Options(useGeneratedKeys=true, keyProperty="ruid", keyColumn="ruid")
	boolean saveSysRoleUser(SysRoleUser roleUser);
	
	@Insert("<script>insert into sys_role_user(role_id,user_id) values "
			+ "<foreach collection='list' item='item' separator=',' open='(' close=')'>"
			+ "#{role_id},#{user_id}"
			+ "</foreach></script>")
	boolean saveSysRoleUserList(List<SysRoleUser> list);
	
	@Delete("delete from sys_role_user where user_id=#{user_id}")
	boolean deleteSysRoleUserByUserId(Integer user_id);
	
	@Select("select role.* from sys_role_user roleUser "
			+ "left join sys_role role on role.role_id=roleUser.role_id "
			+ "where roleUser.user_id=#{user_id}")
	List<SysRole> getSysRolesByUserId(Integer user_id);
}
