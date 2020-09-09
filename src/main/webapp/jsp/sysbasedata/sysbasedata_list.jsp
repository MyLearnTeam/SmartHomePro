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
<title>数据词典 list</title>
<%@ include file="../../base/baseCss.jsp"%>
</head>

<body class="animated fadeInRight">
<form id="searchForm" name="searchForm" action="" onsubmit="return false">
	<div class="wrapper wrapper-content animated fadeIn">
		<input type="hidden" id="parent_id" name="parent_id" value="">
		<div class="row">
           <div  class="col-sm-2" style="padding: 0px 0px; border-width: 1px;">
                <ul id="TreeId" class="ztree" style="overflow: auto;"></ul>
           </div>
           <div class="col-sm-10">
           <div id="toolbar">
               <shiro:hasPermission name="deleteSysBasedata">
                   <button class="btn btn-rounded btn-sm btn-danger" onclick="deleteRecord()">
                       <i class="fa fa-trash"></i>批量删除
                   </button>
               </shiro:hasPermission>
               <shiro:hasPermission name="newSysBasedata">
                   <button class="btn btn-primary btn-rounded btn-sm" onclick="add()">
                       <i class="fa fa-plus"></i> 添加
                   </button>
               </shiro:hasPermission>
               <shiro:hasPermission name="sortSysBasedata">
                   <button class="btn btn-primary btn-rounded btn-sm" onclick="sort()">
                       <i class="glyphicon glyphicon-stats"></i> 排序
                   </button>
               </shiro:hasPermission>
               <shiro:hasPermission name="importSysBasedata">
                   <button class="btn btn-primary btn-rounded btn-sm" onclick="importXls()">
                       <i class="fa fa-edit"></i> 批量导入
                   </button>
               </shiro:hasPermission>
               <shiro:hasPermission name="exportSysBasedata">
                   <button class="btn btn-primary btn-rounded btn-sm" onclick="exportXls()">
                       <i class="fa fa-edit"></i> 批量导出
                   </button>
               </shiro:hasPermission>
           </div>
              
           <table id="sysbasedatalist" data-toggle="table" data-url="getSysBasedataList"
               data-classes="table table-bordered table-hover table-striped"
               data-mobile-responsive="true" data-pagination="true"  
               data-checkboxHeader="true" data-cache="false"
               data-side-pagination="server" data-page-size="15"  data-page-list="[10,15,20,50,100]"
               data-icon-size="outline" data-query-params="queryParams"
               data-show-columns="true" data-toolbar="#toolbar">
               <thead style="background-color: #C0E4F1;">
                   <tr>
                       <th data-width="30" data-checkbox="true"></th>
                       <th data-width="30" data-halign="center" data-align="center" data-formatter="getRowIndex">序号</th>
                     <th data-width="100" data-field="parent_id_cn">上级</th>
                     <th data-width="100" data-field="param_token">数据类型标识</th>
                     <th data-width="100" data-field="pvalue">数据值</th>
                     <th data-width="100" data-field="ptext">数据中文</th>
                     <th data-width="100" data-field="remark">说明</th>
                     <th data-width="100" data-field="sort">排序</th>
                       <th data-width="200" data-align="center" data-formatter="operateFormatter">操作</th>
                   </tr>
               </thead>
           </table>
    </div>
    </div>
 </div>
</form>
   </body>

<%@ include file="../../base/baseJs.jsp"%>
<script>
var setting = {
		async : {
			enable : true,
			url : "getSysBasedataTreeData?RootText=树形结构"
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
				$("#parent_id").val(treeNode.id);
				refreshGrid();
			}
		    }
	};
//刷新树
function refreshTree(){
	$.fn.zTree.init($("#TreeId"), setting);
}

  $(document).ready(function() {
       $.fn.zTree.init($("#TreeId"), setting);//加载树
       //行双击事件
       $("#sysbasedatalist").on('dbl-click-row.bs.table', function(e, name, row) {
           <shiro:hasPermission name="viewSysBasedata">
               view(name.param_id);
           </shiro:hasPermission>
       });
       
  });
   
    //表格行点击事件
    $('#sysbasedatalist').on('click-row.bs.table', function (e, row, $element) {
        $('.success').removeClass('success');
        $($element).addClass('success');
    });
   function refreshGrid() {
       $("#sysbasedatalist").bootstrapTable('refresh', {});
   }
   //序号
   function getRowIndex(value, row, index) {
       var options = $("#sysbasedatalist").bootstrapTable("getOptions", null);
       var pageNumber = options.pageNumber;
       var pageSize = options.pageSize;
       var str = "" + (index + (pageNumber - 1) * pageSize + 1);
       return str;
   }
   //设置每行数据的操作元素及事件
   function operateFormatter(value, row, index) {
       var res = '';
       <shiro:hasPermission name="viewSysBasedata">
           res += '<button class="btn btn-info btn-xs" onclick="view('+row.param_id+')" title="查看">查看</button>&nbsp;';
       </shiro:hasPermission>
       <shiro:hasPermission name="editSysBasedata">
           res += '<button class="btn btn-success btn-xs edit" onclick="edit('+row.param_id+')" title="修改">修改</button>&nbsp;';
       </shiro:hasPermission>
       <shiro:hasPermission name="deleteSysBasedata">
           res += '<button class="btn btn-danger btn-xs remove" onclick="deleteRecord('+row.param_id+')" title="删除">删除</button>&nbsp;';
       </shiro:hasPermission>
       
       return res;
   }

   //添加
   function add() {
       openDialog("新增", "newSysBasedata", "50%", "65%");    
   }
   //修改
   function edit(id) {
       openDialogEdit("修改", "editSysBasedata", "50%", "65%", $("#sysbasedatalist"),id,"param_id");
   }
   //删除
   function deleteRecord(id) {
       deleteRecords("deleteSysBasedata", $("#sysbasedatalist"), id,"param_id");
   }
   //查看
   function view(id) {
       openDialog("查看", "viewSysBasedata?id=" + id, "50%", "65%");    
   }
   //排序
   function sort() {
      openDialog("排序", "sortSysBasedata", "50%", "65%");
   }
   //参数
   function queryParams(params) { //bootstrapTable自带参数
       var temp = $("#searchForm").formToObject();
           temp.limit = params.limit; //页面大小
           temp.offset = params.offset;
       return temp;
   }
   
    //查找按钮
   $("#searchbtn").click(function(){
       $("#sysbasedatalist").bootstrapTable('selectPage',1);
   });
    
   
</script>
</html>

