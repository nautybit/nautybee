/*
 * jquery.clickstatus
 *
 * ・擬似クラス :hover, :active の挙動を JavaScriptで再現する
 * ・disabled状態の場合に :hover, :active を無効にする
 *
 * ブラウザによって擬似クラスが正常に動作しない場合や、タッチ端末での :hover の動作を無効にしたい場合に使用する
 */

(function($){
    $.fn.clickStatus = function(options) {

        options = $.extend({
            targetSelector		: null,
            cancelBubble		: false,
        }, options);

        var startEvent	= gDownEvent;
        var endEvent	= gUpEvent;
        var cancelEvent = gCancelEvent;
        /*
         * ハンドラ設定
         */
        this.each(function() {
            var $this	= $(this);
            var $target	= null;

            var targetSelector		= options.targetSelector;
            var cancelBubble		= options.cancelBubble;

            // クリック領域をドラッグできないようにする
            $this.on('dragstart', targetSelector, function(e) {
                e.preventDefault();
            });

            $this.on('mouseenter', targetSelector, function() {
                if ($(this).hasClass('disabled') || $target != null) {
                    return;
                }
                $(this).addClass('hover');
            }).on('mouseleave', targetSelector, function() {
                if (!$(this).hasClass('hover')) {
                    return;
                }
                $(this).removeClass('hover');
            });

            $this.on(startEvent, targetSelector, function(e) {
                if ($(this).hasClass('disabled') || $target != null) {
                    return;
                }
                $target = $(this);
                $target.addClass('active');

                if (cancelBubble == true) {
                    e.stopPropagation();
                }
            });
            //$this.on( endEvent, targetSelector, function(e) {
            //	$target = $(this);
            //
            //	if(isActive){
            //		$target.trigger($.Event('clickstatus.up'));
            //	}
            //});

            $this.bind(endEvent, function(e) {
                if ($target != null) {
                    var isActive = $target.hasClass("active");
                    if(isActive){
                        $target.removeClass('active');
                        $target.trigger($.Event('clickstatus.up'));
                    }
                    $target = null;
                }
                if (cancelBubble == true) {
                    e.stopPropagation();
                }
            });
            $this.bind(cancelEvent, function(e) {
                if ($target != null) {
                    $target.removeClass('active');
                }
            });
        });

        return this;
    };
})( window.Zepto || window.jQuery );