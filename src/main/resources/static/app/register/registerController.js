angular.module('scoutbookApp')
.controller('registerController', function($rootScope, $state, $scope, AuthenticationService) {
	$scope.credentials = {};
	
	$scope.login = function() {
		AuthenticationService.authenticate($scope.credentials);
	}
});