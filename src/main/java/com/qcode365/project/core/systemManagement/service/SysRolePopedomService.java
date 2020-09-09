package com.qcode365.project.core.systemManagement.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcode365.project.core.systemManagement.Entity.SysCatalog;
import com.qcode365.project.core.systemManagement.mappers.SysRolePopedomMapper;

//===================??????=======================/
//?????????????? ????? ?????????                                                                    /
//??????????????????                               /
//??????????? QCode                              / 
//??????2019-10-01                              / 
//===============================================/

@Service
public class SysRolePopedomService {
	
	@Autowired
    private SysRolePopedomMapper rolePopedomMapper;

	//@Cacheable(cacheNames = "userCache",key="'getPopedomsByUserId_'+#user_id")
	public Set<String> getPopedomsByUserId(Integer user_id) {
		List<SysCatalog> catalogList = rolePopedomMapper.getSysCatalogssByUserId(user_id);
		Set<String> result = new HashSet<>();
		for(SysCatalog sysCatalog:catalogList) {
			if(null != sysCatalog.getUrl() && !"".equals(sysCatalog.getUrl())) {				
				result.add(sysCatalog.getUrl());
			}
		}
		return result;
	}
}
