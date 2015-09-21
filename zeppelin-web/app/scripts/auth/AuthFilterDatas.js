'use strict';

angular.module('zeppelinWebApp')

.run(function(RouteFilter, Authentication) {

  RouteFilter.register('guest', [ '/' ], function() {
    return !Authentication.exists();
  }, '/');

  RouteFilter.register('user', [ '/workspace/:workspaceId',
                                 '/workspaceWizard/:workspaceId',
                                 '/notebook/:noteId/wrkspcId/:wrkspcId',
                                 '/notebook/:wrkspcId/:noteId/paragraph/:paragraphId/wrkspcId/:wrkspcId?',
                                 '/userUpdate' ], function() {
    return Authentication.exists();
  }, '/');

  RouteFilter.register('workspaceAdmin', [], function() {
    return Authentication.isWorkspaceAdmin();
  }, '/');

  RouteFilter.register('admin', [ '/interpreter', 
                                  '/userMgr', 
                                  '/datastore',
                                  '/datastoreWizard',
                                  '/datasource',
                                  '/datasourceWizard/:datasourceId'], function() {
    return Authentication.isAdmin();
  }, '/');

});
