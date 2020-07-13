<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	int pg = (Integer)request.getAttribute("pg");
	String mode = (String)request.getAttribute("mode");
	
	TrainingPostscript trainingPostscript = (TrainingPostscript)request.getAttribute("trainingPostscript");
	List<Training> trainingIdList = (List<Training>)request.getAttribute("trainingIdList");
	int postId = (Integer)request.getAttribute("postId");
%>
<form name="dform" action="" method="post">
<input type="hidden" name="mode" value="delete"/>
<input type="hidden" name="postId" value="<%=postId %>"/>
</form>
<form name="lform" action="" method="post">
<input type="hidden" name="pg" value="<%=pg%>"/>
</form>
<form name="wform" action="" method="post">
<input type="hidden" name="postId" value="<%=postId %>" />
<input type="hidden" name="mode" value="<%=mode %>" />
<div class="well">
	
	<table class="table">
	<col width="100"/>
	<col width="*"/>
		<tbody>
		<tr>
			<th>제목(*)</th>
			<td>
				<input type="text" name="title" maxLength="128" value="<%=trainingPostscript.getTitle() %>"/>
			</td>
		</tr>
		<tr>
			<th>연수 명(*)</th>
			<td>
				<select name="trainingId">
					<%
						for(Training list : trainingIdList){
					%>
						<option value="<%=list.getTrainingId()%>" <%if(list.getTrainingId() == trainingPostscript.getTrainingId() ){ %>selected<%} %>><%=list.getTitle()%></option>
					<%
						}
					%>
			
				</select>				
			</td>
		</tr>
		<tr>
			<th>후기 내용(*)</th>
			<td colspan="3">
				<tiles:insertDefinition name="daumeditor"/>
				<textarea id="contents-data" style="display:none;"><%=trainingPostscript.getContents() %></textarea>
			</td>
		</tr>
	</tbody>	
	</table>
	</form>
</div>
<div class="btn-toolbar">
	<button class="btn btn-primary" onclick="checkForm();"><i class="icon-plus"></i>저장</button>
	<%if(mode.equals("modify")){ %>
	<button class="btn btn-danger" onclick="goDelete();">삭제</button>
	<%}%>
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
function goList(pg){
	var f = document.forms.lform;
	if(pg == undefined)
		f.pg.value = 1;
	else
		f.pg.value = pg;
	
	f.action = "trainingPostscriptList.do";
	f.submit();
}
function checkForm(){
	
	var f = document.forms.wform;
	
	// save contents
	saveContent();
	
	if( f.title.value == '') return doError('타이틀을 입력하여주십시오.' , 'title' , 'wform');
	if( f.contents.value == '' ) return doError('내용을 입력하세요.' , 'contents' , 'wform');
	
	f.target = '_self';
	f.action = 'trainingPostscriptAction.do';
	f.submit();
}
function goDelete(){
	var f = document.forms.dform;
	
	if(confirm('해당 글을 삭제합니다.\r\n진행하시겠습니까?')){
		f.target = '_self';
		f.action = 'trainingPostscriptAction.do';
		f.submit();
	}
}
//-->
</script>