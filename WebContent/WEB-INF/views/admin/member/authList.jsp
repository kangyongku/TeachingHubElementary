<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	int pg = (Integer)request.getAttribute("pg");
	String paging = (String)request.getAttribute("paging");
	List<Member> authList = (List<Member>)request.getAttribute("authList");
	int authNum = (Integer)request.getAttribute("authNum");
	String mode = (String)request.getAttribute("mode");
	
	String findMethod = (String) request.getAttribute("findMethod");
	String findStr = (String) request.getAttribute("findStr");
	String findAuthType = (String) request.getAttribute("findAuthType");
%>

<!-- 검색 영역 -->
<form name="lform" method="post">
<input type="hidden" name="pg" value="<%=pg %>" />
<table class="table table-bordered" >
	<col width="80" />
	<col width="*" />
	<tbody>
		<tr>
			<th rowspan="2">검색</th>
			<td colspan="2">
				회원 검색 : 
				<select name="findAuthType">
					<option value="">전체</option>					
					<option value="N" <%if ( findAuthType.equals("N") ){%> selected <%}%> >일반관리자</option>					
					<option value="S" <%if ( findAuthType.equals("S") ){%> selected <%}%> >슈퍼관리자</option>					
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				기본검색 :  
				<select name="findMethod" style="width:100px;">
					<option value="fn" <%if ( findMethod.equals("fn") ){%> selected <%}%>>이름</option>
					<option value="fi" <%if ( findMethod.equals("fi") ){%> selected <%}%>>아이디</option>
					<option value="fh" <%if ( findMethod.equals("fh") ){%> selected <%}%>>휴대전화</option>
					<option value="fe" <%if ( findMethod.equals("fe") ){%> selected <%}%>>이메일</option>
					<option value="fr" <%if ( findMethod.equals("fr") ){%> selected <%}%>>가입일</option>
				</select>
				<input type="text" name="findStr" maxlength="128" value="<%=findStr %>"/>
				<a href="javascript:goList()" class="btn">검색</a>
			</td>
		</tr>
	</tbody>
</table>

<!-- 본문 영역 -->
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
				<th>관리자지정일</th>
				<th>권한지정자</th>
			</tr>
				<% 
					if( authNum > 0 ){
						for( Member list: authList ){
				%>
					<tr>
						<td><%=authNum %></td>
						<td><%if ( list.getAuthType().equals("S") ){%>슈퍼관리자<%}else{%>일반관리자<%}%></td>
						<td><a href="javascript:goForm('<%=list.getAuthId()%>')"><%=list.getUserId()%></a></td>
						<td><%=list.getName() %></td>
						<td><%=list.getHpNum() %></td>
						<td><%=list.getWritedate() %></td>
						<td><%=list.getAdminDate() %></td>
						<td><%=list.getAuthGranter() %></td>
					</tr>
				<% 		
						authNum--;
						}
					}else{
				%>
					<tr>
						<td colspan="6" style="height:80px;text-align:center;">
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
</form>

<div class="btn-toolbar">
	<button class="btn btn-primary" onclick="goForm();"><i class="icon-plus"></i> 등록</button>
</div>

<form name="qform" action="" method="get">
<input type="hidden" name="pg" value="<%=pg%>"/>
<input type="hidden" name="mode" value="" />
<input type="hidden" name="authId"/>
</form>

<!-- 스크립트 영역 -->
<script>
<!--

function goForm(authId){
	var f = document.forms.qform;
	
	if( authId == undefined ){
		authId = '0';
	}
	else{
		f.authId.value = authId;
		f.mode.value = "modify";
	}
	f.action = "authForm.do"
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
	f.action = "authList.do";
	f.submit();
}

//-->
</script>
