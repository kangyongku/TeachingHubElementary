// 교육서비스 링크
function goList()
{
	var link = $('#education-data').val();
	location.href = link;
}
//교육서비스 링크
function goList2()
{
	var link = $('#education-data2').val();
	location.href = link;
}
//교육서비스 링크
function goList3()
{
	var link = $('#education-data3').val();
	location.href = link;
}
// -----------------------------------------------

function doError(str , fos , frm)
{
	alert(str);
	eval("document.forms." + frm + "." + fos + ".focus();");
	return;
}

function goLogin()
{
	location.href='http://cs.kumsung.co.kr/index.do?returnUrl=' + location.href;
}

function MM_openBrWindow(theURL,winName,features) { //v2.0
	  window.open(theURL,winName,features);
	}

function trim(str){
	   str = str.replace(/^\s*/,'').replace(/\s*$/, ''); 
	   return str;
	 } 
//카테고리 관리 script
//카테고리의 level은 2 , 3 , 4 까지 허용된다.
function showCategory(level , selected , prefix)
{	
	if( prefix == undefined )
		prefix = '';
	
	if( $('select[name=' + prefix + 'category-' + level + '] option:selected').val() != '' )
	{
		if( level == '4' )
		{
			// level 4에 대한 변경값은 무조건 코드를 셋팅하는 것으로 한다.
			$('input[name=' + prefix + 'category]').val( $('select[name=' + prefix + 'category-4] option:selected').val() );
		}
		else
		{
			$.ajax({
				url : '/commons/category/getCategoryChildren.do' ,
				type : 'post' ,
				data : 'category=' + $('select[name=' + prefix + 'category-' + level + '] option:selected').val() ,
				dataType : 'json' ,
				error : function(){
					alert('서버와 통신중 에러가 발생되었습니다.');
				},
				success : function(json){
					if( json.result == 'SUCCESS' )
					{
						var loopCnt = json.categories.length;
						
						for(var i = (level + 1) ; i <= 4 ; i++)
						{
							$('select[name=' + prefix + 'category-' + i + ']').empty();
							//$('select[name=' + prefix + 'category-' + i + ']').css('display' , 'none');
						}						
											
						if( loopCnt > 0 )
						{	
							// 카테고리가 존재하므로 level + 1 의 카테고리를 활성화 하고 option을 셋팅한다.
							$('select[name=' + prefix + 'category-' + (level + 1) + ']').css('display' , 'block');
							
							// add default option
							$('select[name=' + prefix + 'category-' + (level + 1) + ']').append('<option value="">::선택::</option>');
							
							for(var i = 0 ; i < loopCnt ; i++)
							{
								var category = json.categories[i].category;
								var name = json.categories[i].name;
								var depth = json.categories[i].depth;
								var children = json.categories[i].children;
								
								var isSelected = ( selected != undefined && selected == category ) ? 'selected' : '';
								
								$('select[name=' + prefix + 'category-' + (level + 1) + ']').append('<option value="' + category + '" data-depth="' + depth + '" data-children="' + children + '" ' + isSelected + '>' + name + '</option>');	
							}
						}
					}
					else
						alert(json.msg);
				}
			});
		}
	}
}

//카테고리 초기화
function initCategory(category , prefix)
{	
	if( prefix == undefined )
		prefix = '';
	
	// 각각의 카테고리에 대한 값을 가지고 온다.
	if( category.length >= 4 )
	{	
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
function setupCategory(level , category , prefix)
{	
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
			if( json.result == 'SUCCESS' )
			{
				var loopCnt = json.categories.length;
				
				// clear target
				$('select[name=' + prefix + 'category-' + level + ']').empty();
				$('select[name=' + prefix + 'category-' + level + ']').append('<option value="">::선택::</option>');
				
				if( loopCnt > 0 )
				{
					$('select[name=' + prefix + 'category-' + level + ']').css('display' , 'block');
					
					for( var i = 0 ; i < loopCnt ; i++ )
					{	
						var selected = ( category == json.categories[i].category ) ? 'selected' : '';
						$('select[name=' + prefix + 'category-' + level + ']').append('<option value="' + json.categories[i].category + '" ' + selected + '>' + json.categories[i].name + '</option>');
					}
				}
				else
				{
					//$('select[name=' + prefix + 'category-' + level + ']').css('display' , 'none');
				}
			}
			else
				alert(json.msg);
		}
	});
}

//활성화된 카테고리를 가지고 온다.
function getCategoryCode(prefix)
{
	if( prefix == undefined )
		prefix = '';
	
	// level은 총 3개 이며 높은 카테고리 순의 선택된 값이 존재한다면 break;
	var category = "";
	
	for(var i = 4 ; i >= 2 ; i-- )
	{
		if( $('select[name=' + prefix + 'category-' + i + ']').css('display') == 'block' )
		{
			// 활성화 되어있는 것만 scan 한다.
			if( $('select[name=' + prefix + 'category-' + i + '] option:selected').val() != '' )
			{
				category = $('select[name=' + prefix + 'category-' + i + '] option:selected').val();
				break;
			}
		}
	}
	
	return category;
}

//단원의 데이타를 가지고 온다.
function getUnitId()
{
	var unitId = 0;
	
	for( var i = 3 ; i >= 1 ; i-- )
	{
		if( $('select[name=unit-' + i + ']').css('display') == 'block' )
		{
			if( $('select[name=unit-' + i + '] option:selected').val() != '' )
			{
				unitId = $('select[name=unit-' + i + '] option:selected').val()
				break;
			}
		}
	}
	
	if( unitId == undefined )
		unitId = '';
	
	return unitId;
}

function getUnitIds()
{
	var tmp = [];
	
	for(var i = 1 ; i < 4 ; i++ )
	{
		var unitId = $('select[name=unit-' + i + '] option:selected').val();
		if( unitId != undefined && unitId != '' )
			tmp.push(unitId);
	}
	
	if( tmp.length > 0 )
		return tmp.join(',');
	
	return '';
}

function showSmartUnit(depth)
{
	showUnit(depth , '/commons/unit/getSmartUnitChildren.do');
}

function showLearningUnit(depth)
{
	showUnit(depth , '/commons/unit/getLearningUnitChildren.do');
}

//단원관리
// 단원의 level은 1 , 2 , 3 까지 허용된다.
function showUnit(depth , url)
{	
	var bookId = $('select[name=bookId] option:selected').val();
	var unitId = $('select[name=unit-' + (depth - 1) + '] option:selected').val();
	
	// input hidden 값이 존재하는지 체크
	if( bookId == undefined )
		bookId = $('input[name=bookId]').val();
	
	if( bookId == undefined || bookId == '' )
	{
		for( var i = 1 ; i < 4 ; i++ )
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
			if( json.result == 'SUCCESS' )
			{
				// 자신의 자식 노드를 비활성화 시킨다.
				/*
				for( var i = 3 ; i > (depth - 1) ; i-- )
					$('select[name=unit-' + i + ']').css('display' , 'none');
				*/
				
				var loopCnt = json.units.length;
				
				// clear target
				$('select[name=unit-' + depth + ']').empty();
				$('select[name=unit-' + depth + ']').append('<option value="">::선택::</options>');
				
				for( var i = 0 ; i < loopCnt ; i++ )
					$('select[name=unit-' + depth + ']').append('<option value="' + json.units[i].unitId + '">' + json.units[i].name + '</option>');
				
				if( loopCnt > 0 )
					$('select[name=unit-' + depth + ']').css('display' , 'block');
			}
			else
				alert(json.msg);
		}
	});
}

// 도서정보를 가지고 온다.
function getSmartBooks()
{
	getBooks('/web/smart/getSmartBooks.do');
}

function getBooks(url)
{
	var category = getCategoryCode();
	
	if( category != '' )
	{
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
				if( json.result == 'SUCCESS' )
				{
					var loopCnt = json.books.length;
					
					$('select[name=bookId]').empty();
					$('select[name=bookId]').append('<option value="">도서 선택</option>');
					
					if( loopCnt > 0 )
					{	
						for(var i = 0 ; i < loopCnt ; i++)
							$('select[name=bookId]').append('<option value="' + json.books[i].bookId + '">' + json.books[i].name + '</option>');
					}
				}
				else
					alert(json.msg);
			}
		});
	}
}

function renderSmartUnits(bookId , unitIds )
{
	renderUnits( bookId , unitIds , '/commons/unit/getSmartUnitDepthList.do');
}

function renderLearningUnits( bookId , unitIds )
{
	renderUnits( bookId , unitIds , '/commons/unit/getLearningUnitDepthList.do');
}

// 단원들을 보여준다.
function renderUnits(bookId , unitIds , url)
{
	if( unitIds != '' )
	{
		var units = unitIds.split(',');
		var loopCnt = units.length;
		
		for(var i = 0 ; i < loopCnt ; i++ )
		{
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
function renderUnit(depth , unitId , json)
{	
	$('select[name=unit-' + depth + ']').empty();
	$('select[name=unit-' + depth + ']').append('<option value="">::선택::</option>');
	
	var loopCnt = json.list.length;
	
	for( var i = 0 ; i < loopCnt ; i++ )
	{	
		var selected = (json.list[i].unitId == unitId) ? 'selected' : ''; 
		
		$('select[name=unit-' + depth + ']').append('<option value="' + json.list[i].unitId + '" ' + selected + '>' + json.list[i].name + '</option>');		
	}
	
	$('select[name=unit-' + depth + ']').css('display' , 'block');			
}

//이미지 슬라이드
$(document).ready(function(){
	
	Slider02('div.slide');
	});

	function Slider02(selector){
		  if(!($(selector).length)){return false;} 
		  var numSelector = $(selector).length;
		  var slideNow = new Array(numSelector);
		  var numSlide = new Array(numSelector);
		  var slideWidth = new Array(numSelector);
		  for(i=0;i<numSelector;i++){
		    slideNow[i] = 1;
			numSlide[i] = $(selector+':eq('+i+')').find('ul.slideConts:eq(0) li').length;
			slideWidth[i] = $(selector+':eq('+i+')').find('ul.slideConts').width();
			for(j=0;j<numSlide[i];j++){
		      $(selector+':eq('+i+')').find('ul.slideConts li:eq('+j+')').css({'left':(j*slideWidth[i])+'px'});
		    }	
		  }
		  
		  $(selector).find('ul.slideBar li:eq(0) a').bind('click',function(){
		    var numSel = $(selector).index($(this).parent().parent().parent());
			var prev = (slideNow[numSel] - 1) < 1 ? numSlide[numSel] : slideNow[numSel] - 1;
		    ShowSlide(numSel,prev);
		  });
		  $(selector).find('ul.slideBar li:last a').bind('click',function(){
			var numSel = $(selector).index($(this).parent().parent().parent());
			var next = (slideNow[numSel] + 1) > numSlide[numSel] ? 1 : slideNow[numSel] + 1;	
			ShowSlide(numSel,next);
		  });
		  $(selector).find('ul.slideBar li.dot a').bind('click',function(){
			var numSel = $(selector).index($(this).parent().parent().parent());
			var selected = $(selector+':eq('+numSel+')').find('ul.slideBar li.dot').index($(this).parent()) + 1;
			ShowSlide(numSel,selected);
		  });
		  
		  function ShowSlide(numSelector,n){
		    if(slideNow[numSelector] == n){return false;}
			for(i=0;i<numSlide[numSelector];i++){
		      $(selector+':eq('+numSelector+')').find('ul.slideConts li:eq('+i+')').stop().animate({'left':((i-n+1)*slideWidth[numSelector])+'px'});
		    }
			$(selector+':eq('+numSelector+')').find('ul.slideBar li.dot').removeClass('now');
			$(selector+':eq('+numSelector+')').find('ul.slideBar li.dot:eq('+(n-1)+')').addClass('now');
			slideNow[numSelector] = n;
		  }
	}