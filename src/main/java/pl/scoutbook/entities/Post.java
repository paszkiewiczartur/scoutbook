package pl.scoutbook.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import pl.scoutbook.model.PostCategory;
import pl.scoutbook.serializer.CustomPostDeserializer;
import pl.scoutbook.serializer.CustomPostSerializer;

@Entity
@JsonSerialize(using = CustomPostSerializer.class)
@JsonDeserialize(using = CustomPostDeserializer.class)
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Size(max = 1000)
	private String content;
	@Column(nullable = false)
	private LocalDateTime createdAt;
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

	public Post() {
	}

	private Post(Builder builder) {
		this.content = builder.content;
		this.owner = builder.owner;
		this.category = builder.category;
		this.createdAt = builder.createdAt;

		this.user_profile = builder.user_profile;
		this.group = builder.group;
		this.event = builder.event;
	}

	public static class Builder {
		private String content;
		private LocalDateTime createdAt;
		private UserProfile owner;
		private PostCategory category;

		private UserProfile user_profile = null;
		private Group group = null;
		private Event event = null;

		public Builder(String content, UserProfile owner, PostCategory category) {
			this.content = content;
			this.owner = owner;
			this.category = category;
			this.createdAt = LocalDateTime.now();
		}

		public Builder user_profile(UserProfile user_profile) {
			this.user_profile = user_profile;
			return this;
		}

		public Builder group(Group group) {
			this.group = group;
			return this;
		}

		public Builder event(Event event) {
			this.event = event;
			return this;
		}

		public Post build() {
			return new Post(this);
		}
	}

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
	public UserProfile getUserProfile() {
		return user_profile;
	}
	public void setUserProfile(UserProfile user_profile) {
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