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
		<form id="inputForm" action="updateSysCatalog">
			<input type="hidden" value="${sysCatalog.catalog_id }" name="id" />
			<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
				<tr height=25>
					<td align="right" width="30%"><label ID="lbl_parent_id" style="color: #0066FF">上级</label></Td>
					<td><input type="text" name="parentname" id="parentname" value="${parentname}" readonly="readonly"><input type="hidden" name="parent_id" id="parent_id"
						value="${sysCatalog.parent_id}"> [<a href="javascript:SearchBack()">选择</a>]</td>
				</Tr>
				<tr height=25>
					<td align="right" width="30%"><label ID="lbl_catalogcode" style="color: #0066FF">模块编号</label></Td>
					<td><input type="text" required="required" id="catalogcode" name="catalogcode" maxlength=50 class="form-control" value="${sysCatalog.catalogcode}"></td>
				</Tr>
				<tr height=25>
					<td align="right" width="30%"><label ID="lbl_catalog" style="color: #0066FF">模块名称</label><font color=red>*</font></Td>
					<td><input type="text" id="catalog" required="required" name="catalog" maxlength=50 class="form-control" value="${sysCatalog.catalog}"></td>
				</Tr>
				<tr height=25>
					<td align="right" width="30%"><label ID="lbl_img" style="color: #0066FF">图标</label></Td>
					<td><input type="text" id="img" name="img" maxlength=150 class="form-control" value="${sysCatalog.img}"></td>
				</Tr>
				<tr height=25>
					<td align="right" width="30%"><label ID="lbl_url" style="color: #0066FF">访问地址</label></Td>
					<td><textarea id="url" name="url" rows="1" class="form-control">${sysCatalog.url}</textarea></td>
				</Tr>
				<tr height=25>
					<td align="right" width="30%"><label ID="lbl_winWidth" style="color: #0066FF">窗口宽度</label></Td>
					<td><input type="text" id="winWidth" name="winWidth" value="900" class="form-control" value="${sysCatalog.winWidth}"></td>
				</Tr>
				<tr height=25>
					<td align="right" width="30%"><label ID="lbl_winHeight" style="color: #0066FF">窗口高度</label></Td>
					<td><input type="text" id="winHeight" name="winHeight" value="600" class="form-control" value="${sysCatalog.winHeight}"></td>
				</Tr>


				<tr height=25>
					<td align="right" width="30%"><label ID="lbl_is_menu" style="color: #0066FF">类型</label><font color=red>*</font></Td>
					<td>
						<input type="radio" name="is_menu" value="1" <c:if test="${sysCatalog.is_menu==1}">checked</c:if>>菜单 
						<input type="radio" name="is_menu" value="2" <c:if test="${sysCatalog.is_menu==2}">checked</c:if>>权限 
					</td>
				</Tr>
				<tr height=25>
					<td align="right" width="30%"><label ID="lbl_sort" style="color: #0066FF">排序</label></Td>
					<td><textarea id="sort" name="sort" rows="2" class="form-control">${sysCatalog.sort}</textarea></td>
				</Tr>

				<tr height=25>
					<td align="right" width="30%"><label ID="lbl_remark" style="color: #0066FF">备注</label></Td>
					<td><textarea id="remark" name="remark" rows="2" class="form-control">${sysCatalog.remark}</textarea></td>
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
<%@ include file="../../../base/baseJs.jsp"%>
<script type="text/javascript">
	function save() {
		// 提交页面数据
		var inputForm = $("#inputForm");
		if (inputForm.valid()) {
			$.post(inputForm.attr("action"), inputForm.serialize(), function(data) {
				if (data.statusCode == "200") {
					top.layer.msg("保存成功", {
						icon : 1,
						time : 1000
					});
					getParentWindow("listCatalog").refreshGrid();
					getParentWindow("listCatalog").refreshTree();
					closeCurrent();
					//top.getActiveTab()[0].contentWindow.refreshGrid();
				} else {
					if (data.errMsg != null) {
						top.layer.msg(data.errMsg, {icon : 2});
					} else {
						top.layer.msg("保存失败", {icon : 2});
					}
				}
			});
		}
	}

	function reset() {
		inputForm.reset();
	}
	//添加
	function SearchBack() {
		openDialog("选择上级", "treeCatalog", "500px", "450px");	
	}
	//设值
	function setValue(parent_id,parentname){
		$("#parent_id").val(parent_id);
		$("#parentname").val(parentname);
	}
</script>
</html>