<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	String mode = (String) request.getAttribute("mode");
	int eventId = (Integer) request.getAttribute("eventId");
	int pg = (Integer) request.getAttribute("pg");
	Event event = (Event) request.getAttribute("event");
	//BoardConfig boardConfig = (BoardConfig) request.getAttribute("boardConfig");
%>

<div class="well">
	<form name="lform" action="" method="post">
	<input type="hidden" name="pg" value="<%=pg %>" />
	</form>
	
	<iframe name="actionFrame" width="0" height="0" frameborder="0"></iframe>
	<form name="wform" action="" method="post">
	<input type="hidden" name="mode" value="<%=mode %>" />
	<input type="hidden" name="eventId" value="<%=eventId %>" />
	<input type="hidden" name="bannerImg" />
	<table class="table">
		<col width="100"/>
		<col width="350"/>
		<tbody> 
		<tr>
			<th>이벤트 명(*)</th>
			<td>
				<input type="text" name="title" maxLength="128" value="<%=event.getTitle() %>" />
			</td>
		</tr>
		<tr>
			<th>이벤트 기간(*)</th>
			<td>
			<input type="text" id="txtDateS" name="startDate"  value="<%=event.getStartDate() %>" readonly> 
			~
			<input type="text" id="txtDateE" name="endDate" value="<%=event.getEndDate() %>" readonly>
			</td>
		</tr>
		<tr>
			<th>요약내용(*)</th>
			<td>
				<textarea name="summaryContents" style="margin: 0px; width: 450px; height: 80px;"><%=event.getSummaryContents() %></textarea>
			</td>
		</tr>
		<tr>
			<th>배너 이미지(*)</th>
			<td>
				<div>
					<button name="goForm" class="btn btn-danger" onclick="showFileDialog('banner');" data-prefix="banner" data-file-count="1" >파일 등록</button><br/>
					허용되는 파일 형식 : jpg, gif, png <br/>
					배너 사이즈 : 330 x 180<br/>
				 </div>
				<div id="banner-files" attached-prefix="banner" attached-file-id="<%=event.getBannerImg()%>">
				<% if( !event.getBannerImg().equals("") ){ %>
						<img src="<%=event.getBannerImg() %>" style="max-width:800px;"/>
				<%} %>
				</div>
				<br/>
			</td>
		</tr>
		<tr>
			<th>상세내용(*)</th>
			<td>
				<tiles:insertDefinition name="daumeditor"/>
				<textarea id="contents-data" style="display:none;"><%=event.getContents() %></textarea>
			</td>
		</tr>
		
		<tr>
			<th>댓글</th>
			<td>
				<input id="test1" type="radio" name="useComment" value="Y" <% if( event.getUseComment().equals("Y")){ %>checked<% } %>/> 사용
				<input id="test2" type="radio" name="useComment" value="N" <% if( event.getUseComment().equals("N")){ %>checked<% } %>/> 미사용
			</td>
			<th>답글</th>
			<td>
				<input type="radio" name="useReply" value="Y" <% if( event.getUseReply().equals("Y")){ %>checked<% } %>/> 사용
				<input type="radio" name="useReply" value="N" <% if( event.getUseReply().equals("N")){ %>checked<% } %>> 미사용
			</td>
		</tr>
		</tbody>
		</table>
	</form>
</div>
<!-- 버튼 영역 -->
<div class="btn-toolbar">
	<button class="btn btn-primary" onclick="checkForm();"><i class="icon-plus"></i> 저장</button>
	<%if(mode.equals("modify")){ %><button class="btn btn-danger" onclick="eventDelete();">삭제</button> <%}%>
	<button class="btn" onclick="goList();">리스트</button>
	<div class="btn-group">
	</div>
</div>

<!-- 레이어 팝업 영역 -->
<div class="modal" id="fileDialog" style="display:none;">
	<form name="fileForm" action="" method="post" enctype="multipart/form-data" onsubmit="saveAttachFile();return false;">
	<input type="hidden" name="boardId" value="<%=eventId %>"/>
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

//load default contents data
$(document).ready(function(){
	Editor.modify({
		"content": document.getElementById("contents-data")
	});
});

function addComment()
{	
	var f = document.forms.commentForm;
	
	if( f.comment.value == '' ) return doError('내용을 입력해주십시오.' , 'comment' , 'commentForm');
	
	$.ajax({
		url : '/api/article/addComment.do' ,
		type : 'post' ,
		data : $('form[name=commentForm]').serialize() ,
		dataType : 'json' ,
		error : function(){
			alert('서버와 통신을 할 수 없습니다.');
		},
		success : function(json){
			if( json.result == 'SUCCESS' )
			{	
				f.reset();
				$('textarea').val('');
				location.reload();
			}
			else
			{
				if( json.resultType == 'LOGIN' )
				{
					if( confirm('로그인이 필요합니다.\r\n로그인 하시겠습니까?') )
					{
						goLogin();
					}
				}
				else
					alert(json.msg);
			}
		}
	});
}


function goList()
{
	var f = document.forms.lform;
	f.target = '_self';
	f.action = 'eventList.do';
	f.submit();
}

function checkForm()
{
	var f = document.forms.wform;
	setFiles();
	
	// save contents
	saveContent();
	if( f.title.value == '') return doError('타이틀을 입력하여주십시오.' , 'title' , 'wform');
	if( f.startDate.value == '') return doError('이벤트 기간을 입력하여주십시오.' , 'startDate' , 'wform');
	if( f.endDate.value == '') return doError('이벤트 기간을 입력하여주십시오.' , 'endDate' , 'wform');
	if( f.summaryContents.value == '') return doError('요약내용을 입력하여주십시오.' , 'summaryContents' , 'wform');
	if( $("#banner-files").find("img").length < 1 ) {
		return alert('배너 이미지를 등록하여 주십시오');
		showFileDialog('banner');
	}
	
	var validator = new Trex.Validator();
	var content = Editor.getContent();
	if (!validator.exists(content)) return alert('내용을 입력하세요');
	
	f.target = '_self';
	f.action = 'eventAction.do';
	f.submit();
	
}

function eventDelete()
{
	if(confirm('해당 글을 삭제합니다.\r\n진행하시겠습니까?')){
		var f = document.forms.wform;
		f.target = '_self';
		f.action = 'eventAction.do'
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
	
	if($("input[name=upfile]").attr("value")=="")
		return true;
	var IMG_FORMAT = "\.(gif|jpg|jpeg|png)$"; 
    if(!(new RegExp(IMG_FORMAT, "i")).test($("input[name=upfile]").attr("value"))){
    	
	    $("input[name=upfile]").focus();
	    alert("목록 이미지는 gif,jpg,png만 첨부하실 수 있습니다.");
    	return false;
    }else{
		f.target = 'actionFrame';
		f.action = 'eventUpload.do';
		f.submit();
    }
}

function attachedFile( prefix , fileId , fileName , filePath )
{	
	var fileCount = parseInt($('[data-prefix=' + prefix + ']').attr('data-file-count'));
	// add image
	if( prefix == 'banner')
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
		if( $(this).attr('attached-prefix') == 'banner' )
			f.bannerImg.value = $(this).attr('attached-file-id');
	});
}


//-->
</script>