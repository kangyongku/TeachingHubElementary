$( document ).ready(function() {

 
        $( window ).scroll( function() {
            if ($(document).scrollTop() > 0) {
                //$('.contents').addClass('scroll');
                $('.snsBox').addClass('scroll');
            } else {
                //$('.contents').removeClass('scroll');
                $('.snsBox').removeClass('scroll');
            }


            if ($(document).scrollTop() > 220) {
                $('.subTitle').addClass('scroll');
                $('.contentsBox').addClass('scroll');
            }             
            else {
                $('.subTitle').removeClass('scroll');
                $('.contentsBox').removeClass('scroll');
            }

            var scrollValue = $(document).scrollTop();
            $('.console').html(scrollValue)



        });


        function LineW() {

            var titW = $('.contentsBox.sub3 .contentHeader .title .topText').width();
            var hrW = $(window).width() - 200 - titW;
            $('.contentsBox.sub3 .contentHeader .title .lineBox').css('width', hrW);

        }

        //LineW();
        
        

        $( window ).resize( function() {
           // LineW();
        });
});