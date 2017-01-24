var gViewModel = {};
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
    gViewModel.$wrapper = $('#wrapper');
    gViewModel.$wrapper.clickStatus({
        targetSelector:".goodsBlock",
        touchEventEnable:gIsTouchDevice
    });
    gViewModel.$scrollMenuBar = $('#scrollMenuBar');
    gViewModel.$fixedMenuBar = $('#fixedMenuBar');
    gViewModel.$groupBtn = $('.groupBtn');
    gViewModel.$wrapper.on("clickstatus.up",'.goodsBlock',function(){
        NativeCaller.viewActivityGoodsDetail($(this).find(".goodsInfo").attr("goodsId"));
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
            $('#dataStorage .goodsInfo[goodsid='+goodsId+']').closest('.goodsRow').find('input').val($input.val());
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
            $('#dataStorage .goodsInfo[goodsid='+goodsId+']').closest('.goodsRow').find('input').val($input.val());
            valueDidChangedHanlder();
        }
    });
    gViewModel.objTemplate = $('#wrapper .goodsBlock').last().clone();
    $('.groupBtn').on('click', function () {
        var key = $(this).attr("key");
        mainMenuClick(key);
    });
    _initSpinner();
    mainMenuClick(subActivityList[0].activityKey);
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
            var $goodsInfos = $('#dataStorage .goodsInfo');
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
function valueDidChangedHanlder(value){
    var param = {};
    param.activityId = $('.activityInfo').attr("activityId");
    param.totalGoodsNumber = 0;
    param.goodsList = [];
    var $goodsInfos = $('#dataStorage .goodsInfo');
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
    baseShowIndicator(true,"数据加载中...");
    gViewModel.$groupBtn.removeClass("menuSelected");
    gViewModel.$groupBtn.addClass("menuNormal");
    $('.groupBtn[key='+key+']').removeClass("menuNormal");
    $('.groupBtn[key='+key+']').addClass("menuSelected");
    if($('#dataStorage>div[groupKey='+key+']').length>0){
        $('#wrapper .goodsBlock').remove();
        $('#noGoodsInfo').css("display","none");
        $('#rule').before($('#dataStorage>div[groupKey='+key+']').clone().children('.goodsBlock'));
        menuChanged();
    }else{
        fetchRender(key);
    }
}
function menuChanged(){
    baseShowIndicator(false);
    refreshScroll();
}
function fetchRender(key){
    baseShowIndicator(true,"数据加载中...");
    $.ajax({
        type: 'get',
        url: window.location.origin + '/fauna/groupActivity/queryMoreByKey?activityKey=' + activityKey + '&groupKey=' + key + '&cityStation=' + cityStation,
        async: true,
        dataType: 'json',
        success: function (result) {
            var res = result.result;
            if (!res.success) {
                window.location.href = window.location.href;
            }else{
                //渲染新商品
                var goodsList = res.data;
                var $container = $('<div groupKey="'+key+'"></div>');
                for(var i = 0;i<goodsList.length;i++){
                    var entity = goodsList[i];
                    var groupKey = entity.activityGoodsParamDo.groupKey;
                    var goodsId = entity.goodsView.id;
                    var skuNum = entity.skuNum;
                    var storeId = entity.goodsView.storeId;
                    var goodsPrice = entity.goodsPrice;
                    var basePrice = entity.basePrice;
                    var activityGoodsRule = entity.activityGoodsRule;
                    var priceInCircle = parseFloat(entity.skuNum*entity.goodsPrice).toFixed(2);
                    var img = entity.goodsView.goodsImg+'!S';
                    var goodsName = entity.goodsView.goodsName;
                    var unit = entity.goodsView.unit;
                    var saleUnit = entity.saleUnit;
                    var salePrice = parseFloat(entity.skuNum*entity.goodsView.basePrice).toFixed(2);
                    var totalGoodsNum = entity.activityGoodsParamDo.totalGoodsNum;
                    var minValue = entity.activityGoodsParamDo.activityGoodsNumberCfg.minGoodsNumber;
                    var step = entity.activityGoodsParamDo.activityGoodsNumberCfg.goodsNumberStep;
                    var maxValue = entity.activityGoodsParamDo.activityGoodsNumberCfg.maxGoodsNumber;
                    var storeName = entity.goodsView.storeName;
                    var soldOut = entity.soldOut;

                    var obj = gViewModel.objTemplate.clone();
                    $(obj).css("visibility","visible");
                    $(obj).attr("groupKey",groupKey);
                    $(obj).find('.goodsInfo').attr("goodsId",goodsId);
                    $(obj).find('.goodsInfo').attr("skuNum",skuNum);
                    $(obj).find('.goodsInfo').attr("storeId",storeId);
                    $(obj).find('.goodsInfo').attr("goodsPrice",goodsPrice);
                    $(obj).find('.goodsInfo').attr("basePrice",basePrice);
                    $(obj).find('.goodsInfo').attr("activityGoodsRule",activityGoodsRule);
                    $(obj).find('.priceInCircle').text(priceInCircle);
                    $(obj).find('.img-goods').attr("data-original",img);
                    $(obj).find('.goodsname').text(goodsName);
                    $(obj).find('.renderSkuNum').text(skuNum);
                    $(obj).find('.renderUnit').text(unit);
                    $(obj).find('.renderSaleUnit').text(saleUnit);
                    $(obj).find('.salePrice').text(salePrice);
                    $(obj).find('.renderTotalGoodsNum').text(totalGoodsNum);
                    $(obj).find('.inputGroup').attr("minValue",minValue);
                    $(obj).find('.inputGroup').attr("step",step);
                    $(obj).find('.inputGroup').attr("maxValue",maxValue);
                    $(obj).find('.numInputBorder').val(0);
                    if(soldOut == "Y"){
                        $(obj).find('.outStockPic').css("display","block");
                        $(obj).find('.inputGroup').css("display","none");
                    }else{
                        $(obj).find('.outStockPic').css("display","none");
                        $(obj).find('.inputGroup').css("display","block");
                    }
                    $container.append(obj);
                }
                $('#wrapper .goodsBlock').remove();
                $('#rule').before($container.clone().children('.goodsBlock'));
                $('#dataStorage>div[groupKey='+key+']').remove();
                $('#dataStorage').append($container);
                menuChanged();
                baseShowIndicator(false);
            }
        }
    });
}
function getScreenWidth(){
    return document.body.clientWidth;
}
function windowResized(){
    var leftPartWidth = (getScreenWidth() - 48)*0.35;
    var leftPartHeight = leftPartWidth;
    $('div.img_wrapper').css({
        'height':leftPartHeight+'px',
        'width':leftPartWidth+'px'
    });
    var groupNum = subActivityList.length;
    if(groupNum < 5){
        $('.menuWrapper').css({
            'width':'100%'
        });
        $('.menuWrapper').children().css({
            'width':parseFloat(100/groupNum)+'%'
        });
    }else{
        $('.menuWrapper').css({
            'width':100*groupNum+'px'
        });
        $('.menuWrapper').children().css({
            'width':'100px'
        });
    }
    $('#fixedMenuBar').css({
        'line-height':80/720*getScreenWidth()+'px'
    });
    $('#scrollMenuBar').css({
        'line-height':80/720*getScreenWidth()+'px'
    });
    $('.menuWrapper').css({
        'height':80/720*getScreenWidth()+'px'
    });
    $('.outStockPic').css({
        'height':80/320*getScreenWidth()+'px'
    });
    $('.bgColor').css({
        'background-color':bgColor
    });
    $('.menuSlide').css({
        'width':20/720*getScreenWidth()+'px',
        'height':32/720*getScreenWidth()+'px',
        'position':'absolute',
        'top':24/720*getScreenWidth()+'px',
        'right':5/720*getScreenWidth()+'px',
        'line-height':1
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
    });
    fixedMenuScroll.on('scrollEnd',function(e){
        var val = this.x;
        alterMenuScroll.scrollTo(val, 0);
    });
    alterMenuScroll.on('scrollEnd',function(e){
        var val = this.x;
        fixedMenuScroll.scrollTo(val, 0);
    });
    refreshScroll();
}
function refreshScroll(){
    setTimeout(function(){
        gPageScroll.refresh();
        if( gViewModel.$scrollMenuBar.get(0).offsetTop+gPageScroll.y > 0){
            gViewModel.$fixedMenuBar.css("display","none");
        }
        $("#wrapper .img-goods").lazyload();
    },200);
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