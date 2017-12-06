angular.module('scoutbookApp')
.controller('navController', function($rootScope, $scope, $http, AuthenticationService) {
	$rootScope.credentials = {};
	
	$scope.logout = function() {
		AuthenticationService.logout();
	};
	
	
});