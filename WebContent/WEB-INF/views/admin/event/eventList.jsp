<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	List<Event> eventList = (List<Event>) request.getAttribute("eventList");
	int eventTotalCnt = (Integer) request.getAttribute("eventTotalCnt");
	int eventNum = (Integer) request.getAttribute("eventNum");
	int pg = (Integer)request.getAttribute("pg");
	
	String findStr = (String) request.getAttribute("findStr");
	String findMethod = (String) request.getAttribute("findMethod");
	String findStartDate = (String) request.getAttribute("findStartDate");
	String findEndDate = (String) request.getAttribute("findEndDate");
	String nowTime = (String) request.getAttribute("nowTime");
	String paging = (String) request.getAttribute("paging");
	String findStatusDate = (String) request.getAttribute("findStatusDate");
%>
<form name="qform" method="post">
	<input type="hidden" name="mode"/>
	<input type="hidden" name="eventId" />
	<input type="hidden" name="winId"/>
	<input type="hidden" name="pg" value="<%=pg %>" />
</form>

<!-- 검색 테이블 영역 -->
<form name="lform" method="post">
	<input type="hidden" name="pg" value="<%=pg %>" />
	<div>
		<table class="table table-bordered" >
			<col width="80" />
			<col width="*" />
			<tbody>
				<tr>
					<th rowspan="2">검색</th>
					<td>
						이벤트 기간
						<input type="text" name="findStartDate" style="width:80px;" readonly value="<%=findStartDate %>"/>
						~
						<input type="text" name="findEndDate" style="width:80px;" readonly value="<%=findEndDate %>"/>
					</td>
					<td>
						진행 상태
						<select name="findStatusDate">
							<option value="">전체</option>
							<option value="Y" <% if( findStatusDate.equals("Y") ){%>selected<%}%>>진행중</option>
							<option value="N" <% if( findStatusDate.equals("N") ){%>selected<%}%>>진행완료</option>
							}
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						기본검색
						<select name="findMethod" style="width:100px;" >
							<option value="">전체</option>
							<option value="ft" <%if(findMethod.equals("ft")){ %>selected<%} %>>제목</option>
							<option value="fi" <%if(findMethod.equals("fi")){ %>selected<%} %>>작성자</option>
						</select>
						<input type="text" name="findStr" value="<%=findStr %>" />
						<button href="javascript:goList();" class="btn">검색</button> 
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</form>

<!-- 본문 테이블 영역 -->
<div class="well">
	<table class="table">
	<thead>
		<tr>
			<th>번호</th>
			<th>이벤트 명</th>
			<th>댓글관리</th>
			<th>시작일</th>
			<th>종료일</th>
			<th>진행상태</th>
			<th>당첨자발표</th>
			<th>작성자</th>
			<th>조회수</th>
		</tr>
	</thead>
	<tbody>
		<%
			if(eventNum > 0){
				for(Event list : eventList){
				// for reply articles
		%>
				<tr>
					<td><%=eventNum%></td>
					<td><a href="javascript:goForm('<%=list.getEventId()%>')"><%=list.getTitle()%></a></td>
					<td>보기(<%=list.getCnt()%>)</td>
					<td><%=list.getStartDate()%></td>
					<td><%=list.getEndDate()%></td>
					<td><%if( list.getProgress().equals("Y") ){ %> 진행중 <%}else{%>진행완료<%}%></td>
					<td><%if( list.getEventCnt() > 0 ){%><a href="javascript:goWinForm('<%=list.getWinId() %>');">발표 ></a> <%}%></td>
					<td><%=list.getModifyId()%></td>
					<td><%=list.getHits()%></td>
				</tr>
		<%		
				eventNum--;
				}
			}else{
		%>
			<tr>
				<td colspan="9" style="height:80px;text-align:center;">
				[ 등록된 게시물이 없습니다. ]
				</td>
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

function goForm(eventId){
	var f = document.forms.qform;
	if( eventId == undefined )
	{
		eventId = '0';
		f.mode.value = "add";
		f.eventId.value = "0";
	}
	else
	{
		f.mode.value = "modify";
		f.eventId.value = eventId;
	}
	f.action = "eventForm.do"
	f.submit();
}

function goList(pg){
	var f = document.forms.lform;
	if(pg == undefined){
		f.pg.value = 1;
		$("form").each(function() { this.reset(); });
	}
	else{
		f.pg.value = pg;
	}
	f.action = "eventList.do";
	f.submit();
}

function goWinForm(winId){
	var f = document.forms.qform;
	if( winId == undefined )
	{
		winId = '0';
		f.mode.value = "add";
		f.winId.value = "0";
	}
	else
	{
		f.mode.value = "modify";
		f.winId.value = winId;
	}
	f.action = "eventWinForm.do"
	f.submit();
}

$("input[name=findStartDate]").datepicker({
	monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월' ],
	dayNamesMin: ['일','월','화','수','목','금','토'],
	dateFormat: 'yy-mm-dd'
});
$("input[name=findEndDate]").datepicker({
	monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월' ],
	dayNamesMin: ['일','월','화','수','목','금','토'],
	dateFormat: 'yy-mm-dd'
});

//-->
</script>
