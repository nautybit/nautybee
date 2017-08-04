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
    this.url ="wx/jssdkConfig?url="+encodeURIComponent(window.location.href);
    this.method = "get";
    this.contentType = "application/json";
});
$(function(){
    init();
});
function init(){
    initEnvOpenId();
    windowResized();
    $('body').css("visibility","visible");
    //初始化iscroll
    loadScroll();
    //微信初始化
    initWx();
}
function initEnvOpenId(){
    setCookie('wxOpenId', gOpenId, null, '/');
}
function getScreenWidth(){
    return document.body.clientWidth;
}
function windowResized(){
    $('#innerWrapper').css({
        'position':'absolute',
        'width':580/640*getScreenWidth()+'px',
        'top':120/640*getScreenWidth()+'px',
        'left':30/640*getScreenWidth()+'px',
        'border-radius':6/640*getScreenWidth()+'px'
    });
    $('#QRCodeWrapper').css({
        'width':580/640*getScreenWidth()+'px',
        'height':580/640*getScreenWidth()+'px'
    });
    $('#topInfo').css({
        'width':500/640*getScreenWidth()+'px',
        'margin':40/640*getScreenWidth()+'px '+40/640*getScreenWidth()+'px 0 '+40/640*getScreenWidth()+'px '
    });
    $('#headImgWrapper').css({
        'width':120/640*getScreenWidth()+'px',
        'height':120/640*getScreenWidth()+'px'
    });
    $('#recommendSource').css({
        'width':330/640*getScreenWidth()+'px',
        'margin':20/640*getScreenWidth()+'px',
        'font-size':28/640*getScreenWidth()+'px'
    });
    $('#bottomInfo').css({
        'width':500/640*getScreenWidth()+'px',
        'margin':'0 '+40/640*getScreenWidth()+'px '+40/640*getScreenWidth()+'px '+40/640*getScreenWidth()+'px ',
        'font-size':24/640*getScreenWidth()+'px'
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

