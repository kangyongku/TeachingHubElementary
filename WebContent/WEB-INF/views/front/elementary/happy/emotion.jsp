<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	// sidebar
	String boardSkinPath = (String) request.getAttribute("boardSkinPath");
	System.out.println("emotion boardSkinPath : "+boardSkinPath);
%>
<div class="inner_container">
	<!-- sidebar -->
	<tiles:insertDefinition name="elementarySidebarHappy"/>
	<!-- sub_content -->
	<div class="sub_content">
		<h2 class="page_title">생활의 지혜</h2>
		<p>바쁜 생활 속 웃음과 감동을 전해주는 좋은 글을 통해 의미 있는 하루를 시작하세요.</p>
			<tiles:insertDefinition name="<%=boardSkinPath %>"/>
	</div>
</div>