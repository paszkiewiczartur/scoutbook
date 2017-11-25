angular.module('scoutbookApp')
.controller('registerController', function($rootScope, $state, $scope) {
	$scope.signIn = function() {
		$state.go('home.wall');
	}
});