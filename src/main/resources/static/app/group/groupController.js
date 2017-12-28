angular.module('scoutbookApp')
.constant("groupUrl", "/api/groups/")
.controller('groupController', function($rootScope, $scope, $http, $stateParams, $q, 
		groupUrl, postsUrl, profileUrl, AddMemberService) {

	var loadPosts = function(){
        $http.get(groupUrl + $stateParams.groupId + "/posts")
        .then(function(response) {
            $scope.groupData = response.data._embedded;
            loadPostOwners();
        }, function(response) {
            $scope.info = "Something went wrong with loading group posts.";
        });
		
	};

	var loadPostOwners = function(){
		$scope.profileErrors = []; 
		angular.forEach($scope.groupData.posts, function(post){
		    var owner = post._links.owner.href;
		    var index = owner.indexOf("api");
		    var address = owner.substr(index - 1, owner.length);
			$http.get(address)
			.then(function(response){
				post.owner = response.data;
			}, function(response){
				$scope.profileErrors.push("There is an error.");
			});			
		});
	};

	var loadGroup = function(){
		$http.get(groupUrl + $stateParams.groupId)
		.then(function(response){
			$scope.group = response.data;
		}, function(response){
			$scope.infoGroup = "Something went wrong with group."
		});
	};

	var loadUsers = function(){
		var promise = $http.get(groupUrl + $stateParams.groupId + "/users")
		.then(function(response){
			$scope.users = response.data._embedded.userProfiles;
		}, function(response){
			$scope.infoUsers = "Something went wrong with group."
		});
		return promise;
	};

	var loadFriends = function(){
		var promise = $http.get(profileUrl + $rootScope.profileId + "/friends")
		.then(function(response){
			$scope.friends = response.data._embedded.userProfiles;
		}, function(response){
			$scope.infoFriends = "Failed to load observer friends";
		});			
		return promise;
	};

	var compareFriendsAndUsers = function(){
    	angular.forEach($scope.friends, function(friend){
    		angular.forEach($scope.users, function(user){
    			if(friend.id == user.id){
    				friend.isMember = true;
    			}
    		});
    	});
    };
	
	(function () {
		var promises = [];
		loadGroup();
		loadPosts();
		promises.push(loadUsers());
		promises.push(loadFriends());
		$q.all(promises).then(function(response){
			compareFriendsAndUsers();
		}, function (error){	
			console.log("Couldn't compare users and friends.");
		});
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
    
    $scope.addMember = function(friend){
    	var promise = AddMemberService.addGroupMember(friend.id, $stateParams.groupId);
    	promise.then(function(response){
    		friend.isMember = true;
    	}, function(response){
    		console.log("Couldn't add member.");
    	});
    };
	
});