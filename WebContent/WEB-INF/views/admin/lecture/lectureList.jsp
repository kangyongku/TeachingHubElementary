<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	List<Lecture> lectureList = (List<Lecture>) request.getAttribute("lectureList");
	int lectureTotalCnt = (Integer) request.getAttribute("lectureTotalCnt");
	int lectureNum = (Integer) request.getAttribute("lectureNum");
	int pg = (Integer)request.getAttribute("pg");
	
	String findStr = (String) request.getAttribute("findStr");
	String findMethod = (String) request.getAttribute("findMethod");
	String findStartDate = (String) request.getAttribute("findStartDate");
	String findEndDate = (String) request.getAttribute("findEndDate");
	String paging = (String) request.getAttribute("paging");
	String statusDate = (String) request.getAttribute("statusDate");
%>
<form name="qform" method="post">
	<input type="hidden" name="mode"/>
	<input type="hidden" name="lectureId" />
</form>

<form name="lform" method="post">
	<input type="hidden" name="pg" value="<%=pg %>" />
	<div>
		<table class="table table-bordered" >
			<col width="80" />
			<col width="*" />
			<tbody>
				<tr>
					<th rowspan="2">검색</th>
					<td colspan="2">
						분류 :
						<select>
							<option>전체</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						등록일 :
						<input type="text" name="findStartDate" style="width:80px;" readonly value="<%=findStartDate %>"/>
						~
						<input type="text" name="findEndDate" style="width:80px;" readonly value="<%=findEndDate %>"/>
					</td>
					<td>
						기본검색 :
						<select name="findMethod" style="width:100px;" >
							<option value="">전체</option>
							<option value="ft" <%if(findMethod.equals("ft")){ %>selected<%} %>>제목</option>
							<option value="fc" <%if(findMethod.equals("fc")){ %>selected<%} %>>내용</option>
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

<!-- 본문 영역 -->
<div class="well">
	<table class="table">
		<tbody>
			<tr>
				<th>번호</th>
				<th>분류</th>
				<th>목록이미지</th>
				<th>제목</th>
				<th>작성자</th>
				<th>등록일</th>
				<th>조회수</th>
			</tr>
		<%
			if(lectureNum > 0){
				for(Lecture list : lectureList){
				// for reply articles
		%>
				<tr>
					<td><%=lectureNum%></td>
					<td><img src="<%=list.getThumbnail() %>"</td>
					<td><a href="javascript:goForm('<%=list.getLectureId()%>')"><%=list.getTitle()%></a></td>
					<td><%=list.getRegId()%></td>
					<td><%=list.getRegDate()%></td>
					<td><%=list.getHits()%></td>
				</tr>
		<%		
				lectureNum--;
				}
			}else{
		%>
			<tr>
				<td colspan="7" style="height:80px;text-align:center;">
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
function goForm(lectureId){
	var f = document.forms.qform;
	if( lectureId == undefined )
	{
		lectureId = '0';
		f.mode.value = "add";
		f.lectureId.value = "0";
	}
	else
	{
		f.mode.value = "modify";
		f.lectureId.value = lectureId;
	}
	f.action = "lectureForm.do"
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
	f.action = "lectureList.do";
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
