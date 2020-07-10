<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="kr.co.kumsung.thub.setting.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	// get request uri
	String requestUri = (String) request.getAttribute("javax.servlet.forward.request_uri");
	int boardType = Common.getParameter(request, "boardType", 1);
%>
<!-- nav_box-->
<div class="nav_box">
    <div class="lnb_title_box" id="activity">
        <p class="lnb_title">창의적 체험활동</p>
    </div>
    <ul class="lnb_second activity">
        <li <%if( requestUri.indexOf("/creative.do") > -1 )%>class="on"<% %>>
            <a href="/elementary/calendar.do">오늘의 역사</a>
        </li>
        <li <%if( requestUri.indexOf("/experience.do") > -1 )%>class="on"<% %>>
            <a href="/elementary/experience.do">체험활동 프로그램</a>
        </li>
        <li <%if( requestUri.indexOf("/career.do") > -1 )%>class="on"<% %>>
            <a href="/elementary/career.do">진로교육</a>
        </li>
        <li <%if( requestUri.indexOf("/cardnews.do") > -1 )%>class="on"<% %>>
            <a href="/elementary/cardnews.do">카드 뉴스</a>
        </li>
        <li <%if( requestUri.indexOf("/new1.do") > -1 )%>class="on"<% %>>
            <a href="/elementary/new1.do">신규1(진행중)</a>
        </li>
        <li <%if( requestUri.indexOf("/new2.do") > -1 )%>class="on"<% %>>
            <a href="/elementary/new2.do">신규2(진행중)</a>
        </li>
        <li <%if( requestUri.indexOf("/new3.do") > -1 )%>class="on"<% %>>
            <a href="/elementary/new3.do">신규3(진행중)</a>
        </li>
    </ul>
</div>
    