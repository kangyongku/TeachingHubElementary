<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="kr.co.kumsung.thub.exception.CommonException" %>    
<%
	CommonException exception = (CommonException) request.getAttribute("exception");
%>
<script type="text/javascript" src="/assets/admin/lib/jquery-1.8.1.min.js"></script>
<% if( exception.getActionType() == 0 ){ %>
		<script type="text/javascript">
		<!--
		$(document).ready(function(){
			alert('${exception.message}');history.go(-1);
		});
		//-->
		</script>
<% }else if(exception.getActionType() == 1 ){ %>
	 	<script type="text/javascript">
		<!--
		$(document).ready(function(){
			alert('${exception.message}');
			location.href='<%=exception.getLocation()%>';
		});
		//-->
		</script>
<% }else if(exception.getActionType() == 2 ){ %>
		<script type="text/javascript">
		<!--
		$(document).ready(function(){
			parent.location.reload();
		});
		//-->
		</script>
<% } %>