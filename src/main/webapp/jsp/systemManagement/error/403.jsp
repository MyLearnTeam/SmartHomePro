<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>404 页面</title>
    <link rel="shortcut icon" href="favicon.ico"> 
	<%@ include file="../../../base/baseCss.jsp"%>
</head>
<body class="gray-bg">
    <div class="middle-box text-center animated fadeInDown">
        <h1>403</h1>
        <h3 class="font-bold">抱歉，您没有权限做此操作~！</h3>
        <div style="display: none;">${msg}</div>
        <div class="error-desc">
            <br/>请联系管理员!
        </div>
    </div>
</body>
<%@ include file="../../../base/baseJs.jsp"%>
</html>