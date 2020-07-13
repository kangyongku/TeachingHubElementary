<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="kr.co.kumsung.thub.domain.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" 			prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" 	prefix="fn"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" 			prefix="fmt" %>
<%
	int pg = (Integer) request.getAttribute("pg");
	//String mode = (String) request.getAttribute("mode");
	//List<Member> AccessList = (List<Member>) request.getAttribute("AccessList");
	Member accessMember = (Member) request.getAttribute("accessMember");
	//Member authMap = (Member)request.getAttribute("authMap");
	//int authId = (Integer)request.getAttribute("authId");
	
%>

<style>
.table th,.table td {
	border:1px solid #ddd;
	vertical-align: middle;
}

.items{
	margin:0px;
	padding:0px;
	padding-left:50px;
}

.items > li{
	list-style:none;
	float:left;
	width:200px;
	margin:3px;
	vertical-align:middle;
}

.well input{
	display:inline;
	margin-top:-3px;
}

.well h3{
	font-size:1.5em;
}

.items > li span{
	display:inline;
	font-weight:normal;
	font-size:1.2em;
	padding-left:3px;
}
</style>
<form name="lform" action="" method="post">
	<input type="hidden" name="pg" value="<%=pg %>" />
</form>
<div class="well">
	<%-- <%if( mode.equals("add") ){ %> --%>
		<form name="sform" action="" method="post" onsubmit="findUser();return false;">
		<table class="table">
			<col width="120" />
			<col width="*" />
			<tbody>
				<tr>
					<th>아이디</th>
					<td>
						<!-- <input type="text" name="findUserId"/> -->
						<select name="findUserId" onchange="javascript:findUser(this.value);">
							<option value="">아이디</option>
							<%-- <% for(Member accessId : AccessList){ %>
							<option value="<%=accessId.getUserId() %>"><%=accessId.getUserId() %></option>
							<option value="<%=accessId.getUserId() %>" <c:if test="${accessId.getUserId() == accessMember.getUserId() }">selected</c:if>>${accessId.getUserId() }</option>
							<% } %> --%>
							<c:forEach items="${AccessList }" var="list">
			                 	<option value="${list.getUserId() }" <c:if test="${list.getUserId() == accessMember.getUserId() }">selected</c:if>>${list.getUserId() }</option>
			                 </c:forEach>
						</select>
					</td>
				</tr>
			</tbody>
		</table>
		</form>
<%-- 	<%} %> --%>
	
	<form name="wform" action="" method="post">
	<input type="hidden" name="mode" value="" />
	<input type="hidden" name="userId" value="<%=accessMember.getUserId() %>" />
	<table class="table">
	<col width="120"/>
	<col width="50"/>
	<col width="100"/>
	<col width="120"/>
	<col width="80"/>
	<col width="120"/>
	<tbody> 
	<tr>
		<th>이름</th>
		<td colspan="5"><span id="name"><%=accessMember.getName() %></span></td>
	</tr>
	<tr>
		<th>아이디</th>
		<td colspan="5"><span id=userId><%=accessMember.getUserId() %></span></td>
	</tr>
	<tr>
		<th>구분</th>
		<td colspan="5"><span id="authType" name="authType"><%=accessMember.getAuthType() %></span></td>
	</tr>
	<tr>
		<th>등록 IP 주소</th>
		<td colspan="5"><input type="text" id="ip" name="ip" maxlength="15" value="<%=accessMember.getIp() %>"/></td>
	</tr>
	</tbody>
	</table>
	
	</form>
</div>

<!-- 버튼 영역 -->
<div class="btn-toolbar">
	<button class="btn btn-primary" onclick="checkForm();">
		<i class="icon-plus"></i> 등록
	</button>
	<div class="btn-group"></div>
	<button class="btn" onclick="goList();">리스트</button>
	<div class="btn-group"></div>
	<button class="btn" onclick="goDelete();">삭제</button>
	
</div> 

<!-- 스크립트 영역 -->
<script type="text/javascript">
<!--

$(document).ready(function(){
	
	<%-- 
	//수정시 초기값 세팅
	<%if ( mode.equals("add") ){%>
		$("#findUserDiv").hide();
	<%}%>
	<%if ( mode.equals("modify") ){%>
	<%}%>
	 --%>
})


function findUser(val)
{
	if( val != '' ){
		$.ajax({
			url : 'findAccessUserInfo.do' ,
			type : 'post' ,
			data : 'userId=' + val ,
			dataType : 'json' ,
			error : function(){
				alert('서버와의 통신도중 에러가 발생되었습니다.');
			},
			success : function(json){
				if( json.result == 'SUCCESS' )
				{
					$("#name").text(json.accessMember.name);
					$("#userId").text(json.accessMember.userId);
					$('input[name=userId]').val(json.accessMember.userId);
					$("#ip").val(json.accessMember.ip);
					$("#authType").text(json.accessMember.authType);
				}
				else
				{
					alert(json.msg);
				}
			}
		});
	}
}

function goList() {
	var f = document.forms.lform;
	f.target = '_self';
	f.action = 'accessList.do';
	f.submit();
}
function goDelete() {
	if(confirm("IP를 삭제 하시겠습니까?") == true){
		var wf = document.forms.wform;
		var sf = document.forms.sform;
		
		if( sf.findUserId.value == ''){
			return doError('아이디를 선택해 주십시오.' , 'findUserId' , 'sform');
		}
		
		wf.mode.value="delete";
		wf.target = '_self';
		wf.action = 'accessAction.do';
		wf.submit();	
	}else{
		return;
	}
}
function checkForm() {
	var sf = document.forms.sform;
	var wf = document.forms.wform;
	
	if( sf.findUserId.value == ''){
		return doError('아이디를 선택해 주십시오.' , 'findUserId' , 'sform');
	}
	
	if($("#ip").val() == ""){
		 return doError('IP 주소를 입력해주세요 .' , 'ip' , 'wform');
	}
	
	wf.mode.value="modify";
	wf.target = '_self';
	wf.action = 'accessAction.do'
	wf.submit();
	
}
//-->
</script>