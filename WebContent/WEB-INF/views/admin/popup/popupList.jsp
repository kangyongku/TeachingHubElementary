<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	List<Popup> popupList = (List<Popup>) request.getAttribute("popupList");
	int popupTotalCnt = (Integer) request.getAttribute("popupTotalCnt");
	int popupNum = (Integer) request.getAttribute("popupNum");
	int pg = (Integer)request.getAttribute("pg");
	
	String findStr = (String) request.getAttribute("findStr");
	String findMethod = (String) request.getAttribute("findMethod");
	String paging = (String) request.getAttribute("paging");
%>
<form name="qform" method="post">
<input type="hidden" name="mode"/>
<input type="hidden" name="popupId" />
</form>

<form name="lform" method="post">
<input type="hidden" name="pg" value="<%=pg %>" />

<!-- 검색 영역 -->
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

<!-- 본문 영역 -->
<div class="well">
	<table class="table">
		<tbody>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>대상</th>
				<th>형태</th>
				<th>노출유무</th>
				<th>작성자</th>
				<th>노출기간</th>
				<th>등록일</th>
			</tr>
		<%
			if(popupNum > 0){
				for(Popup list : popupList){
				// for reply articles
		%>
				<tr>
					<td><%=popupNum%></td>
					<td><a href="javascript:goForm('<%=list.getPopupId()%>')"><%=list.getTitle()%></a></td>
					<td><%if(list.getTarget().equals("THUB")){%> 티칭허브 <%}else{%> 티칭백과 <%}%>
					<td><%if(list.getType().equals("P")){%> 팝업 <%}else{%> 레이어 <%}%>
					<td><%if(list.getIsUse().equals("Y")){%> 노출 <%}else{%> 미노출 <%}%>
					<td><%=list.getRegId()%></td>
					<td><%=list.getStartDate()%> ~ <%=list.getEndDate() %></td>
					<td><%=list.getRegDate()%></td>
				</tr>
		<%		
				popupNum--;
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
</form>

<!-- 스크립트 영역 -->
<script>
<!--
function goForm(popupId){
	var f = document.forms.qform;
	if( popupId == undefined )
	{
		popupId = '0';
		f.mode.value = "add";
		f.popupId.value = "0";
	}
	else
	{
		f.mode.value = "modify";
		f.popupId.value = popupId;
	}
	f.action = "popupForm.do"
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
	f.action = "popupList.do";
	f.submit();
}

//-->
</script>
