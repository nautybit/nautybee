var gViewModel = {};
gViewModel.$main = $('#main');
gViewModel.$wrapper = $('#wrapper');
var gPageScroll;
$(function(){
    if(clientType == "app"){
        $(document).on("bridgeReady",function(){
            init();
        });
        NativeCaller.connect();
    }else{
        init();
    }
});
function init(){
    //调整页面显示
    windowResized();
    $('body').css("visibility","visible");
    initButtonStatus();
    initEventHandlers();
    //初始化iscroll
    loadScroll();
    document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
}
function loadScroll () {
    gPageScroll = new IScroll('#wrapper', {
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
    gPageScroll.on('scrollStart',function(e){
        $('.eachGoods').addClass('disabled');
        $(".active").removeClass("active");
    });
    gPageScroll.on('scroll',function(e){
        gViewModel.$wrapper.trigger("scroll");
    });
    gPageScroll.on('scrollEnd',function(e){
        $(".active").removeClass("active");
        gViewModel.$wrapper.trigger("scroll");
        refreshScroll();
        setTimeout(function(){
            $('.eachGoods').removeClass('disabled');
        },200);
    });
    refreshScroll();
}
function refreshScroll(){
    setTimeout(function(){
        gPageScroll.refresh();
        $("#wrapper .img-goods").lazyload({ threshold : 1000 });
    },200);
}

function getScreenWidth(){
    return document.body.clientWidth;
}
function getScreenHeight(){
    return document.body.clientHeight;
}
function windowResized(){
    $('#toBrowser').css({
        'position':'absolute',
        'width':0.7*getScreenWidth()+'px',
        'top':0,
        'left':0.3*getScreenWidth()+'px'
    });
    $('#ruleLow').css({
        'width':123/720*getScreenWidth()+'px',
        'height':54/320*getScreenWidth()+'px',
        'position':'absolute',
        'top':546/720*getScreenWidth()+'px',
        'right':48/720*getScreenWidth()+'px'
    });
    $('#ruleHigh').css({
        'width':123/720*getScreenWidth()+'px',
        'height':54/320*getScreenWidth()+'px',
        'position':'absolute',
        'top':0,
        'right':48/720*getScreenWidth()+'px'
    });
    $('#toMainPage').css({
        'width':686/720*getScreenWidth()+'px',
        'height':256/720*getScreenWidth()+'px',
        'position':'absolute',
        'top':737/720*getScreenWidth()+'px',
        'right':17/720*getScreenWidth()+'px'
    });
    $('.actWrapper').css({
        'margin-top':22/720*getScreenWidth()+'px',
        'margin-bottom':22/720*getScreenWidth()+'px'
    });
    $('.actBanner').css({
        'margin-bottom':22/720*getScreenWidth()+'px'
    });
    $('.goodsBlock').css({
        'margin-bottom':2/720*getScreenWidth()+'px',
        'margin-left':16/720*getScreenWidth()+'px',
        'margin-right':16/720*getScreenWidth()+'px'
    });
    $('.leftGoods').css({
        'padding-right':8/720*getScreenWidth()+'px',
        'padding-bottom':20/720*getScreenWidth()+'px'
    });
    $('.rightGoods').css({
        'padding-left':8/720*getScreenWidth()+'px',
        'padding-bottom':20/720*getScreenWidth()+'px'
    });
    $('.rulePic').css({
        'width':getScreenWidth()+'px',
        'position':'absolute',
        'top':0,
        'right':0
    });
    $('#bigCover').css({
        'position':'absolute',
        'width':getScreenWidth()+'px',
        'height':$('#wrapper').height()+'px',
        'top':0,
        'left':0
    });
    if(clientType == 'browser'){
//        console.log($('#ddAppBar').height());
        $('#act3').css('margin-bottom',$('#ddAppBar').height()+'px');
    }

}
function initButtonStatus(){
    gViewModel.$main.clickStatus({
        targetSelector:"#openApp",
        touchEventEnable:gIsTouchDevice
    });
    gViewModel.$main.clickStatus({
        targetSelector:".toBrowser",
        touchEventEnable:gIsTouchDevice
    });
    gViewModel.$main.clickStatus({
        targetSelector:"#closeBar",
        touchEventEnable:gIsTouchDevice
    });

    gViewModel.$main.clickStatus({
        targetSelector:"#bigCover",
        touchEventEnable:gIsTouchDevice
    });

    gViewModel.$main.clickStatus({
        targetSelector:"#ruleLow",
        touchEventEnable:gIsTouchDevice
    });
    gViewModel.$main.clickStatus({
        targetSelector:"#ruleHigh",
        touchEventEnable:gIsTouchDevice
    });
    gViewModel.$main.clickStatus({
        targetSelector:".rulePic",
        touchEventEnable:gIsTouchDevice
    });
    gViewModel.$main.clickStatus({
        targetSelector:"#toMainPage",
        touchEventEnable:gIsTouchDevice
    });
    gViewModel.$main.clickStatus({
        targetSelector:".eachGoods",
        touchEventEnable:gIsTouchDevice
    });
}

function initEventHandlers(){
    $('#openApp').on("clickstatus.up",function(e){
        if(gIsWechat()){
            $('.toBrowser').css('display','block');
            return;
        }
        openApp();
    });
    $('.toBrowser').on("clickstatus.up",function(e){
        $('.toBrowser').css('display','none');
    });
    $('#closeBar').on("clickstatus.up",function(e){
        $('#ddAppBar').css('display','none');
    });
    $('#bigCover').on("clickstatus.up",function(e){
        if($('#winTip').css('display')=='block'){
            $('#winTip').css('display','none');
            $('#bigCover').css('display','none');
        }
        if($('#rulePic').css('display')=='block'){
            $('#rulePic').css('display','none');
            $('#bigCover').css('display','none');
        }
    });
    $('#ruleLow').on("clickstatus.up",function(e){
        $('#bigCover').css('display','block');
        $('#rulePicLow').css('display','block');
    });
    $('#ruleHigh').on("clickstatus.up",function(e){
        $('#bigCover').css('display','block');
        $('#rulePicHigh').css('display','block');
    });
    $('.rulePic').on("clickstatus.up",function(e){
        $('.rulePic').css('display','none');
        $('#bigCover').css('display','none');
    });
    $('#toMainPage').on("clickstatus.up",function(e){
        if(clientType != 'app'){
            $('#openApp').trigger('clickstatus.up');
        }else{
            NativeCaller.jumpToMainView();
        }
    });
    $('.eachGoods').on("clickstatus.up",function(e){
        if(clientType != 'app'){
            $('#openApp').trigger('clickstatus.up');
        }else{
            window.location = "fauna-b://store?storeId="+$(this).attr('storeId');
        }
    });
}
function openApp(){
    NativeCaller.showToast("open!");
    var url = window.location.origin+'/fauna/double11/stores';
    window.location = 'fauna-b://webview?url='+url;

    setTimeout(function() {
        window.location = gAppDownloadPage.b;
    }, 5000);
}