$(document).ready(function(){
    
  var winWidth = $(window).width();
  var winCenter = $(window).width()/2;

  if (winWidth <= 1100) {
    var mainWidth = 1100;
  }else{
    var mainWidth = winWidth;
  };
  $(".main01, .main02, .main03").css('width', mainWidth);
  var totalWidth = $(".visual_wrap").outerWidth()+mainWidth*3+$(".footer_wrap").width();
  $(".container, .shade").css('width',totalWidth);
  

  //horizontal scroll
  $("html").mousewheel(function(e, delta) {
    this.scrollLeft -= (delta *50);
    e.preventDefault();
  });

  $(".mybook_list").mousewheel(function(e, delta){
    $(".mybook_list").scrollTop -= (delta *20);
    e.stoppropagation();
    e.preventDefault();
  });  

  
  //페이지 로딩시 GNB close
  setTimeout(function(){
    $(".header_wrap, .logo, .bottom_menu, .bottom_wrap> ul li").stop().addClass("off");
    $(".bottom_layer").animate({left:'110px'},200);
    $(".visual_wrap").animate({padding:'0 0 0 110px'},200);
    $(".gnb_wrap, .middle_wrap, .shade, .sub_menu_list ul").hide();
    $(".grade_list>a, .sub_menu_list>a, .txtbook_list, .txtbook_cont").removeClass('on');
    $(".gnb_off").fadeIn();
  },1000);

  //GNB open
  $(".gnb_off").click(function() {
    $(".header_wrap, .logo, .bottom_menu").stop().removeClass("off");
    $(".bottom_layer").animate({left:'210px'},500);
    $(".gnb_off").fadeOut();
    $(".gnb_wrap, .middle_wrap").stop().delay(400).fadeIn();
    if ($("html").scrollLeft() == 0){
      $(".visual_wrap").animate({padding:'0 0 0 210px'},200);
    };
  });

  //GNB close
  $(".container").click(function(){
    $(".header_wrap, .logo, .bottom_menu, .bottom_wrap> ul li").stop().addClass("off");
    $(".visual_wrap").animate({padding:'0 0 0 110px'},200);
    $(".gnb_wrap").hide();
    $(".gnb_off").fadeIn();
    $(".middle_wrap").stop().hide();
  });

  //교과서 메뉴 오픈
  $(".grade_list>a").click(function(){
    $(".shade").show();
    $(".sub_menu_list>a, .grade_list>a, .txtbook_list, .txtbook_cont").removeClass("on");
    $(this).parent().find(".txtbook_list, .txtbook_list>li>a:first").addClass("on");
    $(".txtbook_list>li>a:first").parent().find(".txtbook_cont").addClass("on");
    $(this).addClass('on');
    $(".sub_menu_list ul, .bottom_layer").hide();
  });

  $(".txtbook_list>li>a").click(function(){
    $(".txtbook_list>li>a, .txtbook_cont").removeClass("on");
    $(this).addClass("on");
    $(this).parent().find(".txtbook_cont").addClass("on");
  });

  //서브 메뉴 오픈
  $(".sub_menu_list>a").click(function(){
    $(".shade").show();
    $(".sub_menu_list").find('ul').hide();
    $(this).parent().find('ul').fadeIn();
    $(".sub_menu_list>a, .grade_list>a, .txtbook_list, .txtbook_cont").removeClass('on');
    $(this).addClass('on');
    $(".txtbook, .bottom_layer").hide();
  });

  //로그인 레이어 오픈
  $(".login").click(function(){
    if ($(".header_wrap").hasClass('off')){
      $(".bottom_layer").css('left','110px');
    } else {
      $(".bottom_layer").css('left','210px');
    };
    $(".shade").show();
    $(".login_layer").fadeIn();
    $(".grade_list>a, .sub_menu_list>a, .txtbook_list, .txtbook_cont").removeClass('on');
    $(".txtbook, .sub_menu_list ul, .mypage_layer, .search_layer").hide();
  });

  //마이페이지 레이어 오픈
  $(".mypage").click(function(){
    if ($(".header_wrap").hasClass('off')){
      $(".bottom_layer").css('left','110px');
    } else {
      $(".bottom_layer").css('left','210px');
    };
    $(".shade").show();
    $(".mypage_layer").fadeIn();
    $(".grade_list>a, .sub_menu_list>a, .txtbook_list, .txtbook_cont").removeClass('on');
    $(".txtbook, .sub_menu_list ul, .login_layer, .search_layer").hide();
  });
  
  $(".mybook_list").slimScroll({
    height:'400px',
    alwaysVisible:true,
    color:'#222222',
    allowPageScroll: false,
    distance: '10px',
  });

  $(".txtList").slimScroll({
    height:'500px',
    alwaysVisible:true,
    color:'#ffffff',
    allowPageScroll: false,
    railColor:'#740f00',
    distance: '0px',
  });
  

  //퀵검색 레이어 오픈
  $(".search").click(function(){
    if ($(".header_wrap").hasClass('off')){
      $(".bottom_layer").css('left','110px');
    } else {
      $(".bottom_layer").css('left','210px');
    };
    $(".shade").show();
    $(".search_layer").fadeIn();
    $(".grade_list>a, .sub_menu_list>a, .txtbook_list, .txtbook_cont").removeClass('on');
    $(".txtbook, .sub_menu_list ul, .mypage_layer, .login_layer").hide();
    $(function () {
      $('.search_list').slick({
          vertical: true,
          centerMode: true,
          infinite: true,
          slidesToShow: 6,
          slidesToScroll: 1,
          autoplay: true,
          autoplaySpeed: 1500,
          pauseOnHover: true,
          swipe: false,
          arrows: false,
      });
    });
  });

  //추천검색어 클릭 시 검색어 등록
  $(".search_list li a").click(function(){
    var searchWord = $(this).text();
    $("#search").val(searchWord);
  });

  //교과서 메뉴, 서브 메뉴, 레이어 닫기
  $(".close").click(function(){
    $(".shade, .txtbook, .sub_menu_list ul, .bottom_layer").fadeOut();
    $(".grade_list>a, .sub_menu_list>a, .txtbook_list, .txtbook_cont").removeClass('on');
    setTimeout(function(){
      $(".header_wrap, .logo, .bottom_menu, .bottom_wrap> ul li").stop().addClass("off");
      $(".visual_wrap").animate({padding:'0 0 0 110px'},200);
      $(".gnb_wrap, .middle_wrap").hide();
      $(".gnb_off").fadeIn();
    },1000);
  });
  
});