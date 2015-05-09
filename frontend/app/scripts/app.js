'use strict';

/**
 * @ngdoc overview
 * @name frontendApp
 * @description
 * # frontendApp
 *
 * Main module of the application.
 */
angular
  .module('frontendApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'uiGmapgoogle-maps'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
      .when('/map', {
        templateUrl: 'views/map.html',
        controller: 'MapCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  })
  .config(function(uiGmapGoogleMapApiProvider) {
      uiGmapGoogleMapApiProvider.configure({
          key: 'AIzaSyDI04z9p-iLuBheMNG9AJMnNS66w99zClw',
          v: '3.17',
          libraries: 'weather,geometry,visualization'
      });
  });