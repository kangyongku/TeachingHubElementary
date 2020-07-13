<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	int pg = (Integer) request.getAttribute("pg");
	int bookId = (Integer) request.getAttribute("bookId");
	String mode = (String) request.getAttribute("mode");
	
	LegacyBook legacyBook = (LegacyBook) request.getAttribute("legacyBook");
	Book book = (Book) request.getAttribute("book");
	List<Category> categories = (List<Category>) request.getAttribute("categories");
	List<Category> courses = (List<Category>) request.getAttribute("courses");
	List<Unit> units = (List<Unit>) request.getAttribute("units");
	List<Cd> cdes = (List<Cd>) request.getAttribute("cdes");
	List<Category> spc_categories = (List<Category>) request.getAttribute("specialize_categories");
	
	Member member = (Member) session.getAttribute("adminMember");
	
	if( legacyBook == null )
		legacyBook = new LegacyBook();
%>
<div class="well">

	<div style="margin:10px;">		
		<table class="table">
		<col width="150"/>
		<col width="*"/>
		<col width="150"/>
		<col width="*"/>
		<tbody>	
		<form name="wform" action="" method="post">
		<input type="hidden" name="bookId" value="<%=bookId%>"/>
		<input type="hidden" name="mode" value="<%=mode%>"/>
		<input type="hidden" name="legacyId" value="<%=book.getLegacyId()%>"/>
		<input type="hidden" name="category"/>
		<a href="http://thub.kumsung.co.kr/learning/detail.do?category=<%=book.getCategory() %>&bookId=<%=book.getBookId() %>" target="_blank">http://thub.kumsung.co.kr/learning/detail.do?category=<%=book.getCategory() %>&bookId=<%=book.getBookId() %></a>
		<tr>
			<th>분류</th>
			<td colspan="3">
				<select name="category-2" class="category" style="width:100px;margin-right:10px;float:left;" onchange="showCategory(2);">
					<option value="">::선택::</option>
					<% for(Category category : categories){ %>
					<option value="<%=category.getCategory()%>" data-depth="<%=category.getDepth() %>" data-children="<%=category.getChildren()%>" <% if( category.getCategory().equals(book.getCategory()) ){ %>selected<% } %>><%=category.getName() %></option>
					<% } %>
				</select>
				<select name="category-3" class="category" style="width:100px;margin-right:10px;float:left;display:none;" onchange="showCategory(3);">
					<option value="">::선택::</option>
				</select>
				<select name="category-4" class="category" style="width:100px;margin-right:10px;float:left;display:none;" onchange="showCategory(4);">
					<option value="">::선택::</option>
				</select>
				<div style="clear:both;"></div>
			</td>
		</tr>
		<tr>
			<th>도서명</th>
			<td>
				<input type="text" name="name" value="<%=book.getName()%>"/>
				<a href="javascript:openLegacyBookSearch();" class="btn btn-primary">도서검색</a> <br/>
				<span id="legacyName"><% if( !Validate.isEmpty(legacyBook.getName()) ){%> 기간계 도서명 : <%=legacyBook.getName() %><% } %></span>
			</td>
			<th>교육과정</th>
			<td>
				<select name="course">
					<option value="">없음</option>
					<% for(Category category : courses){ %>
					<option value="<%=category.getCategory()%>" <% if( category.getCategory().equals(book.getCourse()) ){ %>selected<% } %>><%=category.getName()%></option>
					<% } %>
				</select>
			</td>
		</tr>
		<tr> 
			<th>저자</th>
			<td colspan="3">
				<input type="text" name="author" value="<%=book.getAuthor()%>"/>
			</td>
		</tr>
		<tr> 
			<th rowspan="2">교사용 CD보기</th>
			<td colspan="3">
				<input type="text" name="teacherCdView" value="<%=book.getTeacherCdView()%>" placeholder="URL 입력" style="width:550px;"/>
			</td>
		</tr>
		<tr>
			<td colspan="3">
				새창 가로 사이즈 : <input type="text" name="cdViewWidth" value="<%=book.getCdViewWidth()%>" style="width:80px;"/>
				새창 세로 사이즈 : <input type="text" name="cdViewHeight" value="<%=book.getCdViewHeight()%>" style="width:80px;"/>
			</td>
		</tr>
		<tr> 
			<th>미리보기 URL</th>
			<td colspan="3">
				<input type="text" name="previewUrl" value="<%=book.getPreviewUrl()%>" placeholder="URL 입력" style="width:550px;"/>
			</td>
		</tr>
		<tr> 
			<th>사용구분</th>
			<td colspan="3">
				<input type="radio" name="isUse" value="Y" <% if( Validate.isEmpty(book.getIsUse()) || book.getIsUse().equals("Y")){ %>checked<%} %>/> 사용
				<input type="radio" name="isUse" value="N" <% if( book.getIsUse().equals("N")){ %>checked<%} %>/> 미사용
			</td>
		</tr>
		<tr> 
			<th>정렬순서</th>
			<td colspan="3">
				<input type="text" name="sequence" value="<%=book.getSequence()%>"/>
				<div>정렬순서는 작은 순서가 먼저 노출되어집니다.</div>
			</td>
		</tr>
		<tr> 
			<th>특화자료</th>
			<td colspan="3">
				<select name="spc_category" class="category" style="width:200px;margin-right:10px;float:left;" >
					<option value="">::선택::</option>
					<% for(Category category : spc_categories){ %>
					<option value="<%=category.getCategory()%>" <% if(category.getCategory().equals(book.getSpecialCd())) out.print("selected");%>><%=category.getName() %></option>
					<% } %>
				</select>
			</td>
		</tr>	
		</form>
		
		<% if( mode.equals("modify")){ %>

		
		<tr>
			<th>교사용 CD 다운로드</th>
			<td colspan="3">
				<form name="cdForm" action="" method="post">
				<input type="hidden" name="bookId" value="<%=bookId%>"/>
				<table class="table">
				<col width="80"/>
				<col width="*"/>
				<col width="80"/>
				<col width="*"/>
				<col width="80"/>
				<tbody>	
				<tr>
					<th>CD명</th>
					<td><input type="text" name="name"/></td>
					<th>URL 경로</th>
					<td><input type="text" name="url"/></td>
					<td><a href="javascript:addTeacherCdDownload();" class="btn">등록</a></td>
				</tr>
				</tbody>
				</table>
				</form>
				
				<ul id="cdList">
					<% for(Cd cd : cdes){ %>
					<li style="background-color:#ddd;border:1px solid #eee;padding:5px;" class="cd-<%=cd.getCdId()%>"><%=cd.getName() %> | <%=cd.getUrl() %> <a href="javascript:deleteTeacherCdDownload('<%=cd.getCdId() %>');" class="btn btn-danger">삭제</a></li>
					<% } %>
				</ul>
				
				<script type="text/javascript">
				<!--
				
				function addTeacherCdDownload()
				{
					var f = document.forms.cdForm;
					
					if( f.name.value == '' ) return doError('CD명을 입력해주십시오.' , 'name' , 'cdForm');
					if( f.url.value == '' ) return doError('URL을 입력해주십시오.' , 'url' , 'cdForm');
					
					$.ajax({
						url : 'addTeacherCdDownload.do' ,
						type : 'post' ,
						data : $('form[name=cdForm]').serialize() ,
						dataType : 'json' ,
						error : function(){
							alert('서버와의 통신도중 에러가 발생되었습니다.');
						},
						success : function(json){
							if( json.result == 'SUCCESS' )
							{
								$('#cdList').append('<li style="background-color:#ddd;border:1px solid #eee;padding:5px;" class="cd-' + json.cd.cdId + '">' + json.cd.name + ' | ' + json.cd.url + ' <a href="javascript:deleteTeacherCdDownload(\'' + json.cd.cdId + '\');" class="btn btn-danger">삭제</a></li>');
							}
							else
								alert(json.msg);
						},
						complete : function(){
							f.reset();
						}
					});
				}
				
				function deleteTeacherCdDownload(cdId)
				{
					if( confirm('데이타를 삭제하시겠습니까?') )
					{
						$.ajax({
							url : 'deleteTeacherCdDownload.do' ,
							type : 'post' ,
							data : 'cdId=' + cdId ,
							dataType : 'json' ,
							error : function(){
								alert('서버와의 통신도중 에러가 발생되었습니다.');
							},
							success : function(json){
								if( json.result == 'SUCCESS' )
								{
									$('.cd-' + json.cdId).remove();
								}
								else
									alert(json.msg);
							}
						});
					}
				}
				
				//-->
				</script>
				
			</td>			
		</tr>
		<% } %>		
		</tbody>
		</tbody>
		</table>
		
		
		
		<div class="btn-toolbar">
			<button class="btn btn-primary" onclick="checkForm();"><i class="icon-plus"></i> 저장</button>
			<% if( member.getAuthType().equals("S") && bookId > 0 ){ %>
			<button class="btn btn-danger" onclick="deleteBook();"><i class="icon-plus"></i> 삭제</button>
			<% } %>
			<button class="btn" onclick="goList();">리스트</button>
			<div class="btn-group">
			</div>
		</div>
	</div>
	
	<% if( bookId > 0){ %>
	<div style="margin:10px;margin-top:30px;">
		
		<div class="btn-toolbar">
			<button class="btn btn-primary" onclick="showUnitDialog('1');"><i class="icon-plus"></i> 대단원 등록</button>
			<div class="btn-group">
			</div>
		</div>
		
		<div id="sort-table" style="width:900px;">
			
			<% 
				if( units.size() > 0 ){ 
					for(Unit fUnit : units ){
			%>
			<div data-unit-id="<%=fUnit.getUnitId()%>" data-sequence="<%=fUnit.getSequence()%>" data-depth="1">
				<div style="margin:5px;width:100%;background-color:#ccffff;padding:5px 15px;">
					<div style="float:left;font-weight:bold;font-size:1.2em;'"><%=fUnit.getName() %></div>
					<div style="float:right;">
						<button class="btn" onclick="showModifyUnitDialog('<%=fUnit.getUnitId()%>');"><i class="icon-plus"></i> 수정</button>
						<button class="btn btn-danger" onclick="deleteUnit('<%=fUnit.getUnitId()%>');"><i class="icon-plus"></i> 삭제</button>
						<button class="btn btn-primary" onclick="showUnitDialog('2' , '<%=fUnit.getUnitId() %>' , '<%=fUnit.getUnitId()%>');"><i class="icon-plus"></i> 중단원 등록</button>
					</div>
					<div style="clear:both;"></div>
				</div>
				<ul class="second" style="margin-left:50px;width:98%;" id="parent-<%=fUnit.getUnitId()%>">
					<% 
						if( fUnit.getChildren() != null 
								&& fUnit.getChildren().size() > 0 ) {
							for(Unit sUnit : fUnit.getChildren()){
					%>
					<li data-parent-id="<%=sUnit.getParentId() %>" data-unit-id="<%=sUnit.getUnitId() %>" data-sequence="<%=sUnit.getSequence() %>" data-depth="2">
						<div style="background-color:#ccffcc;padding:5px 10px;margin-bottom:3px;">
							<div style="float:left;font-size:1.2em;'"><%=sUnit.getName() %></div> 
							<div style="float:right;">
								<button class="btn" onclick="showModifyUnitDialog('<%=sUnit.getUnitId()%>');">수정</button>
								<button class="btn btn-danger" onclick="deleteUnit('<%=sUnit.getUnitId()%>');">삭제</button>
							</div>
							<div style="clear:both;"></div>
						</div>
					</li>
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
		</div>
	</div>
	<% } %>
	
</div>

<iframe name="actionFrame" width="0" height="0" frameborder="0"></iframe>

<form name="listForm" action="bookList.do" method="get">
<input type="hidden" name="pg" value="<%=pg%>"/>
</form>

<div id="unitDialog" style="display:none;">
	<form name="unitForm" action="" method="post" onsubmit="saveUnit();return false;">
	<input type="hidden" name="bookId" value="<%=bookId%>"/>
	<input type="hidden" name="unitId"/>
	<input type="hidden" name="parentId"/>
	<input type="hidden" name="depth"/>	
	<input type="hidden" name="mode"/>
    <div class="modal-header">
        <button type="button" class="close unblock">×</button>
        <h3>단원 관리</h3>
    </div>
    <div class="modal-body" style="text-align:left;">
		<label>단원명</label>
       	<input type="text" name="name" class="input-xlarge">
    </div>
    <div class="modal-footer">
    	<div style="float:left;display:none;" class="ajax-loader"><img src="/assets/admin/images/ajax-loader.gif"/></div>
    	<div style="float:right;">
    	<a href="javascript:saveUnit();" class="btn btn-danger"></i>저장</a>
        <a class="btn unblock">취소</a>
        </div>
    </div>
    </form>
</div>

<script type="text/javascript">
<!--

$(document).ready(function(){
	sortable();
	
	initCategory('<%=book.getCategory()%>');
});

<% if( member.getAuthType().equals("S") && bookId > 0 ){ %>
function deleteBook()
{
	if( $('[data-unit-id]').length > 0 )
		return alert('하위 단원이 포함되어있으므로 삭제할 수 없습니다.');
	else
	{
		if( confirm('정말로 삭제하시겠습니까?') )
		{
			$.ajax({
				url : 'deleteLearningBook.do' ,
				type : 'post' ,
				data : 'bookId=<%=book.getBookId()%>',
				dataType : 'json',
				error : function(){
					alert('서버와의 통신도중 에러가 발생되었습니다.');
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
}
<% } %>

function openLegacyBookSearch()
{
	window.open('legacyBookSearchPopup.do' , 'legacy' , 'width=850,height=600,scrollbars=no');
}

function setBookInfo(legacyId , name , author , previewlink)
{
	var f = document.forms.wform;
	
	f.legacyId.value = legacyId;
	f.name.value = name;
	f.author.value = author;
	f.previewUrl.value = 'http://file.kumsung.co.kr' + previewlink;
	
	$('#legacyName').text('기간계 도서명 : ' + name);
}

function sortable()
{	
	$('#sort-table').sortable({
		update : function(event , ui){
			
			// 대단원의 sequence 재정리
			var sequences = [];
			
			$('#sort-table').find('[data-depth=1]').each(function(){
				var unitId = $(this).attr('data-unit-id');
				var sequence = $(this).attr('data-sequence');
				sequences.push(unitId + ',' + sequence);
			});
			
			updateUnitSequence( 1 , sequences );
		}
	});
	
	$('#sort-table .second').sortable({
		update : function(event , ui){
			
			var parentId = ui.item.attr('data-parent-id');
			
			// 대단원의 sequence 재정리
			var sequences = [];
			 
			$('#sort-table').find('[data-parent-id=' + parentId + ']').each(function(){
				var unitId = $(this).attr('data-unit-id');
				var sequence = $(this).attr('data-sequence');
				sequences.push(unitId + ',' + sequence);
			});
			
			updateUnitSequence( 2 , sequences );
		}
	});
	
	$('#sort-table .third').sortable({
		update : function(event , ui){
			
			var parentId = ui.item.attr('data-parent-id');
			
			// 대단원의 sequence 재정리
			var sequences = [];
			 
			$('#sort-table').find('[data-parent-id=' + parentId + ']').each(function(){
				var unitId = $(this).attr('data-unit-id');
				var sequence = $(this).attr('data-sequence');
				sequences.push(unitId + ',' + sequence);
			});
			
			updateUnitSequence( 2 , sequences );
		}
	});
	
}

function updateUnitSequence(depth , sequences)
{
	$.ajax({
		url : 'updateUnitSequence.do',
		type : 'post',
		data : 'depth=' + depth + '&sequences=' + sequences.join('^'),
		dataType : 'json',
		beforeSend : function(){
			$.blockUI({
				message : '<img src="/assets/admin/images/ajax-loader.gif" style="margin:10px;"/>'
			});
		},
		error : function(){
			alert('서버와의 통신도중 에러가 발생되었습니다.');
		},
		success : function(json){
			if( json.result == 'FAILURE' )					
				alert(json.msg);
		},
		complete : function(){
			location.reload();
		}
	});
}

// 단원을 삭제한다.
function deleteUnit(unitId)
{
	if(confirm('삭제된 데이타는 복구할 수 없습니다.\r\n진행하시겠습니까?'))
	{
		var f = document.forms.unitForm;
		
		f.reset();
		f.bookId.value = '<%=bookId%>';
		f.name.value = 'delete';
		f.unitId.value = unitId;
		f.parentId.value = 1;
		f.depth.value = 1;
		f.mode.value = 'delete';
		
		$.ajax({
			url : 'unitFormAction.do' ,
			type : 'post' ,
			data : $('form[name=unitForm]').serialize() ,
			dataType : 'json' ,
			error : function(){
				alert('서버와의 통신중 에러가 발생하였습니다.');
			},
			success : function(json){
				if( json.result == 'SUCCESS' )
				{
					 alert('삭제를 완료하였습니다.');
					 location.reload();
				}
				else
					alert(json.msg);
			}
		});
	}
}

// unit form을 띄운다.
function showUnitDialog(depth , unitId , parentId)
{
	var f = document.forms.unitForm;
	
	f.reset();
	f.depth.value = depth;
	f.unitId.value = (unitId == undefined) ? 0 : unitId;
	f.parentId.value = (parentId == undefined) ? 0 : parentId;
	f.mode.value = ((depth == 1 && unitId == undefined) || (depth == 2 && parentId != undefined) || (depth == 3 && parentId != undefined)) ? 'add' : 'modify';
	
	var msg = '';
	
	if( depth == 1 )
		msg = '대단원 관리';
	else if( depth == 2 )
		msg = '중단원 관리';
	else if( depth == 3 )
		msg = '소단원 관리';
	
	$('#unitDialog').find('h3').text( msg );
	
	$.blockUI({
		message : $('#unitDialog') ,
		onBlock : function(){
			$('.unblock').click(function(){
				$.unblockUI();
			});
		}
	});
}

//modify unit form을 띄운다.
function showModifyUnitDialog(unitId)
{
	var f = document.forms.unitForm;
	
	$.ajax({
		url : 'getUnitData.do' ,
		type : 'post',
		data : 'unitId=' + unitId ,
		dataType : 'json' ,
		error : function(){
			alert('데이타를 받아오는 도중 에러가 발생되었습니다.');
		},
		success : function(json){
			if( json.result == 'SUCCESS' )
			{
				var msg = '';
				
				f.mode.value = 'modify';
				f.bookId.value = json.unit.bookId;
				f.unitId.value = json.unit.unitId;
				f.parentId.value = json.unit.parentId;
				f.name.value = json.unit.name;
				f.depth.value = json.unit.depth;				
				
				depth = json.unit.depth;
				
				if( depth == 1 )
					msg = '대단원 관리';
				else if( depth == 2 )
					msg = '중단원 관리';
				else if( depth == 3 )
					msg = '소단원 관리';
				
				$('#unitDialog').find('h3').text( msg );
				
				$.blockUI({
					message : $('#unitDialog') ,
					onBlock : function(){
						$('.unblock').click(function(){
							$.unblockUI();
						});
					}
				});
			}
			else
				alert(json.msg);
		}
	});
	
}

function saveUnit()
{
	var f = document.forms.unitForm;
	
	if( f.name.value == '' ) return doError('단원명을 입력해주십시오.' , 'name' , 'unitForm');
	
	// ajax call
	$.ajax({
		url : 'unitFormAction.do',
		type : 'post',
		data : $('form[name=unitForm]').serialize(),
		dataType : 'json',
		beforeSend : function(){
			$('.ajax-loader').css('display' , 'block');
		},
		error : function(){
			alert('서버와의 통신도중 에러가 발생되었습니다.');
		},
		success : function(json){
			if( json.result == 'SUCCESS' )
			{
				if( json.mode == 'add' )
					addUnitNode(json);
				else if( json.mode == 'modify' )
				{
					alert('수정을 완료하였습니다.');
					location.reload();
				}
				
				$.unblockUI();
			}
			else
				alert(json.msg);
		},
		complete : function(){
			$('.ajax-loader').css('display' , 'none');
			sortable();
		}
	});	
}

function addUnitNode(json)
{
	// add to sort-table
	if( json.depth == 1 )
	{
		$('#sort-table').append('<div data-unit-id="' + json.unitId + '" data-sequence="' + json.sequence + '" data-depth="1">\r\n' +
			'<div style="margin:5px;width:100%;background-color:#ccffff;padding:5px 15px;">\r\n' +
				'<div style="float:left;font-weight:bold;font-size:1.2em;">' + json.name + '</div>\r\n' +
				'<div style="float:right;">\r\n' +
					'<button class="btn" onclick="showModifyUnitDialog(\'' + json.unitId + '\');"><i class="icon-plus"></i> 수정</button>\r\n' +
					'<button class="btn btn-danger" onclick="deleteUnit(\'' + json.unitId + '\');"><i class="icon-plus"></i> 삭제</button>\r\n' +
					'<button class="btn btn-primary" onclick="showUnitDialog(\'2\' , \'' + json.unitId + '\' , \'' + json.unitId + '\');"><i class="icon-plus"></i> 중단원 등록</button>\r\n' +
				'</div>\r\n' +
				'<div style="clear:both;"></div>\r\n' +
			'</div>\r\n' +
			'<ul class="second" style="margin-left:50px;width:98%;" id="parent-' + json.unitId + '">\r\n' +
			'</ul>\r\n' +
		'</div>\r\n');
	}	
	else if( json.depth == 2 )
	{
		$('#parent-' + json.parentId).append('<li data-depth="2" data-parent-id="' + json.parent_id + '" data-unit-id="' + json.unit_id + '" data-sequence="' + json.sequence + '" data-depth="3">\r\n' + 
				'<div style="background-color:#ccffcc;padding:5px 10px;margin-bottom:3px;">\r\n' + 
				'<div style="float:left;font-size:1.2em;">' + json.name + '</div>\r\n' + 
				'<div style="float:right;">\r\n' + 
					'<button class="btn" onclick="showModifyUnitDialog(\'' + json.unitId + '\');">수정</button>\r\n' + 
					'<button class="btn btn-danger" onclick="deleteUnit(\'' + json.unitId + '\');">삭제</button>\r\n' + 
				'</div>\r\n' + 
				'<div style="clear:both;"></div>\r\n' + 
			'</div>\r\n' + 
		'</li>\r\n');
	}
}


// ajax call
function checkForm()
{
	var f = document.forms.wform;
	
	f.category.value = getCategoryCode();
	
	if( f.category.value == '' ) return doError('선택된 카테고리가 없습니다.' , 'category-1' , 'wform');
	if( f.name.value == '') return doError('도서명을 입력해주십시오.' , 'name' , 'wform');
	if( f.course.value == '') return doError('교육과정을 입력해주십시오.' , 'course' , 'wform');
	
	f.target = '_self';
	f.action = 'bookFormAction.do';
	f.submit();
}

function goList()
{
	var f = document.forms.listForm;
	
	f.target = '_self';
	f.submit();
}

//-->
</script>
