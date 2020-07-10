<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>

<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	List<History> calendarList = (List<History>) request.getAttribute("calendarList"); 
	int todayMonth = (Integer) request.getAttribute("todayMonth");
	int todayDay = (Integer) request.getAttribute("todayDay");

	String findCategory = (String)request.getAttribute("findCategory");
	Member member = (Member) session.getAttribute("member");
	if( member == null ) member = new Member();
	List<ChoiceBook> globalMyBooks = (List<ChoiceBook>) request.getAttribute("globalMyBooks");
	List<Article> notices = (List<Article>) request.getAttribute("notices");
	List<MainData> bestdata = (List<MainData>) request.getAttribute("bestdata");
	List<MainData> banarea_m = (List<MainData>) request.getAttribute("banarea_m");
	List<MainData> banarea_s = (List<MainData>) request.getAttribute("banarea_s");
	List<MainData> spcont = (List<MainData>) request.getAttribute("spcont");
	
 	Cookie[] cookies = request.getCookies();		// popup을 위한 쿠키
	List<Popup> popupList = (List<Popup>) request.getAttribute("popupList");
	//String paging = (String) request.getAttribute("paging");
	//int idx = 0;	
%>      
                 <div class="in_container">
             
				  <!--POPUP:  layerpopup 일 경우에만 layer 클래스 추가 -->
				<% 
						for(Popup popup : popupList){
							if(popup.getType().equals("L")){
								if( Validate.isEmpty(Common.getCookieValue(cookies, "dic_popup_" + popup.getPopupId())) ){
				%>
				<div class="popup layer" data-popup-id="<%=popup.getPopupId() %>" style="width:<%=popup.getSizeWidth()%>px;height:<%=popup.getSizeHeight()%>px;left:<%=popup.getPositionWidth()%>px;top:<%=popup.getPositionHeight()%>px;position:absolute;z-index:90000">
				
				<!-- 에디터 입력될 컨텐츠 영역 -->
				<div class="cont">
					<%=popup.getContents() %>
				</div>
				<!--// 에디터 입력될 컨텐츠 영역 -->
				<!--// 임시 숨김 2020.04.06 -->
				<!--
				<div class="controls">
					<input type="checkbox" name="close_popup" data-popup-id="<%=popup.getPopupId() %>" value="<%=popup.getPopupId()%>"/>
					<p>오늘 하루 이창을 열지 않음</p>
					<a href="#" class="btn_popup_close" data-popup-id="<%=popup.getPopupId() %>"><img src="/assets/front/dic/img/btn/popup_close.png" alt="창닫기" /></a>
				</div>
				-->
				  </div>
				  <% 
								}
							}
						} 
				%>	
				<!-- 온라인e-book 서비스 안내 20200403 -->
					<div id="online_edu" style="position:absolute;top:0;left:0;z-index:2100000"><img src="/assets/front/imgs/ebookguide2.gif" alt="" usemap="#Map">
						<map name="Map">
							
							<!-- 레이어팝업닫기 -->
							<area href="javascript:do_close();" shape="rect" coords="490,3,515,29">
							<!-- 초등학교 -->
							<area href="http://bit.ly/2VX1vnq" shape="rect" coords="175,308,262,347" target="_blank">
							<!-- 중학교 -->
							<area href="http://bit.ly/2VUoqiX" shape="rect" coords="280,310,368,346" target="_blank">
							<!-- 고등학교 -->
							<area href="http://bit.ly/2IwfOap" shape="rect" coords="382,309,471,346" target="_blank">
							
							<!-- 학생활동자료: 추후 자료등록후 URL 전달 예정 -->
							<area href="javascript:goURL(1);" shape="rect" coords="175,363,263,400">
							<area href="javascript:goURL(2);" shape="rect" coords="282,363,367,395">
							<area href="javascript:goURL(3);" shape="rect" coords="384,363,471,399">
							
							<!-- 교수학습자료 -->
							<area href="https://bit.ly/2Ut5mHr" shape="rect" coords="174,414,262,451" target="_blank">
							<area href="https://bit.ly/2Ut5mHr" shape="rect" coords="281,412,368,449" target="_blank">
							<area href="https://bit.ly/2Ut5mHr" shape="rect" coords="383,413,470,452" target="_blank">
							
							
							<area href="https://thub.kumsung.co.kr/customer/notice.do?articleId=5780&amp;mode=detail" shape="rect" coords="249,502,335,522" target="_blank">
						</map>
					</div>
					<!-- 온라인e-book 서비스 안내 20200403-->
             		<!-- 초등학교 차시별 수업자료 20200416 -->
					<div id="cho_chasi" style="position:absolute;top:0;right:0;z-index:2000000;width:467px;height:291px;"><img src="https://thub.kumsung.co.kr//assets/front/imgs/mainban_eleadd.gif" alt="" usemap="#Map2">
						<map name="Map2">
							<!-- 닫기버튼 -->
							<area shape="rect" coords="440,7,462,26" href="javascript:do_close1();">
							
							<!-- 바로가기 -->
							<area shape="rect" coords="155,217,300,257" href="https://bit.ly/2K6dmYZ">
						</map>
					</div>
					<!-- 초등학교 차시별 수업자료 20200416 -->
					
                      	<div class="cont1 box">
							<div class="mybook">
								<h3 class="m">내 교과서</h3>
								<div class="setting"><a href="/myhub/setting.do"><img src="/assets/front/2019img/main/icn_setting.png" alt=""> 설정</a></div>
								<% if( Validate.isEmpty(member.getUserId())){ %>   
								<!-- 로그인전 -->
								<div class="offmybook">로그인 후 <span>내 교과서를 등록</span>하세요.<br>원하는 자료로 바로 이동 가능합니다. </div>
								
								<% }else{ %>  
								<!-- 로그인후 -->
								<!-- 내교과서 리스트 -->
								<%
									int gap = 0;
									if(globalMyBooks.size()>0){	
								%>

								<div class="mybooklist">
									<div class="slider autoplay">
											<%										

													for(ChoiceBook choiceBook : globalMyBooks ){ 
											%>										
										
										
										<div class="cover">
										<a href="javascript:goBookDetail('<%=choiceBook.getCategory() %>' , '<%=choiceBook.getBookId()%>');">
											<img src="//mall.kumsung.co.kr/prdimg/<%=choiceBook.getImgUrl() %>" alt="<%=choiceBook.getName() %>" width="155" height="194"></a>
														<%if(choiceBook.getCourseName().indexOf("09")>-1){ %>
															 <div class="mark_09">09<br>개정</div>                                    
														<% }else if(choiceBook.getCourseName().indexOf("15")>-1){%>
															 <div class="mark_15">15<br>개정</div>
														<% }%> 										
											
											 <div class="bookdata"  style="display:none">
											 	<div class="blc"></div>
												<div class="booklist">
													<div class="cen">
														<div class="btnadd_w"><a href="javascript:goBookDetail('<%=choiceBook.getCategory() %>' , '<%=choiceBook.getBookId()%>');">단원 자료</a></div>
														<%if(!Validate.isEmpty(choiceBook.getSpecialCd())){%>
														<div class="btnadd_w"><a href="/learning/special.do?category=<%=choiceBook.getCategory() %>&bookId=<%=choiceBook.getBookId()%>&specialCd=<%=choiceBook.getSpecialCd() %>">특화 자료</a></div>
														<%} %>
														<%if(!Validate.isEmpty(choiceBook.getTeacherCdView())){ %>
														<div class="btnadd_y"><a href="javascript:goTeacherCdView('<%=choiceBook.getTeacherCdView() %>');">DVD 보기</a></div>
														<%} %>
														<%if(!Validate.isEmpty(choiceBook.getCdLinks())){ %>
														<div class="btnadd_y"><a href="javascript:goTeacherCdDown('<%=choiceBook.getBookId()%>');">DVD 다운</a></div>
												
														<%} %>
													</div>
												</div>
											 </div>	
											 
										</div>
											<%
/* 														gap++;
														if(gap >=8){
															break;
														} */												
													} 
											%>								
									</div>
								</div>
								<!-- 내교과서리스트 끝 -->
										<%
												}else{													
										%> 		
								<div class="offmybook"><span>내 교과서를 등록</span>하세요.<br>원하는 자료로 바로 이동 가능합니다. </div>						
								<!-- 로그인후 끝 -->
								<%	} %>	
								<% } %> 
							</div>
							
							<div class="banarea_m">
								<div class="slider fade">
					<% 
					//우상단 배너
					for(MainData ban_ms : banarea_m){ 
					%>								
									<div><a href="<%=ban_ms.getdispLink()%>" target="<%=ban_ms.getdispTarget()%>"><img src="//thub.kumsung.co.kr/<%=ban_ms.getImgurl()%>" alt="<%=ban_ms.getdispSubj()%>" class="ban"></a></div>
									
					<%
					}
					%>	
								</div>
							</div>
						
						</div>
						
						<div class="cont2 box">
							
							<!-- 이달의 추천 자료 -->
							<div class="bestdata">
								<h3 class="m">이달의 추천 자료</h3>
								<ul>
					<% 
					for(MainData bestdatas : bestdata){ 
					%>
									<li>
										<a href="<%=bestdatas.getdispLink()%>" target="<%=bestdatas.getdispTarget()%>">
										<div class="thum_img"><img src="<%=bestdatas.getImgurl()%>" alt="이미지"></div>
										<div class="thum_title"><%=bestdatas.getdispSubj()%></div>
										</a>
									</li>					
					<%
					}
					%>								

								</ul>
							</div>
							
							<div class="thubmc">
								
								<!-- 오늘의 역사 -->
								<div class="historyday">
									<h3 class="m">오늘의 역사</h3>
									<p class="btnmore"><a href="/open/outer/calendar.do"><img src="/assets/front/2019img/main/btn_more.png" alt="더보기"></a></p>
									<div class="todayh">
													<div class="todaym">
											<div class="hmon"><span><%=todayMonth %></span>月</div>
											<div class="hday"><span><%=todayDay %></span>日</div>
										</div>
										<ul class="todayd">									

			<% 
				int idx = 0;
				int dow = Integer.valueOf(DateUtil.getDayOfWeek());
				String sunClass = "class='on'";
				String calHref = "";
				String calStory  = "";
				String calThum = "";
				String styleType = "block";
				StringBuffer sb = new StringBuffer();
			


				for(History calendars : calendarList){
					 //sunClass = (dow%7==1)? "class='on'": "";

			%>									
					<li><a href="javascript:chgHistory('<%=idx %>')" id="day_<%=idx %>" <%=sunClass %>><%=calendars.getHistoryDay()%></a></li>
			<% 

					 calHref = "/open/outer/calendarDetail.do?headwordId="+calendars.getHeadwordId()+"&findYear="+DateUtil.getDate("yyyy")+"&findMonth="+calendars.getHistoryMonth()+"&findDay="+calendars.getHistoryDay()+"&weekCount="+calendars.getWeekCount();
						
				
					 calStory = calendars.getHistoryYear()+"년 "+calendars.getHistoryMonth()+"월 "+calendars.getHistoryDay()+"일 "+calendars.getHistoryExp();
					 if(calStory.length() > 50) calStory = calStory.substring(0, 50);
					 calThum = calendars.getThumbnail();

					 sb.append("<div class='hissue' id='his_"+idx+"' style='display:"+styleType+"'>\n");
					sb.append("<a href=\"javascript:goHistory('"+calHref+"')\">\n");
					if( !Validate.isEmpty(calThum)){
						sb.append("<div class='hthum'><img src='//thub.kumsung.co.kr"+calThum+"' alt='오늘의 역사' width='99' height='76'></div>\n");
					}
					sb.append("<div class='hexp'>"+calStory +" </div>\n");
					sb.append("</a></div>\n");
					sunClass = "";
					styleType = "none";
					idx++;	
					dow++;

					

					
				} 
			%>											
										</ul>
									</div>
									<%=sb.toString() %>

								</div>
								
								<!-- 공지사항 -->
								<div class="notice">
									<h3 class="m">공지사항</h3>
									<p class="btnmore"><a href="/customer/notice.do"><img src="/assets/front/2019img/main/btn_more.png" alt="더보기"></a></p>
									<ul>
					<% 
					for(Article notice : notices){ 
					%>
				            <li>
                                <a href="/customer/notice.do?articleId=<%=notice.getArticleId() %>&mode=detail" title="<%=notice.getSubject()%>">
							<%
							String title = "";
							if(notice.getSubject().length() > 16){
								title = notice.getSubject().substring(0,16);//+"...";
							}else{
								title = notice.getSubject();
							}
							%>
							<%=title%>
                                </a>
                                <%if(notice.getDiffDate() > -14){ %><img src="/assets/front/2019img/main/icn_new.jpg" alt="new"><%} %>

                            </li>

					<% 
					} 
					%>
														

									</ul>
								</div>
								
								<div class="dvddata"><a href="javascript:handleCdDownload();">교사용 DVD</a></div>
							</div>
						</div>
						
						<div class="cont3 box">
							<div class="thubmc2">
								<div class="paperdata">
									<a href="#n" onclick="javascript:itemPool();">
									<h3 class="m">문제 은행</h3>
									<p>문제 유형, 난이도에<br>따른 나만의 시험지<br>제작 서비스</p>
									</a>
								</div>
								
								<div class="dicsearch">
									<a href="//dic.kumsung.co.kr/web/main.do" target="_blank">
									<h3 class="m">티칭 백과</h3>
									<p>자기주도형 교육을 위한<br>온라인 교육 백과 서비스</p>
									</a>
									<form name="dicForm" action="" method="get" onsubmit="return false;">
									<div class="search_dic">
									<input type = "hidden" name = "findSearchMethod" value="">									
									<input type="text" name = "findSearchStr" id="finddicSearch" title="검색"  placeholder="검색어를 입력하세요." />
									<p class="btn_s2">
 									<a href="javascript:dicSearch();"><img src="/assets/front/2019img/main/btn_searchs_off.png" alt="검색"  /></a> 

									</p></div>
									</form>	
								</div>


							</div>
							<div class="banarea_s">
					<% 
					//중단 배너
					for(MainData ban_ss : banarea_s){ 
					%>								
							<a href="<%=ban_ss.getdispLink()%>" target="<%=ban_ss.getdispTarget()%>"><img src="//thub.kumsung.co.kr/<%=ban_ss.getImgurl()%>" alt="<%=ban_ss.getdispSubj()%>" class="ban"></a>
									
					<%
					}
					%>	
							</div>
						</div>
						
						<div class="cont4 box">
							<div class="quickmenu_m">
								<ul>
									<li class="a1"><a href="/customer/usage/usage2.do">교사 인증하기 <img src="/assets/front/2019img/main/icn_quickm01_off.png" alt="" class="qmoff"><img src="/assets/front/2019img/main/icn_quickm01_on.png" alt="교사인증하기" class="qmon"></a></li>
									<li class="a2"><a href="/customer/usage/usage1.do">이용 안내 <img src="/assets/front/2019img/main/icn_quickm02_off.png" alt="" class="qmoff"><img src="/assets/front/2019img/main/icn_quickm02_on.png" alt="이용 안내" class="qmon"></a></li>
									<li class="a3"><a href="/customer/tel.do">문의 전화 <img src="/assets/front/2019img/main/icn_quickm03_off.png" alt="" class="qmoff"><img src="/assets/front/2019img/main/icn_quickm03_on.png" alt="문의 전화" class="qmon"></a></li>
									<li class="a4"><a href="/customer/customer.do?boardCategory=C003001"">1:1 문의 <img src="/assets/front/2019img/main/icn_quickm04_off.png" alt="" class="qmoff"><img src="/assets/front/2019img/main/icn_quickm04_on.png" alt="1:1 문의" class="qmon"></a></li>
								</ul>
							</div>
						</div>
						
						<div class="cont5 box">
							<div class="spcont">
								<h3 class="m">특별 기획전</h3>
								<ul>
					<% 
					//기획전
					for(MainData spconts : spcont){ 
					%>								
							<li><div><a href="<%=spconts.getdispLink()%>" target="<%=spconts.getdispTarget()%>"><img src="//thub.kumsung.co.kr/<%=spconts.getImgurl()%>" alt="<%=spconts.getdispSubj()%>" class="ban"></a></div></li>
									
					<%
					}
					%>									

								</ul>
							</div>
						</div>
                        
                    </div>
	 
<!-- component: layer popup -->
   <!-- 교사용 CD 다운 레이어 팝업 -->
    <div class="modal">
      <div class="modal-dim"></div>

      <div id="pop_teacher_cd_download" class="modal_popup cddownload">

      </div>
      
    </div>
    <!--// 교사용 CD 다운 레이어 팝업 -->                    
<script type="text/javascript">
<!--
	$(document).ready(function(){	
		if(device_check()) $('.bookdata').show();		
		$("#finddicSearch").keypress(function(key) {
			if (key.which == 13) {
				dicSearch();
			}
		});
	});	

	function device_check() {
	    // 디바이스 종류 설정
	    var pc_device = "win16|win32|win64|mac|macintel";
	 
	    // 접속한 디바이스 환경
	    var this_device = navigator.platform;
	 
	    if ( this_device ) {
	 
	        if ( pc_device.indexOf(navigator.platform.toLowerCase()) < 0 ) {
	            return false;
	        } else {
	            return true;
	        }
	 
	    }
	}
	function goTeacherCdView(cdLink){
		<% if( member != null && member.getIsFinallyAuth().equals("Y")){ %>
			window.open('http://thub.kumsung.co.kr/upfiles'+cdLink);
		<% }else{ %>
			alert('교사인증회원만 열람가능합니다.');
		<% } %>
	}
	
 	function goTeacherCdDown(bookId){
 		<% if( member != null && member.getIsFinallyAuth().equals("Y")){ %> 		
		$.ajax({
			  url      : '/ajax/teacherCdDown.do' 
			, type     : 'post' 
			, data     : 'bookId=' + bookId
			, dataType : 'json'
			, success  : function(json){
				if( json.result == 'SUCCESS' ){	
					var book = json.book;
					var cdes = json.cdes;
					var appendStr = '' ; 
					appendStr += '<div class="pop_header">\r\n' ;
					appendStr += ' <h5>'+book.name+'('+book.author+') '+book.courseName+' 교사용 CD 다운로드</h5>\r\n' ;
					appendStr += '  <a href="#pop_teacher_cd_download" class="btn_pop_close" onclick="modal_close($(this).attr(\'href\'));return false;"><img src="/assets/front/2017img/btn/btn_pop_close.png" alt="닫기"></a>\r\n' ;
					appendStr += '</div>\r\n' ;
					appendStr += '<div class="pop_content">\r\n' ;
					appendStr += '  <div class="textbook_area">\r\n' ;
					appendStr += '    <img src="//mall.kumsung.co.kr/prdimg/'+book.imgUrl+'" alt="'+book.name+'">\r\n' ;
					appendStr += '  </div>\r\n' ;
					appendStr += '  <div class="txt_area">\r\n' ;
					appendStr += '    <strong class="tit">'+book.name+'('+book.author+') '+book.courseName+'</strong>\r\n' ;
					appendStr += '    <div class="select_wrap">\r\n' ;
					appendStr += '      <select data-book-id="'+book.bookId+'">\r\n' ;
					for(var i=0; i<cdes.length; i++){ 
						appendStr += '<option value="'+cdes[i].cdId+'">'+cdes[i].name+'</option>\r\n' ;
					}             
					appendStr += '      </select>\r\n' ;
					appendStr += '    </div>\r\n' ;
					appendStr += '  </div>\r\n' ;
					appendStr += '  <div class="btn_wrap">\r\n' ;
					appendStr += '    <a href="javascript:getCdDownload(\''+book.bookId+'\');" class="btn downloadBtn"><span>다운로드</span></a>\r\n' ;
					appendStr += '  </div>\r\n' ;
					appendStr += '</div>\r\n' ;
			        
					$('#pop_teacher_cd_download').html(appendStr);

				}else{
					alert('오류가 발생했습니다.');
				}		
			  }
			, complete : function(){
				modal_open($('#pop_teacher_cd_download'));
			  }
		});	
		<% }else{ %>
		alert('교사인증회원만 열람가능합니다.');
		<% } %>
	} 
	function dicSearch(){
		var f = document.forms.dicForm;
		
		//if( f.findSearchStr.value.length < 2 ) return doError('검색어는 2자 이상이여야 합니다.' , 'findSearchStr' , 'dicForm');

		$.ajax({
			url : '/web/api/saveSearchWord.do',
			type : 'post' ,
			data : 'findSearchStr=' + f.findSearchStr.value ,
			dataType : 'json' ,
			complete : function(){
				f.target = '_blank';
				f.action = '/web/search.do';
				f.submit();		
			}
		});
	}
	function chgHistory(idx){
		 $("[id^='day_']").removeClass();
		 $("#day_"+idx).addClass('on');
		 $("[id^='his_']").hide();
		 $("#his_"+idx).show();
	}
	function goHistory(url){
		<% 
		if( Validate.isEmpty(member.getUserId())){ 
		%>
		location.href='http://cs.kumsung.co.kr/index.do?returnUrl=//thub.kumsung.co.kr'+url;
		<% 
		}else{ 
		%>
		location.href=url;
		<% 
		}
		%>		
	}

//-->
</script>
<script type="text/javascript">

$(document).ready(function(){
	$('.btn_popup_close').click(function(){
		var popupId = $(this).attr('data-popup-id');
		
		if( $('input[data-popup-id=' + popupId + ']').is(':checked') )
			$.cookie('dic_popup_' + popupId , 'Y' , {expires : 1});
		
		$('[data-popup-id=' + popupId + ']').hide();
	});
	
	
	//$('#newbook_layer').click(function(){
	//	$('#newbook_layer').hide(); 
	//});


	if( $('.personal_header').find('[data-my-category]:eq(0)').attr('data-my-category') != undefined
			&& $('.personal_header').find('[data-my-category]:eq(0)').attr('data-my-category') != '')
	{
		if( $('.overview').find('li').length == 0 )
			selectedMyBooksBlock($('.personal_header').find('[data-my-category]:eq(0)').attr('data-my-category'));
	}
	
	// 새창 팝업을 띄운다.
	<% 
		for(Popup popup : popupList){
			if(popup.getType().equals("P") && Validate.isEmpty(Common.getCookieValue(cookies, "dic_popup_" + popup.getPopupId()))){
	%>
	window.open('/web/popup.do?popupId=<%=popup.getPopupId()%>' , 'popup_<%=popup.getPopupId()%>' , 'width=<%=popup.getSizeWidth()%>,height=<%=popup.getSizeHeight()%>,left=<%=popup.getPositionWidth()%>,top=<%=popup.getPositionHeight()%>,scrollbars=no,statusbar=no');
	<%
			}
		}
	%>
});

</script>
<script language="javascript">
 function goURL(v){
   if(v == "1") {
  //location.href="http://thub.kumsung.co.kr/open/share/utilize.do?articleId=5782&mode=detail&boardType=2&viewType=1&pg=1&findCategory=0&findMethod=&findStr=&sub_category=";
   window.open('https://thub.kumsung.co.kr/open/share/utilize.do?articleId=5782&mode=detail&boardType=2&viewType=1&pg=1&findCategory=0&findMethod=&findStr=&sub_category=','title','height=' + screen.height + ',width=' + screen.width + 'fullscreen=yes, scrollbars=yes, menubar=yes, toolbar=yes, resizable=yes');

  }else if(v == "2") {
  //location.href="http://thub.kumsung.co.kr/open/share/utilize.do?articleId=5783&mode=detail&boardType=2&viewType=1&pg=1&findCategory=0&findMethod=&findStr=&sub_category=";
   window.open('https://thub.kumsung.co.kr/open/share/utilize.do?articleId=5783&mode=detail&boardType=2&viewType=1&pg=1&findCategory=0&findMethod=&findStr=&sub_category=','title','height=' + screen.height + ',width=' + screen.width + 'fullscreen=yes, scrollbars=yes, menubar=yes, toolbar=yes, resizable=yes');

  }else {
  //location.href="http://thub.kumsung.co.kr/open/share/utilize.do?articleId=5784&mode=detail&boardType=2&viewType=1&pg=1&findCategory=0&findMethod=&findStr=&sub_category=";
  window.open('https://thub.kumsung.co.kr/open/share/utilize.do?articleId=5784&mode=detail&boardType=2&viewType=1&pg=1&findCategory=0&findMethod=&findStr=&sub_category=','title','height=' + screen.height + ',width=' + screen.width + 'fullscreen=yes, scrollbars=yes, menubar=yes, toolbar=yes, resizable=yes');

  }
 }
 function do_close() {
	$('#online_edu').hide();
 }

 function do_close1() {
		$('#cho_chasi').hide();
	 }

</script>                    