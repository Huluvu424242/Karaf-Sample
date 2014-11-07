'use strict';
// definieren eines Moduls
//var itemVerwaltung = angular.module("itemVerwaltung", ["ngResource", "hateoas"]);
//itemVerwaltung.config(function (HateoasInterceptorProvider) {
//HateoasInterceptorProvider.transformAllResponses();
//});

var itemVerwaltung = angular.module("itemVerwaltung", []);

//$locationProvider.html5Mode(true);

// hinzufügen eines Controllers zum Modul
itemVerwaltung.controller("ContentController", function($scope, $http) {
	
	$scope.goBack = function(url){
		location.href=url;
	}
	
	$scope.listCategories = function() {
		
		$http.get('http://localhost:8181/ube/kategorien/all').success(
				function(data) {
					$scope.liste = data;
				});
	}
	
	$scope.listItems = function() {
		
		$http.get('http://localhost:8181/ube/items/all').success(
				function(data) {
					$scope.liste = data;
				});
	}
	
});
