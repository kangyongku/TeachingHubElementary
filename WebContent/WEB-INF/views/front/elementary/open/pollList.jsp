<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	int pg = (Integer) request.getAttribute("pg");

	String findMethod = (String) request.getAttribute("findMethod");
	String findStr = (String) request.getAttribute("findStr");

	List<Poll> list = (List<Poll>) request.getAttribute("list");
	int articleNum = (Integer) request.getAttribute("articleNum");
	
	String paging = (String) request.getAttribute("paging");
	
	Member member = (Member) session.getAttribute("member");
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
                        <span class="curr">설문조사</span>
                    </div> -->

                    <div class="inner_container">
                        <!-- sidebar -->
						<tiles:insertDefinition name="elementarySidebarCommunication"/>

                        <div class="sub_content">
                            <h2 class="page_title">설문조사</h2>
                            <p>더 나은 서비스를 위한 노력! 선생님들의 의견 수렴을 위한 공간입니다. 많은 참여 부탁 드립니다.</p>
                           
                            
							 <!-- 게시판 -->
                           	<div class="list_board">
								<table cellpadding="0" cellspacing="0" summary="" >
									<caption></caption>
									<colgroup>
										<col width="75"/>
										<col width="75"/>
										<col width="*"/>
										<col width="155"/>
										<col width="100"/>
									</colgroup>
									<thead>
										<tr>
											<th>번호</th>
											<th>대상</th>
											<th>제목</th>
											<th>기간</th>
											<th>결과</th>
										</tr>
									</thead>
									<tbody>
					
										<% if(articleNum > 0){  %>
											<%
												for(Poll poll : list){
												// for reply articles
													if( poll.getIsUse().equals("Y") ) {
											%>
												<tr>
													<td><%=articleNum %></td>
													<td><%=poll.getTargetName() %></td>
													<td class="title">
														<% if( member == null ){ %>
															<a href="javascript:alert('로그인 후 설문조사 참여가 가능합니다.');">
														<% }else{ %>
															<% if ( poll.getProgress().equals("Y") ){ %>
																<% if( poll.getTarget().equals("A") 
																		|| (poll.getTarget().equals("B") && member.getSubCategory().indexOf("A001") > -1 ) 
																		|| (poll.getTarget().equals("C") && member.getSubCategory().indexOf("A002") > -1 )
																		|| (poll.getTarget().equals("D") && member.getSubCategory().indexOf("A003") > -1 ) ){ %>
																<a href="javascript:goResponse('<%=poll.getPollId() %>')">
																<% }else{ %>
																<a href="javascript:alert('설문참여 대상이 아닙니다. ^^;');">
																<% } %>
															<% }else{ %>
															<a href="javascript:alert('현재 진행중인 설문조사가 아닙니다.');">
															<% } %>
														<% } %>
														<%=poll.getSubject() %></a>
														<%if ( poll.getProgress().equals("Y") ){ %><span class="ongoing"></span><%} %>
													</td>
													<td><%=poll.getStartDate() %> ~ <%=poll.getEndDate() %></td>
													<td>
														<%if ( poll.getIsResult().equals("Y") ){ %>
															<a href="javascript:goResult('<%=poll.getPollId() %>')"><img src="/assets/front/img/btn/btn_view.gif" alt="보기" /></a></td>
														<%} %>
												</tr>
											<%	
														articleNum--;
													}
												}
											%>
										<%}else{ %>	
											<tr>
												<td colspan="5" style="height:80px;text-align:center;">
												[ 등록된 게시물이 없습니다. ]
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
							
							<!-- 검색 -->
							<form name="sForm" action="pollList.do" method="get">
								<!-- component: search -->
								<div class="board_search">
									<span class="search01">
										<select class="jq-select" name="findMethod">
										<option value="ft" <%if ( findMethod.equals("ft") ){ %>selected<%} %>>제목</option>						
										</select>
									</span>
									<span class="search02">
										<input type="text" class="text_bx" name="findStr" value="<%=findStr %>"/><input type="image" src="/assets/front/img/customer/images/img2017/btn/btn_search_txt.gif" alt="검색" class="search_bx" onclick="javscript:goSearch();"/>
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


<form name="listForm" action="pollList.do" method="get">
<input type="hidden" name="pg"/>
<input type="hidden" name="findMethod" value="<%=findMethod%>"/>
<input type="hidden" name="findStr" value="<%=findStr%>"/>
</form>

<form name="detailForm" action="" method="get">
<input type="hidden" name="pollId"/>
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

//설문조사 등록 폼
function goResponse(pollId){
	var f = document.forms.detailForm;
	f.pollId.value = pollId;
	f.action = 'pollResponseDetail.do'
	f.submit();
}

//설문조사 결과 
function goResult(pollId)
{
	var f = document.forms.detailForm;
	f.pollId.value = pollId;
	f.target = '_self';
	f.action = 'pollResult.do';
	f.submit();
}



</script>

