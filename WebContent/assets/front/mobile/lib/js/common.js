$(document).ready(function(){
	var lib={
		header: $("#header"),
		contents: $("#contents"),
		footer: $("#footer"),
		buildNav: function(){

			var gnb=$("#gnb");						
			var wrap=$(".nav");
			var menu=wrap.find(".menu");
			var items=menu.find("> li");			
			
			// GNB Navigation 높이 설정
			wrap.height(menu.find("> .on > .sub_menu").height()+menu.height());		
			
			// 메뉴
			menu.find("> li > a").bind("click",function(e){
				e.preventDefault();
				items.removeClass("on");
				$(this).parent("li").addClass("on");
				wrap.height(menu.find("> .on > .sub_menu").height()+menu.height());	// GNB Navigation 높이 재설정
				setTimeout(lib.buildGnbHeight,50);
			});

		},
		buildGnbHeight: function(){
			if($("#gnb").height() < $(document).height()){				
				$("#gnb").height($(document).height());
			}
		},
		buildSubNavEvent: function(){
			$(".nav .sub_menu").delegate("li","mouseover",function(e){
				var self = $(this);

				
				var emptyCount=self.parent().children().length - self.find(".third_menu > li").length;		
				if(emptyCount){
					while(emptyCount > 0){
						self.find(".third_menu").append("<li class='empty'></li>");
						emptyCount--;
					}
				}
				self.parent().children().removeClass("on");
				self.delay(3000).addClass("on");
				self.find(".third_menu").height($("#gnb").height()-130).find("li").eq(self.index()).addClass("focus");
				
				e.stopPropagation();
				$(document).bind("mouseover",function(){					
					self.removeClass("on");
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
					},
					effect: "slide",
					speed: 100
				});
			}
		}		
	}
	lib.buildNav();
	lib.buildSelectBox();
	lib.buildSubNavEvent();
	setTimeout(lib.buildGnbHeight,50);
});