var gViewModel = {};
gViewModel.$main = $('#main');
gViewModel.$wrapper = $('#wrapper');
//gViewModel.pageScrollEnabled = true;
var gPageScroll;
var gPicScroll;
var curStoreIndex = 0;
$(function(){
    init();
});
function init(){
    //调整页面显示
    windowResized();
    $('body').css("visibility","visible");

    initButtonStatus();
    initEventHandlers();
    _initSpinner();
    //初始化iscroll
    loadScroll();
    document.addEventListener('touchmove', function (e) {
        e.preventDefault();
    }, false);
//    document.addEventListener('touchstart', function (e) {
//        if(!gViewModel.pageScrollEnabled){
//            loadScroll();
//            gViewModel.pageScrollEnabled = true;
//        }
//    }, false);
}
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
        preventDefaultException: { className: /(cameraBtn)|(inputVal)/ },
        click:false
    });
    gPageScroll.on('scrollStart',function(e){
        $(".active").removeClass("active");
        $('.inputVal').blur();
    });
    gPageScroll.on('scroll',function(e){
        gViewModel.$wrapper.trigger("scroll");
    });
    gPageScroll.on('scrollEnd',function(e){
        $(".active").removeClass("active");
        gViewModel.$wrapper.trigger("scroll");
    });

    if(dataList.length > 0){
        gPicScroll = new IScroll('#picScroll', {
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
        gPicScroll.on('scrollStart',function(e){
            $(".active").removeClass("active");
            //iscroll嵌套时，为防止触发外层iscroll，暂时先disable之
            gPageScroll.disable();
        });
        gPicScroll.on('scrollEnd',function(e){
            $(".active").removeClass("active");
            //iscroll嵌套，内层事件处理完后重新使能外层iscroll
            gPageScroll.enable();
        });
    }
    refreshScroll();
}
function refreshScroll(){
    setTimeout(function(){
        gPageScroll.refresh();
    },400);
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
    if(dataList.length > 0){
        $('#bottomBG').css({
            'padding-bottom':58/720*getScreenWidth()+'px'
        });
    }
    $('#infoWrapper').css({
        'padding-top':48/720*getScreenWidth()+'px',
        'padding-bottom':28/720*getScreenWidth()+'px'
    });
    $('#infoHead').css({
        'padding-bottom':36/720*getScreenWidth()+'px'
    });


    $('#infoName').css({
        'line-height':55/720*getScreenWidth()+'px',
        'padding-bottom':48/720*getScreenWidth()+'px'
    });
    $('.infoTip').css({
        'line-height':55/720*getScreenWidth()+'px'
    });
    $('.searchInput').css({
        'height':55/720*getScreenWidth()+'px',
        'line-height':55/720*getScreenWidth()+'px'
    });
    $('input').css({
        'padding':10/720*getScreenWidth()+'px',
        'border-radius':8/720*getScreenWidth()+'px',
        'height':55/720*getScreenWidth()+'px',
        'line-height':55/720*getScreenWidth()+'px'
    });

    $('#infoMobile').css({
        'line-height':55/720*getScreenWidth()+'px',
        'padding-bottom':48/720*getScreenWidth()+'px'
    });
    $('#infoIntro').css({
        'height':140/720*getScreenWidth()+'px',
        'margin-bottom':30/720*getScreenWidth()+'px'
    });
    $('textarea').css({
        'padding':10/720*getScreenWidth()+'px',
        'border-radius':8/720*getScreenWidth()+'px'
    });

    $('#uploadPic').css({
        'height':70/720*getScreenWidth()+'px'
    });
    $('#cameraIco').css({
        'width':70/720*getScreenWidth()+'px',
        'height':70/720*getScreenWidth()+'px'
    });
    $('#cameraBtn').css({
        'width':200/720*getScreenWidth()+'px',
        'height':120/720*getScreenWidth()+'px'
    });
    $('#loadedPic').css({
        'left':90/720*getScreenWidth()+'px',
        'width':70/720*getScreenWidth()+'px',
        'height':70/720*getScreenWidth()+'px'
    });
    $('#submitBtn').css({
        'position':'absolute',
        'left':235/720*getScreenWidth()+'px',
        'top':28/720*getScreenWidth()+'px',
        'width':250/720*getScreenWidth()+'px',
        'height':74/720*getScreenWidth()+'px'
    });
//    $('#enrolledStores').css({
//        'padding-bottom':44/720*getScreenWidth()+'px'
//    });
    $('#picListWrapper').css({
        'width':520*dataList.length/720*getScreenWidth()+'px',
        'height':364/720*getScreenWidth()+'px',
        'margin-top':44/720*getScreenWidth()+'px',
//        'margin-left':36/720*getScreenWidth()+'px',
//        'margin-right':36/720*getScreenWidth()+'px',
        'margin-bottom':18/720*getScreenWidth()+'px'
    });
    $('#btnLeft').css({
        'position':'absolute',
        'left':0,
        'top':117/720*getScreenWidth()+'px',
        'width':36/720*getScreenWidth()+'px',
        'height':65/720*getScreenWidth()+'px'
    });
    $('#btnRight').css({
        'position':'absolute',
        'right':0,
        'top':117/720*getScreenWidth()+'px',
        'width':36/720*getScreenWidth()+'px',
        'height':65/720*getScreenWidth()+'px'
    });
    $('.infoWrapper').css({
        'margin-left':32/720*getScreenWidth()+'px',
        'margin-right':32/720*getScreenWidth()+'px',
        'width':520/720*getScreenWidth()+'px',
        'height':364/720*getScreenWidth()+'px'
    });
    $('.pic').css({
        'width':520/720*getScreenWidth()+'px',
        'height':300/720*getScreenWidth()+'px'
    });
    $('.storeName').css({
        'margin-top':18/720*getScreenWidth()+'px',
        'width':520/720*getScreenWidth()+'px',
        'height':46/720*getScreenWidth()+'px',
        'line-height':46/720*getScreenWidth()+'px'
    });
    $('#successTip').css({
        'position':'absolute',
        'left':100/720*getScreenWidth()+'px',
        'top':300/720*getScreenWidth()+'px',
        'width':520/720*getScreenWidth()+'px'
    });
    $('#goDown').css({
        'left':215/720*getScreenWidth()+'px',
        'top':423/720*getScreenWidth()+'px',
        'width':312/720*getScreenWidth()+'px',
        'height':68/720*getScreenWidth()+'px'
    });
}

function initButtonStatus(){
    gViewModel.$main.clickStatus({
        targetSelector:"#cameraBtn",
        touchEventEnable:gIsTouchDevice
    });
    gViewModel.$main.clickStatus({
        targetSelector:"#submitBtn",
        touchEventEnable:gIsTouchDevice
    });
    gViewModel.$main.clickStatus({
        targetSelector:"#btnLeft",
        touchEventEnable:gIsTouchDevice
    });
    gViewModel.$main.clickStatus({
        targetSelector:"#btnRight",
        touchEventEnable:gIsTouchDevice
    });
    gViewModel.$main.clickStatus({
        targetSelector:"#bigCover",
        touchEventEnable:gIsTouchDevice
    });
    gViewModel.$main.clickStatus({
        targetSelector:"#successTip",
        touchEventEnable:gIsTouchDevice
    });
    gViewModel.$main.clickStatus({
        targetSelector:"#goDown",
        touchEventEnable:gIsTouchDevice
    });

}

function initEventHandlers(){
    $('#cameraBtn').on('click',function(){
        $('#picFile').trigger('click');
    });
//    $('#btnLeft').on('clickstatus.up',function(){
//        var storeLength = dataList.length;
//        if(curStoreIndex == 0){
//            $('#pic').css("background-image","url("+dataList[storeLength-1].applyImg+")");
//            $('#storeName').text(dataList[storeLength-1].applicant);
//            curStoreIndex = storeLength-1;
//        }else{
//            curStoreIndex--;
//            $('#pic').css("background-image","url("+dataList[curStoreIndex].applyImg+")");
//            $('#storeName').text(dataList[curStoreIndex].applicant);
//        }
//        console.log("curStoreIndex"+curStoreIndex);
//    });
//    $('#btnRight').on('clickstatus.up',function(){
//        var storeLength = dataList.length;
//        if(curStoreIndex < storeLength-1){
//            curStoreIndex++;
//            $('#pic').css("background-image","url("+dataList[curStoreIndex].applyImg+")");
//            $('#storeName').text(dataList[curStoreIndex].applicant);
//        }else{
//            $('#pic').css("background-image","url("+dataList[0].applyImg+")");
//            $('#storeName').text(dataList[0].applicant);
//            curStoreIndex = 0;
//        }
//        console.log("curStoreIndex"+curStoreIndex);
//    });
    $('#submitBtn').on('clickstatus.up',function(){
        var param = {};
        param.applicant = $('#infoName').children('.searchInput').children().val();
        param.applyImg = $('#loadedPic').children().attr('src');
        param.contactMobile = $('#infoMobile').children('.searchInput').children().val();
        param.briefDesc = $('.form-control').val();

        if(param.applicant == '' || param.applicant == null || param.contactMobile == '' || param.contactMobile == null){
            alert("请输入必填信息");
            return;
        }
        if(!isValidMobile(param.contactMobile)){
            alert("无效的手机号！");
            return;
        }
        baseShowIndicator(true,"资料上传中...");

        $.ajax({
            url: window.location.origin + '/fauna/enrollment/submitInfo',
            data:JSON.stringify(param),
            dataType: "json",
            type: 'post',
            async: true,
            contentType : 'application/json',
            success: function (result) {
                var res = result;
                if (!res.success) {
                    alert(res.errorMsg);
                }else{
                    $('.inputVal').val('');

                    $('#bigCover').css('display','block');
                    $('#successTip').css('display','block');
                }
                baseShowIndicator(false);
            }
        });
    });
    $('#bigCover').on("clickstatus.up",function(e){
        if($('#successTip').css('display')=='block'){
            $('#successTip').css('display','none');
            $('#bigCover').css('display','none');
        }
    });
    $('#successTip').on("clickstatus.up",function(e){
        $('#successTip').css('display','none');
        $('#bigCover').css('display','none');
    });
    $('#goDown').on("clickstatus.up",function(e){
        var pos = $("#infoWrapper").offset().top - gPageScroll.y - 300/720*getScreenWidth();
        gPageScroll.scrollTo(0, gPageScroll.maxScrollY);
    });
}
function picChanged(obj){
    var files = obj.files;
    var fileReader = new FileReader();
    fileReader.readAsDataURL(files[0]);
    fileReader.onload = function (oFREvent) {
        $('#loadedPic').css('display','block');
        $('#loadedPic').children().attr('src',oFREvent.target.result);
    };
}
function openApp(){
    NativeCaller.showToast("open!");
    var url = window.location.origin+'/fauna/double11/stores';
    window.location = 'fauna-b://webview?url='+url;

    setTimeout(function() {
        window.location = gAppDownloadPage.b;
    }, 5000);
}
function disablePageScroll(){
    gPageScroll.disable();
}
function enablePageScroll(){
    gPageScroll.enable();
}
function readBlobAsDataURL(blob) {
    var a = new FileReader();
    console.log(a.readAsDataURL(blob));
}







//在Canvas上draw
//$("#submitBtn").click(function() {
//    drawPhoto($('#loadedPic').children()[0], 0, 0, 350, 250);
//});
//获取照片的元信息（拍摄方向）
function getPhotoOrientation(img) {
    var orient;
    EXIF.getData(img, function() {
        orient = EXIF.getTag(this, 'Orientation');
    });
    console.log(orient);
    return orient;
}

//绘制照片
function drawPhoto(photo, x, y, w, h) {

    //获取照片的拍摄方向
    var orient = getPhotoOrientation(photo);
    alert("orient2:" + orient);

    var canvas = document.getElementById("canvas");
    if (canvas.getContext) {
        var ctx = canvas.getContext("2d");
        //draw on Canvas
        var img = new Image();
        img.onload = function() {

            var canvas_w = Number(ctx.canvas.width);
            var canvas_h = Number(ctx.canvas.height);

            //判断图片拍摄方向是否旋转了90度
            if (orient == 6) {
                ctx.save(); //保存状态
                ctx.translate(canvas_w / 2, canvas_h / 2); //设置画布上的(0,0)位置，也就是旋转的中心点
                ctx.rotate(90 * Math.PI / 180); //把画布旋转90度
                // 执行Canvas的drawImage语句
                ctx.drawImage(img, Number(y) - canvas_h / 2, Number(x) - canvas_w / 2, h, w); //把图片绘制在画布translate之前的中心点，
                ctx.restore(); //恢复状态
            } else {
                // 执行Canvas的drawImage语句
                ctx.drawImage(img, x, y, w, h);
            }

        };
        img.src = photo.src; // 设置图片源地址
    }
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
