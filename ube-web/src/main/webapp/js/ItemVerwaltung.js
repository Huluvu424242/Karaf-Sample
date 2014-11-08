'use strict';
// definieren eines Moduls
// var itemVerwaltung = angular.module("itemVerwaltung", ["ngResource",
// "hateoas"]);
// itemVerwaltung.config(function (HateoasInterceptorProvider) {
// HateoasInterceptorProvider.transformAllResponses();
// });

var itemVerwaltung = angular.module("itemVerwaltung", []);


// controller to navigation
itemVerwaltung.controller("NavigationController", function($scope) {
		
	$scope.goTo = function(url){
		location.href=url;
	}
});


// controller to manage categories
itemVerwaltung.controller("CategoryController", function($scope, $http) {
		
	$scope.listCategories = function() {
	
		$http.get('/ube/kategorien/all').success(
			function(data) {
				$scope.liste = data;
		});
	}
	
//	$scope.toPut = function(targetURL) {
//	
//		 $http({
//			 method: 'PUT',
//			 url: targetURL,
//			 headers: {'Content-Type': 'application/json'},
//			 data: $scope.category
//			 }).success(function (data){
//				// location.href=targetURL;
//			 	});
//	}

});


// Controller to manage items
itemVerwaltung.controller("ItemController", function($scope, $http) {
		
	$scope.listItems = function() {
		
		$http.get('/ube/items/all').success(
				function(data) {
					$scope.liste = data;
				});
	}
	
});

