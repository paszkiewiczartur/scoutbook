angular.module('scoutbookApp')
.constant("profileGroupsUrl", "http://localhost:8080/api/userProfiles/")
.constant("postsUrl", "http://localhost:8080/api/posts/")
.constant("userWallUrl", "http://localhost:8080/api/userWall/search/findByUser?user=")
.constant("newEventUrl", "http://localhost:8080/api/events")
.constant("newGroupUrl", "http://localhost:8080/api/groups")
.constant("pageSize", 3)
.controller('wallController', function($rootScope, $scope, $http, $q, $state,
		newGroupUrl, newEventUrl, profileGroupsUrl, userWallUrl, postsUrl, pageSize, AddMemberService) {

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

    $scope.selectedPage = 0;
    
    $scope.nextPage = function(){
    	$scope.selectedPage = $scope.selectedPage + 1;
    	$scope.loadNextUserWallPosts($scope.selectedPage, false);
    };

    $scope.previousPage = function(){
    	$scope.selectedPage = $scope.selectedPage - 1;
    	$scope.loadNextUserWallPosts($scope.selectedPage, false);
    };
    
    $scope.newPostText = "";
    
    $scope.sendNewPost = function(){
    	if($scope.newPostText != "" && $scope.newPostText != null){
    		var post = {};
    		post.content = $scope.newPostText;
        	post.owner_id = Number($rootScope.profileId);
        	post.category = "USERWALL";
        	post.user_profile_id = Number($rootScope.profileId);
            $http({
                method : 'POST',
                url : postsUrl,
                data : post
            }).then(function(response) {
                console.log(response.data);
                $scope.newPostText = "";
                $scope.loadNextUserWallPosts(0, false);
            }, function(response) {
            	console.log(response.data);
            });
    	}
    };
    
    $scope.newGroup = {};
    var newGroupWindow = document.getElementById('newGroup');
 	$scope.openNewGroup = function(){
 		newGroupWindow.style.display = "block";
 	};
    $scope.createGroup = function(){
    	var group = {};
    	group.name = $scope.newGroup.name;
    	if($scope.newGroup.image != null && $scope.newGroup.image != "")
    		group.image = $scope.newGroup.image;
    	console.log(group);
 		$http({
            method : 'POST',
            url : newGroupUrl,
            data : group
        }).then(function(response) {
        	$scope.newEvent = {};
         	newGroupWindow.style.display = "none";
         	AddMemberService.addGroupMember($rootScope.profileId, response.data.id);
			$state.go("home.group", {groupId: response.data.id});
        }, function(response) {
        	console.log(response.data);
        	$scope.newGroup = {};
        	$scope.newGroup.error = "Something went wrong. Try again later.";
        });
    };
 	var closeGroupSpan = document.getElementById("closeGroup");
 	closeGroupSpan.onclick = function() { 
     	newGroupWindow.style.display = "none";
     	$scope.newGroup = {};
 	};
    
 	$scope.newEvent = {};
 	var newEventWindow = document.getElementById("newEvent");
 	$scope.openNewEvent = function(){
 		newEventWindow.style.display = "block";
 	};
 	var closeEventSpan = document.getElementById("closeEvent");
 	closeEventSpan.onclick = function() { 
     	newEventWindow.style.display = "none";
     	$scope.newEvent = {};
 	};
 	$scope.createEvent = function(){
 		var event = prepareEvent();
 		$http({
            method : 'POST',
            url : newEventUrl,
            data : event
        }).then(function(response) {
        	$scope.newEvent = {};
         	newEventWindow.style.display = "none";
         	AddMemberService.addEventMember($rootScope.profileId, response.data.id);
			$state.go("home.event", {eventId: response.data.id});
        }, function(response) {
        	$scope.newEvent = {};
        	$scope.newEvent.error = "Something went wrong. Try again later.";
        });
 	};

 	var prepareEvent = function(){
 		var event = {};
 		event.organizer = Number($rootScope.profileId);
 		event.name = $scope.newEvent.name;
 		event.place = $scope.newEvent.place;
 		if($scope.newEvent.info != null && $scope.newEvent.info != "")
 			event.info = $scope.newEvent.info;
 		event.start = prepareDateTime("start");
 		event.end = prepareDateTime("end");
 		if($scope.newEvent.image != null && $scope.newEvent.image != "")
 			event.image = $scope.newEvent.image;
 		return event;
 	};
 	
 	var prepareDateTime = function(startEnd){
 		var result = "";
 		var date;
 		var time;
 		if(startEnd == "start"){
 			date = $scope.newEvent.startDate;
 			time = $scope.newEvent.startTime;
 		} else if(startEnd == "end"){
 			date = $scope.newEvent.endDate;
 			time = $scope.newEvent.endTime;
 		} else{
 			return null;
 		}
 		result += date.getFullYear();
 		result += "-";
 		if(date.getMonth() + 1 < 10) result += "0";
 		result += (date.getMonth() + 1);
 		result += "-";
 		if(date.getDate() < 10) result += "0";
 		result += date.getDate();
 		result += "T";
 		if(time.getHours() < 10) result += "0";
 		result += time.getHours();
 		result += ":";
 		if(time.getMinutes() < 10) result += "0";
 		result += time.getMinutes();
 		result += ":";
 		result += "00";
 		return result;
 	};
 	
});