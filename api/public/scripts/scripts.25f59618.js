"use strict";angular.module("frontendApp",["ngAnimate","ngCookies","ngResource","ngRoute","ngSanitize","ngTouch","uiGmapgoogle-maps"]).config(["$routeProvider",function(a){a.when("/",{templateUrl:"views/main.html",controller:"MainCtrl"}).when("/map",{templateUrl:"views/map.html",controller:"MapCtrl"}).otherwise({redirectTo:"/"})}]).config(["uiGmapGoogleMapApiProvider",function(a){a.configure({key:"AIzaSyDI04z9p-iLuBheMNG9AJMnNS66w99zClw",v:"3.17",libraries:"weather,geometry,visualization"})}]),angular.module("frontendApp").controller("MainCtrl",["$scope",function(a){a.awesomeThings=["HTML5 Boilerplate","AngularJS","Karma"]}]),angular.module("frontendApp").controller("MapCtrl",["$scope","$http",function(a,b){a.map={center:{latitude:40.1451,longitude:-99.668},zoom:4,bounds:{}},a.options={scrollwheel:!1},b.get("http://panicapp.herokuapp.com/panicpackages/markers").success(function(b){alert(b),a.panicMarkers=b;for(var c=0,d=a.panicMarkers.length;d>c;c++)a.panicMarkers[c].onClick=function(){ret.show=!ret.show}})}]);