angular.module('scoutbookApp')
.constant("profileUrl", "http://localhost:8080/api/userProfiles/")
.controller('profileEntryController', function($rootScope, $scope, $http, $stateParams, profileUrl) {
	
	(function () {
        $http.get(profileUrl + $stateParams.profileId)
        .then(function(response) {
            $rootScope.profile = angular.copy(response.data);
        }, function(response) {
            $scope.infoProfile = "Something went wrong with profile";
        });
    })();
	
});