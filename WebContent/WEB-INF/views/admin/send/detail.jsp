<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	int sendId = (Integer) request.getAttribute("sendId");
	int pg = (Integer) request.getAttribute("pg");
	int findBoardId = (Integer) request.getAttribute("findBoardId");
	String findIsAccept = (String) request.getAttribute("findIsAccept");
	String findStartDate = (String) request.getAttribute("findStartDate");
	String findEndDate = (String) request.getAttribute("findEndDate");
	String findMethod = (String) request.getAttribute("findMethod");
	String findStr = (String) request.getAttribute("findStr");
	
	Send send = (Send) request.getAttribute("send");
%>
<div class="btn-toolbar">
	<button class="btn btn-danger" onclick="deleteSend();">삭제</button>
	<button class="btn" onclick="goList();">리스트</button>
	<div class="btn-group">
	</div>
</div>

<div class="well">
	<table class="table">
	<col width="150"/>
	<col width="*"/>
	<col width="150"/>
	<col width="*"/>
	<tbody>
	<tr>
		<th>글쓴이</th>
		<td colspan="3">
			<%=send.getUserName()%>(<%=send.getUserId() %>)
		</td>
	</tr>
	<tr>
		<th>분류</th>
		<td colspan="3">
			<%=send.getBoardName()%><% if( !Validate.isEmpty(send.getCategoryName())){ %>(<%=send.getCategoryName() %>)<% } %>
		</td>
	</tr>
	<tr>
		<th>제목</th>
		<td colspan="3">
			<%=send.getTitle() %>
		</td>
	</tr>
	<tr>
		<th>내용</th>
		<td colspan="3">
			<%=send.getContents() %>
		</td>
	</tr>
	<tr>
		<th>첨부파일</th>
		<td colspan="3">
			<% if( !Validate.isEmpty(send.getFileName1())){ %>
			<div><a href="/commons/file/getAdminFile.do?fileName=<%=send.getFileName1() %>&filePath=<%=send.getDataFile1()%>"><%=send.getFileName1() %></a></div>
			<% } %>
			<% if( !Validate.isEmpty(send.getFileName2())){ %>
			<div><a href="/commons/file/getAdminFile.do?fileName=<%=send.getFileName2() %>&filePath=<%=send.getDataFile2()%>"><%=send.getFileName2() %></a></div>
			<% } %>
			<% if( !Validate.isEmpty(send.getFileName3())){ %>
			<div><a href="/commons/file/getAdminFile.do?fileName=<%=send.getFileName3() %>&filePath=<%=send.getDataFile3()%>"><%=send.getFileName3() %></a></div>
			<% } %>
		</td>
	</tr>
	<tr>
		<th>승인</th>
		<td colspan="3">
			<select name="isAccept" onchange="changeAccept();">
				<option value="Y" <% if( send.getIsAccept().equals("Y")){ %>selected<% } %>>승인</option>
				<option value="N" <% if( send.getIsAccept().equals("N")){ %>selected<% } %>>미승인</option>
			</select>
		</td>
	</tr>
	</tbody>
	</tbody>
	</table>
	</form>
</div>

<div class="btn-toolbar">
	<button class="btn btn-danger" onclick="deleteSend();">삭제</button>
	<button class="btn" onclick="goList();">리스트</button>
	<div class="btn-group">
	</div>
</div>

<form name="listForm" action="list.do" method="get">
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

function goList()
{
	var f = document.forms.listForm;
	f.submit();
}

function deleteSend()
{
	if( confirm('삭제된 데이타는 복구할 수 없습니다.\r\n진행하시겠습니까?') )
	{
		$.ajax({
			url : 'deleteSend.do' ,
			type : 'post',
			data : 'sendId=<%=sendId%>' ,
			dataType : 'json',
			error : function(){
				alert('서버와 통신도중 에러가 발생되었습니다.');
			},
			success : function(json){
				if( json.result == 'SUCCESS' )
					goList();
				else
					alert(json.msg);
			}
		});
	}
}

function changeAccept()
{
	var isAccept = $('select[name=isAccept] option:selected').val();
	var msg = (isAccept == 'Y') ? '승인' : '미승인';
	
	if(confirm('현재 상태를 \r\n[' + msg + ']\r\n상태로 변경하시겠습니까?'))
	{
		$.ajax({
			url : 'changeAccept.do' ,
			type : 'post',
			data : 'sendId=<%=sendId%>&isAccept=' + isAccept ,
			dataType : 'json',
			error : function(){
				alert('서버와 통신도중 에러가 발생되었습니다.');
			},
			success : function(json){
				if( json.result == 'FAILURE' )
					alert(json.msg);
			}
		});
	}
	
}

//-->
</script>