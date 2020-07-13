<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	String mode = (String) request.getAttribute("mode");
	int winId = (Integer) request.getAttribute("winId");
	int pg = (Integer) request.getAttribute("pg");
	
	EventWin eventWin = (EventWin) request.getAttribute("eventWin");
	List<Event> eventSelectList = (List<Event>) request.getAttribute("EventSelectList");
	BoardConfig boardConfig = (BoardConfig) request.getAttribute("boardConfig");
%>

<div class="well">
	<form name="lform" action="" method="post">
	<input type="hidden" name="pg" vale="<%=pg %>" />
	</form>
	<form name="wform" action="" method="post">
	<input type="hidden" name="mode" value="<%=mode %>" />
	<input type="hidden" name="winId" value="<%=winId %>" />
	<table class="table">
		<col width="100"/>
		<col width="350"/>
		<tbody> 
		<tr>
			<th>제목(*)</th>
			<td>
				<input type="text" name="title" maxLength="128" value="<%=eventWin.getTitle() %>" />
			</td>
		</tr>
		<tr>
			<th>이벤트 명(*)</th>
			<td>
				<select name="eventId">
					<%
						for(Event list : eventSelectList){
					%>
						<option value="<%=list.getEventId()%>" <%if( eventWin.getEventId() == list.getEventId() ){ %>selected<%}%>><%=list.getTitle()%></option>
					<%
						}
					%>
				</select>
			</td>
		</tr>
		<tr>
			<th>내용(*)</th>
			<td>
				<tiles:insertDefinition name="daumeditor"/>
				<textarea id="contents-data" style="display:none;"><%=eventWin.getContents() %></textarea>
			</td>
		</tr>
		</tbody>
		</table>
	</form>
</div>
<!-- 버튼 영역 -->
<div class="btn-toolbar">
	<button class="btn btn-primary" onclick="javascript:checkForm();"><i class="icon-plus"></i> 저장</button>
	<%if(mode.equals("modify")){ %><button class="btn btn-danger" onclick="eventWinDelete();">삭제</button> <%}%>
	<button class="btn" onclick="goList();">리스트</button>
	<div class="btn-group">
	</div>
</div>

<script>
<!--
//load default contents data
$(document).ready(function(){
	Editor.modify({
		"content": document.getElementById("contents-data")
	});
});

function goList()
{
	var f = document.forms.lform;
	f.target = '_self';
	f.action = 'eventWinList.do';
	f.submit();
}

function checkForm()
{
	var f = document.forms.wform;
	
	if( f.title.value == '') return doError('제목을 입력하여주십시오.' , 'title' , 'wform');
	
	var validator = new Trex.Validator();
	var content = Editor.getContent();
	if (!validator.exists(content)) return alert('내용을 입력하세요');
	
	saveContent();
	
	f.target = '_self';
	f.action = 'eventWinAction.do';
	f.submit();
	
}

function eventWinDelete()
{
	if(confirm('해당 글을 삭제합니다.\r\n진행하시겠습니까?')){
		var f = document.forms.wform;
		f.target = '_self';
		f.action = 'eventWinAction.do'
		f.mode.value = 'delete'
		f.submit();
	}
}
//-->
</script>