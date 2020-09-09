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
<style>
.change {
	float: left;
	width: 30% !important;
}
</style>
</head>
<body style="overflow: auto; height: 100%; padding: 0px">
	<div class="wrapper wrapper-content animated fadeIn">
		<form id="inputForm" name="inputForm" action="saveSysDepartment">

			<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
				<tr height=25>
					<td align="right" width="40%"><label ID="lbl_parent_id" style="color: #0066FF">上级</label></Td>
					<td><input type="text" name="parentname" id="parentname" value="无上级" readonly="readonly" style="width:300px"><input type="hidden" name="parent_id" id="parent_id" value="0"> [<a
						href="javascript:SearchBack()">选择</a>]</td>
				</Tr>
				<tr height=25>
					<td align="right" width="30%"><label ID="lbl_depart_code" style="color: #0066FF">部门编号</label></Td>
					<td><input type="text" id="depart_code" name="depart_code" maxlength=20 class="form-control"></td>
				</Tr>
				<tr height=25>
					<td align="right" width="30%"><label ID="lbl_depart_name" style="color: #0066FF">部门名称</label><font color=red>*</font></Td>
					<td><input type="text" id="depart_name" name="depart_name" maxlength=50 class="form-control" required="required"></td>
				</Tr>
				<tr height=25>
					<td align="right" width="30%"><label ID="lbl_remark" style="color: #0066FF">说明</label></Td>
					<td><textarea id="remark" name="remark" rows="5" class="form-control"></textarea></td>
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
			var index = layer.load(2);
			$.post(inputForm.attr("action"), inputForm.serialize(), function(data) {
				layer.close(index);
				if (data.statusCode == "200") {
					top.layer.msg("保存成功", {
						icon : 1,
						time : 1000
					});
					getParentWindow("sysDepartment_list").refreshGrid();
					getParentWindow("sysDepartment_list").refreshTree();
					closeCurrent();
				} else {
					if (data.errMsg != null) {
						top.layer.msg(data.msg, {icon : 2});
					} else {
						top.layer.msg("保存失败", {icon : 2});
					}
				}
			});
		} 
	}
	//选择上级
	function SearchBack() {
		//添加
		openDialog("选择上级", "treeSysDepartment", "500px", "450px");	
	}
	function reset() {
		inputForm.reset();
	}
	//设值
	function setValue(parent_id,parentname){
		$("#parent_id").val(parent_id);
		$("#parentname").val(parentname);
	}
</script>
</html>

