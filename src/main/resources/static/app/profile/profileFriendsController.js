angular.module('scoutbookApp')
.controller('profileFriendsController', function($rootScope, $scope, $http, $stateParams, profileUrl) {
	
	(function () {
       $http.get(profileUrl + $stateParams.profileId + "/friends")
        .then(function(response) {
            $scope.friends = response.data._embedded.userProfiles;
        }, function(response) {
            $scope.infoFriends = "Something went wrong with friends";
        });
    })();
	
	
	(function (){
		if($rootScope.profileId != $stateParams.profileId){
			$http.get(profileUrl + $rootScope.profileId + "/friends")
			.then(function(response){
				$scope.observerFriends = response.data._embedded.userProfiles;
			}, function(response){
				$scope.infoObserver = "It failed to load observer friends";
			});	
		}		
	})();
	
	$scope.isObserverFriend= function(friend){
		var found = false;
		if($rootScope.profileId != $stateParams.profileId){
			for(x in $scope.observerFriends){
				if($scope.observerFriends[x].id == friend.id){
					found = true;
					break;
				}
			}			
		} else{
			found = true;
		}
		
		return found;
	};
});