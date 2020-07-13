<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	int dispId = (Integer) request.getAttribute("dispId");
	String mode = (String)request.getAttribute("mode");
	String type = (String)request.getAttribute("type");
	List<MainData> list = (List<MainData>) request.getAttribute("list");
	MainData conf = (MainData) request.getAttribute("maindata");
	int imgWidth = conf.getdispWidth();
	int imgHeight = conf.getdispHeight();
	String dataSort = ("desc".equals(conf.getdispSort()))?"최신":"순서대로";
	String dataLimit = (conf.getdispLimit()==0)?"모두":conf.getdispLimit()+"개";

%>



<div>
이미지 사이즈 : <%=imgWidth %>*<%=imgHeight %> <br>
* <%=dataSort %> 자료가 <%=dataLimit %> 노출됩니다.
</div>
	
<!-- 본문 시작 -->
<div class="well">

	<form name="uform" action="" method="post">
	<table class="table" >
		<col width="200"/>
		<col width="150"/>
		<col width="*" />
		<col width="100"/>
		<col width="130"/>
		<col width="100"/>
		<col width="100"/>
		<thead>
		<tr>
			<th rowspan="2">번호</th>
			<th rowspan="2">이미지</th>
			<th align="left">제목</th>
			
			<th rowspan="2">새창여부</th>
			<th rowspan="2">등록일</th>
			<th rowspan="2">삭제</th>
			<th rowspan="2">수정</th>
		</tr> 
		<tr>
			<th align="left">링크</th>
		</tr>		
		</thead>
		<tbody>

			<%
			int idx = 1;
				for(MainData data : list){
				// for reply articles
			%>
				<input type="hidden" name="dispId" value="<%=data.getdispId()%>" />
				<tr>
					<td rowspan="2"><%=idx %></td>
					<td rowspan="2"><img src="<%=data.getImgurl()%>" /></td>
					<td align="left"><input type="text" style="width:350px;" name="dispSubj" id="dispSubj" value='<%=data.getdispSubj()%>'></td>
					
					<td rowspan="2">
					<select name="dispTarget" style="width:80px;">
						<option value="_self" <% if(data.getdispTarget().equals("_self")){ %>selected<%} %>>_self</option>
						<option value="_blank" <% if(data.getdispTarget().equals("_blank")){ %>selected<%} %>>_blank</option>
					</select>
		
					<td rowspan="2"><%=data.getRegDate()%></td>
					<td rowspan="2"><a href="javascript:delData('<%=data.getdispId()%>');">삭제</a></td>
					<td rowspan="2"><a href="javascript:UpData('<%=data.getdispId()%>');">수정</a></td>
				</tr>
				<tr>
					<td align="left"><textarea name="dispLink" style="width:98%;height:60px;"><%=data.getdispLink()%></textarea><a href="javascript:choice_sel('<%=data.getdispId()%>','U','<%=type%>');">위</a> <a href="javascript:choice_sel('<%=data.getdispId()%>','D','<%=type%>');">아래</a></td>
				</tr>
				
			<%	
			idx++;
				}
			%>

		</tbody>
	</table>
	</form>
</div>


<div>


<form name="wform" action="" method="post" enctype="multipart/form-data">
	<input type="hidden" name="mode" value="add" />
	<input type="hidden" name="type" value="<%=type %>" />
	<input type="hidden" name="dispId" value="" />
<table class="table" >
		<col width="100"/>
		<col width="200"/>
		<col width="*"/>
		<col width="80" />
		<col width="80" />
		<thead>
		<tr>
			<th>이미지</th>
			<th>제목</th>
			<th>링크</th>
			<th>새창여부</th>		
			<th>등록</th>					
		</tr> 
		<tr>
			<td><input type="file" name="upfile"/></td>
			<td><input type="text" name="dispSubj" /></td>
			<td><input type="text" name="dispLink" /></td>
			<td>
				<select name="dispTarget"  style="width:80px">
					<option value="_self">기본</option>
					<option value="_blank">새창</option>
				</select>
			</td>
			<td><button class="btn btn-primary" onclick="checkForm();"> 저장</button></td>
		</tr>		
</table>
</form>
</div>

<!-- 스크립트 영역 -->
<script>
<!--

function checkForm()
{
	var f = document.forms.wform;
	
	if( f.dispLink.value == '' ) return alert('링크주소를 확인해주세요.');
	if( f.upfile.value == '' ) return alert('이미지를 업로드해주세요.');
	

	f.target = '_self';
	f.action = 'bannerAction.do';
	f.submit();
}

function delData(id)
{
	if( confirm('삭제된 자료는 복구할 수 없습니다.\r\n정말로 삭제하시겠습니까?') )
	{
		$.ajax({
			url : 'deleteBanner.do' ,
			type : 'post' ,
			data : 'dispId='+id ,
			dataType : 'json' ,
			error : function(){
				alert('서버와 통신도중 에러가 발생하였습니다.');
			},
			success : function(json){
				if( json.result == 'SUCCESS' )
				{
					location.reload();
				}
				else
					alert(json.msg);
			}
		});
	}

}
function UpData(id, ukey)
{
	var dpSubj = "";
	var dpTgt = "";
	var dpLnk = "";
	
	var f = document.forms.uform;
		
    var array_dispId = document.getElementsByName("dispId");
    var array_dispSubj = document.getElementsByName("dispSubj");
	var array_dispTarget = document.getElementsByName("dispTarget");
	var array_dispLink = document.getElementsByName("dispLink");
    	
	for (var i = 0; i < array_dispSubj.length; i++) {
			if(array_dispId[i].value == id) {
		    	dpSubj = array_dispSubj[i].value;
		    	dpTgt = array_dispTarget[i].value;
		    	dpLnk = array_dispLink[i].value;
		}
	}
	
	if( confirm('수정하시겠습니까?') )
	{
		
		$.ajax({
			url : 'updateBanner.do' ,
			type : 'post' ,
			//data : $('form[name=uform]').serialize(),
			data : {dispId:id,dispSubj:dpSubj,dispTarget:dpTgt,dispLink:dpLnk},
			dataType : 'json' ,
			error : function(){
				alert('서버와 통신도중 에러가 발생하였습니다.');
			},
			success : function(json){
				if( json.result == 'SUCCESS' )
				{
					location.reload();
				}
				else
					alert(json.msg);
			}
		});
	}
}
function choice_sel(id,gbd,type)
{
	var v = gbd;
	var type = type;

		$.ajax({
			url : 'selectBanner.do' ,
			type : 'post' ,
			data : {dispId:id,gb:v,type:type},
			dataType : 'json' ,
			error : function(){
				alert('서버와 통신도중 에러가 발생하였습니다.');
			},
			success : function(json){
				if( json.result == 'SUCCESS' )
				{
					location.reload();
				}
				else
					alert(json.msg);
			}
		});

}

$(document).ready(function(){

})
-->
</script>