<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	Member adminMember = (Member) session.getAttribute("adminMember");
%>
<div class="navbar">
	<div class="navbar-inner">
		<ul class="nav pull-right">
			<li><a href="/admin/logout.do" class="hidden-phone visible-tablet visible-desktop" role="button">Logout</a></li>
			<li id="fat-menu" class="dropdown">
			<a href="#" role="button" class="dropdown-toggle" data-toggle="dropdown">
			<i class="icon-user"></i> <%=adminMember.getName() %>
			</a>			
			</li>
		</ul>
		<a class="brand" href="/admin/multimedia/multimediaList.do"><span class="first">Teaching</span><span class="second">Hub</span></a>
	</div>
</div>