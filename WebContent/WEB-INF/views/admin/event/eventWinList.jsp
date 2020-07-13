<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	List<EventWin> eventWinList = (List<EventWin>) request.getAttribute("eventWinList");
	int eventWinNum = (Integer) request.getAttribute("eventWinNum");
	int pg = (Integer)request.getAttribute("pg");
	
	String findStr = (String) request.getAttribute("findStr");
	String findMethod = (String) request.getAttribute("findMethod");
	String paging = (String) request.getAttribute("paging");
%>
<form name="qform" method="post">
	<input type="hidden" name="mode"/>
	<input type="hidden" name="winId" />
</form>
	
<form name="lform" method="post">
	<input type="hidden" name="pg" value="<%=pg %>" />
	<!-- 검색 테이블 -->
	<div>
		<table class="table table-bordered">
			<col width="80" />
			<col width="*" />
			<tbody>
				<tr>
					<th rowspan="2">검색</th>
					<td>
						기본검색 :
						<select name="findMethod" style="width:100px;" >
							<option value="">전체</option>
							<option value="ft" <%if(findMethod.equals("ft")){ %>selected<%} %>>제목</option>
							<option value="fi" <%if(findMethod.equals("fi")){ %>selected<%} %>>작성자</option>
						</select>
						<input type="text" name="findStr" value="<%=findStr %>" />
						<a href="javascript:goList();" class="btn">검색</a> 
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</form>

<!-- 본문 테이블 시작 -->
<div class="well">
	<table class="table">
		<tbody>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>등록일</th>
				<th>조회수</th>
			</tr>
		<%
			if(eventWinNum > 0){
				for(EventWin list : eventWinList){
				// for reply articles
		%>
				<tr>
					<td><%=eventWinNum%></td>
					<td><a href="javascript:goForm('<%=list.getWinId()%>')"><%=list.getTitle()%></a></td>
					<td><%=list.getModifyId()%></td>
					<td><%=list.getRegDate()%></td>
					<td><%=list.getHits()%></td>
				</tr>
		<%		
				eventWinNum--;
				}
			}else{
		%>
			<tr>
				<td colspan="9">자료가 없습니다. </td>
			</tr>
		<%	} %>
		</tbody>
	</table>
</div>

<div class="pagination">
	<ul>
		<%=paging %>
	</ul>
</div>
<div class="btn-toolbar">
	<a href="javascript:goForm();" class="btn btn-primary"><i class="icon-plus"></i>등록</a>
</div>

<!-- 스크립트 영역 -->
<script>
<!--

function goForm(winId){
	var f = document.forms.qform;
	if( winId == undefined ){
		winId = '0';
		f.mode.value = "add";
		f.winId.value = "0";
	}else{
		f.mode.value = "modify";
		f.winId.value = winId;
	}
	
	
	f.action = "eventWinForm.do"
	f.submit();
}

function goList(pg){
	var f = document.forms.lform;
	if(pg == undefined){
		f.pg.value = 1;
	}else if( pg == -1){
		$("form").each(function() { this.reset(); });
	}else{
		f.pg.value = pg;
	}
	f.action = "eventWinList.do";
	f.submit();
}
//-->
</script>
