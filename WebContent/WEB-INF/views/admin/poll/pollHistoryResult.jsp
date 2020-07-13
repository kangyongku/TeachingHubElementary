<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	int pg = (Integer) request.getAttribute("pg");

	String findMethod = (String) request.getAttribute("findMethod");
	String findStr = (String) request.getAttribute("findStr");

	List<PollResponse> list = (List<PollResponse>) request.getAttribute("list");
	Poll poll  = (Poll) request.getAttribute("poll");
%>
<style>
.main_box{border:1px solid #cecece;}
.main_box dt{color:#333333;font-size:12px;padding:10px 20px;background:#f3f3f3;border-bottom:1px solid #cecece;}
.main_box dt strong{margin-right:5px;}
.main_box dd{padding:20px;}
.main_box dd ul li{margin-top:10px;background:url(/assets/front/img/bul/list.gif) no-repeat 0 5px;padding-left:6px;}
.main_box dd ul li:first-child{margin-top:0;}
.blind{display:block;overflow:hidden;position:absolute;top:-1000em;left:0}
/**.layerpopup{width:568px;height:750px;position:absolute;z-index:1000;left:50%;top: 10%;margin-left:-284px;display:none;overflow:scroll;}**/
.layerpopup{width:568px;height:750px;position:fixed;z-index:1000;left:55%;top: 5%;margin-left:-20%;margin-top:0px;display:none;/*overflow:auto;*/word-break:break-all;/*overflow-X:hidden;*/}
.layerpopup .lp_header{height:59px;background:url(/assets/front/img/etc/popup_header.png) 0 0 no-repeat;position:relative;}
.layerpopup .lp_header h2{font-size:14px;font-weight:bold;color:#fff;letter-spacing:-1px;padding:15px 0 0 29px;}
.layerpopup .lp_header a.btn_close{position:absolute;right:29px;top:28px;background:url(/assets/front/img/btn/popup_close.png) no-repeat 0 0;height:12px;width:12px;display:inline-block;}

.layerpopup .lp_main{background:url(/assets/front/img/etc/popup_loop.png) 0 0 repeat-y;padding:25px 29px;}
.layerpopup .lp_main .lp_main_header{padding-bottom:10px;border-bottom:solid 1px #c6c6c6;margin-bottom:20px;}
.layerpopup .lp_main .lp_main_header:after{content:"";display:block;clear:both;}
.layerpopup .lp_main .lp_main_header h3{float:left;font-size:14px;font-weight:bold;letter-spacing:-1px;color:#444;}
.layerpopup .lp_main .lp_main_header p{float:right;padding-top:3px;}

.layerpopup .lp_main .carousel_wrap{position:relative;}
.layerpopup .lp_main .carousel_wrap:after{content:"";display:block;clear:both}
.layerpopup .lp_main .carousel_wrap ul{width:401px;margin:0 auto;}

.layerpopup .lp_main .carousel_wrap .btn a{position:absolute;top:135px;width:20px;height:31px;text-indent:-999em}
.layerpopup .lp_main .carousel_wrap .btn.left a{background:url(/assets/front/img/btn/left_on.png) no-repeat 0 0;left:0;}
.layerpopup .lp_main .carousel_wrap .btn.left a.disabled{background:url(/assets/front/img/btn/left.png) no-repeat 0 0;}
.layerpopup .lp_main .carousel_wrap .btn.right a{background:url(/assets/front/img/btn/right_on.png) no-repeat 0 0;right:0;}
.layerpopup .lp_main .carousel_wrap .btn.right a.disabled{background:url(/assets/front/img/btn/right.png) no-repeat 0 0;}

.layerpopup .lp_main .carousel_wrap .image{margin-bottom:15px;}
.layerpopup .lp_main .carousel_wrap .desc{line-height:18px;color:#888;letter-spacing:-1px;}

.layerpopup .lp_footer{height:13px;background:url(/assets/front/img/etc/popup_footer.png) 0 0 no-repeat;}

.layerpopup.small{width:416px;position:absolute;top:340px;left:50%;margin-left:-210px;z-index:1000;}
.layerpopup.small .lp_header{height:58px;background:url(/assets/front/img/etc/popup_header_small.png) no-repeat 0 0;}
.layerpopup.small .lp_main{background:url(/assets/front/img/etc/popup_body_small.png) repeat-y;}
.layerpopup.small .lp_footer{background:url(/assets/front/img/etc/popup_footer_small.png) no-repeat 0 0;}

.layerpopup.small .lp_main .btn{text-align:center;margin-top:20px;}
.layerpopup .lp_main_body .search_box{width:275px;border:1px solid #d8d8d8;padding:6px 10px;float:left;border-right:0;}
.layerpopup .lp_main_body input{vertical-align:middle;}

.percent{display:inline-block;height:11px;position:relative; display:inline-block;width:450px;}
.percent.yes{background:url(/assets/front/img/common/img_yes.png) repeat-x;width:69%;}
.layerpopup .lp_main #layerContents{overflow:auto;overflow-X:hidden;height:450px;}
</style>

<!-- component: layer popup -->
		<div class="layerpopup" style="display:none">
			<div class="lp_header">
				<h2>설문결과</h2>
				<a href="javascript:hideLayer()" class="btn_close"><span class="blind">닫기</span></a>
			</div>
			<div class="lp_main">
				<dl class="main_box">
					<dt><strong>질문 <span id="tidx">2</span></strong> <p id="layerTitle"></p></dt>
					<dd>
						<ul id="layerContents">
						</ul>
					</dd>
				</dl>
				<div class="carousel_wrap"><a href="javascript:hideLayer()" style="float:right;">닫기</a></div>
			</div>
			
			<div class="lp_footer"></div>
		</div>
		
		
<table class="table table-bordered" >
	<col width="80"/>
	<col width="350"/>
	<col width="100"/>
	<col width="50"/>
	<col width="50"/>
<%
	int beforeId = 0;
	int beforeEntryId = 0;
	int beforeEntryIded = 0;
	int tIndex = 1;
	int qIndex = 1;
	for(PollResponse pollRes : list){
	// for reply articles
%>

	<%
		if( beforeId != pollRes.getEntryId() ){
			qIndex = 1;
			beforeEntryId = pollRes.getEntryId();
	%>
		<tr style="border:0px"><td colspan="5"></td></tr>
		<tr>
			<td>
				<strong>질문<%=tIndex %></strong>
			</td>
			<td colspan="4">
				<%=pollRes.getTitle() %> 
				<%if ( pollRes.getType().equals("W") ){ %> [주관식] <%} %>
				<%if ( pollRes.getType().equals("M") ){ %> [객관식] <%} %>
				<%if ( pollRes.getType().equals("D") ){ %> [객관식(중복선택)] <%} %>
			</td>
		</tr>
	<%
		tIndex++;
		}
	%>
		<%
			if( beforeEntryId == pollRes.getEntryId() ){
		%>
			<%
				if( pollRes.getType().equals("M")||pollRes.getType().equals("D") ){
			%>
				<tr>
					<td><%=qIndex %></td>
					<td><%=pollRes.getAnswer() %><% if(pollRes.getAnswer().equals("기타")&&pollRes.getAnswerCnt()>0){%>&nbsp;&nbsp;<a href="javascript:goLayer('<%=pollRes.getEntryId() %>','<%=pollRes.getItemId() %>','<%=tIndex %>');" class="btn_result">[기타 의견]<!--img src="/assets/front/img/btn/result02.gif" alt="기타 의견 보기" /> --></a><%} %></td>
					<td>
						<p class="percent yes" style="width:<%=Math.round((((double)pollRes.getAnswerCnt()/(double)pollRes.getEntryCnt()) * 100 ))%>%"></p>
					</td>
					<td>
						<%=Math.round((((double)pollRes.getAnswerCnt()/(double)pollRes.getEntryCnt()) * 100 ))%>% 
					</td>
					<td>
						<%=pollRes.getAnswerCnt() %>명
					</td>
				</tr>

			<%
				} else if ( pollRes.getType().equals("W") ) {
			%>			
				<tr>
					<td colspan="5"><a href="javascript:goLayer('<%=pollRes.getEntryId() %>','0','<%=tIndex %>');" class="btn_result"><img src="/assets/front/img/btn/result02.gif" alt="주관식 설문 결과보기" /></a></td>
				</tr>
			<%
				}
			%>
		<%
			qIndex++;
			}
		%>
<%	
	beforeId = pollRes.getEntryId();
	}
%>
</table>

<div class="write_btn">
	<a href="javascript:goList()"><img src="/assets/front/img/btn/btn_list.gif" alt="목록"></a>
</div>


<form name="listForm" method="post" action="pollHistoryList.do">
<input type="hidden" name="pollId" value="<%=poll.getPollId() %>" />
</form>
<!-- 스크립트 영역 -->
<script type="text/javascript">
<!--

function goList()
{
	var f = document.forms.listForm;
	f.submit();
}

function goLayer(entryId, itemId, tIndex){
	$("#layerContents").html("");
	$.ajax({
		url : '/happy/poll/getPollResultLayer.do',
		type : 'post',
		data : 'entryId=' + entryId + '&itemId=' +itemId + '&pollId=' + $("input[name=pollId]").val(),
		dataType : 'json',
		error : function(){
			alert('서버와의 통신도중 에러가 발생되었습니다.');
		},
		success : function(json){
			if( json.result == 'SUCCESS' ){
			
				if( json.poll.length > 0)
				{
					var jsonObj = json.poll;
					$("#layerTitle").text(poll.title);
				}
				if( json.pollRes.length > 0){
					var appendStr = "";
					for(var i=0; i<json.pollRes.length; i++){
						var jsonObj = json.pollRes[i];
						appendStr += "<li>" + jsonObj.answer ;
						if( jsonObj.answerCnt > 1 )
							appendStr += "&nbsp" + jsonObj.answerCnt + "명";
						appendStr += "</li><br/>"	
					}
					$("#layerContents").html(appendStr);
				}
				
				$("#tidx").text(tIndex-1);
				showLayer();
			}	
				
			if( json.result == 'FAILURE' )					
				alert(json.msg);
		}
	});
}

//레이어를 노출시킨다.
function showLayer(){
	$(".layerpopup").fadeIn(300);
}

function hideLayer(){
	$(".layerpopup").fadeOut(300);
}

//-->
</script>
