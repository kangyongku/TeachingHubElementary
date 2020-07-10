<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	// sidebar
	String boardSkinPath = (String) request.getAttribute("boardSkinPath");
	System.out.println("travel boardSkinPath : "+boardSkinPath);
%>
<div class="inner_container">
	<!-- sidebar -->
	<tiles:insertDefinition name="elementarySidebarHappy"/>
	<!-- sub_content -->
	<div class="sub_content">
		<h2 class="page_title">추천 여행</h2>
		<p>몸이 건강해지고 마음까지 풍요로워지는 자연으로 여행을 떠나보세요</p>
		<tiles:insertDefinition name="<%=boardSkinPath %>"/>
	</div>
</div>