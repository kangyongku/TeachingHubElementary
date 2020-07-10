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