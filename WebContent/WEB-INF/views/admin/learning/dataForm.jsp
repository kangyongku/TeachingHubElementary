<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%//@ page import="com.synap.convert.*" %>
<%
	int pg = (Integer) request.getAttribute("pg");
	int dataId = (Integer) request.getAttribute("dataId");
	String findCategory = (String) request.getAttribute("findCategory");
	int findBookId = (Integer) request.getAttribute("findBookId");
	int findUnitId = (Integer) request.getAttribute("findUnitId");
	String findUnitIds = (String) request.getAttribute("findUnitIds");
	String findStartDate = (String) request.getAttribute("findStartDate");
	String findEndDate = (String) request.getAttribute("findEndDate");
	String findMethod = (String) request.getAttribute("findMethod");
	String findStr = (String) request.getAttribute("findStr");
	String mode = (String) request.getAttribute("mode");
	
	Data data = (Data) request.getAttribute("data");
	Multimedia multimedia = (Multimedia) request.getAttribute("multimedia");
	List<Category> categories = (List<Category>) request.getAttribute("categories");
	List<Category> chasi_categories = (List<Category>) request.getAttribute("chasi_categories");
	List<Category> types = (List<Category>) request.getAttribute("types");
	List<Book> books = (List<Book>) request.getAttribute("books");
	List<Data> chasi_view = (List<Data>) request.getAttribute("cview");
	List<Data> chasi_file = (List<Data>) request.getAttribute("cfile");
	int file_count = 0;

	String src_url = data.getDataFile();
	
	
	if( multimedia == null ) multimedia = new Multimedia();
%>
<div class="well">

	<div style="margin:10px;">
		<form name="wform" action="" method="post" enctype="multipart/form-data">
		<input type="hidden" name="dataId" value="<%=dataId%>"/>
		<input type="hidden" name="mode" value="<%=mode%>"/>
		<input type="hidden" name="category"/>
		<input type="hidden" name="unitId"/>
		<input type="hidden" name="dataType"/>
		<input type="hidden" name="mmId" value="<%=data.getMmId()%>"/>
		<input type="hidden" name="chkFileCount" value="0"/>
		<table class="table">
		<col width="150"/>
		<col width="120"/>
		<col width="*"/>
		<col width="*"/>
		<tbody>	
		<tr>
		<a href="http://thub.kumsung.co.kr/learning/detail.do?category=<%=data.getCategory() %>&bookId=<%=data.getBookId() %>" target="_blank">http://thub.kumsung.co.kr/learning/detail.do?category=<%=data.getCategory() %>&bookId=<%=data.getBookId() %></a>
			<th>분류</th>
			<td colspan="3">
				<select name="category-2" class="category" style="width:100px;margin-right:10px;float:left;" onchange="showCategory(2);getLearningBooks();">
					<option value="">::선택::</option>
					<% for(Category category : categories){ %>
					<option value="<%=category.getCategory()%>" data-depth="<%=category.getDepth() %>" data-children="<%=category.getChildren()%>"><%=category.getName() %></option>
					<% } %>
				</select>
				<select name="category-3" class="category" style="width:100px;margin-right:10px;float:left;display:none;" onchange="showCategory(3);getLearningBooks();">
					<option value="">::선택::</option>
				</select>
				<select name="category-4" class="category" style="width:100px;margin-right:10px;float:left;display:none;" onchange="showCategory(4);getLearningBooks();">
					<option value="">::선택::</option>
				</select>
			</td>
		</tr>
		<tr>
			<th>도서명</th>
			<td colspan="3">
				<select name="bookId" onchange="showLearningUnit('1');">
					<option value="">::도서선택::</option>
					<% for(Book book : books){ %>
					<option value="<%=book.getBookId()%>" <% if( book.getBookId() == data.getBookId()){ %>selected<% } %>><%=book.getName() %></option>
					<% } %>
				</select>
			</td>
		</tr>
		<tr>
			<th>단원</th>
			<td colspan="3">
				<div class="unitWrapper" <% if( data.getDataFlag().equals("C")){ %>style="display:none;"<% } %>>
					<select name="unit-1" style="float:left;" onchange="showLearningUnit('2');">
						<option value="">::대단원::</option>
					</select>
					<select name="unit-2" style="float:left;display:none;" onchange="showLearningUnit('3');">
						<option value="">::중단원::</option>
					</select>
					<select name="unit-3" style="float:left;display:none;">
						<option value="">::소단원::</option>
					</select>
					<div style="clear:both;"></div>
				</div>
			</td>
		</tr>
		<tr>
			<th>자료 구분</th>
			<td colspan="3">
				<input type="radio" name="dataFlag" value="U" <% if( data.getDataFlag().equals("U")){ %>checked<% } %>/> 단원 자료
				<input type="radio" name="dataFlag" value="C" <% if( data.getDataFlag().equals("C")){ %>checked<% } %>/> 공통 자료
			</td>
		</tr>
		<tr>
			<th>자료 유형</th>
			<td colspan="3">
				<select name="type-category-2" class="category" style="width:150px;margin-right:10px;float:left;" onchange="showCategory(2 , '', 'type-');showDataType(this);">
					<option value="">::선택::</option>
					<% for(Category category : types){ %>
					<option value="<%=category.getCategory()%>" data-depth="<%=category.getDepth() %>" data-children="<%=category.getChildren()%>"><%=category.getName() %></option>
					<% } %>
				</select>
				<select name="type-category-3" id="catechasi" class="category" style="width:150px;margin-right:10px;float:left;display:none;" onchange="showCategory(3 , '' , 'type-');">
					<option value="">::선택::</option>
				</select>
				<select name="type-category-4" class="category" style="width:150px;margin-right:10px;float:left;display:none;">
					<option value="">::선택::</option>
				</select>
			</td>
		</tr>
		<tr>
			<th rowspan="5">자료</th>
			<th>제목</th>
			<td colspan="2">
				<input type="text" name="title" value="<%=data.getTitle()%>"/> dataID=<%=dataId%><button type="button" id="chasi-preview-pop" class="btn" onclick="popPreview('<%=dataId%>');" <% if( data.getDataType().indexOf("D006") >= 0  ){ %>style="display:block;"<% }else{ %>style="display:none;"<% } %>>미리보기</button>
			</td>
		</tr>

		<tr id="chasi-preview" <% if( data.getDataType().indexOf("D006") >= 0  ){ %><% }else{ %>style="display:none;"<% } %>>
			<th>차시자료</th>
			<td colspan="2">
				<div>
					<button type="button" class="btn" onclick="addPreview();">추가</button>
				</div>	
				<%  for(Data cview : chasi_view){ %>
				<div cview-id="<%=cview.getcviewId()%>">
					<input type="text" name="chasi-preview-subj" style="width:150px;" value="<%=cview.getChasiStep()%>"/>
					<input type="text" name="chasi-preview-cont" style="width:400px;" value="<%=cview.getChasiPage()%>"/>
					<a href="javascript:delChasiViewEach('<%=cview.getcviewId()%>');"><strong>[삭제]</strong></a>
				</div>
				<% }%>
				<div id="chasi-preview-input"></div>		
		
			</td>
		</tr>

		<tr>
			<th id="dataFlagTitle">관련자료</th>
			<td colspan="2">
				<div id="dataAttachment" <% if( data.getDataType().indexOf("D003") >= 0 || data.getDataType().indexOf("D004") >= 0 || data.getDataType().indexOf("D005") >=0 || data.getDataType().indexOf("D006") >=0 || data.getDataType().indexOf("D007") >=0 ){ %>style="display:none;"<% } %>>
					<input type="file" name="upfile"/>
					<div id="attachment-file-data" style="padding:3px;font-weight:bold;">
						<a href="<%=data.getDataFile() %>" target="_blank"><%=data.getFileName() %></a>
					</div>
					<div>업로드 용량은 100MB까지 가능합니다.</div>
				</div>
				<div id="dataMultimedia" <% if( data.getDataType().indexOf("D003") >= 0 || data.getDataType().indexOf("D004") >= 0 || data.getDataType().indexOf("D005") >=0 || data.getDataType().indexOf("D007") >=0 ){ %>style="display:block;"<% }else{ %>style="display:none;"<% } %>>
					<a href="javascript:showMultimediaPopup();" class="btn">멀티미디어 자료 찾기</a>
					<% if( !Validate.isEmpty(multimedia.getTitle()) ){ %>
					<div><%=multimedia.getTitle() %></div>
					<% } %> 
				</div>
				<div id="dataChasi" <% if( data.getDataType().indexOf("D006") >= 0  ){ %>style="display:block;"<% }else{ %>style="display:none;"<% } %>>
					<% if(chasi_file!=null&&chasi_categories!=null){ 
					for(Data cfile : chasi_file){ %>
					<div id="ctrCData" cfile-id="<%=cfile.getcfileId()%>" cfile-data = "<%=cfile.getDataFile() %>">
						<select name="chasi-data" class="category" style="width:150px;margin-right:10px;float:left;" cfile-id="<%=cfile.getcfileId()%>">
							<option value="">::선택::</option>
							<% for(Category category : chasi_categories){ %>
							<option value="<%=category.getCategory()%>" data-depth="<%=category.getDepth() %>" data-children="<%=category.getChildren()%>" <% if(  cfile.getDataType().equals(category.getCategory())){ %>selected<% } %>><%=category.getName() %></option>
							<% } %>
						</select>
						<input type="hidden" name="cfile_id" value="<%=cfile.getcfileId()%>">
						<input type="file" name="cupfile"/> <a href="#none" onclick="delChasiFileEach(this);"><strong>[삭제]</strong></a>
						<div id="attachment-file-data" style="padding:3px;font-weight:bold;" cfile-id="<%=cfile.getcfileId()%>">
							<a href="<%=cfile.getDataFile() %>" target="_blank"><%=cfile.getFileName() %></a>
						</div>	
					</div>					
					<% file_count++;}} %>				
					<% if(chasi_categories!=null){ 
					for(int i = 0; i < chasi_categories.size()-file_count; i++){ %>
						<select name="chasi-data" class="category" style="width:150px;margin-right:10px;float:left;" >
							<option value="">::선택::</option>
							<% for(Category category : chasi_categories){ %>
							<option value="<%=category.getCategory()%>" data-depth="<%=category.getDepth() %>" data-children="<%=category.getChildren()%>"><%=category.getName() %></option>
							<% } %>
						</select>
						<input type="hidden" name="cfile_id" value="0">
						<input type="file" name="cupfile"/>
						<div id="attachment-file-data" style="padding:3px;font-weight:bold;">
							<a href="<%=data.getDataFile() %>" target="_blank"><%=data.getFileName() %></a>
						</div>						
					<% }} %>
					<div>업로드 용량은 100MB까지 가능합니다.</div>							
				</div>				
			</td>
		</tr>

						
		<tr>
			<th>검색키워드</th>
			<td colspan="2">
				<input type="text" name="keywords" style="width:600px;" value="<%=data.getKeywords()%>"/>
			</td>
		</tr>
		</tbody>
		</tbody>
		</table>
		</form>
		
		<div class="btn-toolbar">
			<button class="btn btn-primary" onclick="checkForm();"><i class="icon-plus"></i> 저장</button>
			<button class="btn btn-danger" onclick="deleteArticle();"><i class="icon-plus"></i> 삭제</button>
			<button class="btn" onclick="goList();">리스트</button>
			<div class="btn-group">
			</div>
		</div>
	</div>
	
</div>

<form name="listForm" action="dataList.do" method="get">
<input type="hidden" name="pg" value="<%=pg%>"/>
<input type="hidden" name="findCategory" value="<%=findCategory%>"/>
<input type="hidden" name="findBookId" value="<%=findBookId%>"/>
<input type="hidden" name="findUnitIds" value="<%=findUnitIds%>"/>
<input type="hidden" name="findUnitId" value="<%=findUnitId%>"/>
<input type="hidden" name="findStartDate" value="<%=findStartDate%>"/>
<input type="hidden" name="findEndDate" value="<%=findEndDate%>"/>
<input type="hidden" name="findMethod" value="<%=findMethod%>"/>
<input type="hidden" name="findStr" value="<%=findStr%>"/>
</form>

<script type="text/javascript">

$(document).ready(function(){
	<% if( mode.equals("modify")){ %>
	initCategory('<%=data.getCategory()%>');
	initCategory('<%=data.getDataType()%>','type-');
	renderLearningUnits('<%=data.getBookId()%>' , '<%=data.getUnitIds()%>');
	<% } %>
	
	$('input[name=dataFlag]').click(function(){
		if( $(this).val() == 'C' )
			$('.unitWrapper').css('display' , 'none');
		else
			$('.unitWrapper').css('display' , 'block');
	});
});

function deleteArticle()
{
	if( confirm('삭제된 자료는 복구할 수 없습니다.\r\n정말로 삭제하시겠습니까?') )
	{
		$.ajax({
			url : 'deleteDataArticle.do' ,
			type : 'post' ,
			data : 'dataId=<%=data.getDataId() %>' ,
			dataType : 'json' ,
			error : function(){
				alert('서버와 통신도중 에러가 발생하였습니다.');
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

function showMultimediaPopup()
{
	window.open('/admin/multimedia/multimediaSearchPopup.do' , 'multimedia' , 'width=870,height=700,scrollbars=yes');
}

function saveMultimedia(mmId , title , keywords)
{
	$('input[name=mmId]').val(mmId);
	$('input[name=title]').val(title);
	
	if( keywords != undefined)
		$('input[name=keywords]').val(keywords);
}

function checkForm()
{
	var f = document.forms.wform;
	
	// get data
	f.category.value = getCategoryCode();
	f.unitId.value = getUnitId();
	if( $('select[name=type-category-2]').val() == 'D006') {
		f.dataType.value = "D006";
		if(!chkFilecount()) return;
	}else if( $('select[name=type-category-2]').val() == 'D004') {
			f.dataType.value = "D004";
	}else{
		f.dataType.value = getCategoryCode('type-');	
	}
	

	if( f.category.value == '' ) return alert('카테고리를 선택해주십시오.');
	if( $('select[name=category-3]').val() == '') return alert('과목을 선택해주십시오.');
	if( f.bookId.value == '') return alert('도서를 선택해주십시오.');
	if( f.unitId.value == '' ) return alert('단원을 선택해주십시오.');
	if( $('input[name=dataFlag]').is(':checked') == false ) return alert('자료 구분을 선택해주십시오.');
	if( f.dataType.value == '' ) return alert('자료 유형을 선택해주십시오.');	
	if( $('select[name=type-category-2]').val() != 'D001'
			&& $('select[name=type-category-2]').val() != 'D002')
	{
		//if(f.mmId.value == '0') return alert('자료를 등록하여주십시오.');
	}
	
	if( $('input[name=dataFlag]:checked').val() == 'U' && $('select[name=unit-1]').val() == '' )
		return doError('단원을 선택하여주십시오.');
	
	if( f.title.value == '' ) return doError('제목을 입력해주십시오.' , 'title' , 'wform');
	
	f.target = '_self';
	f.action = 'dataFormAction.do';
	f.submit();
}

function showDataType(obj)
{
	if( obj.value == 'D003' || obj.value == 'D004' || obj.value == 'D005' || obj.value == 'D007')
	{
		$('#dataAttachment').css('display' , 'none');
		$('#dataMultimedia').css('display' , 'block');
		$('#dataChasi').css('display' , 'none');
		$('#chasi-preview').css('display' , 'none');
		$('#chasi-preview-pop').css('display' , 'none');
	}
	else if( obj.value == 'D006')
	{ 
		$('#dataAttachment').css('display' , 'none');
		$('#dataMultimedia').css('display' , 'none');
		$('#dataChasi').css('display' , 'block');
		$('#chasi-preview').css('display' , '');
		$('#chasi-preview-pop').css('display' , 'block');
	}
	else
	{ 
		$('#dataAttachment').css('display' , 'block');
		$('#dataMultimedia').css('display' , 'none');
		$('#dataChasi').css('display' , 'none');
		$('#chasi-preview').css('display' , 'none');
		$('#chasi-preview-pop').css('display' , 'none');
	}
}

function chkattach(obj){
	
}
function goList()
{
	var f = document.forms.listForm;
	
	f.target = '_self';
	f.submit();
}

function addPreview()
{
	var div = document.createElement('div');
	var len = $('input[name=chasi-preview-subj]').length;

	div.innerHTML = '<div>'
	div.innerHTML += '<input type="text" name="chasi-preview-subj" style="width:150px;" value=""/>\n';
	div.innerHTML += '<input type="text" name="chasi-preview-cont" style="width:400px;" value=""/>\n';
	div.innerHTML += '</div>';
	
	if(len<18){
		document.getElementById('chasi-preview-input').appendChild(div);	
	}
	else{
		alert('최대 18개만 추가 됩니다.')
	}
}

function popPreview(id){
	window.open('chasiPreview.do?dataId='+id , 'chasi_preview' , 'width=1024,height=760,scrollbars=yes');
	
}
function chkFilecount(){ 
	var type_len = $('select[name=chasi-data]').length;
	var file_len = $('input[name=cupfile]').length;
	var type_knt=0, file_knt = 0;
	

	for(i=0;i<type_len;i++){
		if($('select[name=chasi-data]').eq(i).val()!='')	type_knt++;
	}
	<%if(file_count>0){%>
		for(i=0;i<file_len;i++){
			if($('input[name=cfile_id]').eq(i).val()!='0'){
				file_knt++;
			}else if($('input[name=cupfile]').eq(i).val()!=''){
				file_knt++;
			}
		}
	<%}else{%>
		for(i=0;i<file_len;i++){
			if($('input[name=cupfile]').eq(i).val()!='')	file_knt++;
		}
	<%}%>
	
	
	//alert("유형선택:"+type_knt+", 파일업로드:"+file_knt);
	if(type_knt>file_knt){
		alert("첨부파일이 등록되지 않았습니다.");	
		return false;
	}else if(type_knt < file_knt){
		alert("첨부파일 자료유형이 선택되지 않았습니다.");
		return false;
	}else{
		return true;
	}
	
}

function delChasiViewEach(id){
	if( confirm('삭제된 자료는 복구할 수 없습니다.\r\n정말로 삭제하시겠습니까?') )
	{
		
		$.ajax({
			url : 'delChasiViewEach.do' ,
			type : 'post' ,
			data : 'cviewId='+id ,
			dataType : 'json' ,
			error : function(){
				alert('서버와 통신도중 에러가 발생하였습니다.');
			},
			success : function(json){
				if( json.result == 'SUCCESS' )
				{
					$('div[cview-id='+id+']').remove();
				}
				else
					alert(json.msg);
			}
		});
	}
}

function delChasiFileEach(obj){
	if( confirm('삭제된 자료는 복구할 수 없습니다.\r\n정말로 삭제하시겠습니까?') )
	{
		var sector = $(obj).parents().find('#ctrCData');
		var cfileId = sector.attr('cfile-id');
		var dataFile = sector.attr('cfile-data');

		//alert("cfileId:"+cfileId+", dataFile:"+dataFile);
		
 		$.ajax({
			url : 'delChasiFileEach.do' ,
			type : 'post' ,
			data : 'cfileId='+cfileId+'&dataFile='+dataFile ,
			dataType : 'json' ,
			error : function(){
				alert('서버와 통신도중 에러가 발생하였습니다.');
			},
			success : function(json){
				if( json.result == 'SUCCESS' )
				{
					location.href="dataForm.do?pg=1&dataId=<%=data.getDataId() %>";
/* 					sector.find('select[cfile-id='+cfileId+']').val('');
					$(obj).hide();
					if(sector.find('input[name=cfile_id]').val()==cfileId) sector.find('input[name=cfile_id]').val(''); 
					sector.find('div[cfile-id='+cfileId+']').html(''); */
					
				}
				else
					alert(json.msg);
			}
		});
	}
}

//-->
</script>