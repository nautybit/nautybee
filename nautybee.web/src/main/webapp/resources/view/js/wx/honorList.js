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
    windowResized();
    $('body').css("visibility","visible");
    initButtonStatus();
    initEventHandlers();
    //初始化iscroll
    loadScroll();
    $(".headimg").lazyload();
    //微信初始化
    initWx();
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
function initButtonStatus(){
    //注册可点击按钮
    $('.touchable').clickStatus({
        touchEventEnable:gIsTouchDevice
    });
}
function initEventHandlers(){
    $('#guideBtn').on('clickstatus.up',function(){
        window.location.href = window.location.origin + '/nautybee/wx/recommendGuide';
    });
    $('#codeBtn').on('clickstatus.up',function(){
        $('#bigCover').show();
        $('#marketCode').show();
    });
    $('#bigCover').on('clickstatus.up',function(){
        $('#bigCover').hide();
        $('#marketCode').hide();
    });
    $('#marketCode').on('clickstatus.up',function(){
        $('#bigCover').hide();
        $('#marketCode').hide();
    });
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
    var title = "武义小作家“红包风云榜”，兼职10天，收入过万！";
    var descrption = "兼职10天，收入过万，快来加入吧！";
    var imgUrl =window.location.origin + "/nautybee/resources/images/biz/spu/1.jpg";
    var url = window.location.href;

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

