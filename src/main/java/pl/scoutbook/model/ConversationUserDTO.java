package pl.scoutbook.model;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

public class ConversationUserDTO {
	@NotNull
    private Long id;
    @NotNull
    @Column(nullable = false)
	private String firstname;
    @NotNull
    @Column(nullable = false)
	private String lastname;
    @NotNull
    private Long conversation;
    @NotNull
    private String profileImage;
    @NotNull
    private Long page;
    
    public ConversationUserDTO(){}

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

	public Long getConversation() {
		return conversation;
	}

	public void setConversation(Long conversation) {
		this.conversation = conversation;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public Long getPage() {
		return page;
	}

	public void setPage(Long page) {
		this.page = page;
	}
}
