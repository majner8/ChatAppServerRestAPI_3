package chatAPP_DTO.Message;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;




public class MessageDTO {

	private Long order;
	@NotEmpty
	private String chatID;
	private long senderID;
	@NotEmpty
	private String messageID;
	@NotEmpty
	private String message;
	private LocalDateTime receivedTime;
	private boolean wasMessageRemoved=false;
	@NotNull
	private Enum typeOfAction;
	private String referencMessageID;
	private long version;



	public long getVersion() {
		return version;
	}

	public MessageDTO setVersion(Long version) {
		this.version = version;
		return this;
	}

	public String getReferencMessageID() {
		return referencMessageID;
	}

	public MessageDTO setReferencMessageID(String referencMessageID) {
		this.referencMessageID = referencMessageID;
		return this;

	}

	public Enum getTypeOfAction() {
		return typeOfAction;
	}

	public MessageDTO setTypeOfAction(Enum typeOfAction) {
		this.typeOfAction = typeOfAction;
		return this;

	}

	public long getOrder() {
		return order;
	}
	public MessageDTO setOrder(long order) {
		this.order = order;
		return this;

	}
	public String getChatID() {
		return chatID;
	}
	public MessageDTO setChatID(String chatID) {
		this.chatID = chatID;
		return this;

	}
	public long getSenderID() {
		return senderID;
	}
	public MessageDTO setSenderID(long senderID) {
		this.senderID = senderID;
		return this;

	}
	public String getMessageID() {
		return messageID;
	}
	public MessageDTO setMessageID(String messageID) {
		this.messageID = messageID;
		return this;

	}
	public String getMessage() {
		return message;
	}
	public MessageDTO setMessage(String message) {
		this.message = message;
		return this;

	}
	public LocalDateTime getReceivedTime() {
		return receivedTime;
	}
	public MessageDTO setReceivedTime(LocalDateTime receivedTime) {
		this.receivedTime = receivedTime;
		return this;

	}
	public boolean isWasMessageRemoved() {
		return wasMessageRemoved;
	}
	public MessageDTO setWasMessageRemoved(boolean wasMessageRemoved) {
		this.wasMessageRemoved = wasMessageRemoved;
		return this;

	}



}
