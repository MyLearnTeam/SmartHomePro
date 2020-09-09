<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="decorator" content="default" />
<title></title>
<%@ include file="../../../base/baseCss.jsp"%>
<%@ include file="../../../base/baseJs.jsp"%>
</head>
<body class="animated fadeInRight">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-12">
				<div id="toolbar">
					<shiro:hasPermission name="sysRole_new">
						<button class="btn btn-primary btn-rounded btn-sm" onclick="add()">
							<i class="fa fa-plus"></i> 添加
						</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="sysRole_edit">
						<button class="btn btn-primary btn-rounded btn-sm" onclick="edit()">
							<i class="fa fa-edit"></i> 修改
						</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="deleteSysRole">
						<button class="btn btn-rounded btn-sm btn-danger" onclick="deleteRecord()">
							<i class="fa fa-trash"></i>删除
						</button>
					</shiro:hasPermission>
				</div>
				<table data-toggle="table" data-url="getSysRoleList"
					data-pagination="true" data-striped="true" data-toolbar="#toolbar"
					data-sort-order="asc" data-click-to-select="false"
					data-show-columns="true" data-mobile-responsive="true"
					data-checkboxHeader="true" id="sysrolelist" data-cache="false"
					data-side-pagination="server" data-page-size="15"
					data-icon-size="outline" data-page-list="[15,20,50,100]"
					data-show-refresh="true" data-query-params="queryParams">
					<thead style="background-color: #C0E4F1;">
						<tr>
							<th data-checkbox="true">ID</th>
							<th data-formatter="indexFormatter" data-width="60px" data-align="center">序号</th>
							<th data-field="role_code">角色编号</th>
							<th data-field="role_name">角色名称</th>
							<th data-field="remark">备注</th>
							<th data-formatter="settingUser" data-width="110px" data-align="center">角色用户</th>
							<th data-formatter="settingPop" data-width="110px" data-align="center">权限设置</th>
							<th data-width="300" data-align="left" data-formatter="operateFormatter">操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</body>
<script>
	//加载树
	$(document).ready(function() {
		//行双击事件
		$("#sysrolelist").on('dbl-click-row.bs.table',function(e, name, row) {
			view(name.role_id);
		});
	});
	//点击树刷新栏目列表
	function refreshGrid() {
		$("#sysrolelist").bootstrapTable('selectPage',1);
	}
	//添加
	function add() {
		openDialog("新增", "sysRole_new", "50%", "80%");	
	}
	//修改
	function edit(id) {
		openDialogEdit("修改", "sysRole_edit", "50%", "80%", $("#sysrolelist"),id, "role_id");
	}
	//删除
	function deleteRecord(id) {
		deleteRecords("deleteSysRole", $("#sysrolelist"), id,"role_id");
	}
	//查看
	function view(id) {
		openDialogView("查看", "sysRole_view?id=" + id, "50%", "80%", $("#sysrolelist"));
	}
	function setUserRole(id) {
		openDialog("设置用户角色", "sysUser_setRole?userid=" + id, "25%", "80%");	
	}
	//参数
	function queryParams(params) { //bootstrapTable自带参数
		var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, //页面大小
			offset : params.offset,
		};
		return temp;
	}
	//序号
	function indexFormatter(value, row, index) {
		var options = $("#sysrolelist").bootstrapTable("getOptions", null);
		var pageNumber = options.pageNumber;
		var pageSize = options.pageSize;
		var str = "" + (index + (pageNumber - 1) * pageSize + 1);
		return str;
	}
	//表格-操作 - 格式化 
	function operateFormatter(value, row, index) {
		var str = '';
	   <shiro:hasPermission name="sysRole_edit">
	   		str+="<a href='javascript:void(0)' class='btn btn-success btn-xs' onclick='edit("+row.role_id+")'><i class='fa fa-edit'></i> 修改</a>&nbsp;";        
	   </shiro:hasPermission>
	   <shiro:hasPermission name="deleteSysRole">
	   		str+="<a href='javascript:void(0)' class='btn btn-danger btn-xs' onclick='deleteRecord("+row.role_id+")'><i class='fa fa-trash'></i> 删除</a>&nbsp;";
	   </shiro:hasPermission>
	   str+="<a href='javascript:void(0)' class='btn btn-info btn-xs' onclick='view("+row.role_id+")'><i class='fa fa-search-plus'></i>查看</a>&nbsp;";
	   return str;
	}
	function settingUser(value, row, index) {
		return "<buttona onclick='javascript:setRoleUser(" + row.role_id+ ")' class='btn btn-success btn-xs'>设置</button>";
	}
	function setRoleUser(id) {
		openDialog("设置角色-用户", "setRoleUser?roleid=" + id,"650px", "550px");
	}
	 
	function settingPop(value, row, index) {
		return "<a href='javascript:setting(" + row.role_id + ")' class='btn btn-success btn-rounded btn-xs'>设置</a>";
	}
	function setting(id) {
		openDialog("设置角色权限", "setRolePopedom?roleid=" + id, "450px", "550px");
	}
</script>
</html>

