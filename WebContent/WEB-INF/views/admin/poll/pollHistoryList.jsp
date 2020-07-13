<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	int pg = (Integer) request.getAttribute("pg");
	String paging = (String) request.getAttribute("paging");
	String findStr = (String) request.getAttribute("findStr");
	int pollNum = (Integer) request.getAttribute("pollNum");
	List<PollResponse> lists = (List<PollResponse>) request.getAttribute("lists");
	String nowTime = (String) request.getAttribute("nowTime");
%>
<form name="qform" action="" method="post">
<input type="hidden" name="pollId" value=""/>
</form>

<form name="listForm" action="pollHistoryList.do" method="get">
<input type="hidden" name="pg" value="<%=pg%>" />
<input type="hidden" name="findStr" value="<%=findStr%>"/>
</form>

<div class="well">
	<table class="table">
	<col width="80"/>
	<col width="80"/>
	<col width="150"/>
	<col width="180"/>
	<col width="100"/>
	<col width="100"/>
	<col width="80"/>
	<col width="80"/>
	<thead>
	<tr>
		<th>번호</th>
		<th>대상</th>
		<th>제목</th>
		<th>기간</th>
		<th>작성자</th>
		<th>등록일</th>
		<th>상태</th>
		<th>참여</th>
	</tr> 
	</thead>
	<tbody>
	<%
		if( pollNum > 0 ){
			for(PollResponse pollRes : lists){
			// for reply articles
	%>
			<tr>
				<td><%=pollNum %></td>
				<td>TODO : <%=pollRes.getTarget()%></td>
				<td><%=pollRes.getSubject() %></td>
				<td><%=pollRes.getStartDate()%> ~ <%=pollRes.getEndDate()%></td>
				<td><%=pollRes.getRegId() %></td>
				<td><%=pollRes.getRegDate() %></td>
				<td><a href="javascript:goDetail('<%=pollRes.getPollId() %>')"><%if ( pollRes.getProgress().equals("Y") ){ %>진행중<%}else{%>종료<%}%></a></td>
				<td><a href="javascript:goUserList('<%=pollRes.getPollId() %>')"><%=pollRes.getUserCnt() %></a></td>
			</tr>
	<%	
			pollNum--;
			}
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

function goUserList(pollId)
{
	var f = document.forms.qform;
	if( goUserList != undefined)
	{
		f.pollId.value = pollId;
	}
	f.action = "pollHistoryUserlist.do";
	f.submit();
}

function goDetail(pollId)
{
	var f = document.forms.qform;
	if( goUserList != undefined)
	{
		f.pollId.value = pollId;
	}
	f.action = "pollHistoryDetail.do";
	f.submit();
}
-->
</script>