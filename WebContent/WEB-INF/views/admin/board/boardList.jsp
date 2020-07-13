<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	List<BoardConfig> list = (List<BoardConfig>) request.getAttribute("list");
%>
<div class="btn-toolbar">
	<button class="btn btn-primary" onclick="goForm();"><i class="icon-plus"></i> 게시판 생성</button>
	<div class="btn-group">
	</div>
</div>
<div class="well">
	<table class="table">
	<col width="60"/>
	<col width="120"/>
	<col width="*"/>
	<col width="120"/>
	<col width="120"/>
	<col width="120"/>
	<col width="120"/>
	<thead>
	<tr>
		<th>게시판 아이디</th>
		<th>구분</th>
		<th>게시판명</th>
		<th>생성일</th>
		<th>수정일</th>
		<th>총 게시글수</th>
		<th>게시글관리</th>
	</tr>
	</thead>
	<tbody>
	<% 
		if(list.size() > 0){
			
			int articleNum = 1;			
			for(BoardConfig boardConfig : list){
	%>
	<tr>
		<td><%=boardConfig.getBoardId() %></td>
		<td><%=boardConfig.getBoardFlagName() %></td>
		<td><a href="javascript:goForm('<%=boardConfig.getBoardId()%>');"><%=boardConfig.getBoardName() %></a></td>
		<td><%=boardConfig.getRegDate().substring(0,10) %></td>
		<td><%=boardConfig.getModifyDate().substring(0,10) %></td>
		<td><%=boardConfig.getTotalArticles() %></td>
		<td>
			<button class="btn" onclick="goBoardArticles('<%=boardConfig.getBoardId() %>');"><i class="icon-plus"></i> 관리</button>
		</td>
	</tr>
	<%
				articleNum++;
			}
		}else{
	%>
	<tr>
		<td colspan="4" style="height:80px;text-align:center;">
		[ 생성된 게시판이 존재하지 않습니다. ]
		</td>
	</tr>
	<%
		}
	%>
	</tbody>
	</table>
</div>
<!-- 
<div class="pagination">
	<ul>
		<li><a href="#">Prev</a></li>
		<li><a href="#">1</a></li>
		<li><a href="#">2</a></li>
		<li><a href="#">3</a></li>
		<li><a href="#">4</a></li>
		<li><a href="#">Next</a></li>
	</ul>
</div>
//-->

<script type="text/javascript">
<!--

function goForm(boardId)
{
	if( boardId == undefined )
		boardId = '';
	
	location.href='/admin/board/boardForm.do?boardId=' + boardId;
}

function goBoardArticles(boardId)
{
	location.href='/admin/board/list.do?boardId=' + boardId;
}

//-->
</script>