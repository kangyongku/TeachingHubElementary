<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html  xmlns="http://www.w3.org/1999/xhtml" lang="ko"> 
<!-- <!doctype html>
<html lang="ko"> -->
  <head>
  <tiles:insertAttribute name="head"/>
  </head>
<body>

  
<div id="wrap">
        <!-- 헤더 -->
    	<tiles:insertAttribute name="top"/>

        <!--// 헤더 -->
        <div class="pop-bg"></div>

        <!-- 퀵메뉴 -->
		<tiles:insertAttribute name="quick"/>
        <!--// 퀵메뉴 -->

        <!-- container -->
        <div class="container_m">
            <section>
            
                <div class="cont_wrap">

                         <!-- 201908 역사 자료 드림 센터 배너-->
<!--                           <div id="dreampop" style="position:fixed;width:130px;height:85px;z-index:9999999999;margin-left:-135px;margin-top:10px;"> 
                            <a href="//bit.ly/2kTNwOY"><img src="/assets/front/2017img/sub/pop_dreamcenter.png" alt="역사 자료 드림 센터"></a> 
                            <span style="display:block;position:absolute;right:0;top:10px;cursor:pointer;z-index:99999999990;"><img src="/assets/front/2017img/sub/pop_btn_close.png" onclick="document.getElementById('dreampop').style.display='none'"></span>
                          </div> -->
                         <!-- //201908 역사 자료 드림 센터 배너-->
					 <!-- inner_container -->
					<tiles:insertAttribute name="body"/>
                    <!--// inner_container -->

                </div>
            </section>
        </div>
        <!--// container -->

        <!-- footer -->
		<tiles:insertAttribute name="footer"/>
           <!--// footer -->
            <div class="top_ico">
                <a href="#none">
                    <img src="/assets/front/2017img/common/top_ico.png" alt="맨위로">
                </a>
            </div>
    
        </div>
    </body>
    </html>
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-44647320-1', 'kumsung.co.kr');
  ga('send', 'pageview');

</script> 