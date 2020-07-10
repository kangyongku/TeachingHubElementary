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
		<h2 class="page_title">생활의 지혜</h2>
		<p>일상 생활에서 알아두면 편리한 여러 가지 알찬 상식과 정보를 모았습니다.</p>
		<tiles:insertDefinition name="<%=boardSkinPath %>"/>
	</div>
</div>