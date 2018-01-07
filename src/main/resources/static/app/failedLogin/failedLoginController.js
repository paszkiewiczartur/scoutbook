angular.module('scoutbookApp')
.controller('failedLoginController', function($rootScope, $location, $state, $scope, AuthenticationService) {
	$rootScope.credentials = {};
	$scope.login = function() {
		AuthenticationService.authenticate($rootScope.credentials);
	}
});