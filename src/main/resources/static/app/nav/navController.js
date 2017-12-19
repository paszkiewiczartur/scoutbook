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
            console.log("$rootScope.name");
            console.log($rootScope.name);
        }, function(response) {
        	console.log("could not get name");
            $scope.infoProfile = "Something went wrong with profile";
        });
    })();

});