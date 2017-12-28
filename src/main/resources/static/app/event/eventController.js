angular.module('scoutbookApp')
.constant("eventsUrl", "/api/events/")
.controller('eventController', function($rootScope, $scope, $http, $stateParams, $q,
		 profileUrl, eventsUrl, pageSize, postsUrl, AddMemberService) {
		
	var loadPosts = function(){
        $http.get(eventsUrl + $stateParams.eventId + "/posts")
        .then(function(response) {
            $scope.eventData.posts = response.data._embedded.posts;
            loadPostOwners();
        }, function(response) {
            $scope.infoPosts = "Something went wrong with posts";
        });
	};
	
	var loadOrganizer = function(){
        $http.get(eventsUrl + $stateParams.eventId + "/organizer")
        .then(function(response) {
            $scope.eventData.organizer = response.data;
        }, function(response) {
            $scope.infoOrganizer = "Something went wrong with organizer";
        });		
	}
	
	var loadPostOwners = function(){
		$scope.profileErrors = []; 
		angular.forEach($scope.eventData.posts, function(post){
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
	
    $scope.newPostText = "";
    
    $scope.sendNewPost = function(){
    	if($scope.newPostText != "" && $scope.newPostText != null){
    		var post = {};
    		post.content = $scope.newPostText;
        	post.owner_id = Number($rootScope.profileId);
        	post.category = "EVENT";
        	post.events_id = Number($stateParams.eventId);
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


	var loadUsers = function(){
		var promise = $http.get(eventsUrl + $stateParams.eventId + "/users")
		.then(function(response){
			$scope.users = response.data._embedded.userProfiles;
		}, function(response){
			$scope.infoUsers = "Something went wrong with event."
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
        $http.get(eventsUrl + $stateParams.eventId)
        .then(function(response) {
            $scope.eventData = response.data;
            loadOrganizer();
            loadPosts();
    		var promises = [];
    		promises.push(loadUsers());
    		promises.push(loadFriends());
    		$q.all(promises).then(function(response){
    			compareFriendsAndUsers();
    		}, function (error){	
    			console.log("Couldn't compare users and friends.");
    		});

        }, function(response) {
            $scope.infoEvent = "Something went wrong with event";
        });		
    })();
	
    $scope.addMember = function(friend){
    	var promise = AddMemberService.addEventMember(friend.id, $stateParams.eventId);
    	promise.then(function(response){
    		friend.isMember = true;
    	}, function(response){
    		console.log("Couldn't add member.");
    	});
    };
	
	
});