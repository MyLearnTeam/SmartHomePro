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
<script src="${pageContext.request.contextPath}/resources/plugins/jquery/jquery-md5.js"></script>
</head>
<body style="overflow: auto; height: 100%; padding: 0px">
	<div class="wrapper wrapper-content animated fadeIn">
		<form id="inputForm" name="inputForm" action="updateSysUser">
			<input type="hidden" value="${sysuser.user_id}" name="user_id" />
			<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
				<tr height=25>
					<td align="right" width="25%"><label ID="lbl_login_name" style="color: #0066FF">登录名</label><font color=red>*</font></Td>
					<td><input type="text" id="login_name" name="login_name" maxlength=20 class="form-control" required="required" value="${sysuser.login_name}"></td>
				</Tr>
				<tr height=25>
					<td align="right" width="25%"><label ID="lbl_password" style="color: #0066FF">密码</label><font color=red>*</font></Td>
					<td><input type="password" id="password" title="密码长度至少8位，并且必须由 数字、字母、特殊符号中其二进行组合" name="password" maxlength=128 class="form-control"></td>
				</Tr>
				<tr height=25>
					<td align="right" width="25%"><label ID="lbl_truename" style="color: #0066FF">真实姓名</label><font color=red>*</font></Td>
					<td><input type="text" id="truename" name="truename" maxlength=30 class="form-control" required="required" value="${sysuser.truename}"></td>
				</Tr>
				<tr height=25>
					<td align="right" width="25%"><label ID="lbl_usercode" style="color: #0066FF">用户代码</label></Td>
					<td><input type="text" id="usercode" name="usercode" maxlength=20 class="form-control" value="${sysuser.usercode}"></td>
				</Tr>
				<tr height=25>
					<td align="right" width="25%"><label ID="lbl_producttype_id" style="color: #0066FF">所属机构部门</label></Td>
					<td><input type="text" name="depart_name" id="depart_name" value="${departname}" readonly="readonly"><input type="hidden" name="depart_id" id="depart_id"
						value="${sysuser.depart_id}"> [<a href="javascript:void(0)" onclick="selectDepart()">选择</a>]</td>
				</Tr>
				<tr height=25>
					<td align="right" width="25%"><label ID="lbl_photo" style="color: #0066FF">头像地址</label></Td>
					<td><input type="text" id="photo" name="photo" maxlength=128 class="form-control" value="${sysuser.photo}"></td>
				</Tr>
				<tr height=25>
					<td align="right" width="25%"><label ID="lbl_mobile" style="color: #0066FF">手机</label></Td>
					<td><input type="text" id="mobile" name="mobile" maxlength=20 class="form-control" value="${sysuser.mobile}"></td>
				</Tr>
				<tr height=25>
					<td align="right" width="25%"><label ID="lbl_phone" style="color: #0066FF">座机固话</label></Td>
					<td><input type="text" id="phone" name="phone" maxlength=50 class="form-control" value="${sysuser.phone}"></td>
				</Tr>
				<tr height=25>
					<td align="right" width="25%"><label ID="lbl_job_title" style="color: #0066FF">职位</label></Td>
					<td><input type="text" id="job_title" name="job_title" maxlength=45 class="form-control" value="${sysuser.job_title}"></td>
				</Tr>
				<tr height=25>
					<td align="right" width="25%"><label ID="lbl_degree" style="color: #0066FF">职称</label></Td>
					<td><input type="text" id="degree" name="degree" maxlength=45 class="form-control" value="${sysuser.degree}"></td>
				</Tr>
				<tr height=25>
					<td align="right" width="25%"><label ID="lbl_email" style="color: #0066FF">电子邮箱</label></Td>
					<td><input type="text" id="email" name="email" maxlength=50 class="form-control" value="${sysuser.email}"></td>
				</Tr>
				<tr height=25>
					<td align="right" width="25%"><label ID="lbl_remark" style="color: #0066FF">备注</label></Td>
					<td><textarea id="remark" name="remark" rows="2" class="form-control">${sysuser.remark}</textarea></td>
				</Tr>
				<tr height=25>
					<td align="right" width="25%"><label ID="lbl_remark" style="color: #0066FF">状态</label></Td>
					<td><input type="hidden" id="status" name="status" value="${sysuser.status}"><input type="radio" name="rdo" onclick="javascript:clkrdo(1)"
						<c:if test="${sysuser.status==1}">checked</c:if>>启用 <input type="radio" name="rdo" onclick="javascript:clkrdo(0)" <c:if test="${sysuser.status==0}">checked</c:if>>禁用
					</td>
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
	function save() {
		//在这里补充窗口参数检查，
		if ($("#depart_id").val() == "-1") {
			layer.msg("必须选择机构部门！", {
				icon : 2
			});
			return false;
		}
		if ($("#inputForm").valid()) {
			//$("#password").val($.md5($("#password").val()));
			$.post($("#inputForm").attr("action"), $("#inputForm").serialize(),
					function(data) {
						$("button").removeAttr("disabled");
						if (data.statusCode == "200") {
							top.layer.msg("保存成功", {
								icon : 1,
								time : 1000
							});
							getParentWindow("sysUser_list").refreshGrid();
							closeCurrent();
						} else {
							top.layer.msg("保存失败", {
								icon : 2
							});
						}
					});
		}
	}
	function selectDepart() {
		openDialog("选择机构部门", "sltSysDepartment", "500px", "450px");
	}
	//设值
	function setValue(parent_id, parentname) {
		$("#depart_id").val(parent_id);
		$("#depart_name").val(parentname);
	}
	function clkrdo(status){
		$("#status").val(status);
	}
</script>
</html>

