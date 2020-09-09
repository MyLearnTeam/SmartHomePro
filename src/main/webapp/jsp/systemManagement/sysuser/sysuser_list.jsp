<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
			<input type="hidden" id="departId" value="" />
			<!-- 点中的栏目ID -->
			<div class="col-sm-2">
				<ul id="TreeId" class="ztree" style="overflow: auto;"></ul>
			</div>
			<div class="col-sm-10">
				<div id="toolbar">
					<shiro:hasPermission name="sysUser_new">
						<button class="btn btn-primary btn-rounded btn-sm" onclick="add()">
							<i class="fa fa-plus"></i> 添加
						</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="sysUser_edit">
						<button class="btn btn-primary btn-rounded btn-sm"
							onclick="edit()">
							<i class="fa fa-edit"></i> 修改
						</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="deleteSysUser">
						<button class="btn btn-rounded btn-sm btn-danger"
							onclick="deleteRecord()">
							<i class="fa fa-trash"></i>删除
						</button>
					</shiro:hasPermission>
				</div>
				<table data-toggle="table" data-url="getSysUserList"
					data-pagination="true" data-cache="false" data-toolbar="#toolbar"
					data-click-to-select="true" data-show-columns="true"
					data-mobile-responsive="true" data-checkboxHeader="true"
					id="sysuserlist" data-side-pagination="server" data-page-size="15"
					data-icon-size="outline" data-page-list="[15,20,50,100]"
					data-show-refresh="true" data-query-params="queryParams">
					<thead style="background-color: #C0E4F1;">
						<tr>
							<th data-checkbox="true">ID</th>
							<th data-formatter="indexFormatter" data-width="50px"
								data-align="center">序号</th>
							<th data-field="login_name">登录名</th>
							<th data-field="truename">真实姓名</th>
							<th data-field="usercode">用户代码</th>
							<th data-field="depart_name">机构部门</th>
							<th data-field="mobile">手机</th>
							<th data-field="status" data-formatter="statusFormatter">状态</th>
							<th data-width="300" data-align="left" data-formatter="operateFormatter">操作</th>

						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
	<script>
		var setting = {
			async : {
				enable : true,
				url : "getSysDepartmentTreeData"
			},
			data : { // 必须使用data    
				simpleData : {
					enable : true,
					idKey : "id", // id编号命名     
					pIdKey : "pId", // 父id编号命名      
					rootId : 0
				}
			},
			// 回调函数    
			callback : {
				//设置异步数据加载完成后，执行节点展开函数
				onClick : function(event, treeId, treeNode, clickFlag) {
					$("#departId").val(treeNode.id);
					refreshGrid();
				}
			}
		};
		//加载树
		$(document).ready(function() {
			$.fn.zTree.init($("#TreeId"), setting);
			//行双击事件
			$("#sysuserlist").on('dbl-click-row.bs.table',function(e, name, row) {
				view(name.user_id);
			});
		});
		//点击树刷新栏目列表
		function refreshGrid() {
			$("#sysuserlist").bootstrapTable('selectPage',1);
		}

		//刷新树
		function refreshTree() {
			$.fn.zTree.init($("#TreeId"), setting);
		}
		//添加
		function add() {
			//iframe层
			openDialog("新增", "sysUser_new", "50%", "80%");	
		}
		//修改
		function edit(id) {
			openDialogEdit("修改", "sysUser_edit", "50%", "80%", $("#sysuserlist"),id, "user_id");
		}
		//删除
		function deleteRecord(id) {
			deleteRecords("deleteSysUser", $("#sysuserlist"), id,"user_id");
		}
		//查看
		function view(id) {
			openDialogView("查看", "sysUser_view?id=" + id, "50%", "80%", $("#sysuserlist"));
		}
		function setUserRole(id) {
			openDialog("设置用户角色", "sysUser_setRole?userid=" + id, "25%", "80%");	
		}
		//参数
		function queryParams(params) { //bootstrapTable自带参数
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				limit : params.limit, //页面大小
				offset : params.offset,
				departid : $("#departId").val(),
			};
			return temp;
		}
		//序号
		function indexFormatter(value, row, index) {
			var options = $("#sysuserlist").bootstrapTable("getOptions", null);
			var pageNumber = options.pageNumber;
			var pageSize = options.pageSize;
			var str = "" + (index + (pageNumber - 1) * pageSize + 1);
			return str;
		}
		//表格-操作 - 格式化 
		function statusFormatter(value, row, index) {
			var str = '';
			if (row.status == 1) {
				str = "正常";
			} else {
				str = "<font color=red>禁用</font>";
			}
			return str;
		}
		function setwxFocus(value, row, index) {
			var str = '';
			if (row.is_focus == 1) {
				str = "是";
			} else {
				str = "否";
			}
			return str;
		}

		//表格-操作 - 格式化 
		function operateFormatter(value, row, index) {
			var str = '';
	       <shiro:hasPermission name="sysUser_setRole">
	       		str+="<a href='javascript:void(0)' class='btn btn-success btn-xs' onclick='setUserRole("+row.user_id+")'>设置角色</a>&nbsp;";        
	       </shiro:hasPermission>
	       <shiro:hasPermission name="sysUser_edit">
	       		str+="<a href='javascript:void(0)' class='btn btn-success btn-xs' onclick='edit("+row.user_id+")'><i class='fa fa-edit'></i> 修改</a>&nbsp;";        
	       </shiro:hasPermission>
	       <shiro:hasPermission name="deleteSysUser">
	       		str+="<a href='javascript:void(0)' class='btn btn-danger btn-xs' onclick='deleteRecord("+row.user_id+")'><i class='fa fa-trash'></i> 删除</a>&nbsp;";
	       </shiro:hasPermission>
	       str+="<a href='javascript:void(0)' class='btn btn-info btn-xs' onclick='view("+row.user_id+")'><i class='fa fa-search-plus'></i>查看</a>&nbsp;";
	       return str;
		}
	</script>
</body>

</html>

