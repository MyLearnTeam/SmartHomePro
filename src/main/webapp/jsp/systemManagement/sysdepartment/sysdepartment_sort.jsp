<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="decorator" content="default" />
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
	<div class="wrapper wrapper-content animated fadeIn">
		<form id="inputForm" method="post" action="sortedSysDepartment">
			<input name="ids" id="ids" type="hidden" />
			<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
				<tr>
					<td valign="middle" align="right"><select ondblclick="addSortItem()" size="20" multiple="multiple" class="sort" id="sorting" name="sorting"
						style="width: 250px; height: 300px">

							<c:forEach items="${sysdepartmentlist}" var="sysdepartment">
								<option value="${sysdepartment.depart_id}">${sysdepartment.depart_name}</option>
								<!-- 此处字段名称可能要修改 -->
							</c:forEach>

					</select></td>
					<td align="center" style="width: 150px"><label> <input type="button" value="&gt;" onclick="addSortItem()" />
					</label> <label> <input type="button" value="&gt;&gt;" onclick="addAllSortItem()" />
					</label> <label> <input type="button" value="&lt;" onclick="removeSortItem()" />
					</label> <label> <input type="button" value="&lt;&lt;" onclick="removeAllSortItem()" />
					</label></td>
					<td valign="middle"><select ondblclick="removeSortItem()" size="20" multiple="multiple" class="sort" id="sorted" name="sorted" style="width: 250px; height: 300px">
					</select></td>
				</tr>
			</table>

		</form>
	</div>
</body>
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
       return true;
       
   }
 
  </script>