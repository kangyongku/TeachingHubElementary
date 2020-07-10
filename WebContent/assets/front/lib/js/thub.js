/**
 * Thub 공통 스크립트 컨트롤러
 */
/*$(document).ready(function(){
	$('a.copy').zclip({
		path : '/assets/front/lib/flash/ZeroClipboard.swf' ,
		copy : location.href , 
		afterCopy : function(){ 
			alert('URL을 클립보드에 복사하였습니다.');
		}
	});
	
	$('.printer').click(function(){ //printer
		$("#content").printElement();
	});

	$('.col03,.col05').each(function(){ //tab lines
		var maxSize = ($(this).hasClass('col03')) ? 3 : 5;
		if( $(this).find('li').length < maxSize )
			$(this).find('li').addClass('none');
	});
});*/

function copy(){	
	var url =location.href;
	var IE=(document.all)?true:false;
	if(IE){
		window.clipboardData.setData("Text", url);
		alert('클립보드에 URL이 복사되었습니다.\n\nCtrl+V (붙여넣기) 단축키를 이용하시면,\nURL을 붙여 넣으실 수 있습니다.');
	}else{
		temp = prompt("Ctrl+C를 눌러 클립보드로 복사하세요", url);
	}
}

function doError(str, fos, frm){
	alert(str);
	eval("document.forms." + frm + "." + fos + ".focus();");
	return;
}

/**
 * 통합 로그인
 */
function goLogin(){
	var cur_url = location.href;
	cur_url = cur_url.replace("http:", "https:");
	//location.href='https://cs.kumsung.co.kr/index.do?returnUrl=' + location.href;
	location.href='http://cs.kumsung.co.kr/index.do?returnUrl=' + cur_url;
	//location.href='http://cs.kumsung.co.kr/index.do?returnUrl=https://thub.kumsung.co.kr';

}

function goLogout(){
	location.href='http://cs.kumsung.co.kr/logout.do?returnUrl=http://thub.kumsung.co.kr';
	//	location.href='https://cs.kumsung.co.kr:444/logout.do?returnUrl=http://thub.kumsung.co.kr';
}

/**
 * 통합게시판의 첨부파일 다운로드
 * @param fileId
 */
function getBoardAttachmentFile(fileId){
	$.ajax({
		url : '/api/isLogin.do' ,
		dataType : 'json' ,
		error : function(){
			alert('잠시 후 이용하여주십시오.');
		},
		success : function(json){
			if( json.login == true )
				location.href = '/commons/file/getBoardFile.do?fileId=' + fileId;
			else
			{
				if( confirm('로그인 후 이용가능합니다.\r\n로그인 하시겠습니까?') )
					goLogin();
			}
		}
	});
}

function showGnbLearning(target){ //GNB
	$('.edu_courses').css('display', 'none');
	$('.megamenu').find('[data-class=' + target + ']').css('display', 'block');

}

function showGnbLearning1(target){
	$('.list_detail').css('display', 'none');
	$('#widedrmenu').find('[data-class=' + target + ']').css('display', 'block');
}

/*
 * Admin Common.js에 있는 function 추가
 * date : 2013-09-11
*/
//카테고리 관리 script
//카테고리의 level은 2 , 3 , 4 까지 허용된다.
function showCategory(level, selected, prefix){	
	if(prefix == undefined)
		prefix = '';
	
	if($('select[name=' + prefix + 'category-' + level + '] option:selected').val() != ''){
		if(level == '4'){
			//level 4에 대한 변경값은 무조건 코드를 셋팅하는 것으로 한다.
			$('input[name=' + prefix + 'category]').val( $('select[name=' + prefix + 'category-4] option:selected').val());
			$('select[name=bookId').css('display' , 'block');	
		}else{
			$.ajax({
				url : '/commons/category/getCategoryChildren.do',
				type : 'post',
				data : 'category=' + $('select[name=' + prefix + 'category-' + level + '] option:selected').val(),
				dataType : 'json',
				error : function(){
					alert('서버와 통신중 에러가 발생되었습니다.');
				},
				success : function(json){
					if(json.result == 'SUCCESS'){
						var loopCnt = json.categories.length;
						for(var i = (level + 1) ; i <= 4 ; i++){
							$('select[name=' + prefix + 'category-' + i + ']').empty();
							$('select[name=' + prefix + 'category-' + i + ']').css('display' , 'none');
						}						
											
						if(loopCnt > 0 ){	
							// 카테고리가 존재하므로 level + 1 의 카테고리를 활성화 하고 option을 셋팅한다.
							$('select[name=' + prefix + 'category-' + (level + 1) + ']').css('display' , 'block');
							
							// add default option
							$('select[name=' + prefix + 'category-' + (level + 1) + ']').append('<option value="">::선택::</option>');
							
							for(var i = 0 ; i < loopCnt ; i++){
								var category = json.categories[i].category;
								var name = json.categories[i].name;
								var depth = json.categories[i].depth;
								var children = json.categories[i].children;
								var isSelected = ( selected != undefined && selected == category ) ? 'selected' : '';
								$('select[name=' + prefix + 'category-' + (level + 1) + ']').append('<option value="' + category + '" data-depth="' + depth + '" data-children="' + children + '" ' + isSelected + '>' + name + '</option>');	
							}
						}
					}
				}
			});
		}
	}
}

function showLearningUnit(depth){
	showUnit(depth , '/commons/unit/getLearningUnitChildren.do');
}

function showUnit(depth , url){ //단원의 level은 1 , 2 , 3 까지 허용	
	var bookId = $('select[name=bookId] option:selected').val();
	var unitId = $('select[name=unit-' + (depth - 1) + '] option:selected').val();
	
	if(bookId == undefined || bookId == ''){
		for(var i = 1 ; i < 4 ; i++)
			$('select[name=unit-' + i + ']').empty();
			return;
	}
	
	if( unitId == undefined || unitId == '' )
		unitId = 0;
	
	$.ajax({
		url : url ,
		type : 'post' ,
		async : false ,
		data : 'depth=' + depth + '&bookId=' + bookId + '&unitId=' + unitId ,
		dataType : 'json' ,
		error : function(){
			alert('서버와 통신중 에러가 발생되었습니다.');
		},
		success : function(json){
			if( json.result == 'SUCCESS'){
				// 자신의 자식 노드를 비활성화 시킨다.
				for( var i = 3 ; i > (depth - 1) ; i-- )
					$('select[name=unit-' + i + ']').css('display' , 'none');
				
				var loopCnt = json.units.length;
				
				// clear target
				$('select[name=unit-' + depth + ']').empty();
				$('select[name=unit-' + depth + ']').append('<option value="">::선택::</options>');
				
				for( var i = 0 ; i < loopCnt ; i++ )
					$('select[name=unit-' + depth + ']').append('<option value="' + json.units[i].unitId + '">' + json.units[i].name + '</option>');
				
				if( loopCnt > 0 )
					$('select[name=unit-' + depth + ']').css('display' , 'block');
			}else{
				alert(json.msg);
			}
		}
	});
}

function getLearningBooks(){
	getBooks('/admin/learning/getLearningBooks.do');
}

function getBooks(url){
	var category = getCategoryCode();
	if( category != '' ){
		$.ajax({
			url : url,
			type : 'post',
			async : false ,
			data : 'category=' + category ,
			dataType : 'json' ,
			error : function(){
				alert('서버와 통신중 에러가 발생하였습니다.');
			},
			success : function(json){
				if( json.result == 'SUCCESS' ){
					var loopCnt = json.books.length;
					$('select[name=bookId]').empty();
					$('select[name=bookId]').append('<option value="">::도서선택::</option>');
					
					if( loopCnt > 0 ){	
						for(var i = 0 ; i < loopCnt ; i++)
							$('select[name=bookId]').append('<option value="' + json.books[i].bookId + '">' + json.books[i].name +'('+json.books[i].author+')'+json.books[i].courseName+ '</option>');
					}
				}else{
					alert(json.msg);
				}
			}
		});
	}
}

//활성화된 카테고리를 가지고 온다.
function getCategoryCode(prefix){
	if( prefix == undefined )
		prefix = '';
	
	// level은 총 3개 이며 높은 카테고리 순의 선택된 값이 존재한다면 break;
	var category = "";
	
	for(var i = 4 ; i >= 2 ; i-- ){
		if( $('select[name=' + prefix + 'category-' + i + ']').css('display') == 'block' ){
			// 활성화 되어있는 것만 scan 한다.
			if( $('select[name=' + prefix + 'category-' + i + '] option:selected').val() != '' ){
				category = $('select[name=' + prefix + 'category-' + i + '] option:selected').val();
				break;
			}
		}
	}
	
	return category;
}

//카테고리 초기화
function initCategory(category , prefix){	
	if( prefix == undefined )
		prefix = '';
	
	// 각각의 카테고리에 대한 값을 가지고 온다.
	if( category.length >= 4 ){	
		var categories = [];
		categories.push(category.substring(0,4));		// 2차 카테고리
		if( category.length >= 7 )
			categories.push(category.substring(0,7));		// 3차 카테고리
		
		if( category.length >= 10)
			categories.push(category.substring(0,10));		// 4차 카테고리
		
		for( var i = 0 ; i < categories.length ; i++)
			setupCategory( (i + 2) , categories[i] , prefix);
	}
}

//카테고리 셋팅
function setupCategory(level , category , prefix){	
	if( prefix == undefined )
		prefix = '';
	
	var parent = category.substring( 0 , (3 * level) - 5);
	
	$.ajax({
		url : '/commons/category/getCategoryChildren.do' ,
		type : 'post' ,
		data : 'category=' + parent ,
		dataType : 'json' ,
		error : function(){
			alert('데이타를 가지고 오는 도중 에러가 발생.');
		},
		success : function(json){
			if( json.result == 'SUCCESS' ){
				var loopCnt = json.categories.length;
				
				// clear target
				$('select[name=' + prefix + 'category-' + level + ']').empty();
				$('select[name=' + prefix + 'category-' + level + ']').append('<option value="">::선택::</option>');
				
				if( loopCnt > 0 ){
					$('select[name=' + prefix + 'category-' + level + ']').css('display' , 'block');
					
					for( var i = 0 ; i < loopCnt ; i++ ){	
						var selected = ( category == json.categories[i].category ) ? 'selected' : '';
						$('select[name=' + prefix + 'category-' + level + ']').append('<option value="' + json.categories[i].category + '" ' + selected + '>' + json.categories[i].name + '</option>');
					}
				}else{
					$('select[name=' + prefix + 'category-' + level + ']').css('display' , 'none');
				}
			}
		}
	});
}

//단원의 데이타를 가지고 온다.
function getUnitId(){
	var unitId = 0;
	for( var i = 3 ; i >= 1 ; i-- ){
		if( $('select[name=unit-' + i + ']').css('display') == 'block' ){
			if( $('select[name=unit-' + i + '] option:selected').val() != '' ){
				unitId = $('select[name=unit-' + i + '] option:selected').val();
				break;
			}
		}
	}
	
	if( unitId == undefined )
		unitId = '';
	
	return unitId;
}

function getUnitIds(){
	var tmp = [];
	
	for(var i = 1 ; i < 4 ; i++ ){
		var unitId = $('select[name=unit-' + i + '] option:selected').val();
		if( unitId != undefined && unitId != '' )
			tmp.push(unitId);
	}
	
	if( tmp.length > 0 )
		return tmp.join(',');
	
	return '';
}

function showSmartUnit(depth){
	showUnit(depth , '/commons/unit/getSmartUnitChildren.do');
}


/* 에디터 컨텐츠를 저장한다. */
function saveContent() {
	Editor.save(); 
}

/**
 * Editor.save()를 호출한 경우 validForm callback 이 수행된 이후
 * 실제 form submit을 위해 form 필드를 생성, 변경하기 위해 부르는 콜백함수로
 * 각자 상황에 맞게 적절히 응용하여 사용한다.
 * @function
 * @param {Object} editor - 에디터에서 넘겨주는 editor 객체
 * @returns {Boolean} 정상적인 경우에 true
 */
function setForm(editor) {
	var formGenerator = editor.getForm();
	var content = editor.getContent();
	formGenerator.createField(
			tx.textarea({
				'name': "contents", // 본문 내용을 필드를 생성하여 값을 할당하는 부분
				'style': { 'display': "none" }
			}, content)
	);
	
	/* 아래의 코드는 첨부된 데이터를 필드를 생성하여 값을 할당하는 부분으로 상황에 맞게 수정하여 사용한다.
	 첨부된 데이터 중에 주어진 종류(image,file..)에 해당하는 것만 배열로 넘겨준다. */
	var images = editor.getAttachments('image');
	for (var i = 0, len = images.length; i < len; i++) {
		// existStage는 현재 본문에 존재하는지 여부
		if (images[i].existStage) {
			// data는 팝업에서 execAttach 등을 통해 넘긴 데이터
			//alert('attachment information - image[' + i + '] \r\n' + JSON.stringify(images[i].data));
			formGenerator.createField(
					tx.input({
						'type': "hidden",
						'name': 'attach_image',
						'value': images[i].data.imageurl // 예에서는 이미지경로만 받아서 사용
					})
			);
		}
	}
	//return true;
}

/**
 * 페이지 공유를 위한 API
 * @param type
 */
function share(type){
	$.ajax({
		url : '/global/getShortUrl.do' ,
		data : 'longUrl=' + escape(location.href),
		dataType : 'json' ,
		error : function(){
			alert('서버와 통신할 수 없습니다.\r\n잠시 후 이용하여주십시오.');
		},
		success : function(json){
			if( json.result == 'SUCCESS' ){
				if( type == 'twitter' ){
					var text = '[티칭허브] ' + $('htitle').text() + '... ' + json.shortUrl; 
					window.open('https://twitter.com/intent/tweet?text=' + encodeURIComponent(text) , 'twitter' , 'width=550,height=420,scrollbars=yes');
				}else if( type == 'facebook' ){
					window.open('https://www.facebook.com/sharer/sharer.php?u=' + json.shortUrl);
				}else if (type == 'me2day' ){
					window.open("http://me2day.net/posts/new?new_post[body]=\"" +encodeURIComponent('[티칭허브] ' + $('htitle').text()) + "\"" + ":" + json.shortUrl);
				}else if( type == '' ){
					
				}
			}else{
				alert(json.msg);
			}
		}
	});
}

function renderLearningUnits( bookId , unitIds ){
	renderUnits( bookId , unitIds , '/commons/unit/getLearningUnitDepthList.do');
}

//단원들을 보여준다.
function renderUnits(bookId , unitIds , url){
	if( unitIds != '' ){
		var units = unitIds.split(',');
		var loopCnt = units.length;
		for(var i = 0 ; i < loopCnt ; i++ ){
			var unitId = units[i];
			var depth = (i + 1);
			var parentId = ( i > 0 ) ? units[i-1] : 0;
			
			// get set
			$.ajax({
				url : url ,
				async : false ,
				type : 'post' ,
				data : 'bookId=' + bookId + '&depth=' + depth + '&parentId=' + parentId,
				dataType : 'json' ,				
				success : function(json){
					if( json.result == 'SUCCESS' )
					{	
						renderUnit(depth , unitId , json);
					}
					else
						alert(json.msg);
				}
			});
		}
	}
}

//단원을 보여준다.
function renderUnit(depth , unitId , json){	
	$('select[name=unit-' + depth + ']').empty();
	$('select[name=unit-' + depth + ']').append('<option value="">::선택::</option>');
	
	var loopCnt = json.list.length;
	for( var i = 0 ; i < loopCnt ; i++ ){	
		var selected = (json.list[i].unitId == unitId) ? 'selected' : ''; 
		$('select[name=unit-' + depth + ']').append('<option value="' + json.list[i].unitId + '" ' + selected + '>' + json.list[i].name + '</option>');		
	}
	$('select[name=unit-' + depth + ']').css('display' , 'block');
}

function loginCheck(){
	var f = document.forms.loginForm;
	if( f.userId.value == '') return doError('아이디를 입력해주십시오.' , 'userId' , 'loginForm');
	if( f.passWd.value == '') return doError('비밀번호를 입력해주십시오.' , 'passWd' , 'loginForm');
	
	// ajax를 통하여 로그인 처리
	f.target = '_self';
	//f.action = '/loginProc.do';
	f.action = 'https://cs.kumsung.co.kr/loginForm.do';
	f.submit();
}

function scrap(scrapType , relationIds){	
	$.ajax({
		url : '/api/isLogin.do' ,
		dataType : 'json' ,
		success : function(json){
			if( json.login == true ){
				window.open('/popup/scrapFolder.do?scrapType=' + scrapType + '&relationIds=' + relationIds , 'scrap' , 'width=450,height=500,scrollbars=yes');
			}else{
				if( confirm('로그인 후 이용가능합니다.\r\n로그인 하시겠습니까?') ){
					goLogin();
				}
			}
		}
	});
}

function singleFileDownload(dataId){	
	// 데이타를 분기해줘야 한다.
	$.ajax({
		url : '/api/learning/checkDataType.do' ,
		type : 'post' ,
		data : 'dataId=' + dataId ,
		dataType : 'json',
		error : function(){
			alert('서버와 접속도중 에러가 발생되었습니다.');
		},
		success : function(json){
			if( json.result == 'SUCCESS' )
			{	
				if( json.url == 'DIRECT')
					location.href='/commons/file/getLearningDataFile.do?dataId=' + dataId;
				else
					location.href=json.url;
			}
			else
				alert(json.msg);
		}
	});
}

function goFamily(){
	var link = $('#family-data').val();
	window.open(link);
}

/**
 * 나의 교과로 등록한다.
 */
function addMyBook(bookId){
	$.ajax({
		url : '/api/member/addMyBook.do' , 
		type : 'post' ,
		data : 'bookId=' + bookId,
		dataType : 'json',
		error : function(){
			alert('서버와의 통신도중 에러가 발생되었습니다.');
		},
		success : function(json){
			if( json.result == 'SUCCESS' ){
				alert('내 교과서설정을 완료하였습니다.');
			}else{
				alert(json.msg);
			}
		}
	});
}

/**
 * 나의 교과로 등록한다.
 */
function addMyBooks(bookIds){
	$.ajax({
		url : '/api/member/addMyBooks.do' , 
		type : 'post' ,
		data : 'bookIds=' + bookIds,
		dataType : 'json',
		error : function(){
			alert('서버와의 통신도중 에러가 발생되었습니다.');
		},
		success : function(json){
			if( json.result == 'SUCCESS' )
			{
				alert('내 교과서설정을 완료하였습니다.');
				location.reload();
			}
			else
				alert(json.msg);
		}
	});
}

function handleCdDownload(){	
	showCdDownloadPopup();
	$(".download-popup .btn_close").live("click",function(e){
		e.preventDefault();
		$(this).closest(".download-popup").fadeOut(300);
	});
}

function getRightURL(n) {
	var tmpURL = n.replace(/\s/g, "");
	var tmp = tmpURL.toLowerCase();
	if( tmp.indexOf("http://") == 0 ||
			tmp.indexOf("https://") == 0 ||
			tmp.indexOf("ftp://") == 0 ||
			tmp.indexOf("mailto://") == 0 ||
			tmp.indexOf("mms://") == 0 ||
			tmp.indexOf("pnm://") == 0 ||
			tmp.indexOf("telnet://") == 0 ||
			tmp.indexOf("rlogin://") == 0 ||
			tmp.indexOf("news://") == 0 ||
			tmp.indexOf("file://") == 0)
		return tmpURL;
	else
		return "http://"+tmpURL;
}

/**
 * 로그인 체크후 url로 redirect 한다.
 * @param url
 */
function checkLoginAndGo(url){
	$.ajax({
		url : '/api/isLogin.do' ,
		dataType : 'json' ,
		error : function(){
			alert('서버와의 통신도중 에러가 발생되었습니다.');
		},
		success : function(json){
			if( json.login == true ){
				location.href = url;
			}else{
				if( confirm('로그인 후 이용가능합니다.\r\n로그인 하시겠습니까?') )
					goLogin();
			}
		}
	});
}

function goArticle(boardId , articleId){	
	switch(boardId){
	case 2 : 
		location.href='/open/share/utilize.do?articleId=' + articleId + '&mode=detail';
		break;
	case 3 : 
		location.href='/open/share/classes.do?articleId=' + articleId + '&mode=detail';
		break;
	case 4 : 
		location.href='/open/activity/example.do?articleId=' + articleId + '&mode=detail';
		break;
	case 5 : 
		location.href='/open/activity/form.do?articleId=' + articleId + '&mode=detail';
		break;
	case 6 : 
		location.href='/open/activity/site.do?articleId=' + articleId + '&mode=detail';
		break;
	case 8 : 
		location.href='/open/activity/formula.do?articleId=' + articleId + '&mode=detail';
		break;
	case 9 : 
		location.href='/open/outer/travel.do?articleId=' + articleId + '&mode=detail';
		break;
	case 10 : 
		location.href='/open/outer/movie.do?articleId=' + articleId + '&mode=detail';
		break;
	case 18 : 
		location.href='/happy/lecture.do?articleId=' + articleId + '&mode=detail';
		break;
	case 20 : 
		location.href='/happy/healing/camping.do?articleId=' + articleId + '&mode=detail';
		break;
	case 21 : 
		location.href='/happy/healing/travel.do?articleId=' + articleId + '&mode=detail';
		break;
	case 11 : 
		location.href='/happy/rest/emotion.do?articleId=' + articleId + '&mode=detail';
		break;
	case 12 : 
		location.href='/happy/rest/webtoons.do?articleId=' + articleId + '&mode=detail';
		break;
	case 22 : 
		location.href='/happy/rest/wisdom.do?articleId=' + articleId + '&mode=detail';
		break;
	case 14 : 
		location.href='/happy/added/family.do?articleId=' + articleId + '&mode=detail';
		break;
	case 23 : 
		location.href='/happy/added/telling.do?articleId=' + articleId + '&mode=detail';
		break;
	case 24 : 
		location.href='/happy/added/made.do?articleId=' + articleId + '&mode=detail';
		break;
	case 25 : 
		location.href='/open/outer/science.do?articleId=' + articleId + '&mode=detail';
		break;		
	case 27 : 
		location.href='/open/teacher/elementary.do?articleId=' + articleId + '&mode=detail';
		break;
	case 28 : 
		location.href='/open/teacher/middle.do?articleId=' + articleId + '&mode=detail';
		break;
	case 29 : 
		location.href='/open/teacher/high.do?articleId=' + articleId + '&mode=detail';
		break;			
	case 36 : 
		location.href='/activity/freesem.do?articleId=' + articleId + '&mode=detail';
		break;		
	}	
}

/* 자료 등록 페이지 수정을 위한 함수 추가 */
function showCategory2(level , selected , prefix, kind, bookId, bookcategory){	
	if( prefix == undefined )
		prefix = '';
	if( $('select[name=' + prefix + 'category-' + level + '] option:selected').val() != '' ){
		if( level == '4' ){
			// level 4에 대한 변경값은 무조건 코드를 셋팅하는 것으로 한다.
			$('input[name=' + prefix + 'category]').val( $('select[name=' + prefix + 'category-4] option:selected').val() );
			//$('select[name=bookId').empty();
			$('select[name=bookId').css('display' , 'block');	
		}else{
			$.ajax({
				url : '/commons/category/getCategoryChildren.do' ,
				type : 'post' ,
				data : 'category=' + $('select[name=' + prefix + 'category-' + level + '] option:selected').val() ,
				dataType : 'json' ,
				error : function(){
					alert('서버와 통신중 에러가 발생되었습니다.');
				},
				success : function(json){
					if( json.result == 'SUCCESS' ){
						var loopCnt = json.categories.length;
						for(var i = (level + 1) ; i <= 4 ; i++){
							$('select[name=' + prefix + 'category-' + i + ']').empty();
							$('select[name=' + prefix + 'category-' + i + ']').css('display' , 'none');
						}						
											
						if( loopCnt > 0 ){	
							// 카테고리가 존재하므로 level + 1 의 카테고리를 활성화 하고 option을 셋팅한다.
							$('select[name=' + prefix + 'category-' + (level + 1) + ']').css('display' , 'block');
							
							// add default option
							$('select[name=' + prefix + 'category-' + (level + 1) + ']').append('<option value="">::선택::</option>');
							
							for(var i = 0 ; i < loopCnt ; i++){
								var category = json.categories[i].category;
								var name = json.categories[i].name;
								var depth = json.categories[i].depth;
								var children = json.categories[i].children;
								
								//var isSelected = ( selected != undefined && selected == category ) ? 'selected' : '';
								
								var isSelected;
								if(selected.trim() == name.trim()){
									isSelected = 'selected';
								}else{
									isSelected = '';
								}
								
								$('select[name=' + prefix + 'category-' + (level + 1) + ']').append('<option value="' + category + '" data-depth="' + depth + '" data-children="' + children + '" ' + isSelected + '>' + name + '</option>');	
							}
							fn_goSel(kind, selected, bookId, bookcategory);
						}
					}
				}
			});
		}
	}
}

function fn_goSel(kind1, kind2, bookId, bookcategory){
	if(kind1 == "고등"){
		if(kind2 == "영어" || kind2 == "수학" || kind2 == "과학" || kind2 == "사회" || kind2 == "역사"){
			showCategory3(3, '','',bookcategory);
		}
		
	}
	getLearningBooks2(bookId);
}

function showCategory3(level, selected, prefix, bookcategory){	
	if(prefix == undefined)
		prefix = '';
	
	if($('select[name=' + prefix + 'category-' + level + '] option:selected').val() != ''){
		if(level == '4'){
			//level 4에 대한 변경값은 무조건 코드를 셋팅하는 것으로 한다.
			$('input[name=' + prefix + 'category]').val( $('select[name=' + prefix + 'category-4] option:selected').val());
			$('select[name=bookId').css('display' , 'block');	
		}else{
			$.ajax({
				url : '/commons/category/getCategoryChildren.do',
				type : 'post',
				data : 'category=' + $('select[name=' + prefix + 'category-' + level + '] option:selected').val(),
				dataType : 'json',
				error : function(){
					alert('서버와 통신중 에러가 발생되었습니다.');
				},
				success : function(json){
					if(json.result == 'SUCCESS'){
						var loopCnt = json.categories.length;
						for(var i = (level + 1) ; i <= 4 ; i++){
							$('select[name=' + prefix + 'category-' + i + ']').empty();
							$('select[name=' + prefix + 'category-' + i + ']').css('display' , 'none');
						}						
											
						if(loopCnt > 0 ){	
							// 카테고리가 존재하므로 level + 1 의 카테고리를 활성화 하고 option을 셋팅한다.
							$('select[name=' + prefix + 'category-' + (level + 1) + ']').css('display' , 'block');
							
							// add default option
							$('select[name=' + prefix + 'category-' + (level + 1) + ']').append('<option value="">::선택::</option>');
							
							for(var i = 0 ; i < loopCnt ; i++){
								var category = json.categories[i].category;
								var name = json.categories[i].name;
								var depth = json.categories[i].depth;
								var children = json.categories[i].children;
								//var isSelected = ( selected != undefined && selected == category ) ? 'selected' : '';
								var isSelected;
								if(bookcategory.trim() == category.trim()){
									isSelected = 'selected';
								}else{
									isSelected = '';
								}
								$('select[name=' + prefix + 'category-' + (level + 1) + ']').append('<option value="' + category + '" data-depth="' + depth + '" data-children="' + children + '" ' + isSelected + '>' + name + '</option>');	
							}
						}
					}
				}
			});
		}
	}
}

function getLearningBooks2(bookId){
	getBooks2('/admin/learning/getLearningBooks.do', bookId);
}

function getBooks2(url, bookId){
	var category = getCategoryCode();
	var isSelected2;
	
	if( category != '' ){
		$.ajax({
			url : url,
			type : 'post',
			async : false ,
			data : 'category=' + category ,
			dataType : 'json' ,
			error : function(){
				alert('서버와 통신중 에러가 발생하였습니다.');
			},
			success : function(json){
				if( json.result == 'SUCCESS' ){
					var loopCnt = json.books.length;
					$('select[name=bookId]').empty();
					$('select[name=bookId]').append('<option value="">::도서선택::</option>');
					
					if( loopCnt > 0 ){	
						for(var i = 0 ; i < loopCnt ; i++){
							if(bookId == json.books[i].bookId){
								isSelected2 = 'selected';
							}else{
								isSelected2 = '';
							}
							
							$('select[name=bookId]').append('<option value="' + json.books[i].bookId + '" ' + isSelected2 + '>' + json.books[i].name +'('+json.books[i].author+')'+json.books[i].courseName+ '</option>');
						}
					}
				}else{
					alert(json.msg);
				}
			}
		});
	}
}
/* 자료 등록 페이지 수정을 위한 함수 추가 */

