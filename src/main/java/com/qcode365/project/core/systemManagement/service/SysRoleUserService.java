package com.qcode365.project.core.systemManagement.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.qcode365.project.core.systemManagement.Entity.SysRole;
import com.qcode365.project.core.systemManagement.Entity.SysRoleUser;
import com.qcode365.project.core.systemManagement.mappers.SysRoleUserMapper;

@Service
public class SysRoleUserService extends ServiceImpl<SysRoleUserMapper,SysRoleUser>{
	@Autowired
	private SysRoleUserMapper roleUserMapper;

	@Cacheable(cacheNames = "userCache",key="'getRolesByUserId_'+#user_id")
	public Set<String> getRolesByUserId(Integer user_id) {
		List<SysRole> roleList = roleUserMapper.getSysRolesByUserId(user_id);
		Set<String> result = new HashSet<>();
		for (SysRole role : roleList) {
			if (null != role.getRole_name() && !"".equals(role.getRole_name())) {
				result.add(role.getRole_name());
			}
		}
		return result;
	}

}
