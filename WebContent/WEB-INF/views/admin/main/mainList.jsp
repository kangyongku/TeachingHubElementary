<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	int pg = (Integer) request.getAttribute("pg");
	String paging = (String) request.getAttribute("paging");
	String mode = (String)request.getAttribute("mode");
	int articleNum = (Integer)request.getAttribute("articleNum");
	List<MainData> list = (List<MainData>) request.getAttribute("list");
	List<Category> categories = (List<Category>)request.getAttribute("categories");

	List<Category> types = (List<Category>) request.getAttribute("types");

	String findCategory = (String) request.getAttribute("findCategory");
	String findStr = (String) request.getAttribute("findStr");
	String findMethod = (String) request.getAttribute("findMethod");
	String findStartDate = (String) request.getAttribute("findStartDate");
	String findEndDate = (String) request.getAttribute("findEndDate");
%>
<form name="qform" action="" method="get">
	<input type="hidden" name="mode" value="add" />
	<input type="hidden" name="mmId" value="" />
	<input type="hidden" name="pg" value="<%=pg%>" />
	<input type="hidden" name="findCategory" value="<%=findCategory %>" />
</form>

<div class="btn-toolbar">
	<a href="javascript:goForm();" class="btn btn-primary"><i class="icon-plus"></i>교과서 등록</a>
</div>

<form name="lform" action="mainList.do" method="get">
	<input type="hidden" name="pg" value="<%=pg%>" />
	<input type="hidden" name="findCategory" value="<%=findCategory %>"/>

	<!-- 검색 테이블 -->
	<div>
		<table class="table table-bordered">
			<col width="60" />
			<col width="80" />
			<col width="120" />
			<col width="120" />
			<tbody>
				<tr>
					<th rowspan="3">검색</th>
					<th>분류</th>
					<th colspan="3">
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
					</th>
				</tr>
				<tr>
					<th>등록일</th>
					<th>
						<p>
						<input type="text" id="findStartDate" name="findStartDate"  value="<%=findStartDate %>" readonly style="width:70px"> ~
						<input type="text" id="findEndDate" name="findEndDate"  value="<%=findEndDate %>" readonly style="width:70px">
						</p>
					</th>
					
				</tr>
				<tr>
					<th>
						기본검색 
					</th>
					<th>
						<select name="findMethod" style="width:100px;margin-right:10px;">
							<option value="sa" <%if( findMethod.equals("sa") ){%>selected<%}%>>전체</option>
							<option value="st" <%if( findMethod.equals("st") ){%>selected<%}%>>제목</option>
							<option value="sc" <%if( findMethod.equals("sc") ){%>selected<%}%>>작성자</option>
						</select>
						<input type="text" name="findStr" value="<%=findStr%>" style="width:150px;"/>
					</th>
					<th>
						<a href="javascript:goList();" class="btn btn-primary" >검색</a>
					</th>
				</tr>
			</tbody>
		</table>
	</div>
</form>

<!-- 본문 시작 -->
<div class="well">
	<table class="table" >
		<col width="100"/>
		<col width="130"/>
		<col width="100"/>
		<col width="200"/>
		<col width="100"/>
		<col width="130"/>
		<thead>
		<tr>
			<th>번호</th>
			<th>분류</th>
			<th>목록이미지</th>
			<th>제목(교과서명)</th>
			<th>작성자</th>
			<th>등록일</th>
		</tr> 
		</thead>
		<tbody>
		<% if(articleNum > 0){  %>
			<%
				for(MainData data : list){
				// for reply articles
			%>
				<tr>
					<td><%=articleNum%></td>
					<td><%=data.getCategoryPath()%>><%=data.getDataTypeName() %></td>
					<td>
						<%
							if( data.getImgurl()!=null ){
						%>
							<img src="http://mall.kumsung.co.kr/prdimg/<%=data.getImgurl()%>" style="width:80px;height:100px"/>
						<%			
							}else{
						%>
							<img src="/assets/front/img/no_image.gif" width="150" height="115" style="width:80px;height:100px"/>
						<%
							}
						%>
					</td>
					<td><a href="javascript:goForm('<%=data.getDataId() %>')"><%=data.getBookName()%>(<%=data.getAuthor()%>)<%=data.getCourseName()%></a></td>
					<td><%=data.getUserId() %></td>
					<td><%=data.getRegDate()%></td>
				</tr>
			<%	
				articleNum--;
				}
			%>
		<%}else{ %>	
		<tr>
			<td colspan="7" style="height:80px;text-align:center;">
			[ 등록된 게시물이 없습니다. ]
			</td>
		</tr>
		<%} %>
		</tbody>
	</table>
</div>
<div class="pagination">
	<ul>
		<%=paging %>
	</ul>
</div>

<form name="detailForm" action="maindataForm.do" method="post">
	<input type="hidden" name="dataId" value="" />
	<input type="hidden" name="mode" value="add" />
	<input type="hidden" name="pg" value="<%=pg%>" />
	<input type="hidden" name="findCategory" value="<%=findCategory %>" />
	<input type="hidden" name="findStr" value="<%=findStr %>" />
	<input type="hidden" name="findMethod" value="<%=findMethod %>" />
	<input type="hidden" name="findStartDate" value="<%=findStartDate %>" />
	<input type="hidden" name="findEndDate" value="<%=findEndDate %>" />
</form>

<!-- 스크립트 영역 -->
<script>
<!--

function goForm(dataId)
{	
	var f = document.forms.detailForm;
	
	if( dataId == undefined )
		dataId = '0';
	
	f.dataId.value = dataId;
	f.submit();
}
//리스트 버튼
function goList(pg){
	$("form[name=lform]").find("input[name=pg]").val(pg);
	$("form[name=lform]").find("input[name=findCategory]").val(getCategoryCode());
	$("form[name=lform]").submit();
}

//달력
$("#findStartDate").datepicker({
	monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월' ],
	dayNamesMin: ['일','월','화','수','목','금','토'],
	dateFormat: 'yy-mm-dd'
});
$("#findEndDate").datepicker({
	monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월' ],
	dayNamesMin: ['일','월','화','수','목','금','토'],
	dateFormat: 'yy-mm-dd'
});

$(document).ready(function(){
	initCategory('<%=findCategory%>');	
})
-->
</script>