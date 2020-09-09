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

<style>
label input {
	width: 60px;
	height: 30px;
}

td, th {
	padding: 8px;
}
</style>


</head>
<body style="overflow: auto; height: 100%; padding: 0px">
	<form id="inputForm" name="inputForm" action="setUserRole">
		<input type="hidden" value="${userid}" id="userId" name="userId" />
		<input type="hidden" value="" id="roleIds" name="roleIds" />
		<div class="wrapper wrapper-content animated fadeIn">
			<div class="row">
				<div class="col-sm-2" style="padding: 0px 0px; border-width: 1px;" id="RoleTreeBox">
					<ul id="roleTree" class="ztree"></ul>
				</div>
			</div>
			<div class="fixed layui-layer-btn">
				<a class="layui-layer-btn0" onclick="save()">确定</a> 
				<a class="layui-layer-btn1" onclick="closeCurrent()">关闭</a>
			</div>
		</div>
	</form>
</body>
<script type="text/javascript">
	var setting = {
		check:{
			    enable:true
			},
		async : {
			enable : true,
			url : "getSysUserRole?userId="+${userid},
		},
		view : { //节点数据
			  showLine : false
		},
		data : { // 必须使用data    
			simpleData : {
				enable : true,
				idKey : "role_id", // id编号命名     
				pIdKey : 0, // 父id编号命名      
				rootId : 0
			}
		},
		// 回调函数    
		callback : {
			//设置异步数据加载完成后，执行节点展开函数
			onClick : function(event, treeId, treeNode, clickFlag) {
				$("#roleTree").val(treeNode.id);
				refreshGrid();
			}
		}
	};
	//提交信息
	function save() {
		var index = layer.load(2);
	    var $popedomTree = $.fn.zTree.getZTreeObj("roleTree");
	    var nodes = $popedomTree.getCheckedNodes(true);
	    var selected_role_ids="";
	    for(var i=0;i<nodes.length;i++){
	        selected_role_ids+=nodes[i].id;
	        if(i<(nodes.length-1)){
	            selected_role_ids+=",";
	        }
	    }
	    $("#roleIds").val(selected_role_ids);
	    var inputForm = $("#inputForm");
	    $.post(inputForm.attr("action"), inputForm.serialize(), function(data) {
			layer.close(index);
			if (data.statusCode == "200") {
				top.layer.msg("设置成功", {
					icon : 1,
					time : 1000
				});
				closeCurrent();
				//top.getActiveTab()[0].contentWindow.refreshGrid();
			} else {
				if (data.errMsg != null) {
					top.layer.msg(data.errMsg, {icon : 2});
				} else {
					top.layer.msg("设置失败", {icon : 2});
				}
			}
		});


	}
	//加载树
	$(document).ready(function() {
		$.fn.zTree.init($("#roleTree"), setting);
	});
</script>
</html>