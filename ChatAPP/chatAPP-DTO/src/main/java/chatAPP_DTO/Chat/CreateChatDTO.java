package chatAPP_DTO.Chat;

public class CreateChatDTO {

	private long createdBy;

	private long []otherUser;

	public long getCreatedBy() {
		return createdBy;
	}

	public long[] getOtherUser() {
		return otherUser;
	}

	public CreateChatDTO setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
		return this;
	}

	public CreateChatDTO setOtherUser(long [] otherUser) {
		this.otherUser = otherUser;
		return this;
	}

}
