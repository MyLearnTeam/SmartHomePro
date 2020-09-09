<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>数据词典 view</title>
<%@ include file="../../base/baseCss.jsp"%>
</head>
<body style="overflow: auto; height: 100%; padding: 0px">
   <div class="wrapper wrapper-content animated fadeIn">
       <form id="inputForm" name="inputForm" action="" onsubmit="return false">
       <input type="hidden" value="${sysbasedata.param_id }" id="param_id" name="param_id"/>
           <table class="parent-table">
             <tr  >
               <td align="right"  width="30%"><label   ID="lbl_parent_id">上级</label></td>
                <td>${parent_id_cn}</td>
             </tr>
             <tr  >
               <td align="right"  width="30%"><label   ID="lbl_param_token">数据类型标识</label></td>
               <td>${sysbasedata.param_token}</td>
             </tr>
             <tr  >
               <td align="right"  width="30%"><label   ID="lbl_pvalue">数据值</label></td>
               <td>${sysbasedata.pvalue}</td>
             </tr>
             <tr  >
               <td align="right"  width="30%"><label   ID="lbl_ptext">数据中文</label></td>
               <td>${sysbasedata.ptext}</td>
             </tr>
             <tr  >
               <td align="right"  width="30%"><label   ID="lbl_remark">说明</label></td>
               <td valign=top height="200px">${sysbasedata.remark}</td>
             </tr>
             <tr  >
               <td align="right"  width="30%"><label   ID="lbl_sort">排序</label></td>
               <td>${sysbasedata.sort}</td>
             </tr>
         </table>
           <div class="fixed layui-layer-btn">
               <a class="layui-layer-btn0" onclick="closeCurrent()">关闭</a>
           </div>
       </form>
   </div>
</body>
<%@ include file="../../base/baseJs.jsp"%>
<script type="text/javascript">
   $(document).ready(function() {
   });
</script>
</html>

