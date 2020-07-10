<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	// sidebar
	String boardSkinPath = (String) request.getAttribute("boardSkinPath");
	System.out.println("career boardSkinPath : "+boardSkinPath);
%>
<div class="inner_container">
	<!-- nav_box-->
	<tiles:insertDefinition name="elementarySidebarFreestudy"/> 
	<!--// nav_box-->

	<div class="sub_content">
		<h2 class="page_title">진로 교육</h2>
		<p>적성과 미래에 대한 탐색의 첫 걸음! 지식·경쟁 중심의 교육보다 자기 주도적 학습 교육을 지원합니다.</p>
		<!-- 진로교육 tab -->
			<tiles:insertDefinition name="<%=boardSkinPath %>"/>
		<!--// 진로교육 tab -->
	</div>
</div>