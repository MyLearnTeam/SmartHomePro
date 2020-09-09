<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<ul>
	<c:forEach var="catalog" items="${topCatalogList}">
		<li <c:if test="${not empty catalog.childList && catalog.childList.size()>0 }">class='nav-has-sub'</c:if>>
			<c:if test="${not empty catalog.url}">
				<a class="J_menuItem" href="${catalog.url}?catalog_id=${catalog.catalog_id}">${catalog.catalog }</a>
			</c:if>
			<c:if test="${empty catalog.url}">
				<a class="J_menuItem" href="javascript:void(0)">${catalog.catalog }</a>
			</c:if>
			<c:if test="${not empty catalog.childList && fn:length(catalog.childList) > 0}">
				<c:set var="topCatalogList" value="${catalog.childList}" scope="request" />
				<c:import url="/base/topTree.jsp" />
			</c:if>
		</li>
	</c:forEach>
</ul>
