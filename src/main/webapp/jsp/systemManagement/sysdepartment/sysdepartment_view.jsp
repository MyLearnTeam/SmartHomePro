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
			<input type="hidden" value="${sysdepartment.depart_id }" name="depart_id" />
			<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">

				<TR height=25>
					<td align="right" width="30%"><label ID="lbl_depart_code" style="color: #0066FF">部门编号：</label></Td>
					<td>${sysdepartment.depart_code}</td>
				</Tr>
				<TR height=25>
					<td align="right" width="30%"><label ID="lbl_depart_name" style="color: #0066FF">部门名称：</label></Td>
					<td>${sysdepartment.depart_name}</td>
				</Tr>
				<TR height=150 valign=top>
					<td align="right" width="30%"><label ID="lbl_remark" style="color: #0066FF">说明：</label></Td>
					<td>${sysdepartment.remark}</td>
				</Tr>
			</table>
		</form>
	</div>
</body>
</html>

