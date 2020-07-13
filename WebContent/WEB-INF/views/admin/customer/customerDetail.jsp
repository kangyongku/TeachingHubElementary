<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	int customerId = (Integer) request.getAttribute("customerId");
	String mode = (String) request.getAttribute("mode");
	Customer customer = (Customer) request.getAttribute("customer");
	Customer customerAnswer = (Customer) request.getAttribute("customerAnswer");
	
	int pg = (Integer) request.getAttribute("pg");
	String findStr = (String) request.getAttribute("findStr");
	String findMethod = (String) request.getAttribute("findMethod");
	String findCategory = (String) request.getAttribute("findCategory");
	String findBoardCategory = (String) request.getAttribute("findBoardCategory");
	String findAnswerStatus = (String) request.getAttribute("findAnswerStatus");
%>
<form name="listForm" action="customerList.do" method="post">
	<input type="hidden" name="pg" value="<%=pg %>"/>
	<input type="hidden" name="findStr" value="<%=findStr %>" />
	<input type="hidden" name="findMethod" value="<%=findMethod %>" />
	<input type="hidden" name="findCategory" value="<%=findCategory %>" />
	<input type="hidden" name="findBoardCategory" value="<%=findBoardCategory %>" />
	<input type="hidden" name="findAnswerStatus" value="<%=findAnswerStatus %>" />
</form>
<div class="well">
	<table class="table table-bordered">
		<col width="100"/>
		<col width="100"/>
		<col width="100"/>
		<col width="100"/>
		<tbody>
			<tr>
				<th>문의 분류</th>
				<td>
					<% if( customer.getBoardCategory().equals("C003001") ){ %>
						고객문의
					<%}else if( customer.getBoardCategory().equals("C003002") ){ %>
						파일요청
					<%}else{ %>
						내용문의
					<%} %>
				</td>
				<th>
					<% if( customer.getBoardCategory().equals("C003001") ){ %>
					구분
					<% }else{ %>
					학교급/교과(*)
					<% } %>
				</th>
				<td>
					<% if( customer.getBoardCategory().equals("C003001") ){ %>
						<%=customer.getCategoryName() %>
					<%}else{ %>
						<%=customer.getCategoryPath() %>
					<%} %>
				</td>
			<tr>
				<th>이름(아이디)</th>
				<td colspan="3"><%=customer.getName() %>(<%=customer.getRegId() %>)</td>
			</tr>
			<tr>
				<th>이메일(*)</th>
				<td colspan="3"><%=customer.getEmail() %></td>
			</tr>
			<tr>
				<th>전화번호(*)</th>
				<td><%=customer.getPhoneNum() %></td>
				<th>답변 SMS 수신여부</th>
				<td><%=customer.getIsSns() %></td>				
			</tr>
			<tr>
				<th>제목(*)</th>
				<td colspan="3"><%=customer.getTitle() %></td>
			</tr>
			<tr>
				<th>내용(*)</th>
				<td colspan="3"><%=customer.getContents() %></td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td colspan="3">
				<% if ( customer.getDataFile1() != null ){%>
					<a href="/commons/file/getAdminFile.do?fileName=<%=customer.getFileName1() %>&filePath=<%=customer.getDataFile1()%>"><%=customer.getFileName1()%></a>
				<%} %>
				<% if ( customer.getDataFile2() != null ){%>
					<br/><a href="/commons/file/getAdminFile.do?fileName=<%=customer.getFileName2() %>&filePath=<%=customer.getDataFile2()%>"><%=customer.getFileName2()%></a>
				<%} %>
				<% if ( customer.getDataFile3() != null){%>
					<br/><a href="/commons/file/getAdminFile.do?fileName=<%=customer.getFileName3() %>&filePath=<%=customer.getDataFile3()%>"><%=customer.getFileName3()%></a>
				<%} %>
				</td>
			</tr>
		</tbody>
	</table>
		
	<!-- 답변 입력 폼  -->
	<form name="wform" action="" method="post">
		<input type="hidden" name="mode" value="<%=mode %>" />
		<input type="hidden" name="customerId" value="<%=customer.getCustomerId() %>" />
		<table class="table table-bordered" id="answerTable" style="display:none">
			<col width="100"/>
			<col width="300"/>
			<tbody>
	<%if ( customer.getCustomerCnt() > 0 ){ %>			
				<tr>
					<td>답변 제목(*)</td>
					<td><input type="text" name="title" class="answered" maxLength="128" style="width: 90%;" value="<%=customerAnswer.getTitle() %>"></td>
				</tr>
				<tr>
					<td>답변 내용(*)</td>
					<td>
						<tiles:insertDefinition name="daumeditor"/>
						<textarea id="contents-data" name="contents" style="display:none;"><%=customerAnswer.getContents() %></textarea>					
					<%-- <textarea name="contents" class="answered" style="width: 90%; height: 100px;"><%=customerAnswer.getContents() %></textarea> --%>
					</td>
				</tr>
	<%}else{ %>
				<tr>
					<td>답변 제목(*)</td>
					<td><input type="text" name="title" class="answered" maxLength="128" style="width: 90%;" ></td>
				</tr>
				<tr>
					<td>답변 내용(*)</td>
					<td>
						<tiles:insertDefinition name="daumeditor"/>
						<textarea id="contents-data" name="contents" style="display:none;"></textarea>
						<!-- <textarea name="contents" class="answered" style="width: 90%; height: 100px;"></textarea> -->
					</td>
				</tr>	
	<%} %>				
			</tbody>
		</table>
	
	<!-- 답변 보여주기 -->
	<%if ( customer.getCustomerCnt() > 0 ){ %>
	<input type="hidden" name="answerId" value="<%=customerAnswer.getAnswerId() %>" />
	<table class="table table-bordered" id="answeredTable">
		<col width="100"/>
		<col width="300"/>
		<tbody>
			<tr>
				<td>답변 제목(*)</td>
				<td><%=customerAnswer.getTitle() %></td>
			</tr>
			<tr>
				<td>답변 내용(*)</td>
				<td><%=customerAnswer.getContents().replace("\r\n","<br>") %></td>
			</tr>
		</tbody>
	</table>
	<%} %>
	</form>
</div>
<!-- 버튼 영역 -->
<div class="btn-toolbar">
	<button class="btn btn-primary" onclick="javascript:goForm();" id="submitBtn"><i class="icon-plus"> <%if ( customer.getCustomerCnt() > 0 ){ %>수정<%}else{ %>답변<%} %></i></button>
	<button class="btn" onclick="javascript:goCancel()" id="cancelBtn" style="display:none">취소</button>
	<button class="btn" onclick="javascript:goList();">리스트</button>
	<div class="btn-group">
	</div>
</div>

<!-- 스크립트 영역 -->
<script type="text/javascript">
<!--
$(document).ready(function(){
	Editor.modify({
		"content": document.getElementById("contents-data")
	});
	
	$('#tx_trex_container').css('width' , '100%');
});



//리스트 버튼
function goList(){
	var f = document.forms.listForm;
	f.submit();
}
/* 에디터 컨텐츠를 저장한다. */
function saveContent() {
	Editor.save(); 
}

/**
 * Editor.save()를 호출한 경우 validForm callback 이 수행된 이후
 * 실제 form submit을 위해 form 필드를 생성, 변경하기 위해 부르는 콜백함수로
 * 각자 상황에 맞게 적절히 응용하여 사용한다.
 * @function
 * @param {Object} editor - 에디터에서 넘겨주는 editor 객체
 * @returns {Boolean} 정상적인 경우에 true
 */
function setForm(editor) {
	var formGenerator = editor.getForm();
	var content = editor.getContent();
	formGenerator.createField(
			tx.textarea({
				'name': "contents", // 본문 내용을 필드를 생성하여 값을 할당하는 부분
				'style': { 'display': "none" }
			}, content)
	);
	
	/* 아래의 코드는 첨부된 데이터를 필드를 생성하여 값을 할당하는 부분으로 상황에 맞게 수정하여 사용한다.
	 첨부된 데이터 중에 주어진 종류(image,file..)에 해당하는 것만 배열로 넘겨준다. */
	var images = editor.getAttachments('image');
	for (var i = 0, len = images.length; i < len; i++) {
		// existStage는 현재 본문에 존재하는지 여부
		if (images[i].existStage) {
			// data는 팝업에서 execAttach 등을 통해 넘긴 데이터
			//alert('attachment information - image[' + i + '] \r\n' + JSON.stringify(images[i].data));
			formGenerator.createField(
					tx.input({
						'type': "hidden",
						'name': 'attach_image',
						'value': images[i].data.imageurl // 예에서는 이미지경로만 받아서 사용
					})
			);
		}
	}
	//return true;
}
//등록,수정 버튼
function goForm(){
	var f = document.forms.wform;
	
	var validator = new Trex.Validator();
	var content = Editor.getContent();
	if (!validator.exists(content)) return alert('내용을 입력하세요');
	//alert(content);
	saveContent();

	if( f.mode.value == "add" ){
		$("#answerTable").show();
		$("#cancelBtn").show();
	}else{
		$("#answeredTable").hide();	
		$("#answerTable").show();
		$("#cancelBtn").show();
	}
	$("#submitBtn > i").text("저장");
	$("#submitBtn").attr("onClick","checkForm()");
}

//수정버튼 클릭시
function checkForm(){
	var f = document.forms.wform;
	var contents = f.contents.value;
	

	if( f.title.value == '') return doError('답변제목을 입력하여주십시오.' , 'title' , 'wform');
	if( f.contents.value == '') return doError('내용을 입력하여주십시오.' , 'contents' , 'wform');
	
	if( confirm("작성하신 내용을 등록하시겠습니까?") ){
		f.action = "customerAction.do";
		f.submit();
	}
}
// 취소 버튼
function goCancel(){
	var f = document.forms.wform;
	f.action = "customerDetail.do";
	f.submit();	
}

$(document).ready(function(){
	Editor.modify({
		"content": document.getElementById("contents-data")
	});
	
	$('#tx_trex_container').css('width' , '100%');
});

//-->
</script>