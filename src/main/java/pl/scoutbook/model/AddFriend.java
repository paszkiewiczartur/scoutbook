package pl.scoutbook.model;

public class AddFriend {
	private Long userId;
	private Long friendId;
	
	public AddFriend(){}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getFriendId() {
		return friendId;
	}

	public void setFriendId(Long friendId) {
		this.friendId = friendId;
	}
}
