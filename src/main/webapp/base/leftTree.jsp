<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:if test="${level == 1}">
	<!-- 第1层菜单 -->
	<c:set var="levelClass" value="" scope="request" />
</c:if>
<c:if test="${level == 2}">
	<!-- 第2层菜单 -->
	<c:set var="levelClass" value="nav-second-level" scope="request" />
</c:if>
<c:if test="${level == 3}">
	<!-- 第3层菜单 -->
	<c:set var="levelClass" value="nav-third-level" scope="request" />
</c:if>

<ul class="nav ${levelClass} ${uindexClass}" <c:if test="${level==1 }">id="side-menu"</c:if> >
	<c:forEach var="catalog" items="${leftCatalogList}" varStatus="uindex">
		<!-- 设置默认图标--- start -->
		<c:if test="${empty catalog.img }">
			<c:if test="${not empty catalog.childList && fn:length(catalog.childList) > 0}">
				<c:set var="img" value="fa fa-folder-open-o" scope="request" />
			</c:if>
			<c:if test="${empty catalog.childList || fn:length(catalog.childList) == 0}">
				<c:set var="img" value="fa fa-file-o" scope="request" />
			</c:if>
		</c:if>
		<c:if test="${not empty catalog.img }">
				<c:set var="img" value="${catalog.img }" scope="request" />				
		</c:if>
		<!-- 设置默认图标--- end -->
		
		<!-- 设置跳转地址---start -->
		<c:if test="${not empty catalog.url}">
			<!-- url不为空 -->
			<c:set var="ahref" value="${catalog.url}?catalog_id=${catalog.catalog_id}" scope="request" />	
		</c:if>
		<c:if test="${empty catalog.url}">
			<!-- url null -->
			<c:set var="ahref" value="javascript:void(0)" scope="request" />	
		</c:if>
		<!-- 设置跳转地址---end -->
		
		<c:if test="${not empty catalog.childList && fn:length(catalog.childList) > 0}">
			<!-- 有子元素 -->
			<c:set var="urlClass" value="" scope="request" />	
		</c:if>
		<c:if test="${empty catalog.childList || fn:length(catalog.childList) == 0}">
			<!-- 无子元素 -->
			<c:set var="urlClass" value="J_menuItem_li_child" scope="request" />	
		</c:if>
				
		<c:set var="uindexClass" value="" scope="request" />
		<c:set var="lindexClass" value="" scope="request" />
		<c:if test="${uindex.index==0}">
			<!-- 默认展开第一个菜单 -->
			<c:set var="uindexClass" value="in" scope="request" />
			<c:set var="lindexClass" value="active" scope="request" />
		</c:if>
		
		<li class='J_menuItem_li ${lindexClass}' id='li-${catalog.catalog_id}' title='${catalog.catalog }'>
			<a class="J_menuItem ${urlClass}" id='mnu-${catalog.catalog_id}' href="${ahref}">
				<i class='${img}'></i>${catalog.catalog }<c:if test="${not empty catalog.childList && fn:length(catalog.childList) > 0}">
					<span class="fa arrow"></span>
				</c:if>
			</a>
			<c:if test="${not empty catalog.childList && fn:length(catalog.childList) > 0}">
				<c:set var="leftCatalogList" value="${catalog.childList}" scope="request" />
				<c:set var="level" value="${level+1}" scope="request" />
				<c:import url="/base/leftTree.jsp" />
			</c:if>
		</li>
	</c:forEach>
</ul>
<c:set var="level" value="${level - 1}" scope="request" />
