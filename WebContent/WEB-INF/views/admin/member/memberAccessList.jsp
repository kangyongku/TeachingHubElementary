<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	List<Member> memberAccessList = (List<Member>) request.getAttribute("memberAccessList");
	int memberTotalCnt = (Integer) request.getAttribute("memberTotalCnt");
	int accessNum = (Integer) request.getAttribute("accessNum");
	int pg = (Integer)request.getAttribute("pg");
	
	String findStr = (String) request.getAttribute("findStr");
	String findMethod = (String) request.getAttribute("findMethod");
	String findAction = (String) request.getAttribute("findAction");
	String paging = (String) request.getAttribute("paging");
%>
<form name="qform" method="post">
	<input type="hidden" name="mode" value=""/>
	<input type="hidden" name="userId" />
	<input type="hidden" name="findStr" value="<%=findStr %>" />
	<input type="hidden" name="findMethod" value="<%=findMethod %>" />
	<input type="hidden" name="findAction" value="<%=findAction %>" />
	<input type="hidden" name="pg" value="<%=pg %>" />
</form>

<form name="lform" method="post">
	<input type="hidden" name="pg" value="<%=pg %>" />
	<!-- 검색 영역 -->
	<div>
		<table class="table table-bordered" >
			<col width="80" />
			<col width="*" />
			<tbody>
				<tr>
					<th rowspan="2">검색</th>
					<td colspan="2">
						수행업무 :
						<select name="findAction">
							<option value="">전체</option>
							<option value="serch" <%if( findAction.equals("serch") ){ %>selected<%} %>>조회</option>
							<option value="mod" <%if( findAction.equals("mod") ){ %>selected<%} %>>수정</option>
							<option value="del" <%if( findAction.equals("del") ){ %>selected<%} %>>삭제</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						기본검색 :  
						<select name="findMethod" style="width:100px;" >
							<option value="fi" <%if ( findMethod.equals("fi") ){%> selected <%}%>>아이디</option>
							<option value="fn" <%if ( findMethod.equals("fn") ){%> selected <%}%>>이름</option>
							<option value="fd" <%if ( findMethod.equals("fd") ){%> selected <%}%>>접속일</option>
							<option value="fa" <%if ( findMethod.equals("fa") ){%> selected <%}%>>접속자IP</option>
						</select>
						<input type="text" name="findStr" value="<%=findStr %>" />
						<a href="javascript:goList()" class="btn">검색</a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</form>

<!-- 본문 내용 -->
<div class="well">
	<table class="table">
		<tbody>
			<tr>
				<th>번호</th>
				<th>사이트</th>
				<th>아이디</th>
				<th>이름</th>
				<th>처리일시</th>
				<th>접속자 IP</th>
				<th>수행업무</th>
				<th>회원정보</th>
			</tr>
		<%
			if(accessNum > 0){
				for(Member list : memberAccessList){
				// for reply articles
		%>
				<tr>
					<td><%=list.getNum()%></td>
					<td><%if(list.getSiteCode()==3){ %>도서몰
					<%}else if(list.getSiteCode()==4){ %>티칭허브
					<%} %></td>
					<td><%=list.getUserId() %></td>
					<td><%=list.getName() %></td>
					<td><%=list.getActionDate() %></td>
					<td><%=list.getIp() %></td>
					<td><%=list.getActionMode() %></td>
					<td><%=list.getTargetName() %><br/>(<%=list.getTargetId() %>)</td>
					<%-- <td>
						<%if ( list.getIsFinallyAuth().equals("Y") ){ %>
							승인
						<%}else{ %>
							미승인
						<%} %>
					</td> --%>
				</tr>
		<%		
			accessNum--;
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

<!-- 스크립트 영역 -->
<script>
<!--
function goForm(id){
	var f = document.forms.qform;
	if( id == undefined )
	{
		id = '0';
		f.mode.value = "add";
		f.id.value = "0";
	}
	else
	{
		f.mode.value = "modify";
		f.id.value = id;
	}
	f.action = "memberForm.do"
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
	f.action = "memberAccessList.do";
	f.submit();
}
//-->
</script>
