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
	<!-- content -->
	<div class="sub_content">
		<h2 class="page_title">힐링 캠프</h2>
		<p>복잡한 일상에서 벗어나 마음의 여유를 찾을 수 있는 캠핑 장소와 장비 등을 소개합니다</p>
		<tiles:insertDefinition name="<%=boardSkinPath %>"/>
	</div>
</div>