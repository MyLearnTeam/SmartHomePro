<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="decorator" content="default" />
<title>设置权限</title>
<%@ include file="../../../base/baseCss.jsp"%>
</head>

<body style="overflow: auto; height: 100%; padding: 0px">
	<form id="inputForm" name="inputForm" action="setRolePopedomOK">
		<input type="hidden" value="${roleid}" id="roleid" name="roleid" /> <input
			type="hidden" name="cid" id="cid">
		<div class="wrapper wrapper-content animated fadeIn">
			<div class="row">
				<div class="col-sm-2" style="padding: 0px 0px; border-width: 1px;">
					<ul id="catalogTreeId" name="catalogTreeId" class="ztree" style="overflow: auto;height: 463px;"></ul>
				</div>
			</div>
		</div>
		<div class="fixed layui-layer-btn">
			<a class="layui-layer-btn0" onclick="save()">保存</a> 
			<a class="layui-layer-btn0" onclick="closeCurrent()">关闭</a>
		</div>
	</form>
</body>
<%@ include file="../../../base/baseJs.jsp"%>
<script type="text/javascript">
	//ztree操作
	var setting = {
		check : {
			enable : true
		},
		async : {
			enable : true,
			url : "getRoleCatalogTreeData?roleid=${roleid}"
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
				//alert(treeNode.name);
				$("#catalogTreeId").height($(window).height()-60);
			}
		}
	};
	//加载树
	$(document).ready(function() {
		$.fn.zTree.init($("#catalogTreeId"), setting);
		$(window).resize(function () {
			$("#catalogTreeId").height($(window).height()-60);
	    });
		
	});
	function save() {
		var $popedomTree = $.fn.zTree.getZTreeObj("catalogTreeId");
		var nodes = $popedomTree.getCheckedNodes(true);
		var cidArray = [];
		$(nodes).each(function() {
			if (this.id > 0) {
				cidArray[cidArray.length] = this.id;
			}
		});
		$("#cid").val(cidArray.join(","));
		//alert($("#cid").val());	

		$.post($("#inputForm").attr("action"), $("#inputForm").serialize(),function(data) {
			if (data.statusCode == "200") {
				top.layer.msg("保存成功", {
					icon : 1,
					time : 1000
				});
				closeCurrent();
			} else {
				top.layer.msg("保存失败", {
					icon : 2
				});
			}
		});
	}
</script>
</html>
