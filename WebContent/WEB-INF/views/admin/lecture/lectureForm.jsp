<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	String mode = (String) request.getAttribute("mode");
	int lectureId = (Integer) request.getAttribute("lectureId");
	int pg = (Integer) request.getAttribute("pg");
	Lecture lecture = (Lecture) request.getAttribute("lecture");
%>

<div class="well">
<form name="lform" action="" method="post">
<input type="hidden" name="pg" value="<%=pg %>" />
</form>
<iframe name="actionFrame" width="0" height="0" frameborder="0"></iframe>
<form name="wform" action="" method="post">
<input type="hidden" name="mode" value="<%=mode %>" />
<input type="hidden" name="lectureId" value="<%=lectureId %>" />
<input type="hidden" name="thumbnail" />
<table class="table">
	<col width="15%"/>
	<col width="85%"/>
	<tbody> 
	<tr>
		<th>분류(*)</th>
		<td>
			<input type="text" name="category" maxLength="128" value="<%=lecture.getCategory() %>" />
		</td>
	</tr>
	<tr>
		<th>제목(*)</th>
		<td>
			<input type="text" name="title" maxLength="128" value="<%=lecture.getTitle() %>" />
		</td>
	</tr>
	<tr>
		<th>요약내용(*)</th>
		<td>
			<textarea name="summaryContents" style="margin: 0px; width: 450px; height: 250px;"><%=lecture.getSummaryContents() %></textarea>
		</td>
	</tr>
	<tr>
		<th>목록 이미지(*)</th>
		<td>
			<div>
				<button name="goForm" class="btn btn-danger" onclick="showFileDialog('thumbnail');" data-prefix="thumbnail" data-file-count="1" >파일 등록</button><br/>
				허용되는 파일 형식 : jpg, gif, png <br/>
				배너 사이즈 : 150 x 115<br/>
			 </div>
			<div id="thumbnail-files" attached-prefix="thumbnail" attached-file-id="<%=lecture.getThumbnail()%>">
			<% if( !lecture.getThumbnail().equals("") ){ %>
					<img src="<%=lecture.getThumbnail() %>" style="max-width:800px;"/>
			<%} %>
			</div>
			<br/>
		</td>
	</tr>
	
	<tr>
		<th>
			동영상 소스 입력(*)
		</th>
		<td>
			<textarea name="linkUrl" style="margin: 0px; width: 450px; height: 50px;"><%=lecture.getLinkUrl() %></textarea>
		<td>
	</tr>
	<tr>
		<th>상세내용(*)</th>
		<td>
			<tiles:insertDefinition name="daumeditor"/>
			<textarea id="contents-data" style="display:none;"><%=lecture.getContents() %></textarea>
		</td>
	</tr>
	<tr>
		<th>출처(*)</th>
		<td>
			<select id="originCode" name="originCode">
				<option value="D">직접집필</option>
				<option value="TED">TED</option>
				<option value="VOD">VOD</option>
				<option value="Youtube">Youtube</option>
			</select>
			<input type="text" id="originText" name="originText" maxLenght="48" value="<%=lecture.getOriginText()%>"/>
		</td>
	</tr>
	</tbody>
	</table>
</form>
</div>
<!-- 버튼 영역 -->
<div class="btn-toolbar">
	<button class="btn btn-primary" onclick="checkForm();"><i class="icon-plus"></i> 저장</button>
	<%if(mode.equals("modify")){ %><button class="btn btn-danger" onclick="lectureDelete();">삭제</button> <%}%>
	<button class="btn" onclick="goList();">리스트</button>
	<div class="btn-group">
	</div>
</div>

<!-- 레이어 팝업 영역 -->
<div class="modal" id="fileDialog" style="display:none;">
	<form name="fileForm" action="" method="post" enctype="multipart/form-data" onsubmit="saveAttachFile();return false;">
	<input type="hidden" name="boardId" value="<%=lectureId %>"/>
	<input type="hidden" name="prefix"/>
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

<!-- 스크립트 영역 -->
<script type="text/javascript">
<!--

//load default contents data
$(document).ready(function(){
	Editor.modify({
		"content": document.getElementById("contents-data")
	});
	<%if (mode.equals("modify") ){%>
	$("#originCode").find("option[value=<%=lecture.getOriginCode()%>]").attr("selected","true")
	if('<%=lecture.getOriginCode()%>' == "D"){
		$("#originText").show();
	}else{
		$("#originText").val("");
		$("#originText").hide();
	}
	<%}%>
	
	$("#originCode").change(function(){
		if($(this).attr("value")=="D"){
			$("#originText").show();
		}else{
			$("#originText").val("");
			$("#originText").hide();
		}
	});
});

function goList()
{
	var f = document.forms.lform;
	f.target = '_self';
	f.action = 'lectureList.do';
	f.submit();
}

function checkForm()
{
	var f = document.forms.wform;
	
	setFiles();
	
	// save contents
	saveContent();
	
	if( f.title.value == '') return doError('제목을 입력하여주십시오.' , 'title' , 'wform');
	if( f.summaryContents.value == '') return doError('요약내용을 입력하여주십시오.' , 'summaryContents' , 'wform');
	if( $("#thumbnail-files").find("img").length < 1 ) {
		return alert('목록 이미지를 등록하여 주십시오');
		showFileDialog('thumbnail');
	}
	
	if( f.linkUrl.value == '') return doError('동영상 소스를 입력하여주십시오.' , 'linkUrl' , 'wform');
	if( f.contents.value == '') return doError('상세내용을 입력하여주십시오.' , 'contents' , 'wform');
	
	if( $("#originCode :selected").val() == "D" ){
		if( f.originText.value == '') return doError('출처내용을 입력하여 주십시오.' ,'originText' , 'wform');
	}
	
	f.target = '_self';
	f.action = 'lectureAction.do';
	f.submit();
	
}

function lectureDelete()
{
	if(confirm('해당 글을 삭제합니다.\r\n진행하시겠습니까?')){
		var f = document.forms.wform;
		f.target = '_self';
		f.action = 'lectureAction.do'
		f.mode.value = 'delete'
		f.submit();
	}
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

//배너 이미지 업로드
function saveAttachFile()
{
	var f = document.forms.fileForm;
	
	/* title : 확장자 체크. 정규식
	 * parameter = i 이미지, a 파일
	 */
	if($("input[name=upfile]").attr("value")=="")
		return true;
	var IMG_FORMAT = "\.(gif|jpg|jpeg|png)$"; 
    if(!(new RegExp(IMG_FORMAT, "i")).test($("input[name=upfile]").attr("value"))){
    	
	    $("input[name=upfile]").focus();
	    alert("목록 이미지는 gif,jpg,png만 첨부하실 수 있습니다.");
    	return false;
    }else{
	
		f.target = 'actionFrame';
		f.action = 'lectureUpload.do';
		f.submit();
    }
}

function attachedFile( prefix , fileId , fileName , filePath )
{	
	var fileCount = parseInt($('[data-prefix=' + prefix + ']').attr('data-file-count'));
	// add image
	if( prefix == 'thumbnail')
		$('#' + prefix + '-files').html('<div attached-prefix="' + prefix + '" attached-file-id="' + filePath + '"><img src="' + filePath + '" style="max-width:800px;"/><br/></div>');
	$.unblockUI();
}

/**
 * 파일의 정보를 저장한다.
 */
function setFiles()
{
	var f = document.forms.wform;

	$('[attached-file-id]').each(function(){
		if( $(this).attr('attached-prefix') == 'thumbnail' )
			f.thumbnail.value = $(this).attr('attached-file-id');
	});
}
//-->
</script>