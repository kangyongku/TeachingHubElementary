<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	// get category 
	List<Category> elSubjects = (List<Category>) request.getAttribute("elSubjects");
	List<Category> mdSubjects = (List<Category>) request.getAttribute("mdSubjects");
	List<Category> hgSubjects = (List<Category>) request.getAttribute("hgSubjects");

	List<Book> elBooks = (List<Book>) request.getAttribute("elBooks");
	List<Book> mdBooks = (List<Book>) request.getAttribute("mdBooks");
	List<Book> hgBooks = (List<Book>) request.getAttribute("hgBooks"); 

	Member member = (Member) session.getAttribute("member");
	if( member == null ) member = new Member(); 
	List<ChoiceBook> globalMyBooks = (List<ChoiceBook>) request.getAttribute("globalMyBooks");

	String csID = (String) session.getAttribute("csID");
	if( Validate.isEmpty(member.getUserId())&&"N".equals(csID)){
		//로그인 세션 없고 허브권한 획득하지 못한 경우.
		session.setAttribute("csID", "");
		out.print("<script>alert('교사 인증을 완료한 교사 회원만 이용가능 합니다.');");
		out.print("location.href='http://cs.kumsung.co.kr/member/mypage//myInfo.do';</script>");
	}
%>
	<form name="joinForm" action="https://cs.kumsung.co.kr/member/join/joinAgree.do" method="post">
		<input type="hidden" name="memberType" value="TEACHER" />
	</form>
	<div id="header">
		<div class="header_tp_w">
			<div class="header_tp width1660">
				<div class="clearfix">
					<ul class="header_ul1 clearfix">
						<li>
							<a href="http://www.kumsung.co.kr/main.jsp" target="_blank">금성출판사</a>
						</li>
						<li>
							<a href="https://dic.kumsung.co.kr/web/main.do" target="_blank">티칭백과</a>
						</li>
						<li>
							<a href="https://mall.kumsung.co.kr/index.do" target="_blank">금성도서몰</a>
						</li>
						<li>
							<a href="https://text.kumsung.co.kr/" target="_blank">금성교과서</a>
						</li>
						<li>
							<a href="http://purunet.kumsung.co.kr/index.jsp" target="_blank">푸르넷공부방</a>
						</li>
						<li>
							<a href="http://newtext.kumsung.co.kr" target="_blank"><img src="/assets/front/2017img/main/main02.gif" alt="2015년 개정교과서"></a>
						</li>
					</ul>
					<ul class="header_ul2 clearfix">
						<% if( Validate.isEmpty(member.getUserId())){ %>   
						<li class="bg li1">
							<a href="javascript:document.joinForm.submit();">회원가입</a>
						</li>
						<% }else{ %>   
						<li class="bg li2">
							<a href="/myhub/mysetting.do" class="on">마이허브</a>
						</li>
						<% } %>   
						<li class="bg">
							<a href="/customer/usage/usage1.do">고객센터</a>
						</li>
							<!-- 201909 추가  : 기존 사이트맵 pooup 연결하면 됩니다. -->
							<li class="nobg">
						
                                <a href="#" class="all_menu">사이트맵  </a>
            					<div class="pop_menu">
									<p class="tit">사이트맵
										<a href="#n" class="m_close">
										<img src="/assets/front/2017img/main/m_close.gif" alt="닫기">
										</a>
									</p>
									<tiles:insertDefinition name="sitemapLayer"/>  
								</div>
                            </li>
							<!-- 201909 추가 -->						
						<li class="pe">
							<a href="http://blog.naver.com/teachinghub" target="_blank">
								<img src="/assets/front/2019img/main/main03.png" alt="블로그">
							</a>
						</li>
						<li class="pe">
							<a href="https://www.facebook.com/thub01/" target="_blank">
								<img src="/assets/front/2019img/main/main04.png" alt="페이스북">
							</a>
						</li>
						<li class="pe">
							<a href="https://story.kakao.com/ch/thub/app" target="_blank">
								<img src="/assets/front/2019img/main/main05.png" alt="메신저">
							</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="header_bt">
			<div class="header_bt-c clearfix">
				<h1>
					<a href="https://thub.kumsung.co.kr/main.do">
						<img src="/assets/front/2017img/main/logo.gif" alt="티칭허브">
					</a>
				</h1>
				<div class="menu_depth clearfix">
					<ul class="menu_depth_lf clearfix">
						<li>
							<a href="/elementary/main.do" id="menu_depth_lf_01">초등</a>
						</li>
					</ul>
					<ul class="menu_depth_rt clearfix">
						<li>
							<a href="#n" class="m1">교과활동</a>
						</li>
						<li>
							<a href="/elementary/calendar.do" class="m2">창의적 체험활통</a>
						</li>
						<li>
							<a href="/happy/camping.do" class="m3">쉬는 시간</a>
						</li>
						<li>
							<a href="#n" class="m4">평가 자료</a>
						</li>
						<li>
							<a href="/open/utilize.do" class="m5">정보 &middot; 소통</a>
						</li>
					</ul>
				</div>
				<ul class="all clearfix">
				<!-- 201909 변경 -->
	            	<li>
					<form name="mainSearchForm" action="/search/list.do" method="get">
						<div class="search_new">
							<input type="text" name="findSearchStr" title="검색" id="my_search" placeholder="Search" />&nbsp;
							<p class="btn_s">
							<img src="/assets/front/2019img/main/btn_search_off.png" alt="검색"  onclick="document.forms.mainSearchForm.submit();">
							</p>
						</div>
					</form>
	            	</li>
				<!-- 201909 변경 -->			
				</ul>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	</script>