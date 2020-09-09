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
<title> list</title>
<%@ include file="../../../base/baseCss.jsp"%>
<%@ include file="../../../base/baseJs.jsp"%>
</head>

<body class="animated fadeInRight">
       <div class="wrapper wrapper-content animated fadeIn">
           <div id="toolbar">
               <shiro:hasPermission name="deleteSysLogs">
                   <button class="btn btn-rounded btn-sm btn-danger" onclick="deleteRecord()">
                       <i class="fa fa-trash"></i>批量删除
                   </button>
               </shiro:hasPermission>
               
           </div>
           <table id="syslogslist" data-toggle="table" data-url="getlistSysLogs"
               data-classes="table table-bordered table-hover table-striped"
               data-mobile-responsive="true" data-pagination="true"  
               data-checkboxHeader="true" data-cache="false"
               data-side-pagination="server" data-page-size="10"  data-page-list="[10,15,20,50,100]"
               data-icon-size="outline" data-query-params="queryParams"
               data-show-columns="true" data-toolbar="#toolbar">
               <thead style="background-color: #C0E4F1;">
                   <tr>
                       <th data-width="30" data-checkbox="true"></th>
                       <th data-width="30" data-halign="center" data-align="center" data-formatter="getRowIndex">序号</th>
                     <th data-width="100" data-field="title">标题</th>
                     <th data-width="100" data-field="log_info">日志信息</th>
                     <th data-width="100" data-field="user_name">操作人</th>
                     <th data-width="100" data-field="ip_addr">IP地址</th>
                     <th data-width="100" data-field="create_time">发生时间</th>
                     
                   </tr>
               </thead>
           </table>
       </div>
   </body>


<script>

  $(document).ready(function() {
       //行双击事件
       $("#syslogslist").on('dbl-click-row.bs.table', function(e, name, row) {
           <shiro:hasPermission name="viewSysLogs">
               view(name.log_id);
           </shiro:hasPermission>
       });
  });
   
    //表格行点击事件
    $('#syslogslist').on('click-row.bs.table', function (e, row, $element) {
        $('.success').removeClass('success');
        $($element).addClass('success');
    });
    $("#syslogslist").find("tr th").css("background","#C0E4F1");
   function refreshGrid() {
       $("#syslogslist").bootstrapTable('selectPage',1);
   }
   //序号
   function getRowIndex(value, row, index) {
       var options = $("#syslogslist").bootstrapTable("getOptions", null);
       var pageNumber = options.pageNumber;
       var pageSize = options.pageSize;
       var str = "" + (index + (pageNumber - 1) * pageSize + 1);
       return str;
   }
   //设置每行数据的操作元素及事件
   function operateFormatter(value, row, index) {
       var res = '';
       <shiro:hasPermission name="viewSysLogs">
           res += '<button class="btn btn-info btn-xs" onclick="view('+row.log_id+')" title="查看">查看</button>&nbsp;';
       </shiro:hasPermission>
       <shiro:hasPermission name="editSysLogs">
           res += '<button class="btn btn-success btn-xs edit" onclick="edit('+row.log_id+')" title="修改">修改</button>&nbsp;';
       </shiro:hasPermission>
       <shiro:hasPermission name="deleteSysLogs">
           res += '<button class="btn btn-danger btn-xs remove" onclick="deleteRecord('+row.log_id+')" title="删除">删除</button>&nbsp;';
       </shiro:hasPermission>
       
       return res;
   }

   //添加
   function add() {
       openDialog("新增", "newSysLogs", "50%", "65%");    
   }
   //修改
   function edit(id) {
       openDialogEdit("修改", "editSysLogs", "50%", "65%", $("#syslogslist"),id, "log_id");
   }
   //删除
   function deleteRecord(id) {
       deleteRecords("deleteSysLogs", $("#syslogslist"), id,"log_id");
   }
   //查看
   function view(id) {
       openDialogView("查看", "viewSysLogs?log_id=" + id, "50%", "65%", $("#syslogs"));
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
       $("#syslogslist").bootstrapTable('refresh', {});
   });
    
   
</script>
</html>

