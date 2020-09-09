package com.qcode365.project.core.systemManagement.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.qcode365.project.core.base.CommService;
import com.qcode365.project.core.systemManagement.Entity.SysCatalog;
import com.qcode365.project.core.systemManagement.mappers.SysCatalogMapper;
import com.qcode365.project.core.util.BeanToMapUtil;

//===================版权说明=======================/
//本快速开发框架系统 版权归 快码猿所有                                                                    /
//本框架源代码由快码猿提供                               /
//注册商标：快码猿 QCode                              / 
//备注时间：2019-10-01                              / 
//===============================================/

@EnableTransactionManagement(proxyTargetClass = true)
@Service("sysCatalogService")
public class SysCatalogService {

	@Resource
	protected JdbcTemplate jdbcTemplate;
	@Resource
	private SysCatalogMapper sysCatalogMapper;
	@Resource
	private CommService commService;
	
	
	// 根据用户.权限获取左侧菜单栏
	//@Cacheable(cacheNames = "userCache",key="'getCatalogByUserId_new_'+#userId")
	public List<SysCatalog> getCatalogByUserId(Integer userId) throws Exception {
		
		String sql = "select distinct catalog.* from sys_catalog catalog "
				+ "left join sys_role_popedom p on catalog.catalog_id=p.catalog_id "
				+ "left join sys_role_user ru on ru.role_id=p.role_id "
				+ "where ru.user_id="+userId+" and is_menu=1 order by catalog.sort asc";
		
		List<SysCatalog> list = commService.getList(sql, SysCatalog.class);
		List<SysCatalog> resultList = new ArrayList<>();
		//递归每一个一级栏目下的所有子栏目
		for (SysCatalog catalog : list) {
			if(null != catalog && catalog.getParent_id().intValue() == 0) {				
				list.set(list.indexOf(catalog), null);
				setCatalogTree(catalog, list);
				resultList.add(catalog);
			}
		}
		return resultList;
	}
	/**
	 * @param 当前循环项
	 * @param list 源数据
	 */
	public void setCatalogTree(SysCatalog catalog,List<SysCatalog> list) {
		List<SysCatalog> childList = new ArrayList<>();
		for (SysCatalog child : list) {
			if(null == child || null == catalog) {
				continue;
			}
			//递归所有子元素
			if(child.getParent_id().intValue() == catalog.getCatalog_id().intValue()) {
				list.set(list.indexOf(child), null);
				setCatalogTree(child, list);
				childList.add(child);
			}
		}
		catalog.setChildList(childList);
	}
	//------------------------------------------------
	// 根据用户.权限获取左侧菜单栏(不用)
	//@Cacheable(cacheNames = "userCache",key="'getCatalogByUserId_new_'+#userId")
	public String getCatalogByUserId_new(Integer userId) throws Exception {

		String parentid = "0";
		String catalogStr = "";
		String sql = "select * from sys_catalog where is_menu=1 ";
		sql += " and catalog_id in (select catalog_id from sys_role_popedom p left join sys_role_user u on p.role_id=u.role_id where u.user_id=" + userId + ") ";
		sql += " order by sort asc";

		/*List<Map<String, Object>> bigcataloglist = commService.getList(
				"select distinct a.catalog_id from sys_catalog a,sys_role_popedom b,sys_role_user c where a.catalog_id=b.catalog_id and b.role_id=c.role_id and a.parent_id=0 and c.user_id="
						+ userId);*/
		/*if (bigcataloglist.size() <= 1) {// 只有一个1级栏目时
			parentid = bigcataloglist.size() == 0 ? "0" : bigcataloglist.get(0).get("catalog_id").toString();
			sql = "select * from sys_catalog where is_menu=1 and catalog_id!=" + parentid;
			sql += " and catalog_id in (select catalog_id from sys_role_popedom p left join sys_role_user u on p.role_id=u.role_id where u.user_id=" + userId + ") ";
			sql += " order by sort asc";
		}*/

		List<SysCatalog> list = commService.getList(sql, SysCatalog.class);

		for (SysCatalog catalog : list) {
			// 一级菜单
			if (!catalog.getParent_id().toString().equals(parentid)) {
				continue;
			}
			//#39aef5
			catalogStr += "<li class='J_menuItem_li' id='li-" + catalog.getCatalog_id() + "' "
					+ "style='border-top:solid 1px;border-color:#39aef5;'  title='" + catalog.getCatalog()+ "'>";

			if (catalog.getUrl()==null || catalog.getUrl().toString().equals("") ) {
				catalogStr += "  <a class='J_menuItem_a' href='javascript:void(0);'>";
			} else {
				catalogStr += "  <a class='J_menuItem_a' href='javascript:void(0);' tag='" + catalog.getUrl() + "'>";
			}

			catalogStr += "    <i class='" + catalog.getImg() + "'></i>";
			catalogStr += "    <span class='nav-label'>" + catalog.getCatalog() + "</span>";
			catalogStr += "    <span class='fa arrow'></span>";
			catalogStr += "  </a>";
			catalogStr += "  <ul class='nav nav-second-level'>";

			// 二级菜单
			for (SysCatalog child : list) {

				if (!child.getParent_id().equals(catalog.getCatalog_id())) {
					continue;
				}

				boolean hadchild3 = false;// 是否有三级菜单
				for (SysCatalog child3 : list) {
					if (child3.getParent_id().equals(child.getCatalog_id())) {
						hadchild3 = true;
						break;
					}
				}

				// region 有三级菜单
				if (!hadchild3) {
					if (child.getUrl().indexOf("?") > -1){// 路径里带参数border-top: solid 1px;border-color: #4682b4;
						catalogStr += "<li class='J_menuItem_li'><a id='mnu-" + child.getCatalog_id() + "' class='J_menuItem' href='" + child.getUrl() + "&catalog_id="
								+ child.getCatalog_id() + "'>" + child.getCatalog() + "</a></li>";
					} else {
						catalogStr += "<li class='J_menuItem_li'><a id='mnu-" + child.getCatalog_id() + "' class='J_menuItem' href='" + child.getUrl() + "?catalog_id="
								+ child.getCatalog_id() + "'>" + child.getCatalog() + "</a></li>";
					}
					continue;
				}

				catalogStr += "<li class='J_menuItem_li' style='border-top:solid 1px;border-color:#39aef5;'><a href=\"#\">" + child.getCatalog() + "<span class=\"fa arrow\"></span></a><ul class=\"nav nav-third-level\">";

				for (SysCatalog child3 : list) {

					if (!child3.getParent_id().equals(child.getCatalog_id())) {
						continue;
					}
					if (child3.getUrl().indexOf("?") > -1){// 路径里带参数
						catalogStr += "<li class='J_menuItem_li'><a id='mnu-" + child3.getCatalog_id() + "' class='J_menuItem' href='" + child3.getUrl() + "&catalog_id="
								+ child3.getCatalog_id() + "'>" + child3.getCatalog() + "</a></li>";
					}else {
						catalogStr += "<li class='J_menuItem_li'><a id='mnu-" + child3.getCatalog_id() + "' class='J_menuItem' href='" + child3.getUrl() + "?catalog_id="
								+ child3.getCatalog_id() + "'>" + child3.getCatalog() + "</a></li>";
					}
				}
				catalogStr += "</ul></li>";
			}
			catalogStr += "</ul></li>";
		}
		return catalogStr;
	}

	// 获取模块列表数据
	public List<SysCatalog> getSysCatalogList(Map<String, Object> map) throws Exception {
		return (List<SysCatalog>) sysCatalogMapper.listSysCatalog(map);
	}

	// 获取模块列表记录数量
	public int getSysCatalogListNum(Map<String, Object> map) throws Exception {
		String pid = map.get("pid").toString();
		String sql = "select count(catalog_id) from sys_catalog where parent_id=" + pid + " || catalog_id=" + pid;

		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	// 获取树数据
	public List<Map<String, Object>> getCatalogTreeData() {
		String sql = "select catalog_id as id,parent_id as pId,catalog as name from sys_catalog order by sort";
		return jdbcTemplate.queryForList(sql);
	}

	// 删除记录
	public void deleteSysCatalog(Map<String, Object> map) throws Exception {
		// sysCatalogDao.delete("deleteSysCatalog", map);
		commService.ExcuteSQL("delete from sys_role_popedom where catalog_id in(" + map.get("ids") + ")");
		commService.ExcuteSQL("delete from sys_catalog where catalog_id in(" + map.get("ids") + ")");
	}

	// 保存记录
	@SuppressWarnings("unused")
	public void saveSysCatalog(Map<String, Object> map) throws Exception {
		Integer sort = commService.getMaxSort("sys_catalog");// (Integer) sysCatalogDao.findForObject("getMaxSort", map);
		if (null == sort) {
			sort = 1;
		}
		map.put("sort", sort);
		SysCatalog catalog = BeanToMapUtil.convertMap2Bean(map, SysCatalog.class);
		boolean flag = sysCatalogMapper.saveSysCatalog(catalog);
	}

	// 通过id获取
	public SysCatalog getSysCatalogById(Map<String, Object> map) throws Exception {
		return (SysCatalog) sysCatalogMapper.getSysCatalogById(map);
	}

	// 更新数据
	public void updateSysCatalog(Map<String, Object> map) throws Exception {
		boolean flag = sysCatalogMapper.updateSysCatalog(map);
		System.out.println(flag);
	}

	// 排序
	public void SysDepartmentSort(Map<String, Object> map) throws Exception {
		String idstr = map.get("ids").toString();
		if (idstr != null) {
			String[] ids = idstr.split(",");
			for (int k = 0; k < ids.length; k++) {
				commService.ExcuteSQL("update sys_catalog set sort=" + (k + 1) + " where catalog_id=" + ids[k]);
			}
		}
	}

}
