angular.module('scoutbookApp')
.controller('navController', function($rootScope, $scope, $http, AuthenticationService, profileUrl) {
	$rootScope.credentials = {};
	
	$scope.logout = function() {
		AuthenticationService.logout();
	};
	
	(function () {
        $http.get(profileUrl + $rootScope.profileId)
        .then(function(response) {
            $rootScope.name = response.data.firstname + " " + response.data.lastname;
        }, function(response) {
            $scope.infoProfile = "Something went wrong with profile";
        });
    })();

});