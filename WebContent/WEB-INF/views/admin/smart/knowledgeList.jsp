<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	int pg = (Integer) request.getAttribute("pg");
	String findIsApproval = (String) request.getAttribute("findIsApproval");
	String findMethod = (String) request.getAttribute("findMethod");
	String findStr = (String) request.getAttribute("findStr");

	int articleNum = (Integer) request.getAttribute("articleNum");
	int totalCount = (Integer) request.getAttribute("totalCount");
	List<Knowledge> knowledges = (List<Knowledge>) request.getAttribute("knowledges");
	
	String paging = (String) request.getAttribute("paging");
%>
<div>
	<form name="sform" action="" method="get">
	<table class="table">	
	<col width="80"/>
	<col width="350"/>
	<col width="80"/>
	<col width="*"/>	
	<thead>
	<tr>
		<th style="border:1px solid #eee;">승인유무</th>
		<td style="border:1px solid #eee;">
			<select name="findIsApproval" style="width:80px;">
				<option value="">전체</option>
				<option value="Y" <% if( findIsApproval.equals("Y")){ %>selected<% } %>>승인</option>
				<option value="D" <% if( findIsApproval.equals("D")){ %>selected<% } %>>미승인</option>
				<option value="N" <% if( findIsApproval.equals("N")){ %>selected<% } %>>대기</option>
			</select>
		</td>	
		<th style="border:1px solid #eee;">기본검색</th>
		<td style="border:1px solid #eee;">
			<select name="findMethod" style="width:80px;">
				<option value="si">아이디</option>
			</select>
			<input type="text" name="findStr" value="<%=findStr %>"/>
			<button class="btn btn-primary" onclick="document.forms.sform.submit();">검색</button>
		</td>		
	</tr>
	</thead>
	</table>
	</form>
</div>

<div class="well">
	
	<table class="table">
	<col width="60"/>
	<col width="*"/>
	<col width="150"/>	
	<col width="100"/>
	<col width="80"/>
	<col width="100"/>
	<col width="100"/>	
	<thead>
	<tr>
		<th>No.</th>
		<th>표제어</th>		
		<th>구분</th>
		<th>작성자</th>	
		<th>승인유무</th>	
		<th>승인일</th>
		<th>등록일</th>
	</tr>
	</thead>
	<tbody>
	<% 
		if( totalCount > 0){
			for(Knowledge knowledge : knowledges){		
	%>
	<tr>
		<td><%=articleNum %></td>
		<td><a href="javascript:goDetail('<%=knowledge.getKnowledgeId()%>');"><%=knowledge.getTitle() %></a></td>
		<td><%=knowledge.getFlagName() %></td>
		<td><%=knowledge.getUserName() %></td>
		<td><%=knowledge.getIsApprovalName() %></td>
		<td>
			<% if( knowledge.getIsApproval().equals("Y")){ %>
			<%=knowledge.getApprovalDateFormat() %>
			<% } %>
		</td>
		<td><%=knowledge.getRegDateFormat() %></td>
	</tr>
	<% 
				articleNum--;
			}
		}else{ 
	%>
	<tr>
		<td colspan="7" style="text-align:center;height:50px;">등록된 내용이 없습니다.</td>
	</tr>
	<% } %>
	</tbody>
	</table>
</div>
<div class="pagination">
	<ul>
		<%=paging %>
	</ul>
</div>

<form name="detailForm" action="knowledgeForm.do" method="get">
<input type="hidden" name="knowledgeId"/>
<input type="hidden" name="pg" value="<%=pg %>"/>
<input type="hidden" name="findIsApproval" value="<%=findIsApproval %>"/>
<input type="hidden" name="findMethod" value="<%=findMethod %>"/>
<input type="hidden" name="findStr" value="<%=findStr %>"/>
</form>

<script type="text/javascript">
<!--

function goDetail(knowledgeId)
{
	var f = document.forms.detailForm;
	f.knowledgeId.value = knowledgeId;
	f.submit();
}

//-->
</script>
