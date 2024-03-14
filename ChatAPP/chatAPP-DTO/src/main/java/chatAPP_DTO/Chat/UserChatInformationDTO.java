package chatAPP_DTO.Chat;

import java.time.LocalDateTime;

public class UserChatInformationDTO{
	private String userNickName;
	private LocalDateTime memberFrom;
	private LocalDateTime memberUntil;
	private long userID;
	private String chatName;
	private String lastSeenMessageID;
	
	public static UserChatInformationDTO createEmptyObject() {
		return new UserChatInformationDTO();
	}
	
	public String getUserNickName() {
		return userNickName;
	}
	public LocalDateTime getMemberFrom() {
		return memberFrom;
	}
	public LocalDateTime getMemberUntil() {
		return memberUntil;
	}
	public long getUserID() {
		return userID;
	}
	public String getChatName() {
		return chatName;
	}
	public String getLastSeenMessageID() {
		return lastSeenMessageID;
	}
	public UserChatInformationDTO setUserNickName(String userNickName) {
		this.userNickName = userNickName;
		return this;
	}
	public UserChatInformationDTO setMemberFrom(LocalDateTime memberFrom) {
		this.memberFrom = memberFrom;
		return this;
	}
	public UserChatInformationDTO setMemberUntil(LocalDateTime memberUntil) {
		this.memberUntil = memberUntil;
		return this;
	}
	public UserChatInformationDTO setUserID(long userID) {
		this.userID = userID;
		return this;
	}
	public UserChatInformationDTO setChatName(String chatName) {
		this.chatName = chatName;
		return this;
	}
	public UserChatInformationDTO setLastSeenMessageID(String lastSeenMessageID) {
		this.lastSeenMessageID = lastSeenMessageID;
		return this;
	}

	
}

