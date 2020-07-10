<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	int pg = (Integer) request.getAttribute("pg");
	int findCategory = (Integer) request.getAttribute("findCategory");
	String findMethod = (String) request.getAttribute("findMethod");
	String findStr = (String) request.getAttribute("findStr");
	String mode = (String) request.getAttribute("mode");

	BoardConfig boardConfig = (BoardConfig) request.getAttribute("boardConfig");
	boolean isBoardAccess = (Boolean) request.getAttribute("isBoardAccess");
	
	int totalArticles = (Integer) request.getAttribute("totalArticles");
	int articleNum = (Integer) request.getAttribute("articleNum");
	List<Article> notices = (List<Article>) request.getAttribute("notices");
	List<Article> articles = (List<Article>) request.getAttribute("articles");
	List<BoardCategory> categories = (List<BoardCategory>) request.getAttribute("categories");
	String paging = (String) request.getAttribute("paging");
	
%>
<%-- <%=boardConfig.getHeaderSection() %> --%>

<% 
	if( boardConfig.getUseCategory().equals("Y")){
%>
<div class="class_tab_wrap2 activity5">
<ul>						
	<li <% if( findCategory == 0 ){ %>class="on"<% } %>><a href="javascript:goCategorySearch('0');">전체</a><span class="stem"></span></li>
	<%
		int idx = 1;
	
		for(BoardCategory category : categories){ 
			if( category.getArticleCount() > 0){
	%>
	<li <% if( category.getCategoryId() == findCategory){ %>class="on"<% } %>"><a href="javascript:goCategorySearch('<%=category.getCategoryId()%>');"><%=category.getName() %></a><span class="stem"></span></li>
	<% 
				idx++;
			}
		} 
	%>
</ul>
</div>
<%
	}
%>
     <!-- 게시판 -->
                           	<div class="list_board" style="clear:both;">
								<table cellpadding="0" cellspacing="0" summary="" >
									<caption></caption>
									<colgroup>
										<col width="75"/>
										<col width="*"/>
										<% if( isBoardAccess ){ %>
											<col width="110"/>
										<% } %>
										<col width="55"/>
										<col width="100"/>
									</colgroup>
									<thead>
										<tr>
											<th>번호</th>
											<th>제목</th>
											<% if( isBoardAccess ){ %>
											<th>작성자</th>
											<% } %>
											<th>조회수</th>
											<th>등록일</th>
										</tr>
									</thead>
									<tbody>
										<% for(Article article : notices){ %>
											<tr>
												<td><img src="/assets/front/img/customer/images/img2017/common/img_notice.gif" alt="공지" /></td>
												<td class="notice title"><a href="javascript:goDetail('<%=article.getArticleId()%>');"><%=article.getSubject() %></a>
												<% if( article.getDiffDate() > -14){ %>
												<span class="new"></span>
												<% } %>
												</td>
												<% if( isBoardAccess ){ %>
												<td class="name"><%=article.getUserName() %>(<%=article.getUserId() %>)</td>
												<% } %>
												<td class="hit"><%=article.getHits() %></td>
												<td><%=article.getRegDateFormat() %></td>
											</tr>
											<% } %>
											<%
												if( totalArticles > 0 ){
													for(Article article : articles){
											%>
											<tr>
												<td><%=articleNum %></td>
												<td class="title"><a href="javascript:goDetail('<%=article.getArticleId()%>');"><%=article.getSubject() %></a>
												<% if( article.getDiffDate() > -14){ %>
												<span class="new"></span>
												<% } %>
												</td>
												<% if( isBoardAccess ){ %>
												<td class="name"><%=article.getUserName() %>(<%=article.getUserIdSecret() %>)</td>
												<% } %>
												<td class="hit"><%=article.getHits() %></td>
												<td><%=article.getRegDateFormat() %></td>
											</tr>
											<% 
														articleNum--;
													} 
												}else{
											%>
											<tr class="none">
												<td colspan="5">등록된 내용이 없습니다.</td>								
											</tr>
											<%
												}
											%>
									</tbody>
								</table>
							</div>
							
							<% if( isBoardAccess == true ){ %>
							<div class="write_btn">
								<a href="javascript:goForm();"><img
									src="/assets/front/img/btn/btn_send.gif" alt="등록"></a>
							</div>
							<% } %>
											
								<div class="new_paging_wrap">
									<%=paging %>							
								</div>
								
								<!-- 검색 -->
								<!-- component: search -->
<form name="sform" action="" method="get">
	<input type="hidden" name="findCategory" value="<%=findCategory%>" />
	<div class="board_search">
		<span class="search01"> 
		<select class="jq-select" name="findMethod">
			<option value="ss" <% if( findMethod.equals("ss")){ %>selected<% } %>>제목</option>
			<option value="sc" <% if( findMethod.equals("sc")){ %>selected<% } %>>내용</option>
		</select>
		</span>
		 <span class="search02"><input type="text" class="text_bx" name="findStr" value="<%=findStr%>"/><input type="image" onclick="goSearch();"
			src="/assets/front/img/customer/images/img2017/btn/btn_search_txt.gif" alt="검색"
			class="search_bx" />
		</span>
	</div>
</form>
	<form name="categoryForm" action="" method="get">
		<input type="hidden" name="findCategory" />
	</form>

	<form name="listForm" action="" method="get">
		<input type="hidden" name="pg" /> <input type="hidden"
			name="findCategory" value="<%=findCategory%>" /> <input type="hidden"
			name="findMethod" value="<%=findMethod%>" /> <input type="hidden"
			name="findStr" value="<%=findStr%>" />
	</form>

	<form name="detailForm" action="" method="get">
		<input type="hidden" name="articleId" /> <input type="hidden"
			name="mode" /> <input type="hidden" name="pg" value="<%=pg %>" /> <input
			type="hidden" name="findCategory" value="<%=findCategory%>" /> <input
			type="hidden" name="findMethod" value="<%=findMethod%>" /> <input
			type="hidden" name="findStr" value="<%=findStr%>" />
	</form>

<script type="text/javascript">


$(document).ready(function(){
	$('a[article-id]').click(function(e){
		$('.fileListWrapper[article-id=' + $(this).attr('article-id') + ']').fadeIn('fast');
	});
	
	$('.closed').click(function(){
		$('.fileListWrapper').fadeOut('fast');
	});
});

function goList(pg)
{
	if( pg == undefined) pg = 1;
	
	var f = document.forms.listForm;
	f.pg.value = pg;
	f.submit();
}

function goForm()
{	
	var f = document.forms.detailForm;
	f.mode.value = 'form';
	f.submit();
}

function goDetail(articleId)
{	
	var f = document.forms.detailForm;
	f.articleId.value = articleId;
	f.mode.value = 'detail';
	f.submit();
}

function goCategorySearch(findCategory)
{
	var f = document.forms.categoryForm;
	f.findCategory.value = findCategory;
	f.submit();
}

function goSearch()
{
	var f = document.forms.sform;
	f.submit();
}


</script>