$(function(){
    init();

});
function init(){
    //调整页面显示
    windowResized();
    $('body').css("visibility","visible");
    initButtonStatus();
    initEventHandlers();
}

function getScreenWidth(){
    return document.body.clientWidth;
}
function windowResized(){

    $('.winPic').css({
        'width':680/720*getScreenWidth()+'px',
        'margin-top':24/720*getScreenWidth()+'px',
        'margin-left':20/720*getScreenWidth()+'px'
    });
    $('#wrapper').css({
        'min-height':window.screen.availHeight+'px',
        'padding-bottom':24/720*getScreenWidth()+'px'
    });


}

function initButtonStatus(){

}

function initEventHandlers(){

}
