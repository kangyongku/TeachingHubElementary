<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	//get request uri
	String requestUri = (String) request.getAttribute("javax.servlet.forward.request_uri");
	System.out.println("requestUri : "+requestUri);
	String keyword = "티칭허브, 금성출판사, 교수학습자료 서비스, 창의적체험활동, 교수학습자료, 교과활용자료, 학급운영자료, 창의적 체험활동, 공감영상, 역사전시관, 인물사건캘린더, 웹툰, 웹진, 힐링 여행, 캠핑, 생활의 지혜, 이벤트, 모바일, 앱서비스, 티칭백과";

	if( requestUri.indexOf("/learning/") >= 0 )
		keyword = "티칭허브, 금성출판사, 교수학습자료 서비스, 학습자료, 공통자료, 수업자료, 평가자료, 멀티미디어자료, 학습지도안, 본문자료, 수업활동지, 해답자료, 보충자료, 확인평가, 형성평가, 총괄평가, 서술형평가, 수행평가, 국가수준평가, 핵심정리, 판서자료, 탐구활동지, 그림자료, 기타자료, 플래시, 동영상, 이미지, 듣기, 웹링크, 교사용 CD 다운로드";
	else if( requestUri.indexOf("/open/") >= 0 )
		keyword = "티칭허브, 금성출판사, 교수학습자료 서비스, 창의적체험활동, 자료나눔터, 멀티미디어자료, 동영상, 이미지, 플래시, 교과활용자료, 학급운영자료, 창의적 체험활동, 예시 자료, 서식 자료, 참고 사이트, 역사전시관, 인물사건캘린더, 신나는 교과서여행, 재밌는 교과서영화, 유용한 링크집";
	else if( requestUri.indexOf("/happy/") >= 0 )
		keyword = "티칭허브, 금성출판사, 교수학습자료 서비스, 공감영상, 좋은글, 웹툰, 이벤트, 웹진, 힐링 여행, 둘레길, 100대 명산, 자전거길, 캠핑, 생활의 지혜, 스토리텔링과 교과서, 교과서 제작 방법";
	else if( requestUri.indexOf("/customer/") >= 0 )
		keyword = "티칭허브, 금성출판사, 교수학습자료 서비스, 이용안내, 공지사항, 자주묻는질문, 문의요청, 프로그램안내, 문의전화, 찾아오시는길, 사이트맵";
	
	System.out.println("keyword : "+keyword);
%>
    <meta charset="utf-8">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="Content-Script-Type" content="text/javascript" />
    <meta http-equiv="Content-Style-Type" content="text/css" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    
    <title>티칭허브</title>
	<link rel="stylesheet" href="/assets/front/lib/2017css/swiper.css">
    
    <link rel="stylesheet" href="/assets/front/lib/2017css/sub.css">
    <link rel="stylesheet" href="/assets/front/lib/2017css/table.css">
    <link rel="stylesheet" href="/assets/front/lib/2017css/jquery.mCustomScrollbar.css">
	<link rel="stylesheet" href="/assets/front/lib/2017css/jquery.selectbox.css">
	<link rel="stylesheet" type="text/css" href="/assets/front/lib/2017css/sub2017.css" />
	<!-- -------변경css--------- -->
	<link rel="stylesheet" type="text/css" href="/assets/front/lib/2019css/index2019.css" />
	<link rel="stylesheet" type="text/css" href="/assets/front/lib/2019css/common.css">
	<link rel="stylesheet" type="text/css" href="/assets/front/lib/2019css/slick.css" />
	<link rel="stylesheet" type="text/css" href="/assets/front/lib/2019css/slick-theme.css" />
	<!-- -------변경css--------- -->

    <script type="text/javascript" src="/assets/front/lib/2017js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="/assets/front/lib/2017js/jquery-ui.js"></script>
    <script type="text/javascript" src="/assets/front/lib/2017js/jquery.mCustomScrollbar.concat.min.js"></script>
    <script type="text/javascript" src="/assets/front/lib/2017js/flexslider.js"></script>
	<script type="text/javascript" src="/assets/front/lib/2017js/swiper.min.js"></script>
	<script type="text/javascript" src="/assets/front/lib/2017js/jquery.flip.min.js"></script>
	<script type="text/javascript" src="/assets/front/lib/2017js/common.js"></script>
	<script type="text/javascript" src="/assets/front/lib/2017js/jquery.selectbox.min.js"></script>
 	<script type="text/javascript" src="/assets/front/lib/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="/assets/front/lib/2017js/thub.js" charset="utf-8"></script>	  
	<script type="text/javascript" src="/assets/mathjax/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script>
	
	<!-- -------변경js--------- -->
	<script type="text/javascript" src="/assets/front/lib/2019js/slick.js"></script>
	<script type="text/javascript" src="/assets/front/lib/2019js/main2019.js"></script>
	<!-- -------변경css--------- -->
	
	<script>
	   /* 171208 셀렉트박스 */
	   $(function() {
	      $(".select_wrap").each(function() {
	         $(this).find("select").selectBox();
	      });
	   });
	</script>

    <!--[if lt IE 9]>
	    <script type="text/javascript" src="js/html5shiv.js"></script>
    <![endif]-->    
