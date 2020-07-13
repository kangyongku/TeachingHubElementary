<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	int pg = (Integer) request.getAttribute("pg");
	int customerNum = (Integer) request.getAttribute("customerNum");
	String paging = (String) request.getAttribute("paging");

	List<Category> masterCategories = (List<Category>) request.getAttribute("masterCategories");
	List<Category> categories = (List<Category>) request.getAttribute("categories");
	List<Customer> list = (List<Customer>) request.getAttribute("list");
	
	String findStr = (String) request.getAttribute("findStr");
	String findMethod = (String) request.getAttribute("findMethod");
	String findCategory = (String) request.getAttribute("findCategory");
	String findBoardCategory = (String) request.getAttribute("findBoardCategory");
	String findAnswerStatus = (String) request.getAttribute("findAnswerStatus");
%>
<form name="qform" method="post">
	<input type="hidden" name="customerId" />
	<input type="hidden" name="pg" value="<%=pg %>"/>
	<input type="hidden" name="findStr" value="<%=findStr %>" />
	<input type="hidden" name="findMethod" value="<%=findMethod %>" />
	<input type="hidden" name="findCategory" value="<%=findCategory %>" />
	<input type="hidden" name="findBoardCategory" value="<%=findBoardCategory %>" />
	<input type="hidden" name="findAnswerStatus" value="<%=findAnswerStatus %>" />
</form>

<form name="lform" method="post">
<input type="hidden" name="pg" value="<%=pg %>"/>
<input type="hidden" name="findCategory" value="<%=findCategory %>" />
<input type="hidden" name="findBoardCategory" value="<%=findBoardCategory %>" />
<input type="hidden" name="findAnswerStatus" value="<%=findAnswerStatus %>" />
	<div>
		<table class="table table-bordered" >
			<col width="80" />
			<col width="80" />
			<col width="180" />
			<col width="80" />
			<col width="220" />
			<tbody>
				<tr>
					<th rowspan="2">검색</th>
					<th>분류</th>
					<td>
						<select name="board-category-2" id="bcategory" class="category" style="width:100px;margin-right:2px;float:left;" onchange="showCategory(2,'','board-');">
							<option value="">:: 전체 ::</option>
							<% for(Category category : masterCategories){ %>
							<option value="<%=category.getCategory()%>" data-depth="<%=category.getDepth() %>" data-children="<%=category.getChildren()%>" <%if ( findBoardCategory.equals(category.getCategory()) ){%>selected<%}%>><%=category.getName() %></option>
							<% } %>
						</select>
						<select name="board-category-3" class="category" style="width:120px;margin-right:10px;float:left;display:none;" onchange="showCategory(3);">
							<option value="">::선택::</option>
						</select>
					</td>
					<th>학교급/교과</th>
					<td>
						<select name="category-2" class="category" style="width:100px;margin-right:10px;float:left;" onchange="showCategory(2);">
							<option value="">:: 전체 ::</option>
							<% for(Category category : categories){ %>
							<option value="<%=category.getCategory()%>" data-depth="<%=category.getDepth() %>" data-children="<%=category.getChildren()%>" ><%=category.getName() %></option>
							<% } %>
						</select>
						<select name="category-3" class="category" style="width:100px;margin-right:10px;float:left;display:none;" onchange="showCategory(3);">
							<option value="">::선택::</option>
						</select>
						<select name="category-4" class="category" style="width:100px;margin-right:10px;float:left;display:none;" onchange="showCategory(4);">
							<option value="">::선택::</option>
						</select> 
					
					</td>
				</tr>
				<tr>
					<th>답변여부</th>
					<td>
						<input type="checkbox" class="answerStatus" value="N" <%if ( findAnswerStatus.equals("A") || findAnswerStatus.equals("N") ){ %> checked <%} %>/>답변준비
						<input type="checkbox" class="answerStatus" value="Y" <%if ( findAnswerStatus.equals("A") || findAnswerStatus.equals("Y") ){ %> checked <%} %>/>답변완료						
					</td>
					
					<th>기본검색</th>
					<td>
						<select name="findMethod" style="width:100px;" >
							<option value="">:: 전체 ::</option>
							<option value="ft" <% if ( findMethod.equals("ft") ){%> selected <%}%>>제목</option>
							<option value="fc" <% if ( findMethod.equals("fc") ){%> selected <%}%>>내용</option>
							<option value="fi" <% if ( findMethod.equals("fi") ){%> selected <%}%>>작성자</option>
						</select>
						<input type="text" name="findStr" value="<%=findStr %>" style="width:100px"/>
						<button onclick="javascript:goList();" class="btn">검색</button> 
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</form>

<!-- 본문 영역 -->
<div class="well">
	<table class="table">
		<tbody>
			<tr>
				<th>번호</th>
				<th>문의 분류</th>
				<th>제목</th>
				<th>답변상태</th>
				<th>작성자</th>
				<th>등록일</th>
			</tr>
		<%
			if(customerNum > 0){
				for(Customer customer : list){
				// for reply articles
		%>
				<tr>
					<td><%=customerNum %></td>
					<td>
					<% if ( customer.getBoardCategory().equals("C003001") ){ %>
						고객문의
						 > <%=customer.getCategoryName() %>
					<%}else if( customer.getBoardCategory().equals("C003002") ){ %>
						자료요청
						 > <%=customer.getCategoryPath() %>
					<%}else{ %>
						내용문의
						 > <%=customer.getCategoryPath() %>
					<%} %>
					</td>
					<td><a href="javascript:goForm('<%=customer.getCustomerId()%>');"><%=customer.getTitle() %></a></td>
					<td>
						<% if( customer.getCustomerCnt() > 0 ){ %>
							답변완료
						<%}else{ %>
							답변대기
						<%} %>
					</td>
					<td><%=customer.getName() %></td>
					<td><%=customer.getRegDate() %></td>
				</tr>
		<%		customerNum--;
				}
			}else{
		%>
			<tr>
				<td colspan="7" style="height:80px;text-align:center;">
				[ 등록된 게시물이 없습니다. ]
				</td>
			</tr>
		<%	} %>
		</tbody>
	</table>
</div>
<div class="pagination">
	<ul>
		<%=paging %>
	</ul>
</div>

<!-- 스크립트 영역 -->
<script>
<!--
function goForm(customerId){
	var f = document.forms.qform;
	
	f.customerId.value = customerId;
	f.action = "customerDetail.do";
	f.submit();
}

function goList(pg){
	var f = document.forms.lform;
	if(pg == undefined)
		pg = 1;
	
	f.pg.value = pg;
	
	f.findCategory.value = getCategoryCode();
	
	var b1 = $("#bcategory").val();
	var b2 = getCategoryCode('board-');
	
	if(b2 != "") {
		f.findBoardCategory.value = b2;
	}else{
		f.findBoardCategory.value = b1;
	}
	
	if( $(".answerStatus:checked").length == 2){
		f.findAnswerStatus.value = "A";
	}else if( $(".answerStatus:checked").length == 1){
		f.findAnswerStatus.value = $(".answerStatus:checked").val();
	}else{
		f.findAnswerStatus.value = "";
	}
	
	f.action = "customerList.do";
	f.submit();
}
//-->

$(document).ready(function(){
	initCategory('<%=findCategory%>');
	initCategory('<%=findBoardCategory%>', 'board-');
})

</script>
