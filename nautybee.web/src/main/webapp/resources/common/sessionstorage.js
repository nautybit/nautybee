/*
 *	セッションストレージ管理クラス
 * */
var SessionStorageManager = (function( mainKey ) {

	var _fromGetInstance = false;
	var _instance = null;

	function _construct( mainKey ) {
	    if (_fromGetInstance !== true) {
	        throw new Error("must use the getInstance.");
	    }
		this.mainKey = mainKey;
	    _fromGetInstance = false;
	}

	_construct.getInstance = function( mainKey ) {
	    if (_instance) {
	        return _instance;
	    }
	    _fromGetInstance = true;
	    return _instance = new SessionStorageManager( mainKey );
	};
	return _construct;
})();

SessionStorageManager.prototype.write = function( key_value_pair , type )
{
	var key = this.mainKey + "_" + key_value_pair.key;
	var value = key_value_pair.value;
	if( type == "json" )
	{
		value = JSON.stringify( key_value_pair.value );
	}
	sessionStorage.setItem( key , value );
};

SessionStorageManager.prototype.read = function( key , type )
{
	key = this.mainKey + "_" + key;
	var value = sessionStorage.getItem( key );
	if( type == "json" )
	{
		value = JSON.parse( value );
	}
	return value;
};

SessionStorageManager.prototype.remove= function( key )
{
	key = this.mainKey + "_" + key;
	sessionStorage.removeItem( key );
};
SessionStorageManager.prototype.clear= function()
{
    sessionStorage.clear();
};
