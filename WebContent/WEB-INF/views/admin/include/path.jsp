<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String pathTitle = (String) request.getAttribute("pathTitle");
	String path = (String) request.getAttribute("path");
%>
<div class="header">
	<h1 class="page-title"><%=pathTitle %></h1>
</div>
<ul class="breadcrumb">
	<li><a href="index.html">Home</a><span class="divider">/</span></li>
	<li class="active"><%=path %></li>
</ul>