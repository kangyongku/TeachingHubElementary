<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	int pg = (Integer) request.getAttribute("pg");
	String findCategory = (String) request.getAttribute("findCategory");
	String findSecondaryCategory = (String) request.getAttribute("findSecondaryCategory");
	String findMethod = (String) request.getAttribute("findMethod");
	String findStr = (String) request.getAttribute("findStr");	
	List<Category> firstCategories = (List<Category>) request.getAttribute("firstCategories");
	List<Category> secondCategories = (List<Category>) request.getAttribute("secondCategories");	
	int totalCount = (Integer) request.getAttribute("totalCount");
	int articleNum = (Integer) request.getAttribute("articleNum");
	List<Multimedia> list = (List<Multimedia>) request.getAttribute("list");
	String paging = (String) request.getAttribute("paging");	
	int idx = 1;			// tmp var
%>

<div id="wrap">
	<div id="container" class="container">
		<section>
			<div class="inwrap">
				<div class="inner_container">
					<tiles:insertDefinition name="elementarySidebarCommunication"/>
					<div class="sub_content" >
						<h2 class="page_title">교육 관련 추천 사이트</h2>
						<p>다양하고 풍성하게! 활용하면 좋을만한 교육 관련 다양한 참고 사이트를 안내하는 공간입니다.</p>
						<div class="class_tab_wrap2 activity5">
							<ul class="">
							<% for(Category category : firstCategories){ %>
								<li class="none <% if( category.getCategory().equals(findSecondaryCategory) ){ %>on<% } %>">
									<a href="javascript:goCategorySearch('<%=category.getCategory()%>');"><%=category.getName() %></a>
								</li>
							<% } %>
							</ul>
						</div>
						<div class="thumb_board_wrap">							
							<div class="subject_tab">
								<ul class="data_strapline">
								<% for(Category category : secondCategories){ %>
									<li class="<% if( idx == 1){ %>first<% } %> <% if( category.getCategory().equals(findCategory)){ %>on<% } %>">
										<a href="javascript:goCategorySearch('<%=category.getCategory()%>');"><%=category.getName() %></a>
									</li>
								<%
									idx++;
								} 
								%>
								</ul>
							</div>
							<ul class="list_thumb_02">
							<%
								idx = 0;							
								if( totalCount > 0 ){
									for(Multimedia multimedia : list){
							%>
								<li <% if( idx == 0){ %>class="first"<% } %>>
									<div class="list_checkbox">
										<input type="checkbox" />
									</div>
							<% 			if( !Validate.isEmpty(multimedia.getThumbnail())){ %>
									<p class="thumb">
										<a href="<%=multimedia.getDataFile()%>" target="_blank">
											<img src="<%=multimedia.getThumbnail() %>" width="120" height="90" alt="" />
										</a>
									</p>
							<% 			} %>
									<dl>
										<dt>
											<a href="<%=multimedia.getDataFile()%>" target="_blank"><%=multimedia.getTitle() %></a>
										</dt>
										<dd>
											<%=multimedia.getContents() %>
										</dd>
									</dl>
								</li>
							<%			
										idx++;
										articleNum--;
									}
								}else{
							%>
								<li class="none">등록된 내용이 없습니다.</li>
							<%
								}
							%>
							</ul>
							<div class="new_paging_wrap">
								<%=paging %>
							</div>		
							<form name="sform" action="" method="get">
								<input type="hidden" name="findCategory" value="<%=findCategory%>"/>
								<div class="board_search">
									<span class="search01">
										<select class="jq-select" name="findMethod">
											<option value="st" <% if( findMethod.equals("st")){ %>selected<% } %>>제목</option>
											<option value="sc" <% if( findMethod.equals("sc")){ %>selected<% } %>>내용</option>
										</select>
									</span>
									<span class="search02">
										<input type="text" class="text_bx" name="findStr" value="<%=findStr%>"/>
										<input type="image" src="/assets/front/img/btn/btn_search_txt.gif" alt="검색" class="search_bx"/>
									</span>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</section>
	</div>
</div>
<form name="listForm" action="" method="post">
	<input type="hidden" name="pg" value="<%=pg%>"/>
	<input type="hidden" name="findCategory" value="<%=findCategory%>"/>
	<input type="hidden" name="findMethod" value="<%=findMethod%>"/>
	<input type="hidden" name="findStr" value="<%=findStr%>"/>
</form>
<script type="text/javascript">
	function goList(pg){
		var f = document.forms.listForm;
		f.pg.value = pg;
		f.submit();
	}
	
	function goCategorySearch(findCategory){
		location.href='?findCategory=' + findCategory;
	}

	function checkLoginAndGo(url){
		$.ajax({
			  url : '/api/isLogin.do' 
			, dataType : 'json' 
			, error : function(){
				alert('서버와의 통신도중 에러가 발생되었습니다.');
			}
			, success : function(json){
				if( json.login == true ){
					location.href = url;
				}
				else{
					if( confirm('로그인 후 이용가능합니다.\r\n로그인 하시겠습니까?') ){
						goLogin();
					}					
				}
			}
		});
	}
</script>