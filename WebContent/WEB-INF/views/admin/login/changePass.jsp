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

	
	function login()
	{
		var f = document.forms.loginForm;
		
		//if( f.userId.value == '') return alert('아이디를 입력하십시오.');
		//if( f.passwd.value == '') return alert('비밀번호를 입력하십시오.');
		f.afterChange.value = "true";
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
<form name="loginForm" action="" method="post">
<input type="hidden" id="afterChange" name="afterChange" />
</form>
	<div class="navbar">
		<div class="navbar-inner">
			<ul class="nav pull-right">

			</ul>
			<a class="brand" href="index.html"><span class="first">Teaching</span>
				<span class="second">Hub</span></a>
		</div>
	</div>

	<div style="width:98%;padding:100px 0;margin:10px;">
	
	    <div style="margin:20px auto;font-size:20px; text-align:center;width:90%; color:#fff;">
		    <p style="margin:20px 0; color:#06C"><strong style=" text-decoration:underline;">이서현(shshlee) 님!</strong></p>
		    
		    <p style="line-height:160%;font-weight:bold;">비밀번호를 변경한지 6개월이 지났습니다. <br />
		    새로운 비밀번호로 변경해주시기 바랍니다.</p>
	    </div>
	    
	    <div style="margin:50px auto;width:600px; text-align:center;">
	        <span style="margin:0 20px;"><a href="http://cs.kumsung.co.kr/member/mypage/myInfo.do"><img src="/assets/admin/images/btn_pwchange01.gif" alt="지금 변경하기" /></a></span>
	        <span style="margin:0 20px;"><a href="javascript:login();"><img src="/assets/admin/images/btn_pwchange02.gif" alt="나중에 변경하기" /></a></span>
	    </div>
	
	</div>
	<div class="row-fluid">
	
	</div>

	<script src="/assets/admin/lib/bootstrap/js/bootstrap.js"></script>
	
</body>
</html>