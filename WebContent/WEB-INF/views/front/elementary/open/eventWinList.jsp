<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	int pg = (Integer) request.getAttribute("pg");

	String findMethod = (String) request.getAttribute("findMethod");
	String findStr = (String) request.getAttribute("findStr");

	List<EventWin> list = (List<EventWin>) request.getAttribute("list");
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
									<li><a href="eventList.do">이벤트</a></li>
									<li class="on"><a href="eventWinList.do">당첨자 발표</a></li>
								</ul>
                            </div>
                            <!--// tab menu -->
                            
							<!-- 게시판 -->
							<div class="list_board">
								<table cellpadding="0" cellspacing="0" summary="" >
									<caption></caption>
									<colgroup>
										<col width="75"/>
										<col width="*"/>
										<col width="203"/>
										<col width="108"/>
									</colgroup>
									<thead>
										<tr>
											<th>번호</th>
											<th>제목</th>
											<th>이벤트기간</th>
											<th>등록일</th>
										</tr>
									</thead>
									<tbody>
										<% if(articleNum > 0){  %>
											<%
												for(EventWin eventWin : list){
												// for reply articles
												
											%>
												<tr>
													<td><%=articleNum %></td>
													<td class="title"><a href="javascript:goEventWinDetail('<%=eventWin.getWinId() %>')"><%=eventWin.getTitle() %></a>
														<% if( eventWin.getDiffDate() > -14){ %>
														<span class="new"></span>
														<% } %>
													</td>
													<td><%=eventWin.getStartDate() %> ~ <%=eventWin.getEndDate() %></td>
													<td><%=eventWin.getRegDate() %></td>
												</tr>
												
											<%	
												articleNum--;
												}
											%>
										<%}else{ %>	
											<tr>
												<td colspan="4" style="height:80px;text-align:center;">
												등록된 내용이 없습니다.
												</td>
											</tr>
										<%} %>
									</tbody>
								</table>
							</div>

                            <!-- component: pading -->
							<div class="new_paging_wrap">
								<%=paging %>
							</div>
							
							<!-- component: search -->
							<form name="sform" action="" method="get">
							<div class="board_search">
								<span class="search01">
									<select class="jq-select" name="findMethod">
									<option value="ft" <%if ( findMethod.equals("ft") ){ %> selected <%} %>>제목</option>
									<option value="fc" <%if ( findMethod.equals("fc") ){ %> selected <%} %>>내용</option>	
									</select>
								</span>
								<span class="search02">
									<input type="text" class="text_bx" name="findStr" value="<%=findStr %>"/><input type="image" src="/assets/front/2017img/img2017/btn/btn_search_txt.gif" alt="검색" class="search_bx" onclick="document.forms.sform.submit();"/>
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

 
<form name="listForm" action="" method="get">
<input type="hidden" name="pg"/>
<input type="hidden" name="findMethod" value="<%=findMethod%>"/>
<input type="hidden" name="findStr" value="<%=findStr%>"/>
</form>

<form name="detailForm" action="" method="get">
<input type="hidden" name="winId"/>
<input type="hidden" name="pg" value="<%=pg%>"/>
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
function goEventWinDetail(winId)
{
	var f = document.forms.detailForm;
	f.winId.value = winId;
	f.action = 'eventWinDetail.do';
	f.submit();
}

//이벤트 당첨자 상세 보기
function goEventWinWinDetail(winId)
{
	var f = document.forms.detailForm;
	f.winId.value = winId;
	f.action = 'eventWinWinDetail.do';
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

