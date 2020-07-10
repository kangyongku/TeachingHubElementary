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
						<li>
							<a href="#n" onclick="javascript:subjectSetting('A002');" id="menu_depth_lf_02">중등</a>
						</li>
						<li>
							<a href="#n" onclick="javascript:subjectSetting('A003');" id="menu_depth_lf_03">고등</a>
						</li>
					</ul>
					<ul class="menu_depth_rt clearfix">
						<li>
							<a href="#n" class="m4">학생참여수업</a>
						</li>
						<li>
							<a href="#n" class="m5">자유학년 &middot; 창체</a>
						</li>
						<li>
							<a href="#n" class="m9">나눔 &middot; 소통</a>
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
	<div class="s-menu0 s-menu">
		<div class="s-menu0-c">
			<div class="s-menu0-c-tp">
			</div>
			<div class="s-menu0-c-bt">
				<div class="s-menu0-c-bt-tit">
					<div class="s-menu0-c-bt-tit-lf">
						<span id="grade-subj1">고등학교 영어</span>
						<a href="#n" class="a1">표지로 보기</a>
					</div>
					<div class="s-menu0-c-bt-tit-rt">
						<select name="selCourse" id="selCourse" onchange="selectedCourse(this);"  title="교육과정 선택" class="s-menu0-c-select">
							<option value="">09개정</option>
							<option value="">15개정</option>
						</select>
					</div>
				</div>
				<div class="s-menu0-c-bt-c">
					<div class="s-menu0-c-bt-lf">
						<div class="index_topic_box">
							<div class="index_topic" id="content-1">
							</div>
						</div>
					</div>
					<div class="s-menu0-c-bt-rt">
						<div class="s-menu0-c-bt-rt-c">
							<div class="img">
								<a href="#">
									<img src="/assets/front/2017img/common/sm1_img1.gif" alt="">
								</a>
							</div>
							<div class="cont">
								<p class="tit">
									<a href="#">심화 영어 독해와 작문</a>
								</p>
								<p class="name">권오랑</p>
								<p><span class="s g">09개정</span></p>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="s-menu0-c-bt2">
				<div class="s-menu0-c-bt-tit">
					<div class="s-menu0-c-bt-tit-lf">
						<span id="grade-subj2">고등학교 영어</span>
						<a href="#n" class="a2">도서명 보기</a>
					</div>
					<div class="s-menu0-c-bt-tit-rt">
						<select title="교육과정 선택" class="s-menu0-c-select">
							<option value="">09개정</option>
							<option value="">15개정</option>
						</select>
					</div>
				</div>
				<div class="s-menu0-c-bt-c2">
					<div class="content_2 content">
						<div class="images_container">
						</div>
					</div>
				</div>
			</div>
			<a href="#n" class="s_close">
				<img src="/assets/front/2017img/common/s_close.gif" alt="닫기">
			</a>
		</div>
	</div>

	<div class="s-menu4 s-menu">
		<div class="s-menu4-w clearfix">
			<div class="s-menu4-w-lf">
				<img src="/assets/front/2017img/common/sm4_img1.gif" alt="학생참여수업">
			</div>
			<div class="s-menu4-w-rt">
				<ul>
					<li class="tit">
						<a href="/participation/project.do">프로젝트 수업</a>
					</li>
					<li>
						<a href="/participation/project.do?boardType=1">프로젝트 수업이란?</a>
					</li>
					<li>
						<a href="/participation/project.do?boardType=2">수업자료</a>
					</li> 
				</ul>
				<ul>
					<li class="tit">
						<a href="/participation/reverse.do">거꾸로 수업</a>
					</li>
					<li>
						<a href="/participation/reverse.do?boardType=1">거꾸로 수업이란?</a>
					</li>
					<li>
						<a href="/participation/reverse.do?boardType=2">수업자료</a>
					</li> 
				</ul>
				<ul>
					<li class="tit">
						<a href="/participation/havruta.do">하브루타 수업</a>
					</li>
					<li>
						<a href="/participation/havruta.do?boardType=1">하브루타 수업이란?</a>
					</li>
					<li>
						<a href="/participation/havruta.do?boardType=2">수업자료</a>
					</li> 
				</ul>
				<ul>
					<li class="tit">
						<a href="/participation/vthinking.do">비주얼 씽킹 수업</a>
					</li>
					<li>
						<a href="/participation/vthinking.do?boardType=1">비주얼 씽킹 수업이란?</a>
					</li>
					<li>
						<a href="/participation/vthinking.do?boardType=2">수업자료</a>
					</li> 
				</ul>
				<ul>
					<li class="tit">
						<a href="/participation/discuss.do">토론 수업</a>
					</li>
					<li>
						<a href="/participation/discuss.do?boardType=1">토론 수업이란?</a>
					</li>
					<li>
						<a href="/participation/discuss.do?boardType=2">수업자료</a>
					</li> 
				</ul>
				<ul>
					<li class="tit">
						<a href="/participation/subject.do">범교과 주제 수업</a>
					</li>
					<li>
						<a href="/participation/subject.do?boardType=1">범교과 주제 수업이란?</a>
					</li>
					<li>
						<a href="/participation/subject.do?boardType=2">수업자료</a>
					</li> 
				</ul>
				<ul>
					<li class="tit">
						<a href="/participation/qnr.do">큐앤알 수업</a>
					</li>
					<li>
						<a href="/participation/qnr.do?boardType=1">큐앤알 수업이란?</a>
					</li>
					<li>
						<a href="/participation/qnr.do?boardType=2">수업자료</a>
					</li> 
				</ul>					
				<ul>
					<li class="tit">
						<a href="/participation/format.do">서식자료</a>
					</li>
				</ul>
			</div>
		<a href="#n" class="s_close">
			<img src="/assets/front/2017img/common/s_close.gif" alt="닫기">
		</a>
		</div>
	</div>
	<div class="s-menu5 s-menu">
		<div class="s-menu4-w clearfix">
			<div class="s-menu4-w-lf">
				<img src="/assets/front/2017img/common/sm5_img1.gif" alt="자유학년&middot;창체">
			</div>
			<div class="s-menu4-w-rt">
				<ul>
					<li class="tit">
						<a href="/freestudy/freestudy.do">자유학년제</a>
					</li>
					<li>
						<a href="/freestudy/freestudy.do?boardType=1">자유학년제란?</a>
					</li>
					<li>
						<a href="/freestudy/freestudy.do?boardType=2&findCategory=263">주제선택 활동</a>
					</li>
					<li>
						<a href="/freestudy/freestudy.do?boardType=3&findCategory=264">진로탐색 활동</a>
					</li>
					<li>
						<a href="/freestudy/freestudy.do?boardType=4&findCategory=265">예술 · 체육 활동</a>
					</li>
					<li>
						<a href="/freestudy/freestudy.do?boardType=5&findCategory=266">동아리 활동</a>
					</li>
				</ul>
				<ul>
					<li class="tit">
						<a href="/freestudy/creative.do">창의적 체험활동</a>
					</li>
					<li>
						<a href="/freestudy/creative.do?boardType=1">창의적 체험활동이란?</a>
					</li>
					<li>
						<a href="/freestudy/creative.do?boardType=2">서식자료</a>
					</li>
				</ul>
				<ul>
					<li class="tit">
						<a href="/freestudy/experience.do">체험활동 프로그램</a>
					</li>
				</ul>
			</div>
			<a href="#n" class="s_close">
				<img src="/assets/front/2017img/common/s_close.gif" alt="닫기">
			</a>
		</div>
	</div>
	<div class="s-menu9 s-menu">
		<div class="s-menu4-w clearfix">
			<div class="s-menu4-w-lf"><img src="/assets/front/2017img/common/sm6_img1.gif" alt="나눔 · 소통"></div>
			<div class="s-menu4-w-rt">
				<ul>
					<li class="tit">
						<a href="/open/share/utilize.do">티칭허브&#8314; 자료</a>
					</li>
				</ul>
				<ul>
					<li class="tit">
						<a href="/open/multimedia/list.do?findMaster=E004">멀티미디어 자료</a>
					</li>
					<li>
						<a href="/open/multimedia/list.do?findMaster=E004">이미지 자료</a>
					</li>
					<li>
						<a href="/open/multimedia/list.do?findMaster=E001">플래시 자료</a>
					</li>
					<li>
						<a href="/open/multimedia/list.do?findMaster=E002">동영상 자료</a>
					</li>
				</ul>
				<ul>
					<li class="tit">
						<a href="/happy/lecture.do">수업 활용 영상</a>
					</li>
				</ul>
				<ul>
					<li class="tit">
						<a href="/open/share/classes.do">학급 운영 자료</a>
					</li>
				</ul>
				<ul>
					<li class="tit">
						<!-- <a href="/open/outer/special_movie.do">역사전시관</a> -->
						<a href="/open/outer/re_history.do">역사전시관</a>
					</li>
					<!-- <li>
						<a href="/open/outer/special_movie.do">특별 영상 클립</a>
					</li>
					<li>
						<a href="/open/outer/re_history.do">역사 전시관</a>
					</li>
					<li>
						<a href="/open/outer/calendar.do">오늘의 역사</a>
					</li> -->
				</ul>
				<ul>
					<li class="tit">
						<a href="/open/outer/calendar.do">오늘의 역사</a>
					</li>
				</ul>
				<ul>
					<li class="tit">
						<a href="/open/share/cardnews.do">티칭허브 카드뉴스</a>
					</li>
				</ul>
				<ul>
					<li class="tit">
						<a href="/open/multimedia/links.do">유용한 링크집</a>
					</li>
				</ul>				
				
				<ul>
					<li class="tit">
						<a href="/happy/rest/eventList.do">이벤트</a>
					</li>
				</ul>
				<ul>
					<li class="tit">
						<a href="/happy/poll/pollList.do">설문조사</a>
					</li>
				</ul>
			</div>
			<a href="#n" class="s_close">
				<img src="/assets/front/2017img/common/s_close.gif" alt="닫기">
			</a>
			</div>
		</div>
	</div>
	<script type="text/javascript">

	function selectedBooksBlock(category, course){
	
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
				if( json.result == 'SUCCESS' ){
					var booktextlist = '<ul class="clearfix">\r\n';
					var bookimglist = '<ul>\r\n';
					var firstbook = '';
					$('.s-menu0-c-tp > ul > li > a').removeClass("on");
					$('.s-menu0-c-tp > ul > li[subj-id='+category+'] > a').addClass("on");
					var title = '';
					var courselist = '';

				//과정세팅
				courselist += '<select name="selCourse" id="selCourse" onchange="selectedCourse(this);" subj-category="'+category+'" title="교육과정 선택" class="s-menu0-c-select">\r\n';
				courselist +='<option value="">교육과정</option>\r\n' ;
				
				for(var j=0; j < json.courses.length; j++){
					courselist += '<option value="'+json.courses[j].course+'"';
					if(course == json.courses[j].course) courselist += ' selected';
					courselist += '>'+json.courses[j].name+'</option>\r\n' ;
				}
				courselist +='</select>\r\n';
				$('.s-menu0-c-bt-tit-rt').html(courselist);

				for(var i = 0 ; i < json.books.length ; i++ ){
					var book = json.books[i];
					if(i==0){
						firstbook += 
						'<div class="img"><a href="/learning/detail.do?category='+book.category+'&bookId='+book.bookId+'"><img src="//mall.kumsung.co.kr/prdimg/'+book.imgUrl+'" alt="" width="147" height="198" /></a></div>\r\n'+
						'<div class="cont">\r\n'+
						'<p class="tit"><a href="/learning/detail.do?category='+book.category+'&bookId='+book.bookId+'" name="txtSubject">'+book.name+'</a></p>\r\n'+
						'<p class="name" name="author">'+book.author+'</p>\r\n';

						if(book.course.indexOf("C001002")>-1){
							firstbook +='<p><span class="s g" name="revision">09개정</span></p>\r\n';
						}
						else if(book.course.indexOf("C001004")>-1){
							firstbook +='<p><span class="s r" name="revision">15개정</span></p>\r\n';
						}
						else {
							firstbook +='<p><span name="revision"></span></p>\r\n';
						}
						firstbook +=	'</div>\r\n';	
						title += book.categoryPath;
					}
					
					booktextlist += '<li><a href="/learning/detail.do?category='+book.category+'&bookId='+book.bookId+'" onmouseover="bookTitleCover(this);" data-category="'+book.category+'" data-id="'+book.bookId+'" data-cover="//mall.kumsung.co.kr/prdimg/'+book.imgUrl+'" data-subject="'+book.name+'" data-author="'+book.author+'" ';
					
					if(book.course.indexOf("C001002")>-1||book.course.indexOf("C001004")>-1){
						booktextlist += 'data-revision="'+book.courseName+'"';
					}
					booktextlist += '>'+book.name+'('+book.author+')</a>';

					if(book.course.indexOf("C001002")>-1){
						booktextlist +='<span class="s g">09개정</span>';
					}
					else if(book.course.indexOf("C001004")>-1){
						booktextlist +='<span class="s r">15개정</span>';
					}
					else{
						booktextlist +='<span></span>';
					}

					booktextlist += '</li>\r\n';	
					bookimglist +=				'<li>\r\n'+
					'<a href="/learning/detail.do?category='+book.category+'&bookId='+book.bookId+'">\r\n'+
					'<p><img src="//mall.kumsung.co.kr/prdimg/'+book.imgUrl+'" alt=""></p>\r\n';
					if(book.course.indexOf("C001002")>-1){
						bookimglist +='<span class="g2 s2">09<br>개정</span>\r\n';
					}
					else if(book.course.indexOf("C001004")>-1){
						bookimglist +='<span class="r2 s2">15<br>개정</span>\r\n';
					}	

					bookimglist		+=	'<p class="p1">'+book.name+'</p>\r\n'+
					'<p class="p2">'+book.author+'</p>\r\n'+
					'</a>\r\n'+
					'</li>\r\n';
				}
				
				booktextlist += '</ul>\r\n';	
				bookimglist += '</ul>\r\n';
				$('.s-menu0-c-bt-rt-c').html(firstbook); 
				$('#content-1').html(booktextlist); 
				$('.images_container').html(bookimglist); 
				$('#grade-subj1').text(title);
				$('#grade-subj2').text(title);

				$(".content_2 .images_container").width($(".images_container ul li").length * $(".images_container ul li").width());
				$(".mCSB_dragger").css({"left":"0"});
				$(".content_2").mCustomScrollbar("scrollTo",0);
			}
			else
				alert(json.msg);
			}
		});
	}

	function selectedCourse(obj){

		var course = obj.value;
		var category = $(obj).attr('subj-category');
		selectedBooksBlock(category, course);
		//alert("course:"+course+", category:"+category);

	}

	function subjectSetting(sel){
		$.ajax({
			url : '/api/learning/getSubjectByCategory.do' ,
			type : 'post' , 
			data : 'category=' + sel  ,
			dataType : 'json' ,
			error : function(){
				alert('서버와 통신도중 에러가 발생되었습니다.');
			},
			success : function(json){
				if( json.result == 'SUCCESS' ){
					var subjlist = '<ul>\r\n';
					var booktextlist = '<ul class="clearfix">\r\n';
					var bookimglist = '<ul>\r\n';
					var firstbook = '';
					var title = '';
					var firstcategory = '';
					var courselist = '';

					//과목세팅
					for(var i = 0 ; i < json.subjects.length ; i++ ){
						var subj = json.subjects[i];

						subjlist += '<li subj-id="'+subj.category+'">\r\n'+
						'<a href="javascript:selectedBooksBlock(\''+subj.category+'\', \'\');" '; 
						if( i == 0 ) {
							subjlist += 'class="on" ';
							firstcategory = subj.category;
						}
						subjlist += '>'+subj.name+'</a></li>\r\n';	
					}
					
					subjlist += '</ul>\r\n';
					$('.s-menu0-c-tp').html(subjlist);  

					//과정세팅
					courselist += '<select name="selCourse" id="selCourse" onchange="selectedCourse(this);" subj-category="'+firstcategory+'" title="교육과정 선택" class="s-menu0-c-select">\r\n';

					courselist +='<option value="">교육과정</option>\r\n' ;
					
					for(var j=0; j < json.courses.length; j++){                                  
						courselist += '<option value="'+json.courses[j].course+'"';
						//if(course == json.courses[j].course) courselist += ' selected';
						courselist += '>'+json.courses[j].name+'</option>\r\n' ;
					}
					courselist +='</select>\r\n';
					$('.s-menu0-c-bt-tit-rt').html(courselist);

					for(var i = 0 ; i < json.books.length ; i++ ){
						var book = json.books[i];
						if(i==0){
							firstbook += 
							'<div class="img"><a href="https://thub.kumsung.co.kr/learning/detail.do?category='+book.category+'&bookId='+book.bookId+'"><img src="//mall.kumsung.co.kr/prdimg/'+book.imgUrl+'" alt="" width="147" height="198" /></a></div>\r\n'+
							'<div class="cont">\r\n'+
							'<p class="tit"><a href="https://thub.kumsung.co.kr/learning/detail.do?category='+book.category+'&bookId='+book.bookId+'" name="txtSubject">'+book.name+'</a></p>\r\n'+
							'<p class="name" name="author">'+book.author+'</p>\r\n';

							if(book.course.indexOf("C001002")>-1){
							firstbook +='<p><span class="s g" name="revision">09개정</span></p>\r\n';
							}else if(book.course.indexOf("C001004")>-1){
							firstbook +='<p><span class="s r" name="revision">15개정</span></p>\r\n';
							}else {
							firstbook +='<p><span name="revision"></span></p>\r\n';
							}

							firstbook +=	'</div>\r\n';	
							title += book.categoryPath;
						}
						//booktextlist += '<li><a href="/learning/detail.do?category='+book.category+'&bookId='+book.bookId+'">'+book.name+'('+book.author+')</a>';
						booktextlist += '<li><a href="https://thub.kumsung.co.kr/learning/detail.do?category='+book.category+'&bookId='+book.bookId+'" onmouseover="bookTitleCover(this);" data-category="'+book.category+'" data-id="'+book.bookId+'" data-cover="//mall.kumsung.co.kr/prdimg/'+book.imgUrl+'" data-subject="'+book.name+'" data-author="'+book.author+'" ';
						
						if(book.course.indexOf("C001002")>-1||book.course.indexOf("C001004")>-1){
							booktextlist += 'data-revision="'+book.courseName+'"';
						}
						booktextlist += '>'+book.name+'('+book.author+')</a>';

						if(book.course.indexOf("C001002")>-1){
							booktextlist +='<span class="s g">09개정</span>';
						}
						else if(book.course.indexOf("C001004")>-1){
							booktextlist +='<span class="s r">15개정</span>';
						}
						else{
							booktextlist +='<span></span>';
						}

						booktextlist += '</li>\r\n';							
						bookimglist +=				'<li>\r\n'+
						'<a href="https://thub.kumsung.co.kr/learning/detail.do?category='+book.category+'&bookId='+book.bookId+'">\r\n'+
						'<p><img src="//mall.kumsung.co.kr/prdimg/'+book.imgUrl+'" alt=""></p>\r\n';
						
						if(book.course.indexOf("C001002")>-1){
							bookimglist +='<span class="g2 s2">09<br>개정</span>\r\n';
						}
						else if(book.course.indexOf("C001004")>-1){
							bookimglist +='<span class="r2 s2">15<br>개정</span>\r\n';
						}	
						
						bookimglist		+=	'<p class="p1">'+book.name+'</p>\r\n'+
						'<p class="p2">'+book.author+'</p>\r\n'+
						'</a>\r\n'+
						'</li>\r\n';
					}
					booktextlist += '</ul>\r\n';	
					bookimglist += '</ul>\r\n';
					$('.s-menu0-c-bt-rt-c').html(firstbook); 
					$('#content-1').html(booktextlist); 
					$('.images_container').html(bookimglist); 

					$('#grade-subj1').text(title);
					$('#grade-subj2').text(title);

					$(".content_2 .images_container").width($(".images_container ul li").length * $(".images_container ul li").width());
					$(".mCSB_dragger").css({"left":"0"});
					$(".content_2").mCustomScrollbar("scrollTo",0);

				}
				else
					alert(json.msg);
			}
		});
	}

	function bookTitleCover(obj) {

		var $txtbook_showbox = $(obj).closest(".s-menu0-c-bt").find(".s-menu0-c-bt-rt");
		var $showbox_left = $txtbook_showbox.find(".img");
		var $showbox_right = $txtbook_showbox.find(".cont");

		var category = $(obj).attr("data-category");
		var dataid = $(obj).attr("data-id");
		var cover = $(obj).attr("data-cover");

		var subject = $(obj).data("subject");
		var author = $(obj).data("author");
		var revision = $(obj).data("revision");
		var revision_class = $(obj).parent().find("span").attr("class");

		$showbox_left.find("a img").attr("src", cover);
		$showbox_right.find("[name=txtSubject]").html(subject);
		$showbox_right.find("[name=author]").html(author);
		$showbox_right.find(".tit a").attr("href", "/learning/detail.do?category="+category+"&bookId="+dataid);

		if (revision) {
			$showbox_right.find("[name=revision]").html(revision);
			if (revision_class) {
				$showbox_right.find("[name=revision]").attr("class", revision_class);
			}
			$showbox_right.find("[name=revision]").show();
		} 
		else {
			$showbox_right.find("[name=revision]").hide();
		}
	}
	</script>