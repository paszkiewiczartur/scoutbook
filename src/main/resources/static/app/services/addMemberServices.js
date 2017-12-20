angular.module('scoutbookApp')
.constant("addGroupMember", "http://localhost:8080/api/groups/addMember")
.constant("addEventMember", "http://localhost:8080/api/events/addMember")
.service('AddMemberService', function($http, LOGIN_ENDPOINT, $rootScope, $state,
		addEventMember, addGroupMember) {
	this.addGroupMember = function(friendId, groupId){
    	var member = {};
    	member.memberId = friendId;
    	member.groupOrEventId = groupId;
        var promise = $http({
            method : 'POST',
            url : addGroupMember,
            data : member
        }).then(function(response) {
        	friend.isMember = true;
            console.log(response.data);
        }, function(response) {
        	console.log("Couldn't add new member");
        });
        return promise;
    };
	this.addEventMember = function(friendId, groupId){
    	var member = {};
    	member.memberId = friendId;
    	member.groupOrEventId = groupId;
        var promise = $http({
            method : 'POST',
            url : addEventMember,
            data : member
        }).then(function(response) {
        	console.log(response.data);
        }, function(response) {
        	console.log("Couldn't add new member");
        });
        return promise;
    };

});