<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="kr.co.kumsung.thub.setting.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	// get request uri
	String requestUri = (String) request.getAttribute("javax.servlet.forward.request_uri");
	int boardType = Common.getParameter(request, "boardType", 1);
	

%>
<!-- nav -->
                        <!-- nav_box-->
                        <div class="nav_box">
                            <div class="lnb_title_box" id="activity">
                                <p class="lnb_title">자유학년 · 창체</p>
                            </div>
                            <ul class="lnb_second activity">
                                <li <%if( requestUri.indexOf("/freestudy.do") > -1 )%>class="on"<% %>>
                                    <a href="freestudy.do?boardType=1">자유학년제</a>
                                </li>
                                
                                <li <%if( requestUri.indexOf("/creative.do") > -1 )%>class="on"<% %>>
                                    <a href="creative.do?boardType=1">창의적 체험활동</a>
                                </li>
                                
                                <li <%if( requestUri.indexOf("/experience.do") > -1 )%>class="on"<% %>>
                                    <a href="experience.do">체험활동 프로그램</a>
                                </li>
                            </ul>
                        </div>
   
    