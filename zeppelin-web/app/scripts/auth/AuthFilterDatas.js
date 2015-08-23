'use strict';

angular.module('zeppelinWebApp')

.run(function(RouteFilter, Authentication) {

  RouteFilter.register('guest', [ '/' ], function() {
    return !Authentication.exists();
  }, '/');

  RouteFilter.register('user', [ '/workspace/:workspaceId',
                                 '/notebook/:noteId',
                                 '/notebook/:noteId/paragraph/:paragraphId?',
                                 '/userUpdate' ], function() {
    return Authentication.exists();
  }, '/');

  RouteFilter.register('normal', [], function() {
    return Authentication.isNormal();
  }, '/');

  RouteFilter.register('admin', [ '/interpreter', '/userMgr', '/dataStore' ], function() {
    return Authentication.isAdmin();
  }, '/');

});
