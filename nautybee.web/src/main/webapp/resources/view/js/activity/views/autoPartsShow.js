var gViewModel = {};
gViewModel.$main = $('#main');
gViewModel.$wrapper = $('#wrapper');
var gPageScroll;
$(function(){
    init();
});
function init(){
    //横向游幕
    ScrollImgLeft();
    //调整页面显示
    windowResized();
    $('body').css("visibility","visible");
    initButtonStatus();
    initEventHandlers();
    _initSpinner();
    //初始化iscroll
    loadScroll();
    document.addEventListener('touchmove', function (e) {
        e.preventDefault();
    }, false);
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
        $(".active").removeClass("active");
        $('.inputVal').blur();
    });
    gPageScroll.on('scroll',function(e){
        gViewModel.$wrapper.trigger("scroll");
    });
    gPageScroll.on('scrollEnd',function(e){
        $(".active").removeClass("active");
        gViewModel.$wrapper.trigger("scroll");
    });
    refreshScroll();
}
function refreshScroll(){
    setTimeout(function(){
        gPageScroll.refresh();
    },200);
}
function getScreenWidth(){
    return document.body.clientWidth;
}
function windowResized(){
    $('#bigCover').css({
        'position':'absolute',
        'width':getScreenWidth()+'px',
        'height':$('#wrapper').height()+'px',
        'top':0,
        'left':0
    });
    $('input').css({
        'padding':10/720*getScreenWidth()+'px',
        'border-radius':8/720*getScreenWidth()+'px',
        'height':55/720*getScreenWidth()+'px',
        'line-height':55/720*getScreenWidth()+'px'
    });
    $('.inputWrapper').css({
        'position':'absolute',
        'width':203/320*getScreenWidth()+'px',
        'height':25/320*getScreenWidth()+'px',
        'left':95/320*getScreenWidth()+'px'
    });
    $('#storeName').css({
        'top':405/320*getScreenWidth()+'px'
    });
    $('#mobilePhone').css({
        'top':450/320*getScreenWidth()+'px'
    });
    $('#contactName').css({
        'top':490/320*getScreenWidth()+'px'
    });
    $('#submitBtn').css({
        'position':'absolute',
        'left':103/320*getScreenWidth()+'px',
        'top':536/320*getScreenWidth()+'px',
        'width':112/320*getScreenWidth()+'px',
        'height':35/320*getScreenWidth()+'px'
    });
    $('#tip').css({
        'position':'absolute',
        'left':0,
        'top':300/720*getScreenWidth()+'px',
        'width':getScreenWidth()+'px'
    });
    $('#smallPrizeInfo').css({
        'position':'absolute',
        'top':316/320*getScreenWidth()+'px',
        'left':0,
        'height':66/720*getScreenWidth()+'px',
        'line-height':66/720*getScreenWidth()+'px',
        'width':getScreenWidth()+'px'
    });
}

function initButtonStatus(){
    gViewModel.$main.clickStatus({
        targetSelector:"#submitBtn",
        touchEventEnable:gIsTouchDevice
    });
    gViewModel.$main.clickStatus({
        targetSelector:"#bigCover",
        touchEventEnable:gIsTouchDevice
    });
    gViewModel.$main.clickStatus({
        targetSelector:"#tip",
        touchEventEnable:gIsTouchDevice
    });
}
function initEventHandlers(){
    $('#submitBtn').on('clickstatus.up',function(){
        var param = {};
        param.applicant = $('#storeName').children().val();
        param.contactMobile = $('#mobilePhone').children().val();
        param.contactName = $('#contactName').children().val();

        if(param.applicant == '' || param.applicant == null || param.contactMobile == '' || param.contactMobile == null
            || param.contactName == '' || param.contactName == null){
            alert("请输入必填信息！");
            return;
        }
        if(!isValidMobile(param.contactMobile)){
            alert("无效的手机号！");
            $('#mobilePhone').children().val('');
            $('.inputVal').blur();
            return;
        }
        baseShowIndicator(true);
        $.ajax({
            url: window.location.origin + '/fauna/enrollment/autoPartsShowSubmitInfo',
            data:JSON.stringify(param),
            dataType: "json",
            type: 'post',
            async: true,
            contentType : 'application/json',
            success: function (result) {
                var res = result;
                if (!res.success) {
                    alert(res.errorMsg);
                }else{
                    $('#bigCover').css('display','block');
                    $('#tip').css('display','block');
                    $('.inputVal').val('');
                }
                baseShowIndicator(false);
            }
        });
    });
    $('#bigCover').on("clickstatus.up",function(e){
        if($('#tip').css('display')=='block'){
            $('#tip').css('display','none');
            $('#bigCover').css('display','none');
        }
    });
    $('#tip').on("clickstatus.up",function(e){
        $('#tip').css('display','none');
        $('#bigCover').css('display','none');
    });
}
function baseShowIndicator( isShow , message ){
    var $indicator=  $('#indicator');
    var msg= "";
    if( message ){
        msg = message;
    }
    $indicator.find('.message').text(msg);
    if ( isShow ){
        $indicator.removeClass('hide');
    }else  {
        $indicator.addClass('hide');
    }
}
function _initSpinner()
{
    var opts = {
        lines: 10, // The number of lines to draw
        length: 10, // The length of each line
        width: 4, // The line thickness
        radius: 10, // The radius of the inner circle
        corners: 1, // Corner roundness (0..1)
        rotate: 0, // The rotation offset
        direction: 1, // 1: clockwise, -1: counterclockwise
        color: '#FFF', // #rgb or #rrggbb or array of colors
        speed: 1, // Rounds per second
        trail: 60, // Afterglow percentage
        shadow: false, // Whether to render a shadow
        hwaccel: false, // Whether to use hardware acceleration
        className: 'spinner', // The CSS class to assign to the spinner
        zIndex: 2e9, // The z-index (defaults to 2000000000)
        top: '50%', // Top position relative to parent
        left: '50%' // Left position relative to parent
    };
    var $indicator=  $('#indicator');
    var target = $('#indicator .spinner').get(0);
    new Spinner(opts).spin(target);
}
var MyMar = null;
function ScrollImgLeft(speed){
    if(speed==null){
        speed = 25;
    }
    var scroll_begin = document.getElementById("scroll_begin");
    var scroll_end = document.getElementById("scroll_end");
    var scroll_div = document.getElementById("smallPrizeInfo");
    scroll_end.innerHTML=scroll_begin.innerHTML;
    function Marquee(){
        if(scroll_end.offsetWidth-scroll_div.scrollLeft<=0)
            scroll_div.scrollLeft-=scroll_begin.offsetWidth;
        else
            scroll_div.scrollLeft++;
    }
    MyMar=setInterval(Marquee,speed);
}