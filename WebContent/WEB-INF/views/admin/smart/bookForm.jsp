<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	int pg = (Integer) request.getAttribute("pg");
	int bookId = (Integer) request.getAttribute("bookId");
	String findCategory = (String) request.getAttribute("findCategory");
	String findCourse = (String) request.getAttribute("findCourse");
	String findIsUse = (String) request.getAttribute("findIsUse");
	String findMethod = (String) request.getAttribute("findMethod");
	String findStr = (String) request.getAttribute("findStr");
	String findStartDate = (String) request.getAttribute("findStartDate");
	String findEndDate = (String) request.getAttribute("findEndDate");
	String mode = (String) request.getAttribute("mode");
	
	Book book = (Book) request.getAttribute("book");
	List<Category> categories = (List<Category>) request.getAttribute("categories");
	List<Category> courses = (List<Category>) request.getAttribute("courses");
	List<Unit> units = (List<Unit>) request.getAttribute("units");
	
	Member member = (Member) session.getAttribute("adminMember");
%>
<div class="well">

	<div style="margin:10px;">
	
		<form name="wform" action="" method="post">
		<input type="hidden" name="bookId" value="<%=bookId%>"/>
		<input type="hidden" name="mode" value="<%=mode%>"/>
		<input type="hidden" name="category"/>
		<table class="table">
		<col width="150"/>
		<col width="*"/>
		<col width="150"/>
		<col width="*"/>
		<tbody>	
		<tr>
			<th>사용구분</th>
			<td colspan="3">
				<input type="radio" name="isUse" value="Y" <% if( book.getIsUse() == null || book.getIsUse().equals("Y")){ %>checked<% } %>/> 사용
				<input type="radio" name="isUse" value="N" <% if( book.getIsUse().equals("N")){ %>checked<% } %>/> 미사용
			</td>
		</tr>
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
			<th>정렬순서</th>
			<td colspan="3">
				<input type="text" name="sequence" value="<%=book.getSequence()%>"/>
			</td>
		</tr>
		</tbody>
		</tbody>
		</table>
		</form>
		
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
					<div style="float:left;font-weight:bold;font-size:1.2em;'"><%=fUnit.getName() %> ( ID : <%=fUnit.getUnitId() %>)</div>
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
					<li data-parent-id="<%=sUnit.getParentId()%>" data-unit-id="<%=sUnit.getUnitId()%>" data-sequence="<%=sUnit.getSequence()%>"  data-depth="2">
						<div style="background-color:#ffffcc;padding:5px 10px;margin-bottom:3px;">
							<div style="float:left;font-size:1.2em;'"><%=sUnit.getName() %> ( ID : <%=sUnit.getUnitId() %>)</div>
							<div style="float:right;">
								<button class="btn" onclick="showModifyUnitDialog('<%=sUnit.getUnitId()%>');">수정</button>
								<button class="btn btn-danger" onclick="deleteUnit('<%=sUnit.getUnitId()%>');">삭제</button>
								<button class="btn btn-primary" onclick="showUnitDialog('3' , '' , '<%=sUnit.getUnitId()%>');">소단원 등록</button>
							</div>
							<div style="clear:both;"></div>
						</div>
						<ul class="third" style="margin-left:70px;width:92%;" id="parent-<%=sUnit.getUnitId()%>">
							<%
								if( sUnit.getChildren() != null 
										&& sUnit.getChildren().size() > 0 ){
									for(Unit tUnit : sUnit.getChildren()){
							%>
							<li data-parent-id="<%=tUnit.getParentId() %>" data-unit-id="<%=tUnit.getUnitId() %>" data-sequence="<%=tUnit.getSequence() %>" data-depth="3">
								<div style="background-color:#ccffcc;padding:5px 10px;margin-bottom:3px;">
									<div style="float:left;font-size:1.2em;'"><%=tUnit.getName() %> ( ID : <%=tUnit.getUnitId() %>)</div> 
									<div style="float:right;">
										<button class="btn" onclick="showModifyUnitDialog('<%=tUnit.getUnitId()%>');">수정</button>
										<button class="btn btn-danger" onclick="deleteUnit('<%=tUnit.getUnitId()%>');">삭제</button>
									</div>
									<div style="clear:both;"></div>
								</div>
							</li>
							<%
									}
								}
							%>
						</ul>
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
<input type="hidden" name="findCategory" value="<%=findCategory%>"/>
<input type="hidden" name="findCourse" value="<%=findCourse%>"/>
<input type="hidden" name="findStartDate" value="<%=findStartDate%>"/>
<input type="hidden" name="findEndDate" value="<%=findEndDate%>"/>
<input type="hidden" name="findIsUse" value="<%=findIsUse%>"/>
<input type="hidden" name="findMethod" value="<%=findMethod%>"/>
<input type="hidden" name="findStr" value="<%=findStr%>"/>
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
				url : 'deleteSmartBook.do' ,
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
		$('#parent-' + json.parentId).append('<li data-parent-id="' + json.parent_id + '" data-unit-id="' + json.unit_id + '" data-sequence="' + json.sequence + '" data-depth="2">\r\n' +
				'<div style="background-color:#ffffcc;padding:5px 10px;margin-bottom:3px;">' +
				'<div style="float:left;font-weight:bold;font-size:1.2em;">' + json.name + '</div>\r\n' +
				'<div style="float:right;">\r\n' +
					'<button class="btn" onclick="showModifyUnitDialog(\'' + json.unitId + '\');">수정</button>\r\n' +
					'<button class="btn btn-danger" onclick="deleteUnit(\'' + json.unitId + '\');">삭제</button>\r\n' +
					'<button class="btn btn-primary" onclick="showUnitDialog(\'3\' , \'' + json.unitId + '\' , \'' + json.unitId + '\');">소단원 등록</button>\r\n' +
				'</div>\r\n' +
				'<div style="clear:both;"></div>\r\n' +
				'</div>' +
				'<ul class="third" style="margin-left:70px;width:92%;" id="parent-' + json.unitId + '">' +
				'</ul>' +
			'</li>\r\n');
	}
	else if( json.depth == 3 )
	{
		$('#parent-' + json.parentId).append('<li data-depth="3" data-parent-id="' + json.parent_id + '" data-unit-id="' + json.unit_id + '" data-sequence="' + json.sequence + '" data-depth="3">\r\n' + 
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

function checkForm()
{	
	var f = document.forms.wform;
	
	f.category.value = getCategoryCode();
	
	if( f.category.value == '' ) return doError('선택된 카테고리가 없습니다.' , 'category-1' , 'wform');
	if( f.name.value == '') return doError('도서명을 입력해주십시오.' , 'name' , 'wform');

	<% if( member.getAuthType().equals("N")){ %>
	// 권한검사
	if( authCheck() == false ) return doError('허가 받지 않은 분류입니다.' , 'category-1' , 'wform');
	<% } %>
	
	f.target = '_self';
	f.action = 'bookFormAction.do';
	f.submit();
}

function authCheck()
{
	var smartAuth = "<%=member.getSmartAuth()%>";
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
	var f = document.forms.listForm;
	
	f.target = '_self';
	f.submit();
}

//-->
</script>