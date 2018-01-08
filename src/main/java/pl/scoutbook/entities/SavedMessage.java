package pl.scoutbook.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class SavedMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(max = 1000)
    private String message;
    @Column(nullable = false)
    LocalDateTime createdAt = LocalDateTime.now();
    @NotNull
    private Long user;
    @NotNull    
 //   @ManyToOne
 //   @JoinColumn(name = "conversation_id")
    private Long conversation;
    
    public SavedMessage(){}

    public SavedMessage(String message, Long user, Long conversation){
    	this.message = message;
    	this.createdAt = LocalDateTime.now();
    	this.user = user;
    	this.conversation = conversation;
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public Long getConversation() {
		return conversation;
	}

	public void setConversation(Long conversation) {
		this.conversation = conversation;
	}
}
