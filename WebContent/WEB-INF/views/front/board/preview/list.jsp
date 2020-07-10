<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	int pg = (Integer) request.getAttribute("pg");
	int findCategory = (Integer) request.getAttribute("findCategory");
	String findMethod = (String) request.getAttribute("findMethod");
	String findStr = (String) request.getAttribute("findStr");
	String findSort = (String) request.getAttribute("findSort");
	String mode = (String) request.getAttribute("mode");

	BoardConfig boardConfig = (BoardConfig) request.getAttribute("boardConfig");
	boolean isBoardAccess = (Boolean) request.getAttribute("isBoardAccess");
	System.out.println("isBoardAccess : "+isBoardAccess);
	int totalArticles = (Integer) request.getAttribute("totalArticles");
	int articleNum = (Integer) request.getAttribute("articleNum");
	List<Article> articles = (List<Article>) request.getAttribute("articles");
	List<BoardCategory> categories = (List<BoardCategory>) request.getAttribute("categories");
	String paging = (String) request.getAttribute("paging");
	
	Member member = (Member) session.getAttribute("member");
%>
<%-- <%=boardConfig.getHeaderSection() %> --%>

<% 
	if( boardConfig.getUseCategory().equals("Y")){
%>
	<div class="class_tab_wrap4">
    	<ul>
			<li class="none <% if( findCategory == 0 ){ %>on<% } %>"><a href="javascript:goCategorySearch('0');">전체</a><span class="stem"></span></li>
			<%
				int idx = 1;
	
				for(BoardCategory category : categories){ 
					if( category.getArticleCount() > 0){
			%>
			<li class="<% if( idx < 5 ){ %>none<% } %> 
				<% if( category.getCategoryId() == findCategory){ %>on<% } %>">
				<a href="javascript:goCategorySearch('<%=category.getCategoryId()%>');"><%=category.getName() %></a>
				<span class="stem"></span>
			</li>
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
                            <!--// tab menu -->
                            <!-- 게시판 -->
                            <div class="thumb_board_wrap">
                            <% if( boardConfig.getBoardId() == 18){ %>       
								<!-- 멀티미디어 자료는 기존 게시판 스타일 유지 -->
                               <ul class="tab_area">
										<li class="<% if( findSort.equals("articleId")){ %>on<% } %> area01"><a href="javascript:goSort('articleId');">날짜순</a></li>
										<li class="<% if( findSort.equals("recommend")){ %>on<% } %> area02"><a href="javascript:goSort('recommend');">추천순</a></li>
									</ul>
							<% } %>									
									
									<!-- component: data sort -->
									<ul class="data_sort">
										<%
											if( totalArticles > 0 ){
												for(Article article : articles){
										%>
									<li>
									<span class="number"><%=articleNum %></span>
										<a href="javascript:goDetail('<%=article.getArticleId() %>' , '<%=article.getLinkUrl() %>');" class="data_img"><img src="<%=article.getThumbnailPath() %>" width="150" height="115" alt="" /></a>
										<div class="data_detail">
											<dl>
												<dt><a href="javascript:goDetail('<%=article.getArticleId() %>' , '<%=article.getLinkUrl() %>');"><%=article.getSubject() %></a>
												<% if( article.getDiffDate() > -14){ %>
												<span class="new"></span>
												<% } %>
												</dt>
												<% if( boardConfig.getModifyRegDate().equals("Y")){ %>
												<dd class="first"><%=article.getRegDateFormat() %></dd>
												<% } %>
									
												<dd><% if( isBoardAccess ){ %>작성자 : <%=article.getUserName() %>(<%=article.getUserIdSecret() %>)<% } %></dd>
												<% if( !Validate.isEmpty(article.getEtc()) ){ %>
												<dd><%=article.getEtc() %></dd> 
												<% } %>
											</dl>
											<p><%=article.getListContents() %></p>
											<ul class="data_function">
												<% if( boardConfig.getUseRecommend().equals("Y")){ %>
												<li class="data01">추천<strong><%=article.getRecommend() %></strong></li>
												<% } %>
												<li class="data02">댓글<strong><%=article.getComments() %></strong></li>
												<% if( article.getFileCount() > 0 ){ %>
												<li class="data03 on"><a href="javascript:;" article-id="<%=article.getArticleId()%>">첨부파일&nbsp;<strong><%=article.getFileCount() %></strong></a></li>
												<% } %>
											</ul>
										</div>
										<div class="layer_popup fileListWrapper" article-id="<%=article.getArticleId() %>" style="display:none;">
											<div class="layer_popup_wrap">
												<ul></ul>
												<a href="#" class="closed"><img src="/assets/front/img/btn/btn_closed.gif" alt="닫기" /></a>
												<p class="footer">&nbsp;</p>
											</div>
										</div>
									</li>				
									<%
												articleNum--;
											}
										}else{
									%>	
									<li class="none">등록된 내용이 없습니다.</li>	
									<%
										}
									%>	
									</ul>
								
			 <% if( isBoardAccess == true ){ %>
					<div class="write_btn">
						<a href="javascript:goForm();"><img src="/assets/front/img/btn/btn_send.gif" alt="등록"></a>
					</div>  
		<% } %>

<!-- 검색 -->
<div class="new_paging_wrap">
	<%=paging %>
</div>

<!-- component: search -->
<form name="sform" action="" method="get">
<input type="hidden" name="findCategory" value="<%=findCategory %>"/>
<div class="board_search">
	<span class="search01">
		<select class="jq-select" name="findMethod">
		<option value="ss" <% if( findMethod.equals("ss")){ %>selected<% } %>>제목</option>
		<option value="sc" <% if( findMethod.equals("sc")){ %>selected<% } %>>내용</option>
		</select>
	</span>
	<span class="search02">
		<input type="text" class="text_bx" name="findStr" value="<%=findStr%>"/><input type="image" src="/assets/front/img/btn/btn_search_txt.gif" alt="검색" class="search_bx" onclick="goSearch();"/>
	</span>
</div>
</form>

<form name="categoryForm" action="" method="get">
<input type="hidden" name="findCategory"/>
</form>

<form name="listForm" action="" method="get">
<input type="hidden" name="pg"/>
<input type="hidden" name="findCategory" value="<%=findCategory%>"/>
<input type="hidden" name="findMethod" value="<%=findMethod%>"/>
<input type="hidden" name="findStr" value="<%=findStr%>"/>
<input type="hidden" name="findSort" value="<%=findSort%>"/>
</form>

<form name="detailForm" action="" method="get">
<input type="hidden" name="articleId"/>
<input type="hidden" name="mode"/>
<input type="hidden" name="pg" value="<%=pg %>"/>
<input type="hidden" name="findCategory" value="<%=findCategory%>"/>
<input type="hidden" name="findMethod" value="<%=findMethod%>"/>
<input type="hidden" name="findStr" value="<%=findStr%>"/>
<input type="hidden" name="findSort" value="<%=findSort%>"/>
</form>

<script type="text/javascript">


$(document).ready(function(){
	$('a[article-id]').click(function(e){
		
		var articleId = $(this).attr('article-id');
		
		<% if( boardConfig.getUseAnnmsView().equals("N") && member == null ){ %>
		goLogin();
		<% }else{ %>
		// get file list data
		$.ajax({
			url : '/api/article/getAttachments.do' ,
			type : 'post' ,
			data : 'articleId=' + articleId,
			dataType : 'json' ,
			beforeSend : function(){
				$('.fileListWrapper[article-id=' + articleId + ']').find('ul').empty();
			},
			error : function(){
				alert('서버와의 통신도중 에러가 발생되었습니다.');
			},
			success : function(json){
				if( json.result == 'SUCCESS' )
				{
					for(var i = 0 ; i < json.attachments.length ; i++ )
					{	
						$('.fileListWrapper[article-id=' + articleId + ']').find('ul').append('<li><a href="javascript:getBoardAttachmentFile(\'' + json.attachments[i].fileId + '\');"><img src="/assets/front/img/icon/icon_' + json.attachments[i].fileIconExt + '.gif" alt="한글 파일">' + json.attachments[i].originName + '</a></li>\r\n');
					}
					
					$('.fileListWrapper[article-id=' + articleId + ']').fadeIn('fast');	
				}
				else
					alert(json.msg);
			}
		});
		<% } %>
	});
	
	$('.closed').click(function(){
		$('.fileListWrapper').fadeOut('fast');
	});
});

function goSort(findSort)
{
	var f = document.forms.listForm;
	f.pg.value = 1;
	f.findSort.value = findSort;
	f.submit();
}

function goList(pg)
{
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

function goDetail(articleId , linkUrl)
{
	<% if( isBoardAccess == true ){ %>
	var f = document.forms.detailForm;
	f.articleId.value = articleId;
	f.mode.value = 'detail';
	f.submit();
	<% }else{ %>
	if( linkUrl != '' )
		window.open(linkUrl , '' , 'scrollbars=yes,resizeable=yes,toolbar=yes');
	else
	{
		var f = document.forms.detailForm;
		f.articleId.value = articleId;
		f.mode.value = 'detail';
		f.submit();
	}
	<% } %>
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

