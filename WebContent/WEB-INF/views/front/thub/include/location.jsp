<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String beforePath = (String) request.getAttribute("beforePath");
	String currentPath = (String) request.getAttribute("currentPath");

	if( beforePath == null ) beforePath = "";
	if( currentPath == null ) currentPath = "";
%>
                         <!-- 201908 역사 자료 드림 센터 배너-->
                          <div id="dreampop" style="position:fixed;width:130px;height:85px;z-index:9999999999;margin-left:-135px;margin-top:10px;"> 
                            <a href="//bit.ly/2qOUBD2" target="_blank"><img src="/assets/front/2017img/sub/pop_dreamcenter.png" alt="역사 자료 드림 센터"></a> 
                            <span style="display:block;position:absolute;right:0;top:10px;cursor:pointer;z-index:99999999990;"><img src="/assets/front/2017img/sub/pop_btn_close.png" onclick="document.getElementById('dreampop').style.display='none'"></span>
                          </div>
                         <!-- //201908 역사 자료 드림 센터 배너-->
            <div class="location">
              <span><img src="/assets/front/2017img/ico/ico_location_home.png" alt="HOME"></span>
<%if(!beforePath.equals("")){ %>              
              <span><%=beforePath %></span>
<%} %>
<%if(!currentPath.equals("")){ %>                 
              <span class="curr"><%=currentPath %></span>
<%} %>                            
            </div>
