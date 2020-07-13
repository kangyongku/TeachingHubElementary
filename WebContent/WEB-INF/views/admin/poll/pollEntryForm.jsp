<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	String mode = (String) request.getAttribute("mode");
	String findStr = (String) request.getAttribute("findStr");
	int pg = (Integer) request.getAttribute("pg");
	List<PollItem> lists = (List<PollItem>) request.getAttribute("pollItems");
	PollEntry pollEntry = (PollEntry) request.getAttribute("pollEntry");
	int entryId = (Integer) request.getAttribute("entryId");
	
%>
<div class="btn-toolbar">
    <% if ( pollEntry.getProgress().equals("N")|| pollEntry.getProgress().equals("")){ %>
	<button class="btn btn-primary" onclick="checkForm();"><i class="icon-plus"></i>저장</button>
	<%} %>
<%if( pollEntry.getProgress().equals("Y") ){ %>
		<button class="btn btn-primary" onclick="checkUpdateForm();"></i>수정</button>
	<%} %>
<%if( mode.equals("modify")){ %>
		<button class="btn btn-danger" onclick="deletePoll();">삭제</button>
	<%} %>
	<button class="btn" onclick="goList();">리스트</button>
	<div class="btn-group">
	</div>
</div>

<div class="well">
	<form name="dform" action="" method="post">
	<input type="hidden" name="mode" value="delete" />
	<input type="hidden" name="entryId" value="<%=entryId %>" />
	</form>
	
	<form name="lform" action="" method="post">
	<input type="hidden" name="pg" value="<%=pg %>" /> 
	</form>
		
	<form name="wform" action="" method="post">
	<input type="hidden" name="mode" value="<%=mode %>" />
	<input type="hidden" name="findStr" value="<%=findStr %>" />
	<input type="hidden" name="entryId" value="<%=entryId %>" />
	<input type="hidden" name="multiple" id="multiple" value="<%=pollEntry.getMultiple() %>" />
	<table class="table">
	<col width="150"/>
	<col width="150"/>
	<tbody>
	<tr>
		<th>질문</th>
		<td colspan="2">
			<input type="text" name="title" maxLength="128" value="<%=pollEntry.getTitle() %>"/>
		</td>
		<td colspan="1">
			<input type="checkbox" name="ansyn" id="ansyn" value="Y" <%if( pollEntry.getAnsyn().equals("Y") ){ %> checked <%}%> /> 필수답변
		</td>
	</tr>
	<tr>
		<th>종류</th>
		<td colspan="1" >
			<select id="choiceType" name="type">
			<option value="W" selected>주관식</option>
			<option value="M">객관식</option>
			<option value="D">객관식(중복선택)</option>
			</select>
		</td>
		<td colspan="2">
			<!-- <div class="multiSector">복수 응답 여부 : <input type="checkbox" id="multiple"  <%if( pollEntry.getMultiple().equals("Y") ){ %> checked <%}%>></div> -->
		</td>
		
	</tr>
	<tr class="multiSector">
		<th colspan="3">번호</th>
		<th>보기</th>
	</tr>
	</tbody>	
	<tbody class="multiSector" id="multiSector">
	<%
	int item_id = 0;
	int item_seq = 0;
	String item_answer = "";
	if(lists!=null){
		for(PollItem list : lists){
		// for reply articles
			item_seq = list.getSequence();
			item_id = list.getItemId();
			item_answer = list.getAnswer();
			if(item_seq<99){	
	%>
		<tr>
			<td colspan="1"><%=item_seq %></td>
			<td colspan="3"><input type="text" name="answer" maxLength="128" value="<%=item_answer %>"/></td>
			<!-- <td><a href="javascript:updateQuestion();">수정</a></td> -->
			<input type="hidden" name="itemId" value="<%=item_id%>" />
		</tr>
	<% }}} %>
	</tbody>
	<tbody class="etcSector" id=etcSector>
	<tr>
			<td colspan="1">99</td>
			<td colspan="3"><input type="text" name="answer" maxLength="128" value="기타" readOnly/></td>
			<input type="hidden" name="itemId" value="<%=item_id%>" />
	</tr>
	</tbody>
	<tbody class="multiSector">
	<tr>
	<% if ( pollEntry.getProgress().equals("N")|| pollEntry.getProgress().equals("")){ %>	
		<td><a href="javascript:addQuestion();">문항 추가</a></td>
		<td><a href="javascript:remQuestion();">문항 삭제</a></td>
		<td><a href="javascript:addEtc();">기타 추가</a></td>
	<%} %>		
	</tr>
	</tbody>
	</table>
	</form>
</div>

<!-- 스크립트 영역 -->
<script type="text/javascript">

<!--
//리스트
function goList()
{
	var f = document.forms.lform;
	f.target = '_self';
	f.action = 'pollEntryList.do';
	f.submit();
}

//폼 체크-저장
function checkForm()
{
		var f = document.forms.wform;
		var valCheck = true;
		
		if($("#choiceType option:selected").val()=="M"||$("#choiceType option:selected").val()=="D"){
			valCheck = fn_valCheck();
		}else{
			$('.multiSector').remove();
		}
		
		f.target = '_self';
		f.action = 'pollEntryAction.do';
		
		if(valCheck == true)
			f.submit();
}

//폼 체크-수정
function checkUpdateForm()
{
		var f = document.forms.wform;
		var valCheck = true;
		
		if($("#choiceType option:selected").val()=="M"||$("#choiceType option:selected").val()=="D"){
			valCheck = fn_valCheck();
		}else{
			$('.multiSector').remove();
		}
		
		f.target = '_self';
		f.action = 'pollEntryUpdateAction.do';
		
		if(valCheck == true)
			f.submit();
}

//정합성 체크
function fn_valCheck()
{
	var valChk = true;
	
	/**if( $("#multiple").is(":checked")==true )
		$("input[name=multiple]").attr("value","Y");
	else
		$("input[name=multiple]").attr("value","N");
	**/
	var answerIdx = $('input[name=answer]').length;
	if(answerIdx < 2){
		valChk = false;
		alert("객관식일 경우에는 두개 이상 입력하여주십시오.");
	}else{
		for(var i=0; i < answerIdx; i++){
			if( $('input[name=answer]').eq(i).val()=="" ){
				valChk = false;
				alert((i+1)+"번째 항목을 입력하여주십시오.");
				return false;
			}
		}
	}
	return valChk;
}

//항목추가 버튼
function addQuestion()
{
	var msLen = $("#multiSector").children().length;
	
	$("#multiSector").append(
		"<tr class='multiSector'>"
		+"<td colspan='1'>" + (msLen+1) + "</td>"
		+"<td colspan='3'><input type='text' name='answer' maxLength='128' value=''/></td>"
		+"<input type='hidden' name='itemId' />"
	)
}

//기타추가 버튼
function addEtc()
{
	if($("#multiple").val()!="Y") {
		$(".etcSector").show();
		$("#multiple").val("Y");

		
	}else{
		$(".etcSector").hide();
		$("#multiple").val("N");

	}
	
}

//항목삭제 버튼
function remQuestion()
{
	if($("#multiSector tr").length <= 1)
		alert("1개 이하일 경우 삭제할수 없습니다.");
	else
		$("#multiSector tr:last").remove();
}

//글 삭제 버튼
function deletePoll()
{
	var f = document.forms.dform;
	if(confirm('삭제된 데이타는 복구할 수 없습니다.\r\n진행하시겠습니까?')){
		f.target = '_self';
		f.action = 'pollEntryAction.do';
		f.submit();
	}
}

//종류 선택 
$("#choiceType").change(function(){
	var val = $(this).attr("value");
	if (val == "M" || val == "D"){
		$(".multiSector").show();
	}else{
		$(".multiSector").hide();
	}
});


//Form 로드시 설정
<%
	if(!mode.equals("ADD")){
		if(pollEntry.getType().equals("W")){
%>	
		$("#choiceType option[value=W]").attr("selected","true");
		$(".multiSector").hide();
		$(".etcSector").hide();
<%
		}else if(pollEntry.getType().equals("D")){
%>
		$("#choiceType option[value=D]").attr("selected","true");
		//alert($("#multiple").val());
		if($("#multiple").val()!="Y") $(".etcSector").hide();
<%	
		}else{
%>
		$("#choiceType option[value=M]").attr("selected","true");
		//alert($("#multiple").val());
		if($("#multiple").val()!="Y") $(".etcSector").hide();
<%	
		}
	}
%>
//-->
</script>