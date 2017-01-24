var gViewModel = {};
gViewModel.shineTimer = null;
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
    if(clientType == "app"){
        if(userId==null){
            NativeCaller.login();
        }
        if(hasTake == 'already'){
            $('#tryBtn').hide();
            $('#grayBtn').show();
        }
    }
    //调整页面显示
    $('.shine').hide();
    windowResized();
    //横向游幕
    ScrollImgLeft();
//    gViewModel.offsetList = [$("#scrollMenuBar").offset().top,$(".subAct[key='1']").offset().top,$(".subAct[key='2']").offset().top,$(".subAct[key='3']").offset().top,$(".subAct[key='4']").offset().top,$(".subAct[key='5']").offset().top,$(".subAct[key='6']").offset().top,$(".subAct[key='7']").offset().top];
    hideExtraGoods();

    $('body').css("visibility","visible");
    gViewModel.$main = $('#main');
    gViewModel.$wrapper = $('#wrapper');
    gViewModel.$scrollMenuBar = $('#scrollMenuBar');
    gViewModel.$fixedMenuBar = $('#fixedMenuBar');
    gViewModel.$groupBtn = $('.groupBtn');
    gViewModel.menuWidth = $('.menuWrapper').width();

    initButtonStatus();
    initEventHandlers();
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
    //初始化iscroll
    loadScroll();
//    mainMenuClick('JY');
    document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
    document.addEventListener('DOMContentLoaded', function () { setTimeout(loadScroll, 200); }, false);
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
    gPageScroll.scrollTo(0,-pos+$('#scrollMenuBar').height()+30/640*getScreenWidth());
    adjustMenuDisplay();
    changeActiveMenu();
    if(key=='JY'){
        gViewModel.$fixedMenuBar.css("display","none");
    }
}

function getScreenWidth(){
    return document.body.clientWidth;
}
function windowResized(){
    $('#scrollMenuBar').css({
        'height':64/640*getScreenWidth()+'px'
    });
    $('.menuWrapper').css({
        'width':160/640*6*getScreenWidth()+'px'
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
        'padding-bottom':24/640*getScreenWidth()+'px',
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


    $('#lotteryWrapper').css({
        'width':596/640*getScreenWidth()+'px',
        'margin-top':14/640*getScreenWidth()+'px',
        'margin-left':22/640*getScreenWidth()+'px'
    });
    $('.lotteryBtn').css({
        'position':'absolute',
        'width':184/640*getScreenWidth()+'px',
        'height':152/640*getScreenWidth()+'px',
        'top':174/640*getScreenWidth()+'px',
        'left':206/640*getScreenWidth()+'px'
    });
    $('.shine').css({
        'position':'absolute',
        'width':184/640*getScreenWidth()+'px',
        'height':152/640*getScreenWidth()+'px'
    });
    $('#shine0').css({
        'top':22/640*getScreenWidth()+'px',
        'left':22/640*getScreenWidth()+'px'
    });
    $('#shine1').css({
        'top':22/640*getScreenWidth()+'px',
        'left':206/640*getScreenWidth()+'px'
    });
    $('#shine2').css({
        'top':22/640*getScreenWidth()+'px',
        'right':22/640*getScreenWidth()+'px'
    });
    $('#shine7').css({
        'top':174/640*getScreenWidth()+'px',
        'left':22/640*getScreenWidth()+'px'
    });
    $('#shine3').css({
        'top':174/640*getScreenWidth()+'px',
        'right':22/640*getScreenWidth()+'px'
    });
    $('#shine6').css({
        'bottom':22/640*getScreenWidth()+'px',
        'left':22/640*getScreenWidth()+'px'
    });
    $('#shine5').css({
        'bottom':22/640*getScreenWidth()+'px',
        'left':206/640*getScreenWidth()+'px'
    });
    $('#shine4').css({
        'bottom':22/640*getScreenWidth()+'px',
        'right':22/640*getScreenWidth()+'px'
    });

    $('#tipBg').css({
        'position':'absolute',
        'width':592/640*getScreenWidth()+'px',
        'top':262/640*getScreenWidth()+'px',
        'left':24/640*getScreenWidth()+'px'
    });
    $('#winTip1').css({
        'position':'absolute',
        'width':'100%',
        'top':160/640*getScreenWidth()+'px',
        'left':0
    });
    $('.winPic').css({
        'position':'absolute',
        'width':388/640*getScreenWidth()+'px',
        'top':244/640*getScreenWidth()+'px',
        'left':102/640*getScreenWidth()+'px'
    });
    $('#winTip2').css({
        'position':'absolute',
        'width':'100%',
        'top':477/640*getScreenWidth()+'px',
        'left':0
    });
    $('#winTip3').css({
        'position':'absolute',
        'width':'100%',
        'bottom':88/640*getScreenWidth()+'px',
        'left':0
    });
    $('#ruleBtn').css({
        'position':'absolute',
        'width':130/640*getScreenWidth()+'px',
        'bottom':10/640*getScreenWidth()+'px',
        'right':10/640*getScreenWidth()+'px'
    });
    $('#rulePic').css({
        'position':'absolute',
        'width':500/640*getScreenWidth()+'px',
        'top':262/640*getScreenWidth()+'px',
        'right':70/640*getScreenWidth()+'px'
    });
    $('#smallPrizeInfo').css({
        'height':58/640*getScreenWidth()+'px',
        'line-height':58/640*getScreenWidth()+'px',
        'width':getScreenWidth()+'px'
    });






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

        $('#fixedPurchaseBar').show();

        if(document.getElementById('lotteryWrapper').offsetTop+gPageScroll.y <0 ){
            clearInterval(MyMar);
            ScrollImgLeft(100000);
        }else{
            clearInterval(MyMar);
            ScrollImgLeft(25);
        }
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
        targetSelector:".toBrowser",
        touchEventEnable:gIsTouchDevice
    });
    gViewModel.$main.clickStatus({
        targetSelector:"#closeBar",
        touchEventEnable:gIsTouchDevice
    });
    gViewModel.$main.clickStatus({
        targetSelector:"#tryBtn",
        touchEventEnable:gIsTouchDevice
    });
    gViewModel.$main.clickStatus({
        targetSelector:"#tipBg",
        touchEventEnable:gIsTouchDevice
    });
    gViewModel.$main.clickStatus({
        targetSelector:"#bigCover",
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
    $('#tryBtn').on("clickstatus.up",function(e){
        if(clientType != 'app'){
            $('#openApp').trigger('clickstatus.up');
            return;
        }
        $('#tryBtn').css('display','none');
        $('#grayBtn').css('display','block');
        var prize;
        $.ajax({
            type: 'get',
            url: window.location.origin + '/fauna/act101/takePrize?userId=' + userId,
            async: true,
            dataType: 'json',
            success: function (res) {
                if (!res.success) {
                    NativeCaller.showToast("网络异常！");
                }else{
                    prize = res.data;
//                    prize = '8';
                    var pos;
                    if(prize == 66){
                        pos = 2;
                    }else if(prize == 88){
                        pos = 3;
                    }else if(prize == 18){
                        pos = 4;
                    }else if(prize == 8){
                        pos = 5;
                    }else if(prize == 888){
                        pos = 6;
                    }else{
                        pos = 5;
                    }
                    shineIt(pos,prize);
                }
            }
        });
    });
    $('#tipBg').on("clickstatus.up",function(e){
        $('#tipBg').css('display','none');
        $('#bigCover').css('display','none');
    });
    $('#bigCover').on("clickstatus.up",function(e){
        if($('#tipBg').css('display')=='block'){
            $('#tipBg').css('display','none');
            $('#bigCover').css('display','none');
        }
        if($('#rulePic').css('display')=='block'){
            $('#rulePic').css('display','none');
            $('#bigCover').css('display','none');
        }
    });
    $('#ruleBtn').on("clickstatus.up",function(e){
        $('#bigCover').css('display','block');
        $('#rulePic').css('display','block');
    });
    $('#rulePic').on("clickstatus.up",function(e){
        $('#rulePic').css('display','none');
        $('#bigCover').css('display','none');
    });


}
function openApp(){
    var url = window.location.origin+'/fauna/act101/anniversary/lottery';
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
    if(val < $(".subAct[key='PJ']").offset().top - $("#banner").offset().top - $('.goodsBlock').height()){
        $(".groupBtn[key='JY']").addClass('menuSelected');
    }else if(val < $(".subAct[key='YH']").offset().top - $("#banner").offset().top - $('.goodsBlock').height()){
        $(".groupBtn[key='PJ']").addClass('menuSelected');
        alterMenuScroll.scrollTo(-160/640*0.2*getScreenWidth(), 0);
        fixedMenuScroll.scrollTo(-160/640*0.2*getScreenWidth(), 0);
    }else if(val < $(".subAct[key='GJ']").offset().top - $("#banner").offset().top - $('.goodsBlock').height()){
        $(".groupBtn[key='YH']").addClass('menuSelected');
        alterMenuScroll.scrollTo(-160/640*0.5*getScreenWidth(), 0);
        fixedMenuScroll.scrollTo(-160/640*0.5*getScreenWidth(), 0);
    }else if(val < $(".subAct[key='QCYP']").offset().top - $("#banner").offset().top - $('.goodsBlock').height()){
        $(".groupBtn[key='GJ']").addClass('menuSelected');
        alterMenuScroll.scrollTo(-160/640*1.5*getScreenWidth(), 0);
        fixedMenuScroll.scrollTo(-160/640*1.5*getScreenWidth(), 0);
    }else if(val < $(".subAct[key='DP']").offset().top - $("#banner").offset().top - $('.goodsBlock').height()){
        $(".groupBtn[key='QCYP']").addClass('menuSelected');
        alterMenuScroll.scrollTo(-160/640*2*getScreenWidth(), 0);
        fixedMenuScroll.scrollTo(-160/640*2*getScreenWidth(), 0);
    }else{
        $(".groupBtn[key='DP']").addClass('menuSelected');
        alterMenuScroll.scrollTo(-160/640*2*getScreenWidth(), 0);
        fixedMenuScroll.scrollTo(-160/640*2*getScreenWidth(), 0);
    }
}

function shineIt(pos,prize){
    var myDate = new Date();
    console.log(myDate.getTime());
    clearInterval(gViewModel.shineTimer);
    var speed = 100;
    var i = 0,j=0;
    gViewModel.shineTimer = setInterval(function(){
        if(j==3){
            if(i < pos+1){
                lastRound(pos,i,prize);
                i++;
            }else{
                clearInterval(gViewModel.shineTimer);
            }
        }else{
            $('.shine').hide();
            $('#shine'+i).show();
            i++;
            if(i==8){
                i=0;
                j++;
            }
        }
    },speed)
}
function lastRound(pos,i,prize){
    var baseSpeed = 200;
    if(pos == 0){
        setTimeout(function(){
            $('.shine').hide();
            $('#shine'+i).show();
        },baseSpeed);
    }else{
        var d;
        if(pos < 5){
            d = 350/pos;
        }else{
            d = 400/pos;
        }
        setTimeout(function(){
            $('.shine').hide();
            $('#shine'+i).show();
            console.log(pos+'_'+i);
            if(i==pos){
                showRes(prize);
            }
        },(i+1)*baseSpeed+(i+1)*i*d/2);
    }
    var myDate = new Date();
    console.log(myDate.getTime());
}
function showRes(prePrizeVal){
    setTimeout(function(){
        $('#bigCover').css("display","block");
        $('#tipBg').css("display","block");
        $('#tryTip').attr('src',winTip);
        if(prePrizeVal=="8"){
            $('#winTip1').text("运气爆棚，长长久久！");
            $('#win8').css("display","block");
            $('#winTip2C').text("8元");
        }else if(prePrizeVal=="18"){
            $('#winTip1').text("财运滚滚，福星高照！");
            $('#win18').css("display","block");
            $('#winTip2C').text("18元");
        }else if(prePrizeVal=="66"){
            $('#winTip1').text("六六大顺，富贵花开！");
            $('#win66').css("display","block");
            $('#winTip2C').text("66元");
        }else if(prePrizeVal=="88"){
            $('#winTip1').text("八八大发，羡慕嫉妒恨！");
            $('#win88').css("display","block");
            $('#winTip2C').text("88元");
        }else if(prePrizeVal=="888"){
            $('#winTip1').text("人生大赢家，RP大爆发！");
            $('#win888').css("display","block");
            $('#winTip2C').text("888元");
        }
    },1500)
};


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

