<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	String mode = (String) request.getAttribute("mode");
	int id = (Integer) request.getAttribute("id");
	
	
	int pg = (Integer) request.getAttribute("pg");
	String findStr = (String) request.getAttribute("findStr");
	String findMethod = (String) request.getAttribute("findMethod");
	String findIsFinallyAuth = (String) request.getAttribute("findIsFinallyAuth");
	String findAuthType = (String) request.getAttribute("findAuthType");
	
	Member member = (Member) request.getAttribute("member");
%>
<style>
	.table th, .table td {
	vertical-align: middel;
}
</style>
<div class="well">
<form name="lform" method="post">
	<input type="hidden" name="pg" value="<%=pg %>" />
	<input type="hidden" name="findStr" value="<%=findStr %>" />
	<input type="hidden" name="findMethod" value="<%=findMethod %>" />
	<input type="hidden" name="findIsFinallyAuth" value="<%=findIsFinallyAuth %>" />
	<input type="hidden" name="findAuthType" value="<%=findAuthType %>" />
</form>
<form name="wform" action="" method="post">
<input type="hidden" name="mode" value="<%=mode %>" />
<input type="hidden" name="id" value="<%=id %>" />
<table class="table">
	<col width="120"/>
	<col width="50"/>
	<col width="100"/>
	<col width="120"/>
	<col width="80"/>
	<col width="120"/>
	<tbody> 
	<tr>
		<th>이름(아이디)</th>
		<td colspan="5"><%=member.getName() %>(<%=member.getUserId() %>)</td>
	</tr>
	<tr>
		<th>주소</th>
		<td colspan="5">
			[<%=member.getZipcode() %>]
			<%=member.getAddress1() %>&nbsp
			<%=member.getAddress2() %>
		</td>
	</tr>
	<tr>
		<th>집전화번호</th>
		<td colspan="5">
			<select name="tel1" style="width:100px">
				<option value="">선택하세요</option>
				<option value="02" <% if( member.getTel1().equals("02")){ %>selected<% } %>>02</option>
				<option value="031" <% if( member.getTel1().equals("031")){ %>selected<% } %>>031</option>
				<option value="032" <% if( member.getTel1().equals("032")){ %>selected<% } %>>032</option>
				<option value="033" <% if( member.getTel1().equals("033")){ %>selected<% } %>>033</option>
				<option value="041" <% if( member.getTel1().equals("041")){ %>selected<% } %>>041</option>
				<option value="042" <% if( member.getTel1().equals("042")){ %>selected<% } %>>042</option>
				<option value="043" <% if( member.getTel1().equals("043")){ %>selected<% } %>>043</option>
				<option value="051" <% if( member.getTel1().equals("051")){ %>selected<% } %>>051</option>
				<option value="052" <% if( member.getTel1().equals("052")){ %>selected<% } %>>052</option>
				<option value="053" <% if( member.getTel1().equals("053")){ %>selected<% } %>>053</option>
				<option value="054" <% if( member.getTel1().equals("054")){ %>selected<% } %>>054</option>
				<option value="055" <% if( member.getTel1().equals("055")){ %>selected<% } %>>055</option>
				<option value="061" <% if( member.getTel1().equals("061")){ %>selected<% } %>>061</option>
				<option value="062" <% if( member.getTel1().equals("062")){ %>selected<% } %>>062</option>
				<option value="063" <% if( member.getTel1().equals("063")){ %>selected<% } %>>063</option>
				<option value="064" <% if( member.getTel1().equals("064")){ %>selected<% } %>>064</option>
				<option value="070" <% if( member.getTel1().equals("070")){ %>selected<% } %>>070</option>
				<option value="0505" <% if( member.getTel1().equals("0505")){ %>selected<% } %>>0505</option>
			</select>
			<input type="text" name="tel2" maxLength="4" value="<%=member.getTel2() %>" style="width:100px"/>
			<input type="text" name="tel3" maxLength="4" value="<%=member.getTel3() %>" style="width:100px"/>
		</td>
	</tr>
	<tr>
		<th>휴대전화(*)</th>
		<td colspan="3">
			<select name="hp1" style="width:100px">
				<option value="">선택하세요</option>
				<option value="010" <% if( member.getHp1().equals("010")){ %>selected<% } %>>010</option>
				<option value="011" <% if( member.getHp1().equals("011")){ %>selected<% } %>>011</option>
				<option value="016" <% if( member.getHp1().equals("016")){ %>selected<% } %>>016</option>
				<option value="017" <% if( member.getHp1().equals("017")){ %>selected<% } %>>017</option>
				<option value="018" <% if( member.getHp1().equals("018")){ %>selected<% } %>>018</option>
				<option value="019" <% if( member.getHp1().equals("019")){ %>selected<% } %>>019</option>
			</select>
			<input type="text" name="hp2" maxLength="4" value="<%=member.getHp2() %>" style="width:100px"/>
			<input type="text" name="hp3" maxLength="4" value="<%=member.getHp3() %>" style="width:100px"/>
		</td>
		<th>휴대전화<br />동의여부</th>
		<td><%=member.getIs_sms() %></td>		
	</tr>
	<tr>
		<th>E-mail(*)</th>
		<td colspan="3">
			<%=member.getEmail() %>
		</td>
		<th>이메일<br />동의여부</th>
		<td><%=member.getIsremail() %></td>
	</tr>
	<tr>
		<th>직업</th>
		<td colspan="5"><%=member.getJobName() %></td>
	</tr>
	<tr>
		<th>방문목적</th>
		<td colspan="5"><%=member.getVisitreason() %></td>
	</tr>
	<% if( member.getTeacher().equals("Y")){ %>
	<tr>
		<th>학교이름</th>
		<td colspan="3"><%=member.getSchoolNm() %></td>
		<th>담당과목</th>
		<td><%=member.getSubCategoryName() %></td>
	</tr>
	<tr>
		<th>학교주소</th>
		<td colspan="3"><%=member.getSchoolZipcode() %> <%=member.getSchoolAddress1() %> <%=member.getSchoolAddress2() %></td>
		<th>학교연락처</th>
		<td><%=member.getSchoolTel() %></td>
	</tr>
	<tr>
		<th>인증방법</th>
		<td colspan="3" style="vertical-align: middle">
			<input class="radio" type="radio" name="authType" value="Y" <% if( member.getTeacherAuthType().equals("Y")){ %>checked<% } %>/> 전자인증
			<input class="radio" type="radio" name="authType" value="D" <% if( member.getTeacherAuthType().equals("D")){ %>checked<% } %>/> 서류인증
			<!-- <input class="radio" type="radio" name="authType" value="N" <% if( Validate.isEmpty(member.getTeacherAuthType())){ %>checked<% } %>/> 없음 -->
			<input class="radio" type="radio" name="authType" value="N" <% if( member.getTeacherAuthType().equals("N")){ %>checked<% } %>/> 없음
		</td>
		<th>
			승인날짜
		</th>
		<td colspan="5">
			<%
				if( !Validate.isEmpty(member.getIsFinallyAuth())
						&& member.getIsFinallyAuth().equals("Y")){
			%>
				<%=member.getFinallyAuthDate() %>
			<%
				}
			%>
		</td>
	</tr>
	<% } %>
	<tr>
		<th>비고</th>
		<td colspan="5"><input type="text" name="authTypeEtc" value="<%=member.getAuthTypeEtc() %>" style="width:450px;"/></td>
	</tr>
	<tr>
		<th>승인여부</th>
		<td colspan="5" style="vertical-align: middle">
			<input type="radio" name="isFinallyAuth" value="N" <%if ( member.getIsFinallyAuth().equals("N") ){ %>checked<%} %>/> 미승인
			<input type="radio" name="isFinallyAuth" value="Y" <%if ( member.getIsFinallyAuth().equals("Y") ){ %>checked<%} %>/> 승인
		</td>
	</tr>
	</tbody>
	</table>
</form>
</div>
<!-- 버튼 영역 -->
<div class="btn-toolbar">
	<button class="btn btn-primary" onclick="checkForm();"><i class="icon-plus"></i> 수정</button>
	<button class="btn" onclick="goList();">리스트</button>
	<div class="btn-group">
	</div>
</div>

<!-- 스크립트 영역 -->
<script type="text/javascript">
<!--

function goList()
{
	var f = document.forms.lform;
	
	f.target = '_self';
	f.action = 'memberList.do';
	f.submit();
}

function checkForm()
{
	if( confirm('이렇게 수정하시겠습니까?') )
	{
		$.ajax({
			url : 'updateMemberInfo.do' ,
			type : 'post' ,
			data : $('form[name=wform]').serialize() ,
			dataType : 'json' ,
			error : function(){
				alert('서버 통신 오류');
			},
			success : function(json){
				if( json.result == 'SUCCESS' )
					alert('반영을 완료하였습니다.');
				else
					alert(json.msg);
			}
		});
	}
}

//
--></script>