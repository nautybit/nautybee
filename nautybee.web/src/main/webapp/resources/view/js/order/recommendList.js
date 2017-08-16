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
gViewModel.currentOrderId = null;
gViewModel.currentRecommendId = null;

RequestJSApiConfig = extend ( RequestBase, function(){
    this.__super__.constructor(this);
    this.url ="wx/jssdkConfig?url="+encodeURIComponent(window.location.href);
    this.method = "get";
    this.contentType = "application/json";
});
RequestQueryMore = extend ( RequestBase, function(queryParam){
    this.__super__.constructor(this);
    this.url ="wx/order/moreRecommendList";
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
    initEnvOpenId();
    windowResized();
    //初始化iscroll
    loadScroll();
    document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
    initEventHandlers();
    $('.good-one-btn').trigger('click');
    $('body').css("visibility","visible");
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

}
function initEventHandlers(){
    $('.good-one-btn').on('click',function(){
        $('.good-one-btn').addClass('weui-bar__item_on');
        $('.bad-one-btn').removeClass('weui-bar__item_on');
        if(gViewModel.currentOrderId == null){
            queryMore(1,"");
        }else{
            $('.good-one').show();
            $('.bad-one').hide();
            refreshScroll("toTop");
        }
    });
    $('.bad-one-btn').on('click',function(){
        $('.good-one-btn').removeClass('weui-bar__item_on');
        $('.bad-one-btn').addClass('weui-bar__item_on');
        if(gViewModel.currentRecommendId == null){
            queryMore(0,"toTop");
        }else{
            $('.good-one').hide();
            $('.bad-one').show();
            refreshScroll("toTop");
        }
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
        if($('#wrapper').height()-this.y == $('#scroller').height()){
            console.log("give me more");
            if($('.good-one-btn').hasClass('weui-bar__item_on')){
                queryMore(1,"");
            }else{
                queryMore(0,"");
            }
        }
    });
//    refreshScroll();
}
function refreshScroll(needToTop){
    setTimeout(function(){
        gViewModel.gPageScroll.refresh();
        if(needToTop == "toTop"){
            gViewModel.gPageScroll.scrollTo(0,0);
        }
    },200);
}
function queryMore(beDeal,needToTop) {
    var param = {};
    param.openid = gOpenId;
    param.beDeal = beDeal;
    param.currentOrderId = gViewModel.currentOrderId;
    param.currentRecommendId = gViewModel.currentRecommendId;
    var request = new RequestQueryMore(param);
    var net = Net.getInstance();
    var success = function(result){
        if( !result.success ){
            alert("create order fail");
            baseShowModalAlert(
                "danger"
                ,result.errorMsg
            );
            return;
        }else{
            var data = result.data;
            if(beDeal == 1){
                for(var i=0;i<data.length;i++){
                    var obj = data[i];
                    var appendObj = $('.good-one-template').clone();
                    appendObj.removeClass('good-one-template');
                    appendObj.addClass('good-one');
                    appendObj.find('.toUserName').text(obj.toUserName);
                    appendObj.find('.gmtCreateStr').text(obj.gmtCreateStr);
                    appendObj.find('.orderGmtCreateStr').text(obj.orderGmtCreateStr);
                    appendObj.find('.goodsName').text(obj.goodsName);
                    $('#listWrapper').append(appendObj);
                }
                if(data.length > 0){
                    gViewModel.currentOrderId = data[data.length -1].orderId;
                }
                $('.good-one').show();
                $('.bad-one').hide();
                refreshScroll(needToTop);
            }else{
                for(var i=0;i<data.length;i++){
                    var obj = data[i];
                    var appendObj = $('.bad-one-template').clone();
                    appendObj.removeClass('bad-one-template');
                    appendObj.addClass('bad-one');
                    appendObj.find('.toUserName').text(obj.toUserName);
                    appendObj.find('.gmtCreateStr').text(obj.gmtCreateStr);
                    $('#listWrapper').append(appendObj);
                }
                if(data.length > 0){
                    gViewModel.currentRecommendId = data[data.length -1].id;
                }
                $('.good-one').hide();
                $('.bad-one').show();
                refreshScroll(needToTop);
            }
        }
    };
    net.request(request,success);
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

