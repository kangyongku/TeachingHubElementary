<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	int headwordId = (Integer) request.getAttribute("headwordId");
	int weekCount = (Integer) request.getAttribute("weekCount");
	String findYear = (String) request.getAttribute("findYear");
	String findMonth = (String) request.getAttribute("findMonth");
	
	Headword headword = (Headword) request.getAttribute("headword");
	
	String prevFindYear = (String) request.getAttribute("prevFindYear");
	String prevFindMonth = (String) request.getAttribute("prevFindMonth");
	String nextFindYear = (String) request.getAttribute("nextFindYear");
	String nextFindMonth = (String) request.getAttribute("nextFindMonth");
	
	int currentDay = (Integer) request.getAttribute("currentDay");
	int firstDay = (Integer) request.getAttribute("firstDay");
	int lastDay = (Integer) request.getAttribute("lastDay");
	int startWeekOfDay = (Integer) request.getAttribute("startWeekOfDay");
	int prevLastDay = (Integer) request.getAttribute("prevLastDay");
	
	List<ResultMap> calendarList = (List<ResultMap>) request.getAttribute("calendarList");
	Map<String,Object> list = (Map<String,Object>) request.getAttribute("list");
%>



 		<!-- container -->
        <div id="container" class="container">
            <section>
                <div class="inwrap">

                    <!-- <div class="location">
                        <span>
                            <img src="../image/ico/ico_location_home.png" alt="HOME">
                        </span>
                        <span>나눔·소통</span>
                        <span class="curr">인물·사건캘린더</span>
                    </div> -->

                    <div class="inner_container">
						<!-- sidebar -->
						<tiles:insertDefinition name="sidebarCommunication"/>

                        <div class="sub_content" >
                            <h2 class="page_title">인물&middot;사건 캘린더</h2>
                            <p>달력 속에서 만나는 역사 속 오늘! 달력을 넘기면서 지나간 시대의 인물과 사건을 만나는 시간 여행을 합니다.</p>
							
							 <!-- view -->
                            <div class="view_wrap">
                                
								<div class="view_container">
                                   	<div class="view_title_box">
                                        <div class="view_title">
                                            <%=headword.getTitle() %>
                                            
                                            <% if( !Validate.isEmpty(headword.getTitleEng()) || !Validate.isEmpty(headword.getTitleChi())){ %>
											<span class="info">(<%=headword.getTitleEng() %> <%=headword.getTitleChi() %>)</span>
											<% } %>
											
                                            <div class="sns_btn">
                                                <a href="javascript:share('twitter');">
                                                    <img src="/assets/front/2017img/btn/twitter.png" alt="트위터">
                                                </a>
                                                <a href="javascript:share('facebook');">
                                                    <img src="/assets/front/2017img/btn/facebook.png" alt="페이스북">
                                                </a>
                                                <a href="javascript:copy();" class="btn urlBtn">URL 복사</a>
                                            </div>
                                        </div>
                                        <div class="view_sub">
                                       		<%=headword.getSummary() %>
                                        </div>
                                    </div>
                                    
                                    <!-- component: view_area -->
									<div class="view_contents">
										<div class="txt"><%=headword.getContents() %></div>
										<%-- <ul class="view_list">
											<% if( !Validate.isEmpty(headword.getRelationBooks())){ %>
											<li>관련 교과서 : <%=headword.getRelationBooks() %></li>
											<% } %>
											<li>연계 교과서 : <%=headword.getCategoryPath() %> <%=headword.getBookName() %> &gt; <%=headword.getUnitPath() %></li>
										</ul> --%>
									</div>
                                    
                                    <!-- <div class="attach_box">
                                        <span>첨부파일</span>
                                        <a href="#none" class="attach_file">창의체험활동지_문화역사답사.hwp(25Kbytes)</a>
                                    </div> -->
                                    
                                </div>
                                
								<div class="view_btn_box">
                                    <div class="left_btn">
                                        <a class="btn printBtn" href="javascript:goPrint();">
											<span>인쇄</span>
										</a>
                                        <a href="javascript:scrap('ARTICLE','<%=headwordId%>');" class="btn scrapBtn">
                                            <span>스크랩</span>
                                        </a>
                                    </div>
                                    <div class="right_btn">
                                        
                                        <a href="javascript:goList();" class="btn biglistBtn2 pink">달력 전체보기</a>
                                    </div>
                                </div>
								
                            </div>
                            <!--// view -->
							
							<!-- 기존 캘린더 프레임 -->
							<div class="weekly_issue">
								<ul class="tabs">
									<li class="on"><a href="javascript:;">금주의 인물·사건</a><span class="stem"></span></li>
								</ul>
					
								<!-- module: calendar -->
								<div class="calendar">
									
								<table cellpadding="0" cellspacing="0" summary="" >
								<caption></caption>
								<colgroup>
								<col width="105"/>
								<col width="105"/>
								<col width="105"/>
								<col width="105"/>
								<col width="105"/>
								<col width="105"/>
								<col width="104"/>
								</colgroup>
								<tbody>
									<%
										int loopCnt = calendarList.size();
										int thisWeekCount = 0;
									
										for(int i = 0 ; i < loopCnt ; i++){
											ResultMap item = (ResultMap) calendarList.get(i);
									%>
									<% 
										if( i % 7 == 0){ 
											thisWeekCount++;
									%>
									<tr <% if( thisWeekCount != weekCount){ %>style="display:none;"<% } %>>
									<% } %>
										
										<% if( i % 7 == 0){ %>	
										<td class="first">
										<% }else if( i % 7 == 6){ %>
										<td class="last">
										<% }else{ %>
										<td>
										<% } %>
											<% if( item.getString("disabled").equals("true") ){ %>
											<div class="disable">
												<span class="date"><%=item.getInt("date") %></span>
											</div>
											<% }else{ %>
											<div <% if( item.getInt("date") == currentDay){ %>class="today"<% } %>>
												<span class="date"><%=item.getInt("date") %></span>
												<%
													String key = String.format("%s%02d" , findMonth , item.getInt("date"));	
													List<History> histories = (List<History>) list.get(key);	
												
													if( histories.size() > 0 ){
														
														// 첫번째의 데이타가 thumbnail을 가지고 있는가?
														History firstHistory = histories.get(0);
														
														if( !Validate.isEmpty(firstHistory.getThumbnail())){
												%>
														<div class="image"><img src="<%=firstHistory.getThumbnail() %>" width="81" height="63" alt="" /></div>
												<%
														}  
												%>
													<ul>
														<% for(History history : histories){ %>
														<li><a href="javascript:goDetail('<%=history.getHeadwordId()%>' , '<%=weekCount%>');"><%=history.getHistoryExp() %></a></li>
														<% } %>
													</ul>
													 
													<% if( histories.size() > 2){ %>
													<div class="more">+ 더보기</div>
													<div class="list">
														<h4>7월 4일</h4>
														<ul>
															<% for(History history : histories){ %>
															<li><a href="javascript:goDetail('<%=history.getHeadwordId()%>', '<%=weekCount%>');"><%=history.getHistoryExp() %></a></li>
															<% } %>
														</ul>
														<a href="#" class="close"></a>
													</div>
													<% } %>
												<%
													}
												%>
											</div>
											<% } %>
										</td>
									<% if( i % 7 == 6 ){ %>
									</tr>
									<% } %>
									<%
										}
									%>		
									
									</tbody>
								</table>
								</div>
							
							</div>



							
                            <!--// 게시판 -->
                        </div>
                    </div>
                    <!--// inner_container -->

                </div>
            </section>
        </div>
        <!--// container -->


<form name="listForm" action="calendar.do" method="get">
<input type="hidden" name="findYear" value="<%=findYear%>"/>
<input type="hidden" name="findMonth" value="<%=findMonth%>"/>
</form>

<form name="detailForm" action="calendarDetail.do" method="get">
<input type="hidden" name="headwordId"/>
<input type="hidden" name="findYear" value="<%=findYear%>"/>
<input type="hidden" name="findMonth" value="<%=findMonth%>"/>
<input type="hidden" name="weekCount"/>
</form>

<script type="text/javascript">

	$('.view_area').find('.txt').find('img').find('png').each(function(){
		$(this).load(function(){
			if( $.browser.webkit ||  $.browser.firefox )
			{	
				$(this).hide();

				$(this).load(function(){
					if( $(this).width() > 620 )
						$(this).width(620);

					$(this).show();
				});
			}
			else
			{	
				if( $(this).width() > 620 )
					$(this).width(620);
			}
		});
	});


function goList()
{
	var f = document.forms.listForm;
	f.submit();
}

function goDetail(headwordId , weekCount)
{
	var f = document.forms.detailForm;
	f.headwordId.value = headwordId;
	f.weekCount.value = weekCount;
	f.submit();
}

	
	var cal={
		init:function(){
			this.build();
		},
		build:function(){

			// 더보기 버튼 표시 여부 설정
			$(".calendar td > div > ul").each(function(){
				if($(this).height() > 64){
					$(this).css({
						"height": 56+"px",
						"overflow": "hidden"
					}).closest("div").find(".more").show();
				}
			})

			// 더보기 버튼 이벤트 바인드
			$(".calendar .more").bind("click",function(){
				
				if($(this).closest("td").index() > 4){	// 금,토요일 이면 레이어 위치 변경
					$(this).next(".list").css({"left":-212+"px"});
				}
				$(".calendar .list").hide();
				$(this).next(".list").fadeIn(500);
			});
			
			// 더보기 창 닫기 버튼 바인드
			$(".calendar .list .close").live("click",function(){
				$(this).closest(".list").hide();
				return false;
			})
		}
	}
	
	cal.init();

/**
 * 페이지 공유를 위한 API
 * @param type
 */
function share(type){
	$.ajax({
		url : '/global/getShortUrl.do' ,
		data : 'longUrl=' + escape(location.href),
		dataType : 'json' ,
		error : function(){
			alert('서버와 통신할 수 없습니다.\r\n잠시 후 이용하여주십시오.');
		},
		success : function(json){
			if( json.result == 'SUCCESS' ){
				if( type == 'twitter' ){
					var text = '[티칭허브] ' + $('htitle').text() + '... ' + json.shortUrl; 
					window.open('https://twitter.com/intent/tweet?text=' + encodeURIComponent(text) , 'twitter' , 'width=550,height=420,scrollbars=yes');
				}else if( type == 'facebook' ){
					window.open('https://www.facebook.com/sharer/sharer.php?u=' + json.shortUrl);
				}else if (type == 'me2day' ){
					window.open("http://me2day.net/posts/new?new_post[body]=\"" +encodeURIComponent('[티칭허브] ' + $('htitle').text()) + "\"" + ":" + json.shortUrl);
				}else if( type == '' ){
					
				}
			}else{
				alert(json.msg);
			}
		}
	});
}

//복사
function copy(){	
	var url =location.href;
	var IE=(document.all)?true:false;
	if(IE){
		window.clipboardData.setData("Text", url);
		alert('클립보드에 URL이 복사되었습니다.\n\nCtrl+V (붙여넣기) 단축키를 이용하시면,\nURL을 붙여 넣으실 수 있습니다.');
	}else{
		temp = prompt("Ctrl+C를 눌러 클립보드로 복사하세요", url);
	}
}

function goPrint() {
    if ( navigator.userAgent.indexOf("MSIE") > 0 ) {
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
      } else if ( navigator.userAgent.indexOf("Chrome") > 0) {
        window.print();
    }
}

function scrap(scrapType , relationIds){	
	$.ajax({
		url : '/api/isLogin.do' ,
		dataType : 'json' ,
		success : function(json){
			if( json.login == true ){
				window.open('/popup/scrapFolder.do?scrapType=' + scrapType + '&relationIds=' + relationIds , 'scrap' , 'width=450,height=500,scrollbars=yes');
			}else{
				if( confirm('로그인 후 이용가능합니다.\r\n로그인 하시겠습니까?') ){
					goLogin();
				}
			}
		}
	});
}

</script>