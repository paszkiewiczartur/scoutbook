angular.module('scoutbookApp')
.constant("groupUrl", "http://localhost:8080/api/groups/")
.controller('groupController', function($rootScope, $scope, $http, $stateParams, groupUrl, postsUrl) {

	var loadPosts = function(){
        $http.get(groupUrl + $stateParams.groupId + "/posts")
        .then(function(response) {
            $scope.groupData = response.data._embedded;
            loadPostOwners();
        }, function(response) {
            $scope.info = "Something went wrong";
        });
		
	}

	var loadPostOwners = function(){
		$scope.profileErrors = []; 
		angular.forEach($scope.groupData.posts, function(post){
			$http.get(post._links.owner.href)
			.then(function(response){
				post.owner = response.data;
			}, function(response){
				$scope.profileErrors.push("Wystąpił błąd");
			});			
		});
	};

	(function () {
		loadPosts();
	})();
			
    $scope.newPostText = "";
    
    $scope.sendNewPost = function(){
    	if($scope.newPostText != "" && $scope.newPostText != null){
    		var post = {};
    		post.content = $scope.newPostText;
        	post.owner_id = Number($rootScope.profileId);
        	post.category = "GROUP";
        	post.groups_id = Number($stateParams.groupId);
            $http({
                method : 'POST',
                url : postsUrl,
                data : post
            }).then(function(response) {
                console.log(response.data);
                $scope.newPostText = "";
                loadPosts();
            }, function(response) {
            	console.log(response.data);
            });
    	}
    };
	
});