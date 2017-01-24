var gViewModel = {};
gViewModel.interval = 1000;
$(function(){
    $(document).on("bridgeReady",function(){
        init();
});
    NativeCaller.connect();
});
function init(){
    //调整页面显示
    windowResized();
    //判断登陆
    if (!hasLogin) {
        NativeCaller.login();
    }
    //显示页面
    $('body').css("visibility","visible");
    $('#toActBtn').bind( "click" , function(){
        window.location.href = window.location.origin + '/fauna/groupActivity/enterActivity?activityKey='+$(this).attr("activityKey")+'&cityStation='+cityStation+'&type=groupVer13';
    });
    $('#lottery').bind( "click" , function(){
        window.location.href = window.location.origin + '/fauna/lol/turnPlate?userId='+userId+'&lastKey='+lastKey;
    });
    $('#ruleBtn').bind( "click" , function(){
        $('#rulePic').css('display','block');
        $('#bigCover').css({
            'position':'absolute',
            'width':getScreenWidth()+'px',
            'height':$('#wrapper').height()+'px',
            'top':0,
            'left':0,
            'display':'block'
        });
    });
    $('.dsBtn').bind( "click" , function(e){
        if($(this).attr("areac")==area){
            buyMe();
        }else{
            $('#dsTip').css('display','block');
            $('#bigCover').css({
                'position':'absolute',
                'width':getScreenWidth()+'px',
                'height':$('#wrapper').height()+'px',
                'top':0,
                'left':0,
                'display':'block'
            });
        }
    });
    $('#bigCover').bind( "click" , function(){
        if($('#rulePic').css('display')=='block'){
            $('#rulePic').css('display','none');
        }
        if($('#dsTip').css('display')=='block'){
            $('#dsTip').css('display','none');
        }
        $('#bigCover').css('display','none');
    });
    $('#rulePic').bind( "click" , function(){
        $('#rulePic').css('display','none');
        $('#bigCover').css('display','none');
    });
    $('#dsTip').bind( "click" , function(){
        $('#dsTip').css('display','none');
        $('#bigCover').css('display','none');
    });
}
function getScreenWidth(){
    return document.body.clientWidth;
}
function getScreenHeight(){
    return document.body.clientHeight;
}
function windowResized(){
    $('#wrapper').css({
        'width':getScreenWidth()+'px',
        'height':getScreenHeight+'px',
        'position':'absolute',
        'top':0,
        'right':0
    });
    $('#curArea').css({
        'position':'absolute',
        'top':295/720*getScreenWidth()+'px',
        'left':460/720*getScreenWidth()+'px'
    });
    $('#count').css({
        'width':255/720*getScreenWidth()+'px',
        'height':150/720*getScreenWidth()+'px',
        'position':'absolute',
        'top':150/720*getScreenWidth()+'px',
        'left':20/720*getScreenWidth()+'px'
    });
    $('#lottery').css({
        'width':176/720*getScreenWidth()+'px',
        'height':146/720*getScreenWidth()+'px',
        'position':'fixed',
        'bottom':84/720*getScreenWidth()+'px',
        'left':20/720*getScreenWidth()+'px'
    });
    $('.timeDiv').css({
        'width':48/720*getScreenWidth()+'px',
        'height':38/720*getScreenWidth()+'px',
        'position':'absolute',
        'top':77/720*getScreenWidth()+'px'
    });
    $('#hourDiv').css({
        'left':37/720*getScreenWidth()+'px'
    });
    $('#minuteDiv').css({
        'left':108/720*getScreenWidth()+'px'
    });
    $('#secondDiv').css({
        'left':179/720*getScreenWidth()+'px'
    });
    $('#toActBtn').css({
        'width':220/720*getScreenWidth()+'px',
        'height':88/720*getScreenWidth()+'px',
        'position':'absolute',
        'top':195/720*getScreenWidth()+'px',
        'left':470/720*getScreenWidth()+'px'
    });
    $('#ruleBtn').css({
        'width':120/720*getScreenWidth()+'px',
        'height':39/720*getScreenWidth()+'px',
        'position':'absolute',
        'top':1065/720*getScreenWidth()+'px',
        'left':530/720*getScreenWidth()+'px'
    });
    $('.areaInfo').css({
        'width':190/720*getScreenWidth()+'px',
        'height':70/720*getScreenWidth()+'px',
        'position':'absolute'
    });
    $('#HBInfo').css({
        'top':395/720*getScreenWidth()+'px',
        'left':205/720*getScreenWidth()+'px'
    });
    $('#XNInfo').css({
        'top':655/720*getScreenWidth()+'px',
        'left':65/720*getScreenWidth()+'px'
    });
    $('#HDInfo').css({
        'top':688/720*getScreenWidth()+'px',
        'left':430/720*getScreenWidth()+'px'
    });
    $('#HNInfo').css({
        'top':955/720*getScreenWidth()+'px',
        'left':200/720*getScreenWidth()+'px'
    });
    $('.dsBtn').css({
        'width':68/720*getScreenWidth()+'px',
        'height':79/720*getScreenWidth()+'px',
        'position':'absolute'
    });
    $('#dsHB').css({
        'top':390/720*getScreenWidth()+'px',
        'left':400/720*getScreenWidth()+'px'
    });
    $('#dsXN').css({
        'top':650/720*getScreenWidth()+'px',
        'left':260/720*getScreenWidth()+'px'
    });
    $('#dsHD').css({
        'top':685/720*getScreenWidth()+'px',
        'right':30/720*getScreenWidth()+'px'
    });
    $('#dsHN').css({
        'top':955/720*getScreenWidth()+'px',
        'left':395/720*getScreenWidth()+'px'
    });
    $('.flag').css({
        'width':122/720*getScreenWidth()+'px',
        'height':123/720*getScreenWidth()+'px',
        'position':'absolute'
    });
    $('#flagHB').css({
        'top':245/720*getScreenWidth()+'px',
        'left':385/720*getScreenWidth()+'px'
    });
    $('#flagXN').css({
        'top':505/720*getScreenWidth()+'px',
        'left':260/720*getScreenWidth()+'px'
    });
    $('#flagHD').css({
        'top':540/720*getScreenWidth()+'px',
        'left':595/720*getScreenWidth()+'px'
    });
    $('#flagHN').css({
        'top':820/720*getScreenWidth()+'px',
        'left':405/720*getScreenWidth()+'px'
    });
    $('#rulePic').css({
        'width':600/720*getScreenWidth()+'px',
        'height':860/720*getScreenWidth()+'px',
        'position':'absolute',
        'top':170/720*getScreenWidth()+'px',
        'left':60/720*getScreenWidth()+'px'
    });
    $('#dsTip').css({
        'width':290/720*getScreenWidth()+'px',
        'height':128/720*getScreenWidth()+'px',
        'position':'absolute',
        'bottom':576/720*getScreenWidth()+'px',
        'right':215/720*getScreenWidth()+'px'
    });

}
function buyMe(){
    var param = {};
    param.activityId = singleGoodsActId;
    param.activityName = singleGoodsActName;
    param.totalGoodsNumber = 1;
    param.goodsList = [];
    var goodsView = {};
    goodsView.goodsId = buyGoodsId;
    goodsView.goodsNumber = 1;
    param.goodsList.push(goodsView);
    var activityParamStr = JSON.stringify(param);
    NativeCaller.balanceActivityOrder(activityParamStr);
}

function ShowCountDown(year,month,day)
{
    var now = new Date();
    var endDate = new Date(year, month-1, day);
    var leftTime=endDate.getTime()-now.getTime();
    var leftSecond = parseInt(leftTime/1000);
    var hour=Math.floor(leftSecond/3600);
    var minute=Math.floor((leftSecond-hour*3600)/60);
    var second=Math.floor(leftSecond-hour*3600-minute*60);
    hour = prefixZero(hour);
    minute = prefixZero(minute);
    second = prefixZero(second);
    $('#hourDiv').text(hour);
    $('#minuteDiv').text(minute);
    $('#secondDiv').text(second);
}
function prefixZero(num)
{
    var fixedNum;
    if(num/10 < 1){
        fixedNum = "0"+num;
    }else{
        fixedNum = num;
    }
    return fixedNum;
}
if(nextYear != null && nextMonth != null && nextDay != null){
    window.setInterval(function(){ShowCountDown(nextYear,nextMonth,nextDay);}, gViewModel.interval);
}
