/*
* Filename:net.js
*/

/*
 * class Net
 * */
var Net = (function( env ) {

  var _fromGetInstance = false;
  var _instance = null;

  function _construct( env ) {
    if (_fromGetInstance !== true) {
      throw new Error("must use the getInstance.");
    }
    //Default env
    this.netEnv= {
      baseUrl : window.location.origin + '/nautybee/',
      retryLimit : 3
    };
    //user setted env
    if( env ){
      for ( var i in env ) {
        this.netEnv[i] = env[i];
      }
    }
    _fromGetInstance = false;
  }

  _construct.getInstance = function( env ) {
    if (_instance) {
      return _instance;
    }
    _fromGetInstance = true;
    _instance = new Net( env );
    return _instance;
  };
  return _construct;
})();


Net.prototype.getUrl =function (url, onceBaseUrl){
    var resultUrl;
    resultUrl = this.netEnv.baseUrl + url;
    return resultUrl;
};

Net.prototype.processBizSuccess = function(data){
    return false;
};

Net.prototype.processBizFailure = function(data){
    return false;
};

Net.prototype.getAjaxConfig = function (request,onceBaseUrl){
    var url = this.getUrl(request.url,onceBaseUrl);

    if( !request.dataType){
        request.dataType="json";
    }

    var params = request.params;
    if(
        request.contentType === "application/json"
        && request.method.toLowerCase() === "post"
    ) {
        if ( typeof request.params == "object") {
            params = JSON.stringify(params);
        }
    }
    var ajaxConfig =
    {
        net: this ,
        type: request.method,
        url:  url,
        data: params ,
        dataType : request.dataType,
        contentType : request.contentType,
        tryCount : 0,
        retryLimit : this.netEnv.retryLimit,
        async : request.async == null?true:request.async
    };

    if (typeof request.processData != 'undefined') {
        ajaxConfig.processData =  request.processData;
    }
    return ajaxConfig;
};

Net.prototype.request = function( request , success , failure , onceBaseUrl){
  var url = this.netEnv.baseUrl + request.url;
  if( !request.method )
  {
    request.method = "get";
  }
  if( !request.contentType ){
    //request.contentType = "application/json";
  }
  if( !request.dataType){
    request.dataType="json";
  }
  this._exec( request , success , failure , onceBaseUrl );
};

Net.prototype._exec = function ( request, success , failure , onceBaseUrl )
{
    var ajaxConfig = this.getAjaxConfig(request,onceBaseUrl);
    $.ajax(
        $.extend(ajaxConfig,{
            success:function(data){
                if( success !== null ){
                    success.call( this.net , data );
                }
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
                if (textStatus == 'timeout') {
                    this.tryCount++;
                    if (this.tryCount <= this.retryLimit) {
                        //try again
                        $.ajax(this);
                        return;
                    }
                    var msg = "网络连接错误";
                    if( callback.failure !== null ){
                        failure.call( this.net , "网络连接错误");
                    }
                    this.net.showAlert(msg);
                    return;
                }
                //handle error
                var msg = "网络 "+ XMLHttpRequest.status +" 错误";
                console.log( msg );
                this.net.showAlert(msg);
                if( failure  ){
                    failure.call( this.net , msg );
                }
            }
        })
    );
};

Net.prototype.pRequest = function( request , onceBaseUrl){
    var url = this.netEnv.baseUrl + request.url;
    if( !request.method )
    {
        request.method = "get";
    }
    if( !request.contentType ){
        //request.contentType = "application/json";
    }
    if( !request.dataType){
        request.dataType="json";
    }
    return this._pexec( request , onceBaseUrl );
};

Net.prototype._pexec = function ( request, onceBaseUrl )
{
    var deferred =  $.Deferred();
    var ajaxConfig = this.getAjaxConfig(request,onceBaseUrl);

    $.ajax(ajaxConfig).then(function(data){
        if(data.success){
            var net = Net.getInstance();
            if(net.processBizSuccess(data.data)){
                return;
            }
            deferred.resolve(data.data);
        }else{
            var net = Net.getInstance();
            if( !request.processBizErrorSelf ){
                if( net.processBizFailure(data) ){
                    return;
                }
            }
            deferred.reject(data);
        }
    }).fail(function( XMLHttpRequest, textStatus, errorThrown){
        if (textStatus == 'timeout') {
            this.tryCount++;
            if (this.tryCount <= this.retryLimit) {
                //try again
                $.ajax(this);
                return;
            }
            var msg = "网络连接错误";
            this.net.showAlert(msg);
            return;
        }
        //handle error
        var msg = "网络 "+ XMLHttpRequest.status +" 错误";
        console.log( msg );
        this.net.showAlert(msg);
    });

    return deferred;
};

Net.prototype.submitForm = function ( form , callbacks )
{
    var url = this.netEnv.baseUrl
        + form.attr('action');
    form.ajaxSubmit ({
        net: this ,
        url:url,
        type:'post' ,
        dataType:'json',
        success: function( data )
        {
            if( !callbacks )
            {
                return;
            }
            if( !callbacks.success )
            {
                return;
            }
            callbacks.success.call( this.net , data);
        },
        uploadProgress:function(event,position,total,percentComplete)
        {
            if( !callbacks )
            {
                return;
            }
            if( !callbacks.uploadProgress )
            {
                return;
            }
            callbacks.uploadProgress.call( this.net ,event , position , total , percentComplete );
        },
        error:function()
        {
            if( !callbacks )
            {
                return;
            }
            if( !callbacks.error )
            {
                return;
            }
            callbacks.error.call( this.net );
        },
    });
};

Net.prototype.showAlert = function (msg){
  if (
        typeof(baseShowIndicator) !== "undefined" 
        && $.isFunction(baseShowIndicator)) {
    baseShowIndicator(false);
  }
  if ( 
        typeof(baseShowAlert) !== "undefined" 
        && $.isFunction(baseShowAlert)) {
      baseShowAlert(true,msg);
  }else{
    alert(msg);
  }
};
