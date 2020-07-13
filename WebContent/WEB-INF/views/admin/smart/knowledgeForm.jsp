<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	int knowledgeId = (Integer) request.getAttribute("knowledgeId");
	int pg = (Integer) request.getAttribute("pg");
	String findIsApproval = (String) request.getAttribute("findIsApproval");
	String findMethod = (String) request.getAttribute("findMethod");
	String findStr = (String) request.getAttribute("findStr");
	
	Knowledge knowledge = (Knowledge) request.getAttribute("knowledge");
%>
<div class="well">

	<div style="margin:10px;">
	
		<form name="wform" action="" method="post">
		<input type="hidden" name="knowledgeId" value="<%=knowledgeId%>"/>
		<table class="table">
		<col width="150"/>
		<col width="*"/>
		<col width="150"/>
		<col width="*"/>
		<tbody>	
		<tr>
			<th>승인구분</th>
			<td colspan="3">
				<input type="radio" name="isApproval" value="Y" <% if( knowledge.getIsApproval().equals("Y")){ %>checked<% } %>/> 승인
				<input type="radio" name="isApproval" value="D" <% if( knowledge.getIsApproval().equals("D")){ %>checked<% } %>/> 미승인
				<input type="radio" name="isApproval" value="N" <% if( knowledge.getIsApproval().equals("N")){ %>checked<% } %>/> 대기
			</td>
		</tr>
		<tr>
			<th>구분</th>
			<td colspan="3">
				<%=knowledge.getFlagName() %>
			</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td colspan="3">
				<%=knowledge.getUserName() %>(<%=knowledge.getUserId() %>)
			</td>
		</tr>
		<% if( knowledge.getIsApproval().equals("Y")){ %>
		<tr>
			<th>승인자</th>
			<td colspan="3">
				<%=knowledge.getApprovalName() %>(<%=knowledge.getApprovalId() %>)
			</td>
		</tr>
		<tr>
			<th>승인일</th>
			<td colspan="3">
				<%=knowledge.getApprovalDate() %>
			</td>
		</tr>
		<% } %>
		<tr>
			<th>표제어</th>
			<td colspan="3">
				<%=knowledge.getTitle() %> <a class="btn" href="/admin/smart/headwordForm.do?headwordId=<%=knowledge.getHeadwordId()%>" target="_blank">표제어 상세보기</a>
			</td>
		</tr>		
		<tr>
			<th>내용</th>
			<td colspan="3">
				<%=knowledge.getContents() %>
			</td>
		</tr>
		<tr>
			<th>등록일</th>
			<td colspan="3">
				<%=knowledge.getRegDate() %>
			</td>
		</tr>
		</tbody>
		</tbody>
		</table>
		</form>
		
		<div class="btn-toolbar">
			<button class="btn btn-danger" onclick="goDelete();">삭제</button>
			<button class="btn btn-primary" onclick="changeApproval();">상태변경</button>
			<button class="btn" onclick="goList();">리스트</button>
			<div class="btn-group">
			</div>
		</div>
	</div>
</div>

<form name="listForm" action="knowledgeList.do" method="get">
<input type="hidden" name="pg" value="<%=pg%>"/>
<input type="hidden" name="findIsApproval" value="<%=findIsApproval%>"/>
<input type="hidden" name="findMethod" value="<%=findMethod%>"/>
<input type="hidden" name="findStr" value="<%=findStr%>"/>
</form>

<script type="text/javascript">
<!--

function goDelete()
{
	if( confirm('정말로 삭제하시겠습니까?') )
	{
		$.ajax({
			url : 'knowledgeDelete.do' ,
			type : 'post' ,
			data : 'knowledgeId=<%=knowledgeId%>' ,
			dataType : 'json' ,
			error : function(){
				alert('서버와의 통신도중 에러가 발생되었습니다.');
			},
			success : function(json){
				if( json.result == 'SUCCESS' )
				{
					goList();
				}
				else
					alert(json.msg);
			}
		});
	}
}

function changeApproval()
{
	var approval = $('input[name=isApproval]:checked').val();
	var statusMsg = ( approval == 'Y' ) ? '승인' : '미승인';
	
	if( confirm('상태를 [' + statusMsg + ']으로 변경하시겠습니까?') )
	{
		$.ajax({
			url : 'knowledgeChangeStatus.do' ,
			type : 'post' ,
			data : 'knowledgeId=<%=knowledgeId%>&isApproval=' + approval ,
			dataType : 'json' ,
			error : function(){
				alert('서버와의 통신도중 에러가 발생되었습니다.');
			},
			success : function(json){
				if( json.result == 'SUCCESS' )
					location.reload();
				else
					alert(json.msg);
			}
		});
	}
}

function goList()
{
	var f = document.forms.listForm;
	f.submit();
}

//-->
</script>