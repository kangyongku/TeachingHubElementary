<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>

<%
	String findYear = (String) request.getAttribute("findYear");
	String findMonth = (String) request.getAttribute("findMonth");
	
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
                        <span class="curr">인물·사건캘린더</span>
                    </div> -->

                    <div class="inner_container">
                        <!-- sidebar -->
						<tiles:insertDefinition name="elementarySidebarFreestudy"/>

                        <div class="sub_content" >
                            <h2 class="page_title">인물&middot;사건 캘린더</h2>
                            <p>달력 속에서 만나는 역사 속 오늘! 달력을 넘기면서 지나간 시대의 인물과 사건을 만나는 시간 여행을 합니다.</p>
							
							
							<!-- 기존 캘린더 스타일 가져옴 -->
							<!-- module: calendar -->
							<div class="calendar">
					
								<div class="header">
									<a href="calendar.do?findYear=<%=prevFindYear %>&findMonth=<%=prevFindMonth %>" class="prev"></a>
									<div><%=findYear %>.<%=findMonth %></div>
									<a href="calendar.do?findYear=<%=nextFindYear %>&findMonth=<%=nextFindMonth %>" class="next on"></a>
								</div>
								
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
								<thead>
								<tr>
									<th class="first">일</th>
									<th>월</th>
									<th>화</th>
									<th>수</th>
									<th>목</th>
									<th>금</th>
									<th class="last">토</th>
								</tr>
								</thead>
								<tbody>
									<%
										int loopCnt = calendarList.size();
										int weekCount = 0;
									
										for(int i = 0 ; i < loopCnt ; i++){
											ResultMap item = (ResultMap) calendarList.get(i);
									%>
									<% 
										if( i % 7 == 0){
											weekCount++;
									%>
									<tr>
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
														<div class="image" onclick="goDetail('<%=firstHistory.getHeadwordId()%>' , '<%=weekCount%>');" style="cursor:pointer;"><img src="<%=firstHistory.getThumbnail() %>" width="81" height="63" alt="" /></div>
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
														<h4><%=findMonth %>월 <%=item.getInt("date") %>일</h4>
														<ul>
															<% for(History history : histories){ %>
															<li><a href="javascript:goDetail('<%=history.getHeadwordId()%>','<%=weekCount%>');"><%=history.getHistoryExp() %></a></li>
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
							<!-- // 캘린더 끝 -->



							
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

 <form name="detailForm" action="calendarDetail.do" method="get">
<input type="hidden" name="headwordId"/>
<input type="hidden" name="findYear" value="<%=findYear%>"/>
<input type="hidden" name="findMonth" value="<%=findMonth%>"/>
<input type="hidden" name="weekCount"/>
</form>

<script type="text/javascript">
<!--
$(document).ready(function(){
	
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
});

function goDetail(headwordId , weekCount)
{
	var f = document.forms.detailForm;
	f.headwordId.value = headwordId;
	f.weekCount.value = weekCount;
	f.submit();
}

//-->
</script>