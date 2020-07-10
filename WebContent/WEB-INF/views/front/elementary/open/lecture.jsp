<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	// sidebar
	String boardSkinPath = (String) request.getAttribute("boardSkinPath");
	System.out.println("lecture boardSkinPath : "+boardSkinPath);
%>
<div class="inner_container tg_area tg01_001_page">
			
	<!-- sidebar -->
	<tiles:insertDefinition name="elementarySidebarCommunication"/>

	<div class="sub_content">
          <h2 class="page_title">수업 활용 영상</h2>
          <p>마음으로 느끼는 다양한 생각! &nbsp; 여러분야의 다양한 아이디어와 생각, 명강의를 한 곳에서 볼 수 있는 공간입니다.</p>
          <!-- tab -->
			<tiles:insertDefinition name="<%=boardSkinPath %>"/>
         <!--// tab -->
   </div>
	
</div>