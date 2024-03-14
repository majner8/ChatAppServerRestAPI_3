package chatAPP_database.Chat;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import chatAPP_DTO.Chat.UserChatInformationDTO;




@Entity(name=UserChats.userChatsTableName)
public class UserChats {
	public static final String userChatsTableName="users_chat";
	
	public static final String userIDcolumnName="user_id";
	public static final String chatIDcolumnName="chat_id";
	public static final String userNickNamecolumnName="user_nick_name_in_chat";
	public static final String chatNamecolumnName="chat_name_of_user";
	public static final String memberFromcolumnName="member_from";
	public static final String memberUntilcolumnName="member_until";
	public static final String lastSeenMessageIDcolumnName="last_seen_message_id";
	public static final String joinChatColumnName="appropriate_chat";
	
	@Column(name=UserChats.userNickNamecolumnName)
	//name of User
	private String userNickName;
	@Column(name=UserChats.chatNamecolumnName)
	//each user can have own chatName
	private String chatName;
	@Column(name=UserChats.memberFromcolumnName)
	private LocalDateTime memberFrom;
	@Column(name=UserChats.memberUntilcolumnName)
	private LocalDateTime memberUntil=null;
	@Column(name=UserChats.lastSeenMessageIDcolumnName)
	private String lastSeenMessageID;
    @EmbeddedId
	private CompositePrimaryKey primaryKey;
	
	@ManyToOne
	//@Column(name=UserChats.joinChatColumnName)
	@JoinColumn(name="chat",referencedColumnName=ChatEntity.chatIDColumnName)
	private ChatEntity chat;
	
	public UserChatInformationDTO convertEntityToDTO() {
		UserChatInformationDTO x=new UserChatInformationDTO();
		x.setChatName(chatName);
		x.setLastSeenMessageID(lastSeenMessageID);
		x.setMemberFrom(memberFrom);
		x.setMemberUntil(memberUntil);
		x.setUserID(this.primaryKey.getUserID());
		x.setUserNickName(userNickName);
		return x;
	}
    @Embeddable
	public static class CompositePrimaryKey implements Serializable{

		@Column(name=UserChats.userIDcolumnName)
		private long userID;
		@Column(name=UserChats.chatIDcolumnName)
		private String chatID;	
		
		public static CompositePrimaryKey createCompositeKey(String chatID,long userID) {
			return new CompositePrimaryKey()
	    			.setChatID(chatID)
	    			.setUserID(userID)
	    			;
		}
		
		public long getUserID() {
			return userID;
		}
		public CompositePrimaryKey setUserID(long userID) {
			this.userID = userID;
			return this;
		}
		public String getChatID() {
			return chatID;
		}
		public CompositePrimaryKey setChatID(String chatID) {
			this.chatID = chatID;
			return this;
		}
	}


    public UserChats setPrimaryKey(String chatID,long userID) {
    	this.setPrimaryKey(CompositePrimaryKey.createCompositeKey(chatID, userID)
    			);
    	return this;
    }
	public CompositePrimaryKey getPrimaryKey() {
		return primaryKey;
	}
	public ChatEntity getChat() {
		return chat;
	}
	public UserChats setPrimaryKey(CompositePrimaryKey primaryKey) {
		this.primaryKey = primaryKey;
		return this;
	}
	public String getChatName() {
		return chatName;
	}
	public LocalDateTime getMemberFrom() {
		return memberFrom;
	}
	public LocalDateTime getMemberUntil() {
		return memberUntil;
	}
	public String getLastSeenMessageID() {
		return lastSeenMessageID;
	}
	public UserChats setChatName(String chatName) {
		this.chatName = chatName;
		return this;

	}
	public UserChats setMemberFrom(LocalDateTime memberFrom) {
		this.memberFrom = memberFrom;
		return this;

	}
	public UserChats setMemberUntil(LocalDateTime memberUntil) {
		this.memberUntil = memberUntil;
		return this;

	}
	public UserChats setLastSeenMessageID(String lastSeenMessageID) {
		this.lastSeenMessageID = lastSeenMessageID;
		return this;

	}
	public UserChats setChat(ChatEntity chat) {
		this.chat = chat;
		return this;
	}
	
	
}
