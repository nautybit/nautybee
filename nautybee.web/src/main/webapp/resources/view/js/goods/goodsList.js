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

//    if(SEARCH.code){
//        window.location.href = window.location.href.substring(0,window.location.href.indexOf("?code"));
//        return;
//    }

    windowResized();
    $('body').css("visibility","visible")
    initButtonStatus();
    initEventHandlers();
    //初始化iscroll
    loadScroll();
    //微信初始化
    initWx();
}
function getScreenWidth(){
    return document.body.clientWidth;
}
function windowResized(){
    $('.storeBanner').css({
        'width':getScreenWidth()+'px',
        'height':0.4*getScreenWidth()+'px',
        'margin-top':20/640*getScreenWidth()+'px',
        'line-height':0.4*getScreenWidth()+'px',
        'text-align':'center',
        'font-size':'large',
        'background-color':'lightgray'
    })
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
    $('#purchaseBtn').on('clickstatus.up',function(){
        var queryParam = {};
        queryParam.totalFee = 0.01;
        var openid = $('#main').attr("openid");
        alert("openid:"+openid);
        queryParam.wxOpenid = openid;
        doRequest(queryParam);
    });
    $('.goods').on('clickstatus.up',function(){
        window.location.href = window.location.origin + '/nautybee/wx/goods/getSpuDetail'
    })
}
function doRequest(queryParam){
    var request = new RequestToWechatPay(queryParam);
    var net = Net.getInstance();
    var success = function(result){
        if( !result.success ){
            alert("pay fail");
            baseShowModalAlert(
                "danger"
                ,result.errorMsg
            );
            return;
        }else{
            var data = result.data;
            alert("appId:"+data.appId);
            if (typeof WeixinJSBridge == "undefined"){
                console.log("WeixinJSBridge not defined")
                return;
            }
            var invokeParam = {
                appId:data.appId,
                timeStamp:""+data.timeStamp,
                nonceStr:data.nonceStr,
                signType:"MD5",
                paySign:data.sign
            };
            invokeParam.package = "prepay_id="+data.prepayid;
            WeixinJSBridge.invoke(
                "getBrandWCPayRequest",
                invokeParam,
                function(res){
                    console.log("wx pay callback.");
                    console.log("callback res = " + JSON.stringify(res));

                    if(res.err_msg == "get_brand_wcpay_request:cancel" ) {

                        return;
                    }
                    if(res.err_msg == "get_brand_wcpay_request:fail" ) {

                        return;
                    }
                    if(res.err_msg == "get_brand_wcpay_request:ok" ) {

                        return;
                    }
                }
            );
        }
    };
    net.request(request,success);
}
// ************微信相关初始化操作START************
function initWx(){
    wxReady().then(function(data){
        alert("wx ready");
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
        debug:true,
        jsApiList:[
            'onMenuShareTimeline',
            'onMenuShareAppMessage'
        ]
    });
    wx.config(config);
}
function handleShareEvent(){
    var title = "圣诞狂欢购不停";
    var descrption = "";
    var imgUrl = "";
    var url = window.location.href;

    var success = function(){
        // 用户确认分享后执行的回调函数
    };

    var failure = function(){
    };
    wx.onMenuShareTimeline({
        title: title+"："+descrption, // 分享标题
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

