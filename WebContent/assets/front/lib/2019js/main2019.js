$(document).on('ready', function() {
	
		$('.topbanner').css('margin-top','0');
		  $('.autoplay').slick({
			  arrows: true,
			  slidesToShow: 2,
			  slidesToScroll: 1,
			  autoplay: true,
			  autoplaySpeed: 3000,
				}
			);
		  $('.fade').slick({
			  
			  arrows: false,
			  dots: true,
			  infinite: true,
			  speed: 500,
			  fade: true,
			  cssEase: 'linear',
			  autoplay: true,
			  autoplaySpeed: 4000,
pauseOnDotsHover: true,
			});
			
			$(".search_new").on("mouseover" , function(){
    		$(".btn_s img").attr('src','/assets/front/2019img/main/btn_search_on.png');
		});
		
		$(".search_new").on("mouseleave" , function(){
    		$(".btn_s img").attr('src','/assets/front/2019img/main/btn_search_off.png');
		});
		$(".search_dic").on("mouseover" , function(){
    		$(".btn_s2 img").attr('src','/assets/front/2019img/main/btn_searchs_on.png');
		});
		
		$(".search_dic").on("mouseleave" , function(){
    		$(".btn_s2 img").attr('src','/assets/front/2019img/main/btn_searchs_off.png');
		});
		
		$(".cover").on("mouseenter" , function(){
    		$(this).children('.bookdata').stop().animate({opacity: 1}, 200, 'easeInOutBack');
			$(this).css('cursor','default');
			
		});
		$(".cover").on("mouseleave" , function(){
    		$(this).children('.bookdata').stop().animate({opacity: 0});
			$(this).css('cursor','pointer');
		});
		
		$(".quickmenu_m ul li a").on("mouseenter" , function(){
    		$(this).children('.qmoff').stop().animate({opacity: 0,right:64}, 500, 'easeInOutBack');
			$(this).children('.qmon').stop().animate({opacity: 1,right:0}, 500, 'easeInOutBack');
		});
		
		$(".quickmenu_m ul li a").on("mouseleave" , function(){
    		$(this).children('.qmoff').stop().animate({opacity: 1,right:0}, 500, 'easeInOutBack');
			$(this).children('.qmon').stop().animate({opacity: 0,right:-64}, 500, 'easeInOutBack');
		});

		$('.slick-dots li').on('mouseover', function() {
    		$(this).parents('.fade').slick('goTo', $(this).index());
		});
		
		$('.setting a').on('mouseover', function() {
    		$('.setting a img').addClass('gear');
		});	
		
		$('.setting a').on('mouseleave', function() {
    		$('.setting a img').removeClass('gear');
		});	
		
		
		
		$(".topbanner .closetb").on("click" , function(){
    		$('.topbanner').css('margin-top','-124px');
			
			
		});
		
});