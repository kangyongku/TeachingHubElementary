<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	List<Member> memberList = (List<Member>) request.getAttribute("memberList");
	int memberTotalCnt = (Integer) request.getAttribute("memberTotalCnt");
	int memberNum = (Integer) request.getAttribute("memberNum");
	int pg = (Integer)request.getAttribute("pg");
	
	String findStr = (String) request.getAttribute("findStr");
	String findMethod = (String) request.getAttribute("findMethod");
	String findIsFinallyAuth = (String) request.getAttribute("findIsFinallyAuth");
	String findAuthType = (String) request.getAttribute("findAuthType");
	String paging = (String) request.getAttribute("paging");
%>
<form name="qform" method="post">
	<input type="hidden" name="mode" value=""/>
	<input type="hidden" name="id" />
	<input type="hidden" name="findStr" value="<%=findStr %>" />
	<input type="hidden" name="findMethod" value="<%=findMethod %>" />
	<input type="hidden" name="findIsFinallyAuth" value="<%=findIsFinallyAuth %>" />
	<input type="hidden" name="findAuthType" value="<%=findAuthType %>" />
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
						회원 검색 :
						<select name="findIsFinallyAuth">
							<option value="">전체</option>
							<option value="Y" <%if( findIsFinallyAuth.equals("Y") ){ %>selected<%} %>>승인</option>
							<option value="N" <%if( findIsFinallyAuth.equals("N") ){ %>selected<%} %>>미승인</option>
						</select>
						<select name="findAuthType">
							<option value="">전체</option>
							<option value="Y" <%if( findAuthType.equals("Y") ){ %>selected<%} %>>전자인증</option>
							<option value="D" <%if( findAuthType.equals("D") ){ %>selected<%} %>>서류인증</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						기본검색 :  
						<select name="findMethod" style="width:100px;" >
							<option value="fn" <%if(findMethod.equals("fn")){ %>selected<%} %>>이름</option>
							<option value="fi" <%if(findMethod.equals("fi")){ %>selected<%} %>>아이디</option>
							<option value="fh" <%if(findMethod.equals("fh")){ %>selected<%} %>>휴대전화</option>
							<option value="fe" <%if(findMethod.equals("fe")){ %>selected<%} %>>이메일</option>
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
				<th>구분</th>
				<th>아이디</th>
				<th>이름</th>
				<th>연락처</th>
				<th>가입일</th>
				<th>승인여부</th>
			</tr>
		<%
			if(memberNum > 0){
				for(Member list : memberList){
				// for reply articles
		%>
				<tr>
					<td><%=memberNum%></td>
					<td><%=list.getJobName() %></td>
					<td><%=list.getUserId() %></td>
					<td><a href="javascript:goForm('<%=list.getId()%>');"><%=list.getName() %></a></td>
					<td><%=list.getHpNum() %></td>
					<td><%=list.getWritedate() %></td>
					<td>
						<%if ( list.getIsFinallyAuth().equals("Y") ){ %>
							승인
						<%}else{ %>
							미승인
						<%} %>
					</td>
				</tr>
		<%		
				memberNum--;
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
	f.action = "memberList.do";
	f.submit();
}
//-->
</script>
