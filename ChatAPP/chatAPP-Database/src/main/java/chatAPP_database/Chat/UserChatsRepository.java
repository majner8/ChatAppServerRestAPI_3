package chatAPP_database.Chat;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;

import chatAPP_database.CustomJpaRepository;
import chatAPP_database.Chat.UserChats.CompositePrimaryKey;

public interface UserChatsRepository extends CustomJpaRepository<UserChats,CompositePrimaryKey> {

	@Query(value="Select "+UserChats.userIDcolumnName+" from "+UserChats.userChatsTableName
			+" where "+UserChats.chatIDcolumnName+" = :chatid"
			,nativeQuery=true)
	public Set<Long>getUserId(String chatid);
	
	boolean existsByPrimaryKeyUserIDAndPrimaryKeyChatID(long userId, String chatId);
}
