<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<div class="well">
	<form name="wform" action="" method="post">
	<table class="table">
	<col width="120"/>
	<col width="*"/>
	<tbody>
	<tr>
		<th>
			메세지
		</th>
		<th>
			<textarea name="message" onkeyup="textByteCheck();" style="width:120px;height:180px;"></textarea>
			<br/>(<span id="messageByte">0</span>/2000byte)
		</th>
	</tr>
	<tr>
		<th>
			보내는번호
		</th>
		<th>
			<input type="text" name="senderNum"/> ( '-' 없이 입력하세요~)
		</th>
	</tr>
	<tr>
		<th>
			받는번호
		</th>
		<th>
			<input type="text" name="recieverNum"/> ( '-' 없이 입력하세요~)
		</th>
	</tr>
	</tbody>
	</tbody>
	</table>
	</form>
</div>

<div class="btn-toolbar">
	<button class="btn btn-primary" onclick="checkForm();"><i class="icon-plus"></i> 발송하기</button>
	<div class="btn-group">
	</div>
</div>

<script type="text/javascript">
<!--

function checkForm()
{
	var f = document.forms.wform;
	
	if( f.message.value == '') return alert('메세지를 입력해주십시오.'); 
	if( getTextByte() <= 0 || getTextByte > 2000 )
		return alert('메세지는 0 bytes 이상 2000 bytes 이하여야 합니다.');
	if( f.senderNum.value == '') return doError('보내는 사람의 전화번호를 입력해주십시오.' , 'senderNum' , 'wform');
	if( f.recieverNum.value == '') return doError('받는 사람의 전화번호를 입력해주십시오.' , 'recieverNum' , 'wform');
	
	if( confirm('발송시 시간이 다소 소요될 수 있습니다.\r\n발송 대기하여주시기 바랍니다.') )
	{
		$.ajax({
			url : '/admin/sms/single.do' ,
			type : 'post' ,
			data : $('form[name=wform]').serialize() ,
			dataType : 'json' ,
			beforeSend : function(){
				$.blockUI({
					message : '<img src="/assets/admin/images/ajax-loader.gif"/>'
				});
			},
			error : function(){
				alert('서버와의 통신도중 에러가 발생되었습니다.');
			},
			success : function(json){
				alert('발송을 완료하였습니다.');
			},
			complete : function(){
				$.unblockUI();
			}
		});
	}
}

function toggleAll(target , obj)
{	
	if( target == '')
	{
		$('li').find('input[name=targetSubject]').attr('checked' , obj.checked);
		
		if( obj.checked )
		{
			$(obj).parent().parent().next().hide().next().hide().next().hide();
		}
		else
		{
			$(obj).parent().parent().next().show().next().show().next().show();
		}
	}
	else
	{
		$('.' + target).find('input[name=targetSubject]').attr('checked' , obj.checked);
		
		if( obj.checked )
			$('.' + target).find('input[name=targetSubject]').parent().hide();
		else
			$('.' + target).find('input[name=targetSubject]').parent().show();
	}
}

function textByteCheck()
{
	$('#messageByte').text(getTextByte());
}

function getTextByte()
{
	var str = new String($('textarea[name=message]').val());
	var _byte = 0;
	
	if (str.length != 0)
	{
		for ( var i = 0; i < str.length; i++)
		{
			var str2 = str.charAt(i);
			
			if (escape(str2).length > 4)
				_byte += 2;
			else
				_byte++;
		}
	}
	
	return _byte;	
}


//-->
</script>