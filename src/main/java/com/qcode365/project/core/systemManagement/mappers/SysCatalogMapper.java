package com.qcode365.project.core.systemManagement.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.qcode365.project.core.systemManagement.Entity.SysCatalog;

public interface SysCatalogMapper {
	
	@Insert("insert into sys_catalog "
			+ "(parent_id,catalogcode,catalog,url,remark,img,winWidth,winHeight,is_menu,sort) "
			+ "values(#{parent_id},#{catalogcode},#{catalog},#{url},#{remark},#{img},"
			+ "#{winWidth},#{winHeight},#{is_menu},#{sort})")
	@Options(useGeneratedKeys=true, keyProperty="catalog_id", keyColumn="catalog_id")
	boolean saveSysCatalog(SysCatalog sysCatalog);
	
	@Update("update sys_catalog set "
		+"parent_id=#{parent_id},catalogcode=#{catalogcode},catalog=#{catalog},"
		+ "url=#{url},sort=#{sort},remark=#{remark},img=#{img},is_menu=#{is_menu},"
		+ "winWidth=#{winWidth},winHeight=#{winHeight}"
		+"where catalog_id=#{id}")
	boolean updateSysCatalog(Map<String,Object> map);
	
	@Delete("delete from sys_catalog where catalog_id in(#{ids})")
	boolean deleteSysCatalog(Map<String,Object> map);
	
	@Select("select * from sys_catalog where catalog_id=#{id}")
	SysCatalog getSysCatalogById(Map<String,Object> map);
	
	@Select("<script>select * from sys_catalog where 1=1 "
			+ "<if test=\"pid != -1 and pid != ''\"> and parent_id=#{pid} || catalog_id=#{pid} </if> "
			+ "order by sort limit #{start},#{length}</script>")
	List<SysCatalog> listSysCatalog(Map<String,Object> map);

	@Select("select * from sys_catalog")
	List<SysCatalog> getAllSyscatalogs();
	
}
