<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
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
	String findBookId = (String) request.getAttribute("findBookId");
	String findUnitIds = (String) request.getAttribute("findUnitIds");
	String findUnitId = (String) request.getAttribute("findUnitId");
	String findIsApprove = (String) request.getAttribute("findIsApprove");
	String findFlagType = (String) request.getAttribute("findFlagType");
	
	// bookid는 숫자형으로 변환해야 한다.
	if( Validate.isEmpty(findBookId) )
		findBookId = "0";
	
	int totalCount = (Integer) request.getAttribute("totalCount");
	int articleNum = (Integer) request.getAttribute("articleNum");
	List<Headword> list = (List<Headword>) request.getAttribute("list");
	
	List<Category> categories = (List<Category>) request.getAttribute("categories");
	List<Book> books = (List<Book>) request.getAttribute("books");
	List<Category> courses = (List<Category>) request.getAttribute("courses");
	
	String paging = (String) request.getAttribute("paging");
%>
<div class="btn-toolbar">
	<button class="btn btn-primary" onclick="goForm();"><i class="icon-plus"></i> 표제어 등록</button>
	<button class="btn" onclick="copy();"><i class="icon-plus"></i> 표제어 복사</button>
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
		<th style="border:1px solid #eee;vertical-align:middle;" rowspan="6">검색</th>
		<th style="border:1px solid #eee;">분류</th>
		<td style="border:1px solid #eee;" colspan="3">
			<select name="category-2" class="category" style="width:100px;margin-right:10px;float:left;" onchange="showCategory(2);getSmartBooks();">
				<option value="">::선택::</option>
				<% for(Category category : categories){ %>
				<option value="<%=category.getCategory()%>" data-depth="<%=category.getDepth() %>" data-children="<%=category.getChildren()%>"><%=category.getName() %></option>
				<% } %>
			</select>
			<select name="category-3" class="category" style="display:none;width:100px;margin-right:10px;float:left;" onchange="showCategory(3);getSmartBooks();">
				<option value="">::선택::</option>
			</select>
			<select name="category-4" class="category" style="display:none;width:100px;margin-right:10px;float:left;" onchange="showCategory(4);getSmartBooks();">
				<option value="">::선택::</option>
			</select>			
			<div style="clear:both;"></div>
		</td>	
	</tr>
	<tr>
		<th style="border:1px solid #eee;">도서정보</th>
		<td style="border:1px solid #eee;" colspan="3">
			<select name="bookId" onchange="showSmartUnit('1');">
				<option value="">::도서선택::</option>
				<% for(Book book : books) { %>
				<option value="<%=book.getBookId()%>" <% if( book.getBookId() == new Integer(findBookId)){ %>selected<% } %>><%=book.getName() %></option>
				<% } %>
			</select>
		</td>
	</tr>
	<tr>
		<th style="border:1px solid #eee;">단원분류</th>
		<td style="border:1px solid #eee;" colspan="3">
			<select name="unit-1" style="float:left;" onchange="showSmartUnit('2');">
				<option value="">::대단원::</option>
			</select>
			<select name="unit-2" style="float:left;display:none;" onchange="showSmartUnit('3');">
				<option value="">::중단원::</option>
			</select>
			<select name="unit-3" style="float:left;display:none;">
				<option value="">::소단원::</option>
			</select>
			<div style="clear:both;"></div>
		</td>
	</tr>
	<form name="sform" action="" method="get" onsubmit="search();return false;">
	<input type="hidden" name="findCategory" value=""/>
	<input type="hidden" name="findBookId" value=""/>
	<input type="hidden" name="findUnitIds" value=""/>
	<input type="hidden" name="findUnitId" value=""/>
	<tr>
		<th style="border:1px solid #eee;">등록일</th>
		<td style="border:1px solid #eee;">
			<input type="text" name="findStartDate" class="datepicker" style="width:120px;" value="<%=findStartDate%>"/> ~ <input type="text" name="findEndDate" class="datepicker" style="width:120px;" value="<%=findEndDate%>"/>
		</td>	
		<th style="border:1px solid #eee;">노출유무</th>
		<td style="border:1px solid #eee;">
			<select name="findIsUse">
				<option value="">전체</option>
				<option value="Y" <% if( findIsUse.equals("Y")){ %>selected<% } %>>사용</option>
				<option value="N" <% if( findIsUse.equals("N")){ %>selected<% } %>>미사용</option>
			</select>
		</td>	
	</tr>
	<tr>
		<th style="border:1px solid #eee;">구분</th>
		<td style="border:1px solid #eee;">
			<input type="radio" name="findFlagType" value="H" <% if( findFlagType.equals("H")){ %>checked<% } %>/> 인물/사건 캘린더
			<input type="radio" name="findFlagType" value="E" <% if( findFlagType.equals("E")){ %>checked<% } %>/> 역사전시관
		</td>	
		<th style="border:1px solid #eee;">승인</th>
		<td style="border:1px solid #eee;">
			<select name="findIsApprove">
				<option value="">전체</option>
				<option value="Y" <% if( findIsApprove.equals("Y")){ %>selected<% } %>>승인</option>
				<option value="N" <% if( findIsApprove.equals("N")){ %>selected<% } %>>대기</option>
				<option value="D" <% if( findIsApprove.equals("D")){ %>selected<% } %>>미승인</option>
			</select>
		</td>	
	</tr>
	<tr>
		<th style="border:1px solid #eee;">기본검색</th>
		<td style="border:1px solid #eee;" colspan="3">
			<select name="findMethod" style="width:80px;">
				<option value="st" <% if( findMethod.equals("st")){ %>selected<% } %>>표제어</option>
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
	<col width="50"/>
	<col width="180"/>
	<col width="*"/>
	<col width="80"/>
	<col width="100"/>
	<col width="100"/>
	<col width="100"/>
	<thead>
	<tr>
		<th rowspan="2" style="vertical-align:middle;border:1px solid #747474;"><input type="checkbox" name="toggle"/></th>
		<th rowspan="2" style="vertical-align:middle;border:1px solid #747474;">No.</th>
		<th style="vertical-align:middle;border:1px solid #747474;">분류</th>
		<th style="vertical-align:middle;border:1px solid #747474;">제목</th>
		<th style="vertical-align:middle;border:1px solid #747474;">노출유무</th>
		<th rowspan="2" style="vertical-align:middle;border:1px solid #747474;">작성자</th>
		<th rowspan="2" style="vertical-align:middle;border:1px solid #747474;">등록일</th>
		<th rowspan="2" style="vertical-align:middle;border:1px solid #747474;">조회수</th>
	</tr>
	<tr>
		<th style="vertical-align:middle;border:1px solid #747474;">도서명</th>
		<th colspan="2" style="vertical-align:middle;border:1px solid #747474;">단원구분</th>		
	</tr>
	<form name="pform" action="headwordCopyPopup.do" method="post">
		<% 
			if( totalCount > 0 ){ 
			
				for(Headword headword : list){
		%>
		<tr>
			<td rowspan="2" style="vertical-align:middle;text-align:center;border:1px solid #eee;"><input type="checkbox" name="headwordId" value="<%=headword.getHeadwordId()%>"/></td>
			<td rowspan="2" style="vertical-align:middle;text-align:center;border:1px solid #eee;"><%=articleNum %></td>
			<td style="vertical-align:middle;text-align:center;border:1px solid #eee;"><%=headword.getCategoryPath() %></td>
			<td style="vertical-align:middle;border:1px solid #eee;"><a href="javascript:goForm('<%=headword.getHeadwordId()%>');"><%=headword.getTitle() %></a></td>
			<td style="vertical-align:middle;text-align:center;border:1px solid #eee;"><%=headword.getIsUseName() %></td>
			<td rowspan="2" style="vertical-align:middle;text-align:center;border:1px solid #eee;"><%=headword.getUserName() %></td>
			<td rowspan="2" style="vertical-align:middle;text-align:center;border:1px solid #eee;"><%=headword.getRegDateFormatted() %></td>
			<td rowspan="2" style="vertical-align:middle;text-align:center;border:1px solid #eee;"><%=headword.getHits() %></td>		
		</tr>
		<tr>
			<td style="vertical-align:middle;text-align:center;border:1px solid #eee;"><%=headword.getBookName() %></td>
			<td colspan="2" style="vertical-align:middle;border:1px solid #eee;"><%=headword.getUnitPath() %></td>		
		</tr>
		<% 
					articleNum--;
				}
			}else{ 
		%>	
	<tr>
		<td colspan="10" style="height:80px;text-align:center;">[ 조회된 표제어가 없습니다. ]</td>
	</tr>
	<% } %>
	</thead>
	</form>
	<tbody>	
	</tbody>
	</table>
</div>
<!-- 
<div class="btn-toolbar">
	<button class="btn btn-primary" onclick="goForm();"><i class="icon-plus"></i> 표제어 등록</button>
	<button class="btn" onclick="copy();"><i class="icon-plus"></i> 표제어 복사</button>
	<div class="btn-group">
	</div>
</div>
 -->
<div class="pagination">
	<ul>
		<%=paging %>
	</ul>
</div>

<form name="listForm" action="" method="get">
<input type="hidden" name="pg"/>
<input type="hidden" name="findCategory" value="<%=findCategory%>"/>
<input type="hidden" name="findCourse" value="<%=findCourse%>"/>
<input type="hidden" name="findIsUse" value="<%=findIsUse%>"/>
<input type="hidden" name="findMethod" value="<%=findMethod%>"/>
<input type="hidden" name="findStr" value="<%=findStr%>"/>
<input type="hidden" name="findStartDate" value="<%=findStartDate%>"/>
<input type="hidden" name="findEndDate" value="<%=findEndDate%>"/>
<input type="hidden" name="findBookId" value="<%=findBookId%>"/>
<input type="hidden" name="findUnitIds" value="<%=findUnitIds%>"/>
<input type="hidden" name="findUnitId" value="<%=findUnitId%>"/>
<input type="hidden" name="findIsApprove" value="<%=findIsApprove%>"/>
<input type="hidden" name="findFlagType" value="<%=findFlagType%>"/>
</form>

<form name="qform" action="headwordForm.do" method="get">
<input type="hidden" name="headwordId"/>
<input type="hidden" name="pg" value="<%=pg%>"/>
<input type="hidden" name="findCategory" value="<%=findCategory%>"/>
<input type="hidden" name="findCourse" value="<%=findCourse%>"/>
<input type="hidden" name="findIsUse" value="<%=findIsUse%>"/>
<input type="hidden" name="findMethod" value="<%=findMethod%>"/>
<input type="hidden" name="findStr" value="<%=findStr%>"/>
<input type="hidden" name="findStartDate" value="<%=findStartDate%>"/>
<input type="hidden" name="findEndDate" value="<%=findEndDate%>"/>
<input type="hidden" name="findBookId" value="<%=findBookId%>"/>
<input type="hidden" name="findUnitIds" value="<%=findUnitIds%>"/>
<input type="hidden" name="findUnitId" value="<%=findUnitId%>"/>
<input type="hidden" name="findIsApprove" value="<%=findIsApprove%>"/>
<input type="hidden" name="findFlagType" value="<%=findFlagType%>"/>
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
	renderSmartUnits('<%=findBookId%>' , '<%=findUnitIds%>');
	
	$('input[name=toggle]').click(function(){
		$('input[name=headwordId]').attr('checked' , $(this).is(':checked'));
	});
});

function copy()
{
	/*
	var checkedHeadwords = [];
	
	$('input[name=headwordId]').each(function(){
		if( $(this).is(':checked') )
			checkedHeadwords.push($(this).val());
	});
	*/
	
	if( $('input[name=headwordId]:checked').length == 0 )
		return alert('선택된 표제어가 존재하지 않습니다.');
	
	//팝업으로 넘긴다.
	window.open('headwordCopyPopup.do' , 'copy' , 'width=850,height=235,scrollbars=no');
	
	//Post로 파라미터 넘기기
	var f = document.forms.pform;
	f.target = "copy"; 
    f.submit();
}

function search()
{
	var f = document.forms.sform;
	
	var category = getCategoryCode();	
	var unitIds = getUnitIds();
	var unitId = getUnitId();
	
	// set data
	f.findCategory.value = category;
	f.findBookId.value = $('select[name=bookId] option:selected').val();
	f.findUnitIds.value = unitIds;
	f.findUnitId.value = (unitId == 0) ? '' : unitId;
	
	f.target = '_self';
	f.submit();
}

function goList(pg)
{		
	var f = document.forms.listForm;
	f.pg.value = pg;
	f.submit();
}

function goForm(headwordId)
{	
	var f = document.forms.qform;
	
	if( headwordId == undefined )
		headwordId = '0';
	
	f.headwordId.value = headwordId;
	f.submit();
}



//-->
</script>