<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	List<PollEntry> lists = (List<PollEntry>) request.getAttribute("pollEntry");
	int pollNum = (Integer) request.getAttribute("totalPoll");
	int pg = (Integer) request.getAttribute("pg");
	String paging = (String) request.getAttribute("paging");
%>
<a class="btn" href="javascript:goForm();">등록</a>


<form name="qform" action="pollForm.do" method="get">
<input type="hidden" name="pollId" />
<input type="hidden" name="mode"/>
</form>

<form name="listForm" action="list.do" method="get">
<input type="hidden" name="pg" value="<%=pg %>" />
</form>

<div class="well">
	<table class="table">
	<col width="60"/>
	<col width="120"/>
	<col width="120"/>
	<thead>
	<tr>
		<th>선택</th>
		<th>번호</th>
		<th>설문</th>
		<th>등록일</th>
	</tr>
	</thead>
	<tbody>
	<%
		for(PollEntry list : lists){
		// for reply articles
	%>
		<tr>
			<td><input type="checkbox" name="chkEntry" value="<%=list.getEntryId() %>"></td>
			<td><%=pollNum%></td>
			<td><%=list.getTitle()%></td>
		<td>
		<%
			if( list.getType().equals("M")){
		%>
			객관식
		<%
			}else if( list.getType().equals("D")){
		%>
			객관식(중복)			
		<%
			}else{
		%>
			주관식
		<%}%>
				</td>
				<td><%=list.getRegDate() %></td>
			</tr>
			
	<%	
		pollNum--;
		}
	%>
	
		</td>
		</tr>
	</tbody>
	</table>
</div>
<div class="pagination">
	<ul>
		<%=paging %>
	</ul>
</div>
<script>
<!--
function goForm(pollId)
{	
	var f = document.forms.qform;
	
	if( pollId == undefined )
	{
		pollId = '0';
		f.mode.value = "add";
	}
	else
	{
		f.mode.value = "modify";
	}
	f.pollId.value = pollId;
	f.submit();
}

function goList(pg)
{
	var f = document.forms.listForm;
	
	f.pg.value = pg;
	f.submit();
}

-->
</script>