<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	// sidebar
	String boardSkinPath = (String) request.getAttribute("boardSkinPath");
	System.out.println("cardnews boardSkinPath : "+boardSkinPath);
	/* int boardType = Common.getParameter(request, "boardType", 1);
	BoardConfig boardConfig = (BoardConfig) request.getAttribute("boardConfig");
 */
%>
                   <div class="inner_container">
                        <!-- nav_box-->
 						 <tiles:insertDefinition name="elementarySidebarFreestudy"/> 
                        <!--// nav_box-->

                        <div class="sub_content">
                            <h2 class="page_title">티칭허브 카드뉴스</h2>
                            <p>일상생활에서 알아두면 편리한 상식과 정보</p>
                            <!-- 뉴스 tab -->
								<tiles:insertDefinition name="<%=boardSkinPath %>"/>
                            <!--// 뉴스 tab -->
                        </div>
                    </div>
                    <!--// inner_container -->