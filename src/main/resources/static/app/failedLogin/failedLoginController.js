angular.module('scoutbookApp')
.controller('failedLoginController', function($rootScope, $location, $state, $scope, AuthenticationService) {
	$rootScope.credentials = {};
	$scope.login = function() {
		console.log("inside login");
		AuthenticationService.authenticate($rootScope.credentials);
	}
});