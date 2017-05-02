var gViewModel = {};
gViewModel.$main = $('#main');
gViewModel.$wrapper = $('#wrapper');
$(function(){
    init();
});
function init(){
    windowResized();
    $('body').css("visibility","visible")
    initButtonStatus();
    initEventHandlers();
    //初始化iscroll
    loadScroll();
}
function getScreenWidth(){
    return document.body.clientWidth;
}
function windowResized(){
    $('.storeBanner').css({
        'width':getScreenWidth()+'px',
        'height':0.4*getScreenWidth()+'px',
        'margin-top':20/640*getScreenWidth()+'px',
        'line-height':0.4*getScreenWidth()+'px',
        'text-align':'center',
        'font-size':'large',
        'background-color':'lightgray'
    })
}
function loadScroll () {
    gViewModel.gPageScroll = new IScroll('#wrapper', {
        scrollX: false,
        scrollY: true,
        momentum: true,
        snap: false,
        scrollbars: true,
        fadeScrollbars:true,
        shrinkScrollbars: 'scale',
        bounce: true,
        probeType:1,
        click:true
    });
    gViewModel.gPageScroll.on('scrollStart',function(e){
        $(".active").removeClass("active");
    });
    gViewModel.gPageScroll.on('scroll',function(e){
        gViewModel.$wrapper.trigger("scroll");
    });
    gViewModel.gPageScroll.on('scrollEnd',function(e){
        $(".active").removeClass("active");
        gViewModel.$wrapper.trigger("scroll");
    });
    refreshScroll();
}
function refreshScroll(){
    setTimeout(function(){
        gViewModel.gPageScroll.refresh();
    },200);
}
function initButtonStatus(){
    gViewModel.$wrapper.clickStatus({
        targetSelector:".img_wrapper",
        touchEventEnable:gIsTouchDevice
    });

}
function initEventHandlers(){

}
