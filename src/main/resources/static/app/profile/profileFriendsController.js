angular.module('scoutbookApp')
.controller('profileFriendsController', function($rootScope, $scope, $http, $stateParams, profileUrl) {
	
	(function () {
		console.log(profileUrl + $stateParams.profileId + "/friends");
        $http.get(profileUrl + $stateParams.profileId + "/friends")
        .then(function(response) {
            $scope.friends = response.data._embedded.userProfiles;
            console.log("uwaga! przyjaciele");
        	console.log($scope.friends);
        }, function(response) {
            $scope.infoFriends = "Something went wrong with friends";
        });
    })();
	
});