'use strict';
// definieren eines Moduls
var contentModule = angular.module("contentModule", []);

// hinzufügen eines Controllers zum Modul
contentModule.controller("ContentController", function($scope, $http) {
	
	$scope.listCategories = function() {
		
		$http.post('http://localhost:8181/ube/kategorien.json').success(
				function(data) {
					$scope.liste = data;
				});
	}
});
