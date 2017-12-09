angular.module('scoutbookApp')
.constant("userWallPostsUrl", "http://localhost:8080/api/posts/search/findByUserWall?user_profile=")
.controller('profileMainController', function($rootScope, $scope, $http, $stateParams, profileUrl, userWallPostsUrl, pageSize, postsUrl) {

	$scope.loadPostsOwners = function(){
		$scope.ownersErrors = [];
		angular.forEach($scope.posts, function(item){
			$http.get(postsUrl + item.content.id + "/owner")
			.then(function(response){
				item.owner = response.data;
			}, function(response){
				$scope.ownersErrors.push("There was an error with loading posts owners.");
			});			
		});		
	};
	
	$scope.loadNextUserWallPosts = function(page){
		console.log(userWallPostsUrl+$stateParams.profileId+"&page="+page+"&size="+pageSize+"&sort=desc");
		$http.get(userWallPostsUrl+$stateParams.profileId+"&page="+page+"&size="+pageSize+"&sort=desc")
		.then(function(response){
			console.log(response);
			$scope.posts = response.data._embedded.posts;			
			console.log($scope.posts);
			$scope.loadPostsOwners();
		}, function(response){	
			$scope.userWallError = "There was an error with loading posts.";
			console.log(response);
		});
	};
	
	(function () {
		$scope.loadNextUserWallPosts(0);
	})();
	
});