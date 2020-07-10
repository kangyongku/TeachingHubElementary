<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	// sidebar
	String boardSkinPath = (String) request.getAttribute("boardSkinPath");
%>
<div class="inner_container">
	<!-- sidebar -->
	<tiles:insertDefinition name="elementarySidebarHappy"/>
	<!-- sub_content -->
	<div class="sub_content">
	<h2 class="page_title">웹툰</h2>
	<p>만화로 보는 재미있는 일상 이야기를 만나보세요.</p>
		<tiles:insertDefinition name="<%=boardSkinPath %>"/>
	</div>
</div>