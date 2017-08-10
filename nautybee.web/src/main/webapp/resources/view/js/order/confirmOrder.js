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
RequestCreateOrder = extend( RequestBase, function(queryParam)
{
    this.__super__.constructor(this);
    this.url = "wx/order/createOrder";
    this.method = "post";
    this.contentType = "application/json";
    this.dataType = "json";
    this.async = true;
    var params = JSON.stringify(queryParam);
    this.params = params;
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
    //填写属性值
    fillAttribute();
    //隐藏在校信息
//    $('.school-info').hide();
    $('body').css("visibility","visible");
    //输入框初始化
    $("input").focus(function(){
        if(/Android [4-6]/.test(navigator.appVersion)) {
            window.addEventListener("resize", function() {
                if(document.activeElement.tagName=="INPUT" || document.activeElement.tagName=="TEXTAREA") {
                    window.setTimeout(function() {
                        document.activeElement.scrollIntoViewIfNeeded();
                    },0);
                }
            })
        }
    });
    initEventHandlers();
    //微信初始化
    initWx();
}
function getScreenWidth(){
    return document.body.clientWidth;
}
function windowResized(){

}
function initEventHandlers(){
    $('#purchaseBtn').on('click',function(){
        //关闭手机键盘
        document.activeElement.blur();
        //构造参数
        var queryParam = {};
        var goodsId;
        if(gPropLength == 1){
            goodsId = $('.weui-select[name="select"]').val();
        }else{
            goodsId = $('.weui-select[name="select2"]').val();
        }
        var studentName = $('#studentName').val();
        if(studentName == null || studentName == ''){
            showTip("请填写姓名");
            return;
        }
        var contactMobile = $('#contactMobile').val();
        if(studentName == null || studentName == ''){
            showTip("请填写手机号");
            return;
        }else if(!isValidMobile(contactMobile)){
            showTip("请填写正确的手机号");
            return;
        }

        queryParam.chainHeadId = gChainHeadId;
        queryParam.goodsId = goodsId;
        queryParam.storeId = gStoreId;
        queryParam.studentName = studentName;
        queryParam.contactMobile = contactMobile;
//        if($('#isAtSchoolSwitch').prop("checked")){
            queryParam.isAtSchool = 0;
//        }
        queryParam.schoolName = $('#schoolName').val();
        queryParam.schoolGrade = $('#schoolGrade').val();
        queryParam.schoolClass = $('#schoolClass').val();
        queryParam.teacherName = $('#teacherName').val();
        queryParam.teacherMobile = $('#teacherMobile').val();

        queryParam.totalFee = gGoodsPrice;
        queryParam.wxOpenid = getCookie("wxOpenId");

        createOrder(queryParam);
    });
}


function showTip(text){
    var $tooltips = $('.js_tooltips');
    if ($tooltips.css('display') != 'none') return;
    $tooltips.text(text);
    $tooltips.css('display', 'block');
    setTimeout(function () {
        $tooltips.css('display', 'none');
        $tooltips.text('');
    }, 2000);
}


function fillAttribute(){
    var goodsAttributeViewList = [];
    if(gPropLength == 1){
        var goodsId = $('.weui-select[name="select"]').val();
        for(var i=0;i<gGoodsViewList.length;i++){
            if(gGoodsViewList[i].id == goodsId){
                goodsAttributeViewList = gGoodsViewList[i].goodsAttributeViewList;
                break;
            }
        }
    }else{
        var mainDetailName = $('.weui-select[name="select1"]').find("option:selected").text();
        var goodsId = $('.weui-select[name="select2"]').val();
        var goodsViewList = gMainDetailGoodsListMap[mainDetailName];
        for(var i=0;i<goodsViewList.length;i++){
            if(goodsViewList[i].id == goodsId){
                goodsAttributeViewList = goodsViewList[i].goodsAttributeViewList;
                break;
            }
        }
    }
    if(goodsAttributeViewList.length > 0){
        var attrHtmlStr = '';
        for(var i=0;i<goodsAttributeViewList.length;i++){
            var entity = goodsAttributeViewList[i];
            attrHtmlStr += '<p>'+entity.attrName+'：'+entity.attrValue+'</p>'
        }
        $('#attributes').html(attrHtmlStr);
    }
}
function changeDetail1(){
    var mainDetailName = $('.weui-select[name="select1"]').find("option:selected").text();
    var goodsViewList = gMainDetailGoodsListMap[mainDetailName];
    var appendStr = '';
    for(var i=0;i<goodsViewList.length;i++){
        var goodsView = goodsViewList[i];
        if(goodsView.goodsNum < 1){
            appendStr += '<option value="'+goodsView.id+'" disabled="true">'+goodsView.detailName2+'</option>'
        }else{
            appendStr += '<option value="'+goodsView.id+'">'+goodsView.detailName2+'</option>'
        }
    }
    $('.weui-select[name="select2"]').html(appendStr);
    fillAttribute();
}
function switchSchoolInfo(obj){
    if($(obj).prop("checked")){
        $('.school-info').show();
    }else{
        $('.school-info').hide();
    }
}

function waitPayCountDown(){
    $('#loadingToast').show();
    var countDownVal = 5;
    var timer = setInterval(function(){
        $('#loadingToast').find('.weui-toast__content').text(countDownVal--);
        if($('#loadingToast').find('.weui-toast__content').text()==-1){
            clearInterval(timer);
            $('#loadingToast').hide();
            window.location.href = window.location.origin + '/nautybee/wx/order/orderList?openid=' + getCookie("wxOpenId");
        }
    },1000);
}

function createOrder(queryParam){
    $('#loadingToast').show();
    var request = new RequestCreateOrder(queryParam);
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
            var queryParam = {};
            queryParam.totalFee = gGoodsPrice;
            queryParam.wxOpenid = getCookie("wxOpenId");
            queryParam.goodsId = data.goodsId;
            queryParam.goodsName = $('#goodsName').text();
            queryParam.tradeNo = data.orderSn;
            var orderIdList = [];
            orderIdList.push(data.orderId);
            queryParam.orderList = orderIdList;
            doRequest(queryParam);
        }
    };
    net.request(request,success);
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
//            alert("appId:"+data.appId);
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
                        alert("支付失败");
                        $('#loadingToast').hide();
                        return;
                    }
                    if(res.err_msg == "get_brand_wcpay_request:ok" ) {
                        waitPayCountDown();
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

