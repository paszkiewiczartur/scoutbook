package pl.scoutbook.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import pl.scoutbook.serializer.CustomEventDeserializer;

@Entity
@Table(name = "events")
//@JsonSerialize(using = CustomEventSerializer.class)
@JsonDeserialize(using = CustomEventDeserializer.class)
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private UserProfile organizer;
    @NotNull
    private String name;
    @NotNull
    private String place;
    @NotNull
//    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime start;
    @NotNull
//    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime end;
    private String image;
    private String info;
    @ManyToMany(mappedBy = "events")
    private List<UserProfile> users;
    @OneToMany(mappedBy = "event")
    private List<Post> posts;
    
    public Event(){}
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UserProfile getOrganizer() {
		return organizer;
	}
	public void setOrganizer(UserProfile organizer) {
		this.organizer = organizer;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public LocalDateTime getStart() {
		return start;
	}
	public void setStart(LocalDateTime start) {
		this.start = start;
	}
	public LocalDateTime getEnd() {
		return end;
	}
	public void setEnd(LocalDateTime end) {
		this.end = end;
	}
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public List<UserProfile> getUsers() {
		return users;
	}
	public void setUsers(List<UserProfile> users) {
		this.users = users;
	}
	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

    
}
