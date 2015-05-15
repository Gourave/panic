'use strict';

/**
 * @ngdoc function
 * @name frontendApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the frontendApp
 */
 


 
angular.module('frontendApp')
  .controller('MapCtrl', function($scope, $http) {
    $scope.map = {center: {latitude: 40.1451, longitude: -99.6680 }, zoom: 4, bounds: {}};
    $scope.options = {scrollwheel: false};
	 $http.get('panicpackages').success(
        function(response){
            $scope.panicMarkers = response;
			//$scope.panicMarkers = [{"id":"554dca1e94cbfddc051bc53f","latitude":69,"longitude":69,"title":"Alvin"},{"id":"554e2e9be6516e18108f6e6c"},{"id":"554e2f0b913053b40a2a9935"},{"id":"554e3484b495c7f4136bed98","latitude":19,"longitude":69,"title":"Alvin"},{"id":"554e35c4bb4df778141a87d9","latitude":29,"longitude":29,"title":"AlvinGG"},{"id":"554e35d5bb4df778141a87da","latitude":69,"longitude":69,"title":"AlvinGG"}];

			for( var i=0, l=$scope.panicMarkers.length; i<l; i++ ) {
				$scope.panicMarkers[i].onClick = function() {
					//console.log("Clicked!");
					//ret.show = !ret.show;
				};
				$scope.panicMarkers[i].id = $scope.panicMarkers[i]._id; //Hack, angular google maps requres 'id', not '_id'
			}
			console.log($scope.panicMarkers);
        }
    );
    
});