<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	Poll poll = (Poll) request.getAttribute("poll");
	String mode = (String) request.getAttribute("mode");
	int pollId = (Integer) request.getAttribute("pollId");

	int pg = (Integer) request.getAttribute("pg");
	String findStr = (String) request.getAttribute("findStr");
	
	List<PollEntryItem> pollEntryItems = (List<PollEntryItem>) request.getAttribute("pollEntryItems");
	
%>
<div class="btn-toolbar">
	<button class="btn btn-primary" onclick="checkForm();"><i class="icon-plus"></i> 저장</button>
	<%if(mode.equals("modify")){ %><button class="btn btn-danger" onclick="deletePoll();">삭제</button> <%}%>
	<button class="btn" onclick="goList();">리스트</button>
	<div class="btn-group">
	</div>
</div>
<div class="well">
	<form name="lform" action="" method="">
	<input type="hidden" name="pg" value="<%=pg%>" />
	</form>
	<form name="pform" action="" method="post">
	<input type="hidden" name="pollId" value="<%=pollId %>" />
	</form>
	
	<form name="wform" action="" method="post">
	<input type="hidden" name="mode" value="<%=mode %>" />
	<input type="hidden" name="pollId" value="<%=pollId %>" />
	<input type="hidden" name="pg" value="<%=pg %>" />
	<input type="hidden" name="findStr" value="<%=findStr %>" />
	<table class="table">
	<col width="150"/>
	<col width="150"/>
	<tbody> 
	<tr>
		<th>설문지 제목</th>
		<td colspan="4">
			<input type="text" name="subject" maxLength="128" value="<%=poll.getSubject() %>" />
		</td>
	</tr>
	<tr>
		<th>대상</th>
		<td colspan="4">
		<select name="target">
			<option value="A" <%if( poll.getTarget().equals("A")){%>selected<%}%>>전체</option>
			<option value="B" <%if( poll.getTarget().equals("B")){%>selected<%}%>>초등</option>
			<option value="C" <%if( poll.getTarget().equals("C")){%>selected<%}%>>중등</option>
			<option value="D" <%if( poll.getTarget().equals("D")){%>selected<%}%>>고등</option>
		</select>
		</td>
	</tr>
	
	<tr>
		<th>시작일</th>
		<td colspan="4">
		<input type="text" id="txtDateS" name="startDate"  value="<%=poll.getStartDate() %>" readonly> <br/>
		</td>
	</tr>
	<tr>
		<th>종료일</th>
		<td colspan="4">
		<input type="text" id="txtDateE" name="endDate" value="<%=poll.getEndDate() %>" readonly> <br/>
		</td>
	</tr>
	<tr>
		<th>공개 유무</th>
		<td colspan="4">
			<input type="radio" name="isUse" value="Y" <%if ( poll.getIsUse().equals("Y") ){%> checked <%}%>> 공개
			<input type="radio" name="isUse" value="N" <%if ( poll.getIsUse().equals("N") ){%> checked <%}%>> 비공개
		</td>
	</tr>
	<tr>
		<th>결과노출 유무</th>
		<td colspan="4">
			<input type="radio" name="isResult" value="Y" <%if ( poll.getIsResult().equals("Y") ){%> checked <%}%>> 공개
			<input type="radio" name="isResult" value="N" <%if ( poll.getIsResult().equals("N") ){%> checked <%}%>> 비공개
		</td>
	</tr>
	</tbody>
	</table>
	</form>
</div>

<% 
	if (pollEntryItems.size() > 0)
	{ 
%>
	<!-- 설문항목 체크 -->
	<div class="well">
		<table>
		<col width="150"/>
		<col width="300"/>
		<tbody class="table">
	<%
		int beforeId = 0;
		int listIndex = 1;
		int seq = 0;
		for(PollEntryItem list : pollEntryItems){
		// for reply articles
			
			if(list.getEntryId() != beforeId){
	%>	
			<tr><td colspan="2"></td></tr>
			<tr>
				<th>질문<%=listIndex %></td>
				<th><%=list.getTitle() %><%if(list.getAnsyn().equals("Y")){ %>&nbsp;&nbsp;( * )<%} %></td>
			</tr>
			
			<% 
				seq = 1;
				listIndex++;
			
				if(list.getType().equals("M")||list.getType().equals("D")){
			%>
			<tr>
				<td><%=seq %></td>
				<td><%=list.getAnswer() %></td>
			</tr>
			
			<%
				}else{
			%>
				<tr>
					<td colspan="2"> 주관식 </td>
				</tr>
			<%
				}
			%>
			
	<%
			}else{
				seq++;
	%>
			<tr>
				<td><%=seq %></td>
				<td><%=list.getAnswer() %></td>
			</tr>
	<%
			}
			beforeId = list.getEntryId();
			
		}
	%>
	</tbody>
	</table>
</div>
			
<% 
		
}
%>
		
	
<script type="text/javascript">
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

<!--
// load default contents data
function goList()
{
	var f = document.forms.lform;
	f.target = '_self';
	f.action = 'pollList.do';
	f.submit();
}

function checkForm()
{
	var f = document.forms.wform;
	
	f.target = '_self';
	f.action = 'pollAction.do';
	f.submit();
	
}

function deletePoll()
{
	var f = document.forms.wform;
	if(confirm('삭제된 데이타는 복구할 수 없습니다.\r\n진행하시겠습니까?')){
		f.target = '_self';
		f.action = 'pollAction.do'
		f.mode.value = 'delete'
		f.submit();
	}
}
//-->
</script>