<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="kr.co.kumsung.thub.setting.*" %>
<%
	// get request uri
	String requestUri = (String) request.getAttribute("javax.servlet.forward.request_uri");
	
	String sActive = "";		// 2차 카테고리의 활성화 키
	String tActive = "";		// 3차 카테고리의 활성화 키

	sActive = "healing";
	if( requestUri.indexOf("/camping.do") > -1 ) tActive = "camping";
	if( requestUri.indexOf("/travel.do") > -1 ) tActive = "travel";
	if( requestUri.indexOf("/wisdom.do") > -1 ) tActive = "wisdom";
	if( requestUri.indexOf("/emotion.do") > -1 ) tActive = "emotion";
	if( requestUri.indexOf("/webtoons.do") > -1 ) tActive = "webtoons";
%>
<!-- nav -->
<div class="nav_box">
	<div class="lnb_title_box" id="activity">
        <p class="lnb_title">쉬는시간[쉼터]</p>
    </div>
	<ul class="lnb_second activity">
		<li <% if( sActive.equals("lecture")){ %>class="on"<% } %>><a href="#">데일리 잉글리시(미개발)</a></li>
		<li <% if( tActive.equals("camping")){ %>class="on"<% } %>><a href="/happy/camping.do">힐링캠핑</a></li>
		<li <% if( tActive.equals("travel")){ %>class="on"<% } %>><a href="/happy/travel.do">추천여행</a></li>
		<li <% if( tActive.equals("wisdom")){ %>class="on"<% } %>><a href="/happy/wisdom.do">생활의 지혜</a></li>
		<li <% if( tActive.equals("emotion")){ %>class="on"<% } %>><a href="/happy/emotion.do">좋은글</a></li>
		<li <% if( tActive.equals("webtoons")){ %>class="on"<% } %>><a href="/happy/webtoons.do">웹툰</a></li>
	</ul>
</div>
