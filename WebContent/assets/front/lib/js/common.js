






// 공통

var ksp = {

	init: function(){
		this.build();
	},

	build: function(){
		ksp.buildMegameu();
		ksp.buildMegameu1();
		ksp.buildSelectBox();
	//	ksp.addClassSmartDevice();
		ksp.buildCheckAll();
		ksp.buildCmtBtnToggle();
		ksp.buildDataBtn();
		ksp.buildQuickmenu();
		ksp.buildCommondataToggleBtn();
		ksp.buildGnb();
		ksp.buildGnb1();
	//	ksp.buildAttachFileLayer();
	//	ksp.buildCommondataViewer();

		$('.placeholder').placeholder();
	},
	buildGnb:function(){
		var self=$(".gnb .g01,.gnb .g02,.gnb .g03").bind("click",function(e){
			e.preventDefault();
			if($(this).hasClass("on")){
				$(this).removeClass("on");
			}
			else{
				self.removeClass("on");
				$(this).addClass("on");

			}
		});
	},
	buildGnb1:function(){
		var self=$(".gnb .m01").bind("click",function(e){
			e.preventDefault();
			if($(this).hasClass("on")){
				$(this).removeClass("on");
			}
			else{
				self.removeClass("on");
				$(this).addClass("on");

			}
		});
	},
	// GNB 메가메뉴
	buildMegameu: function(){
		var oned=false;
		var self=$(".gnb .g01,.gnb .g02,.gnb .g03").bind("click",function(e){
			
			e.preventDefault();
			$(".megamenu").slideDown(200).addClass("show");
			
			showGnbLearning($(this).attr('data-class'));
						
		});
		
	},
	buildMegameu1: function(){
		var oned=false;
		var self=$(".gnb .m01").bind("click",function(e){

			e.preventDefault();
			$("#widedrmenu").slideDown(200).addClass("show");
			
			showGnbLearning1($(this).attr('data-class'));
		});
	},
	
	// 퀵 메뉴
	buildQuickmenu: function(){
		var wrap=$(".quick_menu");

		//cd 다운로드
		wrap.find(".q01").bind("click",function(e){
			e.preventDefault();
			
			showCdDownloadPopup();
			
			$(".download-popup .btn_close").live("click",function(e){
				e.preventDefault();
				$(this).closest(".download-popup").fadeOut(300);
			});
		});
	},

	// 셀렉트 박스 
	buildSelectBox: function(){		
		if($("select.jq-select").length > 0){
			$("select.jq-select").selectbox({
				onOpen: function (inst) {
					//console.log("open", inst);
				},
				onClose: function (inst) {
					//console.log("close", inst);
				},
				onChange: function (val, inst) {
					//console.log("change", inst);
					eval($(this).attr('onchange-handler')); 
				},
				effect: "slide",
				speed: 100
			});
		}
	},
	
	// 체크박스 전체 선택/해제
	buildCheckAll: function(){
		$(".data_list,.list_thumb,.list_thumb_02").each(function(){

			var self=$(this);	
			var wrap=self.parent();
			var btn=wrap.find(".check-all");
			var items=self.find("input:checkbox");
			var img=btn.find("img");
			var img_piece=[];
			
			btn.bind("click",function(e){
				e.preventDefault();
				if($(this).hasClass("on")){
					$(this).removeClass("on")
					items.removeAttr("checked");
					img_piece=img.attr("src").split("unselect_all.gif");
					img.attr("src",img_piece[0]+"select_all.gif");
					
				}else{
					$(this).addClass("on");
					items.attr("checked","checked");
					img_piece=img.attr("src").split("select_all.gif");
					img.attr("src",img_piece[0]+"unselect_all.gif");
				}
				
			});			
		});		
	},
	// 첨부파일 등록 레이어 팝업
	buildAttachFileLayer: function(){
		$(".data_make").each(function(){
			var btn=$(".file_wrap .file");
			btn.bind("click",function(e){
				e.preventDefault();
				var self=$(".layerpopup.attach_file").fadeIn(300).find(".btn_close,.lp_main .btn a:eq(1)").live("click",function(e){					
					e.preventDefault();
					$(".layerpopup.attach_file").fadeOut(300);
				});
				
			});
		});
	},

	buildDataBtn: function(){
		$(".data_buttons").each(function(){
			/*
			var wrap=$(this);
			var download=wrap.find(".download");

			download.bind("click",function(e){
				e.preventDefault();
				$(".download-popup").fadeIn(500);

				$(".download-popup .btn_close").live("click",function(e){
					e.preventDefault();
					$(this).closest(".download-popup").fadeOut(300);
				});
			});
			*/
		});
	},
	buildCmtBtnToggle: function(){
		$(".comment_list .btn > a.reply").each(function(){
			var btn=$(this);
			var wrap=btn.closest("li");
			var textarea=wrap.find(".conts.re");
			
			btn.bind("click",function(e){
				e.preventDefault();
				if(btn.hasClass("cancel")){
					btn.removeClass("cancel").text("답변");
					textarea.hide();
				}
				else{
					btn.addClass("cancel").text("답변취소");
					textarea.show();
				}
			});
		});
	},
	
	addClassSmartDevice: function(){
		var isSmartDevice=false;
		var mobileKeyWords = new Array("iPhone", "iPod", "iPad","BlackBerry", "Android", "Windows CE", "LG", "MOT", "SAMSUNG", "SonyEricsson","SymbianOS","Windows Phone");
		var device_name = "";
		var target_html = $("body");
		for (var word in mobileKeyWords){
			if (navigator.userAgent.match(mobileKeyWords[word]) != null){
				if(mobileKeyWords[word] == "Windows Phone"){
					device_name = "Windows_Phone";
				}
				else{
					device_name = mobileKeyWords[word];
				}
				target_html.addClass(device_name);				
				isSmartDevice=true;				
			}			
		}
		if(!isSmartDevice) target_html.addClass("desktop");
	},
	
	// 공통자료 토글
	buildCommondataToggleBtn: function(){
		$(".common_data").each(function(){
			var comm_data=$(this);			
			var btn=comm_data.find(".header .btn");
			
			btn.bind("click",function(e){
				e.preventDefault();
				if(comm_data.hasClass("hide")){					
					comm_data.removeClass("hide");					
					$(this).find("span").html("항상숨기기");
					//$.removeCookie('allways');
					$.cookie('thub_common_data' , 'show');
				}else{
					comm_data.addClass("hide");
					$(this).find("span").html("항상보이기");					
		/*			console.log($.cookie('allways','true'));
					console.log($.cookie('allways'));*/
					$.cookie('thub_common_data' , 'hide');
				}
			});
		});
	},
		/*
	buildCommondataViewer: function(){			
		if($.cookie('allways')){
			$(".common_data").addClass("hide");
		}
	}*/
}


// 교수학습자료
var ksp_gs={

	init: function(){
		ksp_gs.buildMultimediaView();
	},

	buildMultimediaView: function(){
		/*
		var layerpopup=$(".gs_area .layerpopup");
		
		$(".list_thumb > li").each(function(){
			$(this).find(".view").bind("click",function(e){
				e.preventDefault();
				layerpopup.fadeIn(300).find(".btn_close").live("click",function(){
					layerpopup.fadeOut(300);
				});
				ksp_gs.buildMultimediaSlider();
			});
		});
		*/
	},

	buildMultimediaSlider: function(){
		$(".carousel_wrap ul").bxSlider({
			slideWidth: 401,
			infiniteLoop: false,
			hideControlOnEnd: true,
			pager: false,
			prevSelector: $(".carousel_wrap .left"),
			nextSelector: $(".carousel_wrap .right")				
		});
	}

}

$(document).ready(function(){
	ksp.init();
	ksp_gs.init();
});

