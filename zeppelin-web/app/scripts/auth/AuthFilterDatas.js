'use strict';

angular.module('zeppelinWebApp')

.run(function(RouteFilter, Authentication) {
  
  RouteFilter.register('guest', [ '/' ], function() {
    return !Authentication.exists();
  }, '/');

  RouteFilter.register('user', [ '/notebook/:noteId',
                                 '/notebook/:noteId/paragraph/:paragraphId?',
                                 '/userUpdate' ], function() {
    return Authentication.exists();
  }, '/');

  RouteFilter.register('normal', [], function() {
    return Authentication.isNormal();
  }, '/');

  RouteFilter.register('admin', [ '/interpreter', '/userMgr' ], function() {
    return Authentication.isAdmin();
  }, '/');

});
