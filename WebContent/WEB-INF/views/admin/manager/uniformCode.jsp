<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div style="line-height:25px;font-weight:bold;">
통합코드는 UI 상에서 삭제할 수 없습니다.<br/>시스템이 불안정 해질 수 있으므로 시스템 관리자에게 문의하십시오.
</div>

<div style="margin:10px;font-weight:bold;color:brown;">
	선택된 카테고리 코드 : <span id="current-category"></span>
</div>

<div class="well">
	<div style="float:left;width:235px;">
		<span>1차 분류</span>
		<select id="category-1" style="width:200px;height:350px;" size="20" onchange="setCategoryList(2);">
		</select>
		<div class="btn-toolbar">
			<button class="btn btn-danger" onclick="showDialog('1' , 'add');"><i class="icon-plus"></i> 추가</button>
			<button class="btn btn-primary" onclick="showDialog('1' , 'modify');"><i class="icon-plus"></i> 수정</button>			
			<div class="btn-group">
			</div>
		</div>
	</div>
	<div style="float:left;width:235px;">
		<span>2차 분류
			<a href="javascript:changeCategoryOrd('category-2' , 'down');" style="border:1px solid #ddd;background-color:#eee;padding:3px;color:#000;">▽</a>
			<a href="javascript:changeCategoryOrd('category-2' , 'up');" style="border:1px solid #ddd;background-color:#eee;padding:3px;color:#000;">△</a>
		</span>
		<select id="category-2" style="width:200px;height:350px;" size="20" onchange="setCategoryList(3);">
		</select>
		<div class="btn-toolbar">
			<button class="btn btn-danger" onclick="showDialog('2' , 'add');"><i class="icon-plus"></i> 추가</button>
			<button class="btn btn-primary" onclick="showDialog('2' , 'modify');"><i class="icon-plus"></i> 수정</button>
			<div class="btn-group">
			</div>
		</div>
	</div>
	<div style="float:left;width:235px;">
		<span>
			3차 분류
			<a href="javascript:changeCategoryOrd('category-3' , 'down');" style="border:1px solid #ddd;background-color:#eee;padding:3px;color:#000;">▽</a>
			<a href="javascript:changeCategoryOrd('category-3' , 'up');" style="border:1px solid #ddd;background-color:#eee;padding:3px;color:#000;">△</a>
		</span>
		<select id="category-3" style="width:200px;height:350px;" size="20" onchange="setCategoryList(4);">
		</select>
		<div class="btn-toolbar">
			<button class="btn btn-danger" onclick="showDialog('3' , 'add');"><i class="icon-plus"></i> 추가</button>
			<button class="btn btn-primary" onclick="showDialog('3' , 'modify');"><i class="icon-plus"></i> 수정</button>
			<div class="btn-group">
			</div>
		</div>
	</div> 
	<div style="float:left;width:235px;">
		<span>4차 분류
			<a href="javascript:changeCategoryOrd('category-4' , 'down');" style="border:1px solid #ddd;background-color:#eee;padding:3px;color:#000;">▽</a>
			<a href="javascript:changeCategoryOrd('category-4' , 'up');" style="border:1px solid #ddd;background-color:#eee;padding:3px;color:#000;">△</a>
		</span>
		<select id="category-4" style="width:200px;height:350px;" size="20" onchange="setCategoryList(5);">
		</select>
		<div class="btn-toolbar">
			<button class="btn btn-danger" onclick="showDialog('4' , 'add');"><i class="icon-plus"></i> 추가</button>
			<button class="btn btn-primary" onclick="showDialog('4' , 'modify');"><i class="icon-plus"></i> 수정</button>
			<div class="btn-group">
			</div>
		</div>
	</div>
	<div style="clear:both;"></div>
</div>

<div class="modal" id="formDialog" style="display:none;">
	<form name="wform" action="" method="post" onsubmit="saveCategory();return false;">
	<input type="hidden" name="category"/>
	<input type="hidden" name="parent"/>
	<input type="hidden" name="depth"/>
	<input type="hidden" name="mode"/>
    <div class="modal-header">
        <button type="button" class="close unblock">×</button>
        <h3>통합코드관리</h3>
    </div>
    <div class="modal-body" style="text-align:left;">
		<label id="code-title">코드관리명</label>
       	<input type="text" name="name" class="input-xlarge">
       	<label id="code-title">사용유무</label>
       	<select name="isUse">
       		<option value="Y">사용합니다.</option>
       		<option value="N">사용하지 않습니다.</option>
       	</select>
    </div>
    <div class="modal-footer">
    	<a href="javascript:saveCategory();" class="btn btn-danger"></i>저장</a>
        <a class="btn unblock">취소</a>
    </div>
    </form>
</div>

<script type="text/javascript">
<!--

$(document).ready(function(){
	setCategoryList(1);
});

function changeCategoryOrd(target , dir)
{
	// 현재 선택된 값이 있는지 체크한다.
	if( $('#' + target + ' option:selected').val() == undefined )
		return alert('선택 카테고리가 없습니다.');
	else
	{
		// 현재 index를 가지고 온다.
		var index = $('#' + target + ' option').index($('#' + target + ' option:selected'));
		
		if( dir == 'up' )
		{
			if( index > 0 )
			{	
				var options = $('#' + target + ' option');				
				$( options[ index - 1 ]).insertAfter( $(options[index]) );
				
				saveCategoryOrd(target);
			}
		}
		else if( dir == 'down')
		{	
			if( index < ($('#' + target + ' option').length - 1) )
			{	
				var options = $('#' + target + ' option');				
				$( options[ index ]).insertAfter( $(options[ index + 1]) );
				
				saveCategoryOrd(target);
			}
		}		
	}
}

function saveCategoryOrd(target)
{
	// get category data
	var categories = [];
	
	$('#' + target +' option').each(function(){
		categories.push($(this).val());
	});

	$.ajax({
		url : 'changeCategoryOrd.do' ,
		type : 'post' ,
		data : 'categories=' + categories ,
		dataType : 'json',
		beforeSend : function(){
			$.blockUI({
				message : '<img src="/assets/admin/images/ajax-loader.gif"/>'
			})
		},
		error : function(){
			alert('서버 설정 도중 에러가 발생되었습니다.');
		},
		success : function(json){
			if( json.result == 'FAILURE' )
				alert(json.msg);
		},
		complete : function(){
			$.unblockUI();
		}
	});
}

function setCategoryList(depth)
{	
	var parent = ( depth > 1 ) ? $('#category-' + (depth - 1) + ' option:selected').val() : '';
	// set current category	
	$('#current-category').text($('#category-' + (depth - 1) + ' option:selected').val());
	
	if( depth < 5 )
	{
		$.ajax({
			url : 'uniformCodeList.do' ,
			type : 'post' ,
			data : 'depth=' + depth + '&parent=' + parent ,
			dataType : 'json' ,
			beforeSend : function(){
				for( var i = (depth + 1) ; i <= 4 ; i++)
					$('#category-' + i).empty();
			},
			error : function(){
				alert('정상적인 접근이 아닙니다.');
			},
			success : function(json){
				if( json.result == 'SUCCESS' )
				{
					// set category
					$('#category-' + json.depth).empty();
					
					var loopCnt = json.categories.length;
					
					for(var i = 0 ; i < loopCnt ; i++)
					{
						var useFlag = (json.categories[i].isUse == 'N') ? '(X)' : '';
						var backgroundColor = ( json.categories[i].isUse == 'N' ) ? 'background-color:#99ccff;' : '';
						
						$('#category-' + json.depth).append('<option value="' + json.categories[i].category + '" data-use="' + json.categories[i].isUse + '" style="' + backgroundColor + '">' + json.categories[i].name + ' ' + useFlag +'</option>');
					}
				}
				else
					alert(json.msg);
			}
		});
	}
}

function saveCategory()
{
	$.ajax({
		url : 'uniformCodeAction.do',
		type : 'post' , 
		data : $('form[name=wform]').serialize() ,
		dataType : 'json' ,
		error : function(){
			alert('처리도중 에러가 발생되었습니다.');
		},
		success : function(json){
			if( json.result == 'SUCCESS' )
			{
				if( $('form[name=wform]').find('input[name=mode]').val() == 'modify' )
				{
					$('#category-' + json.depth + ' option:selected').attr('value' , json.category);
					$('#category-' + json.depth + ' option:selected').attr('data-use' , json.isUse);
					$('#category-' + json.depth + ' option:selected').text(json.name);
				}
				else
					$('#category-' + json.depth).append('<option value="' + json.category + '" data-use="' + json.isUse + '">' + json.name + '</option>');
			}
			else
				alert(json.msg);
		},
		complete : function(){
			$.unblockUI();	
		}
	})
}

function showDialog(depth , mode)
{	
	var f = document.forms.wform;
	
	f.reset();
	
	if( depth == 1 )
		f.parent.value = '';
	else
	{
		var parent = $('#category-' + (depth - 1) + ' option:selected').val();
		
		if( parent == undefined || parent == '' )
		{
			return alert('상위 카테고리가 선택되어있지 않습니다!');
		}
		
		f.parent.value = $('#category-' + (depth - 1) + ' option:selected').val();
	}
	
	if( mode == 'modify' )
	{
		var category = $('#category-' + depth + ' option:selected').val();
		var name = $('#category-' + depth + ' option:selected').text();
		var isUse = $('#category-' + depth + ' option:selected').attr('data-use');
		
		if( category == undefined || category == '' )
			return alert('선택된 카테고리가 존재하지 않습니다.');
		
		f.category.value = category;
		f.isUse.value = isUse;
		f.name.value = name;
	}
	
	f.depth.value = depth;
	f.mode.value = mode;
	
	$.blockUI({
		message : $('#formDialog') ,
		onBlock: function() {
			$('#code-title').text(depth + '차 코드관리명');
			
            $('.unblock').click(function(){
            	$.unblockUI();
            });
        }
	});
}

//-->
</script>
