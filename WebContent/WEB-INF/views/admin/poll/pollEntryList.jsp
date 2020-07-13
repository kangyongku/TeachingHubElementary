<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	int pg = (Integer) request.getAttribute("pg");
	String paging = (String) request.getAttribute("paging");
	String findStr = (String) request.getAttribute("findStr");
	List<PollEntry> lists = (List<PollEntry>) request.getAttribute("lists");
	int pollNum = (Integer) request.getAttribute("pollNum");
%>
<div class="btn-toolbar">
	<button class="btn btn-primary" onclick="javascript:goForm();"><i class="icon-plus"></i>설문조사 문항 등록</button>
</div>

<form name="qform" action="pollEntryForm.do" method="post">
<input type="hidden" name="entryId" />
<input type="hidden" name="pg" value="<%=pg%>" />
<input type="hidden" name="mode" value=""/>
</form>

<form name="lform" action="pollEntryList.do" method="post">
<input type="hidden" name="pg" value="<%=pg%>" />
<input type="hidden" name="findStr" value="<%=findStr%>"/>
</form>

<!-- 본문 테이블 -->
<div class="well">
	<table class="table">
	<col width="20"/>
	<col width="200"/>
	<col width="60"/>
	<col width="120"/>
	<thead>
	<tr>
		<th>번호</th>
		<th>제목</th>
		<th>형식</th>
		<th>등록일</th>
	</tr>
	</thead>
	<tbody>
	<%
		for(PollEntry list : lists){
		String blanks = "";
	%>
		<tr>
			<td><%=pollNum %></td>
			<td><a href="javascript:goForm('<%=list.getEntryId() %>')"><%=list.getTitle()%></a></td>
			<td>
	<%
		if( list.getType().equals("M")){
	%>
		객관식
	<%
		}else if( list.getType().equals("D")){
	%>
		객관식(중복선택)
			
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
	</tbody>
	</table>
</div>
<!-- end.본문테이블 -->

<div class="pagination">
	<ul>
		<%=paging %>
	</ul>
</div>

<!-- 스크립트 영역 -->
<script>
<!--
function goForm(entryId)
{	
	var f = document.forms.qform;
	
	if( entryId == undefined )
	{
		entryId = '0';
		f.mode.value = "add";
	}
	else
	{
		f.mode.value = "modify";
	}
	f.entryId.value = entryId;
	f.submit();
}

function goList(pg)
{
	var f = document.forms.lform;
	
	f.pg.value = pg;
	f.submit();
}
-->
</script>