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
			<input type="hidden" value="${sysuser.user_id }" name="user_id" />
			<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
				<TR height=25>
					<td align="right" width="25%"><label ID="lbl_login_name" style="color: #0066FF">登录名：</label></Td>
					<td>${sysuser.login_name}</td>
				</Tr>

				<TR height=25>
					<td align="right" width="25%"><label ID="lbl_truename" style="color: #0066FF">真实姓名：</label></Td>
					<td>${sysuser.truename}</td>
				</Tr>
				<TR height=25>
					<td align="right" width="25%"><label ID="lbl_usercode" style="color: #0066FF">用户代码：</label></Td>
					<td>${sysuser.usercode}</td>
				</Tr>

				<TR height=25>
					<td align="right" width="25%"><label ID="lbl_depart_id" style="color: #0066FF"> 机构部门：</label></Td>
					<td>${sysuser.depart_cn}</td>
				</Tr>
				<TR height=25>
					<td align="right" width="25%"><label ID="lbl_photo" style="color: #0066FF">头像地址：</label></Td>
					<td>${sysuser.photo}</td>
				</Tr>
				<TR height=25>
					<td align="right" width="25%"><label ID="lbl_mobile" style="color: #0066FF">手机：</label></Td>
					<td>${sysuser.mobile}</td>
				</Tr>
				<TR height=25>
					<td align="right" width="25%"><label ID="lbl_phone" style="color: #0066FF">座机固话：</label></Td>
					<td>${sysuser.phone}</td>
				</Tr>
				<TR height=25>
					<td align="right" width="25%"><label ID="lbl_regdate" style="color: #0066FF">注册日期：</label></Td>
					<td>${sysuser.createTime_tf}</td>
				</Tr>
				<TR height=25>
					<td align="right" width="25%"><label ID="lbl_job_title" style="color: #0066FF">职位：</label></Td>
					<td>${sysuser.job_title}</td>
				</Tr>
				<TR height=25>
					<td align="right" width="25%"><label ID="lbl_degree" style="color: #0066FF">职称：</label></Td>
					<td>${sysuser.degree}</td>
				</Tr>
				<TR height=25>
					<td align="right" width="25%"><label ID="lbl_email" style="color: #0066FF">电子邮箱：</label></Td>
					<td>${sysuser.email}</td>
				</Tr>
				<TR height=150 valign=top>
					<td align="right" width="25%"><label ID="lbl_remark" style="color: #0066FF">备注：</label></Td>
					<td>${sysuser.remark}</td>
				</Tr>
			</table>
		</form>
	</div>
</body>
</html>

