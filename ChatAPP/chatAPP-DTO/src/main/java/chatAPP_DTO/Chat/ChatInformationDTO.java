package chatAPP_DTO.Chat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public  class ChatInformationDTO {

	
	private long createdByUserID;
	private String defaultChatName;
	private String ChatID;
	
	private List<UserChatInformationDTO> user=Collections.synchronizedList(new ArrayList<UserChatInformationDTO>());

	public static ChatInformationDTO createEmptyDTO() {
		return new ChatInformationDTO();
	}
	public long getCreatedByUserID() {
		return createdByUserID;
	}

	public String getDefaultChatName() {
		return defaultChatName;
	}

	public String getChatID() {
		return ChatID;
	}

	public List<UserChatInformationDTO> getUser() {
		return user;
	}

	public ChatInformationDTO setCreatedByUserID(long createdByUserID) {
		this.createdByUserID = createdByUserID;
		return this;
	}

	public ChatInformationDTO setDefaultChatName(String defaultChatName) {
		this.defaultChatName = defaultChatName;
		return this;
	}

	public ChatInformationDTO setChatID(String chatID) {
		ChatID = chatID;
		return this;
	}

	public ChatInformationDTO setUser(List<UserChatInformationDTO> user) {
		this.user = user;
		return this;
	}
	
	public ChatInformationDTO addUserChatInformation(UserChatInformationDTO userChatInformation) {
		this.user.add(userChatInformation);
		return this;
	}

}
