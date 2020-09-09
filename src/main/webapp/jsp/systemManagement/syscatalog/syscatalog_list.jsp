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
<title> </title>
<%-- <%@ include file="../../../base.jsp"%> --%>
<%@ include file="../../../base/baseCss.jsp"%>
</head>

<body class="animated fadeInRight">
	<div class="wrapper wrapper-content">
		<input type="hidden" id="pcid" />
		<div class="row">
			<div class="col-sm-2" style="padding: 0px 0px; border-width: 1px;">
				<ul id="catalogTreeId" name="catalogTreeId" class="ztree" style="overflow: auto;"></ul>
			</div>
			<div class="col-sm-10">
				<div id="toolbar" style="margin-top: 10px;">
					 <%-- <shiro:hasPermission name="toAddSysCatalog"> --%>
						<button class="btn btn-primary btn-rounded btn-sm" onclick="add()">
							<i class="fa fa-plus"></i> 添加
						</button>
						<%-- </shiro:hasPermission> --%>
					<%--  <shiro:hasPermission name="editSysCatalog"> --%>
						<button class="btn btn-primary btn-rounded btn-sm" onclick="edit()">
							<i class="fa fa-edit"></i> 修改
						</button>
						<%-- </shiro:hasPermission> --%>
					<%--  <shiro:hasPermission name="deleteSysCatalog"> --%>
						<button class="btn btn-rounded btn-sm btn-danger" onclick="deleteit()">
							<i class="fa fa-trash"></i>删除
						</button>
						<%-- </shiro:hasPermission> --%>
					<%--  <shiro:hasPermission name="sortSysCatalog"> --%>
					<button id="sortButton" class="btn btn-primary btn-rounded btn-sm"
						onclick="sort()">
						<i class="glyphicon glyphicon-stats"></i>排序
					</button>
					<%-- </shiro:hasPermission> --%>
				</div>
				<table data-toggle="table" data-url="getCatalogList"
					data-pagination="true" data-striped="true" data-cache="false"
					data-show-columns="true" data-mobile-responsive="true"
					data-checkboxHeader="true" id="catalogList"
					data-side-pagination="server" data-page-size="10"
					data-icon-size="outline" data-query-params="queryParams"
					data-show-refresh="true" data-toolbar="#toolbar">
					<thead style="background-color: #C0E4F1;">
						<tr>
							<th data-field="state" data-checkbox="true"></th>
							<th data-width="230" data-field="catalog">栏目名称</th>
							<th data-formatter="catalogtype">类型</th>
							<th data-width="190" data-field="url">路径</th>
							<th data-width="190" data-field="catalogcode">栏目标识</th>
							<th data-formatter="imgFormatter">图标</th>
							<!-- <th data-field="pname">父栏目</th> -->
							<th data-field="sort">排序</th>
							<th data-width="200" data-align="left" data-formatter="operateFormatter">操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
	<%@ include file="../../../base/baseJs.jsp"%>
</body>
<script>
	//添加
	function add() {
		openDialog("新增", "toAddSysCatalog", "50%", "80%");	
	}
	//修改
	function edit(id) {
		openDialogEdit("修改", "editSysCatalog", "50%", "80%", $("#catalogList"),id, "catalog_id");
	}
	//删除
	function deleteit(id,caid) {
		deleteRecords("deleteSysCatalog", $("#catalogList"), id,"catalog_id");
		refreshGrid();
		refreshTree();
	}
	//查看
	function view(id) {
		openDialogView("查看", "viewSysCatalog?id=" + id, "700px", "550px", $("#catalogList"));
	}
	//添加
	function sort() {
		openDialog("排序", "sortSysCatalog", "50%", "50%");	
	}
	//加载树
	$(document).ready(function() {
		//行双击事件
		$("#catalogList").on('dbl-click-row.bs.table', function(e, name, row) {
			view(name.catalog_id);
		});
		$.fn.zTree.init($("#catalogTreeId"), setting);
	});
	//点击树刷新栏目列表
	function refreshGrid() {
		$("#catalogList").bootstrapTable('selectPage',1);
	}
	function queryParams(params) { //bootstrapTable自带参数
		var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, //页面大小
			offset : params.start,
			pid : $("#pcid").val()
		};
		return temp;
	}

	//ztree操作
	var setting = {
		async : {
			enable : true,
			url : "getCatalogTreeData"
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
				//alert(treeNode.id);
				$("#pcid").val(treeNode.id);
				refreshGrid();
			}
		}
	};
	
	
	//刷新树
	function refreshTree() {
		$.fn.zTree.init($("#catalogTreeId"), setting);
	}
	
	//设置每行数据的操作元素及事件
	function operateFormatter(value, row, index) {
		var res = '';
			/* <shiro:hasPermission name="editSysCatalog"> */
			res += '<button class="btn btn-success btn-xs edit" onclick="edit('+row.catalog_id+')" title="修改">修改</button>&nbsp;';
			/* </shiro:hasPermission> */
			/* <shiro:hasPermission name="deleteSysCatalog"> */
			res += '<button class="btn btn-danger btn-xs remove" onclick="deleteit('+row.catalog_id+')" title="删除">删除</button>&nbsp;'
			/* </shiro:hasPermission> */
			/* <shiro:hasPermission name="viewSysCatalog"> */
			res += '<button class="btn btn-success btn-xs" onclick="view('+row.catalog_id+')" title="查看">查看</button>'
		/* 	</shiro:hasPermission> */
		return res;
	}
	function imgFormatter(value, row, index) {
		return "<i class='"+row.img+"'></i>";
	}
	function catalogtype(value, row, index) {
		var str = "";
		if (row.is_menu == 1) {
			str = "菜单";
		} else if (row.is_menu == 2) {
			str = "权限";
		}
		return str;
	}
	
	
</script>
</html>