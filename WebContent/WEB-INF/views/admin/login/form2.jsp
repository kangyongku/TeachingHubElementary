<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>:: TeachingHub Administrator ::</title>
<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<link rel="stylesheet" type="text/css"	href="/assets/admin/lib/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css"	href="/assets/admin/stylesheets/theme.css">
<link rel="stylesheet"	href="/assets/admin/lib/font-awesome/css/font-awesome.css">
<script src="/assets/admin/lib/jquery-1.7.2.min.js"	type="text/javascript"></script>

<style type="text/css">
#line-chart {
	height: 300px;
	width: 800px;
	margin: 0px auto;
	margin-top: 1em;
}

.brand {
	font-family: georgia, serif;
}

.brand .first {
	color: #ccc;
	font-style: italic;
}

.brand .second {
	color: #fff;
	font-weight: bold;
}
</style>

<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

	<script type="text/javascript">
	<!--
	
	$(document).keydown(function(e){
		if( e.keyCode == 13 )
			loginCheck();
	});
	
	function loginCheck()
	{
		
		//ip 가져오기
		var getIp= "";
		$.get("http://ipaddress.urgulbook.com/",function(res){
			getIp = res.IP;
		},"jsonp");
		
		//ip권한 여부
		//if(!getIp=="211.106.68.195" || !getIp=="211.106.68.196" || !getIp=="128.134.78.2"){
		//	alert("접근 권한이 없는 IP 입니다.");
		//	return;
		//}
		
		//주소 확인해서 바꿔주기
		/*
		var getUrl = $(location).attr("href");
		var protocol = $(location).attr("protocol");
		var host = $(location).attr("host");
		var pathname = $(location).attr("pathname");
		alert(getUrl);
		alert(protocol);
		alert(host);
		alert(pathname);
		var changeUrl = "https://"+host+pathname;
		alert(changeUrl);
		$(location).attr("href",changeUrl);
		alert("최종:"+$(location).attr("href"));
		*/
		
		var f = document.forms.loginForm;
		
		if( f.userId.value == '') return alert('아이디를 입력하십시오.');
		if( f.passwd.value == '') return alert('비밀번호를 입력하십시오.');
		//f.action = 'https://cs.kumsung.co.kr:444/loginForm.do';
		f.target = '_self';
		f.submit();
	}
	
	//-->
	</script>

</head>

<!--[if lt IE 7 ]> <body class="ie ie6"> <![endif]-->
<!--[if IE 7 ]> <body class="ie ie7 "> <![endif]-->
<!--[if IE 8 ]> <body class="ie ie8 "> <![endif]-->
<!--[if IE 9 ]> <body class="ie ie9 "> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<body class="">
	<!--<![endif]-->

	<div class="navbar">
		<div class="navbar-inner">
			<ul class="nav pull-right">

			</ul>
			<a class="brand" href="index.html"><span class="first">Teaching</span>
				<span class="second">Hub</span></a>
		</div>
	</div>


	<div class="row-fluid">
		<div class="dialog">
			<div class="block">
				<p class="block-heading">TeachingHub Administrator</p>
				<div class="block-body">
					<form name="loginForm" action="" method="post">
						<label>Id</label> <input type="text" name="userId" class="span12">
						<label>Password</label> <input type="password" name="passwd" class="span12">
						<a href="javascript:loginCheck();" class="btn btn-primary pull-right" style="margin-top:5px;">Login</a>
						<div class="clearfix"></div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<script src="/assets/admin/lib/bootstrap/js/bootstrap.js"></script>
	
</body>
</html>