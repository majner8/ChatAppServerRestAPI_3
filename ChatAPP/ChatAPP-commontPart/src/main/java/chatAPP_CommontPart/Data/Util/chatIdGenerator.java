package chatAPP_CommontPart.Data.Util;

import org.springframework.stereotype.Component;

public interface chatIdGenerator {

	public static final String userToUsersuflix="userID";
	public default String generateIDForUserToUserChat(long user1,long user2) {
		if(user1==user2) {
			throw new IllegalArgumentException("user1 id and user2 id cannot be same");
		}
		long firstID;
		long secondID;
		if(user1<user2) {
			firstID=user1;
			secondID=user2;
		}
		else {
			firstID=user2;
			secondID=user1;
		}
		return String.format("%d%s%d", firstID,userToUsersuflix,secondID);
	}
	@Component
	public static class classJustForBean implements chatIdGenerator{}

}
