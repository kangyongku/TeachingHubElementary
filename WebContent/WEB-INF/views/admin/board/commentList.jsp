<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	int pg = (Integer) request.getAttribute("pg");
	int findBoardId = (Integer) request.getAttribute("findBoardId");

	int totalCount = (Integer) request.getAttribute("totalCount");
	int articleNum = (Integer) request.getAttribute("articleNum");
	List<BoardConfig> boards = (List<BoardConfig>) request.getAttribute("boards");
	List<Comment> comments = (List<Comment>) request.getAttribute("comments");
	
	String paging = (String) request.getAttribute("paging");
%>
<div class="well">
	
	<div style="margin:10px;">
		<select name="findBoardId" onchange="goSearchBoardComment(this);">
			<option value="">전체</option>
			<% for(BoardConfig boardConfig : boards){ %>
			<option value="<%=boardConfig.getBoardId()%>" <% if( boardConfig.getBoardId() == findBoardId){ %>selected<% } %>><%=boardConfig.getBoardName() %></option>
			<% } %>
		</select>
	</div>
	
	<table class="table">
	<col width="*"/>
	<col width="300"/>
	<tbody>
	<% 
		if( totalCount > 0){
			for(Comment comment : comments){
	%>
	<tr>
		<td><strong><%=comment.getUserName() %>(<%=comment.getUserId() %>)</strong> <%=comment.getRegDate() %></td>
		<td style="text-align:right;">
			<a class="btn btn-primary" href="/admin/board/form.do?boardId=<%=comment.getBoardId() %>&articleId=<%=comment.getArticleId() %>" target="_blank">게시글 바로가기</a>
			<a class="btn btn-danger" href="javascript:commentDelete('<%=comment.getCommentId()%>');">삭제</a>
		</td>
	</tr>
	<tr>
		<td colspan="2" style="border:0px;">
			<%=comment.getComment() %>
		</td>
	</tr>
	<%
			}
		}else{
	%>
	<tr>
		<td colspan="2" style="text-align:center;height:50px;"> 댓글이 없습니다.</td>
	</tr>
	<%
		}
	%>
	</tbody>
	</table>
</div>
<div class="pagination">
	<ul>
		<%=paging %>
	</ul>
</div>

<form name="listForm" action="" method="get">
<input type="hidden" name="pg"/>
<input type="hidden" name="findBoardId" value="<%=findBoardId%>"/>
</form>

<script type="text/javascript">
<!--

function goSearchBoardComment(obj)
{
	location.href='commentList.do?findBoardId=' + obj.value;
}

function goList(pg)
{
	var f = document.forms.listForm;
	f.pg.value = pg;
	f.submit();
}

function commentDelete(commentId)
{
	if( confirm('정말로 삭제하시겠습니까?') )
	{
		$.ajax({
			url : 'commentDelete.do' ,
			type : 'post' ,
			data : 'commentId=' + commentId ,
			dataType : 'json' ,
			error : function(){
				alert('서버와의 통신도중 에러가 발생되었습니다.');
			},
			success : function(json){
				if( json.result == 'SUCCESS' )
				{
					location.reload();
				}
				else
					alert(json.msg);
			}
 		})
	}
}

//-->
</script>

