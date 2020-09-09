package com.qcode365.project.core.systemManagement.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.qcode365.project.core.systemManagement.Entity.SysCatalog;
import com.qcode365.project.core.systemManagement.Entity.SysRolePopedom;

public interface SysRolePopedomMapper{
	
	@Insert("insert into sys_role_popedom(role_id,catalog_id) values(role_id,catalog_id)")
	@Options(useGeneratedKeys=true, keyProperty="rpid", keyColumn="rpid")
	boolean saveSysRoleUser(SysRoleUserMapper roleUser);
	
	@Insert("<script>insert into sys_role_popedom(role_id,catalog_id) values "
			+ "<foreach collection='list' item='item' index='index' separator=','>"
			+ "(#{item.role_id},#{item.catalog_id})"
			+ "</foreach></script>")
	boolean saveSysRoleUserList(List<SysRolePopedom> list);
	
	@Delete("delete from sys_role_popedom where role_id=#{role_id}")
	boolean deleteSysRolePopedomByRoleId(Integer role_id);
	
	@Select("select distinct catalog.* from sys_role_popedom rolePopedom "
			+ "left join sys_role role on role.role_id=rolePopedom.role_id "
			+ "left join sys_role_user roleUser on role.role_id=roleUser.role_id "
			+ "left join sys_catalog catalog on catalog.catalog_id=rolePopedom.catalog_id "
			+ "where roleUser.user_id=#{user_id}")
	List<SysCatalog> getSysCatalogssByUserId(Integer user_id);
}
