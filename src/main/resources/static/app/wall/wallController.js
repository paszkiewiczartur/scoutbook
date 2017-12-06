angular.module('scoutbookApp')
.constant("profileGroupsUrl", "http://localhost:8080/api/userProfiles/")
.controller('wallController', function($rootScope, $scope, $http, profileGroupsUrl) {
	
	(function () {
		console.log(profileGroupsUrl + $rootScope.profileId + "/groups");
        $http.get(profileGroupsUrl + $rootScope.profileId + "/groups")
        .then(function(response) {
            $scope.groupsData = angular.copy(response.data._embedded.groups);
            console.log($scope.groupsData);
        }, function(response) {
            $scope.infoGroups = "Something went wrong with groups";
        });
    })();

    
});