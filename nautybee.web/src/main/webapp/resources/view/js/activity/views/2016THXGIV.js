var gViewModel = {};
var turnplate={
    restaraunts:[],				//大转盘奖品名称
    colors:[],					//大转盘奖品区块对应背景颜色
    outsideRadius:165,			//大转盘外圆的半径
    textRadius:135,				//大转盘奖品位置距离圆心的距离
    insideRadius:48,			//大转盘内圆的半径
    startAngle:0,				//开始角度
    bRotate:false				//false:停止;ture:旋转
};
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
window.onload=function(){
    setTimeout(function(){
        drawRouletteWheel();
    },100)
};
function init(){
    if(clientType == "app"){
        if(userId==null){
            NativeCaller.login();
        }
        if(prizeId == null || prizeId == ''){
            $('#tipLine1').text("购物满200就能抽奖！");
            $('#tipLine2').text("好油好礼在等你喔~");
            $('.pointer').css('display','none');
            $('.replacePointer').css('display','block');
        }else{
            if(hasTake){
                $('#tipLine1').text("亲，您今天的运气不错");
                $('#tipLine2').text("明天还要来哦！");
                $('.pointer').css('display','none');
                $('.replacePointer').css('display','block');
                showRes();
            }else{
                $('#tipLine1').text("您有一次抽奖机会");
                $('#tipLine2').text("赶紧来试试手气吧！");
            }
        }
    }else{
        $('#tipLine1').text("您有一次抽奖机会");
        $('#tipLine2').text("赶紧来试试手气吧！");
    }
    //调整页面显示
    if(showTurnplate == 'N'){
        $('#smallPrizeInfo').css('display','none');
        $('#secBanner').css('display','none');
    }
    windowResized();
    //转盘相关
    initTurnplate();
    //横向游幕
    ScrollImgLeft();
    hideExtraGoods();

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
    var pos = $(".subAct[key='"+key+"']").offset().top - gPageScroll.y - 82/720*getScreenWidth();
    gPageScroll.scrollTo(0,-pos);
    adjustMenuDisplay();
    changeActiveMenu();
}

function getScreenWidth(){
    return document.body.clientWidth;
}
function windowResized(){
    $('#scrollMenuBar').css({
        'height':82/720*getScreenWidth()+'px'
    });
    $('.menuWrapper').css({
        'width':240/640*4*getScreenWidth()+'px'
    });
    $('.menuWrapper').children().css({
        'width':240/640*getScreenWidth()+'px',
        'height':82/720*getScreenWidth()+'px',
        'line-height':82/720*getScreenWidth()+'px'
    });
    $('.left_normal').css({
        'width':20/640*getScreenWidth()+'px',
        'height':34/640*getScreenWidth()+'px',
        'position':'absolute',
        'top':22/640*getScreenWidth()+'px',
        'left':4/414*getScreenWidth()+'px',
        'line-height':1,
        'display':'none'
    });
    $('.right_normal').css({
        'width':20/640*getScreenWidth()+'px',
        'height':34/640*getScreenWidth()+'px',
        'position':'absolute',
        'top':22/640*getScreenWidth()+'px',
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


    $('#ruleBtn').css({
        'position':'absolute',
        'width':160/720*getScreenWidth()+'px',
        'top':20/720*getScreenWidth()+'px',
        'right':20/720*getScreenWidth()+'px'
    });
    $('#rulePic').css({
        'position':'absolute',
        'width':getScreenWidth()+'px',
        'top':200/720*getScreenWidth()+'px',
        'right':0
    });
    $('.winTip').css({
        'position':'absolute',
        'width':getScreenWidth()+'px',
        'top':200/720*getScreenWidth()+'px',
        'right':0
    });
    $('#tip').css({
        'width':250/320*getScreenWidth()+'px',
        'height':55/320*getScreenWidth()+'px',
        'top':295/320*getScreenWidth()+'px',
        'left':35/320*getScreenWidth()+'px'
    });
    $('.tips').css({
        'width':250/320*getScreenWidth()+'px',
        'height':27.5/320*getScreenWidth()+'px',
        'line-height':27.5/320*getScreenWidth()+'px'
    });
    $('#smallPrizeInfo').css({
        'height':44/720*getScreenWidth()+'px',
        'line-height':44/720*getScreenWidth()+'px',
        'width':getScreenWidth()+'px'
    });
    $('.banner').css({
        'height':680/720*getScreenWidth()+'px',
        'width':680/720*getScreenWidth()+'px',
        'position':'absolute',
        'top':0,
        'left':20/720*getScreenWidth()+'px'
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

        if(document.getElementById('smallPrizeInfo').offsetTop+gPageScroll.y <0 ){
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
        targetSelector:"#ruleBtn",
        touchEventEnable:gIsTouchDevice
    });
    gViewModel.$main.clickStatus({
        targetSelector:"#rulePic",
        touchEventEnable:gIsTouchDevice
    });
    gViewModel.$main.clickStatus({
        targetSelector:"#winTip",
        touchEventEnable:gIsTouchDevice
    });
    gViewModel.$main.clickStatus({
        targetSelector:".pointer",
        touchEventEnable:gIsTouchDevice
    });
    gViewModel.$main.clickStatus({
        targetSelector:".winTip",
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
    $('#bigCover').on("clickstatus.up",function(e){
        $('.winTip').css('display','none');
        $('#bigCover').css('display','none');
        $('#rulePic').css('display','none');
    });
    $('#ruleBtn').on("clickstatus.up",function(e){
        $('#bigCover').css('display','block');
        $('#rulePic').css('display','block');
    });
    $('#rulePic').on("clickstatus.up",function(e){
        $('#rulePic').css('display','none');
        $('#bigCover').css('display','none');
    });
    $('.winTip').on("clickstatus.up",function(e){
        $('#bigCover').css('display','none');
        $('.winTip').css('display','none');
    });



}
function openApp(){
    var url = window.location.origin+'/fauna/double11/thanksgiving';
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
    if(val < $(".subAct[key='LZ']").offset().top - $("#banner").offset().top - $('.goodsBlock').height()){
        $(".groupBtn[key='WZ']").addClass('menuSelected');
        alterMenuScroll.scrollTo(0, 0);
        fixedMenuScroll.scrollTo(0, 0);
    }else if(val < $(".subAct[key='QZ']").offset().top - $("#banner").offset().top - $('.goodsBlock').height()){
        $(".groupBtn[key='LZ']").addClass('menuSelected');
        alterMenuScroll.scrollTo(-1/16*getScreenWidth(), 0);
        fixedMenuScroll.scrollTo(-1/16*getScreenWidth(), 0);
    }else if(val < $(".subAct[key='BZ']").offset().top - $("#banner").offset().top - $('.goodsBlock').height()){
        $(".groupBtn[key='QZ']").addClass('menuSelected');
        alterMenuScroll.scrollTo(-7/16*getScreenWidth(), 0);
        fixedMenuScroll.scrollTo(-7/16*getScreenWidth(), 0);
    }else{
        $(".groupBtn[key='BZ']").addClass('menuSelected');
        alterMenuScroll.scrollTo(-1/2*getScreenWidth(), 0);
        fixedMenuScroll.scrollTo(-1/2*getScreenWidth(), 0);
    }
}

function showRes(){
    //状态修改
    $('#tipLine1').text("亲，您今天的运气不错");
    $('#tipLine2').text("明天还要来哦！");

    $('#bigCover').css("display","block");
    if(prizeVal=="5"){
        $('#winTip5').css("display","block");
    }else if(prizeVal=="10"){
        $('#winTip10').css("display","block");
    }else if(prizeVal=="20"){
        $('#winTip20').css("display","block");
    }else if(prizeVal=="30"){
        $('#winTip30').css("display","block");
    }else if(prizeVal=="50"){
        $('#winTip50').css("display","block");
    }else if(prizeVal=="SYY"){
        $('#winTipSYY').css("display","block");
    }else {
        $('#winTip5').css("display","block");
    }
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
function initTurnplate(){
    //动态添加大转盘的奖品与奖品区域背景颜色
    turnplate.restaraunts = ["5元红包", "10元红包", "20元红包", "30元红包", "50元红包", "5元红包", "10元红包", "食用油"];
    turnplate.colors = ["#FFF4D6", "#FFFFFF", "#FFF4D6", "#FFFFFF","#FFF4D6", "#FFFFFF", "#FFF4D6", "#FFFFFF"];
    //旋转转盘 item:奖品位置; txt：提示语;
    var rotateFn = function (item, txt){
        var angles = item * (360 / turnplate.restaraunts.length) - (360 / (turnplate.restaraunts.length*2));
        if(angles<270){
            angles = 270 - angles;
        }else{
            angles = 360 - angles + 270;
        }
        $('#wheelcanvas').stopRotate();
        var handler = function(event) {
            event.preventDefault();
        };
        document.body.addEventListener('touchmove',handler, false);
        $('#wheelcanvas').rotate({
            angle:0,
            animateTo:angles+1800,
            duration:8000,
            callback:function (){
                document.body.removeEventListener('touchmove', handler, false);
//                NativeCaller.showToast(txt);
                turnplate.bRotate = !turnplate.bRotate;
                showRes(txt);
            }
        });
    };
    $('.pointer').on('clickstatus.up',function(){
        if(clientType != 'app'){
            $('#openApp').trigger('clickstatus.up');
            return;
        }else{
            if(userId==null){
                NativeCaller.login();
                return;
            }
        }
        $('.pointer').css("display","none");
        $('.replacePointer').css("display","block");
        $.ajax({
            type: 'get',
            url: window.location.origin + '/fauna/double11/takeTurnplatePrize?prizeId='+prizeId,
            async: false,
            dataType: 'json',
            success: function (result) {
                if (!result.success) {
                    NativeCaller.showToast("网络异常！");
                    return;
                }else{
                    prizeVal = result.data;
//                    prizeVal = 'SYY';
                }
            }
        });
        if(turnplate.bRotate)return;
        turnplate.bRotate = !turnplate.bRotate;
        //获取随机数(奖品个数范围内)
        var item = 1;
        if(prizeVal=="5"){
            item = 1;
        }else if(prizeVal=="10"){
            item = 2
        }else if(prizeVal=="20"){
            item = 3
        }else if(prizeVal=="30"){
            item = 4
        }else if(prizeVal=="50"){
            item = 5
        }else if(prizeVal=="SYY"){
            item = 8
        }else{
            item = 1
        }
        //奖品数量等于10,指针落在对应奖品区域的中心角度[252, 216, 180, 144, 108, 72, 36, 360, 324, 288]
        rotateFn(item, turnplate.restaraunts[item-1]);
        console.log(item);
    });
}
function drawRouletteWheel() {
    var canvas = document.getElementById("wheelcanvas");
    if (canvas.getContext) {
        //根据奖品个数计算圆周角度
        var arc = Math.PI / (turnplate.restaraunts.length/2);
        var ctx = canvas.getContext("2d");
        //在给定矩形内清空一个矩形
        ctx.clearRect(0,0,422,422);
        //strokeStyle 属性设置或返回用于笔触的颜色、渐变或模式
        ctx.strokeStyle = "#FFBE04";
        //font 属性设置或返回画布上文本内容的当前字体属性
        ctx.font = '16px Microsoft YaHei';
        for(var i = 0; i < turnplate.restaraunts.length; i++) {
            var angle = turnplate.startAngle + i * arc;
            ctx.fillStyle = turnplate.colors[i];
            ctx.beginPath();
            //arc(x,y,r,起始角,结束角,绘制方向) 方法创建弧/曲线（用于创建圆或部分圆）
            ctx.arc(211, 211, turnplate.outsideRadius, angle, angle + arc, false);
            ctx.arc(211, 211, turnplate.insideRadius, angle + arc, angle, true);
            ctx.stroke();
            ctx.fill();
            //锁画布(为了保存之前的画布状态)
            ctx.save();

            //----绘制奖品开始----
            ctx.fillStyle = "#E5302F";
            var text = turnplate.restaraunts[i];
            var line_height = 17;
            //translate方法重新映射画布上的 (0,0) 位置
            ctx.translate(211 + Math.cos(angle + arc / 2) * turnplate.textRadius, 211 + Math.sin(angle + arc / 2) * turnplate.textRadius);

            //rotate方法旋转当前的绘图
            ctx.rotate(angle + arc / 2 + Math.PI / 2);

            /** 下面代码根据奖品类型、奖品名称长度渲染不同效果，如字体、颜色、图片效果。(具体根据实际情况改变) **/
            if(text.indexOf("M")>0){//流量包
                var texts = text.split("M");
                for(var j = 0; j<texts.length; j++){
                    ctx.font = j == 0?'bold 20px Microsoft YaHei':'16px Microsoft YaHei';
                    if(j == 0){
                        ctx.fillText(texts[j]+"M", -ctx.measureText(texts[j]+"M").width / 2, j * line_height);
                    }else{
                        ctx.fillText(texts[j], -ctx.measureText(texts[j]).width / 2, j * line_height);
                    }
                }
            }else if(text.indexOf("M") == -1 && text.length>6){//奖品名称长度超过一定范围
                text = text.substring(0,6)+"||"+text.substring(6);
                var texts = text.split("||");
                for(var j = 0; j<texts.length; j++){
                    ctx.fillText(texts[j], -ctx.measureText(texts[j]).width / 2, j * line_height);
                }
            }else{
                //在画布上绘制填色的文本。文本的默认颜色是黑色
                //measureText()方法返回包含一个对象，该对象包含以像素计的指定字体宽度
                ctx.fillText(text, -ctx.measureText(text).width / 2, 0);
            }
            //添加对应图标
            if(text.indexOf("5元红包")==0){
                var img= document.getElementById("win5");
                img.onload=function(){
                    ctx.drawImage(img,-24,10,50,50);
                };
                ctx.drawImage(img,-24,10,50,50);
            }else if(text.indexOf("10元红包")==0){
                var img= document.getElementById("win10");
                img.onload=function(){
                    ctx.drawImage(img,-24,10,50,50);
                };
                ctx.drawImage(img,-24,10,50,50);
            }else if(text.indexOf("20元红包")==0){
                var img= document.getElementById("win20");
                img.onload=function(){
                    ctx.drawImage(img,-24,10,50,50);
                };
                ctx.drawImage(img,-24,10,50,50);
            }else if(text.indexOf("30元红包")==0){
                var img= document.getElementById("win30");
                img.onload=function(){
                    ctx.drawImage(img,-24,10,50,50);
                };
                ctx.drawImage(img,-24,10,50,50);
            }else if(text.indexOf("50元红包")==0){
                var img= document.getElementById("win50");
                img.onload=function(){
                    ctx.drawImage(img,-24,10,50,50);
                };
                ctx.drawImage(img,-24,10,50,50);
            }else if(text.indexOf("食用油")==0){
                var img= document.getElementById("winSYY");
                img.onload=function(){
                    ctx.drawImage(img,-27,10,60,60);
                };
                ctx.drawImage(img,-27,10,60,60);
            }
            //把当前画布返回（调整）到上一个save()状态之前
            ctx.restore();
            //----绘制奖品结束----
        }
    }
    $('body').css("visibility","visible");
}