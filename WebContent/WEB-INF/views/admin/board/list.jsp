<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	int boardId = (Integer) request.getAttribute("boardId");
	int pg = (Integer) request.getAttribute("pg");
	String findMethod = (String) request.getAttribute("findMethod");
	String findStr = (String) request.getAttribute("findStr");

	BoardConfig boardConfig = (BoardConfig) request.getAttribute("boardConfig");
	int totalArticle = (Integer) request.getAttribute("totalArticle");
	int articleNum = (Integer) request.getAttribute("articleNum");
	List<Article> articles = (List<Article>) request.getAttribute("articles");
	String paging = (String) request.getAttribute("paging");
	String findType = (String) request.getAttribute("findType");
%>
<div class="btn-toolbar">
	<button class="btn btn-primary" onclick="goForm();"><i class="icon-plus"></i> 게시글 등록</button>
	<div class="btn-group">
	</div>
</div>
<%if(boardId == 36 || boardId == 37){ %>
<form name="lform" method="post">
	<input type="hidden" name="boardId" value="<%=boardId%>"/>
	<input type="hidden" name="pg"  value="<%=pg %>" />
	<!-- 검색 테이블 -->
	<div>
		<table class="table table-bordered">
			<col width="80" />
			<col width="*" />
			<tbody>
				<tr>
					<th rowspan="2">검색</th>
					<td>
						메인노출 :
						<select name="findType" style="width:100px;" onchange="javascript:findtype(<%=pg %>);">
							<option value="">전체</option>
							<option value="N" <%if(findType.equals("N")){ %>selected<%} %> >미노출</option>
							<option value="Y" <%if(findType.equals("Y")){ %>selected<%} %> >노출</option>
						</select>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</form>
<%} %>
<div class="well">
	<table class="table">
	<col width="60"/>
	<col width="*"/>
	<col width="120"/>
	<col width="100"/>
	<col width="80"/>
	<%if(boardId == 36 || boardId == 37){ %>
	<col width="80"/>
	<%} %>
	<thead>
	<tr>
		<th>No.</th>		
		<th>제목</th>
		<th>글쓴이</th>
		<th>등록일</th>
		<th>조회수</th>
		<%if(boardId == 36 || boardId == 37){ %>
		<th>메인노출</th>
		<%} %>
	</tr>
	</thead>
	<tbody>
	<% 
		if(totalArticle > 0){
			
			for(Article article : articles){
			
				// for reply articles
				String blanks = "";
						
				for(int i = 0 ; i < article.getSteps().length() ; i++)
					blanks += "&nbsp;&nbsp;";
	%>
	<tr>
		<td><%=articleNum %></td>
		<td><%=blanks %><a href="javascript:goForm('<%=article.getArticleId()%>');"><%=article.getSubject() %></a></td>
		<td><%=article.getUserId() %></td>
		<td><%=article.getRegDate().substring(0,10) %></td>
		<td><%=article.getHits() %></td>
		<%if(boardId == 36 || boardId == 37){ %>
		<%if(article.getIsSecret().equals("N")) {%>
		<td>미노출</td>
		<%}else{ %>
		<td>노출</td>
		<%} %>
		<%} %>
	</tr>
	<%
				articleNum--;
			}
		}else{
	%>
	<tr>
	<%if(boardId == 36 || boardId == 37){ %>
		<td colspan="6" style="height:80px;text-align:center;">
		[ 등록된 게시물이 없습니다. ]
		</td>
	<%}else{ %>
		<td colspan="5" style="height:80px;text-align:center;">
		[ 등록된 게시물이 없습니다. ]
		</td>
	<%} %>
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

<form name="qform" action="form.do" method="get">
<input type="hidden" name="boardId" value="<%=boardId%>"/>
<input type="hidden" name="articleId"/>
<input type="hidden" name="pg" value="<%=pg%>"/>
<input type="hidden" name="findMethod" value="<%=findMethod%>"/>
<input type="hidden" name="findStr" value="<%=findStr%>"/>
<input type="hidden" name="findType" value="<%=findType%>"/>
</form>

<form name="listForm" action="list.do" method="get">
<input type="hidden" name="boardId" value="<%=boardId%>"/>
<input type="hidden" name="pg"/>
<input type="hidden" name="findMethod" value="<%=findMethod%>"/>
<input type="hidden" name="findStr" value="<%=findStr%>"/>
<input type="hidden" name="findType" value="<%=findType%>"/>
</form>

<script type="text/javascript">
<!--

function goList(pg)
{
	var f = document.forms.listForm;
	
	f.pg.value = pg;
	f.submit();
}

function goForm(articleId)
{	
	var f = document.forms.qform;
	
	if( articleId == undefined )
		articleId = '0';
	
	f.articleId.value = articleId;
	f.submit();
}
function findtype(pg){
	var f = document.forms.lform;
	if(pg == undefined){
		f.pg.value = 1;
	}else if( pg == -1){
		$("form").each(function() { this.reset(); });
	}else{
		f.pg.value = pg;
	}
	f.action = "list.do";
	f.submit();
}
//-->
</script>