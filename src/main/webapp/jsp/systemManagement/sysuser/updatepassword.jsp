<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title></title>
<style>
body {
	padding-bottom: 50px;
}

.fixed {
	position: fixed;
	left: 0px;
	bottom: 0px;
	width: 100%;
	height: 50px;
	background-color: White;
	z-index: 9999;
}
</style>
<%@ include file="../../../base/baseCss.jsp"%>
<%@ include file="../../../base/baseJs.jsp"%>
<script src="${pageContext.request.contextPath}/resources/plugins/jquery/jquery-md5.js"></script>
</head>
<body style="overflow: auto; height: 100%; padding: 0px">
	<div class="wrapper wrapper-content animated fadeIn">
		<form id="inputForm" name="inputForm" action="updatePassWordOK">
			<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
				<tr height=25>
					<td align="right" width="30%"><label ID="lbl_pl_area" style="color: #0066FF">旧密码：</label></Td>
					<td><input type="password" id="old_password" name="old_password" value="" class="form-control"></td>
				</Tr>
				<tr height=25>
					<td align="right" width="30%"><label ID="lbl_pl_area" style="color: #0066FF">新密码：</label></Td>
					<td><input type="password" id="password" name="password" value="" class="form-control"></td>
				</Tr>
				<tr height=25>
					<td align="right" width="30%"><label ID="lbl_pl_area" style="color: #0066FF">确认新密码：</label></Td>
					<td><input type="password" id="password2" name="password2" value="" class="form-control"></td>
				</Tr>
			</table>
		</form>
		<div class="fixed" style="padding: 10px 0;">
			<div class="layui-layer-btn layui-layer-btn-" style="margin-bottom: -15px">
				<button class=" btn btn-default layui-layer-btn0" onclick="javascript:BtnSave();">保存</button>
				<button class=" layui-layer-btn3 btn btn-default" onclick="javascript:BtnClose();">关闭</button>
				<button class=" layui-layer-btn3 btn btn-default" onclick="javascript:BtnReset();">重置</button>
			</div>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
    //保存
    function BtnSave()
    {
//todo:其它验证   
		$("#old_password").val($.md5($("#old_password").val()));
		$("#password").val($.md5($("#password").val()));
		$("#password2").val($.md5($("#password2").val()));
        if($("#inputForm").valid()){
            $('button').attr('disabled',"true");
            $.post($("#inputForm").attr("action"),$("#inputForm").serialize(),function(data){
                $("button").removeAttr("disabled");
                if(data.statusCode=="200"){
                    top.layer.msg("保存成功",{icon:1,time: 1000});
                    parent.$('.J_mainContent .J_iframe').each(function () {
                        if ($(this).data('id').indexOf("listBPolicy?")>-1) {
                            $(window.parent.document).contents().find("#"+$(this).attr("name"))[0].contentWindow.refreshtablelist();
                        }
                    });
                    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                    parent.layer.close(index);
                }else{
                    top.layer.msg("保存失败:"+data.errMsg,{icon:2});
                }
            });
        }
    }
    //关闭
    function BtnClose()
    {
        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        parent.layer.close(index);
    }
    //重置
    function BtnReset()
    {
        inputForm.reset();
    }
</script>
