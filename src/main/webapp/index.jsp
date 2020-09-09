<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<link rel="icon" type="image/png" href="/resources/images/index/logo.ico">
<title>快码猿快速开发框架</title>
<style type="text/css">
.white-color {
	color: white;
}
.blue-color {
	color: #00B99C
}
.row-header {
	line-height: 44px;
}
.skin-1 .nav>li>a:hover {
	background-color: #00B99C;
}

.skin-1 .nav>li>a:focus {
	background-color: #00B99C;
}

.col-sm-4 .nav .open>a, .nav .open>a:focus, .nav .open>a:hover {
	background-color: #00B99C;
}
</style>
<%@ include file="base/baseCss.jsp"%>
<%@ include file="base/baseJs.jsp"%>

</head>
<body class="fixed-sidebar full-height-layout gray-bg pace-done skin-1 animated fadeInRight"
	style="overflow: hidden; background-color: #4682b4">
	<div class="row" style="color: white; background-color: #0086C6">
		<div class="col-sm-4" style="text-align: center;">
			<h2>
				<font>快码猿--快速开发框架</font>
			</h2>
		</div>
		<div class="col-sm-4 row-header">
			<h3 class="row-header">
				<font color=#ffffff>欢迎您！${yhwl_username}</font>
			</h3>
		</div>
		<div class="col-sm-4">
			<ul class="nav navbar-top-links navbar-right">
				<li class="hidden-xs">
					<a href="index_v1.html" class="J_menuItem" data-index="0">
						<i class="fa fa-cart-arrow-down"></i>
						修改密码
					</a>
				</li>
				<li class="dropdown">
					<a class="dropdown-toggle count-info" data-toggle="dropdown" href="#" aria-expanded="false">
						<!-- <i class="fa fa-tasks"></i> -->
						主题<span class="caret"></span>
					</a>
					<ul class="dropdown-menu dropdown-messages blue-color" style="width: 120px; cursor: pointer;">
						<li class="m-t-xs" onclick="chooseToppic('1')">上下布局</li>
						<li class="divider"></li>
						<li onclick="chooseToppic('2')">左右布局</li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
	<div id="topic1" class="nav_menu3">
		<c:set var="topCatalogList" value="${catalogList }" scope="request" />
		<c:import url="/base/topTree.jsp" />
	</div>
	<div id="wrapper">
		<nav id="topic2" class="navbar-default navbar-static-side" role="navigation">
			<div class="nav-close">
				<i class="fa fa-times-circle"></i>
			</div>
			<div class="sidebar-collapse" style="background-color: #0591CD; font-size: 14px">
				<c:set var="level" value="1" scope="request" />
				<c:set var="leftCatalogList" value="${catalogList }" scope="request" />
				<c:import url="/base/leftTree.jsp" />
				<%-- <ul class="nav" id="side-menu">${mycatalogStr}</ul> --%>
			</div>
		</nav>
		<!--左侧导航结束-->
		<!--右侧部分开始-->
		<div id="page-wrapper" class="gray-bg dashbard-1">
			<div class="row content-tabs">
				<!-- <a id="croll-bar" class="navbar-minimalize minimalize-styl-2 btn btn-primary" href="#">
					<i class="fa fa-bars"></i>
				</a> -->
				<button class="roll-nav roll-left J_tabLeft" style="left: 0px;">
					<i class="fa fa-backward"></i>
				</button>
				<nav class="page-tabs J_menuTabs" style="margin-left: 40px;">
					<div class="page-tabs-content">
						<a href="javascript:;" class="active J_menuTab" data-id="Home_Report">首页</a>
					</div>
				</nav>
				<button class="roll-nav roll-right J_tabRight">
					<i class="fa fa-forward"></i>
				</button>
				<button class="roll-nav" style="right: 180px" onclick="refreshCurrent()">
					<i class="fa fa-refresh"></i>
				</button>
				<div class="btn-group roll-nav roll-right">
					<button class="dropdown J_tabClose" data-toggle="dropdown" aria-expanded="false">
						关闭操作
						<span class="caret"></span>
					</button>
					<ul role="menu" class="dropdown-menu dropdown-menu-right">
						<li class="J_tabShowActive">
							<a>定位当前选项卡</a>
						</li>
						<li class="divider"></li>
						<li class="J_tabCloseAll">
							<a>关闭全部选项卡</a>
						</li>
						<li class="J_tabCloseOther">
							<a>关闭其他选项卡</a>
						</li>
					</ul>
				</div>
				<a href="${pageContext.request.contextPath}/logout" class="roll-nav roll-right J_tabExit">
					<i class="fa fa fa-sign-out"></i>
					退出
				</a>
			</div>
			<div class="row J_mainContent" id="content-main">
				<iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="home.jsp" frameborder="0" data-id="Home_Report"
					seamless></iframe>
			</div>
		</div>
		<!--右侧部分结束-->
	</div>
</body>
<script type="text/javascript">
	function chooseToppic(index){
		if(index=="1"){
			$("#topic1").show();
			$("#topic2").hide();
			$("#page-wrapper").css("margin-left","0px");//220px
		}else if(index=="2"){
			$("#topic2").show();
			$("#topic1").hide();
			$("#page-wrapper").css("margin-left","220px");//220px
			//$("#croll-bar").attr("disabled",false);
		}
	}
	chooseToppic('2');
	//J_menuItem_li_child
	$(".J_menuItem_li_child").click(function() {
		$(".J_menuItem_active").removeClass("J_menuItem_active");
		$(this).addClass("J_menuItem_active");
	});
</script>
</html>