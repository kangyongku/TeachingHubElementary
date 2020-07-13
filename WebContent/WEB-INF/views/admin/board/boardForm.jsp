<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	BoardConfig boardConfig = (BoardConfig) request.getAttribute("boardConfig");
	List<BoardCategory> boardCategories = (List<BoardCategory>) request.getAttribute("boardCategories");
	int boardId = (Integer) request.getAttribute("boardId"); 
	String mode = (String) request.getAttribute("mode");	
%>
<div class="btn-toolbar">
	<button class="btn btn-primary" onclick="checkForm();"><i class="icon-plus"></i> 저장</button>
	<button class="btn" onclick="goList();">리스트</button>
	<div class="btn-group">
	</div>
</div>
<div class="well">
	<form name="wform" action="" method="post">
	<input type="hidden" name="mode" value="<%=mode%>"/>
	<input type="hidden" name="boardId" value="<%=boardId%>"/>
	<input type="hidden" name="categoryItems"/>
	<table class="table">
	<col width="150"/>
	<col width="*"/>
	<col width="150"/>
	<col width="*"/>
	<tbody>
	<% if(mode.equals("modify")){ %>
	<tr>
		<th>게시판 ID</th>
		<td colspan="3"><%=boardConfig.getBoardId() %></td>
	</tr>
	<% } %>
	<tr>
		<th>게시판 구분</th>
		<td colspan="3">
			<select name="boardFlag">
				<option value="">:: 게시판 구분 선택::</option>
				<option value="M1" <% if(boardConfig.getBoardFlag().equals("M1")){ %>selected<%} %>>학생참여수업</option>
				<option value="M2" <% if(boardConfig.getBoardFlag().equals("M2")){ %>selected<%} %>>자유학년제·창체</option>
				<option value="M3" <% if(boardConfig.getBoardFlag().equals("M3")){ %>selected<%} %>>나눔소통</option>
				<option value="PDS" <% if(boardConfig.getBoardFlag().equals("PDS")){ %>selected<%} %>>열린학습자료</option>
				<option value="CMT" <% if(boardConfig.getBoardFlag().equals("CMT")){ %>selected<%} %>>선생님행복마당</option>
				<option value="CS" <% if(boardConfig.getBoardFlag().equals("CS")){ %>selected<%} %>>CS관리</option>
				<option value="CA" <% if(boardConfig.getBoardFlag().equals("CA")){ %>selected<%} %>>창의적체험활동</option>
			</select>
		</td>
	</tr>
	<tr>
		<th>게시판명</th>
		<td colspan="3"><input type="text" name="boardName" value="<%=boardConfig.getBoardName()%>"/></td>
	</tr>
	<tr>
		<th>게시판 형태</th>
		<td colspan="3">
			<select name="boardType">
				<%-- <option value="SUMMARY" <% if(boardConfig.getBoardType().equals("SUMMARY")){ %>selected<% } %>>개요+게시판</option> --%>
				<option value="LIST" <% if(boardConfig.getBoardType().equals("LIST")){ %>selected<% } %>>목록형</option>
				<option value="GALLERY" <% if(boardConfig.getBoardType().equals("GALLERY")){ %>selected<% } %>>갤러리형</option>
				<option value="PREVIEW" <% if(boardConfig.getBoardType().equals("PREVIEW")){ %>selected<% } %>>미리보기형</option>
				<option value="WEBZINE" <% if(boardConfig.getBoardType().equals("WEBZINE")){ %>selected<% } %>>웹진형</option>
				<option value="FAQ" <% if(boardConfig.getBoardType().equals("FAQ")){ %>selected<% } %>>FAQ형</option>
			</select>
		</td>
	</tr>
	<tr>
		<th>게시판 상단 HTML</th>
		<td colspan="3">
			<textarea name="headerSection" style="width:98%;height:80px;"><%=boardConfig.getHeaderSection() %></textarea>
		</td>
	</tr>
	<tr>
		<th>게시판 하단 HTML</th>
		<td colspan="3"> 
			<textarea name="footerSection" style="width:98%;height:80px;"><%=boardConfig.getFooterSection() %></textarea>
		</td>
	</tr>
	<tr>
		<th>카테고리 사용</th>
		<td colspan="3">
			<input type="radio" name="useCategory" value="Y" <% if( boardConfig.getUseCategory().equals("Y")){ %>checked<% } %>/> 사용
			<input type="radio" name="useCategory" value="N" <% if( boardConfig.getUseCategory().equals("N")){ %>checked<% } %>/> 미사용
		</td>
	</tr>
	<tr>
		<th>카테고리 구분</th>
		<td colspan="3">
			<select id="categoryList" size="5">
				<% for(BoardCategory category : boardCategories){ %>
				<option value="<%=category.getCategoryId() %>" category-id="<%=category.getCategoryId()%>"><%=category.getName() %></option>
				<% } %>
			</select>
			<div>
				<a class="btn" href="javascript:showCategoryDialog();">카테고리 등록</a>
				<a class="btn" href="javascript:modifyCategoryDialog();">수정</a>
				<a class="btn" href="javascript:deleteCategory();">삭제</a>
			</div>
		</td>
	</tr>
	<tr>
		<th>글쓰기</th>
		<td>
			<input type="radio" name="useWrite" value="Y" <% if( boardConfig.getUseWrite().equals("Y")){ %>checked<% } %>/> 사용
			<input type="radio" name="useWrite" value="N" <% if( boardConfig.getUseWrite().equals("N")){ %>checked<% } %>/> 미사용
		</td>
		<th>추천</th>
		<td>
			<input type="radio" name="useRecommend" value="Y" <% if( boardConfig.getUseRecommend().equals("Y")){ %>checked<% } %>/> 사용
			<input type="radio" name="useRecommend" value="N" <% if( boardConfig.getUseRecommend().equals("N")){ %>checked<% } %>/> 미사용
		</td>
	</tr>
	<tr>
		<th>댓글</th>
		<td>
			<input type="radio" name="useComment" value="Y" <% if( boardConfig.getUseComment().equals("Y")){ %>checked<% } %>/> 사용
			<input type="radio" name="useComment" value="N" <% if( boardConfig.getUseComment().equals("N")){ %>checked<% } %>/> 미사용
		</td>
		<th>답글</th>
		<td>
			<input type="radio" name="useReply" value="Y" <% if( boardConfig.getUseReply().equals("Y")){ %>checked<% } %>/> 사용
			<input type="radio" name="useReply" value="N" <% if( boardConfig.getUseReply().equals("N")){ %>checked<% } %>> 미사용
		</td>
	</tr>
	<tr>
		<th>동영상</th>
		<td>
			<input type="radio" name="useMovie" value="Y" <% if( boardConfig.getUseMovie().equals("Y")){ %>checked<% } %>/> 사용
			<input type="radio" name="useMovie" value="N" <% if( boardConfig.getUseMovie().equals("N")){ %>checked<% } %>/> 미사용
		</td>
		<th>외부링크</th>
		<td>
			<input type="radio" name="useLink" value="Y" <% if( boardConfig.getUseLink().equals("Y")){ %>checked<% } %>/> 사용
			<input type="radio" name="useLink" value="N" <% if( boardConfig.getUseLink().equals("N")){ %>checked<% } %>/> 미사용
		</td>
	</tr>
	<tr>
		<th>비회원 리스트</th>
		<td>
			<input type="radio" name="useAnnmsList" value="Y" <% if( boardConfig.getUseAnnmsList().equals("Y")){ %>checked<% } %>/> 사용
			<input type="radio" name="useAnnmsList" value="N" <% if( boardConfig.getUseAnnmsList().equals("N")){ %>checked<% } %>/> 미사용
		</td>
		<th>비회원 상세</th>
		<td>
			<input type="radio" name="useAnnmsView" value="Y" <% if( boardConfig.getUseAnnmsView().equals("Y")){ %>checked<% } %>/> 사용
			<input type="radio" name="useAnnmsView" value="N" <% if( boardConfig.getUseAnnmsView().equals("N")){ %>checked<% } %>/> 미사용
		</td>
	</tr>
	<tr>
		<th>SNS 사용 유무</th>
		<td>
			<input type="radio" name="useSns" value="Y" <% if( boardConfig.getUseSns().equals("Y")){ %>checked<% } %>/> 사용
			<input type="radio" name="useSns" value="N" <% if( boardConfig.getUseSns().equals("N")){ %>checked<% } %>/> 미사용
		</td>
	<% if(boardConfig.getBoardType().equals("GALLERY") || boardConfig.getBoardType().equals("PREVIEW")) { %>
		<th>등록일 노출 유무</th>
		<td>
			<input type="radio" name="modifyRegDate" value="Y" <% if( boardConfig.getModifyRegDate().equals("Y")){ %>checked<% } %>/> 사용
			<input type="radio" name="modifyRegDate" value="N" <% if( boardConfig.getModifyRegDate().equals("N")){ %>checked<% } %>/> 미사용
		</td>
	<% } %>	
		<th></th><td></td>
	</tr>
	<tr>
		<th>파일 업로드</th>
		<td colspan="3">
			<input type="radio" name="useFileUpload" value="Y" <% if( boardConfig.getUseFileUpload().equals("Y")){ %>checked<% } %>/> 사용
			<input type="radio" name="useFileUpload" value="N" <% if( boardConfig.getUseFileUpload().equals("N")){ %>checked<% } %>/> 미사용
		</td>
	</tr>
	<tr>
		<th>첨부파일 갯수</th>
		<td colspan="3">
			<select name="allowFileCount" style="width:80px;">
				<% for(int i = 1 ; i <= 5 ; i++){ %>
				<option value="<%=i%>" <% if(boardConfig.getAllowFileCount() == i){ %>selected<% } %>><%=i %></option>
				<% } %>
			</select>
			개
		</td>
	</tr>
	<tr>
		<th>허용 파일 확장자</th>
		<td colspan="3">
			<input type="text" name="allowFileTypes" style="width:500px;" value="<%=boardConfig.getAllowFileTypes()%>"/>
		</td>
	</tr>
	<tr>
		<th>허용 파일 용량</th>
		<td colspan="3">
			<input type="text" name="allowFileSize" style="width:150px;" value="<%=boardConfig.getAllowFileSize()%>"/> Kbyes ( 1024 Kbytes = 1MByte )
		</td>
	</tr>
	</tbody>
	</tbody>
	</table>
	</form>
</div>
<div class="btn-toolbar">
	<button class="btn btn-primary" onclick="checkForm();"><i class="icon-plus"></i> 저장</button>
	<button class="btn" onclick="goList();">리스트</button>
	<div class="btn-group">
	</div>
</div>

<div class="modal small" id="categoryDialog" style="display:none;">
	<form name="categoryForm" action="" method="post" onsubmit="saveCategory();return false;">
	<input type="hidden" name="mode"/>
    <div class="modal-header">
        <button type="button" class="close unblock">×</button>
        <h3>카테고리 관리</h3>
    </div>
    <div class="modal-body" style="text-align:left;">
		<label>카테고리명</label>
       	<input type="text" name="categoryName" class="input-xlarge">
    </div>
    <div class="modal-footer">
    	<a href="javascript:saveCategory();" class="btn btn-danger"></i>저장</a>
        <button class="btn unblock">취소</button>
    </div>
    </form>
</div>

<script type="text/javascript">
<!--

$(document).ready(function(){
	checkFileConfig();
	
	// add file check handler
	$('input[name=useFileUpload]').click(function(){
		checkFileConfig();
	});
});

function showCategoryDialog()
{
	document.forms.categoryForm.reset();
	
	$.blockUI({
		message : $('#categoryDialog'),
		onBlock: function() { 
            $('.unblock').click(function(){
            	$.unblockUI();
            });
        }
	});
}

function modifyCategoryDialog()
{
	if( $('#categoryList option:selected').val() != undefined )
	{
		document.forms.categoryForm.reset();
		document.forms.categoryForm.mode.value = 'modify';
		document.forms.categoryForm.categoryName.value = $('#categoryList option:selected').text();
		
		$.blockUI({
			message : $('#categoryDialog'),
			onBlock: function() { 
	            $('.unblock').click(function(){
	            	$.unblockUI();
	            });
	        }
		});
	}
	else
		alert('선택된 카테고리가 없습니다.');
}

function deleteCategory()
{	
	if( $('#categoryList option:selected').val() != undefined )
	{
		if(confirm('해당 카테고리에 게시글이 존재할 경우 정상적이로 노출되지 않을 수 있습니다.\r\n진행하시겠습니까?'))
		{
			var boardId = '<%=boardConfig.getBoardId()%>';
			var categoryId = $('#categoryList option:selected').attr('category-id');
			
			if( boardId == 0  || categoryId == '' )
			{
				$('#categoryList option:selected').remove();	
			}
			else
			{
				$.ajax({
					url  : 'deleteCategory.do',
					type : 'post' , 
					data : 'boardId=' + boardId + '&categoryId=' + categoryId,
					dataType : 'json' ,
					error : function(){
						alert('처리도중 에러가 발생되었습니다.');
					},
					success : function(json){
						if( json.result == 'SUCCESS' )
							$('#categoryList option:selected').remove();
						else
							alert(json.msg);
					}
				});
			}
		}
	}
	else
		alert('선택된 카테고리가 없습니다.');
}

function saveCategory()
{	
	var f = document.forms.categoryForm;
	
	if(f.categoryName.value == '') return doError('카테고리명을 입력해주십시오.' , 'categoryName' , 'categoryForm');
	
	( f.mode.value == 'modify' ) ? 
			$('#categoryList option:selected').text(f.categoryName.value) :
			$('#categoryList').append('<option value="" category-id="">' + f.categoryName.value + '</option>');
	
	$.unblockUI();
}

function goList()
{
	location.href='boardList.do';
}

function checkFileConfig()
{	
	var readOnly = ( $('input:checked[name=useFileUpload]').val() == 'N' ) ? true : false; 
	
	$('select[name=allowFileCount]').attr('readonly' , readOnly);
	$('input[name=allowFileTypes]').attr('readonly' , readOnly);
	$('input[name=allowFileSize]').attr('readonly' , readOnly);
}

function gatheringCategoryItems()
{
	var items = [];
	
	$('#categoryList option').each(function(){
		var item = "{'categoryName' : '" + $(this).text() + "' , 'categoryId' : '" + $(this).attr('category-id') + "'}";
		items.push(item);
	});
	
	return "{'items' : [" + items.join(',') + "]}";
}

function checkForm()
{
	var f = document.forms.wform;
	
	if( f.boardName.value == '' ) return doError('게시판명을 입력해주십시오.' , 'boardName' , 'wform');
	if( f.boardType.value == '' ) return doError('게시판 타입을 선택해주십시오.' , 'boardType' , 'wform');
	if( f.boardFlag.value == '' ) return doError('게시판 구분을 선택해주십시오.' , 'boardFlag' , 'wform');
	
	if( $('input:checked[name=useFileUpload]').val() == 'Y' )
	{
		if( f.allowFileCount.value == 0 ) return doError('첨부파일 갯수를 입력해주십시오.' , 'allowFileCount' , 'wform');
		if( f.allowFileTypes.value == 0 ) return doError('허용 파일 확장자를 입력해주십시오.' , 'allowFileTypes' , 'wform');
		if( f.allowFileSize.value == 0 ) return doError('허용 파일 용량을 입력해주십시오.' , 'allowFileSize' , 'wform');
	}
	
	if( $('input:checked[name=useCategory]').val() == 'Y' )
	{
		var items = gatheringCategoryItems();
		f.categoryItems.value = items;
	}
	
	if( confirm('이렇게 저장하시겠습니까?') )
	{
		f.target = '_self';
		f.action = 'boardFormAction.do';
		f.submit();
	}
}


//-->
</script>