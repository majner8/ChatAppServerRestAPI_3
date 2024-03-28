package chatAPP_DTO.Message;

import java.time.LocalDateTime;

public class UserChatOverViewDTO {

	private long senderID;
	private String senderNickName;
	private String chatID;
	private String chatName;
	private String message;
	private LocalDateTime timeStamp;
	private long order;
	public long getSenderID() {
		return senderID;
	}
	public String getSenderNickName() {
		return senderNickName;
	}
	public String getChatID() {
		return chatID;
	}
	public String getChatName() {
		return chatName;
	}
	public String getMessage() {
		return message;
	}
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
	public long getOrder() {
		return order;
	}
	public UserChatOverViewDTO setSenderID(long senderID) {
		this.senderID = senderID;
		return this;

	}
	public UserChatOverViewDTO setSenderNickName(String senderNickName) {
		this.senderNickName = senderNickName;
		return this;

	}
	public UserChatOverViewDTO setChatID(String chatID) {
		this.chatID = chatID;
		return this;

	}
	public UserChatOverViewDTO setChatName(String chatName) {
		this.chatName = chatName;
		return this;

	}
	public UserChatOverViewDTO setMessage(String message) {
		this.message = message;
		return this;

	}
	public UserChatOverViewDTO setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
		return this;

	}
	public UserChatOverViewDTO setOrder(long order) {
		this.order = order;
		return this;
	}
	
	
	
	
}
