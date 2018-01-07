package pl.scoutbook.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long user;
    @NotNull
    private Long friend;
    
    public Conversation(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public Long getFriend() {
		return friend;
	}

	public void setFriend(Long friend) {
		this.friend = friend;
	}
    
    
}
