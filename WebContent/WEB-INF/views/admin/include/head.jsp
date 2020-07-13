<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta charset="utf-8">
<%@ page import="java.net.InetAddress" %>
<%@ page import="java.lang.String.*" %>
<%@ page import="kr.co.kumsung.thub.util.Validate" %>
<title>:::  :::</title>
<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link rel="stylesheet" type="text/css" href="/assets/admin/lib/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="/assets/admin/stylesheets/theme.css">
<link rel="stylesheet" type="text/css" href="/assets/admin/stylesheets/jquery-ui-1.10.3.custom.min.css">
<link rel="stylesheet" href="/assets/admin/lib/font-awesome/css/font-awesome.css">
<script src="/assets/admin/lib/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="/assets/admin/lib/bootstrap/js/bootstrap.js" type="text/javascript"></script>
<script src="/assets/admin/lib/jquery.blockui.js" type="text/javascript"></script>
<script src="/assets/admin/lib/common.js" type="text/javascript"></script>
<script src="/assets/admin/lib/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
<%
String MyIp = request.getRemoteAddr();
System.out.println("MyIp : "+MyIp);
String Result[] = MyIp.split("[.]");
String ReSplit1 = Result[0];
String ReSplit2 = Result[1];
String ReSplit3 = Result[2];
String PassVar = "";

if(ReSplit1.equals("211") &&  ReSplit2.equals("106") &&  ReSplit3.equals("68")){  //4층 5층 아이피 3째자리까지 체크 수정
	PassVar = "Y";
} 

if(ReSplit1.equals("121") &&  ReSplit2.equals("78") &&  ReSplit3.equals("33")){  // 추가
	PassVar = "Y";
} 

if(ReSplit1.equals("121") &&  ReSplit2.equals("78")){  // 추가
	PassVar = "Y";
} 

boolean ClientIp = Validate.ipCheck("");

%>
<script type="text/javascript">
var PassV = '<%=PassVar%>';
if(!<%= ClientIp%>){
     if(PassV !== "Y") {
		alert("접근 권한이 없습니다..");
		location.href="/commons/ipErro.jsp";
	 }
}
</script>
<!--[if lt IE 9]><script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->