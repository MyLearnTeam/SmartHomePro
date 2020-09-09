<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="decorator" content="default" />
<title>选择上级</title>
<%@ include file="../../../base/baseCss.jsp"%>
</head>

<body style="overflow: auto; height: 100%; padding: 0px">
	<input type="hidden" id="pcid" />
	<input type="hidden" id="parentid" />
	<input type="hidden" id="parentname" />
	<div class="wrapper wrapper-content animated fadeIn">
		<div class="row">
			<div class="col-sm-2" style="padding: 0px 0px; border-width: 1px;">
				<ul id="catalogTreeId" class="ztree" style="overflow: auto;"></ul>
			</div>
			<div class="fixed layui-layer-btn">
				<a class="layui-layer-btn0" onclick="save()">确定</a> 
				<a class="layui-layer-btn1" onclick="closeCurrent()">关闭</a>
			</div>
		</div>
	</div>
</body>
<%@ include file="../../../base/baseJs.jsp"%>
<script>
	//ztree操作
	var setting = {
		// check:{
		//    enable:true
		//},
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
				$('#parentid').val(treeNode.id);
				$('#parentname').val(treeNode.name);
				save();
			}
		}
	};
	function save(){
		var parent = getParentWindow("editSysCatalog");
		if(!parent){
			parent = getParentWindow("toAddSysCatalog");
		}
		parent.setValue($('#parentid').val(),$('#parentname').val());
		closeCurrent();
	}
	//加载树
	$(document).ready(function() {
		$.fn.zTree.init($("#catalogTreeId"), setting);
	});
	//刷新树
	function refreshTree() {
		$.fn.zTree.init($("#catalogTreeId"), setting);
	}
</script>

</html>
