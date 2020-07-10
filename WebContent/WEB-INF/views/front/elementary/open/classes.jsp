<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	// sidebar
	String boardSkinPath = (String) request.getAttribute("boardSkinPath");
	System.out.println("boardSkinPath === " + boardSkinPath);
%>


                   <div class="inner_container">
                        <!-- nav_box-->
 						 <tiles:insertDefinition name="elementarySidebarCommunication"/> 
                        <!--// nav_box-->

                        <div class="sub_content">
                          	<h2 class="page_title">학급운영자료</h2>
                            <p>꼭 필요한 것만 쏙쏙! &nbsp; 수업 및 학급 운영과 관련된 다양한 서식 자료를 공유하는 공간입니다.</p>
							
							<!-- tab menu -->
							<div class="write_btn">
								 <a class="btn biglistBtn2" href="javascript:checkLoginAndGo('/open/share/send.do?boardId=3');">자료 원고 보내기</a>
							</div>
                            <!--// tab menu -->
                            
                            <!-- 포워드 -->
							<tiles:insertDefinition name="<%=boardSkinPath %>"/>
								
                        </div>
                    </div>
                    <!--// inner_container -->
                    

<script type="text/javascript">

/**
 * 로그인 체크후 url로 redirect 한다.
 * @param url
 */
function checkLoginAndGo(url){
	$.ajax({
		url : '/api/isLogin.do' ,
		dataType : 'json' ,
		error : function(){
			alert('서버와의 통신도중 에러가 발생되었습니다.');
		},
		success : function(json){
			if( json.login == true ){
				location.href = url;
			}else{
				if( confirm('로그인 후 이용가능합니다.\r\n로그인 하시겠습니까?') )
					goLogin();
			}
		}
	});
}

</script>
