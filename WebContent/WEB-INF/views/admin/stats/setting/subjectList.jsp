<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	String findCategory = (String) request.getAttribute("findCategory");
	String findAuthType = (String) request.getAttribute("findAuthType");
	String findStartDate = (String) request.getAttribute("findStartDate");
	String findEndDate = (String) request.getAttribute("findEndDate");
	
	int totalCount = (Integer) request.getAttribute("totalCount");
	int currentTotalCount = (Integer) request.getAttribute("currentTotalCount");
	List<ResultMap> list = (List<ResultMap>) request.getAttribute("list");
%>
<div class="btn-toolbar">
	<a class="btn btn-primary" href="gradeList.do">학교급설정</a>
	<a class="btn btn-danger" href="#">교과설정</button>
	<a class="btn btn-primary" href="bookList.do">교과서설정</a>
</div>

<div>
	<form name="sform" action="" method="get">
	<table class="table">
	<col width="80"/>
	<col width="80"/>
	<col width="350"/>
	<col width="80"/>
	<col width="*"/>	
	<thead>
	<tr>
		<th style="border:1px solid #eee;vertical-align:middle;" rowspan="3">검색</th>
		<th style="border:1px solid #eee;">조건</th>
		<td style="border:1px solid #eee;" colspan="3">
			<select name="findCategory">
				<option value="">전체</option>
				<option value="A001" <% if( findCategory.equals("A001")){ %>selected<% } %>>초등</option>
				<option value="A002" <% if( findCategory.equals("A002")){ %>selected<% } %>>중등</option>
				<option value="A003" <% if( findCategory.equals("A003")){ %>selected<% } %>>고등</option>
			</select>
			<a class="btn btn-primary" onclick="goSearch();">검색</a>
		</td>
	</tr>
	<tr>
		<th style="border:1px solid #eee;">구분</th>
		<td style="border:1px solid #eee;" colspan="3">
			<input type="radio" name="findAuthType" id="find" value=""  <% if( Validate.isEmpty(findAuthType)){ %>checked<% } %>/> 누적
			<input type="radio" name="findAuthType" id="find" value="Y" <% if( findAuthType.equals("Y")){ %>checked<% } %> /> 기간
		</td>
	</tr>
	<tr>
		<th style="border:1px solid #eee;">기간</th>
		<td style="border:1px solid #eee;" colspan="3">
			<a class="btn" href="javascript:;" onclick="setFindDate('today');">오늘</a>
			<a class="btn" href="javascript:;" onclick="setFindDate('week');">1주일</a>
			<a class="btn" href="javascript:;" onclick="setFindDate('month');">1개월</a>
			<input type="text" name="findStartDate" class="datepicker" value="<%=findStartDate %>" style="width:100px;"/>~<input type="text" name="findEndDate" class="datepicker" value="<%=findEndDate %>" style="width:100px;"/>
			<a class="btn btn-primary" onclick="goSearch();">검색</a>
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
	<col width="50"/>
	<col width="250"/>
	<col width="*"/>
	<col width="120"/>
	<thead>
	<tr>
		<th>순위</th>
		<th>구분</th>
		<th colspan="2">건수</th>
	</tr>
	</thead>
	<tbody>
	<%
		int articleNum = 1;
		for(ResultMap item : list){
	%>
	<tr>
		<td><%=articleNum %></td>
		<td><%=item.getString("title") %></td>
		<td>
			<div style="background-color:#d9d9d9;border:1px solid #ddd;width:<%=Common.getPercentage(item.getInt("cnt"), currentTotalCount)%>%;height:30px;"></div>
		</td>
		<td><%=new DecimalFormat("###,###").format(item.getInt("cnt")) %> (<%=Common.getPercentage(item.getInt("cnt"), currentTotalCount) %>%)</td>
	</tr>
	<%
			articleNum++;
		} 
	%>
	</tbody>
	</table>
</div>


<script type="text/javascript">

function goSearch()
{
	document.forms.sform.submit();
}


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
	
	<% if( Validate.isEmpty(findStartDate) || Validate.isEmpty(findEndDate) ) { %>
	setFindDate('week');
	<% } %>
	
});

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