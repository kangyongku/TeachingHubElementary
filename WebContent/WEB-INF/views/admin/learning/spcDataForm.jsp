<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	int pg = (Integer) request.getAttribute("pg");
	int sdataId = (Integer) request.getAttribute("sdataId");
	String findCategory = (String) request.getAttribute("findCategory");
	String findStartDate = (String) request.getAttribute("findStartDate");
	String findEndDate = (String) request.getAttribute("findEndDate");
	String findMethod = (String) request.getAttribute("findMethod");
	String findStr = (String) request.getAttribute("findStr");
	String mode = (String) request.getAttribute("mode");
	
	Data data = (Data) request.getAttribute("data");
	List<Category> categories = (List<Category>) request.getAttribute("categories");
	List<Category> categories3 = (List<Category>) request.getAttribute("categories3");
	//String category = data.getCategory();

%>
<div class="well">

	<div style="margin:10px;">
		<form name="wform" action="" method="post" enctype="multipart/form-data">
		<input type="hidden" name="sdataId" value="<%=sdataId%>"/>
		<input type="hidden" name="mode" value="<%=mode%>"/>
		<input type="hidden" name="category"/>
		
		<table class="table">
		<col width="150"/>
		<col width="120"/>
		<col width="*"/>
		<col width="*"/>
		<tbody>	
		<tr>
			<th>분류</th>
			<td colspan="3">
				<select name="category-2" class="category" style="width:200px;margin-right:10px;float:left;" onchange="showCategory(2);">
					<option value="">::선택::</option>
					<% for(Category category : categories){ %>
					<option value="<%=category.getCategory()%>" data-depth="<%=category.getDepth() %>" data-children="<%=category.getChildren()%>"><%=category.getName() %></option>
					<% } %>
				</select>
			</td>
		</tr>
		<tr>
			<th>항목</th>
			<td colspan="3">
				<select name="category-3" class="category" style="width:200px;margin-right:10px;float:left;display:none;">
					<option value="">::선택::</option>
					<% for(Category category : categories3){ %>
					<option value="<%=category.getCategory()%>" data-depth="<%=category.getDepth() %>" data-children="<%=category.getChildren()%>" ><%=category.getName() %></option>
					<% } %>
				</select>
			</td>
		</tr>
		<tr>
			<th>제목</th>
			<td colspan="3">
				<input type="text" name="title" value="<%=data.getTitle()%>"/> sdataID=<%=sdataId%>
			</td>
		</tr>

		<tr>
			<th rowspan="5">자료</th>
			<th id="dataFlagTitle">관련자료</th>
			<td colspan="2">
				<div id="dataAttachment" >
					<input type="file" name="upfile"/>
					<div id="attachment-file-data" style="padding:3px;font-weight:bold;">
						<a href="<%=data.getDataFile() %>" target="_blank"><%=data.getFileName() %></a>
					</div>
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

<form name="listForm" action="spcDataList.do" method="get">
<input type="hidden" name="pg" value="<%=pg%>"/>
<input type="hidden" name="findCategory" value="<%=findCategory%>"/>
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
	<% } %>
});

function deleteArticle()
{
	if( confirm('삭제된 자료는 복구할 수 없습니다.\r\n정말로 삭제하시겠습니까?') )
	{
		$.ajax({
			url : 'deletesDataArticle.do' ,
			type : 'post' ,
			data : 'sdataId=<%=data.getsDataId() %>' ,
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

function checkForm()
{
	var f = document.forms.wform;
	
	// get data
	f.category.value = getCategoryCode();
	
	if( (f.category.value).length < 7 ) return alert('분류/항목을 선택해주십시오.');
	if( f.title.value == '' ) return doError('제목을 입력해주십시오.' , 'title' , 'wform');
	
	//alert(f.category.value);
	f.target = '_self';
	f.action = 'sDataFormAction.do';
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