<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.List" %>
<%@ page import="kr.co.kumsung.thub.domain.*" %>
<%@ page import="kr.co.kumsung.thub.util.*" %>
<%
	List<Category> elCategories = (List<Category>) request.getAttribute("elCategories");
	List<Category> mdCategories = (List<Category>) request.getAttribute("mdCategories");
	List<Category> hgCategories = (List<Category>) request.getAttribute("hgCategories");
%>
<div class="well">
	<form name="wform" action="" method="post">
	<input type="hidden" name="recievers"/>
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
			받는사람
		</th>
		<th>
			<input type="radio" name="recieverFlag" value="Y" checked/> 인증교사
			<input type="radio" name="recieverFlag" value="N"/> 미인증교사
		</th>
	</tr>
	<tr>
		<th>
			수신여부
		</th>
		<th>
			<input type="checkbox" name="isSms" value="N"/> 미수신포함
		</th>
	</tr>
	<tr class="flagWrapper">
		<th>
			학교급/교과 선택
		</th>
		<td>
			<input type="checkbox" name="total" onclick="toggleAll('' , this);"/> 전체선택
		</td>
	</tr>
	<tr class="flagWrapper">
		<th>
			초등<br/>
			<input type="checkbox" name="el" value="A001" onclick="toggleAll('el' , this);"/> 전체선택
		</th>
		<td>
			<ul class="el">
			<% for(Category category : elCategories){ %>
				<li style="width:120px;float:left;"><input type="checkbox" name="targetSubject" value="<%=category.getCategory()%>"/> <%=category.getName() %></li>
			<% } %>
			</ul>
		</td>
	</tr>
	<tr class="flagWrapper">
		<th>
			중등<br/>
			<input type="checkbox" name="md" value="A002" onclick="toggleAll('md' , this);"/> 전체선택
		</th>
		<td>
			<ul class="md">
			<% for(Category category : mdCategories){ %>
				<li style="width:120px;float:left;"><input type="checkbox" name="targetSubject" value="<%=category.getCategory()%>"/> <%=category.getName() %></li>
			<% } %>
			</ul>
		</td>
	</tr>
	<tr class="flagWrapper">
		<th>
			고등<br/>
			<input type="checkbox" name="hg" value="A003" onclick="toggleAll('hg' , this);"/> 전체선택
		</th>
		<td>
			<ul class="hg">
			<% for(Category category : hgCategories){ %>
				<li style="width:120px;float:left;"><input type="checkbox" name="targetSubject" value="<%=category.getCategory()%>"/> <%=category.getName() %></li>
			<% } %>
			</ul>
		</td>
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

$(document).ready(function(){
	
	$('input[name=recieverFlag]').click(function(){
		
		if( $(this).is(':checked'))
		{
			if( $(this).val() == 'Y' )
				$('.flagWrapper').show();
			else
				$('.flagWrapper').hide();
		}
		
	});
	
});

function checkForm()
{
	var f = document.forms.wform;
	var recievers = "";
	var tmp = [];
	
	if( f.message.value == '') return alert('메세지를 입력해주십시오.'); 
	if( getTextByte() <= 0 || getTextByte > 2000 )
		return alert('메세지는 0 bytes 이상 2000 bytes 이하여야 합니다.');
	
	// get targets
	if( f.total.checked == true )
	{
		recievers = 'total';
	}
	else
	{
		if( f.el.checked == true 
				&& f.md.checked == true 
				&& f.hg.checked == true)
		{
			recievers = 'total';
		}
		else
		{
			if( f.el.checked == true )
				tmp.push('A001');
			else
			{
				$('.el').find('input[name=targetSubject]').each(function(){
					if( $(this).is(':checked') )
						tmp.push($(this).val());
				});
			}
			
			if( f.md.checked == true )
				tmp.push('A002');
			else
			{
				$('.md').find('input[name=targetSubject]').each(function(){
					if( $(this).is(':checked') )
						tmp.push($(this).val());
				});
			}
			
			if( f.hg.checked == true )
				tmp.push('A003');
			else
			{
				$('.hg').find('input[name=targetSubject]').each(function(){
					if( $(this).is(':checked') )
						tmp.push($(this).val());
				});
			}
			
			recievers = tmp.join(',');
		}
	}
	
	f.recievers.value = recievers;
	
	// 미인증회원은 과목 설정이 없다.
	if( $('input[name=recieverFlag]').val() == 'N' )
		f.recievers.value = '';	
	
	if( confirm('발송시 시간이 다소 소요될 수 있습니다.\r\n발송 대기하여주시기 바랍니다.') )
	{
		$.ajax({
			url : '/admin/sms/form.do' ,
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
				location.href = 'list.do';
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