package chatAPP_database.Chat;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;

import chatAPP_CommontPart.Log4j2.Log4j2;
import chatAPP_database.CustomJpaRepository;
import chatAPP_database.Chat.UserChats.CompositePrimaryKey;

public interface UserChatsRepository extends CustomJpaRepository<UserChats,CompositePrimaryKey> {

	@Query(value="Select "+UserChats.userIDcolumnName+" from "+UserChats.userChatsTableName
			+" where "+UserChats.chatIDcolumnName+" = :chatid"
			,nativeQuery=true)
	public Set<Long>getUserId(String chatid);
	
	boolean existsByPrimaryKeyUserIDAndPrimaryKeyChatID(long userId, String chatId);
	
	public default void createUserChatSchema(long userID,String chatID,ChatEntity chat) {
		Optional<UserChats> entity=this.findById(CompositePrimaryKey.createCompositeKey(chatID, userID));
		if(entity.isEmpty()) {
			Log4j2.log.warn(Log4j2.MarkerLog.Database.getMarker(),String.format("createUserChatSchema was skipped, schema has been created yet"
					+ " userId: %d chatID: %s"
					+ "", userID,chatID));
			return;
		}
		UserChats userEntity=new UserChats()
		.setPrimaryKey(chatID, userID)
		.setChat(chat);
		this.save(userEntity);
	}
}
