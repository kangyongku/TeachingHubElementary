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

    var chkallBtn = $('.allselectBtns');

    chkallBtn.each(function() {
      $(this).on("click", function(e) {
        e.preventDefault();

        var dataWrap = $(this).parents(".datalist_wrap"),
          chkbox = dataWrap.find("input[type=checkbox]"),
          chkName = chkbox.attr("name"),
          chkleng = $("input[name='"+chkName+"']").length;

          console.log(chkName);

/*          $("input[name='"+chkName+"']").prop('checked', true);

          if ( dataWrap.parent().hasClass("chapter_databox")) {
            $("input[name='"+chkName+"']").closest("tbody tr").addClass("checkedbg");
          };*/

      });
    });
  })();

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
