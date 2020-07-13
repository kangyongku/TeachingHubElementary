<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	int pg = (Integer) request.getAttribute("pg");
	String paging = (String) request.getAttribute("paging");
	int pollNum = (Integer) request.getAttribute("pollNum");
	List<Poll> list = (List<Poll>) request.getAttribute("list");
	int totalPoll = (Integer) request.getAttribute("totalPoll");
	
	String findStr = (String) request.getAttribute("findStr");
%>
<div class="btn-toolbar">
	<button class="btn btn-primary" onclick="goForm();"><i class="icon-plus"></i>설문조사 등록</button>
</div>
<form name="qform" action="pollForm.do" method="get">
	<input type="hidden" name="pollId" />
	<input type="hidden" name="mode"/>
	<input type="hidden" name="pg" value="<%=pg%>" />
</form>

<form name="listForm" action="pollList.do" method="get">
	<input type="hidden" name="pg" value="<%=pg%>" />
	<input type="hidden" name="findStr" value="<%=findStr%>"/>
</form>

<div class="well">
	<table class="table">
	<col width="50"/>
	<col width="100"/>
	<col width="100"/>
	<col width="100"/>
	<col width="50"/>
	<col width="50"/>
	<col width="50"/>
	<thead>
	<tr>
		<th>번호</th>
		<th>제목</th>
		<th>기간</th>
		<th>등록일</th>
		<th>작성자</th>
		<th>공개</th>
		<th>항목 추가</th>
	</tr> 
	</thead>
	<tbody>
	<% if(totalPoll > 0){  %>
		<%
			for(Poll poll : list){
			// for reply articles
		%>
			<tr>
				<td><%=pollNum%></td>
				<td><a href="javascript:goForm('<%=poll.getPollId()%>');"><%=poll.getSubject() %></a></td>
				<td><%=poll.getStartDate() %> ~ <%=poll.getEndDate() %></td>
				<td><%=poll.getRegDate() %></td>
				<td><%=poll.getRegId()%></td>
				<td><%if( poll.getIsUse().equals("Y") ){ %>공개<%}else{ %>비공개<%} %></td>
				<td><a href="javascript:showEntryPopup('<%=poll.getPollId() %>');">등록</a></td>
			</tr>
		<%	
			pollNum--;
			}
		%>
	<%}else{ %>	
	<tr>
		<td colspan="5" style="height:80px;text-align:center;">
		[ 등록된 게시물이 없습니다. ]
		</td>
	</tr>
	<%} %>
	
	</tbody>
	</table>
</div>
<div class="pagination">
	<ul>
		<%=paging %>
	</ul>
</div>

<!-- Layer 영역  / entryForm 영역-->
<div class="modal small" id="entryPopup" style="display:none;">
	<form id="entryForm" name="entryForm" action="" method="post">
	<input type="hidden" name="nonEntryId" />
	<input type="hidden" name="pollId" />
    <div class="modal-header">
        <button type="button" class="close unblock">×</button>
        <h3>설문조사 문항 등록</h3>
    </div>
    <div class="modal-body" style="text-align:left;">
       	<div id="entryList">
       		<table class="table">
       		<col width="60"/>
       		<col width="120"/>
			<col width="60"/>
	
       		<thead>
       			<th>선택</th>
       			<th>제목</th>
       			<th>유형</th>
       		</thead>
       		<tbody id="entryPopupBody">
       		</tbody>
       		</table>
       	</div>
    </div>
    <div class="modal-footer">
    	<a href="javascript:saveEntryPoll();" class="btn btn-danger"></i>저장</a>
        <button type="button" class="btn unblock">취소</button>
    </div>
	</form>
</div>

<!-- 스크립트 영역 -->
<script>
<!--
function goForm(pollId)
{	
	var f = document.forms.qform;
	
	if( pollId == undefined )
	{
		pollId = '0';
		f.mode.value = "add";
	}
	else
	{
		f.mode.value = "modify";
	}
	f.pollId.value = pollId;
	f.submit();
}

function goList(pg)
{
	var f = document.forms.listForm;
	
	f.pg.value = pg;
	f.submit();
}

//설문 문항 팝업
function showEntryPopup(pollId)
{
	$("#entryPopupBody").children().remove();
	$("#entryForm input[name=pollId]").attr("value", pollId);
	$.ajax({
		url  : '/admin/poll/pollEntryListAction.do',
		type : 'post' , 
		data : 'pollId=' + pollId,
		dataType : 'json' ,
		error : function(){
			alert('처리도중 에러가 발생되었습니다.');
		},
		success : function(json){
			if( json.result == 'SUCCESS' )
			{
				if( json.pollEntryed.length > 0)
				{
					for(var i=0; i<json.pollEntryed.length; i++){
						var jsonObj = json.pollEntryed[i];
						if(jsonObj.type=="M")
							type="객관식";
						else if(jsonObj.type=="D")
							type="객관식(중복)";
												
						else
							type="주관식";
						$("#entryPopupBody").append(
							"<tr><td><input type='checkbox' class='checkPolled' value='" + jsonObj.entryId + "' checked/></td>"+ 
							"<td>" + jsonObj.title + "</td>"+
							"<td>" + type+"</td></tr>"
						)
					}
				}
				if( json.pollEntry.length > 0)
				{
					for(var i=0; i<json.pollEntry.length; i++)
					{
						var jsonObj = json.pollEntry[i];
						var type = "";
						if(jsonObj.type=="M")
							type="객관식";
						else if(jsonObj.type=="D")
							type="객관식(중복)";						
						else
							type="주관식";
						$("#entryPopupBody").append(
							"<tr><td><input type='checkbox' name='checkPoll' value='" + jsonObj.entryId + "'/></td>"+ 
							"<td>" + jsonObj.title + "</td>"+
							"<td>" + type+"</td></tr>"
						)
					}	
				}
				//완료시 BlockUI 생성
				$.blockUI({
					message : $('#entryPopup'),
					onBlock: function() { 
			            $('.unblock').click(function(){
			            	$.unblockUI();
			            });
			        }
				});
			}
			else
				alert(json.msg);
		}
	});
	
}

//설문 문항 저장
function saveEntryPoll(){
	var pollObj = $(".checkPolled").not(":checked");
	
	//체크 되어있는 Item 해제시 DB수정
	if(pollObj.length > 0){
		var obj = "";
		for(var i=0; i<pollObj.length; i++){
			obj += pollObj.eq(i).val() + ",";
		}
		$("input[name=nonEntryId]").attr("value",obj);
	}
	//ajax Call
	$.ajax({
		url  : '/admin/poll/updatePollEntryAction.do',
		type : 'post' , 
		data : $("#entryForm").attr("action","updatePollEntryAction").serialize(), 
		dataType : 'json' ,
		error : function(){
			alert('처리도중 에러가 발생되었습니다.');
		},
		success : function(json){
			if( json.result == 'SUCCESS' )
			{
				alert('변경사항이 저장되었습니다.');
						$.unblockUI();
			}
			else
				alert(json.msg);
		}
		
	})
}
-->
</script>