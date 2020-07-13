<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	BoardConfig boardConfig = (BoardConfig) request.getAttribute("boardConfig");
	List<BoardCategory> boardCategories = (List<BoardCategory>) request.getAttribute("boardCategories");
	Article article = (Article) request.getAttribute("article");
	List<FileInfo> attachments = (List<FileInfo>) request.getAttribute("attachments");
	
	int boardId = (Integer) request.getAttribute("boardId");
	int articleId = (Integer) request.getAttribute("articleId");
	int pg = (Integer) request.getAttribute("pg");
	String mode = (String) request.getAttribute("mode");
	String findMethod = (String) request.getAttribute("findMethod");
	String findStr = (String) request.getAttribute("findStr");
	
	String tmp_Sub_category = "";
	
	if( article == null ){
		article = new Article();
	}
	else{
		tmp_Sub_category = article.getSub_category();
	}

	
%>
<div class="btn-toolbar">
	<button class="btn btn-primary" onclick="checkForm();"><i class="icon-plus"></i> 저장</button>	
	<% if( mode.equals("modify") ){ %>
	<button class="btn btn-primary" onclick="goReply();">답변</button>
	<button class="btn btn-danger" onclick="deleteArticle();">삭제</button>
	<% } %>
	<button class="btn" onclick="goList();">리스트</button>
	<% if(articleId > 0) { %>
	<button class="btn" onclick="preView('<%=articleId%>');">미리보기</button>
	<% } %>
	<div class="btn-group">
	</div>
</div>

<div class="well">
	<form name="wform" action="" method="post">
	<input type="hidden" name="mode" value="<%=mode%>"/>
	<input type="hidden" name="boardId" value="<%=boardId%>"/>
	<input type="hidden" name="articleId" value="<%=articleId%>"/>
	<input type="hidden" name="thumbnail"/>
	<input type="hidden" name="attachment"/>
	<table class="table">
	<col width="150"/>
	<col width="*"/>
	<col width="150"/>
	<col width="*"/>
	<tbody>
	<% if(articleId > 0) { %>
	<tr>
		<th>*사용구분</th>
		<td colspan="3">
		<input type="radio" name="isUse" value="Y" <% if( article.getIsUse().equals("Y")){ %>checked<% } %>/>사용&nbsp;&nbsp;&nbsp;
		<input type="radio" name="isUse" value="N" <% if( article.getIsUse().equals("N")){ %>checked<% } %>/>미사용
		</td>
	</tr>		
	<% } else { %>
	<tr>
		<th>*사용구분</th>
		<td colspan="3">
		<input type="radio" name="isUse" value="Y" />사용&nbsp;&nbsp;&nbsp;
		<input type="radio" name="isUse" value="N" checked/>미사용
		</td>
	</tr>		
	
	<% } %>
	<% if( mode.equals("modify")){ %>
	<tr>
		<th>글쓴이</th>
		<td colspan="3">
			<%=article.getUserId() %>
		</td>
	</tr>
	<% } %>	
	<% if( boardConfig.getUseCategory().equals("Y")){ %>
	<tr>
		<th>카테고리</th>
		<td colspan="3">
			<select id="categoryId" name="categoryId">
				<% for(BoardCategory category : boardCategories){ %>
				<option value="<%=category.getCategoryId() %>" <% if (article.getCategoryId() == category.getCategoryId()){ %>selected<% } %>><%=category.getName() %></option>
				<% } %>
			</select>
		</td>
	</tr>
	<% } %>
	
	<% if(boardId == 62){ %>
	<tr>
		<th>서브 카테고리</th>
		<td colspan="3">
			<select id="sub_category" name="sub_category">
				<c:forEach items="${sub_category }" var="list">
					<option value="${list.code }" <c:if test="${list.code == article.getSub_category() }">selected</c:if> >${list.codenm }</option>
				</c:forEach>
			</select>
		</td>
	</tr>
	<%} %>
	<tr>
		<th>공지구분</th>
		<td colspan="3">
			<select name="isNotice">
				<option value="N">일반글</option>
				<option value="Y" <% if( article.getIsNotice().equals("Y")){ %>selected<% } %>>공지모드 게시물 입니다.</option>
			</select>
		</td>
	</tr>
	<% if(boardId==36 || boardId==37 ){ %>
	<tr>
		<th>메인노출</th>
		<td colspan="3">
			<select name="isSecret">
				<option value="N" <% if( article.getIsSecret().equals("N")){ %>selected<% } %>>미노출</option>
				<option value="Y" <% if( article.getIsSecret().equals("Y")){ %>selected<% } %>>노출</option>
			</select>
		</td>
	</tr>
	<%} %>
	<tr>
		<th>제목</th>
		<td colspan="3">
			<input type="text" name="subject" value="<%=article.getSubject()%>" style="width:350px;"/>
		</td>
	</tr>
	<!-- 
	<tr>
		<th>요약설명</th>
		<td colspan="3">
			<input type="text" name="introduce" value="<%=article.getIntroduce()%>" style="width:550px;"/>
		</td>
	</tr>
	//-->
	<% if( boardConfig.getUseLink().equals("Y")){ %>
	<tr>
		<th>외부 링크</th>
		<td colspan="3">
			<input type="text" name="linkUrl" value="<%=article.getLinkUrl()%>" style="width:450px;"/>
		</td>
	</tr>
	<% } %>
	<% if( boardConfig.getUseMovie().equals("Y")){ %>
	<tr>
		<th>동영상 링크</th>
		<td colspan="3">
			<input type="text" name="movieUrl" value="<%=article.getMovieUrl()%>" style="width:450px;"/>
		</td>
	</tr>
	<% } %>
	<% if( !boardConfig.getBoardType().equals("LIST") && !boardConfig.getBoardType().equals("FAQ") ){ %>
	<tr>
		<th>썸네일</th>
		<td colspan="3">
			<button class="btn btn-danger" onclick="showFileDialog('thumbnail');" data-prefix="thumbnail" data-file-count="1" <% if( article.getThumbnail() > 0 ){ %>style="display:none;"<% } %>>파일 등록</button>
			<div id="thumbnail-files">
				<% if( article.getThumbnail() > 0){ %>
				<div attached-prefix="thumbnail" attached-file-id="<%=article.getThumbnail()%>"><img src="<%=article.getThumbnailPath() %>" style="max-width:800px;"/><br/><button class="btn" style="margin-top:5px;" onclick="deleteAttachedFile('thumbnail','<%=article.getThumbnail()%>');">파일삭제</button></div>
				<% } %>
			</div>
			<div>
				<% if( boardConfig.getBoardType().equals("WEBZINE")){ %>
				썸네일의 이미지 사이즈는 가로 150px X 세로 208px 입니다.
				<% }else{ %>
				썸네일의 이미지 사이즈는 가로 208px X 세로 137px 입니다.
				<% } %>
			</div>
		</td>
	</tr>
	<% } %>
	<tr>
		<th>내용</th>
		<td colspan="3">
			<tiles:insertDefinition name="daumeditor"/>
			<textarea id="contents-data" style="display:none;"><%=article.getContents() %></textarea>
		</td>
	</tr>
	<% if( mode.equals("modify")){ %>
	<tr>
		<th>글쓴이</th>
		<td colspan="3">
			<%=article.getUserId() %>
		</td>
	</tr>
	<% } %>
	<tr>
		<th>출처</th>
		<td colspan="3">
			<input type="text" name="etc" value="<%=article.getEtc()%>" style="width:450px;"/>
		</td>
	</tr>
	<tr>
		<th>검색키워드</th>
		<td colspan="3">
			<input type="text" name="keywords" value="<%=article.getKeywords()%>" style="width:650px;"/>
		</td>
	</tr>
	<% if(boardConfig.getUseFileUpload().equals("Y")){ %>
	<tr>
		<th>첨부파일</th>
		<td colspan="3">
			<button class="btn btn-danger" onclick="showFileDialog('attachment');" data-prefix="attachment" data-file-count="<%=boardConfig.getAllowFileCount()%>" <% if( boardConfig.getAllowFileCount() <= attachments.size()){ %>style="display:none;"<%} %>>파일 등록</button>
			<div id="attachment-files">
				<% for(FileInfo attachment : attachments){	%>
				<div style="margin:5px 0;" attached-prefix="attachment" attached-file-id="<%=attachment.getFileId()%>"><%=attachment.getOriginName() %><button class="btn" style="margin-left:5px;" onclick="deleteAttachedFile('attachment','<%=attachment.getFileId()%>');">파일삭제</button></div>
				<% } %>
			</div>
			<div style="color:red;font-weight:bold;">파일은 <%=boardConfig.getAllowFileCount() %>(개당 <%=boardConfig.getAllowFileSize()%> Kbytes 허용)개까지 등록가능합니다.</div>
			<div style="color:red;font-weight:bold;">허용되는 파일확장자는 <%=boardConfig.getAllowFileTypes() %> 입니다.</div>	
		</td>
	</tr>
	<% } %>
	</tbody>
	</tbody>
	</table>
	</form>
</div>
<div class="btn-toolbar">
	<button class="btn btn-primary" onclick="checkForm();"><i class="icon-plus"></i> 저장</button>
	<% if( mode.equals("modify") ){ %>
	<button class="btn btn-primary" onclick="goReply();">답변</button>
	<button class="btn btn-danger" onclick="deleteArticle();">삭제</button>
	<% } %>
	<button class="btn" onclick="goList();">리스트</button>
	<button class="btn" onclick="preView('<%=articleId%>');">미리보기</button>
	<div class="btn-group">
	</div>
</div>

<div class="modal" id="fileDialog" style="display:none;">
	<form name="fileForm" action="" method="post" enctype="multipart/form-data" onsubmit="saveAttachFile();return false;">
	<input type="hidden" name="prefix"/>
	<input type="hidden" name="boardId" value="<%=boardConfig.getBoardId()%>"/>
    <div class="modal-header">
        <button type="button" class="close unblock">×</button>
        <h3>파일 등록</h3>
    </div>
    <div class="modal-body" style="text-align:left;">
		<label>파일 선택</label>
       	<input type="file" name="upfile">
    </div>
    <div class="modal-footer">
    	<a href="javascript:saveAttachFile();" class="btn btn-danger"></i>저장</a>
        <a href="#" class="btn unblock">취소</a>
    </div>
    </form>
</div>

<form name="qform" action="list.do" method="get">
<input type="hidden" name="boardId" value="<%=boardId%>"/>
<input type="hidden" name="pg" value="<%=pg%>"/>
<input type="hidden" name="findMethod" value="<%=findMethod%>"/>
<input type="hidden" name="findStr" value="<%=findStr%>"/>
</form>

<form name="replyform" action="form.do" method="get">
<input type="hidden" name="mode" value="reply"/>
<input type="hidden" name="articleId" value="<%=articleId%>"/>
<input type="hidden" name="boardId" value="<%=boardId%>"/>
<input type="hidden" name="pg" value="<%=pg%>"/>
<input type="hidden" name="findMethod" value="<%=findMethod%>"/>
<input type="hidden" name="findStr" value="<%=findStr%>"/>
</form>

<iframe name="actionFrame" width="0" height="0" frameborder="0"></iframe>

<script type="text/javascript">
<!--
// load default contents data
$(document).ready(function(){
	Editor.modify({
		"content": document.getElementById("contents-data")
	});
	
	$('#categoryId').change(function(){
		
		if($('#categoryId').val() == 263 && <%=boardId%> == 62){
			 
			$("#sub_category").show();
		}
		else{
			$("#sub_category").hide();	
		}		
	});
	
	if( $('#categoryId').val() == 263 && <%=boardId%> == 62 ){
		$("#sub_category").show();
	}
	else{
		$("#sub_category").hide();	
	}
	
});
var flag="";
function saveAttachFile()
{
	var f = document.forms.fileForm;
	
	f.target = 'actionFrame';
	f.action = '/commons/file/upload.do';
	f.submit();
}

function showFileDialog(prefix)
{
	var f = document.forms.fileForm;
	
	f.reset();
	f.prefix.value = prefix;
	
	$.blockUI({
		message : $('#fileDialog') ,
		onBlock: function() { 
            $('.unblock').click(function(){
            	$.unblockUI();
            });
        }
	});
}

function attachedFile( prefix , fileId , fileName , filePath )
{	
	var fileCount = parseInt($('[data-prefix=' + prefix + ']').attr('data-file-count'));
	
	// add image
	if( prefix == 'thumbnail')
		$('#' + prefix + '-files').append('<div attached-prefix="' + prefix + '" attached-file-id="' + fileId + '"><img src="' + filePath + '" style="max-width:800px;"/><br/><button class="btn" style="margin-top:5px;" onclick="deleteAttachedFile(\'' + prefix + '\',\'' + fileId + '\');">파일삭제</button></div>');
	else if( prefix == 'attachment' )
		$('#' + prefix + '-files').append('<div style="margin:5px 0;" attached-prefix="' + prefix + '" attached-file-id="' + fileId + '">' + fileName + '<button class="btn" style="margin-left:5px;" onclick="deleteAttachedFile(\'' + prefix + '\',\'' + fileId + '\');">파일삭제</button></div>');
	
	if( $('#' + prefix + '-files').children().length >= fileCount )
		$('[data-prefix=' + prefix + ']').css('display' , 'none');
	
	$.unblockUI();
}

function deleteAttachedFile(prefix , fileId)
{
	if( confirm('삭제된 파일은 복구할 수 없습니다.\r\n진행하시겠습니까?') )
	{
		// server side에서 삭제한다.
		$.ajax({
			url : '/commons/file/delete.do',
			type : 'post' ,
			data : 'fileId=' + fileId ,
			dataType : 'json' ,
			success : function(json){
				var fileCount = parseInt($('[data-prefix=' + prefix + ']').attr('data-file-count'));
				$('[attached-file-id=' + fileId + ']').remove();
				
				if( $('#' + prefix + '-files').children().length < fileCount )
					$('[data-prefix=' + prefix + ']').css('display' , 'block');
				
				/*
				if( json.result == 'FAILURE' )
					alert(json.msg);
				*/
			}
		});
	}
}

function goList()
{
	document.forms.qform.submit();
}

function goReply()
{
	document.forms.replyform.submit();
}

function checkForm()
{
	var f = document.forms.wform;
	
	setFiles();
	
	// save contents
	Editor.save();	

	if(flag=="N"){
		return;
	}
	//saveContent();
		

	
	<% if( boardConfig.getUseCategory().equals("Y")) { %>
	if( f.categoryId.value == '') return doError('카테고리를 선택하여주십시오.' , 'categoryId' , 'wform');
	<% } %>
	<% if( boardConfig.getUseLink().equals("Y")) { %>
	if( f.linkUrl.value == '') return doError('외부 링크를 입력하십시오.' , 'linkUrl' , 'wform');
	<% } %>
	<% if( boardConfig.getUseMovie().equals("Y")) { %>
	if( f.movieUrl.value == '') return doError('동영상 링크를 입력하십시오.' , 'movieUrl' , 'wform');
	<% } %>	

	if( f.subject.value == '' ) return doError('제목을 입력하세요.' , 'subject' , 'wform');
	//if( f.contents.value == '' ) return doError('내용을 입력하세요.' , 'contents' , 'wform');
		
	<% if( !boardConfig.getBoardType().equals("LIST") && !boardConfig.getBoardType().equals("FAQ") ){ %>
	//if( $('#thumbnail-files').find('[attached-prefix=thumbnail]').attr('attached-prefix') == undefined ) return alert('썸네일 이미지는 필수입니다.');
	<% } %>
	
	
	
	if(<%=boardId%> == 62 ){
		if($('#categoryId').val() == 264 || $('#categoryId').val() == 265 || $('#categoryId').val() == 266){
			//$("#sub_category").val() == "002";
			f.sub_category.value = "002";
		}
	}
	
	f.target = '_self';
	f.action = 'articleAction.do';
	f.submit();
}
function validForm(editor) {
	// Place your validation logic here

	// sample : validate that content exists
	var validator = new Trex.Validator();
	var content = editor.getContent();
	if (!validator.exists(content)) {
		alert('내용을 입력하세요.' );	
		flag="N";
		return false;
	}
	flag="Y";
	return true;

}
function deleteArticle()
{
	if(confirm('삭제된 데이타는 복구할 수 없습니다.\r\n진행하시겠습니까?'))
	{
		var f = document.forms.wform;
		
		f.mode.value = 'delete';
		
		f.target = '_self';
		f.action = 'articleAction.do';
		f.submit();
	}
}

/**
 * 파일의 정보를 저장한다.
 */
function setFiles()
{
	var f = document.forms.wform;
	var attachment = [];
	
	$('[attached-file-id]').each(function(){
		if( $(this).attr('attached-prefix') == 'thumbnail' )
			f.thumbnail.value = $(this).attr('attached-file-id');
		else if( $(this).attr('attached-prefix') == 'attachment' )
		{
			attachment.push($(this).attr('attached-file-id'));
		}
	});
	
	f.attachment.value = attachment.join(',');
}

function preView(aid) 
{
	var w = window.document.body.offsetWidth;
	var h = window.document.body.offsetHeight;
	var url = "https://thub.kumsung.co.kr/customer/notice.do?articleId="+aid+"&mode=detail";
	window.open(url,"_blank","top=0,left=0,width="+w+",height="+h+",toolbar=0,status=0,scrollbars=1,resizeable=0");
}
//-->
</script>