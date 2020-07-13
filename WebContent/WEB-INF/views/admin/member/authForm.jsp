<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="kr.co.kumsung.thub.domain.*"%>
<%
	int pg = (Integer) request.getAttribute("pg");
	String mode = (String) request.getAttribute("mode");
	
	List<Category> learningCategories = (List<Category>) request.getAttribute("learningCategories");
	List<Category> smartCategories = (List<Category>) request.getAttribute("smartCategories");
	List<BoardConfig> boards = (List<BoardConfig>) request.getAttribute("boards");
	
	Member authMap = (Member)request.getAttribute("authMap");
	int authId = (Integer)request.getAttribute("authId");
	
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
	<%if( mode.equals("add") ){ %>
		<form name="sform" action="" method="post" onsubmit="findUser();return false;">
		<table class="table">
			<col width="120" />
			<col width="*" />
			<tbody>
				<tr>
					<th>아이디</th>
					<td>
						<input type="text" name="findUserId"/>
						<a href="javascript:findUser();" class="btn">검색</a>
					</td>
				</tr>
			</tbody>
		</table>
		</form>
	<%} %>
	
	<div style="border:1px solid #eee;background-color:#ddd;width:100%;padding:10px;font-size:1.2em;margin-bottom:10px;" id="findUserDiv">
		<!-- TODO : 냠냠이 | mikelim | 일반관리자 | kim@naver.com | 010-111-1111 -->
		<%=authMap.getName()%> | <%=authMap.getUserId() %> | <% if( authMap.getAuthType().equals("N") ){ %> 일반관리자<%}else{ %>슈퍼관리자 <%} %> | <%=authMap.getEmail() %> | <%=authMap.getHpNum() %>
	</div>
	
	<form name="wform" action="" method="post">
	<input type="hidden" name="mode" value="<%=mode %>" />
	<input type="hidden" name="userId" value="<%=authMap.getUserId() %>" />
	<input type="hidden" name="authId" value="<%=authId %>" />
	<table class="table">
		<col width="120" />
		<col width="*" />
		<tbody>
			<tr>
				<th>권한 구분</th>
				<th>
					<input type="radio" name="authType" value="N" checked/> 일반 관리자
					<input type="radio" name="authType" value="S" /> 슈퍼 관리자
				</th>
			</tr>
		</tbody>
	</table>
	
	<!-- 관리자 디비전 -->
	<div id="adminDiv">
		<h3>■ 관리자 메뉴 권한관리</h3>
		<table class="table">
			<col width="120" />
			<col width="200" />
			<col width="*" />
			<tbody>				
				<tr style="background-color:#eee;">
					<th colspan="2"> - 표제어 등록 교과 권한 관리</th>
					<th style="text-align:right;">
						<a href="javascript:selectAll('smartAuth');" class="btn btn-primary">전체선택</a>
					</th>
				</tr>
				<tr>
					<th colspan="3">
						<% 
							for(Category category : smartCategories){
								
								if( category.getDepth() == 2 ){
						%>
						<div style="clear:both;"><%=category.getName() %></div>
						<div>
							<ul class="items">		
								<% 
									for(Category sCategory : smartCategories){
										if( sCategory.getDepth() == 3 
												&& sCategory.getCategory().indexOf(category.getCategory()) == 0 ){
								%>						
								<li><input type="checkbox" name="smartAuth" value="<%=sCategory.getCategory()%>"/> <span><%=sCategory.getName() %></span></li>
								<% 
										}
									} 
								%>
							</ul>
						</div>
						<% 
								}
							} 
						%>
					</th>
				</tr>
				<tr style="background-color:#eee;">
					<th colspan="2"> - 관리자 메뉴 권한 관리</th>
					<th style="text-align:right;">
						<a href="javascript:selectAll('accessAuth');" class="btn btn-primary">전체선택</a>
					</th>
				</tr>
				<tr>
					<th colspan="3">
						<div>
							<ul class="items">
								<li><input type="checkbox" name="accessAuth" value="multimedia"/> <span>멀티미디어관리</span></li>
								<li><input type="checkbox" name="accessAuth" value="teacher"/> <span>교수·학습 자료관리</span></li>
								<li><input type="checkbox" name="accessAuth" value="smart"/> <span>스마트학생백과</span></li>
								<li><input type="checkbox" name="accessAuth" value="board"/> <span>통합게시판관리</span></li>
								<li><input type="checkbox" name="accessAuth" value="m1"/> <span>학생참여수업</span></li>
								<li><input type="checkbox" name="accessAuth" value="m2"/> <span>자유학년제·창체</span></li>
								<li><input type="checkbox" name="accessAuth" value="m3"/> <span>나눔소통</span></li>								
								<li><input type="checkbox" name="accessAuth" value="pds"/> <span>열린학습자료</span></li>
								<li><input type="checkbox" name="accessAuth" value="cmt"/> <span>선생님행복마당</span></li>
								<li><input type="checkbox" name="accessAuth" value="cs"/> <span>CS관리</span></li>
								<li><input type="checkbox" name="accessAuth" value="poll"/> <span>설문관리</span></li>
								<li><input type="checkbox" name="accessAuth" value="member"/> <span>회원관리</span></li>
								<li><input type="checkbox" name="accessAuth" value="uniform"/> <span>통합코드관리</span></li>
								<li><input type="checkbox" name="accessAuth" value="sms"/> <span>SMS관리</span></li>
								<li><input type="checkbox" name="accessAuth" value="popup"/> <span>팝업관리</span></li>
								<li><input type="checkbox" name="accessAuth" value="main"/> <span>메인화면관리</span></li>
								<li><input type="checkbox" name="accessAuth" value="ca"/> <span>창의적체험활동</span></li>
							</ul>
						</div>
					</th>
				</tr>
			</tbody>
		</table>

		<h3>■ 사용자 메뉴 권한관리</h3>
		<table class="table">
			<col width="120" />
			<col width="200" />
			<col width="*" />
			<tbody>
				<tr style="background-color:#eee;">
					<th colspan="2"> - 교과권한 관리</th>
					<th style="text-align:right;">
						<a href="javascript:selectAll('learningAuth');" class="btn btn-primary">전체선택</a>
					</th>
				</tr>				
				<tr>
					<th colspan="3">
						<% 
							for(Category category : learningCategories){
								
								if( category.getDepth() == 2 ){
						%>
						<div style="clear:both;"><%=category.getName() %></div>
						<div>
							<ul class="items">		
								<% 
									for(Category sCategory : learningCategories){
										if( sCategory.getDepth() == 3 
												&& sCategory.getCategory().indexOf(category.getCategory()) == 0 ){
								%>						
								<li><input type="checkbox" name="learningAuth" value="<%=sCategory.getCategory()%>"/> <span><%=sCategory.getName() %></span></li>
								<% 
										}
									} 
								%>
							</ul>
						</div>
						<% 
								}
							} 
						%>
					</th>
				</tr>
				<tr style="background-color:#eee;">
					<th colspan="2"> - 게시판 권한관리</th>
					<th style="text-align:right;">
						<a href="javascript:selectAll('boardAuth');" class="btn btn-primary">전체선택</a>
					</th>
				</tr>
				<tr>
					<th colspan="3">
						<div>
							<ul class="items">
								<% for(BoardConfig board : boards){ %>
								<li><input type="checkbox" name="boardAuth" value="<%=board.getBoardId() %>"/> <span><%=board.getBoardName() %></span></li>
								<% } %>
							</ul>
						</div>
					</th>
				</tr>
			</tbody>
		</table>
	</div>
	</form>
</div>

<!-- 버튼 영역 -->
<div class="btn-toolbar">
	<button class="btn btn-primary" onclick="checkForm();">
		<i class="icon-plus"></i> 수정
	</button>
	<%if ( mode.equals("modify") ){ %>
	<button class="btn" onclick="clearForm();">권한해지
	<%} %>
	<button class="btn" onclick="goList();">리스트</button>
	<div class="btn-group"></div>
	<button class="btn" onclick="goDelete();">삭제</button>
	<div class="btn-group"></div>
</div>

<!-- 스크립트 영역 -->
<script type="text/javascript">
<!--

$(document).ready(function(){
	
	$("input[name=authType]").click(function(){
		adminDivToggle();
	})
	
	//수정시 초기값 세팅
	<%if ( mode.equals("add") ){%>
		$("#findUserDiv").hide();
	<%}%>
	<%if ( mode.equals("modify") ){%>
		
		//체크박스 세팅
		var smartAuthStr = ""; 
		var accessAuthStr = "";
		var learningAuthStr = "";
		var boardAuthStr = "";
		
		smartAuthStr = "<%=authMap.getSmartAuth()%>";
		accessAuthStr = "<%=authMap.getAccessAuth()%>";
		learningAuthStr = "<%=authMap.getLearningAuth()%>";
		boardAuthStr = "<%=authMap.getBoardAuth()%>";
		
		var smartSplit = smartAuthStr.split(",");
		for(var i=0; i < smartSplit.length; i++){
			$("input[name=smartAuth][value="+smartSplit[i]+"]").attr("checked",true);
		}
		var accessSplit = accessAuthStr.split(",");
		for(var i=0; i < accessSplit.length; i++){
			$("input[name=accessAuth][value="+accessSplit[i]+"]").attr("checked",true);
		}
		var learningSplit = learningAuthStr.split(",");
		for(var i=0; i < learningSplit.length; i++){
			$("input[name=learningAuth][value="+learningSplit[i]+"]").attr("checked",true);
		}
		var boardSplit = boardAuthStr.split(",");
		for(var i=0; i < boardSplit.length; i++){
			$("input[name=boardAuth][value="+boardSplit[i]+"]").attr("checked",true);
		}
		
		$("input[name=authType][value=<%=authMap.getAuthType()%>]").attr("checked",true);
		adminDivToggle();
		//권한 확인
		
	<%}%>
	
})

function selectAll(target)
{
	$('input[name=' + target + ']').each(function(){
		$(this).attr('checked' , 'checked');
	});
}

function enterChecker(e)
{	
	if( e.keyCode == 13 )
	{
		findUser();
	}
}

function findUser()
{
	if( $('input[name=findUserId]').val() != '' )
	{
		$.ajax({
			url : 'findUserInfo.do' ,
			type : 'post' ,
			data : 'userId=' + $('input[name=findUserId]').val() ,
			dataType : 'json' ,
			error : function(){
				alert('서버와의 통신도중 에러가 발생되었습니다.');
			},
			success : function(json){
				if( json.result == 'SUCCESS' )
				{
					$("#findUserDiv").text(
							json.member.name + " | " +
							json.member.userId + " | " +
							json.member.email  + " | " +
							json.member.hpNum
					).show();
					$("input[name=userId]").val(json.member.userId);
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
	f.action = 'authList.do';
	f.submit();
}

function goDelete() {
	if(confirm("티칭허브 관리자를 삭제하시겠습니까?") == true){
		var f = document.forms.wform;
		f.mode.value="delete";
		f.target = '_self';
		f.action = 'authAction.do';
		f.submit();	
	}else{
		return;
	}
}



function checkForm() {
	var f = document.forms.wform;
	if( f.userId.value == '') return doError('아이디를 검색하여 주십시오.' , 'userId' , 'wform');
	if($("input[name=authType]:checked").val() == "S")
		clearForm();
	
	f.target = '_self';
	f.action = 'authAction.do'
	f.submit();
	
	//alert("TODO : 정의 된 후 작업 진행");
	

}
function clearForm(){
	$("input[type=checkbox]").attr("checked",false);
}

function adminDivToggle(){
	var obj = $("input[name=authType]:checked").val();
	if( obj == "N" ){
		$("#adminDiv").show();
	}else{
		$("#adminDiv").hide();
	}
}
//-->
</script>