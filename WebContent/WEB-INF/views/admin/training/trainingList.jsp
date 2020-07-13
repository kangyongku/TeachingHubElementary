<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	List<Training> trainingList = (List<Training>)request.getAttribute("trainingList");
	String mode = (String)request.getAttribute("mode");
	
	String findStatusDate = (String)request.getAttribute("findStatusDate");
	String findStatusSketch = (String)request.getAttribute("findStatusSketch");
	String findMethod = (String)request.getAttribute("findMethod");
	String findStr = (String)request.getAttribute("findStr");
	String findStartDate = (String)request.getAttribute("findStartDate");
	String findEndDate = (String)request.getAttribute("findEndDate");
	
	int pg = (Integer) request.getAttribute("pg");
	String paging = (String) request.getAttribute("paging");	
	
	int trainingNum = (Integer)request.getAttribute("trainingNum");
%>
<form name="qform" action="" method="post">
	<input type="hidden" name="trainingId" value="" />
	<input type="hidden" name="mode" value="<%=mode%>" />
	<input type="hidden" name="pg" value="<%=pg %>"/>
	<input type="hidden" name="postId" value="" />
	<input type="hidden" name="findStr" value="" />
</form>

<form name="lform" action="" method="post">
	<input type="hidden" name="trainingId" value="" />
	<input type="hidden" name="mode" value="<%=mode%>"/>
	<input type="hidden" name="pg" value="<%=pg%>"/>

<!-- 검색 테이블 -->
<div>
	<table class="table table-bordered">
		<col width="80" />
		<col width="150" />
		<col width="150" />
		<tbody>
			<tr>
				<td rowspan="2">검색</td>
				<td>
					진행상태 :
					<select name="findStatusDate" style="width:100px;" >
						<option>전체</option>
						<option value="Y" <%if(findStatusDate.equals("Y")){ %>selected<%} %>>진행중</option>
						<option value="N" <%if(findStatusDate.equals("N")){ %>selected<%} %>>진행완료</option>
					</select> 
				</td>
				<td>
					교사연수 기간 :
					<input type="text" name="findStartDate" style="width:80px;" readonly value="<%=findStartDate %>"/>
					~
					<input type="text" name="findEndDate" style="width:80px;" readonly value="<%=findEndDate %>"/>
				</td>
			</tr>
			<tr>
				<td>
					현장스케치 :
					<select name="findStatusSketch" style="width:100px;">
						<option value="">전체</option>
						<option value="Y" <%if(findStatusSketch.equals("Y")){ %>selected<%} %>>등록</option>
						<option value="N" <%if(findStatusSketch.equals("N")){ %>selected<%} %>>미등록</option>
					</select>
				</td>
				<td>
					기본검색 : 
					<select name="findMethod" style="width:100px;">
						<option value="">전체</option>
						<option value="ft" <%if(findMethod.equals("ft")){ %>selected<%} %>>교사연수명</option>
						<option value="fi" <%if(findMethod.equals("fi")){ %>selected<%} %>>작성자</option>
					</select>
					<input type="text" name="findStr" value="<%=findStr %>" />
					<a href="javascript:goList();"><div class="btn" style="margin-left:20px">검색</div></a>
				</td>
			</tr>
		</tbody>
	</table>
</div>

<!-- 본문테이블 -->
<div class="well">
	<table class="table" >
		<col width="80"/>
		<col width="150"/>
		<col width="100"/>
		<col width="100"/>
		<col width="80"/>
		<col width="80"/>
		<col width="80"/>
		<col width="80"/>
		<col width="80"/>
		<thead>
			<tr>
				<td>번호</td>
				<td>교사연수 명</td>
				<td>시작일</td>
				<td>종료일</td>
				<td>신청자수</td>
				<td>진행상태</td>
				<td>현장스케치</td>
				<td>작성자</td>
				<td>조회수</td>
			</tr> 
		</thead>
		<tbody>
			<% if(trainingNum > 0){  %>
				<%
					for(Training list : trainingList){
					// for reply articles
				%>
					<tr>
						<td><%=trainingNum%></td>
						<td><a href="javascript:goForm('<%=list.getTrainingId()%>')"><%=list.getTitle()%></a></td>
						<td><%=list.getStartDate()%></td>
						<td><%=list.getEndDate()%></td>
						<td><%=list.getReqCnt() %>명</td>
						<td>진행중</td>
						<td><%if(list.getPostCnt() > 0){%> <a href="javascript:goPostscript('<%=list.getPostId()%>')">보기 ></a>  <%}%></td>
						<td><%=list.getRegId()%></td>
						<td><%=list.getHits()%></td>
					</tr>
				<%	
					trainingNum--;
					}
				%>
			<%}else{ %>	
				<tr>
					<td colspan="9" style="height:80px;text-align:center;">
					[ 등록된 게시물이 없습니다. ]
					</td>
				</tr>
			<%} %>
		</tbody>
	</table>
</div>
</form>
<div class="pagination">
	<ul>
		<%=paging %>
	</ul>
</div>
<div class="btn-toolbar">
	<a href="javascript:goForm();" class="btn btn-primary"><i class="icon-plus"></i>등록</a>
	<a href="javascript:goList();" class="btn">리스트</a>
</div>

<!-- 스크립트 영역 -->
<script>
<!--
function goForm(trainingId){
	var f = document.forms.qform;
	if( trainingId == undefined )
	{
		trainingId = '0';
		f.mode.value = "add";
		f.findStr.value = "";
		f.trainingId.value = "0";
	}
	else
	{
		f.mode.value = "modify";
		f.trainingId.value = trainingId;
	}
	f.action = "trainingForm.do"
	f.submit();
}

function goList(pg){
	var f = document.forms.lform;
	if(pg == undefined){
		f.pg.value = 1;
	}
	else{
		f.pg.value = pg;
	}
	f.action = "trainingList.do";
	f.submit();
}

function goPostscript(postId){
	var f = document.forms.qform;
	f.postId.value = postId;
	f.mode.value = "modify";
	f.action = "trainingPostscriptForm.do"
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