angular.module('scoutbookApp')
.constant("profileGroupsUrl", "http://localhost:8080/api/userProfiles/")
.constant("postsUrl", "http://localhost:8080/api/posts/")
.constant("userWallUrl", "http://localhost:8080/api/userWall/search/findByUser?user=")
.constant("pageSize", 3)
.controller('wallController', function($rootScope, $scope, $http, $q, profileGroupsUrl, userWallUrl, postsUrl, pageSize) {

	$scope.loadPosts = function(){
		var promises = [];
		$scope.posts = [];
		$scope.postsErrors = [];
		var post;
		angular.forEach($scope.userWall, function(item){
			var promise = $http.get(postsUrl + item.content.post_id)
			.then(function(response){
				$scope.posts.push(response.data.content);
			}, function(response){
				$scope.postsErrors.push("There was an error with loading the post.");
			});
			promises.push(promise);
		});
		return $q.all(promises);		
	}
	$scope.loadPostsOwners = function(){
		$scope.ownersErrors = [];
		angular.forEach($scope.posts, function(item){
			$http.get(postsUrl + item.id + "/owner")
			.then(function(response){
				item.owner = response.data;
			}, function(response){
				$scope.ownersErrors.push("There was an error with loading posts owners.");
			});			
		});		
	};
	
	$scope.loadNextUserWallPosts = function(page, shown){
		$http.get(userWallUrl+$rootScope.profileId+"&shown="+shown+"&page="+page+"&size="+pageSize+"&sort=desc")
		.then(function(response){
			$scope.userWall = response.data._embedded.userWall;
			$scope.loadPosts().then(function(response){
				$scope.loadPostsOwners();
			}, function (error){	
				console.log("Scoutbook can't load post owners.");
			});
		}, function(response){	
			$scope.userWallError = "There was an error with downloading posts.";
		});
	};

	
	(function () {
        $http.get(profileGroupsUrl + $rootScope.profileId + "/groups")
        .then(function(response) {
            $scope.groupsData = angular.copy(response.data._embedded.groups);
        }, function(response) {
            $scope.infoGroups = "Something went wrong with groups.";
        });
    })();

	(function () {
        $http.get(profileGroupsUrl + $rootScope.profileId + "/events")
        .then(function(response) {
            $scope.eventsData = angular.copy(response.data._embedded.events);
        }, function(response) {
            $scope.infoEvents = "Something went wrong with events.";
        });
    })();

	(function () {
		$scope.loadNextUserWallPosts(0, false);
	})();


});