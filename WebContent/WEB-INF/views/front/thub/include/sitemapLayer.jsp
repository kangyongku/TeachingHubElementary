<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	List<Category> elSubjects = (List<Category>) request.getAttribute("elSubjects");
	List<Category> mdSubjects = (List<Category>) request.getAttribute("mdSubjects");
	List<Category> hgSubjects = (List<Category>) request.getAttribute("hgSubjects");

	List<Book> elBooks = (List<Book>) request.getAttribute("elBooks");
	List<Book> mdBooks = (List<Book>) request.getAttribute("mdBooks");
	List<Book> hgBooks = (List<Book>) request.getAttribute("hgBooks"); 

	Member member = (Member) session.getAttribute("member");
	if( member == null ) member = new Member(); 
%>
<div class="pop_menu_bg">
	<ul class="pop_menu_tab3 clearfix">
		<li>
			<a href="#n" class="bg1">초등교과</a>
			<div class="ptab_con ptab_con1">
				<ul class="class clearfix">
			<%
				int i = 0; int j = 0;
				for(Category category : elSubjects){
			%>		
					<li id="sitemap-<%=category.getCategory() %>">
						<a href="javascript:sitemapSubj('<%=category.getCategory() %>', '');" <%if(i==0){ %>class="on"<%} %>><%=category.getName() %></a>
			<%		

				if(i==0){
					for(Book book : elBooks){
						if(j==0){
			%>
						<div class="class_con class2">
							<ul class="clearfix">
			<%			} %>            
								<li>
									<a href="/learning/detail.do?category=<%=book.getCategory()%>&bookId=<%=book.getBookId()%>"><%=book.getName() %> (<%=book.getAuthor()%>)
									<%if(book.getCourse().indexOf("C001002")>-1){ %>
										<span><%=book.getCourseName() %></span>
									<%}else if(book.getCourse().indexOf("C001004")>-1){ %>
										<span class="r"><%=book.getCourseName() %></span>
									<%} %>
									</a>
								</li>
			<%
						j=1;
					}
			%>
							</ul>
						</div>
		</li>
				<%}else{%>
					<div class="class_con class2"></div>
				<%}
					i=1;
				}
				%> 
				</ul>
			</div>
		</li>
		<li>
			<a href="#n" class="bg2">중등교과</a>
			<div class="ptab_con ptab_con2">
				<ul class="class clearfix">
				<% i = 0;  j = 0;
					for(Category category : mdSubjects){
				%>		
					<li id="sitemap-<%=category.getCategory() %>">
						<a href="javascript:sitemapSubj('<%=category.getCategory() %>', '');" <%if(i==0){ %>class="on"<%} %>><%=category.getName() %></a>
				<%					
						if(i==0){
							for(Book book : mdBooks){
								if(j==0){
				%>
							<div class="class_con class2">
								<ul class="clearfix">
								<%} %>            
									<li>
										<a href="/learning/detail.do?category=<%=book.getCategory()%>&bookId=<%=book.getBookId()%>"><%=book.getName() %> (<%=book.getAuthor()%>)
										<%if(book.getCourse().indexOf("C001002")>-1){ %>
											<span><%=book.getCourseName() %></span>
										<%}else if(book.getCourse().indexOf("C001004")>-1){ %>
											<span class="r"><%=book.getCourseName() %></span>
										<%} %>
										</a>
									</li>
								<%j=1;
							}
				%>
								</ul>
						</div>
					</li>
				<%}else{%>
				<div class="class_con class2"></div>
				<%}
					i=1;
					}
				%> 
				</ul>
			</div>
		</li>
		<li>
			<a href="#n" class="bg3 on">고등교과</a>
			<div class="ptab_con ptab_con3">
				<ul class="class clearfix">
			<% i = 0;  j = 0;
				for(Category category : hgSubjects){
			%>		
					<li id="sitemap-<%=category.getCategory() %>">
						<a href="javascript:sitemapSubj('<%=category.getCategory() %>', '');" <%if(i==0){ %>class="on"<%} %>><%=category.getName() %></a>
			<%					
					if(i==0){
						for(Book book : hgBooks){
							if(j==0){
			%>
							<div class="class_con class2">
								<ul class="clearfix">
			<%				} 
			%>            
									<li>
										<a href="/learning/detail.do?category=<%=book.getCategory()%>&bookId=<%=book.getBookId()%>"><%=book.getName() %> (<%=book.getAuthor()%>)
										<%if(book.getCourse().indexOf("C001002")>-1){ %>
											<span><%=book.getCourseName() %></span>
										<%}else if(book.getCourse().indexOf("C001004")>-1){ %>
											<span class="r"><%=book.getCourseName() %></span>
										<%} %>
										</a>
									</li>
		<%
							j=1;
						}
		%>
								</ul>
							</div>
					</li>
		<%			}else{
		%>
						<div class="class_con class2"></div>
		<%			}
					i=1;
				}
		%> 
				</ul>
			</div>
		</li>
	</ul>
	<div class="menu_list clearfix">
		<div>
			<div class="tit_bg">
				<p>학생참여수업</p>
			</div>
			<ul class="menu_list_depth1">
				<li>
					<a href="/participation/project.do">프로젝트 수업</a>
					<ul class="menu_list_depth2">
						<li>
							<a href="/participation/project.do?boardType=1">프로젝트 수업이란?</a>
						</li>
						<li>
							<a href="/participation/project.do?boardType=2">수업자료</a>
						</li>
					</ul>
				</li>
				<li>
					<a href="/participation/reverse.do">거꾸로 수업</a>
					<ul class="menu_list_depth2">
						<li>
							<a href="/participation/reverse.do?boardType=1">거꾸로 수업이란?</a>
						</li>
						<li>
							<a href="/participation/reverse.do?boardType=2">수업자료</a>
						</li>
					</ul>
				</li>
				<li>
					<a href="/participation/havruta.do">하브루타 수업</a>
					<ul class="menu_list_depth2">
						<li>
							<a href="/participation/havruta.do?boardType=1">하브루타 수업이란?</a>
						</li>
						<li>
							<a href="/participation/havruta.do?boardType=2">수업자료</a>
						</li>
					</ul>
				</li>
				<li>
					<a href="/participation/vthinking.do">비주얼 씽킹 수업</a>
					<ul class="menu_list_depth2">
						<li>
							<a href="/participation/vthinking.do?boardType=1">비주얼 씽킹 수업이란?</a>
						</li>
						<li>
							<a href="/participation/vthinking.do?boardType=2">수업자료</a>
						</li>
					</ul>
				</li>
				<li>
					<a href="/participation/discuss.do">토론 수업</a>
					<ul class="menu_list_depth2">
						<li>
							<a href="/participation/discuss.do?boardType=1">토론 수업이란?</a>
						</li>
						<li>
							<a href="/participation/discuss.do?boardType=2">수업자료</a>
						</li>
					</ul>
				</li>
				<li>
					<a href="/participation/subject.do">범교과 주제 수업</a>
					<ul class="menu_list_depth2">
						<li>
							<a href="/participation/subject.do?boardType=1">범교과 주제 수업이란?</a>
						</li>
						<li>
							<a href="/participation/subject.do?boardType=2">수업자료</a>
						</li>
					</ul>
				</li>
				<li>
					<a href="/participation/qnr.do">큐앤알 수업</a>
					<ul class="menu_list_depth2">
						<li>
							<a href="/participation/qnr.do?boardType=1">큐앤알 수업이란?</a>
						</li>
						<li>
							<a href="/participation/qnr.do?boardType=2">수업자료</a>
						</li>
					</ul>
				</li>				
				<li>
					<a href="/participation/format.do">서식자료</a>
				</li>
			</ul>
		</div>
		<div>
			<div class="tit_bg">
				<p>자유학년&middot;창체</p>
			</div>
			<ul class="menu_list_depth1">
				<li>
					<a href="/freestudy/freestudy.do">자유학년제</a>
				</li>
				<li>
					<a href="/freestudy/creative.do?boardType=1">창의적 체험활동</a>
				</li>
				<li>
					<a href="/freestudy/experience.do">체험활동 프로그램</a>
				</li>
			</ul>
		</div>
		<div>
			<div class="tit_bg">
				<p>나눔&middot;소통</p>
			</div>
			<ul class="menu_list_depth1">
				<li>
					<a href="/open/share/utilize.do">티칭허브&#8314; 자료</a>
				</li>
				<li>
					<a href="/open/multimedia/list.do?findMaster=E004">멀티미디어 자료</a>
					<ul class="menu_list_depth2">
						<li>
							<a href="/open/multimedia/list.do?findMaster=E004">이미지자료</a>
						</li>
						<li>
							<a href="/open/multimedia/list.do?findMaster=E001">플래시자료</a>
						</li>
						<li>
							<a href="/open/multimedia/list.do?findMaster=E002">동영상자료</a>
						</li>
					</ul>
				</li>
				<li>
					<a href="/happy/lecture.do">수업 활용 영상</a>
				</li>
				<li>
					<a href="/open/share/classes.do">학급 운영 자료</a>
				</li>
				<!-- <li>
					<a href="/open/outer/special_movie.do">역사 자료 드림 센터</a>
					<ul class="menu_list_depth2">
						<li>
							<a href="/open/outer/special_movie.do">특별 영상 클립</a>
						</li>
						<li>
							<a href="/open/outer/re_history.do">역사 전시관</a>
						</li>
						<li>
							<a href="/open/outer/calendar.do">오늘의 역사</a>
						</li>
					</ul>
				</li> -->
				<li>
					<a href="/open/outer/re_history.do">역사 전시관</a>
				</li>
				<li>
					<a href="/open/outer/calendar.do">오늘의 역사</a>
				</li>
				
				<li>
					<a href="/open/share/cardnews.do">티칭허브 카드뉴스</a>
				</li>
				<li>
					<a href="/open/multimedia/links.do">유용한 링크집</a>
				</li>				
				<li>
					<a href="/happy/rest/eventList.do">이벤트</a>
				</li>
				<li>
					<a href="/happy/poll/pollList.do">설문조사</a>
				</li>
			</ul>
		</div>
	</div>
	<div class="menu_list clearfix">
		<div>
			<div class="tit_bg">
				<p class="none">고객센터</p>
			</div>
			<ul class="menu_list_depth1">
				<li>
					<a href="/customer/usage/usage1.do">이용안내</a>
				</li>
				<li>
					<a href="/customer/notice.do">공지사항</a>
				</li>
				<li>
					<a href="/customer/faq.do">자주묻는 질문</a>
				</li>
				<li>
					<a href="/customer/customer.do?boardCategory=C003001">고객문의</a>
				</li>
				<li>
					<a href="/customer/customer.do?boardCategory=C003002">교과서 자료요청</a>
				</li>
				<li>
					<a href="/customer/customer.do?boardCategory=C003003">교과서 내용문의</a>
				</li>
				<li>
					<a href="/customer/programs.do">프로그램 안내</a>
				</li>
				<li>
					<a href="/customer/tel.do">문의전화</a>
				</li>
			</ul>
		</div>
		<div>
			<div class="tit_bg">
				<p class="none">맴버십</p>
			</div>
			<ul class="menu_list_depth1">
				<li>
					<a href="javascript:document.joinForm.submit();">회원가입</a>
				</li>
				<li>
					<a href="http://cs.kumsung.co.kr/member/common/idPw.do" target="_blank">아이디/비밀번호 찾기</a>
				</li>
			</ul>
		</div>
		<div>
			<div class="tit_bg">
				<p class="none">회원정책</p>
			</div>
			<ul class="menu_list_depth1">
				<li>
					<a href="http://cs.kumsung.co.kr/member/common/agreement.do" target="_blank">사이트 이용약관</a>
				</li>
				<li>
					<a href="http://cs.kumsung.co.kr/member/common/personal.do" target="_blank">개인정보취급방침</a>
				</li>
			</ul>
		</div>
	</div>
</div>
<script type="text/javascript">
	<!--          

	function sitemapSubj(category, course)
	{

	if(course=='undefined') course = '';

	$.ajax({
	url : '/api/learning/getBooksByCategory.do' ,
	type : 'post' , 
	data : 'category=' + category +'&course='+course ,
	dataType : 'json' ,
	error : function(){
	alert('서버와 통신도중 에러가 발생되었습니다.');
	},
	success : function(json){
	if( json.result == 'SUCCESS' )
	{

	var appendStr = '<ul class="clearfix">\r\n';
	var imgStr = '';

	// add elemebts
	var loopCnt = json.books.length;

	for(var i = 0 ; i < loopCnt ; i++ )
	{
	var book = json.books[i];



	appendStr += '<li><a href="/learning/detail.do?category='+book.category+'&bookId='+book.bookId+'">'+book.name+' ('+book.author+')';
	if(book.course.indexOf("C001002")>-1){
	appendStr += '<span>'+book.courseName+'</span>';
	}else if(book.course.indexOf("C001004")>-1){
	appendStr += '<span class="r">'+book.courseName+'</span>';
	}


	appendStr += '</a></li>\r\n';


	}
	appendStr += '</ul>\r\n';

	$('#sitemap-' + category).find('.class2').html(appendStr);  



	}
	else
	alert(json.msg);
	}

	});

	}


	//-->
</script>		