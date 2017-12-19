package pl.scoutbook.model;

public class AddMember {
	private Long memberId;
	private Long groupOrEventId;
	
	public AddMember(){}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getGroupOrEventId() {
		return groupOrEventId;
	}

	public void setGroupOrEventId(Long groupOrEventId) {
		this.groupOrEventId = groupOrEventId;
	}
	
	
}
