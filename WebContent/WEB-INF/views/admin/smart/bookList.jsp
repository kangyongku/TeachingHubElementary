<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	int pg = (Integer) request.getAttribute("pg");
	String findCategory = (String) request.getAttribute("findCategory");	
	String findCourse = (String) request.getAttribute("findCourse");
	String findIsUse = (String) request.getAttribute("findIsUse");
	String findMethod = (String) request.getAttribute("findMethod");
	String findStr = (String) request.getAttribute("findStr");
	String findStartDate = (String) request.getAttribute("findStartDate");
	String findEndDate = (String) request.getAttribute("findEndDate");
	
	int articleNum = (Integer) request.getAttribute("articleNum");
	int totalBooks = (Integer) request.getAttribute("totalBooks");
	List<Book> list = (List<Book>) request.getAttribute("list");
	
	List<Category> categories = (List<Category>) request.getAttribute("categories");
	List<Category> courses = (List<Category>) request.getAttribute("courses");
	
	String paging = (String) request.getAttribute("paging");
%>
<div class="btn-toolbar">
	<button class="btn btn-primary" onclick="goForm();"><i class="icon-plus"></i> 도서 등록</button>
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
			<select name="category-2" class="category" style="width:100px;margin-right:10px;float:left;" onchange="showCategory(2);">
				<option value="">::선택::</option>
				<% for(Category category : categories){ %>
				<option value="<%=category.getCategory()%>" data-depth="<%=category.getDepth() %>" data-children="<%=category.getChildren()%>"><%=category.getName() %></option>
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
	<form name="sform" action="" method="get" onsubmit="search();return false;">
	<input type="hidden" name="findCategory" value=""/>
	<tr>
		<th style="border:1px solid #eee;">교육과정</th>
		<td style="border:1px solid #eee;" colspan="3">
			<select name="findCourse" style="width:150px;">
				<option value="">::선택::</option>
				<% for(Category course : courses){ %>
				<option value="<%=course.getCategory()%>" <% if( course.getCategory().equals(findCourse) ){ %>selected<% } %>><%=course.getName()%></option>
				<% } %>
			</select>
		</td>
	</tr>
	<tr>
		<th style="border:1px solid #eee;">등록일</th>
		<td style="border:1px solid #eee;">
			<input type="text" name="findStartDate" class="datepicker" style="width:120px;" value="<%=findStartDate%>"/> ~ <input type="text" name="findEndDate" class="datepicker" style="width:120px;" value="<%=findEndDate%>"/>
		</td>	
		<th style="border:1px solid #eee;">노출유무</th>
		<td style="border:1px solid #eee;">
			<select name="findIsUse">
				<option value="">전체</option>
				<option value="Y">사용</option>
				<option value="N">미사용</option>
			</select>
		</td>	
	</tr>
	<tr>
		<th style="border:1px solid #eee;">기본검색</th>
		<td style="border:1px solid #eee;" colspan="3">
			<select name="findMethod" style="width:80px;">
				<option value="st">도서명</option>
				<option value="su">작성자</option>
			</select>
			<input type="text" name="findStr"/>
			<a href="javascript:search();" class="btn btn-primary">검색</a>
		</td>
	</tr>
	</thead>
	</form>
	</table>
</div>

<div class="well">
	
	<table class="table">
	<col width="60"/>
	<col width="200"/>
	<col width="*"/>	
	<col width="100"/>
	<col width="80"/>
	<col width="80"/>
	<col width="100"/>	
	<thead>
	<tr>
		<th>No.</th>
		<th>카테고리</th>		
		<th>도서명</th>
		<th>노출유무</th>
		<th>교육과정</th>	
		<th>작성자</th>	
		<th>등록일</th>
	</tr>
	</thead>
	<tbody>
	<% 
		if(totalBooks > 0){
			
			for(Book book : list){	
	%>
	<tr>
		<td><%=articleNum %></td>
		<td><%=book.getCategoryPath() %></td>
		<td><a href="javascript:goForm('<%=book.getBookId()%>');"><%=book.getName() %></a></td>
		<td><%=book.getIsUseName() %></td>
		<td><%=book.getCourseName() %></td>
		<td><%=book.getUserName() %></td>
		<td><%=book.getRegDate().substring(0,10) %></td>		
	</tr>
	<%
				articleNum--;
			}
		}else{
	%>
	<tr>
		<td colspan="7" style="height:80px;text-align:center;">
		[ 등록된 게시물이 없습니다. ]
		</td>
	</tr>
	<%
		}
	%>
	</tbody>
	</table>
</div>
<div class="pagination">
	<ul>
		<%=paging %>
	</ul>
</div>

<form name="qform" action="bookForm.do" method="get">
<input type="hidden" name="bookId"/>
<input type="hidden" name="pg" value="<%=pg%>"/>
<input type="hidden" name="findCategory" value="<%=findCategory%>"/>
<input type="hidden" name="findCourse" value="<%=findCourse%>"/>
<input type="hidden" name="findStartDate" value="<%=findStartDate%>"/>
<input type="hidden" name="findEndDate" value="<%=findEndDate%>"/>
<input type="hidden" name="findIsUse" value="<%=findIsUse%>"/>
<input type="hidden" name="findMethod" value="<%=findMethod%>"/>
<input type="hidden" name="findStr" value="<%=findStr%>"/>
</form>

<form name="listForm" action="" method="get">
<input type="hidden" name="pg"/>
<input type="hidden" name="findCategory" value="<%=findCategory%>"/>
<input type="hidden" name="findCourse" value="<%=findCourse%>"/>
<input type="hidden" name="findStartDate" value="<%=findStartDate%>"/>
<input type="hidden" name="findEndDate" value="<%=findEndDate%>"/>
<input type="hidden" name="findIsUse" value="<%=findIsUse%>"/>
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

function goList(pg)
{
	var f = document.forms.listForm;
	
	f.pg.value = pg;
	f.submit();
}

function goForm(bookId)
{	
	var f = document.forms.qform;
	
	if( bookId == undefined )
		bookId = '0';
	
	f.bookId.value = bookId;
	f.submit();
}

function search()
{
	var f = document.forms.sform;
	
	f.findCategory.value = getCategoryCode();
	
	f.target = '_self';
	f.submit();
}

//-->
</script>