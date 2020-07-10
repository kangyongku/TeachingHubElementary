<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	String boardSkinPath = (String) request.getAttribute("boardSkinPath");
	int boardType = Common.getParameter(request, "boardType", 1);
	BoardConfig boardConfig = (BoardConfig) request.getAttribute("boardConfig");
	System.out.println("utilize boardSkinPath === " + boardSkinPath);
%>
<script type="text/javascript">
	if ( window.location == 'http://dic.kumsung.co.kr/open/share/utilize.do' ||  window.location == 'https://dic.kumsung.co.kr/open/share/utilize.do') {
		location.href="https://thub.kumsung.co.kr";
	}
</script>
<div class="inner_container">
	<tiles:insertDefinition name="elementarySidebarCommunication"/> 
	<div class="sub_content">
		<h2 class="page_title">티칭허브&#8314; 자료</h2>
		<p>티칭허브가 드리는 이달의 선물. 월별에 맞는 교과 수업자료를 한눈에 쉽게!</p>
		<tiles:insertDefinition name="<%=boardSkinPath %>"/>
	</div>
</div>