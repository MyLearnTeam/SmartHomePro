<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

<title>系统登录</title>
<meta name="keywords" content="">
<meta name="description" content="">
<link rel="icon" type="image/png" href="../resources/images/login/logo.png">
<%@ include file="../base/baseCss.jsp"%>
<link href="${pageContext.request.contextPath}/resources/common/css/login.css" rel="stylesheet">
<script>
	if (window.top !== window.self) {
		window.top.location = window.location;
	}
</script>

<style type="text/css">
.help-block {
	color: #ffffff !important;
}
</style>

</head>

<body class="signin">
	<div class="signinpanel">
		<div class="row">
			<div class="col-sm-7">
				<div class="signin-info">
					<div class="logopanel m-b">
						<span><img src="${pageContext.request.contextPath}/resources/images/login/logo.png"></span>
						<h2>快码猿快速开发框架</h2>
					</div>
					<div class="m-b"></div>
					<h3>欢迎使用 !</h3>
				</div>
			</div>
			<div class="col-sm-5">
				<form id="inputForm" method="post" action="${pageContext.request.contextPath}/sysLogin">
					<h4 class="no-margins">用户登录：</h4>
					<p class="m-t-md"></p>
					<input type="text" id="logname" name="logname"
						class="form-control uname" placeholder="用户名" value="admin"
						style="color: #000 !important;" /> 
					<input type="password"
						id="password" name="password" class="form-control pword m-b"
						placeholder="密码" style="color: #000 !important;" value="123456" />

					<div>
						<input class="form-control" type="text" id="verify_code" name="verify_code" placeholder="验证码" 
							style="width: 100px; height: 30px;display: inline; color: #202021 !important;">
						<img id="test" style="margin-bottom: 2px;" width="70px" height="31px" 
							src="${pageContext.request.contextPath}/getimage" /> 
						<span id="yzm" style="padding-left: 2px; font-size: 12px; cursor: pointer"
							onclick="$('#test').attr('src','${pageContext.request.contextPath}/getimage?'+Math.random())">换一换</span>
					</div>
					<div id="msg" style="display: none">
						<span style="color: red">密码错误！您还能输入${number}次密码</span>
					</div>
					<div>
						<a style="color: white;" href="JavaScript:alert('重置密码请联系系统管理员，谢谢！');">忘记密码了？</a> 
					</div>
					<a href="javascript:void(0)" id="btnLogin" onclick="login()" class="btn btn-success btn-block">登录</a>
				</form>
			</div>
		</div>
		<div class="signup-footer">
			<div class="pull-left">
				<font style="color: #000">版权所有 &copy; 2019-&nbsp;&nbsp;&nbsp;&nbsp; <a href="http://www.qcode365.com" target=_blank>快码猿</a>
				</font>
			</div>
		</div>
	</div>
</body>
<%@ include file="../base/baseJs.jsp"%>
<script src="${pageContext.request.contextPath}/resources/plugins/jquery/jquery-md5.js"></script>
<script type="text/javascript">
	function initValidateRules() {
		//表单验证设置-设置插件validator的默认参数
		var e = "<i class='fa fa-times-circle'></i> ";
		$("#inputForm").validate({
			rules : {
				logname : {
					required : true,
					maxlength : 50
				},
				password : {
					required : true,
					maxlength : 256
				},
			},
			messages : {
				logname : {
					required : e + "请输入用户名",
					maxlength : e + "用户名不能超过50个字符"
				},
				password : {
					required : e + "请输入用户名",
					maxlength : e + "密码长度不能超过256个字符"
				},
			}
		});
	}
	function login() {
		if (!$("#inputForm").valid()) {
			return false;
		}
		/* if($("#verify_code").val().trim()==""){
			alert("请输入验证码!");
			return false;
		} */
		var params = $("#inputForm").formToObject();
		params.password = $.md5(params.password);
		var url = $("#inputForm").attr("action");//请求URL
		httpRequest.ajax({
			url : url,
			data : params,//请求参数
			loading : true,//是否显示loading加载框
			success : function(data, status) {
				if (data == null || data.statusCode == null) {
					layer.msg("提交失败，请重试");
				}
				if (data.statusCode == ServerResults.Success) {
					window.location.href = "../index.jsp";//先从后台跳转
					return false;
				}
				if (data.statusCode == 400) {
					$("#yzm").click();
				}
				layer.msg(data.msg);
			}
		});
	}
	function bind() {
		$("body").bind("keyup", function(e) {
			// 兼容FF和IE和Opera
			var theEvent = e || window.event;
			var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
			if (code == 13) {
				//回车执行查询
				$("#btnLogin").click();
			}
		});
	}
	$(function() {
		//初始化表单验证规则
		initValidateRules();
		bind();
		if (myBrowser()) {
			alert("你的浏览器版本较低，建议升级到IE9或使用 Firefox浏览器。");
		}
	});
	function myBrowser() {
		var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
		var isOpera = userAgent.indexOf("Opera") > -1; //判断是否Opera浏览器
		var isIE = userAgent.indexOf("compatible") > -1
				&& userAgent.indexOf("MSIE") > -1 && !isOpera; //判断是否IE浏览器
		var isFF = userAgent.indexOf("Firefox") > -1; //判断是否Firefox浏览器
		var isSafari = userAgent.indexOf("Safari") > -1; //判断是否Safari浏览器
		if (isIE) {
			var IE5 = IE55 = IE6 = IE7 = IE8 = false;
			var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
			reIE.test(userAgent);
			var fIEVersion = parseFloat(RegExp["$1"]);
			IE55 = fIEVersion == 5.5;
			IE6 = fIEVersion == 6.0;
			IE7 = fIEVersion == 7.0;
			IE8 = fIEVersion == 8.0;
			if (IE55) {
				return true;//"IE55";
			}
			if (IE6) {
				return true;//"IE6";
			}
			if (IE7) {
				return true;//"IE7";
			}
			if (IE8) {
				return true;//"IE8";
			}

			return false;
		}
	}
</script>
</html>