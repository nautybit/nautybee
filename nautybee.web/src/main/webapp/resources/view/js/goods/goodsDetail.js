var SEARCH = {};
if (window.location.search.split('?')[1]) {
    var search = window.location.search.split('?')[1].split('&');
    for (var i in search) {
        var key = search[i].split('=')[0];
        SEARCH[key] = search[i].split('=')[1];
    }
}
var gViewModel = {};
gViewModel.$main = $('#main');
gViewModel.$wrapper = $('#wrapper');
RequestJSApiConfig = extend ( RequestBase, function(){
    this.__super__.constructor(this);
    this.url ="wx/jssdkConfig?url="+window.location.href;
    this.method = "get";
    this.contentType = "application/json";
});
RequestToWechatPay = extend( RequestBase, function(queryParam)
{
    this.__super__.constructor(this);
    this.url = "pay/auth";
    this.method = "post";
    this.contentType = "application/json";
    this.dataType = "json";
    this.async = true;
    var params = JSON.stringify(queryParam);
    this.params = params;
});

$(function(){
    init();
});
function init(){


    windowResized();
    $('body').css("visibility","visible");
    initEventHandlers();
    //微信初始化
    initWx();
    //轮播插件初始化
    initSlider();
    //初始化iscroll
    loadScroll();

//    var wxOpenId = getCookie("wxOpenId");
//    alert("wxOpenId:"+wxOpenId);


}
function getScreenWidth(){
    return document.body.clientWidth;
}
function windowResized(){
    $('#picWrapper').css({
        'width':getScreenWidth()+'px',
        'height':getScreenWidth()*0.75+'px'
    });
    $('.goodsName').css({
        'width':getScreenWidth()+'px',
        'padding':15/414*getScreenWidth()+'px',
        'background-color':'white'
    });
}
function initSlider() {
    var slider = $('#picWrapper').unslider({
        arrows:false,
        infinite:true,
        fluid:true,
        keys:false
    });
}
function initEventHandlers(){
    alert("initEventHandlers");
    $('#purchaseBtn').on('click',function(){
        alert("purchaseBtn on click");
        var spuId = SEARCH.spuId;
        window.location.href = window.location.origin + '/nautybee/wx/order/confirmOrder?spuId='+spuId;
    });
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
// ************微信相关初始化操作START************
function initWx(){
    wxReady().then(function(data){
//        alert("wx ready");
        handleShareEvent();
    }).fail(
        function(){
            alert("wx failed");
        }
    );
    var net = Net.getInstance();
    var request = new RequestJSApiConfig();
    net.pRequest(request)
        .then(function(data){
            initWxConfig(data);
        })
        .fail(function(data){
            alert("出错啦!_"+data.code+":"+data.errorMsg);
        });
}
function wxReady(){
    var defferd = $.Deferred();
    wx.ready(function(){
        defferd.resolve('OK')
    });
    wx.error(function(res){
        defferd.reject(res);
    });
    return defferd;
}
function initWxConfig(wxConfig){
    var config = $.extend({}, wxConfig , {
        debug:false,
        jsApiList:[
            'onMenuShareTimeline',
            'onMenuShareAppMessage'
        ]
    });
    wx.config(config);
}
function handleShareEvent(){
    var title = "武义小作家辅导中心";
    var descrption = "欢迎您加入";
    var imgUrl =window.location.origin + "/nautybee/resources/images/biz/spu/1.jpg";
    var url = window.location.origin + '/nautybee/wx/toFollowPage';

    var success = function(){
        // 用户确认分享后执行的回调函数
    };

    var failure = function(){
    };
    wx.onMenuShareTimeline({
        title: title, // 分享标题
        desc: descrption, // 分享描述
        link: url, // 分享链接
        imgUrl: imgUrl, // 分享图标
        success: success,
        cancel: failure
    });

    wx.onMenuShareAppMessage({
        title: title, // 分享标题
        desc: descrption, // 分享描述
        link: url, // 分享链接
        imgUrl: imgUrl, // 分享图标
        //type: '', // 分享类型,music、video或link，不填默认为link
        //dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
        success: success,
        cancel: failure
    });
}

