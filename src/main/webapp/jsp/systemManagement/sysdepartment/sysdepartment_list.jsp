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
		<input type="hidden" id="parentid" name="parentid" value="${parentid}">
		<div class="row">
			<div  class="col-sm-2" style="padding: 0px 0px; border-width: 1px;">
				<ul id="TreeId" class="ztree" style="overflow: auto;"></ul>
			</div>
			<div class="col-sm-10">
				<div id="toolbar">
					<shiro:hasPermission name="sysDepartment_new">
						<button class="btn btn-primary btn-rounded btn-sm" onclick="add()">
							<i class="fa fa-plus"></i> 添加
						</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="sysDepartment_edit">
						<button class="btn btn-primary btn-rounded btn-sm" onclick="edit()">
							<i class="fa fa-edit"></i> 修改
						</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="deleteSysDepartment">
						<button class="btn btn-rounded btn-sm btn-danger" onclick="deleteRecord()">
							<i class="fa fa-trash"></i>删除
						</button>
					</shiro:hasPermission>
				<!-- 	<button id="sortButton" class="btn btn-primary btn-rounded btn-outline btn-sm" onclick="sort()">
						<i class="glyphicon glyphicon-stats"></i>排序
					</button> -->
				</div>
				<table data-toggle="table" data-url="getSysDepartmentList" 
					data-pagination="true" data-striped="true" data-cache="false"
					data-toolbar="#toolbar" data-click-to-select="true"
					data-show-columns="true" data-mobile-responsive="true" 
					data-checkboxHeader="true" id="sysdepartmentlist" 
					data-side-pagination="server" data-page-size="15" data-query-params="queryParams"
					data-icon-size="outline" data-page-list="[15,20,50,100]" data-show-refresh="true">
					<thead style="background-color: #C0E4F1;">
						<tr>
							<th data-checkbox="true">ID</th>
							<th data-formatter="indexFormatter" data-width="60px" data-align="center">序号</th>
							<th data-field="depart_code">部门编号</th>
							<th data-field="depart_name">部门名称</th>
							<th data-field="remark">说明</th>
							<th data-width="300" data-align="left" data-formatter="operateFormatter">操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</body>
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
				$("#parentid").val(treeNode.id);
				refreshGrid();
			}
		}
	};
	function refreshGrid() {
		$("#sysdepartmentlist").bootstrapTable('selectPage',1);
	}
	//添加
	function add() {
		//iframe层
		openDialog("新增", "sysDepartment_new", "50%", "80%");	
	}
	//修改
	function edit(id) {
		openDialogEdit("修改", "sysDepartment_edit", "50%", "80%", $("#sysdepartmentlist"),id, "depart_id");
	}
	//删除
	function deleteRecord(id) {
		deleteRecords("deleteSysDepartment", $("#sysdepartmentlist"), id,"depart_id");
	}
	//查看
	function view(id) {
		openDialogView("查看", "sysDepartment_view?id=" + id, "50%", "80%", $("#sysdepartmentlist"));
	}
	//参数
	function queryParams(params) { //bootstrapTable自带参数
		var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, //页面大小
			offset : params.offset,
			parentid : $("#parentid").val(),
		};
		return temp;
	}
	//加载树
	$(document).ready(function() {
		$.fn.zTree.init($("#TreeId"), setting);
		//行双击事件
	   	$("#sysdepartmentlist").on('dbl-click-row.bs.table', function (e, name, row) {
	       view(name.depart_id);
	   	}); 
	});

	//刷新树
	function refreshTree(){
		$.fn.zTree.init($("#TreeId"), setting);
	}
   
 	//排序
   function sort(){
       openDialogChkValue("排序","sortSysDepartment?parent_id=" + $("#comid").val(),"650px","450px",$("#sysdepartmentlist"));
   }
   //序号
   function indexFormatter(value, row, index) {
       var options = $("#sysdepartmentlist").bootstrapTable("getOptions", null);
       var pageNumber = options.pageNumber;
       var pageSize = options.pageSize;
       var str = ""+(index+(pageNumber-1)*pageSize+1);
       return str;
   }
   //表格-操作 - 格式化 
   function operateFormatter(value, row, index) {
       var str = '';
       <shiro:hasPermission name="sysDepartment_edit">
       		str+="<a href='javascript:void(0)' class='btn btn-success btn-xs' onclick='edit("+row.depart_id+")'><i class='fa fa-edit'></i> 修改</a>&nbsp;";        
       </shiro:hasPermission>
       <shiro:hasPermission name="deleteSysDepartment">
       		str+="<a href='javascript:void(0)' class='btn btn-danger btn-xs' onclick='deleteRecord("+row.depart_id+")'><i class='fa fa-trash'></i> 删除</a>&nbsp;";
       </shiro:hasPermission>
       str+="<a href='javascript:void(0)' class='btn btn-info btn-xs' onclick='view("+row.depart_id+")'><i class='fa fa-search-plus'></i>查看</a>&nbsp;";
       return str;
   }
   //显示状态
   function statusFormatter(value, row, index){
       if (row.status==1){
           return "是";
       }else{
           return "否";
       }
   }
</script>
</html>

