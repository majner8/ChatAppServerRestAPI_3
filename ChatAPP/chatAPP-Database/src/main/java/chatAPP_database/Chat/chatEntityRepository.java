package chatAPP_database.Chat;


import java.util.List;
import java.util.Optional;

import chatAPP_CommontPart.Log4j2.Log4j2;
import chatAPP_DTO.Chat.ChatInformationDTO;
import chatAPP_DTO.Chat.UserChatInformationDTO;
import chatAPP_database.CustomJpaRepository;


public interface chatEntityRepository extends CustomJpaRepository<ChatEntity,String>  {

	
	public default ChatEntity createChatUserToUserDatabaseSchema(long createdByUser,String chatID) {
		ChatEntity newEntity=new ChatEntity();
		newEntity.setChatID(chatID);
		newEntity.setCreatedByUserID(createdByUser);
		return this.InsertOrIgnore(newEntity,newEntity.getChatID());
	}
	
	public default ChatInformationDTO convertEntityToDTO(ChatEntity entity) {
		List<UserChatInformationDTO> chats=
				entity.getChat().stream()
				.map((value)->{
					return 	new UserChatInformationDTO()
							.setChatName(value.getChatName())
							.setLastSeenMessageID(value.getLastSeenMessageID())
							.setMemberFrom(value.getMemberFrom())
							.setMemberUntil(value.getMemberUntil())
							.setUserID(value.getPrimaryKey().getUserID())
							.setUserNickName(value.getUserNickName());
					
				}).toList();
				
			
		
		return new ChatInformationDTO()
				.setChatID(entity.getChatID())
				.setCreatedByUserID(entity.getCreatedByUserID())
				.setDefaultChatName(entity.getDefaultChatName())
				.setUser(chats);
				
	}
}
