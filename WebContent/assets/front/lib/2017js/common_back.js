$(function() {

	/* lnb toggle */
	var lnbToggle = (function() {
	  var unitLnb = $(".nav_box .chapter_item.unit"),
		lnb = unitLnb.find(".lnb"),
		subLnb = lnb.find(".sub_lnb"),
		Btn = unitLnb.find(".unit_lnb_toggle"),
		state = true;
  
		if ( Btn.hasClass("close") ) {
		  subLnb.hide();
		  Btn.find("span").text("단원 펼치기");
		} else {
		  subLnb.show();
		  Btn.find("span").text("단원 접기");
		};
  
		Btn.on("click", function(e) {
		  e.preventDefault();
		  if ( $(this).hasClass("close")) {
			subLnb.show();
			$(this).removeClass("close").find("span").text("단원 접기");
		  } else {
			subLnb.hide();
			$(this).addClass("close").find("span").text("단원 펼치기");
		  }
		});
  
	})();
  
	/* 자료테이블 펼치기/접기 */
	var databoxToggle = (function() {
	  var btn = $(".databox_toggle");
  
	  btn.on("click", function(e) {
		e.preventDefault();
		$(this).toggleClass("close");
	  });
  
	})();
  
	/* 자료리스트 tab 하위분류 있을 시 높이 조절 */
	var dataTab = (function() {
	  var tab = $(".datalist_wrap .data_tab"),
		tabList = tab.find("li.on");
  
		tabList.each(function () {
		  if ( $(this).find("ul").is(".item_list") ) {
			$(this).find("> a").addClass("arrow");
			$(this).parents(".data_tab").css("height","62px")
		  }
		})
	  })();
	  
	  /* 추가 (2018.01.12 수정) */
	  $(".tbl_data, .weblink_data_area").hide();
  
	  $(".datalist_wrap").each(function(){
		  $(this).find(".tbl_data:first, .weblink_data_area:first").show(); 
	  });
	  
	  
	  /* 2018.01.12 수정 탭 클릭시 스타일 변경 */
	  $(".data_tab").each(function(){
		  $(this).find("li").click(function(){
			  $(this).addClass("on").siblings().removeClass("on");
			  $(this).closest(".datalist_wrap").find(".tbl_data, .weblink_data_area").hide();
			  var tablistOn = $(this).parent().find(">li.on");
			  var activeTab = $(this).find("> a").attr("rel");
			  $("#" + activeTab).show(); /* 선택된 탭에 해당하는 게시판 보여주기 (2018.01.08 수정)*/
  
			  if ( tablistOn.find("ul").is(".item_list") ) {
				  tablistOn.find("> a").addClass("arrow");
				  tablistOn.parents(".data_tab").css("height","62px");
			  };
		  });
	  });
  
	  /* 학생참여수업 탭메뉴 */
	  $(".class_tab_wrap>ul>li").click(function(){
		  $(this).addClass("on").siblings().removeClass("on");
		  $(".class_tab_content").hide();
		  var activeTab = $(this).find("> a").attr("rel");
		  $("#" + activeTab).show();
	  });
  
	  $(".class_tab_content").hide();
	  $(".sub_content").find(".class_tab_content.on").show();
  
  
	/* 테이블 체크박스 선택 시 배경색 */
	var chapchkBG = (function() {
	  var tblWrap = $(".chapter_databox .tbl_data");
  
	  tblWrap.each(function() {
		var chkbox = $(this).find("table tbody input[type='checkbox']");
  
		chkbox.on("change", function() {
		  if ( $(this).is(':checked') ) {
			$(this).closest("tr").addClass("checkedbg");
		  } else {
			$(this).closest("tr").removeClass("checkedbg");
		  }
		})
	  });
	})();
  
	/* table checkbox 전체선택 */
	var checkBox_all = (function() {
  
	  var chkallBtn = $('.allselectBtn');
  
	  chkallBtn.each(function() {
		$(this).on("click", function(e) {
		  e.preventDefault();
  
		  var dataWrap = $(this).parents(".datalist_wrap"),
			chkbox = dataWrap.find("input[type=checkbox]"),
			chkName = chkbox.attr("name"),
			chkleng = $("input[name='"+chkName+"']").length;
  
			//console.log(chkName);
  
			$("input[name='"+chkName+"']").prop('checked', true);
  
			if ( dataWrap.parent().hasClass("chapter_databox")) {
			  $("input[name='"+chkName+"']").closest("tbody tr").addClass("checkedbg");
			};
  
		});
	  });
	  })();
	  
	  /* 미리보기,스크랩,다운로드 버튼 비활성화 (2018.01.04 수정)*/
	  $(".disabled").each(function(){
		  var btn_img = $(this).find("img"),
				  btn_ico = btn_img.attr("src");
		  btn_img.attr({"src":btn_ico.replace("btn_ico","btn_ico_disabled")});
	  });
  
  
  
  
	/* custom select box */
	/*
	 * option
	 * disabled="disabled"
	 */
	(function($) {
		$.fn.extend({
			selectBox: function() {
				$(this).each(function(){
					return new SelectBox(this);
				});
			}
		});
  
		SelectBox = (function(){
			function SelectBox(elm) {
				var self = this;
  
				self.$select = $(elm);
				self.$selectWrap = self.$select.parent();
				self.$selectList = null;
				self.placeholder = self.$select.data("placeholder") || null;
				self.disable = self.$select.prop("disabled");
				self.open = false;
				self.optLen = 0;
				self.index = self.$select.data("selected") || null;
  
				self._setSelect();
				self._eventSet();
			}
			SelectBox.prototype._setSelect = function() {
				var self = this,
					selected = false,
					$selected = $(self.disable ? "<span class='disabled selected'></span>" : "<a href='#' class='selected'></a>");
  
				self.$select.siblings(".selected") ? self.$select.siblings(".selected").remove() : null;
				self.$select.find("option.noneOption") ? self.$select.find("option.noneOption").remove() : null;
  
				self.$select.find("option").each(function(index) {
					if( $(this)[0].defaultSelected  ) {
						selected = true;
					}
				});
  
				if(self.placeholder != null) {
					self.$select.prepend("<option value='' class='noneOption' disabled>"+self.placeholder+"</option>");
				}
  
				if(!selected) {
					if (self.index != null){
						self.$select.find("option:eq("+self.index+")").prop("selected", true);
						$selected.html("<span>" + self.$select.find("option:eq("+self.index+")").text() + "</span>");
					} else {
						if(self.placeholder != null) {
							self.$select.find("option:eq(0)").prop("selected", true);
							$selected.html("<span>" + self.placeholder + "</span>").addClass("placeholder");
						} else {
							$selected.html("<span>" + self.$select.find("option:selected").text() + "</span>");
						}
					}
				} else {
					$selected.html("<span>" + self.$select.find("option:selected").text() + "</span>");
				}
  
				self.$selectWrap.append($selected);
				self.optLen = self.$select.find("option").length;
  
				if( self.$select.data("width") ){
					self.$selectWrap.css({"width": self.$select.data("width")})
				} else {
					self.$selectWrap.css({"width" : self.$select.outerWidth()});
				}
  
				self.$select.hide();
			};
			SelectBox.prototype._eventSet = function() {
				var self = this;
  
				$(document).on("focusin click", function(e){
					if( !(self.$selectWrap.has(e.target).length || self.$selectWrap.is($(e.target))) && self.open){
						self._removeList();
					}
				});
  
				self.$select.on("change", function() {
				   var index = $(this).find("option:selected").index();
  
				   $(this).data("selected", index);
  
				   self.index = index;
				   self._setSelect();
				   self._eventSet();
				});
  
				self.$selectWrap.find(">a").on("click", function(e){
					e.preventDefault();
  
					if(self.open){ self._removeList(); }
					else if(self.optLen) {
						if(self.optLen == 1 && self.$select.find("option").hasClass("noneOption")) {
							return false;
						}
						self._setList();
					}
				});
			};
			SelectBox.prototype._setList = function() {
				var self = this,
					list = "<ul class='select_list_wrap'>";
  
				self.$select.find("option").each(function(i){
					var $opThis = $(this);
  
					if( self.placeholder != null && i == 0 ) {
						list += "";
					} else if($opThis.is(":selected")){
						// list += "<li class='on' data-index=" + i + "><a href='#'><span>" + $opThis.text() + "</span></a></li>";
					} else {
						list += "<li data-index=" + i + "><a href='#'><span>" + $opThis.text() + "</span></a></li>";
					}
				});
				list += "</ul>";
  
				self.$selectWrap.addClass("open");
				self.open = true;
  
				self.$selectList = $(list);
				self.$selectList.appendTo(self.$selectWrap);
  
				self._hasScroll();
  
				self.$selectList.find("a").on("click", function(e){
					var $optThis = $(this);
  
					self.$selectWrap.find(".selected").removeClass("placeholder");
					self.$selectWrap.find(".selected>span").text($optThis.text());
					self.$select.find("option").eq($optThis.parent().data("index")).prop("selected", true);
					self.$select.trigger("change");
  
					self._removeList();
  
					e.preventDefault();
				});
			};
			SelectBox.prototype._removeList = function() {
				var self = this;
  
				self.$selectWrap.removeClass("open");
				self.$selectList.remove();
				self.$selectList = null;
				self.open = false;
			}
			SelectBox.prototype._hasScroll = function() {
				var self = this;
  
				if( self.$selectList.get(0).scrollHeight > self.$selectList.innerHeight() ){
					self.$selectList.find("li").css({
						"padding-right": function(index, value) {
							return parseInt(value) - 17;
						}
					})
				}
			}
			return SelectBox;
		})();
	})(jQuery);
  
  });
  
  /* 모달 레이어 팝업 */
  function modal_open(_target) {
  
	  var $ui = $(_target);
	var $h5 = $ui.find("h5");
	  var $content = $ui.find(".pop_content");
  
	$ui.show().closest(".modal").show();
  
	$(window).resize(function() {
	  modal_resize()
	});
  
	if ( $ui.height() >= $(window).height()*0.8 ) {
	  $ui.find(".con_scroll_area").css({"height":$(window).height()*0.8},{"overflow-y":"auto"});
	}
  
	  function modal_resize() {
  
		  if (!$ui.is(":visible")) return;
		  $content.css({ height:"" });
		  $ui.css({ marginTop:-$ui.height() / 2 });
	  if ( $ui.height() >= $(window).height()*0.8 ) {
		$ui.find(".con_scroll_area").css({"height":$(window).height()*0.8},{"overflow-y":"auto"});
	  }
	  };
  
	  modal_resize();
  }
  
  function modal_close(_target) {
  
	  var $ui = $(_target);
	$ui.hide().closest(".modal").hide();
  };
  
  
  

$(document).ready(function(){



	/* 썸네일 게시판 갤러리형 플립 */
	$(".thumbnail_flip").flip({
		trigger: 'hover'
	});

	/* 썸네일 게시판 랜덤 백그라운드 */

	var lastPick;
	var rand;
	$(".thum_board_list").find(".nothumbnail").each(function() {
  		$(this).css('background',randomColor());
	});
	function randomColor() {
  		var back = ["#fb6060","#1db7ae","#d6b121","linear-gradient(135deg,#fb6060, #ff842a )","linear-gradient(135deg, #57cc47, #1db7ae )"];
  		rand = back[Math.floor(Math.random() * back.length)];
  		rand==lastPick?randomColor():rand;
  		lastPick = rand;
  		return rand;
	};



	$('#slider003').flexslider({
		animation: "fade",
		animationLoop: true,
		smoothHeight: false,
		slideshow: true,
		slideshowSpeed:5000,
		animationSpeed: 600,
		controlNav: true,
		directionNav: true,
		prevText: "이전",
		nextText: "다음",
		pausePlay: true
	});

	$(".flexslider").not("#slider003").each(function(k,e){
		var Main_S_flex_right=($(e).find('.flex-control-nav').outerWidth(true))+"px";
		$(e).find(".flex-direction-nav").css("right",Main_S_flex_right);
	});


	$(".top_ico a").click(function(){
		$('html,body').animate({scrollTop: '0px'}, 300);
	});


	$('.login_btn').click(function(){
		$(this).parent().hide();
		$('.quickmenu_tp').show();
		$('.header_ul2 > li.li1').hide();
		$('.header_ul2 > li.li2').show();
		$('.all > li:first-child > a > img').attr("src","/teaching/images/main/main10_on.gif");
		
	});

	$('.id_check input').click(function(){
		if ( $(this).parent().hasClass("on")){
			$(this).parent().removeClass("on");
		}else{
			$(this).parent().addClass("on");
		}
	});






$(".menu_depth_lf > li > a").click(function(){
	$(".s-menu0").slideDown("fast");
});

$(".s_close").click(function(){
	$(".s-menu0").stop().slideUp("fast");
});




setInterval(function(){
	$(".mCSB_dragger_bar_hei").css({"height":$(".mCSB_dragger").css("top")});
},50)



});

$(window).load(function(){
	$("#content-5").mCustomScrollbar({
		theme:"rounded-dots",
		scrollInertia:590
	});
});


// select 스타일때문에 구 common.js에서 가져옴 닷컴작업 
var ksp = {

	init: function(){
		this.build();
	},

	build: function(){
	
		ksp.buildSelectBox();
		$('.placeholder').placeholder();
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
}

$(document).ready(function(){
	ksp.init();
	ksp_gs.init();
});



