'use strict';
/*
Integrations Controller to recieve Panic Packets
*/
angular.module('panicApi', ['config'])

.controller('IntegrationCtrl', function($scope, $http, ENV) {
  $http.get(ENV.apiEndpoint + 'panicpackages/').then(function(data) {
    $scope.panicPackage = data;
  }, function(err) {
    console.error('ERR', err);
    // err.status will contain the status code
  });
});