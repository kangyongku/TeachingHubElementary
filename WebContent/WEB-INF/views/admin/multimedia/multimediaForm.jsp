<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%
	String mode = (String)request.getAttribute("mode");
	List<Category> categories = (List<Category>) request.getAttribute("categories");
	List<Category> originCodes = (List<Category>) request.getAttribute("originCodes");
	Multimedia multimediaMap = (Multimedia) request.getAttribute("multimediaMap");
	int pg = (Integer)request.getAttribute("pg");
	int mmId = (Integer)request.getAttribute("mmId");
	
	String findCategory = (String) request.getAttribute("findCategory");
	String findStr = (String) request.getAttribute("findStr");
	String findMethod = (String) request.getAttribute("findMethod");
	String findStartDate = (String) request.getAttribute("findStartDate");
	String findEndDate = (String) request.getAttribute("findEndDate");
	String findIsUse = (String) request.getAttribute("findIsUse");
	
	//이미지일경우 Thumb에 원본 파일을 셋업.
	String masterCategory = "";
	if( multimediaMap.getCategory().length() > 3)
		masterCategory = multimediaMap.getCategory().substring(0,4);
		
%>
<form name="lform" action="" method="post">
	<input type="hidden" name="pg" value="<%=pg %>"/>
</form>

<form name="dform" action="" method="post">
	<input type="hidden" name="mmId" value="<%=mmId %>">
</form>


<form name="qform" action="" method="post" enctype="multipart/form-data">
	<input type="hidden" name="mode" value="<%=mode %>" />
	<input type="hidden" name="category" value="<%=multimediaMap.getCategory() %>"/>
	<input type="hidden" name="pg"/>
	<input type="hidden" name="mmId" value="<%=mmId %>" />
	<input type="hidden" name="findCategory" value="<%=findCategory %>" />
	<input type="hidden" name="findStr" value="<%=findStr %>" />
	<input type="hidden" name="findMethod" value="<%=findMethod %>" />
	<input type="hidden" name="findStartDate" value="<%=findStartDate %>" />
	<input type="hidden" name="findEndDate" value="<%=findEndDate %>" />
	<input type="hidden" name="findIsUse" value="<%=findIsUse %>" />
	<div class="well">
		<table class="table">
		<col width="160"/>
		<col width="*"/>
		<thead>
		<tr>
			<th>분류 선택</th>
			<th>
				<td colspan="3">
					<select name="category-2" id = "category-2" class="category" style="width:100px;margin-right:10px;float:left;" onchange="showCategory(2);">
						<option value="">::선택::</option>
						<% for(Category list : categories){ %>
						<option value="<%=list.getCategory()%>" data-depth="<%=list.getDepth() %>" data-children="<%=list.getChildren()%>" <% if( list.getCategory().equals(multimediaMap.getCategory()) ){ %>selected<% } %>><%=list.getName() %></option>
						<% } %>
					</select>
					<select name="category-3" class="category" style="width:100px;margin-right:10px;float:left;display:none;" onchange="showCategory(3);">
						<option value="">::선택::</option>
					</select>
					<select name="category-4" class="category" style="width:100px;margin-right:10px;float:left;display:none;" onchange="showCategory(4);">
						<option value="">::선택::</option>
					</select>
					<div style="clear:both;"></div>
				</td>
			</th>
		</tr>
		</thead>
		<tbody>
		<tr>
			<td rowspan="7">자료</td>
			<td>제목(*)</td>
			<td>
				<input type="text" maxLength=128 name="title" value="<%=multimediaMap.getTitle() %>"/>
			</td>
		</tr>
		<tr id="thumbnailSector">
			<td>목록이미지(*)</td>
			<td>
				<input type="text" id="txThumbnail" class="file_input_textbox" readonly/>
				<br/>(이미지 사이즈 : 150 X 115)
				<div class="file_input_div">
					<input type="file" name="thumbnail" targetTxt="txThumbnail" value="" />
				</div>
				<br/>
				<div id="thumbnailArea">
					<img src="<%=multimediaMap.getThumbnail()%>" width=""/><br/>
				</div>
			</td>
		</tr>
		<tr id="attachSector">
			<td>첨부 파일(*)</td>
			<td>
				<input type="text" id="txFileName" readonly class="file_input_textbox"/>
					</br>허용되는 파일 형식 : jpg, gif, png, hwp, zip, xml, xlsx, doc, docx, ppt, pptx, fla, swf	
				<div class="file_input_div">
					<input type="file" name="fileName" targetTxt="txFileName" />
				</div>
				<br/>
				<div id="attachArea">
					<%=multimediaMap.getFileName() %></br>
					<!-- <a href="javascript:deleteFile('<%=mmId %>','file');"> 파일 삭제 </a> -->
				</div>
			</td>
		</tr>
		<tr id="linkSector" style="display:none">
			<td>링크 주소(*)</td>
			<td>
				<input type="text" name="linkUrl" value="<%=multimediaMap.getDataFile() %>"/>
			</td>
		</tr>
		<tr id="dnSector" style="display:none">
			<td>영상 다운로드 주소</td>
			<td>
				<input type="text" id = "dnUrl" name="dnUrl" value="<%=multimediaMap.getDnUrl() %>"/>
			</td>
		</tr>
		<tr>
			<td>내용(*)</td>
			<td><textarea style="width:650px;height:150px;" name="contents"><%=multimediaMap.getContents() %></textarea></td>
		</tr>
		<tr>
			<td>검색키워드</td>
			<td><input type="text" name="keyword" style="width:500px;" value="<%=multimediaMap.getKeyword() %>"/></td>
		</tr>
		<tr>
			<td>출처(*)</td>
			<td>
				<select name="originCode">
					<option value="">::선택::</option>
					<% for(Category category : originCodes){ %>
					<option value="<%=category.getCategory() %>" <% if( multimediaMap.getOriginCode().equals(category.getCategory())){ %>selected<% } %>><%=category.getName() %></option>
					<% } %>
				</select>
			</td>
		</tr>
		
		<tr>
			<td>노출 유무</td>
			<td colspan="2">
				<input type="radio" name="isUse" value="Y" <%if(multimediaMap.getIsUse().equals("Y")){%>checked<%}%>/>노출
				<input type="radio" name="isUse" value="N" <%if(multimediaMap.getIsUse().equals("N")){%>checked<%}%>/>비노출
			</td>
		</tr>

		</tbody>
		</table>
	</div>
	<div class="btn-toolbar">
		<a href="javascript:goForm();" class="btn btn-primary"><i class="icon-plus"></i>등록</a>
		<%if( mode.equals("modify") ){ %><a href="javascript:goDelete();" class="btn btn-danger">삭제</a><%} %>
		<a href="javascript:goList();" class="btn">리스트</a>
	</div>
</form>
<!-- 스크립트 영역 -->

<form name="listForm" action="multimediaList.do" method="get">
	<input type="hidden" name="pg" value="<%=pg%>" />
	<input type="hidden" name="findCategory" value="<%=findCategory %>" />
	<input type="hidden" name="findStr" value="<%=findStr %>" />
	<input type="hidden" name="findMethod" value="<%=findMethod %>" />
	<input type="hidden" name="findStartDate" value="<%=findStartDate %>" />
	<input type="hidden" name="findEndDate" value="<%=findEndDate %>" />
	<input type="hidden" name="findIsUse" value="<%=findIsUse %>" />
</form>

<script>
<!--
//리스트 버튼
	function goList(){
		$("form[name=listForm]").attr("action", "multimediaList.do").submit();
	}
	
	//저장 버튼
	function goForm(){
		$("input[name=category]").val(getCategoryCode());
		//alert($("select[name=category-2] option:selected").val());
		//3차 이상 카테고리 선택하지 않은 경우에는 강제로 2차 카테고리로 설정. 
		//if( $("input[name=category]").val() == '' && $("select[name=category-2] option:selected").val() == 'E003') {
		if( $("input[name=category]").val() == '' ) {
			$("input[name=category]").val($("select[name=category-2] option:selected").val());
		}
		
		if(fn_valCheck()){
			$("form[name=qform]").attr("action","multimediaAction.do").submit();
		}
		
		// 분류선택이 동영상이 아닌 경우 영상 다운로드 주소는 공백으로 한다.
		if($('#category-2').val() != "E002"){
			("#dnUrl").val() = "";
		}
		
	}

	//파일 변경시 Input 텍스트 변경
	$("input[type=file]").change(function(){
		var thisVal = $(this).attr("value");
		var obj = "#" + $(this).attr("targetTxt");
		$(obj).attr("value",thisVal);
	});
	

	//정합성 체크
	function fn_valCheck()
	{
		var selectCategory = $("select[name=category-2]").find(":selected").attr("value");
	
		if( $("select[name=category-2] option:selected").val() == '' ) return doError('선택된 카테고리가 없습니다.' , 'category-2' , 'qform')
		if( $("input[name='title']").val() == '' ) return doError('제목을 입력해주십시오.' , 'title' , 'qform')
		
		if($("input[name=mode]").val()=="add"){
			if(selectCategory == "E003"){
				//동영상일 경우
				if( $("input[name=fileName]").val() == '' ) return doError('첨부파일을 등록해주십시오.' , 'fileName' , 'qform')
			}else if(selectCategory == "E004"){
				//이미지일 경우
				if( $("input[name=thumbnail]").val() == '' ) return doError('이미지를 등록해주십시오.' , 'thumbnail' , 'qform');
				if( $("input[name=fileName]").val() == '' ) return doError('이미지파일을 등록해주십시오.' , 'fileName' , 'qform')
			}else if(selectCategory == "E005" || selectCategory == "E002" || selectCategory == "E006" ){
				//웹링크일 경우
				if( $("input[name=thumbnail]").val() == '' ) return doError('이미지를 등록해주십시오.' , 'thumbnail' , 'qform')
				if( $("input[name=linkUrl]").val() == '' ) return doError('링크 주소를 입력해주십시오.' , 'linkUrl' , 'qform')
			}else{
				if( $("input[name=thumbnail]").val() == '' ) return doError('이미지를 등록해주십시오.' , 'thumbnail' , 'qform')
				if( $("input[name=fileName]").val() == '' ) return doError('첨부파일을 등록해주십시오.' , 'fileName' , 'qform') 
			}
		}
		if( !checkExt() ) return false();
			
		if( $("textarea[name='contents']").val() == '' ) return doError('내용을 입력하여주십시오.' , 'contents' , 'qform')
		if( $("select[name='originCode']").val() == '' ) return doError('출처를 입력하여주십시오.' , 'originCode' , 'qform')
		
		return true;
	}

	/* title : 확장자 체크. 정규식
	 * parameter = i 이미지, a 파일
	 */
	function checkExt()
	{
		if($("#txThumbnail").attr("value")=="")
			return true;
		var IMG_FORMAT = "\.(gif|jpg|jpeg|png)$"; 
	    if((new RegExp(IMG_FORMAT, "i")).test($("#txThumbnail").attr("value"))) return true;
	
	    $("#thumbnail").focus();
	    alert("목록 이미지는 gif,jpg,png만 첨부하실 수 있습니다.");
	
	    return false;
	}

	//선택된 1레벨에 따른 화면 분류
	$("select[name=category-2]").change(function(){
		var selectVal = $(this).attr("value");
		sectorInit(selectVal);
	});

	//Sector Init
	function sectorInit(selectVal){
		$("#thumbnailSector").show();
		$("#attachSector").show();
		
		if(selectVal != "E003")
			$("#linkSector").hide();
		if(selectVal == "E003" || selectVal == "E004"){		
			$("#linkSector").hide();
		}else if(selectVal == "E005" || selectVal == "E002" || selectVal == "E006"){
			$("#attachSector").hide();
			$("#linkSector").show();
			
			if( selectVal == 'E002' )
				$("#dnSector").show();	
		}
	}
	//글 삭제
	function goDelete(){
		if(confirm('해당 글을 삭제합니다.\r\n진행하시겠습니까?')){
			$("form[name=dform]").attr("action","multimediaDelete.do").submit();
		}
	}
	//로드시 화면 세팅
	$(document).ready(function(){
		
		initCategory('<%=multimediaMap.getCategory()%>');
		sectorInit("<%=masterCategory%>");
		
		$('#category-2').change(function(){
			
			if($('#category-2').val() == "E002"){
				 
				$("#dnSector").show();
			}
			else{
				$("#dnSector").hide();	
				$("#dnUrl").val("");
			}		
		});
	})
-->
</script>