<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	int pg = (Integer) request.getAttribute("pg");
	int boardType = Common.getParameter(request, "boardType", 2);
	String viewType =  Common.getParameter(request, "viewType", "1");
	int findCategory = (Integer) request.getAttribute("findCategory");
	String findMethod = (String) request.getAttribute("findMethod");
	String findStr = (String) request.getAttribute("findStr");

	Article article = (Article) request.getAttribute("article");
	BoardConfig boardConfig = (BoardConfig) request.getAttribute("boardConfig");
	List<BoardCategory> categories = (List<BoardCategory>) request.getAttribute("categories");
	List<FileInfo> attachments = (List<FileInfo>) request.getAttribute("attachments");
	
	String returnPage = (String) request.getAttribute("returnPage");
%>
<%//=boardConfig.getHeaderSection() %>
<link rel="stylesheet" type="text/css" href="/assets/front/lib/2017css/sub2017.css" />
<script type="text/javascript" src="/assets/front/lib/js/jquery.selectbox.min.js"></script>
<script type="text/javascript" src="/assets/front/lib/js/jquery.blockui.js"></script>
<!-- component: data_register -->
<div class="data_make">
	<form name="wform" action="" method="post">
	<input type="hidden" name="boardId" value="<%=boardConfig.getBoardId()%>"/>
	<input type="hidden" name="articleId" value="<%=article.getArticleId()%>"/>
	<input type="hidden" name="pg" value="<%=pg%>"/>
	<input type="hidden" name="findCategory" value="<%=findCategory%>"/>
	<input type="hidden" name="findMethod" value="<%=findMethod%>"/>
	<input type="hidden" name="findStr" value="<%=findStr%>"/>
	<input type="hidden" name="returnPage" value="<%=returnPage%>"/>
	<input type="hidden" name="thumbnail"/>
	<input type="hidden" name="attachment"/>
	<input type="hidden" name="boardType" value="<%=boardType%>"/>
	<input type="hidden" name="viewType" value="<%=viewType%>"/>	
	<p class="text02">* 항목은 필수입력 입니다.</p>
	<table cellpadding="0" cellspacing="0" summary="">
		<caption></caption>
		<colgroup>
			<col width="100" />
			<col width="*" />
		</colgroup>
		<tbody>
			<tr>
				<th>제목 <span class="pt_01">*</span></th>
				<td class="sel_bx"><input type="text" name="subject" value="<%=article.getSubject() %>" class="title_text" /></td>
			</tr>
			<% if( boardConfig.getUseCategory().equals("Y")){ %>
			<tr>
				<th scope="row">분류 <span class="pt_01">*</span></th>
				<td class="select_bx">
					<p class="bx01">
						<select name="categoryId">
							<% for(BoardCategory category : categories){ %>
							<option value="<%=category.getCategoryId()%>" <% if( category.getCategoryId() == article.getCategoryId()){ %>selected<% } %>><%=category.getName() %></option>
							<% } %>
						</select>
					</p>
				</td>
			</tr>
			<% } %>
			<tr>
				<td colspan="2" style="padding:0px;padding-top:3px;padding-bottom:3px;">
					<textarea id="contents-data" style="display:none;">
					<% if( boardConfig.getBoardId() == 27 || boardConfig.getBoardId() == 28 || boardConfig.getBoardId() == 29){ %>
						<% if(article.getContents() == "" ){ %>
							<div class="txc-textbox"><p style="text-align: center;"><span style="color: rgb(102, 102, 102); line-height: 20px; font-family: 돋움, Dotum, AppleGothic,;" serif";="" sans-="" transparent;"="" 12.22px;="">왼쪽 상단의 사진을 클릭 후 원본 사진을 선택하세요.</span></p></div>
								<p style="text-align: justify;">
									<span style="color: rgb(102, 102, 102); line-height: 20px; font-family: 돋움, Dotum, AppleGothic,;" serif";="" sans-="" transparent;"="" 12.22px;=""><strong></strong></span>&nbsp;
								</p>
								<p style="text-align: justify;">
									<span style="color: rgb(102, 102, 102); line-height: 20px; font-family: 돋움, Dotum, AppleGothic,;" serif";="" sans-="" transparent;"="" 12.22px;="">
										<strong>사진 설명</strong>:&nbsp;사진에 대한 간단한 설명을 넣어주세요.
									</span>	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
								<p style="text-align: justify;">&nbsp;</p>
								<p style="text-align: justify;">
									<font color="#666666" face="돋움, Dotum, AppleGothic, sans- serif">
										<span style="line-height: 20px; font-size: 12.22px;"><b>관련단원</b>: 교과에&nbsp; 활용 시 참고할 수 있는 단원을 적어주세요.</span></font></p>
								<p style="text-align: justify;">&nbsp;</p>
								<p style="text-align: justify;">
									<font color="#666666" face="돋움, Dotum, AppleGothic, sans- serif">
										<span style="line-height: 20px; font-size: 12.22px;"><b>위치 및 장소</b></span>: 사진을 찍으신 위치 및 장소에 대한 정보를 간단히 넣어주세요.</span>
									</font>
								</p>
								<p style="text-align: justify;">&nbsp;</p>
								<p style="text-align: justify;">
									<font color="#666666" face="돋움, Dotum, AppleGothic, sans- serif">
										<span style="line-height: 20px; font-size: 12.22px;"><b>출처</b></span>: 사진에 대한 출처를 적어주세요.</font>
									<font color="#666666" face="돋움, Dotum, AppleGothic, sans- serif">
										<span style="line-height: 20px; font-size: 12.22px;">(자신이 찍은 사진은 성함을 써 주시고,&nbsp;아니면 찍은 분의 성함을 써 주세요.)</span>
									</font>
								</p>
								<p style="text-align: justify;">&nbsp;</p>								
								<p style="text-align: justify;">
									<font color="#666666" face="돋움, Dotum, AppleGothic, sans- serif">
										<span style="line-height: 20px; font-size: 12.22px;"><b>※ 저작권 및 저작권료 지급 관련 안내 ※</b></span><br/>나눔터에 올린 사진 중 교과서 등 당사 출판물에 사용된 사진에 대해서는 저작권료를 지급하겠습니다. <br/>사진의 저작권은 기부한 선생님께 있으며, 사진의 출처와 저작권자를 명확하게 공개하겠습니다.
									</font>
								</p>
						<% }else{ %>
							<%=article.getContents() %>
						<% } %>	
					<% }else{ %>
						<%=article.getContents() %>
					<% } %>	
					</textarea>
					<tiles:insertDefinition name="daumeditor"/>
				</td>
			</tr>
			<tr>
				<th>검색키워드</th>
				<td><input type="text" name="keywords" value="<%=article.getKeywords() %>" class="title_text" /></td>
			</tr>
			<% if( boardConfig.getBoardId() == 27 || boardConfig.getBoardId() == 28 || boardConfig.getBoardId() == 29){ %>		
			<% }else{ %>
			<tr>			
				<th>출처</th>			
				<td>
					<input type="text" name="etc" value="<%=article.getEtc() %>" class="title_text" />					
					<p class="text_info">				
					등록할 경우 미리보기 형 목록 화면(해당 유형만), 조회 화면의 내용 하단에 노출됩니다.				
				   </p>
				</td>
			</tr>
			<% } %>
			<% if( boardConfig.getUseLink().equals("Y")){ %>
			<tr>
				<th>링크</th>
				<td><input type="text" name="linkUrl" value="<%=article.getLinkUrl() %>" class="title_text" /></td>
			</tr>
			<% } %>
			<% if( !boardConfig.getBoardType().equals("LIST") && !boardConfig.getBoardType().equals("FAQ") ){ %>
			<tr class="file_wrap add">
				<th rowspan="2">목록이미지 <span class="pt_01">*</span></th>
				<td>
					<a href="javascript:showFileDialog('thumbnail');" class="file" data-prefix="thumbnail" data-file-count="1"><img src="/assets/front/img/btn/btn_file_add.gif" alt="파일등록" /></a>
					<p>
						<% if( boardConfig.getBoardType().equals("WEBZINE")){ %>
						목록에 노출되는 이미지 입니다.<br /> 이미지 사이즈 : 150 X 208 / 파일 형식 : jpg,gif, png
						<% }else{ %>
						목록에 노출되는 이미지 입니다.<br /> 이미지 사이즈 : 150 X 115<span class="pt_01">(원본사진을 넣으면 자동 조절)</span> / 파일 형식 : jpg,gif, png
						<% } %>
					</p>
				</td>
			</tr>
			<tr class="file_wrap add">
				<td id="thumbnail-files">
					<% if( article.getThumbnail() > 0 && !Validate.isEmpty(article.getThumbnailPath()) ){ %>
					<span><div attached-prefix="thumbnail" attached-file-id="<%=article.getThumbnail()%>"><img src="<%=article.getThumbnailPath() %>" style="max-width:800px;"/><a href="javascript:deleteAttachedFile('thumbnail','<%=article.getThumbnail()%>');"><img src="/assets/front/img/btn/btn_add_del.gif" alt="파일삭제" /></a></div></span>
					<% }else{ %>
					<p class="add_none">첨부된 파일이 없습니다.</p>
					<% } %>
				</td>
			</tr>
			<% } %>
			<% if( boardConfig.getUseFileUpload().equals("Y")){ %>
			<tr class="file_wrap add">
				<th rowspan="2">첨부파일</th>
				<td>
					<a href="javascript:showFileDialog('attachment');" data-prefix="attachment" data-file-count="<%=boardConfig.getAllowFileCount()%>" <% if( boardConfig.getAllowFileCount() <= attachments.size()){ %>style="display:none;"<%} %> class="file"><img src="/assets/front/img/btn/btn_file_add.gif" alt="파일등록" /></a>
					<p>
						파일은 3개까지 등록 가능합니다. (개당 2048 Kbytes 허용)<br /> 허용되는 파일 형식 : <%=boardConfig.getAllowFileTypes() %>
					</p>
				</td>
			</tr>
			<tr class="file_wrap add">
				<td id="attachment-files">
					<% for(FileInfo attachment : attachments){	%>
					<p class="add_file add01" attached-prefix="attachment" attached-file-id="<%=attachment.getFileId()%>">
						<span><%=attachment.getOriginName() %></span> <a href="javascript:deleteAttachedFile('attachment' , '<%=attachment.getFileId()%>');"><img src="/assets/front/img/btn/btn_file_del.gif" alt="첨부파일삭제" /></a>
					</p>
					<% } %>
				</td>
			</tr>
			<% } %>
			<% if( boardConfig.getUseMovie().equals("Y")){ %>
			<tr>
				<th>동영상소스</th>
				<td><input type="text" name="movieUrl"  value="<%=article.getMovieUrl() %>" class="title_text" /></td>
			</tr> 
			<% } %>
			<% if( boardConfig.getBoardId() == 27 || boardConfig.getBoardId() == 28 || boardConfig.getBoardId() == 29){ %>
			<tr>
			<th>저작권 동의 <span class="pt_01">*</span></th>
			<td><p class="text_info"><span class="pt_01">※ 사용료 지급 및 저작권 안내</span><br>
					- 등록한 사진 중 교과서 등 당사 출판물에 사용된 경우 저작권자에게 소정의 저작권료 지급 합니다.<br>
					- 자료에 대한 권리/의무 및 저작권 침해 관련 소송에 대한 책임은 게시 당사자에게 있습니다.<br>
					- 게시판 성격에 맞지 않는 글은 관리자에 의해 통보 없이 삭제될 수 있습니다.<br>
					<input type="checkbox" id="site"/> 동의합니다.		
				</p>	
			</td>
			</tr>
			<% } %>
		</tbody>
	</table>
	</form>
	<div class="write_btn">
		<a href="javascript:checkForm();" class="btn biglistBtn pink">등록<!-- <img src="/assets/front/img/btn/btn_send.gif" alt="등록" /> --></a> <a href="javascript:goList();" class="btn biglistBtn">목록<!-- <img src="/assets/front/img/btn/btn_list.gif" alt="목록" /> --></a>
	</div>
</div>

<!-- component: layer popup -->
<div class="layerpopup small attach_file" id="fileDialog" style="display:none;">
	<form name="fileForm" action="" method="post" enctype="multipart/form-data" onsubmit="saveAttachFile();return false;">
	<input type="hidden" name="prefix"/>
	<input type="hidden" name="boardId" value="<%=boardConfig.getBoardId()%>"/>
	<div class="lp_header">
		<h2>파일 등록</h2>
		<a href="#" class="btn_close unblock"><span class="blind">닫기</span></a>
	</div>
	<div class="lp_main">
		<div class="lp_main_header">
			<h3>등록할 파일을 선택하여 주시기 바랍니다.</h3>
		</div>
		<div class="lp_main_body">
			<div class="file_search">
				<input type="file" id="file_route" name="upfile" class="file_text" title=""/>
			</div>
		</div>
		<p class="btn">
			<a href="javascript:saveAttachFile();"><img src="/assets/front/img/btn/btn_storage.gif" alt="저장" /></a>
			<a href="#" class="unblock"><img src="/assets/front/img/btn/btn_cancel.gif" alt="취소" /></a>
		</p>
	</div>
	<div class="lp_footer"></div>
	</form>
</div>

<form name="listForm" action="" method="get">
<input type="hidden" name="mode" value="list"/>
<input type="hidden" name="pg" value="<%=pg%>"/>
<input type="hidden" name="findCategory" value="<%=findCategory %>"/>
<input type="hidden" name="findMethod" value="<%=findMethod%>"/>
<input type="hidden" name="findStr" value="<%=findStr%>"/>
<input type="hidden" name="boardType" value="<%=boardType%>"/>
<input type="hidden" name="viewType" value="<%=viewType%>"/>
</form>

<iframe name="actionFrame" width="0" height="0" frameborder="0"></iframe>

<script type="text/javascript">
<!--
//load default contents data
$(document).ready(function(){
	Editor.modify({
		"content": document.getElementById("contents-data")
	});
});

function saveAttachFile()
{
	var f = document.forms.fileForm;
	
	f.target = 'actionFrame';
	f.action = '/commons/file/upload.do';
	f.submit();
}

function checkForm()
{
	var f = document.forms.wform;
	
	setFiles();
	
	<% if( boardConfig.getUseCategory().equals("Y")) { %>
	if( f.categoryId.value == '') return doError('카테고리를 선택하여주십시오.' , 'categoryId' , 'wform');
	<% } %>
	
	if( f.subject.value == '' ) return doError('제목을 입력하세요.' , 'subject' , 'wform');
	
	var validator = new Trex.Validator();
	var content = Editor.getContent();
	if (!validator.exists(content)) return alert('내용을 입력하세요');
	
	<% // if( boardConfig.getBoardId() == 27 || boardConfig.getBoardId() == 28 || boardConfig.getBoardId() == 29){ %>
	// if( f.etc.value == '' ) return doError('출처를 입력하세요.' , 'etc' , 'wform');
	<%// } %>
	
	<% if( !boardConfig.getBoardType().equals("LIST")){ %>
	if( $('#thumbnail-files').find('[attached-prefix=thumbnail]').length == 0) return alert('썸네일 이미지를 등록하여주십시오.');
	<% } %>
	
	<% if( boardConfig.getUseLink().equals("Y")) { %>
	if( f.linkUrl.value == '') return doError('외부 링크를 입력하십시오.' , 'linkUrl' , 'wform');
	<% } %>
	<% if( boardConfig.getUseMovie().equals("Y")) { %>
	if( f.movieUrl.value == '') return doError('동영상 링크를 입력하십시오.' , 'movieUrl' , 'wform');
	<% } %>
	<% if( boardConfig.getBoardId() == 27 || boardConfig.getBoardId() == 28 || boardConfig.getBoardId() == 29){ %>
	if( $('#site').is(':checked') == false ) return alert('저작권에 동의하셔야 합니다.');
	<% } %>
	
	// save contents
	saveContent();

	f.target = '_self';
	f.action = '/common/board/action.do';
	f.submit();
} 

function showFileDialog(prefix)
{
	var f = document.forms.fileForm;
	
	f.reset();
	f.prefix.value = prefix;
	
	$.blockUI({
		css : {
			backgroundColor : '',
			border : 0 ,
			marginTop : -500
		},
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
	{
		$('#' + prefix + '-files').empty();
		$('#' + prefix + '-files').append('<span><div attached-prefix="' + prefix + '" attached-file-id="' + fileId + '"><img src="' + filePath + '" style="max-width:800px;"/><a href="javascript:deleteAttachedFile(\'thumbnail\',\'' + fileId + '\');"><img src="/assets/front/img/btn/btn_add_del.gif" alt="파일삭제" /></a></div></span>');
	}
	else if( prefix == 'attachment' )
	{
		$('#' + prefix + '-files').append('<p class="add_file add01" attached-prefix="' + prefix + '" attached-file-id="' + fileId + '">\r\n' + 
				'<span>' + fileName + '</span> <a href="javascript:deleteAttachedFile(\'' + prefix + '\',\'' + fileId + '\');"><img src="/assets/front/img/btn/btn_file_del.gif" alt="첨부파일삭제" /></a>\r\n' + 
				'</p>\r\n');
		//$('#' + prefix + '-files').append('<div style="margin:5px 0;" attached-prefix="' + prefix + '" attached-file-id="' + fileId + '">' + fileName + '<button class="btn" style="margin-left:5px;" onclick="deleteAttachedFile(\'' + prefix + '\',\'' + fileId + '\');">파일삭제</button></div>');
	}
		
	
	if( $('#' + prefix + '-files').children().length >= fileCount )
		$('[data-prefix=' + prefix + ']').css('display' , 'none');
	
	$.unblockUI();
}

function goList()
{
	var f = document.forms.listForm;
	f.submit();
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
			error : function(){
				alert('처리도중 에러가 발생되었습니다.');
			},
			success : function(json){
				if( json.result == 'SUCCESS' )
				{
					var fileCount = parseInt($('[data-prefix=' + prefix + ']').attr('data-file-count'));
					$('[attached-file-id=' + fileId + ']').remove();
					
					if( $('#' + prefix + '-files').children().length < fileCount )
						$('[data-prefix=' + prefix + ']').show();
					
					if( prefix == 'thumbnail' )
					{
						$('[data-prefix=' + prefix + ']').show();
						$('#thumbnail-files').empty().append('<p class="add_none">첨부된 파일이 없습니다.</p>');
					}
				}
				else
					alert(json.msg);
			}
		});
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
//-->
</script>
