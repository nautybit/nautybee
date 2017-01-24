var gViewModel = {};
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
    //判断登录
    if(clientType == "app"){
        if(userId==null){
            NativeCaller.login();
        }
    }
    //调整页面显示
    windowResized();
    //横向游幕
//    ScrollImgLeft();
    //隐藏4个以上的商品
    hideExtraGoods();
    //显示页面
    $('body').css("visibility","visible");
    //定义全局变量
    gViewModel.$main = $('#main');
    gViewModel.$wrapper = $('#wrapper');
    gViewModel.$scrollMenuBar = $('#scrollMenuBar');
    gViewModel.$fixedMenuBar = $('#fixedMenuBar');
    gViewModel.$groupBtn = $('.groupBtn');
    gViewModel.menuWidth = $('.menuWrapper').width();

    initButtonStatus();
    initEventHandlers();

    //初始化iscroll
    loadScroll();
    document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
    document.addEventListener('DOMContentLoaded', function () { setTimeout(loadScroll, 200); }, false);
}
function valueDidChangedHanlder(){
    var param = {};
    param.activityId = $('.activityInfo').attr("activityId");
    param.totalGoodsNumber = 0;
    param.goodsList = [];
    var $goodsInfos = $('#wrapper .goodsInfo');
    for(var i=0;i<$goodsInfos.length;i++){
        var $goodsInfoItem = $($goodsInfos[i]);
        if($goodsInfoItem.closest(".goodsRow").find('.numInputBorder').length > 0){
            var $inputer = $goodsInfoItem.closest(".goodsRow").find('.numInputBorder');
            var inputerValue = ( $inputer.val() - 0 );
            param.totalGoodsNumber = (param.totalGoodsNumber - 0 ) + inputerValue;
            var goodsView = {};
            goodsView.goodsId = $goodsInfoItem.attr("goodsId");
            goodsView.goodsNumber = inputerValue;
            goodsView.skuNum = $goodsInfoItem.attr("skuNum");
            goodsView.storeId = $goodsInfoItem.attr("storeId");
            goodsView.goodsPrice = parseFloat($goodsInfoItem.attr("goodsPrice")).toFixed(2);
            goodsView.basePrice = parseFloat($goodsInfoItem.attr("basePrice")).toFixed(2);
            goodsView.activityGoodsRule = $goodsInfoItem.attr("activityGoodsRule");
            param.goodsList.push(goodsView);
        }
    }
//    console.log(param);
    var obj = JSON.parse(dataHandlerService(JSON.stringify(param)));
    var isCanBuy = obj.isCanBuy;
    if(isCanBuy){
        $('#btnPurchase').css("background-color","#ffa311");
        $('#btnPurchase').attr("isCanBuy","Yes");
    }else{
        $('#btnPurchase').css("background-color","#999999");
        $('#btnPurchase').attr("isCanBuy","No");
    }
//    console.log(isCanBuy);
    var allOrderAmount = obj.tempOrderVO.allOrderAmount;
    $('#allOrderAmount').text(allOrderAmount);
    $('#totalGoodsNumber').text(" "+param.totalGoodsNumber+" ");
}
function mainMenuClick(key){
    gViewModel.$groupBtn.removeClass("menuSelected");
    $(".groupBtn[key='"+key+"']").addClass("menuSelected");
    //跳到相应位置
    var pos = $(".subAct[key='"+key+"']").offset().top - $("#banner").offset().top;
    gPageScroll.scrollTo(0,-pos+$('#scrollMenuBar').height());
    adjustMenuDisplay();
    changeActiveMenu();
}

function getScreenWidth(){
    return document.body.clientWidth;
}
function windowResized(){
    $('#scrollMenuBar').css({
        'height':64/640*getScreenWidth()+'px'
    });
    $('.menuWrapper').css({
        'width':160/640*5*getScreenWidth()+'px'
    });
    $('.menuWrapper').children().css({
        'width':160/640*getScreenWidth()+'px',
        'height':64/640*getScreenWidth()+'px',
        'line-height':64/640*getScreenWidth()+'px'
    });
    $('.left_normal').css({
        'width':20/640*getScreenWidth()+'px',
        'height':34/640*getScreenWidth()+'px',
        'position':'absolute',
        'top':15/640*getScreenWidth()+'px',
        'left':4/414*getScreenWidth()+'px',
        'line-height':1,
        'display':'none'
    });
    $('.right_normal').css({
        'width':20/640*getScreenWidth()+'px',
        'height':34/640*getScreenWidth()+'px',
        'position':'absolute',
        'top':15/640*getScreenWidth()+'px',
        'right':4/414*getScreenWidth()+'px',
        'line-height':1
    });
    $('.dayBanner').css({
        'margin-bottom':16/640*getScreenWidth()+'px'
    });
    $('.subAct').css({
//        'padding-bottom':24/640*getScreenWidth()+'px',
        'padding-top':30/640*getScreenWidth()+'px'
    });
    $('.shownGoods').css({
        'padding':8/640*getScreenWidth()+'px'
    });
    $('.goodsBlock').css({
        'padding':6/640*getScreenWidth()+'px',
        'margin-bottom':4/640*getScreenWidth()+'px'
    });
    var leftPartWidth = (getScreenWidth() - 40/640*getScreenWidth())*0.5;
    var leftPartHeight = leftPartWidth;
    $('.img_wrapper').css({
        'height':leftPartHeight+'px',
        'width':leftPartWidth+'px',
        'padding-left':15/640*getScreenWidth()+'px',
        'padding-right':15/640*getScreenWidth()+'px',
        'padding-top':15/640*getScreenWidth()+'px',
        'padding-bottom':6/640*getScreenWidth()+'px'
    });
    $('.goodsCover').css({
        'height':leftPartHeight+'px',
        'width':leftPartWidth+'px',
        'position':'absolute',
        'top':0,
        'left':0
    });
    $('.stepLimit').css({
        'width':142/640*getScreenWidth()+'px',
        'height':26/640*getScreenWidth()+'px',
        'position':'absolute',
        'bottom':0,
        'right':0,
        'line-height':26/640*getScreenWidth()+'px'
    });
    $('.belowPic').css({
        'padding-left':15/640*getScreenWidth()+'px',
        'padding-right':15/640*getScreenWidth()+'px',
        'padding-bottom':10/640*getScreenWidth()+'px'
    });
    $('.storeName').css({
        'padding-top':10/640*getScreenWidth()+'px',
        'padding-bottom':8/640*getScreenWidth()+'px'
    });
    $('.outStockCover').css({
        'width':'100%',
        'height':'100%',
        'position':'absolute',
        'top':0,
        'left':0
    });
    $('.outStockPic').css({
        'width':'40%',
        'height':'24.4%',
        'position':'absolute',
        'top':116/640*getScreenWidth()+'px',
        'left':55/640*getScreenWidth()+'px'
    });
    $('.line3').css({
        'height':54/640*getScreenWidth()+'px',
        'margin-top':8/640*getScreenWidth()+'px'
    });
    $('.sales').css({
        'width':54/640*getScreenWidth()+'px',
        'height':54/640*getScreenWidth()+'px'
    });


    $('.inputGroup').css({
        'width':178/640*getScreenWidth()+'px',
        'height':54/640*getScreenWidth()+'px'
    });
    $('.decrease').css({
        'width':54/640*getScreenWidth()+'px',
        'height':54/640*getScreenWidth()+'px'
    });
    $('.line3Input').css({
        'position':'absolute',
        'bottom':0,
        'left':54/640*getScreenWidth()+'px'
    });
    $('.numInputBorder').css({
        'width':70/640*getScreenWidth()+'px',
        'height':54/640*getScreenWidth()+'px'
    });
    $('.add').css({
        'width':54/640*getScreenWidth()+'px',
        'height':54/640*getScreenWidth()+'px',
        'left':124/640*getScreenWidth()+'px'
    });
    $('.moreBtn').css({
        'width':296/640*getScreenWidth()+'px',
        'height':56/640*getScreenWidth()+'px',
        'margin-left':177/640*getScreenWidth()+'px',
        'line-height':56/640*getScreenWidth()+'px',
        'border-radius':15/640*getScreenWidth()+'px',
        'margin-top':8/640*getScreenWidth()+'px'
    });
    if(clientType == 'browser'){
        $('#goodsBottom').height($('#ddAppBar').height());
    }
    $('#bigCover').css({
        'position':'absolute',
        'width':getScreenWidth()+'px',
        'height':$('#wrapper').height()+'px',
        'top':0,
        'left':0
    });
    $('#toBrowser').css({
        'position':'absolute',
        'width':0.7*getScreenWidth()+'px',
        'top':0,
        'left':0.3*getScreenWidth()+'px'
    });



    $('#couponTip').css({
        'width':getScreenWidth()+'px',
        'padding-top':10/720*getScreenWidth()+'px',
        'padding-left':10/720*getScreenWidth()+'px',
        'padding-right':10/720*getScreenWidth()+'px',
        'padding-bottom':18/720*getScreenWidth()+'px'
    });
    $('#takeCoupon').css({
        'position':'absolute',
        'width':86/320*getScreenWidth()+'px',
        'height':38/320*getScreenWidth()+'px',
        'top':54/320*getScreenWidth()+'px',
        'left':120/320*getScreenWidth()+'px'
    });
    $('#coupons').css({
        'position':'absolute',
        'width':680/720*getScreenWidth()+'px',
        'top':210/720*getScreenWidth()+'px',
        'left':20/720*getScreenWidth()+'px'
    });
    $('#btnClose').css({
        'position':'absolute',
        'width':80/720*getScreenWidth()+'px',
        'top':-20/720*getScreenWidth()+'px',
        'right':-20/720*getScreenWidth()+'px'
    });
    $('#ruleBtn').css({
        'position':'absolute',
        'width':128/720*getScreenWidth()+'px',
        'top':30/720*getScreenWidth()+'px',
        'right':20/720*getScreenWidth()+'px'
    });
    $('#rulePic').css({
        'position':'absolute',
        'width':460/640*getScreenWidth()+'px',
        'top':180/640*getScreenWidth()+'px',
        'right':90/640*getScreenWidth()+'px'
    });





//    $('#smallPrizeInfo').css({
//        'height':58/640*getScreenWidth()+'px',
//        'line-height':58/640*getScreenWidth()+'px',
//        'width':getScreenWidth()+'px'
//    });


}
var gPageScroll;
var fixedMenuScroll;
var alterMenuScroll;
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
    fixedMenuScroll = new IScroll('#fixedMenuBar', {
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
    alterMenuScroll = new IScroll('#scrollMenuBar', {
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
    gPageScroll.on('scrollStart',function(e){
        $(".active").removeClass("active");
        $('.add').addClass("forbidden");
        $('.add').find('img').attr("src",$('.activityInfo').attr("alterPlus"));
        $('.decrease').addClass("forbidden");
        $('.decrease').find('img').attr("src",$('.activityInfo').attr("alterReduce"));
    });
    gPageScroll.on('scroll',function(e){
        gViewModel.$wrapper.trigger("scroll");
        adjustMenuDisplay();
    });
    gPageScroll.on('scrollEnd',function(e){
        $(".active").removeClass("active");
        gViewModel.$wrapper.trigger("scroll");
        adjustMenuDisplay();
        var val = this.y;
        changeActiveMenu(-val);

//        if(document.getElementById('banner').offsetTop+gPageScroll.y <0 ){
//            clearInterval(MyMar);
//            ScrollImgLeft(100000);
//        }else{
//            clearInterval(MyMar);
//            ScrollImgLeft(25);
//        }
    });
    fixedMenuScroll.on('scrollStart',function(e){
        $(".active").removeClass("active");
    });
    fixedMenuScroll.on('scrollEnd',function(e){
        $(".active").removeClass("active");
        var val = this.x;
        alterMenuScroll.scrollTo(val, 0);
        if(gViewModel.menuWidth - getScreenWidth() + val < 10 ){
            $('.right_normal').css("display","none");
        }else{
            $('.right_normal').css("display","block");
        }
        if(val > -10 ){
            $('.left_normal').css("display","none");
        }else{
            $('.left_normal').css("display","block");
        }
    });
    alterMenuScroll.on('scrollStart',function(e){
        $(".active").removeClass("active");
        //iscroll嵌套时，为防止触发外层iscroll，暂时先disable之
        gPageScroll.disable();
    });
    alterMenuScroll.on('scrollEnd',function(e){
        $(".active").removeClass("active");
        var val = this.x;
        fixedMenuScroll.scrollTo(val, 0);
        if(gViewModel.menuWidth - getScreenWidth() + val < 10 ){
            $('.right_normal').css("display","none");
        }else{
            $('.right_normal').css("display","block");
        }
        if(val > -10 ){
            $('.left_normal').css("display","none");
        }else{
            $('.left_normal').css("display","block");
        }
        //iscroll嵌套，内层事件处理完后重新使能外层iscroll
        gPageScroll.enable();
    });
    refreshScroll();
}
function refreshScroll(){
    setTimeout(function(){
        gPageScroll.refresh();
        if( gViewModel.$scrollMenuBar.get(0).offsetTop+gPageScroll.y > 0){
            gViewModel.$fixedMenuBar.css("display","none");
        }
        $("#wrapper .img-goods").lazyload({ threshold : 200 });
    },600);
}
function adjustMenuDisplay(){
    var $scrollMenuBar = gViewModel.$scrollMenuBar;
    var $fixedMenuBar = gViewModel.$fixedMenuBar;
    if($scrollMenuBar.get(0).offsetTop+gPageScroll.y <0){
        $fixedMenuBar.css("display","block");
        fixedMenuScroll.refresh();
    }else{
        $fixedMenuBar.css("display","none");
    }

}
function initButtonStatus(){
    gViewModel.$wrapper.clickStatus({
        targetSelector:".img_wrapper",
        touchEventEnable:gIsTouchDevice
    });
    gViewModel.$main.clickStatus({
        targetSelector:".groupBtn",
        touchEventEnable:gIsTouchDevice
    });
    gViewModel.$main.clickStatus({
        targetSelector:".moreBtn",
        touchEventEnable:gIsTouchDevice
    });
    gViewModel.$main.clickStatus({
        targetSelector:"#openApp",
        touchEventEnable:gIsTouchDevice
    });
    gViewModel.$main.clickStatus({
        targetSelector:"#toBrowser",
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
        targetSelector:"#takeCoupon",
        touchEventEnable:gIsTouchDevice
    });
    gViewModel.$main.clickStatus({
        targetSelector:"#coupons",
        touchEventEnable:gIsTouchDevice
    });
    gViewModel.$main.clickStatus({
        targetSelector:"#btnClose",
        touchEventEnable:gIsTouchDevice
    });
    gViewModel.$main.clickStatus({
        targetSelector:"#ruleBtn",
        touchEventEnable:gIsTouchDevice
    });
    gViewModel.$main.clickStatus({
        targetSelector:"#rulePic",
        touchEventEnable:gIsTouchDevice
    });


}

function initEventHandlers(){
    gViewModel.$wrapper.on("clickstatus.up",'.img_wrapper',function(){
        NativeCaller.viewActivityGoodsDetail($(this).siblings(".goodsInfo").attr("goodsId"));
    }).on(gMyDown,'.goodsBlock .add',function(e){
        e.stopPropagation();
        if($(this).hasClass("forbidden")){
            var alterPicPlus = $('.activityInfo').attr("normalPlus");
            $(this).find('img').attr("src",alterPicPlus);
            $(this).removeClass("forbidden");
            var alterPicReduce = $('.activityInfo').attr("normalReduce");
            $(this).siblings('.decrease').find('img').attr("src",alterPicReduce);
            $(this).siblings('.decrease').removeClass("forbidden");
        }else{
            var $goodsInfo = $(this).closest('.goodsRow').find('.goodsInfo');
            var $inputGroup = $(this).parent();
            var $input = $inputGroup.find('.numInputBorder');
            var minValue = parseInt($inputGroup.attr("minValue"));
            var maxValue = parseInt($inputGroup.attr("maxValue"));
            var step = parseInt($inputGroup.attr("step"));
            if(maxValue == null || maxValue == ""){
                maxValue = 999999;
            }
            if(minValue == null || minValue == ""){
                minValue = 0;
            }
            if(step == null || step == ""){
                step = 1;
            }
            var curValue = parseInt($input.val());
            if(curValue+step<minValue){
                $input.val(minValue);
            }else if(curValue+step > maxValue){
                $input.val(curValue);
            }else{
                $input.val(curValue+step);
            }
            var goodsId = $goodsInfo.attr('goodsid');
            valueDidChangedHanlder();
        }
    }).on(gMyDown,'.goodsBlock .decrease',function(e){
        e.stopPropagation();
        if($(this).hasClass("forbidden")){
            var alterPicReduce = $('.activityInfo').attr("normalReduce");
            $(this).find('img').attr("src",alterPicReduce);
            $(this).removeClass("forbidden");
            var alterPicPlus = $('.activityInfo').attr("normalPlus");
            $(this).siblings('.add').find('img').attr("src",alterPicPlus);
            $(this).siblings('.add').removeClass("forbidden");
        }else{
            var $goodsInfo = $(this).closest('.goodsRow').find('.goodsInfo');
            var $inputGroup = $(this).parent();
            var $input = $inputGroup.find('.numInputBorder');
            var minValue = parseInt($inputGroup.attr("minValue"));
            var step = parseInt($inputGroup.attr("step"));
            if(minValue == null || minValue == ""){
                minValue = 0;
            }
            if(step == null || step == ""){
                step = 1;
            }
            var curValue = parseInt($input.val());
            if(curValue-step<minValue){
                $input.val(0);
            }else{
                $input.val(curValue-step);
            }
            var goodsId = $goodsInfo.attr('goodsid');
            valueDidChangedHanlder();
        }
    });
    $('#btnPurchase').on('click', function () {
        if($('#btnPurchase').attr("isCanBuy") == "Yes"){
            var param = {};
            param.activityId = $('.activityInfo').attr("activityId");
            param.activityName = $('.activityInfo').attr("activityName");
            param.totalGoodsNumber = $('#totalGoodsNumber').text();
            param.goodsList = [];
            var $goodsInfos = $('#wrapper .goodsInfo');
            for(var i=0;i<$goodsInfos.length;i++){
                var $goodsInfoItem = $($goodsInfos[i]);
                var $inputer = $goodsInfoItem.closest(".goodsRow").find('.numInputBorder');
                var inputerValue = ( $inputer.val() - 0 );
                if($goodsInfoItem.closest(".goodsRow").find('.numInputBorder').length > 0 && inputerValue > 0){
                    var goodsView = {};
                    goodsView.goodsId = $goodsInfoItem.attr("goodsId");
                    goodsView.goodsNumber = inputerValue;
                    param.goodsList.push(goodsView);
                }
            }
            var activityParamStr = JSON.stringify(param);
            NativeCaller.balanceActivityOrder(activityParamStr);
        }
    });
    $('.groupBtn').on("clickstatus.up",function(e){
        var key = $(this).attr("key");
        mainMenuClick(key);
    });
    $('.moreBtn').on("clickstatus.up",function(e){
        var key = $(this).attr("key");
        if($(this).hasClass('toHide')){//收起
            extraGoodsFadeOut(key);
            $(this).removeClass('toHide');
            $(this).text('查看更多>>');
            refreshScroll();
        }else{
            extraGoodsFadeIn(key);
            $(this).addClass('toHide');
            $(this).text('收起<<');
            refreshScroll();
        }
    });
    $('#openApp').on("clickstatus.up",function(e){
        if(gIsWechat()){
            $('#toBrowser').css('display','block');
            $('#bigCover').css('display','block');
            return;
        }
        openApp();
    });
    $('#toBrowser').on("clickstatus.up",function(e){
        $('#toBrowser').css('display','none');
        $('#bigCover').css('display','none');
    });
    $('#closeBar').on("clickstatus.up",function(e){
        $('#ddAppBar').css('display','none');
    });
    $('#takeCoupon').on('clickstatus.up',function(){
        if(clientType != 'app'){
            $('#openApp').trigger('clickstatus.up');
            return;
        }else{
            if(userId==null){
                NativeCaller.login();
                return;
            }
        }
        $.ajax({
            type: 'get',
            url: window.location.origin + '/fauna/act101/takeNewArrivalPrize',
            async: true,
            dataType: 'json',
            success: function (result) {
                if (!result.success) {
                    NativeCaller.showToast("网络异常，请稍候");
                    window.location.reload();
                }else{
                    var res = result.data;
                    if(res){
                        return;
                    }else{
                        NativeCaller.showToast("网络异常，请稍候");
                        window.location.reload();
                    }
                }
            }
        });
        $('#bigCover').show();
        $('#coupons').show();
        $('#couponTip').children().eq(0).attr("src",tipTaken);
        $('#takeCoupon').hide();
    });
    $('#bigCover').on("clickstatus.up",function(e){
        if($('#toBrowser').css('display')=='block'){
            $('#toBrowser').css('display','none');
            $('#bigCover').css('display','none');
        }
        if($('#rulePic').css('display')=='block'){
            $('#rulePic').css('display','none');
            $('#bigCover').css('display','none');
        }
    });
    $('#btnClose').on("clickstatus.up",function(e){
        $('#coupons').css('display','none');
        $('#bigCover').css('display','none');
    });
    $('#rulePic').on("clickstatus.up",function(e){
        $('#rulePic').css('display','none');
        $('#bigCover').css('display','none');
    });
    $('#ruleBtn').on("clickstatus.up",function(e){
        $('#rulePic').css('display','block');
        $('#bigCover').css('display','block');
    });




}
function openApp(){
    var url = window.location.origin+'/fauna/act101/newcomer';
    window.location = 'fauna-b://webview?url='+url;

    setTimeout(function() {
        window.location = gAppDownloadPage.b;
    }, 5000);
}

function hideExtraGoods(){
    $(".goodsBlock[isShow='hide']").hide();
}
function extraGoodsFadeIn(key){
    $(".goodsBlock[groupKey='"+key+"'][isShow='hide']").fadeIn();
}
function extraGoodsFadeOut(key){
    $(".goodsBlock[groupKey='"+key+"'][isShow='hide']").fadeOut();
}
function changeActiveMenu(val){
    if(val == null){
        val = -(gPageScroll.y);
    }
    $(".groupBtn").removeClass('menuSelected');
    if(val < $(".subAct[key='DP']").offset().top - $("#banner").offset().top - $('.goodsBlock').height()){
        $(".groupBtn[key='JY']").addClass('menuSelected');
        alterMenuScroll.scrollTo(-160/640*0.01*getScreenWidth(), 0);
        fixedMenuScroll.scrollTo(-160/640*0.01*getScreenWidth(), 0);
    }else if(val < $(".subAct[key='GJ']").offset().top - $("#banner").offset().top - $('.goodsBlock').height()){
        $(".groupBtn[key='DP']").addClass('menuSelected');
        alterMenuScroll.scrollTo(-160/640*0.01*getScreenWidth(), 0);
        fixedMenuScroll.scrollTo(-160/640*0.01*getScreenWidth(), 0);
    }else if(val < $(".subAct[key='QCYP']").offset().top - $("#banner").offset().top - $('.goodsBlock').height()){
        $(".groupBtn[key='GJ']").addClass('menuSelected');
        alterMenuScroll.scrollTo(-160/640*0.5*getScreenWidth(), 0);
        fixedMenuScroll.scrollTo(-160/640*0.5*getScreenWidth(), 0);
    }else if(val < $(".subAct[key='YH']").offset().top - $("#banner").offset().top - $('.goodsBlock').height()){
        $(".groupBtn[key='QCYP']").addClass('menuSelected');
        alterMenuScroll.scrollTo(-160/640*1.0*getScreenWidth(), 0);
        fixedMenuScroll.scrollTo(-160/640*1.0*getScreenWidth(), 0);
    }else{
        $(".groupBtn[key='YH']").addClass('menuSelected');
        alterMenuScroll.scrollTo(-160/640*1.0*getScreenWidth(), 0);
        fixedMenuScroll.scrollTo(-160/640*1.0*getScreenWidth(), 0);
    }
}

//var MyMar = null;
//function ScrollImgLeft(speed){
//    if(speed==null){
//        speed = 25;
//    }
//    var scroll_begin = document.getElementById("scroll_begin");
//    var scroll_end = document.getElementById("scroll_end");
//    var scroll_div = document.getElementById("smallPrizeInfo");
//    scroll_end.innerHTML=scroll_begin.innerHTML;
//    function Marquee(){
//        if(scroll_end.offsetWidth-scroll_div.scrollLeft<=0)
//            scroll_div.scrollLeft-=scroll_begin.offsetWidth;
//        else
//            scroll_div.scrollLeft++;
//    }
//    MyMar=setInterval(Marquee,speed);
//}
//
