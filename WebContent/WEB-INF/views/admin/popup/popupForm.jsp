<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	String mode = (String) request.getAttribute("mode");
	int popupId = (Integer) request.getAttribute("popupId");
	int pg = (Integer) request.getAttribute("pg");
	Popup popup = (Popup) request.getAttribute("popup");
%>

<div class="well">
<form name="lform" action="" method="post">
</form>
<form name="wform" action="" method="post">
<input type="hidden" name="mode" value="<%=mode %>" />
<input type="hidden" name="popupId" value="<%=popupId %>" />
<table class="table">
	<col width="20%"/>
	<col width="30%"/>
	<col width="20%"/>
	<col width="30%"/>
	<tbody> 
	<tr>
		<th>노출 기간(*)</th>
		<td colspan="3">
		<input type="text" id="txtDateS" name="startDate" style="width:100px" value="<%=popup.getStartDate() %>" readonly> 
		~
		<input type="text" id="txtDateE" name="endDate" style="width:100px" value="<%=popup.getEndDate() %>" readonly>
		</td>
	</tr>
	<tr>
		<th>대상(*)</th>
		<td colspan="3">
			<input type="radio" name="target" value="THUB" <% if( popup.getTarget().equals("THUB")){ %>checked<% } %>/> 티칭허브
			<input type="radio" name="target" value="TENC" <% if( popup.getTarget().equals("TENC")){ %>checked<% } %>/> 티칭백과
		</td>
	</tr>
	<tr>
		<th>제목(*)</th>
		<td colspan="3">
			<input type="text" name="title" style="width:800px" maxLength="128" value="<%=popup.getTitle() %>" />
		</td>
	</tr>
	<tr>
		<th>사이즈(*)</th>
		<td>
			가로 <input type="text" name="sizeWidth" style="width:50px" value="<%=popup.getSizeWidth() %>" /> X &nbsp
			세로 <input type="text" name="sizeHeight" style="width:50px" value="<%=popup.getSizeHeight() %>" />
		</td>
		<th>위치(*)</th>
		<td>
			가로 <input type="text" name="positionWidth" style="width:50px" value="<%=popup.getPositionWidth() %>" /> X &nbsp
			세로 <input type="text" name="positionHeight" style="width:50px" value="<%=popup.getPositionHeight() %>" />
		</td>
	</tr>	
	<tr>
		<th>
			형태(*)
		</th>
		<td>
			<input type="radio" name="type" value="P" <% if( popup.getType().equals("P")){ %>checked<% } %>/> 팝업
			<input type="radio" name="type" value="L" <% if( popup.getType().equals("L")){ %>checked<% } %>/> 레이어
		</td>
		<th>
			노출 유무(*)
		</th>
		<td>
			<input type="radio" name="isUse" value="Y" <% if( popup.getIsUse().equals("Y")){ %>checked<% } %>/> 노출
			<input type="radio" name="isUse" value="N" <% if( popup.getIsUse().equals("N")){ %>checked<% } %>/> 미노출
		</td>
	</tr>
	<tr>
		<th>내용(*)</th>
		<td colspan="3" style="padding-top:18px;">
			<tiles:insertDefinition name="daumeditor"/>
			<textarea id="contents-data" style="display:none;"><%=popup.getContents() %></textarea>
		</td>
	</tr>	
	</tbody>
	</table>
</form>
</div>
<!-- 버튼 영역 -->
<div class="btn-toolbar">
	<button class="btn btn-primary" onclick="checkForm();"><i class="icon-plus"></i> 저장</button>
	<%if(mode.equals("modify")){ %><button class="btn btn-danger" onclick="popupDelete();">삭제</button> <%}%>
	<button class="btn" onclick="goList();">리스트</button>
	<div class="btn-group">
	</div>
</div>

<!-- 스크립트 영역 -->
<script type="text/javascript">
<!--

//load default contents data
$(document).ready(function(){
	Editor.modify({
		"content": document.getElementById("contents-data")
	});
	
	<% if( mode.equals("add") ){ %>
		init();
	<%}%>

});

function goList()
{
	var f = document.forms.lform;
	f.target = '_self';
	f.action = 'popupList.do';
	f.submit();
}

function checkForm()
{
	var f = document.forms.wform;
	
	// save contents
	saveContent();
	
	if( f.title.value == '') return doError('제목을 입력하여주십시오.' , 'title' , 'wform');
	if( f.sizeWidth.value == '') return doError('가로 사이즈를 입력하여주십시오.' , 'sizeWidth' , 'wform');
	if( f.sizeHeight.value == '') return doError('세로 사이즈를 입력하여주십시오.' , 'sizeHeight' , 'wform');
	if( f.positionWidth.value == '') return doError('가로 위치를 입력하여주십시오.' , 'positionWidth' , 'wform');
	if( f.positionHeight.value == '') return doError('세로 위치를 입력하여주십시오.' , 'positionHeight' , 'wform');
	if( f.startDate.value == '') return doError('노출기간을 입력하여주십시오.' , 'startDate' , 'wform');
	if( f.endDate.value == '') return doError('노출기간을 입력하여주십시오.' , 'endDate' , 'wform');
	if( f.contents.value == '') return doError('내용을 입력하여주십시오.' , 'contents' , 'wform');
	
	f.target = '_self';
	f.action = 'popupAction.do';
	f.submit();
	
}

function popupDelete()
{
	if(confirm('해당 글을 삭제합니다.\r\n진행하시겠습니까?')){
		var f = document.forms.wform;
		f.target = '_self';
		f.action = 'popupAction.do'
		f.mode.value = 'delete'
		f.submit();
	}
}

$("#txtDateS").datepicker({
	monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월' ],
	dayNamesMin: ['일','월','화','수','목','금','토'],
	dateFormat: 'yy-mm-dd'
});

$("#txtDateE").datepicker({
	monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월' ],
	dayNamesMin: ['일','월','화','수','목','금','토'],
	dateFormat: 'yy-mm-dd'
});

//등록시 기본값 세팅
function init(){
	$("input[name=target]").eq(0).attr("checked",true);
	$("input[name=type]").eq(0).attr("checked",true);
	$("input[name=isUse]").eq(0).attr("checked",true);
	$("input[name=sizeWidth]").val(400);
	$("input[name=sizeHeight]").val(400);
	$("input[name=positionWidth]").val(50);
	$("input[name=positionHeight]").val(50);
}
//-->
</script>