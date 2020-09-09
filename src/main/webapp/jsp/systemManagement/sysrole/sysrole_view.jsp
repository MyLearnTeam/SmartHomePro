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
		<form id="inputForm" action="">
			<input type="hidden" value="${sysrole.role_id }" name="role_id" />
			<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
				<TR height=25>
					<td align="right" width="30%"><label ID="lbl_role_name" style="color: #0066FF">角色名称：</label></Td>
					<td>${sysrole.role_name}</td>
				</Tr>
				<TR height=25>
					<td align="right" width="30%"><label ID="lbl_role_code" style="color: #0066FF">角色编码：</label></Td>
					<td>${sysrole.role_code}</td>
				</Tr>
				<TR height=150 valign=top>
					<td align="right" width="30%"><label ID="lbl_remark" style="color: #0066FF">备注：</label></Td>
					<td>${sysrole.remark}</td>
				</Tr>
			</table>
		</form>
	</div>
</body>
</html>

