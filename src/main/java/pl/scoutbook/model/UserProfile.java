package pl.scoutbook.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity
public class UserProfile {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "id_user_profile")
    private Long id;
    @NotNull
    @Column(nullable = false)
	private String firstname;
    @NotNull
    @Column(nullable = false)
	private String lastname;
	@NotNull
	@Column(nullable = false)
	private Gender gender;
	@NotNull
	@Column(nullable = false)
	private LocalDate birthday;
    @ManyToMany
    @JoinTable(name = "user_groups",
            joinColumns = { @JoinColumn(name = "user_profile_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "groups_id", referencedColumnName = "id") })
    private List<Group> groups;
    
	public UserProfile(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Enumerated(EnumType.ORDINAL)
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

}