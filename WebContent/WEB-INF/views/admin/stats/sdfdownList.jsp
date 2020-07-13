<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	String findFlag = (String) request.getAttribute("findFlag");
	String findStartDate = (String) request.getAttribute("findStartDate");
	String findEndDate = (String) request.getAttribute("findEndDate");
	String category_1 = (String) request.getAttribute("category_1");
	String category_2 = (String) request.getAttribute("category_2");
	
	int currentTotalCount = (Integer) request.getAttribute("currentTotalCount");
	int totalCount = (Integer) request.getAttribute("totalCount");
	List<ResultMap> list = (List<ResultMap>) request.getAttribute("list");
%>
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
		<th style="border:1px solid #eee;vertical-align:middle;" rowspan="2">검색</th>
		<th style="border:1px solid #eee;">합계</th>
		<td style="border:1px solid #eee;" colspan="3">
			<select name="category_1" class="category" style="width:100px;margin-right:10px;float:left;" >
				<option value="all" <% if(category_1.equals("all")){ %>selected<% } %>>::선택::</option>
				<option value="2015" <% if(category_1.equals("2015")){ %>selected<% } %>>2015</option>
				<option value="2016" <% if(category_1.equals("2016")){ %>selected<% } %>>2016</option>
				<option value="2017" <% if(category_1.equals("2017")){ %>selected<% } %>>2017</option>
				<option value="2018" <% if(category_1.equals("2018")){ %>selected<% } %>>2018</option>
				<option value="2019" <% if(category_1.equals("2019")){ %>selected<% } %>>2019</option>
			</select>
			<select name="category_2" class="category" style="width:100px;margin-right:10px;float:left;" >
				<option value="all" <% if(category_2.equals("all")){ %>selected<% } %>>::선택::</option>
				<option value="01" <% if(category_2.equals("01")){ %>selected<% } %>>1월</option>
				<option value="02" <% if(category_2.equals("02")){ %>selected<% } %>>2월</option>
				<option value="03" <% if(category_2.equals("03")){ %>selected<% } %>>3월</option>
				<option value="04" <% if(category_2.equals("04")){ %>selected<% } %>>4월</option>
				<option value="05" <% if(category_2.equals("05")){ %>selected<% } %>>5월</option>
				<option value="06" <% if(category_2.equals("06")){ %>selected<% } %>>6월</option>
				<option value="07" <% if(category_2.equals("07")){ %>selected<% } %>>7월</option>
				<option value="08" <% if(category_2.equals("08")){ %>selected<% } %>>8월</option>
				<option value="09" <% if(category_2.equals("09")){ %>selected<% } %>>9월</option>
				<option value="10" <% if(category_2.equals("10")){ %>selected<% } %>>10월</option>
				<option value="11" <% if(category_2.equals("11")){ %>selected<% } %>>11월</option>
				<option value="12" <% if(category_2.equals("12")){ %>selected<% } %>>12월</option>			
			</select>
			<button class="btn btn-primary" onclick="goSearch();">검색</button>
			<div style="clear:both;"></div>
		</td>
	</tr>
	<tr>
		<th style="border:1px solid #eee;">기간</th>
		<td style="border:1px solid #eee;" colspan="3">
			<a class="btn" href="javascript:;" onclick="setFindDate('today');">오늘</a>
			<a class="btn" href="javascript:;" onclick="setFindDate('week');">1주일</a>
			<a class="btn" href="javascript:;" onclick="setFindDate('month');">1개월</a>
			<input type="text" name="findStartDate" class="datepicker" value="<%=findStartDate %>" style="width:100px;"/>~<input type="text" name="findEndDate" class="datepicker" value="<%=findEndDate %>" style="width:100px;"/>
			<button class="btn btn-primary" onclick="document.forms.sform.submit();">검색</button>
		</td>
	</tr>
	</thead>
	<input type="hidden" name="findType" value="2"/>
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
		<td><%=item.getInt("cnt")%> (<%=Common.getPercentage(item.getInt("cnt"), currentTotalCount) %>%)</td>
	</tr>
	<%
			articleNum++;
		} 
	%>
	</tbody>
	</table>
</div>


<script type="text/javascript">

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

function goSearch()
{
	var t = document.forms.sform;
	t.findType.value=1;
	t.submit();
	
}

</script>