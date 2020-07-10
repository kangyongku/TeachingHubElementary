<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	int pg = (Integer) request.getAttribute("pg");

	String findMethod = (String) request.getAttribute("findMethod");
	String findStr = (String) request.getAttribute("findStr");

	List<Event> list = (List<Event>) request.getAttribute("list");
	int articleNum = (Integer) request.getAttribute("articleNum");
	String paging = (String) request.getAttribute("paging");
	
%>

    <div id="wrap">
        <!-- 헤더 -->
        
        <!-- 퀵메뉴 -->

        <!-- container -->
        <div id="container" class="container">
            <section>
                <div class="inwrap">

                    <!-- <div class="location">
                        <span>
                            <img src="../image/ico/ico_location_home.png" alt="HOME">
                        </span>
                        <span>나눔·소통</span>
                        <span class="curr">이벤트</span>
                    </div> -->

                    <div class="inner_container">
						<!-- sidebar -->
						<tiles:insertDefinition name="elementarySidebarCommunication"/>

                        <div class="sub_content">
                            <h2 class="page_title">이벤트</h2>
                            <p>참여하면 푸짐한 선물이 팡팡!</p>
                            
                            <!-- tab menu -->
                            <div class="class_tab_wrap">
                                <!-- component: tabs -->
								<ul class="">						
									<li class="on"><a href="eventList.do">이벤트</a></li>
									<li><a href="eventWinList.do">당첨자 발표</a></li>
								</ul>
                            </div>
                            <!--// tab menu -->
                            
							<!-- 게시판 -->
                           	<!-- component: data_box_list -->
							<ul class="data_box_list">
								<% if(articleNum > 0){  %>
									<%
										int i=0;
										for(Event event : list){
										// for reply articles
										
									%>
										<li <%if( i%2 == 0){ %>class="left"<%} %>>
											<a href="javascript:goEventDetail('<%=event.getEventId() %>')"><img src="<%=event.getBannerImg() %>" alt="" /></a>
											<dl>
												<dt <%if( event.getProgress().equals("N") ) {%> class="off" <% } %> ><a href="javascript:goEventDetail('<%=event.getEventId() %>')"><%=event.getTitle() %></a></dt>
												<dd class="period"><%=event.getStartDate() %> ~ <%=event.getEndDate() %></dd>
												<dd class="text">
													<%=event.getSummaryContents().replace("\r\n","<br>") %>
												</dd>
											</dl>
											<%if( event.getEventCnt() > 0 ){ %>
												<a href="javascript:goEventWinDetail('<%=event.getWinId()%>');" class="btn"><img src="/assets/front/2017img/img2017/btn/btn_winner.gif" alt="당첨자발표"></a>
											<%}%>
										</li>
									<%	
										i++;
										articleNum--;
										}
									%>
								<%}else{ %>	
									<table style="width:100%;">
									<tr>
										<td colspan="5" style="height:80px;text-align:center;">
										등록된 내용이 없습니다.
										</td>
									</tr>
									</table>
								<%} %>
							</ul>

                            <!-- component: pading -->
							<div class="new_paging_wrap">
								<%=paging %>
							</div>
					                            
							<!-- component: search -->
							<form name="sform" action="eventList.do" method="get">
							<div class="board_search">
								<span class="search01">
									<select class="jq-select" name="findMethod">
									<option value="ft" <%if ( findMethod.equals("ft") ){ %> selected <%} %>>제목</option>
									<option value="fc" <%if ( findMethod.equals("fc") ){ %> selected <%} %>>내용</option>	
									</select>
								</span>
								<span class="search02">
									<input type="text" class="text_bx" name="findStr" value="<%=findStr %>"/><input type="image" src="/assets/front/2017img/img2017/btn/btn_search_txt.gif" alt="검색" class="search_bx" onclick="javascript:goSearch();"/>
								</span>
							</div>
							</form>	
							
							
                            <!--// 게시판 -->
                        </div>
                    </div>
                    <!--// inner_container -->

                </div>
            </section>
        </div>
        <!--// container -->

        <!-- footer -->

    </div>

 
<form name="listForm" action="eventList.do" method="get">
	<input type="hidden" name="pg"/>
	<input type="hidden" name="findMethod" value="<%=findMethod%>"/>
	<input type="hidden" name="findStr" value="<%=findStr%>"/>
</form>

<form name="detailForm" action="" method="get">
	<input type="hidden" name="eventId"/>
	<input type="hidden" name="winId"/>
	<input type="hidden" name="pg" value="1"/>
	<input type="hidden" name="findMethod" value="<%=findMethod%>"/>
	<input type="hidden" name="findStr" value="<%=findStr%>"/>
</form>

<script type="text/javascript">

function goList(pg)
{
	var f = document.forms.listForm;
	f.pg.value = pg;
	f.submit();
}

function goSearch()
{
	var f = document.forms.sform;
	f.submit();
}

//이벤트 상세보기
function goEventDetail(eventId)
{
	var f = document.forms.detailForm;
	f.eventId.value = eventId;
	f.action = 'eventDetail.do';
	f.submit();
}

//이벤트 당첨자 상세 보기
function goEventWinDetail(winId)
{
	var f = document.forms.detailForm;
	f.winId.value = winId;
	f.action = 'eventWinDetail.do';
	f.submit();
}

//select 스타일때문에 구 common.js에서 가져옴 닷컴작업 
var ksp = {

	init: function(){
		this.build();
	},

	build: function(){
	
		ksp.buildSelectBox();
		$('.placeholder').placeholder();
	},
	
	// 셀렉트 박스 
	buildSelectBox: function(){		
		if($("select.jq-select").length > 0){
			$("select.jq-select").selectbox({
				onOpen: function (inst) {
					//console.log("open", inst);
				},
				onClose: function (inst) {
					//console.log("close", inst);
				},
				onChange: function (val, inst) {
					//console.log("change", inst);
					eval($(this).attr('onchange-handler')); 
				},
				effect: "slide",
				speed: 100
			});
		}
	},
}

$(document).ready(function(){
	ksp.init();
	ksp_gs.init();
});
</script>
