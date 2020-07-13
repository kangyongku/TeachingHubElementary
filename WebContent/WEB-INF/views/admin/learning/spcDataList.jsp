<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	int pg = (Integer) request.getAttribute("pg");
	String findCategory = (String) request.getAttribute("findCategory");
	String findMethod = (String) request.getAttribute("findMethod");
	String findStr = (String) request.getAttribute("findStr");
	String findStartDate = (String) request.getAttribute("findStartDate");
	String findEndDate = (String) request.getAttribute("findEndDate");

	List<Category> categories = (List<Category>) request.getAttribute("categories");
	List<Category> categories3 = (List<Category>) request.getAttribute("categories3");
	int totalData = (Integer) request.getAttribute("totalData");
	int articleNum = (Integer) request.getAttribute("articleNum");
	List<Data> list = (List<Data>) request.getAttribute("list");	
	String paging = (String) request.getAttribute("paging");

%>
<div class="btn-toolbar">
	<button class="btn btn-primary" onclick="goForm();"><i class="icon-plus"></i> 자료 등록</button>
	<div class="btn-group">
	</div>
</div>

<div>
	<table class="table">
	<col width="80"/>
	<col width="80"/>
	<col width="350"/>
	<col width="80"/>
	<col width="*"/>	
	<thead>
	<tr>
		<th style="border:1px solid #eee;vertical-align:middle;" rowspan="4">검색</th>
		<th style="border:1px solid #eee;">분류</th>
		<td style="border:1px solid #eee;" colspan="3">
			<select name="category-2" class="category" style="width:200px;margin-right:10px;float:left;" onchange="showSPCCategory(this);">
				<option value="">::선택::</option>
				<% for(Category category : categories){ %>
				<option value="<%=category.getCategory()%>" data-depth="<%=category.getDepth() %>" data-children="<%=category.getChildren()%>" ><%=category.getName() %></option>
				<% } %>
			</select>
			<div style="clear:both;"></div>
		</td> 
	</tr>
	<tr>
		<th style="border:1px solid #eee;">항목 선택</th>
		<td style="border:1px solid #eee;" colspan="3">
			<select name="category-3" class="category" style="width:200px;margin-right:10px;float:left;">
				<option value="">::선택::</option>
				<% for(Category category : categories3){ %>
				<option value="<%=category.getCategory()%>" data-depth="<%=category.getDepth() %>" data-children="<%=category.getChildren()%>" ><%=category.getName() %></option>
				<% } %>
			</select>
			<div style="clear:both;"></div>
		</td>
	</tr>

	
	<form name="sform" action="" method="get" onsubmit="search();return false;">
	<input type="hidden" name="findCategory" />
	
	<tr>
		<th style="border:1px solid #eee;">등록일</th>
		<td style="border:1px solid #eee;" colspan="3">
			<input type="text" name="findStartDate" class="datepicker" style="width:120px;" value="<%=findStartDate%>"/> ~ <input type="text" name="findEndDate" class="datepicker" style="width:120px;" value="<%=findEndDate%>"/>
		</td>
	</tr>
	<tr>
		<th style="border:1px solid #eee;">기본검색</th>
		<td style="border:1px solid #eee;" colspan="3">
			<select name="findMethod" style="width:80px;">
				<option value="ss" <% if( findMethod.equals("ss")){ %>selected<% } %>>제목</option>				
			</select>
			<input type="text" name="findStr" value="<%=findStr%>"/>
			<a href="javascript:search();" class="btn btn-primary">검색</a>
		</td>
	</tr>
	</thead>
	</form>
	</table>
</div>

<div class="well">
	<table class="table">
		<col width="50"/>
		<col width="110"/>
		<col width="110"/>
		<col width="*"/>
		<col width="70"/>
		<col width="110"/>	
	<thead>
	<tr>
		<th>No.</th>
		<th>분류</th>				
		<th>항목</th>
		<th>제목</th>		
		<th>작성자</th>
		<th>등록일</th>	
	</tr>
	</thead>
	<tbody>
	<% 
		if( totalData > 0 ){ 
			for(Data data : list){
	%>
	<tr>
		<td><%=articleNum %></td>
		<td><%=data.getcate2Name() %></td>		
		<td><%=data.getcate3Name() %></td>
		<td><a href="javascript:goForm('<%=data.getsDataId()%>');"><%=data.getTitle() %></a></td>		
		<td><%=data.getUserName() %></td>
		<td><%=data.getRegDate().substring(0 , 10) %></td>	
	</tr>
	<% 
				articleNum--;
			}
		}else{ 
	%>
	<tr>
		<th colspan="7" style="height:80px;text-align:center;">
			[ 자료가 없습니다. ]
		</th>
	</tr>
	<% } %>
	</tbody>
	</table>
</div>
<div class="pagination">
	<ul>
		<%=paging %>
	</ul>
</div>


<form name="qform" action="spcDataForm.do" method="get">
<input type="hidden" name="sdataId"/>
<input type="hidden" name="pg" value="<%=pg%>"/>
<input type="hidden" name="findCategory" value="<%=findCategory%>"/>
<input type="hidden" name="findStartDate" value="<%=findStartDate%>"/>
<input type="hidden" name="findEndDate" value="<%=findEndDate%>"/>
<input type="hidden" name="findMethod" value="<%=findMethod%>"/>
<input type="hidden" name="findStr" value="<%=findStr%>"/>
</form>

<form name="listForm" action="" method="get">
<input type="hidden" name="pg"/>
<input type="hidden" name="findCategory" value="<%=findCategory%>"/>
<input type="hidden" name="findStartDate" value="<%=findStartDate%>"/>
<input type="hidden" name="findEndDate" value="<%=findEndDate%>"/>
<input type="hidden" name="findMethod" value="<%=findMethod%>"/>
<input type="hidden" name="findStr" value="<%=findStr%>"/>
</form>

<script type="text/javascript">
<!--

$(document).ready(function(){
	
	$('.datepicker').datepicker({
		monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		dayNames: ['일','월','화','수','목','금','토'],
		dayNamesShort: ['일','월','화','수','목','금','토'],
		dayNamesMin: ['일','월','화','수','목','금','토'],
		weekHeader: 'Wk' ,
		dateFormat: 'yy-mm-dd'
	});

	initCategory('<%=findCategory%>');
});

function search()
{
	var f = document.forms.sform;
	
	// get data
	f.findCategory.value = $('select[name=category-3] option:selected').val();
	if(f.findCategory.value=='') f.findCategory.value = $('select[name=category-2] option:selected').val();

	f.target = '_self';
	f.submit();
}

function goList(pg)
{
	var f = document.forms.listForm;
	
	f.pg.value = pg;
	f.submit();
}

function goForm(dataId)
{	
	var f = document.forms.qform;
	
	if( dataId == undefined )
		dataId = '0';
	
	f.sdataId.value = dataId;
	f.submit();
}
function showSPCCategory(obj){
	if(obj.value==''){
		$('select[name=category-3]').empty();
		$('select[name=category-3]').append('<option value="">::선택::</option>');
		<% for(Category category : categories3){ %>
			$('select[name=category-3]').append('<option value="<%=category.getCategory()%>"><%=category.getName() %></option>');
		<% } %>
	}else{
		showCategory(2);
	}
	
}

//-->
</script>