<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	int pg = (Integer) request.getAttribute("pg");
	int boardType = Common.getParameter(request, "boardType", 2);
	int findCategory = (Integer) request.getAttribute("findCategory");
	String findMethod = (String) request.getAttribute("findMethod");
	String findStr = (String) request.getAttribute("findStr");
	String mode = (String) request.getAttribute("mode");
	//String menuflag = Common.getParameter(request, "menuFlag", '');
	
	String viewType = "";
	
	//String viewType =  Common.getParameter(request, "viewType", "1");

	BoardConfig boardConfig = (BoardConfig) request.getAttribute("boardConfig");
	
	// 범교과주제수업일 경우 보여지는 형태 강제 고정
	if(boardConfig.getBoardId()==47){
		viewType = Common.getParameter(request, "viewType", "2");
	}
	else{
		viewType =  Common.getParameter(request, "viewType", "1");
	}
	
	boolean isBoardAccess = (Boolean) request.getAttribute("isBoardAccess");
	
	System.out.println("isBoardAccess === " +  isBoardAccess);
	
	int totalArticles = (Integer) request.getAttribute("totalArticles");
	int articleNum = (Integer) request.getAttribute("articleNum");
	List<Article> notices = (List<Article>) request.getAttribute("notices");
	List<Article> articles = (List<Article>) request.getAttribute("articles");
	List<BoardCategory> categories = (List<BoardCategory>) request.getAttribute("categories");
	String paging = (String) request.getAttribute("paging");
	Member member = (Member) session.getAttribute("member");
	
%>
<%//=boardConfig.getHeaderSection() %>
<div class="thumb_board_wrap">
<% 
	if( boardConfig.getUseCategory().equals("Y")){
		System.out.println("boardConfig.getUseCategory() === " + boardConfig.getUseCategory());
		System.out.println("boardConfig.getBoardId() === " + boardConfig.getBoardId());
%>
		<%if(boardConfig.getBoardId()==49 || boardConfig.getBoardId()==55 || boardConfig.getBoardId()==54  || boardConfig.getBoardId()==64){//체험활동만. %>
                            <!-- tab menu -->
                            <div class="class_tab_wrap2 activity">
                                <ul>
											<li <% if( findCategory == 0 ){ %>class="on"<% } %>><a href="javascript:goCategorySearch('0');">전체</a><span class="stem"></span></li>
											<%
												int idx = 1;
											
												for(BoardCategory category : categories){ 
													if( category.getArticleCount() > 0){
											%>
											<li class="<% if( category.getCategoryId() == findCategory){ %>on<% } %>"><a href="javascript:goCategorySearch('<%=category.getCategoryId()%>');"><%=category.getName() %></a><span class="stem"></span></li>
											<% 
														idx++;
													}
												} 
											%>
                                </ul>
                                 <style>
								      table, th, td {
								        border: 1px solid #bcbcbc;
								      }
								      td{ 
								      	width: 50%;
									    height: 50px;
									    text-align: center;
									    font-weight: 400;
									    font-size: 18px;
									    color: #888888;
									    background: #f8f8f8;
									    box-sizing: border-box;
									    border-left: 1px solid #dddddd;
									    border-top: 1px solid #dddddd;
									    border-bottom: 1px solid #dddddd;
									    border-right: none
								      }
								    </style>
                                <table style="width: 100%;">
									<tr>
									    <td rowspan="3" width="25%">전체</td>
									    <td width="25%">111</td>
									    <td width="25%">111</td>
									    <td width="25%">111</td>
									  </tr>
									  <tr>
									  	<td width="25%">222</td>
									  	<td width="25%">222</td>
									  	<td width="25%">222</td>
									  </tr>
									  <tr>
									  	<td width="25%">3333</td>
									  	<td width="25%">3333</td>
									  </tr>
								</table>
							</div>
                            <!--// tab menu -->	
		<%}else{ %>
				<%if(boardConfig.getBoardId() == 62){ %>
				
					<%if(findCategory == 263){ %>
						<div class="subject_tab">
                             <ul>
								<li class="none"><a href="javascript:goCategorySearch_Sub('0');">전체</a><span class="stem"></span></li>....
								<li class="none"><a href="javascript:goCategorySearch_Sub('001');">수업자료</a><span class="stem"></span></li>
								<li class="none"><a href="javascript:goCategorySearch_Sub('002');">서식자료</a><span class="stem"></span></li>
                             </ul>
                         </div>						
					<%}else{ %>
						<div class="subject_tab">
                             <ul>
								<li class="none"><a href="javascript:goCategorySearch_Sub('0');">전체</a><span class="stem"></span></li>
								<li class="none"><a href="javascript:goCategorySearch_Sub('002');">서식자료</a><span class="stem"></span></li>
                             </ul>
                         </div>						
					<%} %>
				<%}else{%>
					<div class="subject_tab">
						<ul>
							<li class="none <% if( findCategory == 0 ){ %>on<% } %>">
								<a href="javascript:goCategorySearch('0');">전체</a>
								<span class="stem"></span>
							</li>
							<%
								int idx = 1;

								for(BoardCategory category : categories){ 
									if( category.getArticleCount() > 0){
							%>
										<li class="<% if( category.getCategoryId() == findCategory){ %>on<% } %>"><a href="javascript:goCategorySearch('<%=category.getCategoryId()%>');"><%=category.getName() %></a><span class="stem"></span></li>
							<% 
										idx++;
									}
								} 
							%>
    					</ul>
					</div>					
				<%} %>
	<%
		}
	%>	
<%} //카테고리가 있으면 %>        
                                  <div class="board_type_sel">
                                        <div class="type_sel_wrap">
                                            <a href="#none" <%if("1".equals(viewType)) %>class="on"<% %> id="gallery_type" onclick="chgviewType('1');">갤러리형</a>
                                            <a href="#none" <%if("2".equals(viewType)) %>class="on"<% %> id="list_type" onclick="chgviewType('2');">리스트형</a>
                                        </div>
                                    </div>
                                    <div class="gallery_board <%if("1".equals(viewType)) %>on<% %>">
                                        <ul>
   	<%
   		int thumbId = 0;
		if( totalArticles > 0 ){
			for(Article article : articles){
				thumbId = article.getThumbnail();
				if(thumbId>0){
	%>
                                           <li>
                                                <a href="javascript:goDetail('<%=article.getArticleId()%>' , '<%=article.getLinkUrl()%>');">
                                                    <div class="thumbnail_flip">
                                                        <div class="thumbnail front">
                                                            <div class="gal_thumb_box">
                                                                <img src="<%=article.getThumbnailPath() %>" alt="">
                                                            </div>
                                                            <div class="thumb_title_box">
                                                                <p><%=Common.cutString(article.getSubject(), 24) %></p>
                                                            </div>
                                                           <% if( boardConfig.getModifyRegDate().equals("Y")){ %>  
                                                            <div class="thumb_btm_box">
                                                                <span class="thumb_date"><%=article.getRegDateFormat() %></span>
                                                                <%-- <span class="thumb_read"><%=article.getHits() %></span> --%>
                                                            </div>
                                                              <% } %>
                                                        </div>
                                                        <div class="nothumbnail back">
                                                            <div class="nothumb_title_box">
                                                                <p><%=Common.cutString(article.getSubject(), 30) %></p>
                                                            </div>
                                                           <% if( boardConfig.getModifyRegDate().equals("Y")){ %>
                                                            <div class="nothumb_btm_box">
                                                                <span class="nothumb_date"><%=article.getRegDateFormat() %></span>
                                                                <%-- <span class="nothumb_read"><%=article.getHits() %></span> --%>
                                                            </div>
                                                            <% } %>
                                                        </div>
                                                    </div>
                                                </a>
                                            </li>	
					<%}else{ %>
                                            <li>
                                                <a href="javascript:goDetail('<%=article.getArticleId()%>' , '<%=article.getLinkUrl()%>');">
                                                    <div class="nothumbnail">
                                                        <div class="nothumb_title_box">
                                                            <p><%=Common.cutString(article.getSubject(), 16) %></p>
                                                        </div>
                                                        <div class="nothumb_btm_box">
                                                            <span class="nothumb_date"><%=article.getRegDateFormat() %></span>
                                                            <%-- <span class="nothumb_read"><%=article.getHits() %></span> --%>
                                                        </div>
                                                    </div>
                                                </a>
                                            </li>	
					<%} %>

	<% 
			}
		}
	%>
                                
                                       </ul>
                                    </div>
                                    <div class="list_board <%if("2".equals(viewType)) %>on<% %>">
                                        <table>
                                            <colgroup>
                                                <col style="width:23%">
                                                <col style="width:*">
                                                <col style="width:20%">
                                            </colgroup>
                                            <tbody>
  	<%
   		
		if( totalArticles > 0 ){
			for(Article article : articles){
				thumbId = article.getThumbnail();
				if(thumbId>0){
	%>

                                            	<tr>
                                                    <td>
                                                        <a href="javascript:goDetail('<%=article.getArticleId()%>' , '<%=article.getLinkUrl()%>');">
                                                            <div class="list_thumb_box">
                                                                <img src="<%=article.getThumbnailPath() %>" alt="">
                                                            </div>
                                                        </a>
                                                    </td>
                                                    <td class="list_title">
                                                        <a href="javascript:goDetail('<%=article.getArticleId()%>' , '<%=article.getLinkUrl()%>');"><%=article.getSubject()%></a>
                                                    </td>
                                                    <td class="rt">
                                                        <span><%=article.getRegDateFormat() %></span>
                                                        <span class="list_read"><%=article.getHits() %></span>
                                                    </td>
                                                </tr>
					<%}else{ %>
                                                <tr>
                                                    <td class="list_title" >
                                                        <a href="javascript:goDetail('<%=article.getArticleId()%>' , '<%=article.getLinkUrl()%>');"><%=article.getSubject() %></a>
                                                    </td>
                                                    <td class="rt">
                                                        <span><%=article.getRegDateFormat() %></span>
                                                        <span class="list_read"><%=article.getHits() %></span>
                                                    </td>
                                                </tr>	
					<%} %>

	<% 
			}
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
                            </div>
<!-- component: search -->
<form name="sform" action="" method="get">
	<input type="hidden" name="findCategory" value="<%=findCategory %>"/>
	<input type="hidden" name="boardType" value="<%=boardType%>"/>
	<input type="hidden" name="viewType" value="<%=viewType%>"/>
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
	<input type="hidden" name="boardType" value="<%=boardType%>"/>
	<input type="hidden" name="viewType" value="<%=viewType%>"/>
	<input type="hidden" name="sub_category"/>
</form>

<form name="listForm" action="" method="get">
	<input type="hidden" name="pg"/>
	<input type="hidden" name="boardType" value="<%=boardType%>"/>
	<input type="hidden" name="viewType" value="<%=viewType%>"/>
	<input type="hidden" name="findCategory" value="<%=findCategory%>"/>
	<input type="hidden" name="findMethod" value="<%=findMethod%>"/>
	<input type="hidden" name="findStr" value="<%=findStr%>"/>
	<input type="hidden" name="sub_category"/>
</form>

<form name="detailForm" action="" method="get">
	<input type="hidden" name="articleId"/>
	<input type="hidden" name="mode"/>
	<input type="hidden" name="boardType" value="<%=boardType%>"/>
	<input type="hidden" name="viewType" value="<%=viewType%>"/>
	<input type="hidden" name="pg" value="<%=pg %>"/>
	<input type="hidden" name="findCategory" value="<%=findCategory%>"/>
	<input type="hidden" name="findMethod" value="<%=findMethod%>"/>
	<input type="hidden" name="findStr" value="<%=findStr%>"/>
	<input type="hidden" name="sub_category"/>
</form>

<script type="text/javascript">

	function goForm()
	{	
		var f = document.forms.detailForm;
		f.mode.value = 'form';
		f.submit();
	}
	
	function goDetail(articleId , linkUrl)
	{
		if( linkUrl != '' )
			window.open(linkUrl , '' , 'scrollbars=yes,resizeable=yes,toolbar=yes');
		else
		{
			var f = document.forms.detailForm;
			f.articleId.value = articleId;
			f.mode.value = 'detail';
			f.submit();
		}
	}
	
	function goCategorySearch(findCategory)
	{
	
		var f = document.forms.categoryForm;
		f.findCategory.value = findCategory;
		f.submit();
	}
	
	function goCategorySearch_Sub(sub_category)
	{
		var f = document.forms.categoryForm;
		
		f.findCategory.value = <%=findCategory%>;
		f.sub_category.value = sub_category;
		
		f.submit();
	}
	
	
	function goSearch()
	{
		var f = document.forms.sform;
		f.submit();
	}
	
	function goList(pg)
	{
		if( pg == undefined) pg = 1;
		
		var f = document.forms.listForm;
		f.pg.value = pg;
		f.submit();
	}
	
	function chgviewType(type){
	
		document.forms.categoryForm.viewType.value = type;
		document.forms.listForm.viewType.value = type;
		document.forms.detailForm.viewType.value = type;
	
	}

</script>                                
