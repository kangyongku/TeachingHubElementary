<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%
	String footer = (String)request.getAttribute("footer");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<tiles:insertAttribute name="popupHead"/>
</head>

<!--[if lt IE 7 ]> <body class="ie ie6"> <![endif]-->
<!--[if IE 7 ]> <body class="ie ie7 "> <![endif]-->
<!--[if IE 8 ]> <body class="ie ie8 "> <![endif]-->
<!--[if IE 9 ]> <body class="ie ie9 "> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<body class="" >
<!--<![endif]-->
	<tiles:insertAttribute name="popupBody"/>
	<%if(footer.equals("Y")){ %>
	<tiles:insertAttribute name="popupFooter"/>
	<%}%>
</body>
</html>