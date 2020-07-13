<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" 			prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" 	prefix="fn"%> <!---타이틀 시작 -->
<%
	System.out.println("admin sidebar ");
	List<BoardConfig> lnbBoardList = (List<BoardConfig>) request.getAttribute("lnbBoardList");
	System.out.println("lnbBoardList : "+lnbBoardList);
	String requestURI = (String) request.getAttribute("javax.servlet.forward.request_uri");
	System.out.println("requestURI : "+requestURI);
	BoardConfig boardConfig = (BoardConfig) request.getAttribute("boardConfig");
	System.out.println("boardConfig : "+boardConfig);
	Member adminMember = (Member) session.getAttribute("adminMember");
	System.out.println("adminMember : "+adminMember);
	
	if( boardConfig == null )
		boardConfig = new BoardConfig();
	
	String active = "";
	
	if( requestURI.indexOf("/admin/smart/") >= 0 
			|| requestURI.indexOf("/admin/curriculumContact/curriculumContactList2.do") >= 0)
		active = "smart";	
	else if( requestURI.indexOf("/admin/send/") >= 0 
			|| boardConfig.getBoardFlag().equals("PDS") )
		active = "send";
	else if( requestURI.indexOf("/admin/activity/") >= 0 
			|| boardConfig.getBoardFlag().equals("CA") )
		active = "activity";
	else if( requestURI.indexOf("/admin/training/") >= 0 
			|| requestURI.indexOf("/admin/event/") >= 0
			|| boardConfig.getBoardFlag().equals("CMT") )
		active = "happy";
	else if( requestURI.indexOf("/admin/customer/") >= 0
			|| boardConfig.getBoardFlag().equals("CS") )
		active = "cs";
	else if( requestURI.indexOf("/admin/poll/") >= 0 )
		active = "poll";
	else if( requestURI.indexOf("/admin/popup/") >= 0 )
		active = "popup";
	else if( requestURI.indexOf("/admin/multimedia/") >= 0 	|| requestURI.indexOf("/admin/learning/") >= 0	|| requestURI.indexOf("/admin/curriculumContact/curriculumContactList.do") >= 0){
		active = "learning";
	}else if( requestURI.indexOf("/admin/member/") >= 0)
		active = "member";
	else if( requestURI.indexOf("/admin/sms/") >= 0)
		active = "sms";
	else if( requestURI.indexOf("/admin/stats/") >= 0)
		active = "stats";
	else if( requestURI.indexOf("/admin/board/") >= 0 && !boardConfig.getBoardFlag().equals("M1") && !boardConfig.getBoardFlag().equals("M2") && !boardConfig.getBoardFlag().equals("M3") && !boardConfig.getBoardFlag().equals("PDS"))
		active = "board";
	else if( boardConfig.getBoardFlag().equals("M1"))
		active = "m1";
	else if( boardConfig.getBoardFlag().equals("M2"))
		active = "m2";
	else if( boardConfig.getBoardFlag().equals("M3"))
		active = "m3";
	else if( requestURI.indexOf("/admin/main/") >= 0 )
		active = "main";	
	// access 권한
	System.out.println("adminsidebar active : "+active);
	String accessAuth = (adminMember.getAuthType().equals("S")) ? "main,multimedia,teacher,smart,board,pds,cmt,cs,ca,poll,member,uniform,popup,stats,sms,m1,m2,m3" : adminMember.getAccessAuth();
	System.out.println("adminsidebar accessAuth : "+accessAuth);
	pageContext.setAttribute("active",active);
	pageContext.setAttribute("accessAuth",accessAuth);
	
%>
<c:forEach var="list" items="${fn:split(accessAuth,',')}" varStatus="g">
	<c:if test="${list == active }">
		<c:out value="11111111${list}" />
	</c:if> 
<%-- <c:out value="111111111111${active}" /> --%>
</c:forEach>

<div class="sidebar-nav">
<input type="text" value="<%=requestURI %>" />
	<form class="search form-inline">
		<input type="text" placeholder="Search...">
	</form>
	<!-- 
	<a href="#dashboard-menu" class="nav-header" data-toggle="collapse"><i class="icon-dashboard"></i>Dashboard</a>
	<ul id="dashboard-menu" class="nav nav-list collapse">
		<li><a href="index.html">Home</a></li>
		<li><a href="users.html">Sample List</a></li>
		<li><a href="user.html">Sample Item</a></li>
		<li><a href="media.html">Media</a></li>
		<li><a href="calendar.html">Calendar</a></li>
	</ul>
	//-->
	<% if( accessAuth.indexOf("main") > -1){ %>
	<a href="#main-menu" class="nav-header" data-toggle="collapse"><i class="icon-th-large"></i>메인화면 관리</a>
	<ul id="main-menu" class="nav nav-list collapse <% if( active.equals("main")){%>in<% } %>">
		<li><a href="/admin/main/mainList.do">메인페이지 교과서 관리</a></li>
		<li><a href="/admin/main/banner.do?type=bestdata">이달의 추천 자료 관리</a></li>
		<li><a href="/admin/main/banner.do?type=banarea_m">우상단 배너 관리</a></li>
		<li><a href="/admin/main/banner.do?type=banarea_s">중단 배너 관리</a></li>
		<li><a href="/admin/main/banner.do?type=spcont">특별 기획전 관리</a></li>
	</ul>
	<% } %>
	<% if( accessAuth.indexOf("multimedia") > -1){ %>
	<a href="/admin/multimedia/multimediaList.do" class="nav-header">멀티미디어 관리</a>
	<% } %>
	
	<% if( accessAuth.indexOf("teacher") > -1){ %>
	<a href="#teacher-menu" class="nav-header" data-toggle="collapse">교수·학습 자료관리</a>
	<ul id="teacher-menu" class="nav nav-list collapse <% if( active.equals("learning")){%>in<% } %>">
		<li><a href="/admin/multimedia/multimediaList.do">멀티미디어 관리</a></li>
		<li><a href="/admin/learning/bookList.do">도서관리</a></li>
		<li><a href="/admin/learning/dataList.do">자료관리</a></li>
		<li><a href="/admin/curriculumContact/curriculumContactList.do">교과별담당자설정</a></li>
		<li><a href="/admin/learning/spcDataList.do">특화자료</a></li>
	</ul>
	<% } %>
	
	<% if( accessAuth.indexOf("smart") > -1){ %>
	<a href="#smart-menu" class="nav-header" data-toggle="collapse">티칭백과</a>
	<ul id="smart-menu" class="nav nav-list collapse <% if( active.equals("smart")){%>in<% } %>">
		<li><a href="/admin/smart/bookList.do">도서관리</a></li>
		<li><a href="/admin/smart/headwordList.do">표제어관리</a></li>
		<li><a href="/admin/smart/plusList.do">백과플러스</a></li>				
		<li><a href="/admin/smart/knowledgeList.do">지식나눔관리</a></li>
		<li><a href="/admin/curriculumContact/curriculumContactList2.do">교과별담당자설정</a></li>
		<li><a href="/admin/smart/commentList.do">댓글관리</a></li>		
	</ul>
	<% } %>
	
	<% if( accessAuth.indexOf("board") > -1){ %>
	<a href="#board-menu" class="nav-header" data-toggle="collapse">통합게시판관리</a>
	<ul id="board-menu" class="nav nav-list collapse <% if( active.equals("board")){%>in<% } %>">
		<li><a href="/admin/board/boardList.do">게시판 생성관리</a></li>
		<li><a href="/admin/board/commentList.do">댓글관리</a></li>		
	</ul>
	<% } %>
		<% if( accessAuth.indexOf("m1") > -1){ %>
	<a href="#m1-menu" class="nav-header" data-toggle="collapse">학생참여수업</a>
	<ul id="m1-menu" class="nav nav-list collapse <% if( active.equals("m1")){%>in<% } %>">
		<% 
			for(BoardConfig item : lnbBoardList){
				if( item.getBoardFlag().equals("M1")){
		%>
		<li><a href="/admin/board/list.do?boardId=<%=item.getBoardId()%>"><%=item.getBoardName() %></a></li>
		<% 
				}
			}
		%>

	</ul>
	<% } %>
		<% if( accessAuth.indexOf("m2") > -1){ %>
	<a href="#m2-menu" class="nav-header" data-toggle="collapse">자유학년제·창체</a>
	<ul id="m2-menu" class="nav nav-list collapse <% if( active.equals("m2")){%>in<% } %>">
		<% 
			for(BoardConfig item : lnbBoardList){
				if( item.getBoardFlag().equals("M2")){
		%>
		<li><a href="/admin/board/list.do?boardId=<%=item.getBoardId()%>"><%=item.getBoardName() %></a></li>
		<% 
				}
			}
		%>
	</ul>
	<% } %>
		<% if( accessAuth.indexOf("m3") > -1){ %>
	<a href="#m3-menu" class="nav-header" data-toggle="collapse">나눔소통</a>
	<ul id="m3-menu" class="nav nav-list collapse <% if( active.equals("m3")){%>in<% } %>">
		<% 
			for(BoardConfig item : lnbBoardList){
				if( item.getBoardFlag().equals("M3")){
		%>
		<li><a href="/admin/board/list.do?boardId=<%=item.getBoardId()%>"><%=item.getBoardName() %></a></li>
		<% 
				}
			}
		%>

	</ul>
	<% } %>
	<% if( accessAuth.indexOf("pds") > -1){ %>
	<a href="#pds-menu" class="nav-header" data-toggle="collapse">열린자료마당</a>
	<ul id="pds-menu" class="nav nav-list collapse <% if( active.equals("send")){%>in<% } %>">
		<% 
			for(BoardConfig item : lnbBoardList){
				if( item.getBoardFlag().equals("PDS")){
		%>
		<li><a href="/admin/board/list.do?boardId=<%=item.getBoardId()%>"><%=item.getBoardName() %></a></li>
		<% 
				}
			}
		%>
		<li><a href="/admin/send/list.do">회원원고관리</a></li>		
	</ul>
	<% } %>
	
	<% if( accessAuth.indexOf("ca") > -1){ %>
	<a href="#ca-menu" class="nav-header" data-toggle="collapse">창의적체험활동</a>
	<ul id="ca-menu" class="nav nav-list collapse <% if( active.equals("activity")){%>in<% } %>">
		<% 
			for(BoardConfig item : lnbBoardList){
				if( item.getBoardFlag().equals("CA")){
		%>
		<li><a href="/admin/board/list.do?boardId=<%=item.getBoardId()%>"><%=item.getBoardName() %></a></li>
		<% 
				}
			}
		%>
	</ul>
	<% } %>
	
	<% if( accessAuth.indexOf("cmt") > -1){ %>
	<a href="#cmt-menu" class="nav-header" data-toggle="collapse">선생님행복마당</a>
	<ul id="cmt-menu" class="nav nav-list collapse <% if( active.equals("happy")){%>in<% } %>"">
		<!-- <li><a href="/admin/lecture/lectureList.do">공감! 강연 관리</a></li> //-->
		<% 
			for(BoardConfig item : lnbBoardList){
				if( item.getBoardFlag().equals("CMT")){
		%>
		<li><a href="/admin/board/list.do?boardId=<%=item.getBoardId()%>"><%=item.getBoardName() %></a></li>
		<% 
				}
			}
		%>
		<li><a href="/admin/training/trainingList.do">교사연수지원관리</a></li>
		<li><a href="/admin/training/trainingPostscriptList.do">현장스케치 등록</a></li>
		<li><a href="/admin/event/eventList.do">이벤트관리</a></li>
		<li><a href="/admin/event/eventWinList.do">이벤트당첨자 등록</a></li>
		<li><a href="/admin/event/eventCommentList.do">이벤트댓글 관리</a></li>
	</ul>
	<% } %>
	
	<% if( accessAuth.indexOf("cs") > -1){ %>
	<a href="#cs-menu" class="nav-header" data-toggle="collapse">CS관리</a>
	<ul id="cs-menu" class="nav nav-list collapse <% if( active.equals("cs")){%>in<% } %>">
		<% 
			for(BoardConfig item : lnbBoardList){
				if( item.getBoardFlag().equals("CS")){
		%>
		<li><a href="/admin/board/list.do?boardId=<%=item.getBoardId()%>"><%=item.getBoardName() %></a></li>
		<% 
				}
			}
		%>
		<li><a href="/admin/customer/customerList.do">문의요청 게시판 관리</a></li>
	</ul>
	<% } %>
	
	<% if( accessAuth.indexOf("poll") > -1){ %>
	<a href="#poll-menu" class="nav-header" data-toggle="collapse">설문관리</a>
	<ul id="poll-menu" class="nav nav-list collapse <% if( active.equals("poll")){%>in<% } %>">
		<li><a href="/admin/poll/pollList.do">설문 조사 관리</a></li>
		<li><a href="/admin/poll/pollEntryList.do">설문 조사 문항 관리</a></li>
		<li><a href="/admin/poll/pollHistoryList.do">설문 조사 통계</a></li>
	</ul>
	<% } %>
	
	<% if( accessAuth.indexOf("member") > -1){ %>
	<a href="#member-menu" class="nav-header" data-toggle="collapse">회원관리</a>
	<ul id="member-menu" class="nav nav-list collapse <% if( active.equals("member")){%>in<% } %>">
		<li><a href="/admin/member/memberList.do">회원목록</a></li>
		<li><a href="/admin/member/authList.do">관리자 권한관리</a></li>
		<li><a href="/admin/member/accessList.do">접근권한 관리</a></li>
		<li><a href="/admin/member/memberAccessList.do">회원정보 접속기록 관리</a></li>
	</ul>
	<% } %>
	
	<% if( accessAuth.indexOf("stats") > -1){ %>
	<a href="#stats-menu" class="nav-header" data-toggle="collapse">통계관리</a>
	<ul id="stats-menu" class="nav nav-list collapse <% if( active.equals("stats")){%>in<% } %>">
		<li><a href="/admin/stats/memberList.do">회원통계</a></li>
		<li><a href="/admin/stats/hub/learningList.do">티칭허브 콘텐츠관리</a></li>
		<li><a href="/admin/stats/smart/gradeList.do">티칭백과 콘텐츠관리</a></li>
		<li><a href="/admin/stats/cs/requestList.do">CS 관리</a></li>
		<li><a href="/admin/stats/setting/gradeList.do">교과설정관리</a></li>
		<li><a href="/admin/stats/searchList.do">검색어관리</a></li>
		<li><a href="/admin/stats/sdfdownList.do">저작도구관리</a></li>
	</ul>
	<% } %>
	
	<% if( accessAuth.indexOf("sms") > -1){ %>
	<a href="#sms-menu" class="nav-header" data-toggle="collapse">SMS관리</a>
	<ul id="sms-menu" class="nav nav-list collapse <% if( active.equals("sms")){%>in<% } %>">
		<li><a href="/admin/sms/single.do">개별 SMS발송</a></li>
		<li><a href="/admin/sms/form.do">단체 SMS발송</a></li>
		<li><a href="/admin/sms/list.do">SMS발송 목록</a></li>
	</ul>
	<% } %>
	
	<% if( accessAuth.indexOf("uniform") > -1){ %>
	<a href="/admin/manager/uniformCode.do" class="nav-header">통합코드관리</a>
	<% } %>
	
	<% if( accessAuth.indexOf("popup") > -1){ %>
	<a href="#popup-menu" class="nav-header" data-toggle="collapse">팝업 관리</a>
	<ul id="popup-menu" class="nav nav-list collapse <% if( active.equals("popup")){%>in<% } %>">
		<li><a href="/admin/popup/popupList.do">팝업 목록</a></li>
	</ul>
	<% } %>
</div>