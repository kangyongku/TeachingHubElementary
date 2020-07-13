<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	String mode = (String) request.getAttribute("mode");
	int pg = (Integer) request.getAttribute("pg");
	String findCategory = (String) request.getAttribute("findCategory");	
	String findCourse = (String) request.getAttribute("findCourse");
	String findIsUse = (String) request.getAttribute("findIsUse");
	String findMethod = (String) request.getAttribute("findMethod");
	String findStr = (String) request.getAttribute("findStr");
	String findStartDate = (String) request.getAttribute("findStartDate");
	String findEndDate = (String) request.getAttribute("findEndDate");
	String findBookId = (String) request.getAttribute("findBookId");
	String findUnitIds = (String) request.getAttribute("findUnitIds");
	String findUnitId = (String) request.getAttribute("findUnitId");
	String findIsApprove = (String) request.getAttribute("findIsApprove");
	String findFlagType = (String) request.getAttribute("findFlagType");

	int headwordId = (Integer) request.getAttribute("headwordId");
	Headword headword = (Headword) request.getAttribute("headword");
	List<Category> categories = (List<Category>) request.getAttribute("categories");
	List<Category> exhibits = (List<Category>) request.getAttribute("exhibits");
	List<Book> books = (List<Book>) request.getAttribute("books");
	List<History> histories = (List<History>) request.getAttribute("histories");
	List<RelationHeadword> relationHeadwords = (List<RelationHeadword>) request.getAttribute("relationHeadwords");
	List<Multimedia> additionals = (List<Multimedia>) request.getAttribute("additionals");
	
	Member adminMember = (Member) session.getAttribute("adminMember");
%>
<div class="btn-toolbar">
	<button class="btn btn-primary" onclick="checkForm();"><i class="icon-plus"></i> 저장</button>
	<button class="btn btn-danger" onclick="goDelete();">삭제</button>
	<button class="btn" onclick="goList();">리스트</button>
	<% if( mode.equals("modify")){ %>
	<a class="btn" href="/web/smart/detail.do?headwordId=<%=headword.getHeadwordId() %>&findCategory=<%=headword.getCategory() %>&findBookId=<%=headword.getBookId() %>" target="_blank">미리보기</a>
	<% } %>
</div>

<div class="well">
	<div style="margin:10px;">
		<label style="font-size:16px;font-weight:bold;color:#ffffff;background-color:#1e65a6;padding:10px;">표제어 기본정보</label>
		<form name="wform" action="" method="post" enctype="multipart/form-data">
		<input type="hidden" name="headwordId" value="<%=headword.getHeadwordId()%>"/>
		<input type="hidden" name="mode" value="<%=mode%>"/>
		<input type="hidden" name="pg" value="<%=pg%>"/>
		<input type="hidden" name="findCategory" value="<%=findCategory%>"/>
		<input type="hidden" name="findCourse" value="<%=findCourse%>"/>
		<input type="hidden" name="findIsUse" value="<%=findIsUse%>"/>
		<input type="hidden" name="findMethod" value="<%=findMethod%>"/>
		<input type="hidden" name="findStr" value="<%=findStr%>"/>
		<input type="hidden" name="findStartDate" value="<%=findStartDate%>"/>
		<input type="hidden" name="findEndDate" value="<%=findEndDate%>"/>
		<input type="hidden" name="findBookId" value="<%=findBookId%>"/>
		<input type="hidden" name="findUnitIds" value="<%=findUnitIds%>"/>
		<input type="hidden" name="findUnitId" value="<%=findUnitId%>"/>
		<input type="hidden" name="findIsApprove" value="<%=findIsApprove%>"/>
		<input type="hidden" name="findFlagType" value="<%=findFlagType%>"/>
		<input type="hidden" name="category"/>
		<input type="hidden" name="exhibit"/>
		<input type="hidden" name="unitId"/>
		<input type="hidden" name="unitIds"/>
		<table class="table">
		<col width="150"/>
		<col width="*"/>
		<col width="150"/>
		<col width="*"/>
		<tbody>	
		<tr>
			<th>등록 구분(*)</th>
			<td>
				<input type="radio" name="flag" value="H" <% if( headword.getFlag().equals("H")){ %>checked<% } %>> 표제어
				<input type="radio" name="flag" value="P" <% if( headword.getFlag().equals("P")){ %>checked<% } %>> 백과플러스
			</td>
			<th>작성자</th>
			<td>
				<%=headword.getRegId() %>
			</td>
		</tr>
		<tr>
			<th>승인 구분</th>
			<td>
				<input type="radio" name="isApprove" value="N" <% if( headword.getIsApprove().equals("N")){ %>checked<% } %>> 대기
				<input type="radio" name="isApprove" value="Y" <% if( headword.getIsApprove().equals("Y")){ %>checked<% } %>> 승인
				<input type="radio" name="isApprove" value="D" <% if( headword.getIsApprove().equals("D")){ %>checked<% } %>> 미승인
			</td>
			<th>노출 구분</th>
			<td>
				<input type="radio" name="isUse" value="Y" <% if( headword.getIsUse().equals("Y")){ %>checked<% } %>> 노출
				<input type="radio" name="isUse" value="N" <% if( headword.getIsUse().equals("N")){ %>checked<% } %>> 미노출
			</td>
		</tr>
		<tr>
			<th>학교급/교과(*)</th>
			<td colspan="3">
				<select name="category-2" class="category" style="width:100px;margin-right:10px;float:left;" onchange="showCategory(2);getSmartBooks();">
					<option value="">::선택::</option>
					<% for(Category category : categories){ %>
					<option value="<%=category.getCategory()%>" data-depth="<%=category.getDepth() %>" data-children="<%=category.getChildren()%>"><%=category.getName() %></option>
					<% } %>
				</select>
				<select name="category-3" class="category" style="width:100px;margin-right:10px;float:left;display:none;" onchange="showCategory(3);getSmartBooks();">
					<option value="">::선택::</option>
				</select>
				<select name="category-4" class="category" style="width:100px;margin-right:10px;float:left;display:none;" onchange="showCategory(4);getSmartBooks();">
					<option value="">::선택::</option>
				</select>
			</td>
		</tr>
		<tr>
			<th>도서(*)</th>
			<td colspan="3" style="height:25px;">
				<select name="bookId" onchange="showSmartUnit('1');">
					<option value="">::도서선택::</option>
					<% for(Book book : books) { %>
					<option value="<%=book.getBookId()%>" <% if( book.getBookId() == headword.getBookId()){ %>selected<% } %>><%=book.getName() %></option>
					<% } %>
				</select>
			</td>
		</tr>
		<tr>
			<th>단원(*)</th>
			<td colspan="3">
				<select name="unit-1" style="float:left;" onchange="showSmartUnit('2');">
					<option value="">::대단원::</option>
				</select>
				<select name="unit-2" style="float:left;display:none;" onchange="showSmartUnit('3');">
					<option value="">::중단원::</option>
				</select>
				<select name="unit-3" style="float:left;display:none;">
					<option value="">::소단원::</option>
				</select>
				<div style="clear:both;"></div>
			</td>
		</tr>
		<tr>
			<th>표제어(*)</th>
			<td colspan="3">
				<input type="text" name="title" style="width:500px;" value="<%=headword.getTitle()%>"/>
			</td>
		</tr>
		<tr>
			<th>표제어 영문</th>
			<td>
				<input type="text" name="titleEng" value="<%=headword.getTitleEng()%>"/>
			</td>
			<th>표제어 한문</th>
			<td>
				<input type="text" name="titleChi" value="<%=headword.getTitleChi()%>"/>
			</td>
		</tr>
		<tr>
			<th>목록 이미지</th>
			<td colspan="3">
				<input type="file" name="thumbnail"/>
				<% if( !Validate.isEmpty(headword.getThumbnail()) 
						&& !headword.getThumbnail().equals("/assets/front/img/no_image.gif")){ %>
				<div style="margin:5px;">
					<img src="<%=headword.getThumbnail() %>" width="150"/>
					<br/> <input type="button" value="삭제" onclick="deleteThumbnail();"/>
				</div>
				<% } %>
				<div>
				* 이미지 사이즈는 가로 150px X 세로 115px 입니다.
				</div>
			</td>
		</tr>
		<tr>
			<th>요약설명(*)</th>
			<td colspan="3">
				<textarea name="summary" style="width:98%;height:80px;"><%=headword.getSummary() %></textarea>
			</td>
		</tr>
		<tr>
			<th>전체설명(*)</th>
			<td colspan="3">
				<tiles:insertDefinition name="daumeditor"/>
				<textarea id="contents-data" style="display:none;"><%=headword.getContents() %></textarea>
				<textarea id="preview-data" name="preview_contents" style="display:none;"></textarea>
				<div>
					<a href="javascript:showContents();" class="btn btn-primary">본문 미리보기</a>
				</div>
				<script type="text/javascript">
				<!--
				function showContents()
				{	
					var f = document.forms.wform;
					
					window.open("" , "preview" , "width=800,height=800,scrollbars=yes");
					
					// save editor contents
					f.preview_contents.value = Editor.getContent();
					
					// set values
					f.target = 'preview';
					f.action = 'headwordContentsPopup.do';
					f.submit();					
				}
				//-->
				</script>
			</td>
		</tr>
		<tr>
			<th>출처</th>
			<td colspan="3">
				<select name="originCode">
					<option value="DIRECT" <% if( headword.getOriginCode().equals("DIRECT")){ %>selected<% } %>>직접집필</option>
					<option value="SOURCE" <% if( headword.getOriginCode().equals("SOURCE")){ %>selected<% } %>>출처</option>
				</select>
				<input type="text" name="originTxt" value="<% if( !Validate.isEmpty(headword.getOriginCode()) && !headword.getOriginCode().equals("DIRECT")){ %><%=headword.getOriginTxt()%><% } %>"/>
			</td>
		</tr>
		<tr>
			<th>관련 교과서</th>
			<td colspan="3">
				<input type="text" name="relationBooks" style="width:500px;" value="<%=headword.getRelationBooks()%>"/>
			</td>
		</tr>
		<tr>
			<th>더 알아보기</th>
			<td colspan="3">
				<iframe id="added-data-box" name="added-data-box" src="editor.do?target=added" style="width:100%;height:340px;" frameborder="0"></iframe>
				<textarea name="added" id="added" style="display:none;"><%=headword.getAdded() %></textarea>
			</td>
		</tr>
		</tbody>
		</form>
		</table>
		
		
		<% if( mode.equals("modify")){ %>
		<label style="font-size:16px;font-weight:bold;color:#ffffff;background-color:#1e65a6;padding:10px;">관련용어</label>
		
		<!-- 관련용어 리스트 wrapper -->
		<div style="margin:5px;">
			<a class="btn btn-primary" href="javascript:showHeadwordSearchPopup();"><i class="icon-plus"></i>용어추가</a>
		</div>
		
		<div id="relation-wrapper">
			<ul>
				<% for(RelationHeadword relationHeadword : relationHeadwords){ %>
				<li style="background-color:#eee;border:1px solid #ddd;padding:10px;margin:5px;" class="relation-<%=relationHeadword.getRelationId()%>">
					<div style="float:left;font-size:11pt;">
						<%=relationHeadword.getTitle() %>
					</div>
					<div style="float:right;">
						<a href="javascript:deleteRelationHeadword('<%=relationHeadword.getRelationId() %>');" class="btn btn-danger">삭제</a>
					</div>
					<div style="clear:both;"></div>
				</li>
				<% } %>
			</ul>
		</div>	
		
		<label style="font-size:16px;font-weight:bold;color:#ffffff;background-color:#1e65a6;padding:10px;">참고자료</label>
		
		<!-- 참고자료 리스트 wrapper -->
		<div style="margin:5px;">
			<a class="btn btn-primary" href="javascript:showMultimediaPopup();"><i class="icon-plus"></i>참고자료 추가</a>
		</div>
		
		<div id="additional-wrapper">
			<ul>
				<% for(Multimedia additional : additionals){ %>
				<li style="background-color:#eee;border:1px solid #ddd;padding:10px;margin:5px;" class="additional-<%=additional.getMmId()%>">
					<div style="float:left;font-size:11pt;">
						<%=additional.getTitle() %>
					</div>
					<div style="float:right;">
						<a href="javascript:deleteAdditional('<%=additional.getMmId() %>');" class="btn btn-danger">삭제</a>
					</div>
					<div style="clear:both;"></div>
				</li>
				<% } %>
			</ul>
		</div>
		
		
		<a name="#pool-form"></a>
		<label style="font-size:16px;font-weight:bold;color:#ffffff;background-color:#1e65a6;padding:10px;">문제 관리</label>
		<form name="poolForm" action="" method="post" id="poolForm">
		<input type="hidden" name="headwordId" value="<%=headword.getHeadwordId()%>"/>
		<input type="hidden" name="mode" value="add"/>
		<input type="hidden" name="poolId"/>
		<table class="table">
		<col width="150"/>
		<col width="*"/>
		<col width="150"/>
		<col width="*"/>
		<tbody>	
		<tr>
			<th>유형구분</th>
			<td colspan="3">
				<select name="flag">
					<option value="C">확인문제</option>
					<option value="R">기출문제</option>
				</select>
			</td>
 		</tr>
		<tr>
			<th>질문</th>
			<td colspan="3">
				<iframe id="question-data-box" name="question-data-box" src="editor.do?target=question" style="width:100%;height:340px;" frameborder="0"></iframe>
				<textarea name="question" id="question" style="display:none;"></textarea>
			</td>
		</tr>
		<tr>
			<th>정답</th>
			<td colspan="3">
				<input type="text" name="answer" style="width:500px;"/>
			</td>
		</tr>
		<tr>
			<th>정답해설</th>
			<td colspan="3">
				<textarea name="explanation" style="width:95%;height:100px;"></textarea>
			</td>
		</tr>
		<tr>
			<th colspan="4">
				<a class="btn btn-primary" href="javascript:savePool();"><i class="icon-plus"></i>문제 저장</a>
				<a class="btn btn-danger" href="javascript:resetPool();"><i class="icon-plus"></i>취소</a>
			</th>
		</tr>
		</tbody>
		</table>
		</form>
		
		<!-- 문제 리스트 wrapper -->
		<div id="pool-wrapper"></div>	
		<label style="font-size:16px;font-weight:bold;color:#ffffff;background-color:#1e65a6;padding:10px;">인물,사건 캘린더</label>
		<form name="historyCalendarForm" action="" method="post">
		<input type="hidden" name="headwordId" value="<%=headword.getHeadwordId()%>"/>
		<input type="text" name="historyId"/>
		<input type="hidden" name="mode"/>		
		<table class="table">
		
		<col width="150"/>
		<col width="*"/>
		<col width="150"/>
		<col width="*"/>
		<tbody>	
		<tr>
			<th>우선노출</th>
			<td colspan="3">
				<input type="checkbox" name="isPrimary" value="Y"/> 우선노출
				<span style="font-size:.85em;color:#747474;">우선노출을 선택하시면 다른 동일 월/일의 데이타는 자동으로 우선순위가 해제됩니다.</span>
			</td>
 		</tr>
		<tr>
			<th>날짜선택</th>
			<td colspan="3">
				<select name="historyType" style="width:120px;">
					<option value="BC">기원전</option>
					<option value="AD" selected>기원후</option>
				</select>
				<input type="text" name="historyDate" maxlength="8"/> 예) 20130726
			</td>
		</tr>
		<tr>
			<th>역사사건</th>
			<td colspan="3">
				<input type="text" name="historyExp" style="width:500px;" onKeyUp="input_cal_byte(this, 28)"/>
			</td>
		</tr>
		<tr>
			<th colspan="4">
				<a class="btn btn-primary" href="javascript:addHistory();"><i class="icon-plus"></i> 인물,사건 캘린더 저장</a>
			</th>
		</tr>
		</tbody>
		</table>
		</form>
		
		<div id="history-wrapper">
		<table class="table">
		<col width="150"/>
		<col width="180"/>
		<col width="*"/>
		<col width="120"/>
		<thead>
		<tr>
			<th>우선노출</th>
			<th>년도</th>
			<th>내용</th>
			<th>삭제</th>
 		</tr>
 		</thead>
 		<tbody>
		</tbody>
		</table>
		</div>
		
		<label style="font-size:16px;font-weight:bold;color:#ffffff;background-color:#1e65a6;padding:10px;">역사전시관 설정</label>
		<form name="historyForm" action="" method="post">
		<input type="hidden" name="headwordId" value="<%=headword.getHeadwordId()%>"/>
		<table class="table">
		<tbody>	
		<tr>
			<td>
				<select name="exhibit-category-2" class="category" style="width:150px;margin-right:10px;float:left;" onchange="showCategory(2 , '' , 'exhibit-');">
					<option value="">::선택::</option>
					<% for(Category category : exhibits){ %>
					<option value="<%=category.getCategory()%>" data-depth="<%=category.getDepth() %>" data-children="<%=category.getChildren()%>"><%=category.getName() %></option>
					<% } %>
				</select>
				<select name="exhibit-category-3" class="category" style="width:150px;margin-right:10px;float:left;display:none;" onchange="showCategory(3 , '' , 'exhibit-');">
					<option value="">::선택::</option>
				</select>
				<select name="exhibit-category-4" class="category" style="width:150px;margin-right:10px;float:left;display:none;" onchange="showCategory(4 , '' , 'exhibit-');">
					<option value="">::선택::</option>
				</select>
			</td>
 		</tr>
		</tbody>
		</table>
		</form>
		<% } %>
		
		<div class="btn-toolbar">
			<button class="btn btn-primary" onclick="checkForm();"><i class="icon-plus"></i> 표제어 저장</button>
			<button class="btn" onclick="goList();">리스트</button>
			<div class="btn-group">
			</div>
		</div>
		
	</div>
	
</div>

<form name="lform" action="" method="get">
<input type="hidden" name="pg" value="<%=pg%>"/>
<input type="hidden" name="findCategory" value="<%=findCategory%>"/>
<input type="hidden" name="findCourse" value="<%=findCourse%>"/>
<input type="hidden" name="findIsUse" value="<%=findIsUse%>"/>
<input type="hidden" name="findMethod" value="<%=findMethod%>"/>
<input type="hidden" name="findStr" value="<%=findStr%>"/>
<input type="hidden" name="findStartDate" value="<%=findStartDate%>"/>
<input type="hidden" name="findEndDate" value="<%=findEndDate%>"/>
<input type="hidden" name="findBookId" value="<%=findBookId%>"/>
<input type="hidden" name="findUnitIds" value="<%=findUnitIds%>"/>
<input type="hidden" name="findUnitId" value="<%=findUnitId%>"/>
<input type="hidden" name="findIsApprove" value="<%=findIsApprove%>"/>
<input type="hidden" name="findFlagType" value="<%=findFlagType%>"/>
</form>

<script type="text/javascript">
<!--
//load default contents data
$(document).ready(function(){
	Editor.modify({
		"content": document.getElementById("contents-data")
	});
	
	if( $('select[name=originCode] option:selected').val() == 'DIRECT' )
		$('input[name=originTxt]').css('visibility' , 'hidden');
	
	$('select[name=originCode]').change(function(){
		if( $(this).val() == 'DIRECT' )
			$('input[name=originTxt]').css('visibility' , 'hidden');
		else
			$('input[name=originTxt]').css('visibility' , 'visible');
	});
	
	<% if( mode.equals("modify")){ %>
	initCategory('<%=headword.getCategory()%>');
	initCategory('<%=headword.getExhibit()%>','exhibit-');
	renderSmartUnits('<%=headword.getBookId()%>' , '<%=headword.getUnitIds()%>');
	getHistoryList();
	getPoolList();
	<% } %>
});

function addHeadword(headwordId , title)
{
	// add to headword to relation
	$.ajax({
		url : 'addRelationHeadword.do' ,
		type : 'post' ,
		data : 'headwordId=<%=headwordId%>&childrenId=' + headwordId,
		dataType : 'json' ,
		error : function(){
			alert('서버와 연결도중 에러가 발생되었습니다.');
		},
		success : function(json){
			if( json.result == 'SUCCESS' )
			{
				$('#relation-wrapper ul').append('<li style="background-color:#eee;border:1px solid #ddd;padding:10px;margin:5px;" class="relation-' + json.relationId + '">\r\n' +
						'<div style="float:left;font-size:11pt;">\r\n' +
						title + '\r\n' +
					'</div>\r\n' +
					'<div style="float:right;">\r\n' + 
						'<a href="javascript:deleteRelationHeadword(\'' + json.relationId + '\');" class="btn btn-danger">삭제</a>\r\n' +
					'</div>\r\n' +
					'<div style="clear:both;"></div>\r\n' +
				'</li>\r\n');
			}
			else
				alert(json.msg);
		}
	});
}

function deleteRelationHeadword(relationId)
{
	// add to headword to relation
	if( confirm('정말로 삭제하시겠습니까?') )
	{
		$.ajax({
			url : 'deleteRelationHeadword.do' ,
			type : 'post' ,
			data : 'relationId=' + relationId,
			dataType : 'json' ,
			error : function(){
				alert('서버와 연결도중 에러가 발생되었습니다.');
			},
			success : function(json){
				if( json.result == 'SUCCESS' )
				{
					$('#relation-wrapper ul').find('.relation-' + relationId).remove();
				}
				else
					alert(json.msg);
			}
		});
	}
}

function showHeadwordSearchPopup()
{
	window.open('headwordSearchPopup.do' , 'headword' , 'width=850,height=780,scrollbars=no');
}

function getIframeEditorData(target)
{
	var obj = document.getElementById(target);
	var objDoc = obj.contentWindow || obj.contentDocument;
	return objDoc.getContent(); 
}

function checkForm()
{	
	var f = document.forms.wform;
	
	var category = getCategoryCode();
	var exhibit = getCategoryCode('exhibit-');
	var unitId = getUnitId();
	var unitIds = getUnitIds();
	
	// save editor contents
	saveContent();
	
	// set values
	f.category.value = category;
	f.exhibit.value = exhibit;
	f.unitId.value = unitId;
	f.unitIds.value = unitIds;
	f.added.value = getIframeEditorData("added-data-box");
	
	if( f.category.value == '' ) return doError('학교급/교과를 선택해주십시오.' , 'category-2' , 'wform');
	if( f.bookId.value == '' ) return doError('도서를 선택해주십시오.' , 'bookId' , 'wform');
	if( f.unitId.value == 0 || f.unitId.value == '') return doError('단원을 선택해주십시오.' , 'unit-1' , 'wform');
	if( f.title.value == '' ) return doError('표제어를 입력해주십시오.' , 'title' , 'wform');
	if( f.summary.value == '' ) return doError('요약설명을 입력해주십시오.' , 'summary' , 'wform');
	if( f.contents.value == '' ) return doError('전체설명을 입력해주십시오.' , 'contents' , 'wform');
	if( f.originCode.value == 'SOURCE' && f.originTxt.value == '' ) return doError('출처를 입력해주십시오.' , 'originTxt' , 'wform');
	
	<% if( adminMember.getAuthType().equals("N")){ %>
	// 권한검사
	if( authCheck() == false ) return doError('허가 받지 않은 분류입니다.' , 'category-1' , 'wform');
	<% } %>
	
	<% if( mode.equals("modify") ){ %>
	if( f.exhibit.value != '' 
		&& 
		($('input[name=thumbnail]').val() == '' && $('input[name=thumbnail]').parent().children().length == 2) )
	{
		return doError('역사전시관을 등록하시려면 썸네일 입력을 반드시 하셔야 합니다.');
	}
	<% } %>
	
	f.target = '_self';
	f.action = 'headwordFormAction.do';
	f.submit();
}

function authCheck()
{
	var smartAuth = "<%=adminMember.getSmartAuth()%>";
	var t = smartAuth.split(",");
	var targetData = $('select[name=category-3] option:selected').val();
	
	for(var i = 0 ; i < t.length ; i++)
	{	
		if( targetData == t[i] )
			return true;
	}
	
	return false;
}

function goList()
{
	var f = document.forms.lform;
	
	f.target = '_self';
	f.action = 'headwordList.do';
	f.submit();
}

function addHistory()
{	
	var f = document.forms.historyCalendarForm;
	
	if( f.historyType.value == '' ) return doError('날짜구분을 선택해주십시오.' , 'historyType' , 'historyForm');
	if( isNaN(f.historyDate.value) || f.historyDate.value.length < 5 ) return doError('날짜 입력 형식을 지켜주십시오.\r\n예 ) 20130726' , 'historyDate' , 'historyForm');
	if( f.historyExp.value == '') return doError('역사사건의 내용을 입력해주십시오.' , 'historyExp' , 'historyForm');
	
	// get history date
	var date = f.historyDate.value;
	
	var type = (f.historyType.value == 'BC') ? '기원전' : '기원후';
	var year = date.substring(0 , date.length - 4);
	var month = date.substr(year.length , 2);
	var day = date.substr(year.length + month.length , 2);
	var msg = '입력하신 날짜가\r\n\r\n[' + type + ' ' + year + '년 ' + month + '월 ' + day + '일]\r\n\r\n이 맞습니까?';
	
	if( confirm(msg) )
		historyHandler();
}

function deleteHistory(historyId)
{
	if( confirm('정말로 삭제하시겠습니까?') )
	{
		var f = document.forms.historyCalendarForm;
		alert("historyId: "+historyId);
		//f.reset();
		f.historyId.value = historyId;
		f.mode.value = 'delete';
		
		historyHandler();
	}
}
 
function historyHandler()
{
	$.ajax({
		url : 'historyAction.do' ,
		type : 'post' ,
		async : false ,
		data : $('form[name=historyCalendarForm]').serialize() ,
		dataType : 'json' ,
		error : function(){
			alert('서버와의 통신도중 에러가 발생되었습니다.');
		},
		success : function(json){
			if( json.result == 'SUCCESS' )
			{
				getHistoryList();
			}
			else
				alert(json.msg);
		},
		complete : function(){
			f.reset();
		}
	});
}

function getHistoryList()
{
	$.ajax({
		url : 'getHistoryList.do' ,
		type : 'post' ,
		async : true ,
		data : 'headwordId=' + $('form[name=historyForm]').find('input[name=headwordId]').val() ,
		dataType : 'json' ,
		success : function(json){
			if( json.result == 'SUCCESS' )
			{
				$('#history-wrapper').find('tbody').empty();
				
				var loopCnt = json.list.length; 
				
				for(var i = 0 ; i < loopCnt ; i++)
				{
					var isPrimary = (json.list[i].isPrimary == 'Y') ? '우선노출' : '';
					var type = (json.list[i].historyType == 'BC') ? '기원전' : '기원후';
					var year = json.list[i].historyDate.substring(0 , json.list[i].historyDate.length - 4);
					var month = json.list[i].historyDate.substr(year.length , 2);
					var day = json.list[i].historyDate.substr(year.length + month.length , 2);
					
					var msg = '' + type + ' ' + year + '년 ' + month + '월 ' + day + '일';
					
					var el = '<tr>\r\n' + 
						'<td>' + isPrimary + '</td>\r\n' + 
						'<td>' + msg + '</td>\r\n' + 
						'<td>' + json.list[i].historyExp + '</td>\r\n' + 
						'<td>\r\n' + 
							'<a class="btn btn-danger" href="javascript:deleteHistory(\'' + json.list[i].historyId + '\');">삭제</a>\r\n' + 
						'</td>\r\n' + 
			 		'</tr>\r\n';
			 		
			 		$('#history-wrapper').find('tbody').append(el);
				}
			}
		},
		complete : function(){
			$('form[name=historyForm]').find('input[type=checkbox]').attr('checkec' , '');
			$('form[name=historyForm]').find('input[type=text]').val();
			$('form[name=historyForm]').find('input[type=hidden]').val();
		}
	})
}

function savePool()
{
	var f = document.forms.poolForm;
	
	f.question.value = getIframeEditorData("question-data-box");
	
	if( f.flag.value == '' ) return doError('유형을 구분해주십시오.' , 'flag' , 'poolForm');
	if( f.question.value == '' ) return doError('질문을 입력해주십시오.' , 'question' , 'poolForm');
	if( f.answer.value == '' ) return doError('답변을 입력해주십시오.' , 'answer' , 'poolForm');
	if( f.explanation.value == '' ) return doError('정답해설 입력해주십시오.' , 'explanation' , 'poolForm');
	
	poolHandler();
}

function modifyPool(poolId)
{
	$.ajax({
		url : 'getPool.do' ,
		type : 'post' ,
		async : false ,
		data : 'poolId=' + poolId ,
		dataType : 'json' ,
		error : function(){
			alert('서버와의 통신중 에러가 발생되었습니다.');
		},
		success : function(json){
			if( json.result == 'SUCCESS' )
			{
				var f = document.forms.poolForm;
				
				f.poolId.value = poolId;
				f.mode.value = 'modify';
				f.flag.value = json.pool.flag;
				f.question.value = json.pool.question;
				f.answer.value = json.pool.answer;
				f.explanation.value = json.pool.explanation;
				
				$('#question-data-box').attr('src' , 'editor.do?target=question');
				
				var position = $('form[name=poolForm]').position();
				$('body').scrollTop(2000);
			}
			else
				alert(json.msg);
		}
	});
}

function deletePool(poolId)
{
	if( confirm('문제를 삭제하시겠습니까?') )
	{
		var f = document.forms.poolForm;
		
		f.poolId.value = poolId;
		f.mode.value = 'delete';
		
		poolHandler();
	}
}

function resetPool()
{
	var f = document.forms.poolForm;
	
	//f.reset();
	
	f.mode.value = 'add';
	f.poolId.value = '';
	f.answer.value = '';
	f.explanation.value = '';
	f.question.value = '';
	
	$('#question-data-box').attr('src' , 'editor.do?target=question');
}

function poolHandler()
{
	$.ajax({
		url : 'poolAction.do' ,
		type : 'post' ,
		async : false ,
		data : $('form[name=poolForm]').serialize(),
		dataType : 'json' ,
		error : function(){
			alert('서버와의 통신중 에러가 발생되었습니다.');
		},
		success : function(json){
			if( json.result == 'SUCCESS' )
			{
				// list를 받아오자!!!
				getPoolList();
			}
			else
				alert(json.msg);
		},
		complete : function(){
			resetPool();
		}
	});
}

function getPoolList()
{
	var defaultFlag = 'C';
	
	$.ajax({
		url : 'getPoolList.do' ,
		type : 'post' ,
		async : true ,
		data : 'headwordId=' + $('form[name=poolForm]').find('input[name=headwordId]').val(),
		dataType : 'json' ,
		beforeSend : function(){
			$('#pool-wrapper').empty();
		},
		error : function(){
			alert('서버와의 통신중 에러가 발생되었습니다.');
		},
		success : function(json){
			if( json.result == 'SUCCESS' )
			{
				var loopCnt = json.list.length;
				
				for(var i = 0 ; i < loopCnt ; i++ )
				{
					var flag = (json.list[i].flag == 'C') ? '확인문제' : '기출문제';
					
					if( json.list[i].flag == 'C' )
						defaultFlag = 'R';
					
					// list를 받아오자!!!
					var el = '<table class="table">\r\n' + 
									'<col width="150"/>\r\n' + 
								'<col width="*"/>\r\n' + 
								'<tbody>\r\n' + 
								'<tr>\r\n' + 
									'<th style="border:1px solid #696969;">문제유형</th>\r\n' + 
									'<td style="border:1px solid #696969;">\r\n' + 
										flag + '\r\n' + 
									'</td>\r\n' + 
								'</tr>\r\n' + 
								'<tr>\r\n' + 
									'<th style="border:1px solid #696969;">질문</th>\r\n' + 
									'<td style="border:1px solid #696969;">\r\n' + 
										nl2br(json.list[i].question) + '\r\n' +  
									'</td>\r\n' + 
								'</tr>\r\n' + 
								'<tr>\r\n' + 
									'<th style="border:1px solid #696969;">정답</th>\r\n' + 
									'<td style="border:1px solid #696969;">\r\n' + 
										nl2br(json.list[i].answer) + '\r\n' + 
									'</td>\r\n' + 
								'</tr>\r\n' + 
								'<tr>\r\n' + 
									'<th style="border:1px solid #696969;">정답해설</th>\r\n' + 
									'<td style="border:1px solid #696969;">\r\n' + 
										nl2br(json.list[i].explanation) + '\r\n' + 
									'</td>\r\n' + 
								'</tr>\r\n' + 
								'<tr>\r\n' + 
									'<td style="border:1px solid #696969;" colspan="2">\r\n' +
										'<a class="btn" href="javascript:modifyPool(\'' + json.list[i].poolId + '\');">수정</a>\r\n' + 
										'<a class="btn btn-danger" href="javascript:deletePool(\'' + json.list[i].poolId + '\');">삭제</a>\r\n' + 
									'</td>\r\n' + 
								'</tr>\r\n' + 
						 		'</tbody>\r\n' + 
								'</table>\r\n';
					
					$('#pool-wrapper').append(el);
				}
			}
			else
				alert(json.msg);
		},
		complete : function(){
			resetPool();
			$('form[name=poolForm]').find('select[name=flag]').val(defaultFlag);
		}
	});
}

function showMultimediaPopup()
{
	window.open('/admin/multimedia/multimediaSearchPopup.do' , 'multimedia' , 'width=870,height=700,scrollbars=yes');
}

function saveMultimedia(mmId)
{
	$.ajax({
		url : 'additionalAction.do' ,
		type : 'post' ,
		data : 'mode=add&headwordId=<%=headwordId%>&mmId=' + mmId ,
		dataType : 'json' ,
		error : function(){
			alert('서버와 연결할 수 없습니다.');
		},
		success : function(json){
			if( json.result == 'SUCCESS' )
			{
				$('#additional-wrapper').find('ul').empty();
				
				var loopCnt = json.list.length;
				
				for(var i = 0 ; i < loopCnt ; i ++)
				{
					var item = json.list[i];
					
					$('#additional-wrapper').find('ul').append('<li style="background-color:#eee;border:1px solid #ddd;padding:10px;margin:5px;" class="additional-' + item.mmId + '">\r\n' +
							'<div style="float:left;font-size:11pt;">\r\n' +
								item.title + '\r\n' +
							'</div>\r\n' +
							'<div style="float:right;">\r\n' +
								'<a href="javascript:deleteAdditional(\'' + item.mmId + '\');" class="btn btn-danger">삭제</a>\r\n' +
							'</div>\r\n' +
							'<div style="clear:both;"></div>\r\n' +
						'</li>\r\n');
				}
			}
			else
				alert(json.msg);
		}
	});
}

function deleteAdditional(mmId)
{
	$.ajax({
		url : 'additionalAction.do' ,
		type : 'post' ,
		data : 'mode=delete&headwordId=<%=headwordId%>&mmId=' + mmId ,
		dataType : 'json' ,
		error : function(){
			alert('서버와 연결할 수 없습니다.');
		},
		success : function(json){
			if( json.result == 'SUCCESS' )
			{
				$('#additional-wrapper').find('ul').empty();
				
				var loopCnt = json.list.length;
				
				for(var i = 0 ; i < loopCnt ; i ++)
				{
					var item = json.list[i];
					
					$('#additional-wrapper').find('ul').append('<li style="background-color:#eee;border:1px solid #ddd;padding:10px;margin:5px;" class="additional-' + item.mmId + '">\r\n' +
							'<div style="float:left;font-size:11pt;">\r\n' +
								item.title + '\r\n' +
							'</div>\r\n' +
							'<div style="float:right;">\r\n' +
								'<a href="javascript:deleteAdditional(\'' + item.mmId + '\');" class="btn btn-danger">삭제</a>\r\n' +
							'</div>\r\n' +
							'<div style="clear:both;"></div>\r\n' +
						'</li>\r\n');
				}
			}
			else
				alert(json.msg);
		}
	});
}

function goDelete()
{
	if( confirm('삭제된 데이타는 복구할 수 없습니다.\r\n정말로 삭제하시겠습니까?') )
	{
		$.ajax({
			url  : 'deleteHeadword.do' ,
			type : 'post' ,
			data : 'headwordId=<%=headword.getHeadwordId()%>' ,
			dataType : 'json' ,
			error : function(){
				alert('서버와 통신 중 에러가 발생되었습니다.');
			},
			success : function(json){
				if( json.result == 'SUCCESS' )
				{
					goList();
				}
				else
					alert(json.msg);
			}
		});
	}
}

/**
 * 썸네일을 삭제한다.
 */
function deleteThumbnail()
{
	if( confirm('목록 이미지를 삭제하시겠습니까?') )
	{
		$.ajax({
			url : 'deleteThumbnail.do' ,
			type : 'post',
			data : 'headwordId=<%=headword.getHeadwordId()%>' ,
			dataType : 'json' ,
			error : function(){
				alert('서버와의 통신도중 에러가 발생되었습니다.');
			},
			success : function(json){
				if( json.result == 'SUCCESS' )
				{
					location.reload();
				}
				else
					alert(json.msg);
			}
		});
	}
}

//-->
</script>