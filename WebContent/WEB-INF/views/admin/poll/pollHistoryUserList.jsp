<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	Poll poll = (Poll) request.getAttribute("poll");
	List<PollResponse> lists = (List<PollResponse>) request.getAttribute("lists");
	int pollNum = (Integer) request.getAttribute("pollNum");
	int pollId = (Integer) request.getAttribute("pollId");
	int pg = (Integer) request.getAttribute("pg");
	String paging = (String) request.getAttribute("paging");	
%>

<form name="listForm" action="pollHistoryUserlist.do" method="post">
<input type="hidden" name="pg" value="<%=pg%>" />
<input type="hidden" name="pollId" value="<%=pollId%>" />
</form>

<div class="well">
	<table class="table">
	<col width="150"/>
	<col width="150"/>
	<col width="150"/>
	<col width="150"/>
	<col width="150"/>
	<thead>
	<tr>
		<th>학원명</th>
		<th>성명</th>
		<th>아이디</th>
		<th>구분</th>
		<th>참여일</th>
	</tr> 
	</thead>
	<tbody>
	<%
		for(PollResponse pollRes : lists){
		// for reply articles
	%>
		<tr>
			<td><%=pollRes.getSchoolName() %></td>
			<td><%=pollRes.getName() %></td>
			<td><%=pollRes.getRegId()%></td>
			<td><%=pollRes.getJobName()%></td>
			<td><%=pollRes.getRegDate() %></td>
		</tr>
	<%	
		}
	%>
	</tbody>
	</table>
</div>
<div class="pagination">
	<ul>
		<%=paging %>
	</ul>
</div>


<!-- 스크립트 영역 -->
<script>
<!--

function goList(pg)
{
	var f = document.forms.listForm;
	
	f.pg.value = pg;
	f.submit();
}
-->
</script>