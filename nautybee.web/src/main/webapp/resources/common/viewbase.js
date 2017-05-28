/*
 * Filename:viewbase.js
 */

var gViewModel = {};

gViewModel.sortOrders = [
    {id: "none", direction: "NONE"},
    {id: "desc", direction: "DESC"},
    {id: "asc", direction: "ASC"},
];

$(function () {
    _initMenu();
    //  _initHeader();
    _initPager();
    //_initSpinner();
    _launchView();
    _initModalAlert();

    //  _initModalAlert();
    //  _getLogoutBaseUrl();// _loadUserInfo();

});

var gModalAlert = null;

function _launchView() {
    if(typeof( $.fn.placeholder ) !== 'undefined'){
        $('input').placeholder();
        $('textarea').placeholder();
    }
    if (
        typeof(initView) !== "undefined"
        && $.isFunction(initView)) {
        initView();
    }
    _setCommonHandlers();
    if (
        typeof(setViewHandlers) !== "undefined"
        && $.isFunction(setViewHandlers)) {
        setViewHandlers();
    }
}

function _initModalAlert() {

    if (
        typeof(ModalAlert) !== "undefined"
        && $.isFunction(ModalAlert)) {
        gViewModel['modalAlert'] = new ModalAlert({
            id: "modalAlert"
        });
    }
}

function _initMenu() {
    $('#sideMenu').on('click', 'div.menu-group-item', function (e) {
        if ($(this).parent().hasClass('expand')) {
            $(this).parent().removeClass('expand');
        } else {
            $(this).parent().addClass('expand');
        }
    });
    $('#sideMenu').on('click', 'div.menu-group-proxy-item', function (e) {
        if ($(this).parent().hasClass('expand')) {
            $(this).parent().removeClass('expand');
        } else {
            $(this).parent().addClass('expand');
        }
    });
    //var url =  document.URL;
    //var urls = url.match(/http(.?):\/\/(.+?)\/(.*)\?(.*)/);
    //var path = '/'+urls[3];

    var $a = $('#sideMenu a[href="' + window.location.pathname + '"]');
    $a.closest('li').addClass('on');

}

function _initPager() {
    if (
        typeof(Pager) !== "undefined"
        && $.isFunction(Pager)) {

        var vhp= null;
        if (
            typeof(viewHandleGotoPage) !== "undefined"
            && $.isFunction(viewHandleGotoPage)) {
            vhp = viewHandleGotoPage;
        }

        var pager = new Pager(
            {
                id: "pageContainer"
                ,handleGotoPage:vhp
            }
        );
        gViewModel.pager = pager;
    }
}

//function _initHeader(){
//    $('header#top .nav > li.dropdown').mouseenter(function () {
//        $(this).children('.dropdown-menu').stop().show(300);
//    });
//    $('header#top .nav > li.dropdown').mouseleave(function () {
//        $(this).children('.dropdown-menu').stop().hide(100);
//    });
//}

//function _loadUserInfo() {
/*
 var net = Net.getInstance();
 var request = new RequestGetUserInfo();
 var success = function(result) {
 if (result.success) {
 console.log(result);
 if (result.data.url) {
 _setLogoutHandler(result.data.url);
 }
 } else {
 showModelAlert('danger', result.errorMsg);
 }
 };
 net.request(request, success);
 */
//}
//function _getLogoutBaseUrl(){
//    var net = Net.getInstance();
//    var request = new RequestLogoutBaseUrl();
//    var success = function(result) {
//        if (result.success) {
//            if (result.data) {
//                _setLogoutHandler(result.data);
//            }
//        } else {
//            showModelAlert('danger', result.errorMsg);
//        }
//    };
//    net.request(request, success);
//
//}

//function _setLogoutHandler(url) {
//    $('.log-out').bind('click', function() {
//        var net = Net.getInstance();
//        var request = new RequestLogout();
//        var success = function(result) {
//            console.log(result);
//            window.location.href = this.netEnv.baseUrl + 'logout';
//        };
//        net.request(request, success, null, url);
//    });
//}


function _setCommonHandlers() {
}

/*
 state: success,info,warning, danger
 message :
 button: {
 success:{
 name:"ok",
 callback:function,
 },
 default:{
 name:"cancel",
 callback:function,
 }
 }
 */
function baseShowModalAlert( state, message, button , closeCallback , closeAfterTimeout ) {
    var modalAlert = gViewModel['modalAlert'];
    if (!modalAlert) {
        return;
    }
    modalAlert.show(state, message, button , closeCallback , closeAfterTimeout );
}

//function baseSetNextSortOrder($th)
//{
//    var curDirection = $th.attr('direction');
//    var orderObjs = gViewModel.sortOrders;
//    var curOrderIndex=0;
//    for( var i = 0 ; i< orderObjs.length ; i++,curOrderIndex++){
//        var orderObj = orderObjs[i];
//        if( orderObj.direction == curDirection ){
//            break;
//        }
//    }
//    var nextOrderIndex = (curOrderIndex+1) % orderObjs.length;
//    var nextDirection = orderObjs[nextOrderIndex].direction;
//    $th.closest('table').find('th.sortable').attr('direction','NONE');
//    $th.attr('direction',nextDirection);
//}
//
//function baseGetSortData($tableHead){
//    var sort =[];
//    var sortableThs = $tableHead.find('th.sortable');
//    for(var i=0 ;i<sortableThs.length;i++){
//        var $th = $(sortableThs[i]);
//        var direction = $th.attr('direction');
//        if( direction && direction !=="NONE"){
//            var order = {};
//            var property = $th.attr('property');
//            order.direction = direction;
//            order.property = property;
//            sort.push( order );
//            break;
//        }
//    }
//    if( sort.length=== 0){
//        sort = null;
//    }
//    return sort;
//}
//
//function _initSpinner()
//{
//    var opts = {
//        lines: 10, // The number of lines to draw
//        length: 10, // The length of each line
//        width: 4, // The line thickness
//        radius: 10, // The radius of the inner circle
//        corners: 1, // Corner roundness (0..1)
//        rotate: 0, // The rotation offset
//        direction: 1, // 1: clockwise, -1: counterclockwise
//        color: '#FFF', // #rgb or #rrggbb or array of colors
//        speed: 1, // Rounds per second
//        trail: 60, // Afterglow percentage
//        shadow: false, // Whether to render a shadow
//        hwaccel: false, // Whether to use hardware acceleration
//        className: 'spinner', // The CSS class to assign to the spinner
//        zIndex: 2e9, // The z-index (defaults to 2000000000)
//        top: '50%', // Top position relative to parent
//        left: '50%' // Left position relative to parent
//    };
//    var $indicator=  $('#indicator');
//    if( $indicator.length === 0 )
//    {
//        var html = '<div id="indicator" class="hide">'
//            + '<div class="wrapper">'
//            + '<div class="spinner"></div>'
//            + '<p class="message">数据加载中，请稍等...</p>'
//            + '</div>'
//            + '</div>';
//        $('#main').append(html);
//        var $indicator =  $('#indicator');
//    }
//    var target = $('#indicator .spinner').get(0);
//    new Spinner(opts).spin(target);
//}
//
//function baseShowIndicator( isShow , message ){
//    var $indicator=  $('#indicator');
//    var msg= "";
//    if( message ){
//        msg = message;
//    }
//    $indicator.find('.message').text(msg);
//    if ( isShow ){
//        $indicator.removeClass('hide');
//    }else  {
//        $indicator.addClass('hide');
//    }
//}
//
//function _initAlertView(){
//    var html = '<div id="alertView" class="hide">'+
//            '<div class="alertWrap">'+
//                '<div class="alertMsgWrap">'+
//                    '<p id="alertMsg"></p>'+
//                '</div>'+
//                '<div class="alertBtnWrap">'+
//                    '<p class="alertBtnConfirm">确认</p>'+
//                '</div>'+
//            '</div>'+
//        '</div>';
//    $('#main').append(html);
//
//    var confirmBtn = new Button({
//        selector:'#alertView .alertBtnConfirm',
//        onClicked:function(){
//            baseShowAlert(false);
//        }
//    });
//}
//
//function baseShowAlert( isShow , message ){
//    var $alertView=  $('#alertView');
//    var msg= "";
//    if( message ){
//        msg = message;
//    }
//    $alertView.find('#alertMsg').text(msg);
//    if ( isShow ){
//        $alertView.removeClass('hide');
//    }else  {
//        $alertView.addClass('hide');
//    }
//}
// function doSearch(extParam, openNew) {
//     doSearch(false,extParam,openNew);
// }
function doSearch(extParam, openNew,isClearGetParam) {
    var $ctrls = $('.search-param');
    if ($ctrls.length === 0) {
        return;
    }
    $ctrls.each(function (idx, el) {
        var value = $(this).val();
        var key = $(this).attr('cookie-key');
        if (key) {
            if (value) {
                setCookie(key, value);
            } else {
                delCookie(key);
            }
        }
    });
    var url = window.location.pathname;
    if(!isClearGetParam){
        var urlParams = getGetParams();
        urlParams.page = null;
        if(extParam){
            $.extend(urlParams, extParam);
        }
        url = combineUrl(window.location.pathname, urlParams);
    }

    if(openNew){
        window.open(url);
    }else {
        window.location.href = url;
    }
}

function doClearSearchParams(){
    var $ctrls = $('.search-param');
    if ($ctrls.length === 0) {
        return;
    }
    $ctrls.each(function (idx, el) {
        $(this).val('');
        var key = $(this).attr('cookie-key');
        if (key) {
            delCookie(key);
        }
    });
}


function baseBrowserNotSupport(){
    $('#browser-no-support').removeClass('hide');
}

function baseRemoveAllCookies(){
    if($.prompt){
        $.prompt("确认清除缓存吗？", {
            buttons: { "确认": true, "取消": false },
            submit: function(e,v,m,f){
                if(v){
                    deleteAllCookies();
                    location.reload();
                }
            }
        });
    }else{
        deleteAllCookies();
        location.reload();
    }
}



