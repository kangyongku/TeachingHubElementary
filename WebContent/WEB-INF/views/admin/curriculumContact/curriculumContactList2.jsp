<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	String mode = (String)request.getAttribute("mode");
	int pg = (Integer) request.getAttribute("pg");
	String paging = (String) request.getAttribute("paging");

	List<Category> categories = (List<Category>)request.getAttribute("categories");
	String category = (String)request.getAttribute("category");
	String findCategory = (String)request.getAttribute("findCategory");

	CurriculumContact curriculumContact = (CurriculumContact) request.getAttribute("curriculumContact");
	int curriculumContactNum = (Integer)request.getAttribute("curriculumContactNum");
	List<CurriculumContact> curriculumContactList = (List<CurriculumContact>)request.getAttribute("curriculumContactList");
	int curriculumId = (Integer)request.getAttribute("curriculumId");
%>	
<form name="dform" action="" method="post">
	<input type="hidden" name="curriculumId" value="" />
	<input type="hidden" name="mode" value="" />
</form>

<!-- 입력 영역 -->
<form name="wform" action="" method="post">
	<input type="hidden" name="mode" value="<%=mode %>" />
	<input type="hidden" name="curriculumId" value="<%=curriculumId %>" />
	<input type="hidden" name="category" value="<%=curriculumContact.getCategory() %>" />
	<input type="hidden" name="pg" value="<%=pg %>" />
	<input type="hidden" name="beforeCategory" value="<%=curriculumContact.getCategory() %>" />
 	<div>
		<table class="table table-bordered">
			<col width="150"/>
			<col width="150"/>
			<tbody> 
			<tr>
				<th>학교급/교과(*)</th>
				<td>
					<select name="category-2" class="category" style="width:100px;margin-right:10px;float:left;" onchange="showCategory(2);">
						<option value="">::선택::</option>
						<% for(Category list : categories){ %>
						<option value="<%=list.getCategory()%>" data-depth="<%=list.getDepth() %>" data-children="<%=list.getChildren()%>"><%=list.getName() %></option>
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
				<th>이메일(*)</th>
				<td>
					<input type="text" name="email" value="<%=curriculumContact.getEmail() %>" style="width:400px"/><br/>
					여러 개의 메일 주소 입려 시 메일 주소 다음에; 를 띄어쓰기 없이 입력해주세요.<br/>
					고객센터 > 문의요청게시판 > 자료 요청 시 입력하신 이메일로 발송됩니다.
				</td>
			</tr>
			<tr>
				<th>교과공통자료</th>
				<td>
					<input type="text" name="linkUrl" value="<%=curriculumContact.getLinkUrl() %> " style="width:400px" maxlength="128"/>
				</td>
			</tr>
			</tbody>
		</table>
	</div>
</form>
<div class="btn-toolbar">
	<div class="btn-group">
	<%if(mode.equals("modify")){ %><button class="btn btn-danger" onclick="checkForm();">수정</button> <%}else{%>
	<button class="btn btn-primary" onclick="checkForm();"><i class="icon-plus"></i> 등록</button> <%} %>
	</div>
</div>

<!-- 본문 영역 -->
	<form name="lform" action="" method="get">
	<input type="hidden" name="pg" value="<%=pg %>" />
	<input type="hidden" name="findCategory" value="<%=findCategory %>"/>
	<input type="hidden" name="mode" value="<%=mode %>" />
	<input type="hidden" name="curriculumId" value="<%=curriculumId %>" />
	</form>
	
	<!-- 설문항목 체크 --> 
	<div class="well">
		<table class="table">
			<col width="80" />
			<col width="80" />
			<col width="180" />
			<col width="50" />
			<col width="180" />
			<col width="80" />
			<tbody>
				<tr>
				<th>검색</th>
				<th>
				학교급/교과
				</th>
				<th>
					<select name="find-category-2" class="category" style="width:100px;margin-right:10px;float:left;" onchange="showCategory(2,'','find-');">
						<option value="">::선택::</option>
						<% for(Category list : categories){ %>
						<option value="<%=list.getCategory()%>" data-depth="<%=list.getDepth() %>" data-children="<%=list.getChildren()%>"><%=list.getName() %></option>
						<% } %>
					</select>
					<select name="find-category-3" class="category" style="width:100px;display:none;" onchange="showCategory(3,'','find-');">
						<option value="">::선택::</option>
					</select>
					<select name="find-category-4" class="category" style="width:100px;display:none;" onchange="showCategory(4,'','find-');">
						<option value="">::선택::</option>
					</select>
				</th>
				<td>
					<a href="javascript:goList()" class="btn btn-primary">검색</a>
				</td>
				</tr>
			</tbody>
		</table>
		
		<table class="table">
		<col width="80" />
		<col width="80" />
		<col width="300" />
		<col width="150" />
		<col width="100" />
			<tbody>
			<tr>
				<th>학교급</th>
				<th>교과</th>
				<th>담당자 이메일</th>
				<th>공통자료 URL</th>
				<th>관리</th>
			</tr>
			<%
				if(curriculumContactNum > 0){
					for(CurriculumContact list : curriculumContactList){
			%>	
						<tr>
						<td><%=list.getGradeName() %></td>
						<td><%=list.getCategoryName() %></td>
						<td><%=list.getEmail() %></td>
						<td>
						<% if( list.getLinkUrl() != "" ){ %>
							<a href="<%=list.getLinkUrl() %>" target="_blank"><%=list.getLinkUrl() %></a>
						<%}%>
						</td>
						<td>
							<a href="javascript:goForm('<%=list.getCurriculumId() %>')">수정</a>&nbsp|&nbsp
							<a href="javascript:goDelete('<%=list.getCurriculumId() %>')">삭제</a>
						</td>
						</tr>
			<%
						}
					}else{
			%>
					<tr>
						<td colspan="5" style="height:80px;text-align:center;">
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

<!-- 스크립트 영역 -->
<script type="text/javascript">
<!--
// load default contents data
$(document).ready(function(){
	<%if ( !curriculumContact.getCategory().equals("") ){%>
	initCategory('<%=curriculumContact.getCategory()%>');	
	<%}%>
	<%if ( !findCategory.equals("") ){%>
	initCategory('<%=findCategory%>','find-');	
	<%}%>
})

function goList(pg)
{
	var f = document.forms.lform;
	if(pg == undefined){
		f.pg.value = 1;
	}
	else{
		f.pg.value = pg;
	}
	f.findCategory.value = getCategoryCode('find-');
	f.target = '_self';
	f.action = 'curriculumContactList2.do';
	f.submit();
}

function checkForm()
{
	var f = document.forms.wform;
	
	$("input[name=category]").val(getCategoryCode());
	if( $("input[name=category]").val() == '' ) return doError('선택된 카테고리가 없습니다.' , 'category-1' , 'wform');
	if( $("input[name=email]").val() == '' ) return doError('이메일을 등록하여주십시오.' , 'email' , 'wform');
	
	f.target = '_self';
	f.action = 'curriculumContactAction2.do';
	f.submit();
	
}

function goForm(curriculumId)
{
	var f = document.forms.lform;
	
	f.curriculumId.value = curriculumId;
	f.mode.value = "modify";
	
	if( curriculumId == '' ) return doError('새로고침후 시도해주시기 바랍니다.' , '' , 'wform');
	
	f.action = "curriculumContactList2.do";
	f.submit();
}

function goDelete(curriculumId)
{
	var f = document.forms.dform;
	if(confirm('해당 글을 삭제합니다.\r\n진행하시겠습니까?')){
		f.curriculumId.value = curriculumId;
		f.target = '_self';
		f.action = 'curriculumContactAction2.do'
		f.mode.value = 'delete'
		f.submit();
	}
}
//-->
</script>