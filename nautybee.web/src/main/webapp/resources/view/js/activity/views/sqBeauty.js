var gViewModel = {};
var picScroll;
$(function(){
    $(document).on("bridgeReady",function(){
        init();
});
    NativeCaller.connect();
});
function init(){
    //调整页面显示
    windowResized();
    //初始化iscroll
    loadScroll();
    picScroll.scrollTo(-$('.todayPic').offset().left+170/720*getScreenWidth(),0);
    $('body').css("visibility","visible");
    if(seeHB != "" && seeHB != null){
        $('#rewardBtn').css('display','block');
    }else{
        $('#rewardBtn').css('display','none');
    }
//    document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
//    document.addEventListener('DOMContentLoaded', function () { setTimeout(loadScroll, 200); }, false);
    $('.todayPic').bind( "click" , function(){
        plusOneEye($(this).attr("activityKey"));
        window.location.href = window.location.origin + '/fauna/groupActivity/enterActivity?activityKey='+$(this).attr("activityKey")+'&cityStation='+cityStation+'&type=groupVer13';
    });
    $('#rewardBtn').bind( "click" , function(){
        if(seeHB == 'cash'){
            $('#weChatPic').css('display','block');
        }else if(seeHB == 'coupons_5'){
            $('#coupon5Pic').css('display','block');
        }else{
            $('#coupon10Pic').css('display','block');
        }
        $('#bigCover').css({
            'position':'absolute',
            'width':getScreenWidth()+'px',
            'height':$('#wrapper').height()+'px',
            'top':0,
            'left':0,
            'display':'block'
        });
    });
    $('#weChatPic').bind( "click" , function(){
        $('#weChatPic').css('display','none');
        $('#bigCover').css('display','none');
    });
    $('.couponPic').bind( "click" , function(){
        $('.couponPic').css('display','none');
        $('#bigCover').css('display','none');
    });
    $('#ruleBtn').bind( "click" , function(){
        $('#rulePic').css('display','block');
        $('#bigCover').css({
            'position':'absolute',
            'width':getScreenWidth()+'px',
            'height':$('#wrapper').height()+'px',
            'top':0,
            'left':0,
            'display':'block'
        });
    });
    $('#rulePic').bind( "click" , function(){
        $('#rulePic').css('display','none');
        $('#bigCover').css('display','none');
    });
    $('#money_ico').bind( "click" , function(e){
        e.stopPropagation();
        buyMe();
    });
}
function getScreenWidth(){
    return document.body.clientWidth;
}
function windowResized(){
    $('#picScroll').css({
        'position':'absolute',
        'bottom':160/720*getScreenWidth()+'px',
        'right':0,
        'width':getScreenWidth()+'px'
    });
    $('#picWrapper').css({
        'width':(actLength-1)*18/720*getScreenWidth() + 380/720*getScreenWidth()*actLength + 170/720*getScreenWidth() + 'px',
        'padding-left':170/720*getScreenWidth() + 'px'
//        'padding-right':170/720*getScreenWidth() + 'px'
    });
    $('.todayPic').css({
        'width':380/720*getScreenWidth()+'px',
        'padding-right':18/720*getScreenWidth() + 'px'
    });
    $('.singlePic').css({
        'width':356/720*getScreenWidth()+'px',
        'margin-top':16/720*getScreenWidth() + 'px',
        'padding-right':18/720*getScreenWidth() + 'px'
    });
    $('.tips').css({
        'position':'absolute',
        'bottom':30/720*getScreenWidth()+'px',
        'width':356/720*getScreenWidth()+'px',
        'left':0
    });
    $('.shadow').css({
        'width':356/720*getScreenWidth()+'px',
        'border-radius':5/414*getScreenWidth()+'px'
    });
    $('#eye_ico').css({
        'width':35/720*getScreenWidth()+'px',
        'position':'absolute',
        'bottom':22/720*getScreenWidth()+'px',
        'left':15/720*getScreenWidth()+'px',
        'line-height':1
    });
    $('#likeNum').css({
        'position':'absolute',
        'bottom':20/720*getScreenWidth()+'px',
        'left':65/720*getScreenWidth()+'px'
    });
    $('#money_ico').css({
        'width':100/720*getScreenWidth()+'px',
        'height':36/720*getScreenWidth()+'px',
        'position':'absolute',
        'bottom':21/720*getScreenWidth()+'px',
        'right':38/720*getScreenWidth()+'px'
    });
    $('#weChatPic').css({
        'width':640/720*getScreenWidth()+'px',
        'position':'absolute',
        'top':260/720*getScreenWidth()+'px',
        'right':40/720*getScreenWidth()+'px'
    });
    $('.couponPic').css({
        'width':600/720*getScreenWidth()+'px',
        'position':'absolute',
        'top':280/720*getScreenWidth()+'px',
        'right':60/720*getScreenWidth()+'px'
    });
    $('#rulePic').css({
        'width':500/720*getScreenWidth()+'px',
        'position':'absolute',
        'top':300/720*getScreenWidth()+'px',
        'right':110/720*getScreenWidth()+'px'
    });
    $('#rewardBtn').css({
        'width':120/720*getScreenWidth()+'px',
        'height':38/720*getScreenWidth()+'px',
        'position':'absolute',
        'top':40/720*getScreenWidth()+'px',
        'right':172/720*getScreenWidth()+'px'
    });
    $('#ruleBtn').css({
        'width':120/720*getScreenWidth()+'px',
        'height':38/720*getScreenWidth()+'px',
        'position':'absolute',
        'top':40/720*getScreenWidth()+'px',
        'right':30/720*getScreenWidth()+'px'
    });
    if(getScreenWidth() < 325){
        $('#picWrapper').css({
            'font-size':12+'px'
        });
    }else if(getScreenWidth() < 380){
        $('#picWrapper').css({
            'font-size':14+'px'
        });
    }else{
        $('#picWrapper').css({
            'font-size':16+'px'
        });
    }
    $($('.pics')[actLength-1]).removeClass('floatLeft');
    $($('.pics')[actLength-1]).addClass('inlineBlock');
}
function loadScroll () {
    picScroll = new IScroll('#picScroll', {
        scrollX: true,
        scrollY: false,
        momentum: true,
        snap: false,
        scrollbars: false,
        fadeScrollbars:true,
        shrinkScrollbars: 'scale',
        bounce: true,
        probeType:1,
        click:true
    });
    picScroll.on('scrollEnd',function(e){
        var val = this.x;
    });
    refreshScroll();
}
function refreshScroll(){
    setTimeout(function(){
        picScroll.refresh();
    },500);
}
function plusOneEye(activityKey){
    $.ajax({
        type: 'get',
        url: window.location.origin + '/fauna/sqBeauty/plusViewTimes?activityKey=' + activityKey,
        async: true,
        dataType: 'json',
        success: function (result) {
            var res = result.result;
            if (!res.success) {
                console.log(res.errMsg);
            }else{
                console.log(res.data);
            }
        }
    });
}
function buyMe(){
    var param = {};
    param.activityId = singleGoodsActId;
    param.activityName = singleGoodsActName;
    param.totalGoodsNumber = 1;
    param.goodsList = [];
    var goodsView = {};
    goodsView.goodsId = buyGoodsId;
    goodsView.goodsNumber = 1;
    param.goodsList.push(goodsView);
    var activityParamStr = JSON.stringify(param);
    NativeCaller.balanceActivityOrder(activityParamStr);
}
