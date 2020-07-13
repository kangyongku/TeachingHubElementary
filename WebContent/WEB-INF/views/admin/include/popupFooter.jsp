<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<footer>
<div style="align:center">
	<a href="javascript:history.onclick=closeWin()"><div class="btn">창닫기</div></a>
</div>
<script type="text/javascript">
<!--
function closeWin() {
	if(confirm("정말 닫으시겠습니까?")){
	    var nvua = navigator.userAgent;
	    if (nvua.indexOf('MSIE') >= 0){
	        if(nvua.indexOf('MSIE 5.0') == -1) {
	            top.opener = '';
	        }
	    } else (nvua.indexOf('Gecko') >= 0){
	        top.name = 'CLOSE_WINDOW';
	        wid = window.open('','CLOSE_WINDOW');
	    }
	    top.close();
	}
}
// -->
</script>


</footer>
