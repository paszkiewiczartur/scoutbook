package pl.scoutbook.model;

public class ChatMessage {
	private Long user;
	private String message;
	
	public ChatMessage(){}
	
	public ChatMessage(Long user, String message){
		this.user = user;
		this.message = message;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
