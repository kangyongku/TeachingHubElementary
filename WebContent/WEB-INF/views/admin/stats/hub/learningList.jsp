<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	String findCategory = (String) request.getAttribute("findCategory");
	String findStartDate = (String) request.getAttribute("findStartDate");
	String findEndDate = (String) request.getAttribute("findEndDate");
	String findBookId = (String) request.getAttribute("findBookId");
	String findType = (String) request.getAttribute("findType");
	
	// bookid는 숫자형으로 변환해야 한다.
	if( Validate.isEmpty(findBookId) )
		findBookId = "0";
	
	List<Category> categories = (List<Category>) request.getAttribute("categories");
	List<Book> books = (List<Book>) request.getAttribute("books");
	List<Category> types = (List<Category>) request.getAttribute("types");
	
	int totalCount = (Integer) request.getAttribute("totalCount");
	int currentTotalCount = (Integer) request.getAttribute("currentTotalCount");
	List<ResultMap> list = (List<ResultMap>) request.getAttribute("list");
%>
<div class="btn-toolbar">
	<a class="btn btn-danger" href="#">교수학습자료</a>
	<a class="btn btn-primary" href="articleList.do">통합게시판</button>
	<a class="btn btn-primary" href="scrapList.do">스크랩 순위</a>
</div>

<div>
	<form name="sform" action="" method="get" onsubmit="goSearch();return false;">
	<table class="table">
	<col width="80"/>
	<col width="80"/>
	<col width="350"/>
	<col width="80"/>
	<col width="*"/>	
	<thead>
	<tr>
		<th style="border:1px solid #eee;vertical-align:middle;" rowspan="2">검색</th>
		<th style="border:1px solid #eee;">기간</th>
		<td style="border:1px solid #eee;" colspan="3">
			<a class="btn" href="javascript:;" onclick="setFindDate('today');">오늘</a>
			<a class="btn" href="javascript:;" onclick="setFindDate('week');">1주일</a>
			<a class="btn" href="javascript:;" onclick="setFindDate('month');">1개월</a>
			<input type="text" name="findStartDate" class="datepicker" value="<%=findStartDate %>" style="width:100px;"/>~<input type="text" name="findEndDate" class="datepicker" value="<%=findEndDate %>" style="width:100px;"/>
			<a class="btn btn-primary" onclick="goSearch();">검색</a>
		</td>
	</tr>
	<tr>
		<th style="border:1px solid #eee;">조건</th>
		<td style="border:1px solid #eee;" colspan="3">
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
			<select name="bookId" style="width:100px;margin-right:10px;float:left;">
				<option value="">::도서선택::</option>
				<% for(Book book : books) { %>
				<option value="<%=book.getBookId()%>" <% if( book.getBookId() == new Integer(findBookId)){ %>selected<% } %>><%=book.getName() %></option>
				<% } %>
			</select>
			<select name="findType" style="width:100px;margin-right:10px;float:left;">
				<option value="">::선택::</option>
				<% for(Category category : types) { %>
				<option value="<%=category.getCategory()%>" <% if( category.getCategory().equals(findType)){ %>selected<% } %>><%=category.getName() %></option>
				<% } %>
			</select>
			<a class="btn btn-primary" onclick="goSearch();">검색</a>
			<div style="clear:both;"></div>
		</td>
	</tr>
	</thead>
	</table>
	</form>
</div>

<div>
<div style="float:left;font-weight:bold;font-size:11pt;">
	누적 : <%=new DecimalFormat("###,###").format(totalCount) %>
</div>
<div style="float:right;font-weight:bold;font-size:11pt;">
	검색결과 : <%=new DecimalFormat("###,###").format(currentTotalCount) %>
</div>
<div style="clear:both;"></div>
</div>

<div class="well">
	<table class="table">
	<col width="250"/>
	<col width="*"/>
	<col width="120"/>
	<thead>
	<tr>
		<th>구분</th>
		<th colspan="2">자료수</th>
	</tr>
	</thead>
	<tbody>
	<% for(ResultMap item : list){ %>
	<tr>
		<td><%=item.getString("name") %></td>
		<td>
			<div style="background-color:#d9d9d9;border:1px solid #ddd;width:<%=Common.getPercentage(item.getInt("cnt"), currentTotalCount)%>%;height:30px;"></div>
		</td>
		<td><%=new DecimalFormat("###,###").format(item.getInt("cnt")) %> (<%=Common.getPercentage(item.getInt("cnt"), currentTotalCount) %>%)</td>
	</tr>
	<% } %>
	</tbody>
	</table>
</div>

<form name="searchForm" action="" method="get">
<input type="hidden" name="findType"/>
<input type="hidden" name="findBookId"/>
<input type="hidden" name="findCategory"/>
<input type="hidden" name="findStartDate"/>
<input type="hidden" name="findEndDate"/>
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
	
	<% if( Validate.isEmpty(findStartDate) || Validate.isEmpty(findEndDate) ) { %>
	setFindDate('week');
	<% } %>
	
});

function goSearch()
{
	var f = document.forms.searchForm;
	var f2 = document.forms.sform;
	
	f.findCategory.value = getCategoryCode();
	f.findBookId.value = f2.bookId.value;
	f.findStartDate.value = f2.findStartDate.value;
	f.findEndDate.value = f2.findEndDate.value;
	f.findType.value = f2.findType.value;
	
	f.submit();
}

function setFindDate(term)
{
	var d = new Date();
	var n = new Date();
	
	if( term == 'today' )
	{	
		var findStartDate = d.getFullYear() + '-' + zeroPad((d.getMonth() + 1) , 10) + '-' + zeroPad(d.getDate() , 10);
		var findEndDate = d.getFullYear() + '-' + zeroPad((d.getMonth() + 1) , 10) + '-' + zeroPad(d.getDate() , 10);
	}
	else if( term == 'week')
	{
		n.setFullYear(d.getFullYear() , d.getMonth() , d.getDate() - 7);
		
		var findStartDate = n.getFullYear() + '-' + zeroPad((n.getMonth() + 1) , 10) + '-' + zeroPad(n.getDate() , 10);
		var findEndDate = d.getFullYear() + '-' + zeroPad((d.getMonth() + 1) , 10) + '-' + zeroPad(d.getDate() , 10);
	}
	else if( term == 'month' )
	{
		n.setFullYear(d.getFullYear() , d.getMonth(), d.getDate() - 30);
		
		var findStartDate = n.getFullYear() + '-' + zeroPad((n.getMonth() + 1) , 10) + '-' + zeroPad(n.getDate() , 10);
		var findEndDate = d.getFullYear() + '-' + zeroPad((d.getMonth() + 1) , 10) + '-' + zeroPad(d.getDate() , 10);
	}
	
	$('input[name=findStartDate]').val(findStartDate);
	$('input[name=findEndDate]').val(findEndDate);
}

//-->
</script>