<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>数据词典 sort</title>
<%@ include file="../../base/baseCss.jsp"%>
</head>
<body>
<br><br>
       <div class="wrapper wrapper-content animated fadeIn">
           <form id="inputForm" method="post" action="sortedSysBasedata">
               <input name="ids" id="ids" type="hidden" />
               <table align=center cellspacing="5" cellpadding="5" border="0"
                   width="80%" height="90%">
                   <tr>
                       <td valign="middle" align="right">

                           <select size="20" multiple="multiple" class="sort" id="sorting" title="双击进入下级"
                               name="sorting" style="width: 250px; height: 300px" ondblclick="javascript:getChilds(this.value)">

                               <c:forEach items="${sysbasedatalist}" var="sysbasedata">
                                   <option value="${sysbasedata.param_id}">
                                       ${sysbasedata.ptext}
                                   </option>
                                   <!-- 此处字段名称可能要修改 -->
                               </c:forEach>

                           </select>
                       </td>
                       <td align="center" style="width: 150px">
                           <label>
                               <input type="button" value="&gt;" onclick="addSortItem()"
                                   style="width: 100px; height: 30px" />
                           </label>
                           <label>
                               <input type="button" value="&gt;&gt;" onclick="addAllSortItem()"
                                   style="width: 100px; height: 30px" />
                           </label>
                           <label>
                               <input type="button" value="&lt;" onclick="removeSortItem()"
                                   style="width: 100px; height: 30px" />
                           </label>
                           <label>
                               <input type="button" value="&lt;&lt;"
                                   onclick="removeAllSortItem()"
                                   style="width: 100px; height: 30px" />
                           </label>
                       </td>
                       <td valign="middle">

                           <select size="20" multiple="multiple" class="sort" id="sorted"
                               name="sorted" style="width: 250px; height: 300px">
                           </select>
                       </td>
                   </tr>
               </table>

            <div class="fixed layui-layer-btn">
               <a class="layui-layer-btn0" onclick="BtnSave()">提交</a> 
               <a class="layui-layer-btn3" onclick="BtnReset()">重置</a> 
               <a class="layui-layer-btn0" onclick="closeCurrent()">关闭</a>
            </div>
       </form>
   </div>
</body>
<%@ include file="../../base/baseJs.jsp"%>
       <script type="text/javascript">
              function addSortItem(){
               $("#sorting option:selected").each(function(){
                   $(this).attr({selected:false});
                   $(this).appendTo("#sorted");
               });
               }
               function addAllSortItem(){
                   $("#sorting option").each(function(){  
                   $(this).appendTo("#sorted");
               });
               }
               function removeSortItem(){
               $("#sorted option:selected").each(function(){
                   $(this).attr({selected:false});
                   $(this).appendTo("#sorting");
               });
               }
               function removeAllSortItem(){
                   $("#sorted option").each(function(){   
                   $(this).appendTo("#sorting");
               });
               }
               function checkSelect(){
                   if($("#sorting option").size()>0){
                       alertMsg.error("数据不完整！");
                   return false;
                   }
                   var values = [];
                   $("#sorted option").each(function(){
                   values[values.length]=this.value;  
                   });
                   $("#ids").val(values.join(","));
                   return true;
               }
       </script>
<script type="text/javascript">
  
   function ChkValue()
   {
       var values = [];
        $("#sorted option").each(function(){
              values[values.length]=this.value;  
        });
        $("#ids").val(values.join(","));
       
   }
//保存
function BtnSave()
{
//todo:其它验证   
 ChkValue();
if($("#ids").val()==""){
    alert("请先从左边选择记录到右边");
    return false;
 }
var params=$("#inputForm").formToObject();   
 //var strJson=JSON.stringify(params);
 var index = layer.load(2);
 httpRequest.ajax({
     url: $("#inputForm").attr("action"),//请求URL
     data: params,//请求参数
     loading: true,//是否显示loading加载框
     success: function (data, status) {
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

     }
 });

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
   window.location.reload();
}


function getChilds(sltvalue)
{
	  // alert($("#sorting").find("option:selected").text());
	  var sltobj=document.getElementById("sorting");
	  if ($("#sorting").find("option:selected").text()=="[返回上级]")
	  {
		  $.post("getSysBaseDataChilds?dowhat=up&pid="+sltvalue,$("#inputForm").serialize(),function(data){
	           if(data.statusCode=="200"){
	             //  alert(data.list.length);
	               if (data.list.length>0)
	            	   { 
						sltobj.length=0;
						 sltobj.options[sltobj.length] = new Option("[返回上级]", data.list[0].param_id );
						for (var i=0;i<data.list.length;i++)
						{
							sltobj.options[sltobj.length] = new Option(data.list[i].ptext, data.list[i].param_id);
						}
	            	   }
	            } 
	       });
	  }
	  else
		  {
		   $.post("getSysBaseDataChilds?dowhat=down&pid="+sltvalue,$("#inputForm").serialize(),function(data){
	           if(data.statusCode=="200"){
	              // alert(data.list.length);
	               if (data.list.length>0)
	            	   {
						sltobj.length=0;
						sltobj.options[sltobj.length] = new Option("[返回上级]", data.list[0].param_id );
						for (var i=0;i<data.list.length;i++)
						{
							sltobj.options[sltobj.length] = new Option(data.list[i].ptext, data.list[i].param_id);
						}
	            	   }
	            } 
	       });
		  }
}


</script>
</html>

