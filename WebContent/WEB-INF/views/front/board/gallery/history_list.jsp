<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	String findMaster               = (String) request.getAttribute("findMaster");
	String findExhibit              = (String) request.getAttribute("findExhibit");
	String findSecondCategory       = (String) request.getAttribute("findSecondCategory");
	String findMethod               = (String) request.getAttribute("findMethod");
	String findStr                  = (String) request.getAttribute("findStr");
	String paging                   = (String) request.getAttribute("paging");
	int pg                          = (Integer) request.getAttribute("pg");
	int totalCount                  = (Integer) request.getAttribute("totalCount");
	int articleNum                  = (Integer) request.getAttribute("articleNum");	
	int idx                         = 1;
	List<Category> firstCategories  = (List<Category>) request.getAttribute("firstCategories");
	List<Category> secondCategories = (List<Category>) request.getAttribute("secondCategories");
	List<Headword> headwords        = (List<Headword>) request.getAttribute("headwords");
	BoardConfig boardConfig         = (BoardConfig) request.getAttribute("boardConfig");
	boolean isBoardAccess           = (Boolean) request.getAttribute("isBoardAccess");
%>
	<div class="thumb_board_wrap">
<% 
	if( boardConfig.getUseCategory().equals("Y")){
%>
        <div class="gallery_board on">
            <ul>
<%
   		int thumbId = 0;
		if( totalCount > 0 ){
			for(Headword headword : headwords){
				thumbId = headword.getThumbnail().length();
				if(thumbId>0){
%>
               	<li>
                   	<a href="javascript:goDetail('<%=headword.getHeadwordId()%>');">
                       	<div class="thumbnail_flip">
                           	<div class="thumbnail front">
                               	<div class="gal_thumb_box">
                                   	<img src="<%="https://thub.kumsung.co.kr/"+headword.getThumbnail() %>" alt="">
                                </div>
                                <div class="thumb_title_box">
                                   	<p><%=Common.cutString(headword.getTitle(), 20) %></p>
                                </div>
                            </div>
                            <div class="nothumbnail back">
                              	<div class="nothumb_title_box">
                                   	<p><%=Common.cutString(headword.getTitle(), 20) %></p>
                                </div>
                            </div>
                        </div>
                   	</a>
                </li>	
<%				}else{ 
%>
                <li>
					<a href="javascript:goDetail('<%=headword.getHeadwordId()%>');">
						<div class="nothumbnail">
							<div class="nothumb_title_box">
								<p><%=Common.cutString(headword.getTitle(), 20) %></p>
                            </div>
                        </div>
                    </a>
                </li>	
<%				} 
%>
<%			}
		}
%>
            </ul>
<% 		if( isBoardAccess == true ){ 
%>
		<!-- 
			<div class="write_btn">
				<a href="javascript:goForm();">
					<img src="/assets/front/img/btn/btn_send.gif" alt="등록">
				</a>
			</div>
		 -->
<% 		} 
%>		            	
			<div class="new_paging_wrap">
				<%=paging %>
			</div>
		</div>
		<div class="board_search">
			<form name="sform" action="" method="get">
			<input type="hidden" name="findMaster"         value="<%=findMaster%>"/>
			<input type="hidden" name="findExhibit"        value="<%=findExhibit%>"/>
			<input type="hidden" name="findSecondCategory" value="<%=findSecondCategory%>"/>
			<span class="search01">
				<select class="jq-select" name="findMethod">
				<option value="st" <% if( findMethod.equals("st")){ %>selected<% } %>>제목</option>
				<option value="sc" <% if( findMethod.equals("sc")){ %>selected<% } %>>내용</option>	
				</select>
			</span>
			<span class="search02">
				<input type="text" class="text_bx" name="findStr" value="<%=findStr%>" OnKeyPress="fn_keydown(this)"/>
				<input type="image" src="/assets/front/img/btn/btn_search_txt.gif" alt="검색" class="search_bx" onclick="document.forms.sform.submit();"/>
			</span>
			</form>
		</div>
<%	}
%>
        
<script type="text/javascript">
	function goList(pg){
		var f = document.forms.listForm;
		f.pg.value = pg;
		f.submit();
	}
	
	function goCategorySearch(findExhibit){
		var f = document.forms.categoryForm;
		f.findExhibit.value = findExhibit;
		f.submit();
	}
	
	function goDetail(headwordId){
		var f = document.forms.detailForm;
		f.headwordId.value = headwordId;
		f.submit();
	}

	function fn_keydown(obj){
		if(event.keyCode == 13){
			document.forms.sform.submit();
		}
	}	
</script>