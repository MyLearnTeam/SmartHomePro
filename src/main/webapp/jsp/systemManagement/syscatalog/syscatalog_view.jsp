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
</head>
<body style="overflow: auto; height: 100%; padding: 0px">
	<div class="wrapper wrapper-content animated fadeIn">
		<form id="inputForm" action="">
			<input type="hidden" value="${syscatalog.catalog_id }" name="id" />
			<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">

				<TR height=25>
					<td align="right" width="30%"><label ID="lbl_parent_id" style="color: #0066FF">上级：</label></Td>
					<td>${parentname}</td>
				</Tr>
				<TR height=25>
					<td align="right" width="30%"><label ID="lbl_catalogcode" style="color: #0066FF">模块编号：</label></Td>
					<td>${sysCatalog.catalogcode}</td>
				</Tr>
				<TR height=25>
					<td align="right" width="30%"><label ID="lbl_catalog" style="color: #0066FF">模块名称：</label></Td>
					<td>${sysCatalog.catalog}</td>
				</Tr>
				<TR height=25>
					<td align="right" width="30%"><label ID="lbl_img" style="color: #0066FF">图标：</label></Td>
					<td><i class='${sysCatalog.img}'></i></td>
				</Tr>
				<TR height=50 valign=top>
					<td align="right" width="30%"><label ID="lbl_url" style="color: #0066FF">访问地址：</label></Td>
					<td>${sysCatalog.url}</td>
				</Tr>
				
				<TR height=25>
					<td align="right" width="30%"><label ID="lbl_winHeight" style="color: #0066FF">窗口宽：</label></Td>
					<td>${sysCatalog.winWidth}</td>
				</Tr>
				<TR height=25>
					<td align="right" width="30%"><label ID="lbl_winHeight" style="color: #0066FF">窗口高：</label></Td>
					<td>${sysCatalog.winHeight}</td>
				</Tr>
				<TR height=25>
					<td align="right" width="30%"><label ID="lbl_is_menu" style="color: #0066FF">类型：</label></Td>
					<td>
						<c:choose>
							<c:when test="${sysCatalog.is_menu==1}">菜单</c:when>
							<c:otherwise>权限</c:otherwise>
						</c:choose>
					</td>
				</Tr>

				<TR height=100 valign=top>
					<td align="right" width="30%"><label ID="lbl_remark" style="color: #0066FF">备注：</label></Td>
					<td>${sysCatalog.remark}</td>
				</Tr>
			</table>
		</form>
	</div>
</body>
<%@ include file="../../../base/baseJs.jsp"%>
</html>
