'use strict';

angular.module('zeppelinWebApp')

.run(function(RouteFilter, Authentication) {

  RouteFilter.register('guest', [ '/' ], function() {
    return !Authentication.exists();
  }, '/');

  RouteFilter.register('user', [ '/workspace/:workspaceId',
                                 '/workspaceWizard/:workspaceId',
                                 '/notebook/:noteId',
                                 '/notebook/:noteId/paragraph/:paragraphId?',
                                 '/userUpdate' ], function() {
    return Authentication.exists();
  }, '/');

  RouteFilter.register('normal', [], function() {
    return Authentication.isNormal();
  }, '/');

  RouteFilter.register('admin', [ '/interpreter', 
                                  '/userMgr', 
                                  '/datastore',
                                  '/datastoreWizard/:datastoreId',
                                  '/datasource',
                                  '/datasourceWizard/:datasourceId'], function() {
    return Authentication.isAdmin();
  }, '/');

});
