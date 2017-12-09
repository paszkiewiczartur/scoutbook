angular.module('scoutbookApp')
.constant("profileUrl", "http://localhost:8080/api/userProfiles/")
.controller('profileEntryController', function($rootScope, $scope, $http, $stateParams, profileUrl) {
	
	(function () {
		console.log(profileUrl + $stateParams.profileId);
        $http.get(profileUrl + $stateParams.profileId)
        .then(function(response) {
            $rootScope.profile = angular.copy(response.data);
            console.log("info o profilu");
            console.log($rootScope.profile);
        }, function(response) {
            $scope.infoProfile = "Something went wrong with profile";
        });
    })();
	
});