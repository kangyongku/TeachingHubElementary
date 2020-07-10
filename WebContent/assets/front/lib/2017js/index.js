
var page = 0;
var slided = true;

function documentWheelEvent(bind) {
    if (bind == "on") {
        $("html").on("mousewheel", function(e, delta) {
            // this.scrollLeft -= (delta *50);
            if (slided) {

                slided = false;

                page = (delta < 0) ? page + 1 : page - 1;
                page = (page < 0) ? 0 : page;
                page = (page > 4) ? 4 : page;

                var header_wrap_width = $(".header_wrap").width();
                var visual_wrap_width = $(".visual_wrap").width();
                var main01_pos = $(".main01").width();
                var main02_pos = $(".main02").width();
                var main03_pos = $(".main03").width();
                var footer_pos = $(".footer_wrap").width();

                switch (page) {
                    case 0: move_size = 0; break;
                    case 1: move_size = (visual_wrap_width) * -1; break;
                    case 2: move_size = (visual_wrap_width + main01_pos) * -1; break;
                    case 3: move_size = (visual_wrap_width + main01_pos + main02_pos) * -1; break;
                    case 4: move_size = (visual_wrap_width + main01_pos + main02_pos + footer_pos) * -1; break;
                    default:
                }
                $(".container").animate({ left : move_size +"px" }, 700, function() {
                    slided = true;
                });
            }

            e.preventDefault();
        });
    } else {
        $("html").off("mousewheel");
    }
}

function bookTitleClickEvent(e) {
    var $txtbook_showbox = $(this).closest(".booktitle_box").find(".txtbook_showbox");
    var $showbox_left = $txtbook_showbox.find(".showbox_left");
    var $showbox_right = $txtbook_showbox.find(".showbox_right");
    var category = $(this).attr("data-category");
    var dataid = $(this).attr("data-id");
    var cover = $(this).attr("data-cover");

    var subject = $(this).data("subject");
    var author = $(this).data("author");
    var revision = $(this).data("revision");
    var revision_class = $(this).parent().find("span").attr("class");

    $showbox_left.find("img").attr("src", cover);
    $showbox_right.find("[name=txtSubject]").html(subject);
    $showbox_right.find("[name=author]").html(author);
    $showbox_right.find(".txtbook_title a").attr("href", "/learning/detail.do?category="+category+"&bookId="+dataid);

    if (revision) {
        $showbox_right.find("[name=revision]").html(revision);
        if (revision_class) {
            $showbox_right.find("[name=revision]").attr("class", revision_class);
        }
        $showbox_right.find("[name=revision]").show();
    } else {
        $showbox_right.find("[name=revision]").hide();
    }

    e.preventDefault();
}

function groupby(array) {
    var items = [];
    $.each(array, function(i, item){
    	if($.inArray(item, items) === -1) {
            items.push(item);
        }
    });
    return items;
}

function shuffleArray(array) {
    for (var i = array.length - 1; i > 0; i--) {
        var j = Math.floor(Math.random() * (i + 1));
        var temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    return array;
}

function bubbleAnimation($items, delay, duration) {
    var d = 200;
    var items = [];

    $items.each(function() { items.push($(this)); });

    // Random
    item = shuffleArray(items);

    $.each(items, function(i, item) {
        var $bubble = $(item);
        var w = $bubble.width();
        var h = $bubble.height();
        $bubble.css({opacity: 0.3, width: 0, height: 0, marginLeft: w/2, marginTop: h/2});
        setTimeout(function(){
            // easeOutElastic, easeOutBounce
            $bubble.animate({"marginTop": 0, "marginLeft": 0, "height": w, "width": h, "opacity" : 1}, duration, 'easeOutElastic');
        }, d);
        d += delay;
    });
}

function mainWrapResize() {
    var winWidth = $(window).width();
    var winHeight = $(window).height(); /* 2018.01.29 수정 */
    var main01_height = $(".main01_wrap").height();
    var main02_height = $(".main02_wrap").height();
    var main03_height = $(".main03_wrap").height();
    $(".main01_wrap").css('marginTop', (winHeight / 2) - (main01_height / 2));
    $(".main02_wrap").css('marginTop', (winHeight / 2) - (main02_height / 2));
    $(".main03_wrap").css('marginTop', (winHeight / 2) - (main03_height / 2));

    var mainWidth = (winWidth <= 1100) ? 1100 : winWidth;

    $(".main01, .main02, .main03").css('width', mainWidth);
    var totalWidth = $(".visual_wrap").outerWidth() + mainWidth * 3 + $(".footer_wrap").width();
    $(".container, .shade").css('width', totalWidth);
}

$(window).resize(mainWrapResize);

$(document).ready(function() {

    // var winWidth = $(window).width();
    // var winCenter = $(window).width() / 2;
    // var winHeight = $(window).height(); /* 2018.01.29 수정 */

    // if (winWidth <= 1100) {
    //     var mainWidth = 1100;
    // } else {
    //     var mainWidth = winWidth;
    // };

    // $(".main01_wrap").css('margin-top',winHeight*0.2); /* 2018.01.29 수정 */
    // $(".main02_wrap").css('margin-top',winHeight*0.23); /* 2018.01.29 수정 */
    // $(".main03_wrap").css('margin-top',winHeight*0.08); /* 2018.01.29 수정 */



    // 페이지 리사이즈
    // $(".main01, .main02, .main03").css('width', mainWidth);
    // var totalWidth = $(".visual_wrap").outerWidth() + mainWidth * 3 + $(".footer_wrap").width();
    // $(".container, .shade").css('width', totalWidth);

    document.body.style.overflowX = 'hidden';

    // 페이지 리사이즈
    mainWrapResize();
    // 휠 이벤트
    documentWheelEvent("on");

    // 휠 이벤트 Block
    $(".gnb_wrap, .shade, .bottom_layer, .shade_all, .pop_menu").on("mouseenter", function() {
        documentWheelEvent("off");
    }).on("mouseleave", function() {
        documentWheelEvent("on");
    });

    // 도서명 클릭 이벤트
    $(".txtbook_cont").find(".booktitle_box ul li > a").on("hover", bookTitleClickEvent);

    // 표지로 보기, 도서명 보기 Event
    $(".txtbook_cont .txtbook_toggle2").data("toggle", "title");
    $(".booktitle_box").show();
    $(".bookcover_box").hide();

    $(".txtbook_cont .txtbook_toggle2").on("click", function(e) {
        var toggle = $(this).data("toggle");
        if (toggle == "title") {
            $(this).find("img").attr("src", "/assets/front/2017img/main/btn_booktitle.jpg");
            $(".booktitle_box").hide();
            $(".bookcover_box").show();
            $(this).data("toggle", "cover");
        } else {
            $(this).find("img").attr("src", "/assets/front/2017img/main/btn_cover.jpg");
            $(".booktitle_box").show();
            $(".bookcover_box").hide();
            $(this).data("toggle", "title");
        }
    });

    //교과서 메뉴 오픈
    $(".grade_list > a").click(function(e) {

        // Dim
        $(".shade").show();

        // lnb 초기화
        $(".grade_list > a").removeClass("on");
        $(".sub_menu_list > a").removeClass("on");

        // Active
        $(this).addClass('on');

        // Sub menu
/*        $(".txtbook_list").removeClass("on");
        $(".txtbook_cont").removeClass("on");
        $(this).parent().find(".txtbook_list, .txtbook_list > li > a:first").addClass("on");*/
        // Sub menu
        $(".txtbook_list").removeClass("on");
        $(".txtbook_list > li > a").removeClass("on");
        $(".txtbook_cont").removeClass("on");
        $(".txtbook_cont").animate({ marginLeft : "-150px" }, 0).animate({ marginLeft : "0px" }, 500);
        $(this).parent().find(".txtbook_list, .txtbook_list > li > a:first").addClass("on");
        

        // Default
        $(".txtbook_list > li > a:first").parent().find(".txtbook_cont").addClass("on");
        $(".sub_menu_list ul, .bottom_layer").hide();

        e.preventDefault();
    });

    // 셀렉트박스 세팅
    $("[name=books_group]").each(function() {
        var $this = $(this);
        var title_books = [];
        var cover_books = [];
        var items = [];

        $this.closest(".txtbook_cont").find(".booktitle_box ul > li").each(function(i, li) {
            title_books.push(li);
        });
        $this.closest(".txtbook_cont").find(".bookcover_box ul > li").each(function(i, li) {
            cover_books.push(li);
        });

        $(this).closest(".txtbook_cont").find(".txtList > li").each(function(i, item) {
            var revision = $(this).find("a").data("revision");
            if (revision) {
                items.push(revision);
            }
        });

        items = groupby(items);

        $.each(items, function(i, revision) {
            $this.append("<option value='"+ revision +"'>"+ revision +"</option>");
        });

        // 셀렉트박스 이벤트
        $(this).on("change", function(e) {
            var search_revision = $(this).val();
            var $title_books = $this.closest(".txtbook_cont").find(".booktitle_box ul");
            var $cover_books = $this.closest(".txtbook_cont").find(".bookcover_box ul");

            $title_books.find("li").remove();
            $cover_books.find("li").remove();

            // 도서명 목록
            $.each(title_books, function(i, book) {
                if (!search_revision == "") {
                    var revision = $(book).find("a").data("revision");
                    if (search_revision == revision) {
                        $title_books.append($(book));
                    }
                } else {
                    $title_books.append($(book));
                }
            });

            // 표지 목록
            $.each(cover_books, function(i, book) {
                if (!search_revision == "") {
                    var revision = $(book).find("a").data("revision");
                    if (search_revision == revision) {
                        $cover_books.append($(book));
                    }
                } else {
                    $cover_books.append($(book));
                }
            });

            // 도서명 클릭 이벤트
            $title_books.find("li > a").on("click", bookTitleClickEvent);

            // 첫번째 도서 상세 노출
            $title_books.find("li > a:first").trigger("click");

        });
    });
    
    //퀵검색 레이어 오픈
    $(".search").click(function() {

        try {
            $('.search_list').slick('unslick');
        } catch(e) {}

        if ($(".header_wrap").hasClass('off')) {
            $(".bottom_layer").css('left','110px');
        } else {
            $(".bottom_layer").css('left','210px');
        };
        $(".shade").show();
        $(".search_layer").fadeIn();
        $(".grade_list>a, .sub_menu_list>a, .txtbook_list, .txtbook_cont").removeClass('on');
        $(".txtbook, .sub_menu_list ul, .mypage_layer, .login_layer").hide();

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

    // 마우스 아이콘
    $(".mouse_scroll").animate({ bottom : "-44px" }, 0).animate({ bottom : "30px" }, 500);

    // 꽃가루 에니메이션
    bubbleAnimation($(".slide_04").find(".bubble"), 150, 750);
    

    // 도서명 보기 스크롤
    $(".booktitle_box .scrollBox ul").slimScroll({
    	/*
    	180406 수정 전
        height: 480,
        alwaysVisible:true,
        color:'#ffffff',
        allowPageScroll: false,
        railColor:'#740f00',
        distance: '0px',
        wheelStep: 10
        */
    	// 180406 수정 후
    	setWidth: "109%"
    });

    // 표지로 보기 스크롤
    $(".bookcover_box .scrollBox ul").slimScroll({
    	/*
    	180406 수정 전
        height: 770, //2018.01.18
        alwaysVisible:true,
        color:'#ffffff',
        allowPageScroll: false,
        railColor:'#740f00',
        distance: '0px',
        wheelStep: 10
        */
    	setWidth: "109%"
    });

    $(".mybook_list").slimScroll({
        height:'400px',
        alwaysVisible:true,
        color:'#222222',
        allowPageScroll: false,
        distance: '10px',
        wheelStep: 10
    });

    // End 수정 사항


  //페이지 로딩시 GNB close
/*  setTimeout(function(){
    $(".header_wrap, .logo, .bottom_menu, .bottom_wrap> ul li").stop().addClass("off");
    $(".bottom_layer").animate({left:'110px'},200);
    $(".notice_rolling").animate({left:'150px'},200);
    $(".visual_wrap").animate({padding:'0 0 0 110px'},200);
    $(".gnb_wrap, .middle_wrap, .shade, .sub_menu_list ul").hide();
    $(".grade_list>a, .sub_menu_list>a, .txtbook_list, .txtbook_cont").removeClass('on');
    $(".gnb_off").fadeIn();
  },2000);*/

  //GNB open
  $(".gnb_off").click(function() {
    $(".header_wrap, .logo, .bottom_menu").stop().removeClass("off");
    $(".bottom_layer").animate({left:'210px'},500);
    $(".notice_rolling").animate({left:'250px'},200);
    $(".gnb_off").fadeOut();
    $(".gnb_wrap, .middle_wrap").stop().delay(400).fadeIn();
    if ($("html").scrollLeft() == 0){
      $(".visual_wrap").animate({padding:'0 0 0 210px'},200);
    };
  });

  //GNB close
/*  $(".container").click(function(){
    $(".header_wrap, .logo, .bottom_menu, .bottom_wrap> ul li").stop().addClass("off");
    $(".visual_wrap").animate({padding:'0 0 0 110px'},200);
    $(".notice_rolling").animate({left:'150px'},200);
    $(".gnb_wrap").hide();
    $(".gnb_off").fadeIn();
    $(".middle_wrap").stop().hide();
  });*/

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

/**/

  //추천검색어 클릭 시 검색어 등록
  $(".search_list li a").click(function(){
    var searchWord = $(this).text();
    $("#search").val(searchWord);
  });

  //교과서 메뉴, 서브 메뉴, 레이어 닫기
  $(".close").click(function(){
    $(".shade, .txtbook, .sub_menu_list ul, .bottom_layer").fadeOut();
    $(".grade_list>a, .sub_menu_list>a, .txtbook_list, .txtbook_cont").removeClass('on');
/*    setTimeout(function(){
      $(".header_wrap, .logo, .bottom_menu, .bottom_wrap> ul li").stop().addClass("off");
      $(".visual_wrap").animate({padding:'0 0 0 110px'},200);
      $(".notice_rolling").animate({left:'150px'},200);
      $(".gnb_wrap, .middle_wrap").hide();
      $(".gnb_off").fadeIn();
    },1000);*/
  });

  // 사이트맵 2018.01.18
  $('.toggle_btn').click(function(){
    $(".s-menu").hide();
    $('.pop_menu').show();
    $(this).next('div').addClass("on");
    $('.shade_all').show();
});
$(".m_close").click(function(){
    $('.pop_menu').hide();
    $('.shade_all').hide();
});
$(".class > li > a").click("mouseover focus", function(){
    $(".class_con").hide();
    $(".class > li > a").removeClass('on');
    $(this).next('div').show();
    $(this).addClass('on');
    
});


$(".pop_menu_tab3 > li > a").click("mouseover focus", function(){
    $(".ptab_con").hide();
    $(".pop_menu_tab3 > li > a").removeClass('on');
    $(this).next('div').show();
    $(this).addClass('on');
    
});

});
