<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	int totalCount = (Integer) request.getAttribute("totalCount");
	int articleNum = (Integer) request.getAttribute("articleNum");
	List<ResultMap> list = (List<ResultMap>) request.getAttribute("list");
	String paging = (String) request.getAttribute("paging");
%>
<!-- 본문 내용 -->
<div class="well">
	<table class="table">
		<col width="50"/>
		<col width="50"/>
		<col width="120"/>
		<col width="*"/>
		<col width="170"/>
		<col width="60"/>
		<col width="100"/>
		<tbody>
			<tr>
				<th>번호</th>
				<th>구분</th>
				<th>학교급/교과</th>
				<th>내용</th>
				<th>시간</th>
				<th>발신번호</th>
				<th>총 전송 수(건)</th>
			</tr>
			<%
				if( totalCount > 0 ){
					for(ResultMap item : list){
			%>
			<tr>
				<td><%=articleNum %></td>
				<td>
				    <%
			           if(item.getString("singletype").equals("1")) {
			        %>
			           		개인
			        <% } else { %>
		            		단체
		            	
			  		 <% } %>
				</td>
		 		<td><%=item.getString("subjects") %></td>
				<td><%=item.getString("message") %></td>
				<td><%=item.getString("reg_date") %></td>
				<td><%=item.getString("callback") %></td>
				<td><%=item.getInt("total_count") %>건</td>
			</tr>
			<%
						articleNum--;
					}
				}else{
			%>
			<tr>
				<td colspan="8" style="height:80px;text-align:center;">
				[ 발송 내역이 없습니다. ]
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

<script type="text/javascript">
<!--

function goList(pg)
{
	location.href = 'list.do?pg=' + pg;
}

//-->
</script>
