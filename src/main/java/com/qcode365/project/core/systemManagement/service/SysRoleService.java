package com.qcode365.project.core.systemManagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qcode365.project.core.base.BaseService;
import com.qcode365.project.core.systemManagement.Entity.SysRole;
import com.qcode365.project.core.systemManagement.Entity.SysRolePopedom;
import com.qcode365.project.core.systemManagement.mappers.SysRoleMapper;
import com.qcode365.project.core.systemManagement.mappers.SysRolePopedomMapper;

@Transactional
@Service
public class SysRoleService extends BaseService<SysRoleMapper,SysRole>{
	
   @Autowired
   private SysRolePopedomMapper rolePopedomMapper;
   
   //分页查询
   public Map<String, Object> getListByPage(Map<String, Object> map, Map<String, Object> respMap) throws Exception {

		String sqlstr = "select * from";
		String countstr = "select count(0) as row from";

		String sTable = " sys_role ";
		String orderBy = " order by create_time desc";// 在这里修改排序
		String strWhere = " where 1=1 ";
		
		// 获取参数值
		String start = map.get("offset") == null ? "0" : map.get("offset").toString();// 从0页开始
		String length = map.get("limit") == null ? "15" : map.get("limit").toString();// 默认15条每页

		List<Map<String, Object>> list = commService.getList(sqlstr + sTable + strWhere + orderBy + " limit " + start + "," + length);
		int totalCount = commService.getFieldIntValue(countstr + sTable + strWhere);

		respMap.put("rows", list);
		respMap.put("total", totalCount);

		return respMap;
	}

   //给角色设置权限
	public void setRolePopedom(String cid, String roleId) {
		Integer role_id = Integer.parseInt(roleId);
		// 先清除该角色的权限，再重新写入
		rolePopedomMapper.deleteSysRolePopedomByRoleId(Integer.parseInt(roleId));
		List<SysRolePopedom> poList = new ArrayList<>();
		SysRolePopedom rolePo;
		if (cid != "") {
			String[] idstr = cid.split(",");
			for (int i = 0; i < idstr.length; i++) {
				rolePo = new SysRolePopedom();
				rolePo.setRole_id(role_id);
				rolePo.setCatalog_id(Integer.parseInt(idstr[i]));
				poList.add(rolePo);
			}
		}
		rolePopedomMapper.saveSysRoleUserList(poList);
	}

	//给角色设置用户
	public void setRoleUser(String roleId, String ids) throws Exception {
		// 先清除该角色的权限，再重新写入
		commService.ExcuteSQL("delete from sys_role_user where role_id=" + roleId);
		if (ids != "") {
			String[] idstr = ids.split(",");
			for (int i = 0; i < idstr.length; i++) {
				commService.ExcuteSQL("insert into sys_role_user(user_id,role_id) values(" + idstr[i] + "," + roleId + ")");
			}
		}
	}

}


