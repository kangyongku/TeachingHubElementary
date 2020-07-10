<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	// sidebar
	String boardSkinPath = (String) request.getAttribute("boardSkinPath");
	System.out.println("boardSkinPath : "+boardSkinPath);
	int boardType = Common.getParameter(request, "boardType", 1);
	BoardConfig boardConfig = (BoardConfig) request.getAttribute("boardConfig");

%>


                   <div class="inner_container">
                        <!-- nav_box-->
 						  <tiles:insertDefinition name="elementarySidebarFreestudy"/> 
                        <!--// nav_box-->

                        <div class="sub_content">
                            <h2 class="page_title">체험활동 프로그램</h2>
                            <p>다양한 주제의 체험활동 프로그램을 제공합니다.</p>
                            <div class="class_tab_wrap2 activity3">
                            </div>
                            <!--// tab menu -->
                            


                            <!-- 수업이란 tab -->
								<div class="class_tab_content" id="class_data">
								 <tiles:insertDefinition name="<%=boardSkinPath %>"/> 
								</div>								
                            <!--// 수업이란 tab -->
                        </div>
                    </div>