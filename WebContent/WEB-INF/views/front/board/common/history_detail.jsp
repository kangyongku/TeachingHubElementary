<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	String boardSkinPath                     = (String) request.getAttribute("boardSkinPath");
	int boardType                            = Common.getParameter(request, "boardType", 1);
	BoardConfig boardConfig                  = (BoardConfig) request.getAttribute("boardConfig");
	String findMaster 			             = (String) request.getAttribute("findMaster");
	String findExhibit                       = (String) request.getAttribute("findExhibit");
	String findSecondCategory                = (String) request.getAttribute("findSecondCategory");
	String findMethod                        = (String) request.getAttribute("findMethod");
	String findStr                           = (String) request.getAttribute("findStr");
	Headword prevHeadword                    = (Headword) request.getAttribute("prevHeadword");
	Headword nextHeadword                    = (Headword) request.getAttribute("nextHeadword");
	Headword headword                        = (Headword) request.getAttribute("headword");
	int pg                          		 = (Integer) request.getAttribute("pg");
	List<RelationHeadword> relationHeadwords = (List<RelationHeadword>) request.getAttribute("relationHeadwords");
	
	Member member = (Member) session.getAttribute("member");
	if( member == null ) member = new Member();
	
	boolean goLogin = false;
	if( Validate.isEmpty(member.getUserId())){
		goLogin = true;
	}
		
%>
<% if( goLogin == false ){ %>
<div class="view_wrap">
	<div class="view_container">
		<div class="view_title_box">
			<div class="view_title">
				<%=headword.getTitle()%>
				<div class="sns_btn">
					<a href="javascript:share('twitter');"><img src="/assets/front/2017img/btn/twitter.png" alt="트위터" /></a>
					<a href="javascript:share('facebook');"><img src="/assets/front/2017img/btn/facebook.png" alt="페이스북" /></a>
					<a href="javascript:copy();" class="btn urlBtn">URL 복사</a>
				</div>
			</div>
			<div class="view_sub">작성자
				<span><%=headword.getUserName() %>(<%=headword.getRegId() %>)</span>
				조회수
				<span><%=headword.getHits() %></span>
				등록일
				<span><%=headword.getRegDateFormatted() %></span>
				분류 
				<span id="view_sub_last"><%=headword.getCategoryPath() %></span>
			</div>
		</div>
		<div class="view_contents">
			<%=headword.getContents()%>
		</div>
	</div>
	<div class="view_btn_box">
		<div class="left_btn">
			<a href="javascript:goPrint();" class="btn printBtn">
				<span>인쇄</span>
			</a>
			<!-- 
			<a href="javascript:scrap('ARTICLE','<%=headword.getRegId() %>');" class="btn scrapBtn">
				<span>스크랩</span>
			</a>
			 -->
		</div>
		<div class="right_btn">
			<a href="javascript:goList();" class="btn biglistBtn pink">목록</a>
		</div>
		<div class="move_box">
<% if( prevHeadword != null){ %>
        	<div class="move_prev">
            	<span>다음글</span>
				<a href="javascript:goDetail('<%=prevHeadword.getHeadwordId()%>');"><%=prevHeadword.getTitle() %></a>
			</div>
<% } %>  
<% if( nextHeadword != null){ %>
            <div class="move_next">
            	<span>이전글</span>
				<a href="javascript:goDetail('<%=nextHeadword.getHeadwordId()%>');"><%=nextHeadword.getTitle() %></a>
            </div>
<% } %>
		</div>         		
	</div>
</div>

<form name="listForm" action="re_history.do" method="get">
	<input type="hidden" name="pg"          value="<%=pg%>"/>
	<input type="hidden" name="findMaster"  value="<%=findMaster%>"/>
	<input type="hidden" name="findExhibit" value="<%=findExhibit%>"/>
</form>

<form name="detailForm" action="" method="get">
	<input type="hidden" name="headwordId"/>
	<input type="hidden" name="pg"           		value="<%=pg%>"/>
	<input type="hidden" name="findMethod"   		value="<%=findMethod%>"/>
	<input type="hidden" name="findStr"      		value="<%=findStr%>"/>
	<input type="hidden" name="findMaster"   		value="<%=findMaster%>"/>
	<input type="hidden" name="findExhibit"  		value="<%=findExhibit%>"/>
	<input type="hidden" name="findSecondCategory"  value="<%=findSecondCategory%>"/>
</form>
<% } %>
<script type="text/javascript">
<!--
	<% if( goLogin == true ){ %>
	$(document).ready(function(){
		goLoginE();
	});
	<% } %>
	
	function goDetail(articleId){
		var f = document.forms.detailForm;
		f.headwordId.value = articleId;
		f.submit();
	}
	
	function goList(){
		var f = document.forms.listForm;
		f.submit();
	}
	
	
	function goPrint(){
	    if( navigator.userAgent.indexOf("MSIE") > 0 ){
	    	var OLECMDID = 7;
	        /* OLECMDID values:
	        * 6 - print
	        * 7 - print preview
	        * 1 - open window
	        * 4 - Save As
	        */
	        var PROMPT = 1; // 2 DONTPROMPTUSER 
	        var WebBrowser = '<OBJECT ID="WebBrowser1" WIDTH=0 HEIGHT=0 CLASSID="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2"></OBJECT>';
	        document.body.insertAdjacentHTML('beforeEnd', WebBrowser);
	        WebBrowser1.ExecWB( OLECMDID, PROMPT);
	        WebBrowser1.outerHTML = "";
	      } else if( navigator.userAgent.indexOf("Chrome") > 0){
	        window.print();    	
	    }
	}
	function goLoginE(){
		var cur_url = location.href;
	
		cur_url = cur_url.replace("http:", "https:");
	
		//location.href='https://cs.kumsung.co.kr/index.do?returnUrl=' + location.href;
		location.href='http://cs.kumsung.co.kr/index.do?returnUrl=' + cur_url;
		//location.href='http://cs.kumsung.co.kr/index.do?returnUrl=https://thub.kumsung.co.kr';
	}
	//-->
</script>