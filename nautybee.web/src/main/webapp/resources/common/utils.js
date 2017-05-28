var gAppDownloadPage = {
    b:"http://www.sqmall.com/dd"
}
function extend(s, c) {
    function f() {
    }

    f.prototype = s.prototype;
    c.prototype = new f();
    c.prototype.__super__ = s.prototype;
    c.prototype.__super__.constructor = s;
    c.prototype.constructor = c;
    return c;
}

function gGetDevice() {
    var userAgent = navigator.userAgent || navigator.vendor || window.opera;

    if( userAgent.match( /iPad/ig ) || userAgent.match( /iPhone/ig ) || userAgent.match( /iPod/ig ) )
    {
        return 'ios';
    }
    else if( userAgent.match( /Android/i ) )
    {
        return 'android';
    }
    else
    {
        return 'unknown';
    }
}
    var gDevice = gGetDevice();

var gIsTouchDevice = ( gDevice =='ios' || gDevice == 'android');
var gMyDown = gIsTouchDevice ? "touchstart" : "mousedown";
var gMyUp = gIsTouchDevice ? "touchend" : "mouseup";
var gMyMove = gIsTouchDevice ? "touchmove" : "mousemove";
var gMyOver = gIsTouchDevice ? "" : "mouseover";


var gDownEvent = gIsTouchDevice ? "touchstart" : "mousedown";
var gUpEvent = gIsTouchDevice ? "touchend" : "mouseup";
var gMoveEvent = gIsTouchDevice ? "touchmove" : "mousemove";
var gOverEvent = gIsTouchDevice ? "" : "mouseover";
var gCancelEvent = gIsTouchDevice ? "touchcancel" : "mouseleave";


var gIsIpad = ((/ipad/gi).test(navigator.userAgent));
var gIsIphone = ((/iphone/gi).test(navigator.userAgent));
var gIsTablet = ((/ipad|android/gi).test(navigator.userAgent) );
var gIsAndroid = ((/android/gi).test(navigator.userAgent) );


function gIsWechat(){
    var ua = navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i)=="micromessenger") {
        return true;
    }
    else if( (/qq/gi).test(ua) ) {
        return true;
    }
}
var gIsAndroid = ((/android/gi).test(navigator.userAgent) );

//写cookies
function setCookie(name, value, day , path , domain, secure)
{
    if(
        typeof($.cookie) !== "undefined"
    ){
        $.cookie(name,value,{
            expires:7,
            path:path,
            domain:domain,
            secure:secure
        });

        return;
    }
    var exp = null;
    if(day){
        exp = new Date();
        exp.setTime( exp.getTime() + day * 24 * 60 * 60 * 1000 );
    }
    var cookieStr = [
        name , '=', encodeURIComponent(value),
        exp ? '; expires=' + exp.toUTCString() : '', // use expires attribute, max-age is not supported by IE
        path    ? '; path=' + path : '',
        domain  ? '; domain=' + domain : '',
        secure  ? '; secure' : ''
    ].join('');
    document.cookie =  cookieStr;
}

//读取cookies
function getCookie(name) {
    if(
        typeof($.cookie) !== "undefined"
    ){
        return $.cookie(name);
    }
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    arr = document.cookie.match(reg);
    if (arr) {
        return decodeURIComponent(arr[2]);
    }
    else {
        return null;
    }
}
//删除cookies
function delCookie( name, path, domain ) {
    if(
        typeof($.removeCookie) !== "undefined"
    ){
        $.removeCookie(name,{path:path,domain:domain});
        return;
    }
    setCookie(name,"",path,-1,domain);
}
function isValidMobile(mobile){
    var isValidMobile =  (/^(13[0-9]|15[0-9]|18[0-9]|14[5|7]|17[6-8])\d{8}$/gi).test(mobile);
    return isValidMobile;
}