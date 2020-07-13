<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	int pg = (Integer)request.getAttribute("pg");
	String mode = (String)request.getAttribute("mode");
	
	List<TrainingRequest> trainingReqList = (List<TrainingRequest>)request.getAttribute("trainingReqList");
	Training training = (Training)request.getAttribute("training");
	int trainingId = (Integer)request.getAttribute("trainingId");
	int totalTrainingRequest = (Integer)request.getAttribute("totalTrainingRequest");
	
%>
<form name="dform" action"" method="post">
<input type="hidden" name="mode" value="delete">
<input type="hidden" name="trainingId" value="<%=trainingId %>" />
</form>
<form name="lform" action="" method="post">
<input type="hidden" name="pg" value="<%=pg%>"/>
</form>
<form name="wform" action="" method="post">
<input type="hidden" name="trainingId" value="<%=trainingId %>" />
<input type="hidden" name="mode" value="<%=mode %>" />
<div class="well">
	
	<table class="table">
	<col width="100"/>
	<col width="*"/>
		<tbody>
		<tr>
			<th>연수 명(*)</th>
			<td>
				<input type="text" name="title" maxLength="128" value="<%=training.getTitle() %>"/>
			</td>
		</tr>
		<tr>
			<th>연수기간(*)</th>
			<th>
				<input type="text" name="startDate" value="<%=training.getStartDate() %>" readonly/>
				~
				<input type="text" name="endDate" value="<%=training.getEndDate() %>" readonly/>
			</th>
		</tr>
		<tr>
			<th>내용</th>
			<td colspan="3">
				<tiles:insertDefinition name="daumeditor"/>
				<textarea id="contents-data" style="display:none;"><%=training.getContents() %></textarea>
			</td>
		</tr>
	</tbody>	
	</table>
	</form>
</div>
<%
	if(totalTrainingRequest > 0){
		if(totalTrainingRequest > 10)
			totalTrainingRequest = 10;
		
	%>
	<div class="frameSet">
		<p class="popover-title">■ 신청자</p>
		<iframe src="trainingRequestFrame.do?trainingId=<%=trainingId%>" style="border:0 solid #FFFFFF"  width="980" height="<%=200+(57*totalTrainingRequest) %>"></iframe>
	</div>
<%} %>
<div class="btn-toolbar">
	<button class="btn btn-primary" onclick="checkForm();"><i class="icon-plus"></i>저장</button>
	<%if(mode.equals("modify")){ %>
		<button class="btn btn-danger" onclick="goDelete();">삭제</button>
	<%} %>	
	<button class="btn" onclick="goList();">리스트</button>
</div>

<!-- 스크립트 영역 -->
<script type="text/javascript">
//load default contents data
<!--
$(document).ready(function(){
	Editor.modify({
		"content": document.getElementById("contents-data")
	});
});

$("input[name=startDate]").datepicker({
	monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월' ],
	dayNamesMin: ['일','월','화','수','목','금','토'],
	dateFormat: 'yy-mm-dd'
});
$("input[name=endDate]").datepicker({
	monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월' ],
	dayNamesMin: ['일','월','화','수','목','금','토'],
	dateFormat: 'yy-mm-dd'
});

function goList(pg){
	var f = document.forms.lform;
	if(pg == undefined)
		f.pg.value = 1;
	else
		f.pg.value = pg;
	
	f.action = "trainingList.do";
	f.submit();
}
function checkForm(){
	
	var f = document.forms.wform;
	
	// save contents
	saveContent();
	
	if( f.title.value == '') return doError('타이틀을 입력하여주십시오.' , 'title' , 'wform');
	
	if( f.startDate.value == '' ) return doError('시작일을 선택하여주십시오.' , 'startDate' , 'wform');
	if( f.endDate.value == '' ) return doError('종료일을 선택하여주십시오.' , 'endDate' , 'wform');
	if( f.contents.value == '' ) return doError('내용을 입력하세요.' , 'contents' , 'wform');
		
	f.target = '_self';
	f.action = 'trainingAction.do';
	f.submit();
}
function goDelete(){
	var f = document.forms.dform;
	if(confirm('해당 글을 삭제합니다.\r\n진행하시겠습니까?')){
		f.target = '_self';
		f.action = 'trainingAction.do';
		f.submit();
	}
}
//-->
</script>