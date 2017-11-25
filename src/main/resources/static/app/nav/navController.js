angular.module('scoutbookApp')
.controller('navController', function($rootScope, $scope, AuthenticationService) {
	$rootScope.credentials = {};
	
	$scope.logout = function() {
		AuthenticationService.logout();
	}
});