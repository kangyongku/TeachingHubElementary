<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	List<TrainingPostscript> trainingPostscriptList = (List<TrainingPostscript>)request.getAttribute("trainingPostscriptList");
	String mode = (String)request.getAttribute("mode");
	String findMethod = (String)request.getAttribute("findMethod");
	String findStr = (String)request.getAttribute("findStr");
	int pg = (Integer) request.getAttribute("pg");
	String paging = (String) request.getAttribute("paging");	
	int trainingPostscriptNum = (Integer)request.getAttribute("trainingPostscriptNum");
%>
<form name="qform" action="" method="post">
	<input type="hidden" name="mode" value="<%=mode%>" />
	<input type="hidden" name="pg" value="<%=pg %>"/>
	<input type="hidden" name="postId" />
</form>

<form name="lform" action="" method="post">
	<input type="hidden" name="mode" value="<%=mode%>"/>
	<input type="hidden" name="pg" value="<%=pg%>"/>
<!-- 검색 테이블 -->
<div>
	<table class="table table-bordered">
		<col width="80" />
		<col width="*" />
		<tbody>
			<tr>
				<th rowspan="2">검색</th>
				<th>
					기본검색 :
					<select name="findMethod" style="width:100px;" >
						<option value="ft" <%if(findMethod.equals("ft")){ %>selected<%} %>>제목</option>
						<option value="fc" <%if(findMethod.equals("fc")){ %>selected<%} %>>내용</option>
						<option value="fi" <%if(findMethod.equals("fi")){ %>selected<%} %>>작성자</option>
					</select>
					<input type="text" name="findStr" value="<%=findStr %>" />
					<a href="javascript:goList();" class="btn">검색</a> 
				</th>
			</tr>
		</tbody>
	</table>
</div>

<!-- 본문 테이블 -->
<div class="well">
	<table class="table" >
		<col width="80"/>
		<col width="250"/>
		<col width="120"/>
		<col width="150"/>
		<col width="100"/>
		<thead>
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>등록일</th>
			<th>조회수</th>
		</tr> 
		</thead>
		<tbody>
			<% if(trainingPostscriptNum > 0){  %>
				<%
					for(TrainingPostscript list : trainingPostscriptList){
					// for reply articles
				%>
					<tr>
						<td><%=trainingPostscriptNum%></td>
						<td><a href="javascript:goForm('<%=list.getPostId()%>')"><%=list.getTitle()%></a></td>
						<td><%=list.getRegId()%></td>
						<td><%=list.getRegDate()%></td>
						<td><%=list.getHits() %></td>
					</tr>
				<%	
				trainingPostscriptNum--;
				}
				%>
			<%}else{ %>	
				<tr>
					<td colspan="5" style="height:80px;text-align:center;">
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
function goForm(postId){
	var f = document.forms.qform;
	if( postId == undefined )
	{
		postId = '0';
		f.mode.value = "add";
		f.postId.value = "0";
	}
	else
	{
		f.mode.value = "modify";
		f.postId.value = postId;
	}
	f.action = "trainingPostscriptForm.do"
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
	
	f.action = "trainingPostscriptList.do";
	f.submit();
}
//-->
</script>