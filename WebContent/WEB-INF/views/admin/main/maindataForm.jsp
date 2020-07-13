<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
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
	String gubun = (request.getParameter("gubun") == null || request.getParameter("gubun").length() == 0  ) ? "null" : (String)(request.getParameter("gubun"));
	MainData data = (MainData) request.getAttribute("data");

	List<Category> categories = (List<Category>) request.getAttribute("categories");
	List<Category> types = (List<Category>) request.getAttribute("types");
	List<Book> books = (List<Book>) request.getAttribute("books");
	
%>
<div class="well">

	<div style="margin:10px;">
		<form name="wform" action="" method="post" enctype="multipart/form-data">
		<input type="hidden" name="dataId" value="<%=dataId%>"/>
		<input type="hidden" name="mode" value="<%=mode%>"/>
		<input type="hidden" name="category"/>
		<input type="hidden" name="unitId"/>
		<input type="hidden" name="dataType"/>
		<table class="table">
		<col width="100"/>
		<col width="300"/>
		<col width="*"/>
		<col width="*"/>
		<tbody>	
		<tr>
			<th>분류</th>
			<td colspan="3">
				<select name="category-2" class="category" style="width:100px;margin-right:10px;float:left;" onchange="showCategory(2);getLearningBooks();resetimg();">
					<option value="">::선택::</option>
					<% for(Category category : categories){ %>
					<option value="<%=category.getCategory()%>" data-depth="<%=category.getDepth() %>" data-children="<%=category.getChildren()%>"><%=category.getName() %></option>
					<% } %>
				</select>
				<select name="category-3" class="category" style="width:100px;margin-right:10px;float:left;display:none;" onchange="showCategory(3);getLearningBooks();resetimg();">
					<option value="">::선택::</option>
				</select>
				<select name="category-4" class="category" style="width:100px;margin-right:10px;float:left;display:none;" onchange="showCategory(4);getLearningBooks();resetimg();">
					<option value="">::선택::</option>
				</select>
			</td>
		</tr>
		<tr>
			<th>도서명</th>
			<td colspan="3">
				<select name="bookId" onchange="showLearningUnit('1');imgch();">
					<option value="">::도서선택::</option>
					<% for(Book book : books){ %>
					<option value="<%=book.getBookId()%>" <% if( book.getBookId() == data.getBookId()){ %>selected<% } %>><%=book.getName()%>(<%=book.getAuthor()%>)<%=book.getCourseName() %></option>
					<% } %>
				</select>
			</td>
		</tr>
		<tr>
			<th>썸네일 미리보기</th>
			<td colspan="3">
				<div id="thumbnailArea">
				<%if(mode.equals("add")){ %>
					<img name="bookimg" id="bookimg" src="/assets/front/img/no_image.gif" width=""/><br/>
				<%}else{ %>
					<img name="bookimg" id="bookimg" src="http://mall.kumsung.co.kr/prdimg/<%=data.getImgurl()%>" width="" style="width:100px;height:150px"/><br/>
				<%} %>
				</div>
			</td>
		</tr>
		<tr>
			<th>단원</th>
			<td colspan="3">
				<div class="unitWrapper" >
					<select name="unit-1" style="float:left;" onchange="showLearningUnit('2');">
						<option value="">::대단원::</option>
					</select>
					<div style="clear:both;"></div>
				</div>
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
				<select name="type-category-3" class="category" style="width:150px;margin-right:10px;float:left;display:none;" onchange="showCategory(3 , '' , 'type-');">
					<option value="">::선택::</option>
				</select>
				<select name="type-category-4" class="category" style="width:150px;margin-right:10px;float:left;display:none;">
					<option value="">::선택::</option>
				</select>
			</td>
		</tr>

		</tbody>
		</table>
		</form>
		
		<div class="btn-toolbar">
			<button class="btn btn-primary" onclick="checkForm();"><i class="icon-plus"></i> 저장</button>
			<%if( mode.equals("modify") ){ %><button class="btn btn-danger" onclick="deleteArticle();">삭제</button><%} %>
			<button class="btn" onclick="goList();">리스트</button>
			<div class="btn-group">
			</div>
		</div>
	</div>
	
</div>

<form name="listForm" action="mainList.do" method="get">
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
<!--

$(document).ready(function(){
	<% if( mode.equals("modify")){ %>
	initCategory('<%=data.getCategory()%>');
	initCategory('<%=data.getDataType()%>','type-');
	renderLearningUnits('<%=data.getBookId()%>' , '<%=data.getUnitIds()%>');
	<% } %>
	<% if( gubun.equals("insert")){%>
		alert("추가 되었습니다.");
	<% }else if(gubun.equals("update")){ %>
		alert("수정 되었습니다.");
	<%}%>

});

function deleteArticle()
{
	if( confirm('삭제된 자료는 복구할 수 없습니다.\r\n정말로 삭제하시겠습니까?') )
	{
		$.ajax({
			url : 'deleteMainData.do' ,
			type : 'post' ,
			data : 'dataId=<%=data.getDataId() %>' ,
			dataType : 'json' ,
			error : function(){
				alert('서버와 통신도중 에러가 발생하였습니다.');
			},
			success : function(json){
				if( json.result == 'SUCCESS' )
				{
					alert("삭제 되었습니다.");
					goList();
				}
				else
					alert(json.msg);
			}
		});
	}
}

function imgch(){
	var imgscr="";
	var bookId =$('select[name=bookId]').val();
	for(i=0;i<img.length;i++){
		imgscr=img[i].split("&");
		if(imgscr[1] == bookId){	
			document.getElementById("bookimg").src="http://mall.kumsung.co.kr/prdimg/"+imgscr[0];
			break;
		}
	}	
}
function resetimg(){
	document.getElementById("bookimg").src="/assets/front/img/no_image.gif";
}
function checkForm()
{
	var f = document.forms.wform;
	
	// get data
	f.category.value = getCategoryCode();
	f.unitId.value = getUnitId();
	f.dataType.value = getCategoryCode('type-');

	if( f.category.value == '' ) return alert('카테고리를 선택해주십시오.');
	if( f.bookId.value == '') return alert('도서를 선택해주십시오.');
	if( f.unitId.value == '' ) return alert('단원을 선택해주십시오.');
	if( f.dataType.value == '' ) return alert('자료 유형을 선택해주십시오.');	

	if($('select[name=unit-1]').val() == '' )
		return doError('단원을 선택하여주십시오.');
	

	f.target = '_self';
	f.action = 'maindataFormAction.do';
	f.submit();
}

function showDataType(obj)
{
	if( obj.value == 'D003' || obj.value == 'D004' || obj.value == 'D005' )
	{
		$('#dataAttachment').css('display' , 'none');
		$('#dataMultimedia').css('display' , 'block');
	}
	else
	{ 
		$('#dataAttachment').css('display' , 'block');
		$('#dataMultimedia').css('display' , 'none');
	}
}

function goList()
{
	var f = document.forms.listForm;
	
	f.target = '_self';
	f.submit();
}

//-->
</script>