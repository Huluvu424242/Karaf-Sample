'use strict';
// definieren eines Moduls
var contentModule = angular.module("contentModule", []);
 
// hinzufügen eines Controllers zum Modul
contentModule.controller("ContentController", function ($scope, $http) {
   $scope.user = {};
   
   $scope.loginUser = function() {
   
   $http({
	      method: 'POST',
	      url: 'http://localhost:8181/ube/kategorien/auflisten',
	      headers: {'Content-Type': 'application/json'},
	      data:  $scope.user
	    }).success(function (data) 
	      {
	    	$scope.message=data;
	      });
   };
});
