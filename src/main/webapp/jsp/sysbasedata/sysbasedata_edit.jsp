<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>数据词典 edit</title>
<%@ include file="../../base/baseCss.jsp"%>
</head>
<body style="overflow: auto; height: 100%; padding: 0px">
   <div class="wrapper wrapper-content animated fadeIn">
       <form id="inputForm" name="inputForm" action="updateSysBasedata" onsubmit="return false">
       <input type="hidden" value="${sysbasedata.param_id }" id="param_id" name="param_id"/>
           <table class="parent-table">
             <tr  >
               <td align="right"  width="30%"><label   ID="lbl_parent_id">上级</label></td>
                <td>
                 <div class="btn-group treeDrop">
                  <button type="button" id="dropButton" onclick="selectTree('dropdown-menu')" class="btn btn-default dropdown-toggle  form-control">
                   <span id="parent_id_cn">${parent_id_cn}</span> 
                   <span class="caret"></span>
                  </button>
                 <div class="dropdown-menu" id="dropdown-menu">
                	 <ul id="TreeId" class="ztree"></ul>
                 </div>
                 </div>
                 <input type="hidden" name="parent_id" id="parent_id" value="${sysbasedata.parent_id}">
                </td>
             </tr>
             <tr  >
               <td align="right"  width="30%"><label   ID="lbl_param_token">数据类型标识</label></td>
               <td><input type="text" id="param_token" name="param_token" maxlength=30  class="form-control" value="${sysbasedata.param_token}"></td>
             </tr>
             <tr  >
               <td align="right"  width="30%"><label   ID="lbl_pvalue">数据值</label></td>
               <td><input type="text" id="pvalue" name="pvalue"     class="form-control"  required="required" number="true" value="${sysbasedata.pvalue}"></td>
             </tr>
             <tr  >
               <td align="right"  width="30%"><label   ID="lbl_ptext">数据中文</label></td>
               <td><input type="text" id="ptext" name="ptext" maxlength=150  class="form-control" value="${sysbasedata.ptext}"></td>
             </tr>
             <tr  >
               <td align="right"  width="30%"><label   ID="lbl_remark">说明</label></td>
               <td><input type="text" id="remark" name="remark" maxlength=200  class="form-control" value="${sysbasedata.remark}"></td>
             </tr>
         </table>
           <div class="fixed layui-layer-btn">
               <a class="layui-layer-btn0" onclick="save(1)">提交</a> 
               <a class="layui-layer-btn3" onclick="reset()">重置</a> 
               <a class="layui-layer-btn0" onclick="closeCurrent()">关闭</a>
           </div>
       </form>
   </div>
</body>
<%@ include file="../../base/baseJs.jsp"%>
<script type="text/javascript">
	//ztree操作
	var setting = {
		// check:{
           //    enable:true
           //},
		async : {
			enable : true,
			url : "getSysBasedataTreeData?RootText=无上级"
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
			  $("#parent_id_cn").html(treeNode.name);
			  $("#parent_id").val(treeNode.id);
			  $("#dropdown-menu").hide();
			}
		}
	};
   $(document).ready(function() {
       $.fn.zTree.init($("#TreeId"), setting);//初始化树型数据
   });
   function save(btntypeid) {
       // 提交页面数据
      if($("#parent_id").val()=="0" && $("#param_token").val()=="")
       {
    	   top.layer.msg("必须填写数据类型标识！", {
               icon : 2,
               time : 3000
           });
    	   return false;
       }
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
                   getParentWindow("listSysBasedata").refreshGrid();
                   closeCurrent();
                   //top.getActiveTab()[0].contentWindow.refreshGrid();
               } else {
                   if (data.msg != null) {
                       top.layer.msg(data.msg, {icon : 2});
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
</script>
</html>

