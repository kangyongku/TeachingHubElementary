<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	// get request uri
	String requestUri = (String) request.getAttribute("javax.servlet.forward.request_uri");
	String queryString = (String) request.getAttribute("javax.servlet.forward.query_string"); 
	
	String sActive = "";		// 2차 카테고리의 활성화 키
	String sActive2 = "";		// 3차 카테고리의 활성화 키
	
	if( requestUri.indexOf("/utilize.do") > -1 )         sActive = "utilize";
	if( requestUri.indexOf("/lecture.do") > -1 )         sActive = "lecture";
	if( requestUri.indexOf("/open/classes.do") > -1 )   sActive = "classes";
	if( requestUri.indexOf("/open/send.do") > -1 )      sActive = "classes";
	if( requestUri.indexOf("/open/sendComplete") > -1 ) sActive = "classes";
	if( requestUri.indexOf("/open/links.do") > -1 )           sActive = "links";
	if( requestUri.indexOf("/open/eventList.do") > -1 )       sActive = "eventList";
	if( requestUri.indexOf("/open/eventDetail.do") > -1 )     sActive = "eventList";
	if( requestUri.indexOf("/open/eventWinList.do") > -1 )    sActive = "eventList";
	if( requestUri.indexOf("/open/eventWinDetail.do") > -1 )  sActive = "eventList";
	if( requestUri.indexOf("/open/pollList.do") > -1 )        sActive = "pollList";
	if( requestUri.indexOf("/open/pollResult.do") > -1 )      sActive = "pollList";
	
	if(queryString != null  && !"null".equals(queryString) && !"".equals(queryString)){
		if( queryString.indexOf("findMaster=E004") > -1 ) sActive2 = "E004";
		if( queryString.indexOf("findMaster=E001") > -1 ) sActive2 = "E001";
		if( queryString.indexOf("findMaster=E002") > -1 ) sActive2 = "E002";
	}
	
%>
<div class="nav_box">
    <div class="lnb_title_box" id="communication">
        <p class="lnb_title">정보 · 소통</p>
    </div>
    <ul class="lnb_second activity">
        <li <% if( "utilize".equals(sActive)){ %>class="on"<% } %> >
            <a href="/open/utilize.do">티칭허브&#8314; 자료</a>
        </li>
        <li <% if( "lecture".equals(sActive)){ %>class="on"<% } %>>
            <a href="/open/lecture.do">수업 활용 영상</a>
        </li>
        <li <% if( "classes".equals(sActive)){ %>class="on"<% } %>>
            <a href="/open/classes.do">학급운영자료</a>
        </li>
        <li <% if( "links".equals(sActive)){ %>class="on"<% } %>>
            <a href="/open/links.do">유용한 링크집</a>
        </li>                 
        <li <% if( "eventList".equals(sActive)){ %>class="on"<% } %>>
            <a href="/open/eventList.do">이벤트</a>
        </li>
        <li <% if( "pollList".equals(sActive)){ %>class="on"<% } %>>
            <a href="/open/pollList.do">설문조사</a>
        </li>
    </ul>
</div>