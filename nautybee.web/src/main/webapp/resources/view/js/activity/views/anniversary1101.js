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
    $('body').css("visibility","visible");
    $('#toActBtn').bind( "click" , function(){
        window.location.href = window.location.origin + '/fauna/groupActivity/enterActivity?activityKey='+$(this).attr("activityKey")+'&cityStation='+cityStation+'&type=groupVer13';
    });
//    $('#rewardBtn').bind( "click" , function(){
//        if(seeHB == 'cash'){
//            $('#weChatPic').css('display','block');
//        }else if(seeHB == 'coupons_5'){
//            $('#coupon5Pic').css('display','block');
//        }else if(seeHB == 'coupons_10'){
//            $('#coupon10Pic').css('display','block');
//        }else{
//            $('#weChatTip').css('display','block');
//        }
//        $('#bigCover').css({
//            'position':'absolute',
//            'width':getScreenWidth()+'px',
//            'height':$('#wrapper').height()+'px',
//            'top':0,
//            'left':0,
//            'display':'block'
//        });
//    });
//    $('#weChatPic').bind( "click" , function(){
//        $('#weChatPic').css('display','none');
//        $('#bigCover').css('display','none');
//    });
//    $('#weChatTip').bind( "click" , function(){
//        $('#weChatTip').css('display','none');
//        $('#bigCover').css('display','none');
//    });
//    $('#ruleBtn').bind( "click" , function(){
//        $('#rulePic').css('display','block');
//        $('#bigCover').css({
//            'position':'absolute',
//            'width':getScreenWidth()+'px',
//            'height':$('#wrapper').height()+'px',
//            'top':0,
//            'left':0,
//            'display':'block'
//        });
//    });
//    $('#rulePic').bind( "click" , function(){
//        $('#rulePic').css('display','none');
//        $('#bigCover').css('display','none');
//    });
//    $('.couponPic').bind( "click" , function(){
//        $('.couponPic').css('display','none');
//        $('#bigCover').css('display','none');
//    });

//    $('#bigCover').bind( "click" , function(){
//        $('#bigCover').css('display','none');
//        $('#weChatPic').css('display','none');
//        $('#weChatTip').css('display','none');
//        $('#rulePic').css('display','none');
//        $('.couponPic').css('display','none');
//    });

    $('.goodsArea').on( "click" , function(){
        var info = $(this).attr("storeId");
        if(info.indexOf('S',info.length-1)>-1){
            var storeId = info.substring(0,info.indexOf('_'));
            window.location.href = 'fauna-b://store?storeId='+storeId;
        };
    });
//    $('#dsBtn').bind( "click" , function(e){
//        e.stopPropagation();
//        buyMe();
//    });
}
function getScreenWidth(){
    return document.body.clientWidth;
}
function getScreenHeight(){
    return document.body.clientHeight;
}
function windowResized(){
    $('#wrapper').css({
        'width':getScreenWidth()+'px',
        'height':getScreenHeight+'px',
        'position':'absolute',
        'top':0,
        'right':0
    });
    $('.shadow').css({
        'width':356/720*getScreenWidth()+'px',
        'border-radius':5/414*getScreenWidth()+'px'
    });
//    $('#weChatPic').css({
//        'width':640/720*getScreenWidth()+'px',
//        'position':'absolute',
//        'top':260/720*getScreenWidth()+'px',
//        'right':40/720*getScreenWidth()+'px'
//    });
//    $('#weChatTip').css({
//        'width':640/720*getScreenWidth()+'px',
//        'position':'absolute',
//        'top':260/720*getScreenWidth()+'px',
//        'right':40/720*getScreenWidth()+'px'
//    });
//    $('#rulePic').css({
//        'width':680/720*getScreenWidth()+'px',
//        'position':'absolute',
//        'top':260/720*getScreenWidth()+'px',
//        'right':20/720*getScreenWidth()+'px'
//    });
//    $('#rewardBtn').css({
//        'width':120/720*getScreenWidth()+'px',
//        'height':38/720*getScreenWidth()+'px',
//        'position':'absolute',
//        'top':182/720*getScreenWidth()+'px',
//        'right':250/720*getScreenWidth()+'px'
//    });
//    $('#ruleBtn').css({
//        'width':120/720*getScreenWidth()+'px',
//        'height':38/720*getScreenWidth()+'px',
//        'position':'absolute',
//        'top':182/720*getScreenWidth()+'px',
//        'right':110/720*getScreenWidth()+'px'
//    });
    $('#toActBtn').css({
        'width':380/720*getScreenWidth()+'px',
        'height':107/720*getScreenWidth()+'px',
        'position':'absolute',
        'top':74/720*getScreenWidth()+'px',
        'right':15/720*getScreenWidth()+'px'
    });
    $('#goodsWrapper').css({
        'width':699/720*getScreenWidth()+'px',
        'height':984/720*getScreenWidth()+'px',
        'position':'absolute',
        'top':654/720*getScreenWidth()+'px',
        'left':12/720*getScreenWidth()+'px'
    });
//    $('#dsBtn').css({
//        'width':190/720*getScreenWidth()+'px',
//        'height':127/720*getScreenWidth()+'px',
//        'position':'fixed',
//        'bottom':100/720*getScreenWidth()+'px',
//        'right':50/720*getScreenWidth()+'px'
//    });
    for(var i=0;i<storeIdList.length;i++){
        var storeId = storeIdList[i];
        $('.goodsArea[storeId='+storeId+']').css({
            'width':233/720*getScreenWidth()+'px',
            'height':246/720*getScreenWidth()+'px',
            'position':'absolute',
            'top':246*(parseInt(i/3))/720*getScreenWidth()+'px',
            'left':233*(i%3)/720*getScreenWidth()+'px'
        });
        if(storeId.indexOf('H',storeId.length-1)>-1){
            $('.goodsArea[storeId='+storeId+']').css('display','none');
        };
    }
//    $('.couponPic').css({
//        'width':600/720*getScreenWidth()+'px',
//        'position':'absolute',
//        'top':280/720*getScreenWidth()+'px',
//        'right':60/720*getScreenWidth()+'px'
//    });
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