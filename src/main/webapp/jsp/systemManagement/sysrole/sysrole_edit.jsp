<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title></title>
<%@ include file="../../../base/baseCss.jsp"%>
<%@ include file="../../../base/baseJs.jsp"%>
</head>
<body style="overflow: auto; height: 100%; padding: 0px">
	<div class="wrapper wrapper-content animated fadeIn">
		<form id="inputForm" name="inputForm" action="updateSysRole">
			<input type="hidden" value="${sysrole.role_id }" name="role_id" />
			<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
				<tr height=25>
					<td align="right" width="30%"><label ID="lbl_role_name" style="color: #0066FF">角色名称</label><font color=red>*</font></Td>
					<td><input type="text" id="role_name" name="role_name" maxlength=50 class="form-control" required="required" value="${sysrole.role_name}"></td>
				</Tr>
				<tr height=25>
					<td align="right" width="30%"><label ID="lbl_role_code" style="color: #0066FF">角色编码</label></Td>
					<td><input type="text" id="role_code" name="role_code" maxlength=30 class="form-control" value="${sysrole.role_code}"></td>
				</Tr>
				<tr height=25>
					<td align="right" width="30%"><label ID="lbl_remark" style="color: #0066FF">备注</label></Td>
					<td><textarea id="remark" name="remark" rows="5" class="form-control">${sysrole.remark}</textarea></td>
				</Tr>
			</table>
			<div class="fixed layui-layer-btn">
				<a class="layui-layer-btn0" onclick="save()">保存</a> 
				<a class="layui-layer-btn3" onclick="reset()">重置</a> 
				<a class="layui-layer-btn0" onclick="closeCurrent()">关闭</a>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript">
	function reset() {
		inputForm.reset();
	}
	function save(){
	    if($("#inputForm").valid()){
	        $.post($("#inputForm").attr("action"),$("#inputForm").serialize(),function(data){
	            if(data.statusCode=="200"){
	            	top.layer.msg("保存成功", {icon : 1,time : 1000 });
	                getParentWindow("sysRole_list").refreshGrid();
					closeCurrent();
	            }else{
	                top.layer.msg("保存失败",{icon:2});
	            }
	        });
	    }
	}
</script>
</html>

