/**
 * Request Base Class
 */
function RequestBase( __this__ )
{
  var th = this;
  if( __this__ !== null )
  {
    th = __this__;
  }
  th.method = "get";
  th.dataType = "json";
  th.async = true;
}

function RequestPageBase(__this__ , searchParam , pageData , sort )
{
  var th = this;
  if( __this__ !== null )
  {
    th = __this__;
  }
  th.dataType = "json";
  th.method = "post";
  th.contentType = "application/json";
  th.async=true;
  var pageRequest={};

  pageRequest.page = pageData.page;
  pageRequest.size = pageData.size;
  if( sort 
      && sort.length > 0 ){
     pageRequest.sort = sort;
  }

  if (searchParam) {
    pageRequest.params={};
    for (var i in searchParam) {
      if( searchParam[i] === null ){
        continue;
      }
      pageRequest.params[i] = searchParam[i];
    }
  }
  th.params = JSON.stringify(pageRequest);
}

//RequestLogin = extend( RequestBase, function( mobile,password
//            )
//        {
//            this.__super__.constructor(this);
//            this.url = "user/login";
//            this.method = "post";
//            this.contentType = "application/json";
//            this.dataType='json';
//            var params = {
//                mobilephone:mobile,
//                password:password,
//            };
//
//            this.params = JSON.stringify(params);
//        });

