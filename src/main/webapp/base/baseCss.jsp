<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
	.animate{
		left:100%;
		width:100%;
		position:absolute;
		/* overflow-x: hidden; */
	}
	.fixed {
		position: fixed;
		left: 0px;
		bottom: 0px;
		width: 100%;
		height: 40px;
		background-color: #F8F8F8;
		z-index: 9999;
	}
	.fixed a {
		margin-top: 7px;
	}
	/* .help-block span{
		color: red;
	} */
	/* 条件查询样式 */
	.list-row .ibox{
		margin-bottom: 0px;
	}
	.searchbar .ibox-title{
		min-height: 50px;
		border-width: 0px 0px 0px 0px;
		margin-top: 15px;
		border-bottom-style: solid;
		cursor: pointer;
		padding: 5px 15px 0px 5px;
		min-height: 30px;
		background-color: #F9F9F8;
	}
	.searchbar .ibox-tools{
		margin-top: 5px;
	}
	.searchbar .ibox-title div{
		display: inline;
	}
	.searchbar .ibox-content{
		border-width: 1px 1px 1px;
		padding: 10px 10px 0px 0px;
	}
	.searchbar .form-control{
		width: 65%;
		float: right;
		height: 30px;
		padding: 0px 10px;
	}
	.searchbar .search-col{
		padding-left: 0px;
		padding-right: 0px;
		width: 300px;
		display: inline-block;
		/* margin-bottom: 10px; */
	}
	.search-col .form-control {
	    width: 65%;
	    float: right;
	    height: 30px;
	    padding: 0px 10px;
	}
	.search-name{
		width: 35%;
	    float: left;
	    font-size: 13px;
	    height: 30px;
	    text-align: right;
	    line-height: 30px;
	    padding-right: 10px;
	    white-space: nowrap;
	}
	.wrapper .list-row{
		background-color: white;margin: 5px -5px;
	}
	.searchbar .search-col .dateform{
		width: 32.5%;
		padding: 6px 6px;
		float: left;
	}
	/* .searchbar .search-col .dateform{
		width: 32.5%;
		padding: 6px 6px;
	} */
	/* 父子表样式 */
	.wrapper .child-table {
   		margin-bottom: 10px;
   		margin-top: 5px;
   	}
	.child-table .form-control{
		border: none;
		padding: 0px;
		height: 30px;
	}
	.child-table tbody tr th:last-child{
       width: 65px;
   	}
	.child-table tbody tr th{
       color: #0066FF;
   	}
   	.child-table tbody tr{
   		height: 30px;
   	}
	.child-table tbody select{
       min-width: 70px;
   	}
	.child-table tbody tr td a{
       margin-right: auto;
   	}
	.wrapper .child-table tbody tr td{
        padding: 0px 8px;
   	}
   	.template-tr{
   		display: none;
   	}
   	.parent-table {	
    	width: 100%;
	}  
	.parent-table td{
		height: 40px;
		padding: 5px;
		border: 1px solid #e7e7e7;
		color: #4682B4;
	}
	.parent-table label{
		color: #0066FF;
	} 
</style>
<!-- 引入bootstrap插件 -->
<link href="${pageContext.request.contextPath}/resources/plugins/bootstrap/bootstrap.min.css?v=3.3.5" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/plugins/fonts/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
<!--[if (gt IE 9)|(!IE)]> -->
<link href="${pageContext.request.contextPath}/resources/common/css/style.css?v=4.0.0" rel="stylesheet">
<!-- <![endif]-->
<!--[if lte IE 9]>
	<link href="${pageContext.request.contextPath}/resources/plugins/bootstrap/css/styleie8.css?v=4.0.0" rel="stylesheet">
<![endif]-->

<!-- 引入bootstrap table -->
<link href="${pageContext.request.contextPath}/resources/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/plugins/bootstrap-table/extensions/resizableColumns/jquery.resizableColumns.css" rel="stylesheet">

<link href="${pageContext.request.contextPath}/resources/common/css/animate.css" rel="stylesheet">

<!-- 引入ztree件 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugins/ztree/css/zTreeStyle.css">
<!-- 引入下拉树 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/common/css/common.css">
<!-- 顶部菜单栏 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/common/css/nav_menu3.css">
<!-- 引入多选下拉框 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugins/plugins-select/bootstarp-select-min.css">