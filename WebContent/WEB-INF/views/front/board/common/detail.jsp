<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	int pg = (Integer) request.getAttribute("pg");
	int boardType = Common.getParameter(request, "boardType", 2);
	String viewType =  Common.getParameter(request, "viewType", "1");
	int findCategory = (Integer) request.getAttribute("findCategory");
	String findMethod = (String) request.getAttribute("findMethod");
	String findStr = (String) request.getAttribute("findStr");
	String mode = (String) request.getAttribute("mode");

	BoardConfig boardConfig = (BoardConfig) request.getAttribute("boardConfig");
	Article article = (Article) request.getAttribute("article");
	List<FileInfo> attachments = (List<FileInfo>) request.getAttribute("attachments");
	Article prevArticle = (Article) request.getAttribute("prevArticle");
	Article nextArticle = (Article) request.getAttribute("nextArticle");
	List<Comment> comments = (List<Comment>) request.getAttribute("comments");

	// get session 
	Member member = (Member) session.getAttribute("member");
	if( member == null ) member = new Member();
	
	boolean isBoardAccess = (Boolean) request.getAttribute("isBoardAccess");
	
	int idx = 0;			// temporary var
	
	boolean goLogin = false;
	
	if( boardConfig.getUseAnnmsView().equals("N") )
	{
		if( Validate.isEmpty(member.getUserId()))
			goLogin = true;
	}
%>
<%//=boardConfig.getHeaderSection() %>

<% if( goLogin == false ){ %>
<!-- for facebook -->
<meta property="og:image" content="http://121.78.122.133/assets/front/img/common/logo_facebook.png"/>
<meta property="og:title" content="<%=article.getSubject()%>"/>
<meta property="og:site_name" content="티칭허브"/>
<meta property="og:description" content="<%=article.getListContents()%>"/>

                            <!-- view -->
                            <div class="view_wrap">
                                <div class="view_container">
                                    <div class="view_title_box">
                                        <div class="view_title">
                                              <%=article.getSubject() %>
                                            <div class="sns_btn">
		<% if( boardConfig.getUseSns().equals("Y")){ %>
		<a href="javascript:share('twitter');"><img src="/assets/front/2017img/btn/twitter.png" alt="트위터" /></a>
		<a href="javascript:share('facebook');"><img src="/assets/front/2017img/btn/facebook.png" alt="페이스북" /></a>
		<!-- <a href="javascript:share('me2day');"><img src="/assets/front/img/icon/icon_metoday.gif" alt="미투데이" /></a> -->
		<!-- <a href="javascript:share('cyworld');"><img src="/assets/front/img/icon/icon_cyworld.gif" alt="싸이월드" /></a> //-->
		<% } %>
		<a href="javascript:copy();" class="btn urlBtn">URL 복사</a>

                                            </div>
                                        </div>
                                        

	                                        
                                        <div class="view_sub">
                                            작성자
                                            <span><%=article.getUserName() %>(<%=article.getUserIdSecret() %>)</span>
                                            조회수
                                            <span><%=article.getHits() %></span>
											<% if( boardConfig.getUseRecommend().equals("Y")){ %>
											추천수 
										 	<span><%=article.getRecommend() %></span>
											<% } %>                                            

                                            <% if( boardConfig.getModifyRegDate().equals("Y")){ %>
                                             등록일  
                                            <span><%=article.getRegDateFormat() %></span>
                                             <% } %>
											<% if( !Validate.isEmpty(article.getCategoryName())){ %>
											분류 <span id="view_sub_last"><%=article.getCategoryName() %></span>
											<% } %>                                            

                                        </div>
                                    </div>


                           
                                    <div class="view_contents">
                                        <%=article.getContents() %>
										<% if( !Validate.isEmpty(article.getEtc())){ %>

											<%=article.getEtc() %>

										<% } %> 
										<% if( member != null && member.getUserId().equals(article.getUserId()) ){ %>

											검색키워드 <%=article.getKeywords() %>
									
										<% } %>
										<% if( !Validate.isEmpty(article.getLinkUrl())){ %>

											링크 <a href="<%=article.getLinkUrl()%>" target="_blank"><%=article.getLinkUrl() %></a>
								
										<% } %>
			
                                    </div>
                                   <% for(FileInfo attachment : attachments){	%>
                                    <div class="attach_box">
                                        <span>첨부파일</span>
										
											<% long mb = attachment.getFileSize();  %>
											<%if(mb > 102400){ %>
												<%mb= mb/1024; %>
												<a href="javascript:getBoardAttachmentFile('<%=attachment.getFileId()%>');" class="attach_file"><%=attachment.getOriginName() %> (<%=mb%> Mbytes)</a>
											<%}else{ %>
												<a href="javascript:getBoardAttachmentFile('<%=attachment.getFileId()%>');" class="attach_file"><%=attachment.getOriginName() %> (<%=attachment.getFileSize()%> Kbytes)</a>
											<%} %>
										                                       

                                    </div>
                                    <% } %>  
                                </div>
                                
                                <div class="view_btn_box">
                                    <div class="left_btn">
                                        <a href="javascript:goPrint();" class="btn printBtn">
                                            <span>인쇄</span>
                                        </a>
                                        <a href="javascript:scrap('ARTICLE','<%=article.getArticleId()%>');" class="btn scrapBtn">
                                            <span>스크랩</span>
                                        </a>
										<% if( boardConfig.getUseRecommend().equals("Y")){ %>
										<a href="javascript:recommend();" class="btn recomBtn">
											<span>추천</span>
										</a>
										<% } %>                                        
                                    </div>
                                    <div class="right_btn">
									<% if( isBoardAccess || ( boardConfig.getBoardId() == 27 || boardConfig.getBoardId() == 28 || boardConfig.getBoardId() == 29)){ %>
										<% if( isBoardAccess || article.getUserId().equals(member.getUserId())){ %>		
											<a href="javascript:goModify('<%=article.getArticleId()%>');" class="btn bigmodifyBtn">수정</a>		
											<a href="javascript:deleteArticle('<%=article.getArticleId()%>');" class="btn bigdeleteBtn">삭제</a>
										<% } %>
									<% } %>

                                        <a href="javascript:goList();" class="btn biglistBtn pink">목록</a>
                                    </div>
                                </div>
                            </div>
                            <!--// view -->
                            <!-- 댓글 -->
<% if( boardConfig.getUseComment().equals("Y")){ %>                            
                            <div class="reply_wrap">
                            
                                <div class="reply_write">
                                  	  댓글
                                    <span><%=comments.size() %></span>
                                    <div class="reply_write_box">
										<form name="commentForm" action="" method="post" onsubmit="addComment();return false;">
										<input type="hidden" name="boardId" value="<%=article.getBoardId()%>"/>
										<input type="hidden" name="articleId" value="<%=article.getArticleId() %>"/>
										<input type="hidden" name="commentId"/>                                    
                                        <textarea name="comment" id="comment" placeholder="댓글을 입력하세요."></textarea>
                                        <a href="javascript:addComment();">등록</a>
										</form>                                        
                                    </div>
                                </div>

                                <div class="reply_list">
                                    <ul>
	<%
		idx = 0;
	
		for(Comment comment : comments){
	%>

		<% if( comment.getDepth() == 1 ){ %>
			<% if( comment.getIsDel().equals("Y")) { %>
				<li>
					삭제된 댓글입니다.
				</li>
			<% }else{ %>
                                        <li id="comment-<%=comment.getCommentId()%>">
                                            <div>
                                                <div class="reply_top">
                                                    <span><%=comment.getUserIdSecret() %></span>
                                                    <%=comment.getRegDateFormat() %>
                                                    <div class="reply_btn">
                                                        <a href="#none" class="btn replyBtn">답변</a> <!-- '답변' 버튼을 클릭하면 대댓글 입력창이 나옵니다. -->
                                                        
													<% if( comment.getUserId().equals(member.getUserId())){ %>
													<a href="#none" onclick="deleteComment('<%=comment.getCommentId() %>');" class="btn deleteBtn">삭제</a>
													<% } %>                                                        
                                                    </div>
                                                </div>
                                                <div class="reply_cont">
                                                    <%=comment.getComment() %>
                                                </div>
                                            </div>
                                            <div class="re_reply_write">
                                                <textarea name="comment-<%=comment.getCommentId()%>" id="comment-<%=comment.getCommentId()%>" placeholder="댓글을 입력하세요."></textarea>
                                                <a href="javascript:replyComment('<%=comment.getCommentId()%>');">등록</a>
                                            </div>
                                        </li>
                                        			

			<% } %>
		<% }else{ %>
                                   <li class="re_reply">
                                            <div>
                                                <div class="re_reply_top">
                                                    <span><%=comment.getUserIdSecret() %></span>
                                                    <%=comment.getRegDateFormat() %>
                                                    <div class="re_reply_btn">
													<% if( comment.getUserId().equals(member.getUserId())){ %>
													 <a href="javascript:deleteComment('<%=comment.getCommentId() %>');" class="btn deleteBtn">삭제</a>
													<% } %>
					                                                    
                                                       
                                                    </div>
                                                </div>
                                                <div class="re_reply_cont">
                                                    <%=comment.getComment() %>
                                                </div>
                                            </div>
                                        </li>
                                        		

		<% } %>

	<%
			idx++;
		} 
	%>
	                                
                                    </ul>
                                
                            </div>
                            <!--// 댓글 -->
    <% } %> 


                            <div class="move_box">
   	<% if( prevArticle != null){ %>
                                <div class="move_prev">
                                    <span>다음글</span>
									<a href="javascript:goDetail('<%=prevArticle.getArticleId()%>');"><%=prevArticle.getSubject() %></a>
								</div>
	<% } %>  
	<% if( nextArticle != null){ %>
                                <div class="move_next">
                                    <span>이전글</span>
									<a href="javascript:goDetail('<%=nextArticle.getArticleId()%>');"><%=nextArticle.getSubject() %></a>
                              </div>
	<% } %>

                            </div>                                      
                                        
 

<form name="listForm" action="" method="get">
<input type="hidden" name="mode" value="list"/>
<input type="hidden" name="pg" value="<%=pg%>"/>
<input type="hidden" name="findCategory" value="<%=findCategory%>"/>
<input type="hidden" name="findMethod" value="<%=findMethod%>"/>
<input type="hidden" name="findStr" value="<%=findStr%>"/>
<input type="hidden" name="boardType" value="<%=boardType%>"/>
<input type="hidden" name="viewType" value="<%=viewType%>"/>
</form>

<form name="detailForm" action="" method="get">
<input type="hidden" name="articleId"/>
<input type="hidden" name="mode" value="detail"/>
<input type="hidden" name="pg" value="<%=pg%>"/>
<input type="hidden" name="findCategory" value="<%=findCategory%>"/>
<input type="hidden" name="findMethod" value="<%=findMethod%>"/>
<input type="hidden" name="findStr" value="<%=findStr%>"/>
<input type="hidden" name="boardType" value="<%=boardType%>"/>
<input type="hidden" name="viewType" value="<%=viewType%>"/>
</form>

<form name="deleteForm" action="" method="post">
<input type="hidden" name="articleId" value="<%=article.getArticleId()%>"/>
<input type="hidden" name="pg" value="<%=pg%>"/>
<input type="hidden" name="findCategory" value="<%=findCategory%>"/>
<input type="hidden" name="findMethod" value="<%=findMethod%>"/>
<input type="hidden" name="findStr" value="<%=findStr%>"/>
<input type="hidden" name="boardType" value="<%=boardType%>"/>
<input type="hidden" name="viewType" value="<%=viewType%>"/>
</form>

<% } %>

<script type="text/javascript">
<!--

<% if( goLogin == true ){ %>
$(document).ready(function(){
	goLoginE();
});
<% } %>

function goModify(articleId)
{
	var f = document.forms.detailForm;
	f.articleId.value = articleId;
	f.mode.value = 'form';
	f.submit();
}

function addComment()
{	
	var f = document.forms.commentForm;
	
	if( f.comment.value == '' ) return doError('내용을 입력해주십시오.' , 'comment' , 'commentForm');
	
	$.ajax({
		url : '/api/article/addComment.do' ,
		type : 'post' ,
		data : $('form[name=commentForm]').serialize() ,
		dataType : 'json' ,
		error : function(){
			alert('서버와 통신을 할 수 없습니다.');
		},
		success : function(json){
			if( json.result == 'SUCCESS' )
			{	
				f.reset();
				$('textarea').val('');
				location.reload();
			}
			else
			{
				if( json.resultType == 'LOGIN' )
				{
					if( confirm('로그인이 필요합니다.\r\n로그인 하시겠습니까?') )
					{
						goLoginE();
					}
				}
				else
					alert(json.msg);
			}
		}
	});
}

function replyComment(commentId)
{
	if( $('textarea[name=comment-' + commentId + ']').val() == '' )
		return alert('내용을 입력하여주십시오.');
	
	var f = document.forms.commentForm;
	
	f.comment.value = $('textarea[name=comment-' + commentId + ']').val();
	f.commentId.value = commentId;
	
	addComment();
}

function deleteComment(commentId)
{
	if( confirm('정말로 삭제하시겠습니까?') )
	{
		$.ajax({
			url : '/api/article/deleteComment.do' ,
			type : 'post' ,
			data : 'boardId=<%=article.getBoardId() %>&articleId=<%=article.getArticleId()%>&commentId=' + commentId ,
			dataType : 'json' ,
			error : function(){
				alert('서버와의 통신도중 에러가 발생되었습니다.');
			},
			success : function(json){
				if( json.result == 'SUCCESS' )
				{
					location.reload();
					//$('#comment-' + json.commentId).remove();
				}
				else
					alert(json.msg);
			}
		});
	}
}

function goDetail(articleId)
{
	var f = document.forms.detailForm;
	f.articleId.value = articleId;
	f.submit();
}

function recommend()
{
	$.ajax({
		url : '/api/article/recommend.do',
		type : 'post' ,
		data : 'articleId=<%=article.getArticleId()%>' ,
		dataType : 'json' ,
		async : true ,
		error : function(){
			alert('서버와 통신도중 에러가 발생되었습니다.');
		},
		success : function(json){
			if( json.result == 'SUCCESS' )
			{
				alert('추천을 하였습니다.');
			}
			else
			{
				if( json.resultType == 'LOGIN' )
				{
					if( confirm('로그인이 필요합니다.\r\n로그인 하시겠습니까?') )
					{
						goLoginE();
					}
				}
				else
					alert(json.msg);
			}
		}
	});
}

function goList()
{
	var f = document.forms.listForm;
	f.submit();
}

function deleteArticle()
{
	if( confirm('삭제하신 데이타는 복구할 수 없습니다.\r\n진행하시겠습니까?') )
	{
		$.ajax({
			url : '/common/board/deleteAction.do' ,
			type : 'post' ,
			data : $('form[name=deleteForm]').serialize() ,
			dataType : 'json' ,
			error : function(){
				alert('서버와의 통신도중 에러가 발생되었습니다.');
			},
			success : function(json){
				if( json.result == 'SUCCESS' )
				{
					goList();
				}
				else
					alert(json.msg);
			}
		});
	}
}

function goPrint()
{
    if( navigator.userAgent.indexOf("MSIE") > 0 ){
    	var OLECMDID = 7;
        /* OLECMDID values:
        * 6 - print
        * 7 - print preview
        * 1 - open window
        * 4 - Save As
        */
        var PROMPT = 1; // 2 DONTPROMPTUSER 
        var WebBrowser = '<OBJECT ID="WebBrowser1" WIDTH=0 HEIGHT=0 CLASSID="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2"></OBJECT>';
        document.body.insertAdjacentHTML('beforeEnd', WebBrowser);
        WebBrowser1.ExecWB( OLECMDID, PROMPT);
        WebBrowser1.outerHTML = "";
      } else if( navigator.userAgent.indexOf("Chrome") > 0){
        window.print();    	
    }
}
function goLoginE(){
	var cur_url = location.href;

	cur_url = cur_url.replace("http:", "https:");

	//location.href='https://cs.kumsung.co.kr/index.do?returnUrl=' + location.href;
	location.href='http://cs.kumsung.co.kr/index.do?returnUrl=' + cur_url;
	//location.href='http://cs.kumsung.co.kr/index.do?returnUrl=https://thub.kumsung.co.kr';
}
//-->
</script>