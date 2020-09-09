package com.qcode365.project.core.systemManagement.mappers;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import com.qcode365.project.core.systemManagement.Entity.SysDepartment;

public interface SysDepartmentMapper extends BaseMapper<SysDepartment>{
	
	/*@Insert("insert into sys_department"
			+ "(depart_code,depart_name,remark,sort,depart_type,"
			+ "parent_id,province_code,city_code,district_code) "
			+ "values(#{depart_code},#{depart_name},#{remark},#{sort},"
			+ "#{depart_type},#{parent_id},#{province_code},#{city_code},#{district_code})")
	@Options(useGeneratedKeys=true, keyProperty="depart_id", keyColumn="depart_id")
	boolean saveSysDepartment(SysDepartment depart);
	
	@Update("update sys_department set "
			+ "depart_code=#{depart_code},depart_name=#{depart_name},remark=#{remark},"
			+ "parent_id=#{parent_id} where depart_id=#{depart_id}")
	boolean updateSysDepartment(Map<String,Object> map);
	
	@Delete("delete from sys_department where depart_id in(#{ids})")
	boolean deleteSysDepartment(Map<String,Object> map);
	
	@Select("select * from sys_department where depart_id=#{depart_id}")
	SysDepartment getSysDepartmentById(Map<String,Object> map);
	
	@Select("select * from sys_department order by sort")
	List<Map<String, Object>> listSysDepartment(Map<String,Object> map);
	
	@Select("${sqlstr}")
	List<SysDepartment> searchSysDepartment(Map<String,Object> map);*/
}
