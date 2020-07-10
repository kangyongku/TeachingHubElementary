<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%

	// get category 
	List<Category> elCdCategory = (List<Category>) request.getAttribute("elCdCategory");
	List<Category> mdCdCategory = (List<Category>) request.getAttribute("mdCdCategory");
	List<Category> hgCdCategory = (List<Category>) request.getAttribute("hgCdCategory");
	
	List<Category> elSubjects = (List<Category>) request.getAttribute("elSubjects");
	List<Category> mdSubjects = (List<Category>) request.getAttribute("mdSubjects");
	List<Category> hgSubjects = (List<Category>) request.getAttribute("hgSubjects");
	List<Book> elBooks = (List<Book>) request.getAttribute("elBooks");
	List<Book> mdBooks = (List<Book>) request.getAttribute("mdBooks");
	List<Book> hgBooks = (List<Book>) request.getAttribute("hgBooks");
	
	Member member = (Member) session.getAttribute("member");
	if( member == null ) member = new Member();
	
	int idx = 0; 			// tmp
	int loopCnt = 0;		// tmp
%>
<!-- 퀵메뉴 -->

	<div id="quickmenu">
<% 
if( Validate.isEmpty(member.getUserId())){ 

%>	
			
	<form name="loginForm" action="" method="post" onsubmit="mainLogin();return false;">
	<input type="hidden" name="returnUrl" value="http://thub.kumsung.co.kr/"/> 
		<div class="quickmenu_prev">
			<img src="/assets/front/2017img/main/main15.png" alt="">
			<p>티칭허브<br><strong>로그인</strong></p>
			<div class="log_id"><input type="text" title="아이디" name="userId" id="userId" placeholder="아이디"></div>
			<div class="log_password"><input type="password" title="비밀번호" name="passWd" id="passWd" placeholder="비밀번호"></div>
			<div class="id_check">
				<input type="checkbox" name="save_id" id="save_id" value="Y">
				<label for="in3">아이디저장</label>
			</div>
			<a href="javascript:mainLogin();" class="login_btn">로그인</a>
			<div class="login_btns">
				<a href="http://cs.kumsung.co.kr/member/common/idPw.do" target="_blank">아이디</a>/
				<a href="http://cs.kumsung.co.kr/member/common/idPw.do" target="_blank">비밀번호 찾기</a>
				<br>
				<a href="javascript:document.joinForm.submit();" class="on">회원가입</a>
			</div>

		</div>
	</form>		
	<script type="text/javascript">
			
	$(document).ready(function(){
		$.browser={};(function(){
		    jQuery.browser.msie=false;
		    $.browser.version=0;if(navigator.userAgent.match(/MSIE ([0-9]+)\./)){
		    $.browser.msie=true;jQuery.browser.version=RegExp.$1;}
		})();
		
		if( $.cookie('thub_main_id') != '' )
		{
			$('input[name=userId]').val($.cookie('thub_main_id'));
			$('#save_id').prop( 'checked', true );
			$('#save_id').parent().addClass("on");
		}
			
		if( $.browser.msie )
		{
			if( parseInt($.browser.version) < 8 )
			{
				$('form[name=loginForm]').find('input').focus(function(){
					alert('IE8 이하는 보안정책상 통합회원 페이지에서 로그인 하여야 합니다.');
						location.href='http://cs.kumsung.co.kr/index.do?returnUrl=http://thub.kumsung.co.kr';
				});
			}	
		}
		 	if( $.browser.msie )
		{
			if( parseInt($.browser.version) < 8 )
			{
					alert('티칭허브는 IE8이상에 최적화 되어 있습니다. Internet exploler7 이하는 업그레이드 후 이용 바랍니다.');
			}	
		} 
		
		$("#passWd").keypress(function(key) {

			if (key.which == 13) {

				mainLogin();

			}
		});
		
	});

			
			function quickLogin()
			{
				var f = document.forms.loginForm;
				var cur_url = location.href;
				if( $('#save_id').is(':checked') ){
					$.cookie('thub_main_id' , $('input[name=userId]').val());
				}

				//f.action = 'https://cs.kumsung.co.kr:444/loginForm.do';	//운영
				//f.action = 'http://localhost/loginForm.do';		//개발
				//f.action = "http://localhost:8090/loginProc.do?userId="+$('input[name=userId]').val()+"&passWd="+encodeURIComponent($('input[name=passWd]').val());
				f.action = "/loginProc.do?userId="+$('input[name=userId]').val()+"&passWd="+encodeURIComponent($('input[name=passWd]').val())+"&returnUrl="+encodeURIComponent(cur_url);
				f.target = '_self';
				f.submit();
			}
			
			</script> 	
  	<% }else{ %>
		<div class="quickmenu_tp" style="display:block;">
			<img src="/assets/front/2017img/main/main15.png" alt="">
			<p>
						<% if( member.getAuthType().equals("S") || member.getAuthType().equals("N")){ %>
							<a href="/admin/learning/dataList.do" target="_blank"><%=member.getName() %>님</a>
						<% }else{ %>
							<%=member.getName() %>님
						<% } %>  			

			<br>환영합니다.</p>
			<p style="font-size:14px;padding-top:5px;">
				<%
				if( member.getIsFinallyAuth().equals("N")) {
				%>
					[교사인증 필요]
				<%	
				}
				%>
			</p>
			<a href="javascript:goLogout();">로그아웃</a>
			<ul>
				<li><a href="https://cs.kumsung.co.kr/member/mypage/myInfo.do" target="_blank" class="bg1">나의 정보</a></li>
				<li><a href="/myhub/scrap.do" class="bg2">나의 스크랩</a></li>
				<li><a href="/myhub/questionList.do" class="bg3">나의 질문</a></li>
			</ul>
		</div>

	<% } %> 
		<div class="quickmenu_bt">
			<ul>
				<li><a href="#n" onclick="javascript:itemPool();"><img src="/assets/front/2017img/main/main19.png" alt="문제은행"></a></li>
				<li><a href="//dic.kumsung.co.kr/web/main.do" target="_blank"><img src="/assets/front/2017img/main/main20.png" alt="티칭백과"></a></li>
				<li><a href="/customer/usage/usage1.do"><img src="/assets/front/2017img/main/main21.png" alt="이용안내"></a></li>
				<li><a href="/customer/customer.do?boardCategory=C003001"><img src="/assets/front/2019img/main/main22.png" alt="1:1문의"></a></li>
				<li><a href="javascript:handleCdDownload();"><img src="/assets/front/2017img/main/main23.png" alt="교사용 DVD"></a></li>
			</ul>
		</div>
		
	</div>	
<!-- component: 교사용 CD다운로드 팝업 -->
<div class="layerpopup_02 download-popup">
	<div class="popup_header">
		<h2><span class="bul_popup"></span><strong>교사용 DVD 다운로드</strong></h2>
		<div class="popup_tit_wrap">
			<ul class="popup_tit">
				<li class="on" data-parent="elBooksWrapperCd"><a href="javascript:showCdWrapper('elBooksWrapperCd' , this);">초등</a><span class="stem"></span></li>
				<li data-parent="mdBooksWrapperCd"><a href="javascript:showCdWrapper('mdBooksWrapperCd' , this);">중등</a><span class="stem"></span></li>
				<li data-parent="hgBooksWrapperCd"><a href="javascript:showCdWrapper('hgBooksWrapperCd' , this);">고등</a><span class="stem"></span></li>
			</ul>
		</div>
		<a href="javascript:handleCdDownload();" class="btn_close"><img src="/assets/front/2017img/img2017/btn/popup_close.png" width="12" height="12" alt="닫기"></a>
	</div>
	<div class="popup_main_wrap">
		<div class="popup_main">
			<ul class="popup_sub_warp">
				<span id="elBooksWrapperCd">
				<%
					idx = 1;
					loopCnt = elCdCategory.size();
					
					for(Category category : elCdCategory){
				%>
				<li <% if( idx == loopCnt){ %>class="last"<% } %> data-cd-category="<%=category.getCategory()%>"><a href="javascript:loadCdBooksContents('<%=category.getCategory() %>' , this);"><%=category.getName() %></a><span class="active"></span></li>
				<%
						idx++;
					} 
				%>
				</span>
				<span id="mdBooksWrapperCd" style="display:none;">
				<%
					idx = 1;
					loopCnt = mdCdCategory.size();
					
					for(Category category : mdCdCategory){
				%>
				<li <% if( idx == loopCnt){ %>class="last"<% } %> data-cd-category="<%=category.getCategory()%>"><a href="javascript:loadCdBooksContents('<%=category.getCategory() %>' , this);"><%=category.getName() %></a><span class="active"></span></li>
				<%
						idx++;
					} 
				%>
				</span>
				<span id="hgBooksWrapperCd"  style="display:none;">
				<%
					idx = 1;
					loopCnt = hgCdCategory.size();
					
					for(Category category : hgCdCategory){
				%>
				<li <% if( idx == loopCnt){ %>class="last"<% } %> data-cd-category="<%=category.getCategory()%>"><a href="javascript:loadCdBooksContents('<%=category.getCategory() %>' , this);"><%=category.getName() %></a><span class="active"></span></li>
				<%
						idx++;
					}
				%>
				</span>
			</ul>

			<span id="contentsSection"></span>
		</div>
	</div>
	<div class="popup_footer"></div>
</div>
<!-- component: 교사용 CD다운로드 팝업 -->
<form name="exam" id="exam" action="" method="post" >
<input type="hidden" name="param" value="{userid:'<%=member.getUserId()%>', username:'<%=member.getName() %>'}"/>
</form>        		
<script type="text/javascript">
<!--
function showCdDownloadPopup()
{
	<% if( member.getIsFinallyAuth().equals("Y")){ %>
	$(".download-popup").fadeIn();
	
	var loaded = false;
	
	$('.popup_sub_warp li').each(function(){
		if( $(this).hasClass('on') )
			loaded = true;
	});
	
	if( loaded == false )
		loadCdBooksContents('A001001');
	<% }else{ %>
	alert('교사인증회원만 열람 가능합니다.');
	<% } %>
}

function showCdWrapper(target){
	$('.popup_sub_warp span').css('display' , 'none');
	$('#' + target).css('display' , 'block');
	$('.popup_tit li').removeClass('on');
	//alert($.browser.msie );
	$('[data-parent=' + target + ']').addClass('on');
	/* if( $.browser.msie )
		//$('#' + target).addClass('on');
		$('[data-parent=' + target + ']').addClass('on');
	else
		$('[data-parent=' + target + ']').addClass('on'); */
}

function loadCdBooksContents(category)
{	
	$('.popup_sub_warp span li').removeClass('on');
	$('.popup_sub_warp').find('[data-cd-category=' + category+ ']').addClass('on');
	
	$('#contentsSection').load('/api/learning/ajax/getBookListWithCd.do?findCategory=' + category);
}

function getCdDownload(bookId)
{
	var cdId = $('select[data-book-id=' + bookId +'] option:selected').val();
	if( cdId != '' )
	{
		$.ajax({
			url : '/api/learning/getCdLink.do' ,
			type : 'post' ,
			data : 'cd_id=' + cdId ,
			dataType : 'json' ,
			error : function(){
				alert('서버와의 통신도중 에러가 발생되었습니다.');
			},
			success : function(json){
				if( json.result == 'SUCCESS' )
					location.href='/commons/file/getCdFile.do?filePath=/upfiles' + escape(json.url);
				else
					alert(json.msg);
			}
		});
	}
}

//-->
</script>	
            