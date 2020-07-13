<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	int pg = (Integer) request.getAttribute("pg");
	int findBoardId = (Integer) request.getAttribute("findBoardId");
	String findIsAccept = (String) request.getAttribute("findIsAccept");
	String findStartDate = (String) request.getAttribute("findStartDate");
	String findEndDate = (String) request.getAttribute("findEndDate");
	String findMethod = (String) request.getAttribute("findMethod");
	String findStr = (String) request.getAttribute("findStr");
	
	int totalCount = (Integer) request.getAttribute("totalCount");
	int articleNum = (Integer) request.getAttribute("articleNum");
	List<Send> list = (List<Send>) request.getAttribute("list");
	String paging = (String) request.getAttribute("paging");
%>
<div>
	<form name="sform" action="" method="get" onsubmit="search();return false;">
	<table class="table">
	<col width="80"/>
	<col width="80"/>
	<col width="350"/>
	<col width="80"/>
	<col width="*"/>	
	<thead>
	<tr>
		<th style="border:1px solid #eee;vertical-align:middle;" rowspan="4">검색</th>
		<th style="border:1px solid #eee;">분류</th>
		<td style="border:1px solid #eee;">
			<input type="radio" name="findBoardId" value="2" <% if( findBoardId == 2){ %>checked<% } %>/> 교과활용자료
			<input type="radio" name="findBoardId" value="3" <% if( findBoardId == 3){ %>checked<% } %>/> 학급운영자료
		</td>
		<th style="border:1px solid #eee;">승인</th>
		<td style="border:1px solid #eee;">
			<select name="findIsAccept">
				<option value="">전체</option>
				<option value="Y" <% if( findIsAccept.equals("Y")){ %>selected<% } %>>승인</option>
				<option value="N" <% if( findIsAccept.equals("N")){ %>selected<% } %>>대기</option>
			</select>
		</td>
	</tr>
	<tr>
		<th style="border:1px solid #eee;">등록일</th>
		<td style="border:1px solid #eee;" colspan="3">
			<input type="text" name="findStartDate" class="datepicker" style="width:120px;" value="<%=findStartDate%>"/> ~ <input type="text" name="findEndDate" class="datepicker" style="width:120px;" value="<%=findEndDate%>"/>
		</td>
	</tr>
	<tr>
		<th style="border:1px solid #eee;">기본검색</th>
		<td style="border:1px solid #eee;" colspan="3">
			<select name="findMethod" style="width:80px;">
				<option value="st" <% if( findMethod.equals("st")){ %>selected<% } %>>제목</option>
				<option value="su" <% if( findMethod.equals("su")){ %>selected<% } %>>내용</option>
			</select>
			<input type="text" name="findStr" value="<%=findStr %>"/>
			<a href="javascript:document.forms.sform.submit();" class="btn btn-primary">검색</a>
		</td>
	</tr>
	</thead>
	</table>
	</form>
</div>

<div class="well">
	<table class="table">
	<col width="60"/>
	<col width="180"/>
	<col width="*"/>
	<col width="80"/>
	<col width="80"/>
	<col width="100"/>
	<thead>
	<tr>
		<th>No.</th>		
		<th>분류</th>
		<th>제목</th>
		<th>승인</th>
		<th>작성자</th>
		<th>등록일</th>
	</tr>
	</thead>
	<tbody>
	<% 
		if(totalCount > 0){
			for(Send send : list){	
	%>
	<tr>
		<td><%=articleNum %></td>
		<td><%=send.getBoardName() %> <% if( !Validate.isEmpty(send.getCategoryName()) ){%> > <%=send.getCategoryName()%><% } %></td>
		<td><a href="javascript:goForm('<%=send.getSendId()%>');"><%=send.getTitle() %></a></td>		
		<td><%=send.getIsAcceptName() %></td>
		<td><%=send.getUserName() %></td>
		<td><%=send.getRegDate().substring(0,10) %></td>
	</tr>
	<%
				articleNum--;
			}
		}else{
	%>
	<tr>
		<td colspan="5" style="height:80px;text-align:center;">
		[ 등록된 원고가 없습니다. ]
		</td>
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

<form name="detailForm" action="detail.do" method="get">
<input type="hidden" name="sendId"/>
<input type="hidden" name="pg" value="<%=pg%>"/>
<input type="hidden" name="findBoardId" value="<%=findBoardId%>"/>
<input type="hidden" name="findIsAccept" value="<%=findIsAccept%>"/>
<input type="hidden" name="findStartDate" value="<%=findStartDate%>"/>
<input type="hidden" name="findEndDate" value="<%=findEndDate%>"/>
<input type="hidden" name="findMethod" value="<%=findMethod%>"/>
<input type="hidden" name="findStr" value="<%=findStr%>"/>
</form>

<script type="text/javascript">
<!--

$(document).ready(function(){
	
	$('.datepicker').datepicker({
		monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		dayNames: ['일','월','화','수','목','금','토'],
		dayNamesShort: ['일','월','화','수','목','금','토'],
		dayNamesMin: ['일','월','화','수','목','금','토'],
		weekHeader: 'Wk' ,
		dateFormat: 'yy-mm-dd'
	});
	
});

function goList(pg)
{
	var f = document.forms.listForm;
	
	f.pg.value = pg;
	f.submit();
}

function goForm(sendId)
{	
	var f = document.forms.detailForm;
	f.sendId.value = sendId;
	f.submit();
}

//-->
</script>