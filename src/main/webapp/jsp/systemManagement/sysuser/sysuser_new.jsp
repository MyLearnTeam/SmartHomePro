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
<script src="${pageContext.request.contextPath}/resources/plugins/jquery/jquery-md5.js"></script>
</head>
<body style="overflow: auto; height: 100%; padding: 0px">
	<div class="wrapper wrapper-content animated fadeIn">
		<form id="inputForm" name="inputForm" action="saveSysUser">
			<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
				<tr height=25>
					<td align="right" width="25%"><label ID="lbl_login_name" style="color: #0066FF">登录名</label><font color=red>*</font></Td>
					<td><input type="text" id="login_name" name="login_name" maxlength=20 class="form-control" required="required"></td>
				</Tr>
				<tr height=25>
					<td align="right" width="25%"><label ID="lbl_password" style="color: #0066FF">密码</label><font color=red>*</font></Td>
					<td><input type="password" id="password" placeholder="密码长度至少8位，并且必须由 数字、字母、特殊符号进行组合" name="password" maxlength=128 class="form-control" required="required"> <span
						id="passstrength"></span></td>
				</Tr>
				<tr height=25>
					<td align="right" width="25%"><label ID="lbl_truename" style="color: #0066FF">真实姓名</label><font color=red>*</font></Td>
					<td><input type="text" id="truename" name="truename" maxlength=30 class="form-control" required="required"></td>
				</Tr>
				<tr height=25>
					<td align="right" width="25%"><label ID="lbl_usercode" style="color: #0066FF">用户代码</label></Td>
					<td><input type="text" id="usercode" name="usercode" maxlength=20 class="form-control"></td>
				</Tr>
				<tr height=25>
					<td align="right" width="25%"><label ID="lbl_producttype_id" style="color: #0066FF">所属机构部门</label></Td>
					<td><input type="text" style="width: 50%;display: inline;" class="form-control" name="depart_name" id="depart_name" value="" readonly="readonly">
						<input type="hidden" name="depart_id" id="depart_id" value="-1"> 
						[<a href="javascript:void(0);" onclick="selectDepart()">选择</a>]</td>
				</Tr>
				<tr height=25>
					<td align="right" width="25%"><label ID="lbl_photo" style="color: #0066FF">头像地址</label></Td>
					<td><input type="text" id="photo" name="photo" maxlength=128 class="form-control"></td>
				</Tr>
				<tr height=25>
					<td align="right" width="25%"><label ID="lbl_mobile" style="color: #0066FF">手机</label></Td>
					<td><input type="text" id="mobile" name="mobile" maxlength=12 class="form-control"></td>
				</Tr>
				<tr height=25>
					<td align="right" width="25%"><label ID="lbl_phone" style="color: #0066FF">座机固话</label></Td>
					<td><input type="text" id="phone" name="phone" maxlength=30 class="form-control"></td>
				</Tr>
				<tr height=25>
					<td align="right" width="25%"><label ID="lbl_job_title" style="color: #0066FF">职位</label></Td>
					<td><input type="text" id="job_title" name="job_title" maxlength=45 class="form-control"></td>
				</Tr>
				<tr height=25>
					<td align="right" width="25%"><label ID="lbl_degree" style="color: #0066FF">职称</label></Td>
					<td><input type="text" id="degree" name="degree" maxlength=45 class="form-control"></td>
				</Tr>
				<tr height=25>
					<td align="right" width="25%"><label ID="lbl_email" style="color: #0066FF">电子邮箱</label></Td>
					<td><input type="text" id="email" name="email" maxlength=50 class="form-control"></td>
				</Tr>
				<tr height=25>
					<td align="right" width="25%"><label ID="lbl_remark" style="color: #0066FF">备注</label></Td>
					<td><textarea id="remark" name="remark" rows="5" class="form-control"></textarea></td>
				</Tr>
			</table>
			<div class="fixed layui-layer-btn">
				<a class="layui-layer-btn0" onclick="save()">保存</a> 
				<a class="layui-layer-btn3" onclick="reset()">重置</a> 
				<a class="layui-layer-btn0" onclick="closeCurrent()">关闭</a>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript">
    //密码校验是否通过
    var passVlida=false;
    function reset() {
		inputForm.reset();
	}
    function save(){
        if ($("#depart_id").val()=="-1"){
            layer.msg("必须选择机构部门！",{icon:2});
            return false;
        }
        if($("#inputForm").valid()){
            //$('button').attr('disabled',"true");
            //$("#password").val($.md5($("#password").val()));
            $.post($("#inputForm").attr("action"),$("#inputForm").serialize(),function(data){
                $("button").removeAttr("disabled");
                if(data.statusCode=="200"){
                	top.layer.msg("保存成功", {icon : 1,time : 1000 });
                    getParentWindow("sysUser_list").refreshGrid();
					closeCurrent();
                }else{
                    top.layer.msg("保存失败",{icon:2});
                }
            });
        }
    }
    function selectDepart() {
        openDialog("选择机构部门", "sltSysDepartment","500px","450px");	
    }
  	//设值
	function setValue(parent_id,parentname){
		$("#depart_id").val(parent_id);
		$("#depart_name").val(parentname);
	}
    /* $(function(){
        $('#password').blur(function(e) {
            var mediumRegex = new RegExp("^(?=.{8,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g");
            if (mediumRegex.test($(this).val())) {
                passVlida=true;
                $('#passstrength').hide();
            } else {
                $('#passstrength').show();
                $('#passstrength').css("color","red");
                $('#passstrength').html('密码过于简单!');
                passVlida=false;
            }
            return true;
        });
    }); */
</script>
</html>

