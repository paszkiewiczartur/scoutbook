package pl.scoutbook.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import pl.scoutbook.serializer.CustomPostSerializer;

@Entity
@JsonSerialize(using = CustomPostSerializer.class)
public class Post {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(max = 1000)
    private String content;
    @NotNull 
    LocalDateTime createdAt = LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "groups_id")
    private Group group;
    @ManyToOne
    @JoinColumn(name = "events_id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "user_profile_id")
    private UserProfile user_profile;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserProfile owner;
    @NotNull
    private PostCategory category;
    
    public Post(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}


	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public UserProfile getUser_profile() {
		return user_profile;
	}

	public void setUser_profile(UserProfile user_profile) {
		this.user_profile = user_profile;
	}

	public UserProfile getOwner() {
		return owner;
	}

	public void setOwner(UserProfile owner) {
		this.owner = owner;
	}
	
	@Enumerated(EnumType.ORDINAL)
	public PostCategory getCategory() {
		return category;
	}

	public void setCategory(PostCategory category) {
		this.category = category;
	}

}
